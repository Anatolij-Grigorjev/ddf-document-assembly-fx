package lt.tiem625.docbuild.data;

import lt.tiem625.docbuild.ViewableEntity;

import java.util.Locale;

public record SpiCombination(String spiCode, String subSpiCode) implements ViewableEntity {

    public SpiCombination(String spiCode, String subSpiCode) {
        this.spiCode = spiCode.toUpperCase(Locale.ROOT);
        this.subSpiCode = subSpiCode.toUpperCase(Locale.ROOT);
    }
    @Override
    public String asView() {
        return String.format("%s-%s", spiCode, subSpiCode);
    }
}
