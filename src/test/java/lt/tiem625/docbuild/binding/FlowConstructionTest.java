package lt.tiem625.docbuild.binding;

import lt.tiem625.docbuild.binding.FlowStructure.DataFlowMapping;
import lt.tiem625.docbuild.data.*;
import org.junit.jupiter.api.*;

import java.util.List;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class FlowConstructionTest {

    private FlowConstruction construction;
    private final StructureType SQL_TABLE = new StructureType("SQL Table");
    private final IntegrationType API = new IntegrationType("API");
    private final Organization EXAMPLE_OWNER = new Organization("Test org");
    private final DataAsset EXAMPLE_ASSET = new DataAsset("Test asset", EXAMPLE_OWNER);
    private final SpiCombination TEST_SPI = new SpiCombination("TS", "ST");
    private final Application EXAMPLE_APPLICATION = new Application("Test application", TEST_SPI);

    @BeforeEach
    public void setupConstruction() {
        construction = new FlowConstruction();
    }

    @Test
    public void cannot_create_applications_mappings_when_has_nulls() {

        Assertions.assertThrows(IllegalArgumentException.class, () -> construction.applicationsMappingsConstruction(null, null));
        Assertions.assertThrows(IllegalArgumentException.class, () -> construction.applicationsMappingsConstruction(EXAMPLE_ASSET, null));
        Assertions.assertThrows(IllegalArgumentException.class, () -> construction.applicationsMappingsConstruction(null, EXAMPLE_APPLICATION));
    }

    @Test
    public void cannot_submit_flow_mapping_with_nulls() {
        var applicationsMappingsConstruction = construction.applicationsMappingsConstruction(EXAMPLE_APPLICATION, EXAMPLE_APPLICATION);
        var service = new Service(EXAMPLE_APPLICATION, "service", API);
        var sourceStructure = new Structure(EXAMPLE_APPLICATION, "source-structure", SQL_TABLE);
        var targetStructure = new Structure(EXAMPLE_APPLICATION, "target-structure", SQL_TABLE);
        Assertions.assertThrows(IllegalArgumentException.class, () -> applicationsMappingsConstruction.addDataFlow(null, service, targetStructure));
        Assertions.assertThrows(IllegalArgumentException.class, () -> applicationsMappingsConstruction.addDataFlow(sourceStructure, null, targetStructure));
        Assertions.assertThrows(IllegalArgumentException.class, () -> applicationsMappingsConstruction.addDataFlow(sourceStructure, service, null));
    }

    @Test
    public void cannot_submit_flow_mappings_invalid_ownership() {
        var applicationsMappingsConstruction = construction.applicationsMappingsConstruction(EXAMPLE_APPLICATION, EXAMPLE_APPLICATION);
        var serviceApplication = new Service(EXAMPLE_APPLICATION, "service-application", API);
        var serviceAsset = new Service(EXAMPLE_ASSET, "service-asset", API);
        var sourceStructureApplication = new Structure(EXAMPLE_APPLICATION, "source-structure-application", SQL_TABLE);
        var sourceStructureAsset = new Structure(EXAMPLE_ASSET, "source-structure-asset", SQL_TABLE);
        var targetStructureApplication = new Structure(EXAMPLE_APPLICATION, "target-structure-application", SQL_TABLE);
        var targetStructureAsset = new Structure(EXAMPLE_ASSET, "target-structure-asset", SQL_TABLE);

        Assertions.assertThrows(IllegalArgumentException.class, () -> applicationsMappingsConstruction.addDataFlow(sourceStructureAsset, serviceApplication, targetStructureApplication));
        Assertions.assertThrows(IllegalArgumentException.class, () -> applicationsMappingsConstruction.addDataFlow(sourceStructureApplication, serviceAsset, targetStructureApplication));
        Assertions.assertThrows(IllegalArgumentException.class, () -> applicationsMappingsConstruction.addDataFlow(sourceStructureApplication, serviceApplication, targetStructureAsset));
    }

    @Test
    public void creating_applications_construction_sets_context() {
        var applicationsMappingsConstruction = construction.applicationsMappingsConstruction(EXAMPLE_APPLICATION, EXAMPLE_ASSET);

        Assertions.assertEquals(EXAMPLE_APPLICATION, construction.constructing.source);
        Assertions.assertEquals(EXAMPLE_ASSET, construction.constructing.target);
    }

    @Test
    public void create_applications_context_invoke_with_new_applications_gets_new_context() {

        var applicationsMappingsConstruction = construction.applicationsMappingsConstruction(EXAMPLE_APPLICATION, EXAMPLE_ASSET);
        var service = new Service(EXAMPLE_ASSET, "service", API);
        var sourceStructure = new Structure(EXAMPLE_APPLICATION, "source-structure", SQL_TABLE);
        var targetStructure = new Structure(EXAMPLE_ASSET, "target-structure", SQL_TABLE);
        applicationsMappingsConstruction.addDataFlow(sourceStructure, service, targetStructure);

        Assertions.assertEquals(EXAMPLE_APPLICATION, construction.constructing.source);
        Assertions.assertEquals(EXAMPLE_ASSET, construction.constructing.target);
        Assertions.assertEquals(1, construction.constructing.dataFlowMappings.size());


        construction.applicationsMappingsConstruction(EXAMPLE_ASSET, EXAMPLE_APPLICATION);
        Assertions.assertEquals(EXAMPLE_ASSET, construction.constructing.source);
        Assertions.assertEquals(EXAMPLE_APPLICATION, construction.constructing.target);
        Assertions.assertEquals(0, construction.constructing.dataFlowMappings.size());
    }

    @Test
    public void can_add_flow_mapping() {
        var applicationsMappingsConstruction = construction.applicationsMappingsConstruction(EXAMPLE_APPLICATION, EXAMPLE_APPLICATION);
        var service = new Service(EXAMPLE_APPLICATION, "service", API);
        var sourceStructure = new Structure(EXAMPLE_APPLICATION, "source-structure", SQL_TABLE);
        var targetStructure = new Structure(EXAMPLE_APPLICATION, "target-structure", SQL_TABLE);

        applicationsMappingsConstruction.addDataFlow(sourceStructure, service, targetStructure);

        Assertions.assertEquals(1, construction.constructing.dataFlowMappings.size());
    }

    @Test
    public void remove_data_flow_missing_noop() {
        var applicationsMappingsConstruction = construction.applicationsMappingsConstruction(EXAMPLE_APPLICATION, EXAMPLE_APPLICATION);
        var service = new Service(EXAMPLE_APPLICATION, "service", API);
        var sourceStructure = new Structure(EXAMPLE_APPLICATION, "source-structure", SQL_TABLE);
        var targetStructure = new Structure(EXAMPLE_APPLICATION, "target-structure", SQL_TABLE);

        Assertions.assertEquals(List.of(), applicationsMappingsConstruction.getDataFlowsListView());
        Assertions.assertDoesNotThrow(() -> applicationsMappingsConstruction.removeDataFlow(sourceStructure, service, targetStructure));
    }

    @Test
    public void remove_data_flow_present_removed() {
        var applicationsMappingsConstruction = construction.applicationsMappingsConstruction(EXAMPLE_APPLICATION, EXAMPLE_APPLICATION);
        var service = new Service(EXAMPLE_APPLICATION, "service", API);
        var sourceStructure = new Structure(EXAMPLE_APPLICATION, "source-structure", SQL_TABLE);
        var targetStructure = new Structure(EXAMPLE_APPLICATION, "target-structure", SQL_TABLE);

        applicationsMappingsConstruction.addDataFlow(sourceStructure, service, targetStructure);
        Assertions.assertEquals(1, applicationsMappingsConstruction.getDataFlowsListView().size());

        applicationsMappingsConstruction.removeDataFlow(sourceStructure, service, targetStructure);
        Assertions.assertEquals(List.of(), applicationsMappingsConstruction.getDataFlowsListView());
    }

    @Test
    public void cannot_add_same_flow_twice() {
        var applicationsMappingsConstruction = construction.applicationsMappingsConstruction(EXAMPLE_APPLICATION, EXAMPLE_APPLICATION);
        var service = new Service(EXAMPLE_APPLICATION, "service", API);
        var sourceStructure = new Structure(EXAMPLE_APPLICATION, "source-structure", SQL_TABLE);
        var targetStructure = new Structure(EXAMPLE_APPLICATION, "target-structure", SQL_TABLE);

        applicationsMappingsConstruction.addDataFlow(sourceStructure, service, targetStructure);
        Assertions.assertEquals(1, applicationsMappingsConstruction.getDataFlowsListView().size());
        Assertions.assertThrows(UnsupportedOperationException.class, () -> applicationsMappingsConstruction.addDataFlow(sourceStructure, service, targetStructure));
    }

    @Test
    public void can_add_source_structure_attribute() {
        var applicationsMappingsConstruction = construction.applicationsMappingsConstruction(EXAMPLE_APPLICATION, EXAMPLE_APPLICATION);
        var service = new Service(EXAMPLE_APPLICATION, "service", API);
        var sourceStructure = new Structure(EXAMPLE_APPLICATION, "source-structure", SQL_TABLE);
        var targetStructure = new Structure(EXAMPLE_APPLICATION, "target-structure", SQL_TABLE);

        applicationsMappingsConstruction.addDataFlow(sourceStructure, service, targetStructure);

        List<DataFlowMapping> flows = applicationsMappingsConstruction.getDataFlowsListView();

        DataFlowMapping structuresFlow = flows.get(0);
        structuresFlow.specifySourceAttribute(new StructureAttribute(sourceStructure, "source-attr"));

        Assertions.assertEquals(1, construction.constructing.dataFlowMappings.get(0).source.attributes.size());
    }

    @Test
    public void cant_add_attribute_of_another_structure() {
        var applicationsMappingsConstruction = construction.applicationsMappingsConstruction(EXAMPLE_APPLICATION, EXAMPLE_APPLICATION);
        var service = new Service(EXAMPLE_APPLICATION, "service", API);
        var sourceStructure = new Structure(EXAMPLE_APPLICATION, "source-structure", SQL_TABLE);
        var targetStructure = new Structure(EXAMPLE_APPLICATION, "target-structure", SQL_TABLE);

        applicationsMappingsConstruction.addDataFlow(sourceStructure, service, targetStructure);

        List<DataFlowMapping> flows = applicationsMappingsConstruction.getDataFlowsListView();

        DataFlowMapping structuresFlow = flows.get(0);
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                structuresFlow.specifyTargetAttribute(new StructureAttribute(sourceStructure, "source-attr"))
        );
    }
}