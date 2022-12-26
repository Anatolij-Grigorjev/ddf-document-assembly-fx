package lt.tiem625.docbuild.binding;

import lt.tiem625.docbuild.data.BusinessApplication;
import lt.tiem625.docbuild.data.Service;
import lt.tiem625.docbuild.data.Structure;
import lt.tiem625.docbuild.data.StructureAttribute;

import java.util.ArrayList;
import java.util.List;

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

        static class StructureWithAttributes {

            Structure structure;
            List<StructureAttribute> attributes;

            public StructureWithAttributes(Structure structure) {
                this.structure = structure;
                attributes = new ArrayList<>();
            }
        }
    }
}
