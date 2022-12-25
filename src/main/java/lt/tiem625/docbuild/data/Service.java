package lt.tiem625.docbuild.data;

import lt.tiem625.docbuild.ViewableEntity;

public record Service(BusinessApplication owner, String name, IntegrationType integrationType) implements ViewableEntity {

    @Override
    public String asView() {
        return String.format("%s (%s)", name, integrationType.name());
    }
}
