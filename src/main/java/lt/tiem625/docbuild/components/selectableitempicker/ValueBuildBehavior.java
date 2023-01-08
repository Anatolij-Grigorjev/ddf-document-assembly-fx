package lt.tiem625.docbuild.components.selectableitempicker;

@FunctionalInterface
public interface ValueBuildBehavior<T> {

    T buildFromText(String text);

    default boolean canBuildFromText() {
        return true;
    }

    static <T> ValueBuildBehavior<T> buildingNotSupported() {
        return new ValueBuildBehavior<>() {
            @Override
            public T buildFromText(String text) {
                return null;
            }

            @Override
            public boolean canBuildFromText() {
                return false;
            }
        };
    }
}
