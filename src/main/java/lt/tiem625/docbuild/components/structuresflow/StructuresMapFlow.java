package lt.tiem625.docbuild.components.structuresflow;

import lt.tiem625.docbuild.data.Service;
import lt.tiem625.docbuild.data.Structure;

public record StructuresMapFlow(StructureDetails sourceStructure, Service flowService, StructureDetails targetStructure) {

    StructuresMapFlow(Structure source, Service service, Structure target) {
        this(new StructureDetails(source), service, new StructureDetails(target));
    }
}
