package lt.tiem625.docbuild.binding;

import lt.tiem625.docbuild.data.BusinessApplication;
import lt.tiem625.docbuild.data.Service;
import lt.tiem625.docbuild.data.Structure;

import java.util.ArrayList;
import java.util.List;

/**
 * Binder facility to put together raw data of the kind described in the
 * data.* package into the required shape of a flow
 */
public class FlowConstruction {

    FlowStructure constructing;

    public FlowConstruction() {
        constructing = new FlowStructure();
    }

    public ApplicationsMappingsContext applicationsMappingsConstruction(BusinessApplication source, BusinessApplication target) {
        return null;
    }

    public static class ApplicationsMappingsContext {

        BusinessApplication source;
        BusinessApplication target;
        List<SpecificStructuresFlow> dataFlowsList;
        private ApplicationsMappingsContext(BusinessApplication source, BusinessApplication target) {
            this.source = source;
            this.target = target;
            this.dataFlowsList = new ArrayList<>();
        }

        public List<SpecificStructuresFlow> getDataFlowsListView() {
            throw new UnsupportedOperationException("TODO");
        }

        public void addDataFlow(Structure sourceStructure, Service flowService, Structure targetStructure) {
            throw new UnsupportedOperationException("TODO");
        }

        public void removeDataFlow(Structure sourceStructure, Service flowService, Structure targetStructure) {
            throw new UnsupportedOperationException("TODO");
        }
    }
}
