package lt.tiem625.docbuild.components.selectableitemdialog;

@FunctionalInterface
public interface ValueBuilder<T> {

    T buildFromText(String text);

    default boolean canBuildFromText() {
        return true;
    }

    static <T> ValueBuilder<T> notSupported() {
        return new ValueBuilder<>() {
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
