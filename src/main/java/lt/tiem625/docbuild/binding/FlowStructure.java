package lt.tiem625.docbuild.binding;

import lt.tiem625.docbuild.data.BusinessApplication;
import lt.tiem625.docbuild.data.Service;
import lt.tiem625.docbuild.data.Structure;
import lt.tiem625.docbuild.data.StructureAttribute;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FlowStructure {

    BusinessApplication source;
    List<DataFlowMapping> dataFlowMappings;
    BusinessApplication target;

    FlowStructure() {
        dataFlowMappings = new ArrayList<>();
    }

    static class DataFlowMapping {

        Service flowService;
        StructureWithAttributes source;
        StructureWithAttributes target;

        public DataFlowMapping(Structure source, Service flowService, Structure target) {
            this.source = new StructureWithAttributes(source);
            this.flowService = flowService;
            this.target = new StructureWithAttributes(target);
        }

        public void specifySourceAttribute(StructureAttribute attribute) {
            if (attribute == null || !Objects.equals(source.structure, attribute.owner())) {
                throw new IllegalArgumentException("attribute not part of structure!");
            }
            source.attributes.add(attribute);
        }

        public void specifyTargetAttribute(StructureAttribute attribute) {
            if (attribute == null || !Objects.equals(target.structure, attribute.owner())) {
                throw new IllegalArgumentException("attribute not part of structure!");
            }
            target.attributes.add(attribute);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DataFlowMapping that = (DataFlowMapping) o;
            return Objects.equals(source, that.source) && Objects.equals(target, that.target) && Objects.equals(flowService, that.flowService);
        }

        @Override
        public int hashCode() {
            return Objects.hash(source, flowService, target);
        }

        static class StructureWithAttributes {

            Structure structure;
            List<StructureAttribute> attributes;

            public StructureWithAttributes(Structure structure) {
                this.structure = structure;
                attributes = new ArrayList<>();
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                StructureWithAttributes that = (StructureWithAttributes) o;
                return Objects.equals(structure, that.structure);
            }

            @Override
            public int hashCode() {
                return Objects.hash(structure);
            }
        }
    }
}
