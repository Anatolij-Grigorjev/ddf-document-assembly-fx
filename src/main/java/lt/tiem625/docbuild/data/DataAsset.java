package lt.tiem625.docbuild.data;

public record DataAsset(String name, String ownerName) implements BusinessApplication {

    @Override
    public String asView() {
        return String.format("%s - %s", ownerName, name);
    }
}
