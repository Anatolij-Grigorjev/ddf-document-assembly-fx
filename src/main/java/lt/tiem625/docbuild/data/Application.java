package lt.tiem625.docbuild.data;

public record Application(String name, SpiCombination spi) implements BusinessApplication {

    @Override
    public String asView() {
        return String.format("%s-%s", spi.asView(), name);
    }
}
