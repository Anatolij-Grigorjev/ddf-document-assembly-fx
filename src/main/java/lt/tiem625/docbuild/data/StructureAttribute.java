package lt.tiem625.docbuild.data;

import lt.tiem625.docbuild.ViewableEntity;

public record StructureAttribute(Structure owner, String name, BusinessTerm businessTerm) implements ViewableEntity {

    public StructureAttribute(Structure owner, String name) {
        this(owner, name, null);
    }

    @Override
    public String asView() {
        return String.format("%s.%s", owner.name(), name) + (businessTerm != null ? String.format(" - %s", businessTerm) : "");
    }
}
