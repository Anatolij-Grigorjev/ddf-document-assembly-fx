package lt.tiem625.docbuild.components.structuresflow;

import lt.tiem625.docbuild.data.Structure;
import lt.tiem625.docbuild.data.StructureAttribute;

import java.util.ArrayList;
import java.util.List;

public record StructureDetails(Structure structure, List<StructureAttribute> attributes) {

    StructureDetails(Structure structure) {
        this(structure, new ArrayList<>());
    }
}
