package lt.tiem625.docbuild.binding;

import lt.tiem625.docbuild.data.IntegrationType;
import lt.tiem625.docbuild.data.StructureType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class FlowConstructionTest {

    private FlowConstruction construction;
    private final StructureType SQL_TABLE = new StructureType("SQL Table");
    private final IntegrationType API = new IntegrationType("API");

    @BeforeEach
    public void setupConstruction() {
        construction = new FlowConstruction();
    }
  
}