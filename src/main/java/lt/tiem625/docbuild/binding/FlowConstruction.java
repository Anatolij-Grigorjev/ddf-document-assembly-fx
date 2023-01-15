package lt.tiem625.docbuild.binding;

import lt.tiem625.docbuild.binding.FlowStructure.DataFlowMapping;
import lt.tiem625.docbuild.data.BusinessApplication;
import lt.tiem625.docbuild.data.Service;
import lt.tiem625.docbuild.data.Structure;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Binder facility to put together raw data of the kind described in the
 * data.* package into the required shape of a flow
 */
public class FlowConstruction {

    FlowStructure constructing;

    private ApplicationsMappingsContext applicationsMappingsContext;

    public FlowConstruction() {
        constructing = new FlowStructure();
    }

    public ApplicationsMappingsContext applicationsMappingsConstruction(BusinessApplication source, BusinessApplication target) {
        if (source == null || target == null) {
            throw new IllegalArgumentException("applications cannot be nulls!");
        }
        if (!applicationsAreCurrent(source, target)) {
            constructing = new FlowStructure();
            constructing.source = source;
            constructing.target = target;
            applicationsMappingsContext = new ApplicationsMappingsContext();
        }

        return applicationsMappingsContext;
    }

    private boolean applicationsAreCurrent(BusinessApplication source, BusinessApplication target) {
        return constructing != null
                && Objects.equals(constructing.source, source)
                && Objects.equals(constructing.target, target);
    }

    public class ApplicationsMappingsContext {

        private ApplicationsMappingsContext() {

        }

        public BusinessApplication source() {
            return constructing.source;
        }

        public BusinessApplication target() {
            return constructing.target;
        }

        public List<DataFlowMapping> getDataFlowsListView() {
            return Collections.unmodifiableList(constructing.dataFlowMappings);
        }

        public void addDataFlow(Structure sourceStructure, Service flowService, Structure targetStructure) {

            if (sourceStructure == null || flowService == null || targetStructure == null) {
                throw new IllegalArgumentException("null flow members not allowed!");
            }
            //correct members ownership
            if (!Objects.equals(sourceStructure.owner(), constructing.source)) {
                throw new IllegalArgumentException("source structure not owned by flow source application! " +
                        "Expected: " + constructing.source.asView() + ", but got " + sourceStructure.owner().asView());
            }
            if (!Objects.equals(flowService.owner(), constructing.target)) {
                throw new IllegalArgumentException("flow service not owned by flow target application! " +
                        "Expected: " + constructing.target.asView() + ", but got " + flowService.owner().asView());
            }
            if (!Objects.equals(targetStructure.owner(), constructing.target)) {
                throw new IllegalArgumentException("target structure not owned by flow target application! " +
                        "Expected: " + constructing.target.asView() + ", but got " + targetStructure.owner().asView());
            }

            var mapping = new DataFlowMapping(sourceStructure, flowService, targetStructure);
            if (constructing.dataFlowMappings.contains(mapping)) {
                throw new UnsupportedOperationException("adding same flow twice not supported!");
            }
            constructing.dataFlowMappings.add(mapping);
        }

        public void removeDataFlow(Structure sourceStructure, Service flowService, Structure targetStructure) {
            constructing.dataFlowMappings.removeIf(flow -> flow.equals(new DataFlowMapping(sourceStructure, flowService, targetStructure)));
        }
    }
}
