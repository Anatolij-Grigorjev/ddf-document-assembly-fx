package lt.tiem625.docbuild.data;

public record DataAsset(String name, Organization owner) implements BusinessApplication {

    @Override
    public String asView() {
        return String.format("%s - %s", owner.name(), name);
    }
}
