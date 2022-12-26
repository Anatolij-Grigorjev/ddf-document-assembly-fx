package lt.tiem625.docbuild.binding;

import lt.tiem625.docbuild.binding.FlowStructure.DataFlowMapping.StructureWithAttributes;
import lt.tiem625.docbuild.data.Service;
import lt.tiem625.docbuild.data.StructureAttribute;

public class SpecificStructuresFlow {

    private final StructureWithAttributes sourceStructure;
    private final StructureWithAttributes targetStructure;
    private final Service service;

    SpecificStructuresFlow(StructureWithAttributes source, Service flowService, StructureWithAttributes target) {
        this.sourceStructure = source;
        this.service = flowService;
        this.targetStructure = target;
    }

    public void specifySourceAttribute(StructureAttribute attribute) {
        throw new UnsupportedOperationException("TODO");
    }

    public void specifyTargetAttribute(StructureAttribute attribute) {
        throw new UnsupportedOperationException("TODO");
    }
}
