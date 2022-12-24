package lt.tiem625.docbuild.data;

import lt.tiem625.docbuild.ViewableEntity;

public record Structure(String name, StructureType type) implements ViewableEntity {

    @Override
    public String asView() {
        return String.format("%s (%s)", name, type.asView());
    }
}
