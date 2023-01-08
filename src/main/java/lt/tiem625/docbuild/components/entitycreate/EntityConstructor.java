package lt.tiem625.docbuild.components.entitycreate;

import lt.tiem625.docbuild.ViewableEntity;

@FunctionalInterface
public interface EntityConstructor<E extends ViewableEntity, I extends ViewableEntity> {

    E constructFrom(String text, I dependant);
}
