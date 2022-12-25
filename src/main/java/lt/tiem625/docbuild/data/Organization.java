package lt.tiem625.docbuild.data;

import lt.tiem625.docbuild.ViewableEntity;

public record Organization(String name) implements ViewableEntity {

    @Override
    public String asView() {
        return name;
    }
}
