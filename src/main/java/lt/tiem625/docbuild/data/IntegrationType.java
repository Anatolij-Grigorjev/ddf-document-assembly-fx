package lt.tiem625.docbuild.data;

import lt.tiem625.docbuild.ViewableEntity;

public record IntegrationType(String name, boolean isDeprecated) implements ViewableEntity {

    @Override
    public String asView() {
        return name + (isDeprecated ? " [DEPRECATED]" : "");
    }
}
