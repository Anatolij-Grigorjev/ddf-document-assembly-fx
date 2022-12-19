package lt.tiem625.docbuild.model;

import lt.tiem625.docbuild.ViewableEntity;

public record StructureType(String name) implements ViewableEntity {

    @Override
    public String asView() {
        return name;
    }
}
