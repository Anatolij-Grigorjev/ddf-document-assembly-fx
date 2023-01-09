package lt.tiem625.docbuild.datasource;

import lt.tiem625.docbuild.data.*;

import java.util.List;

public class MockMetadataDataProvider implements KnownDataProvider {

    @Override
    public List<Organization> getKnownOrganizations() {
        return List.of(
                new Organization("AML"),
                new Organization("Risk"),
                new Organization("Data Platform")
        );
    }

    @Override
    public List<Application> getKnownApplications() {
        return List.of(
                new Application("Calculator", new SpiCombination("RS", "CA")),
                new Application("Storage", new SpiCombination("D4", "D4")),
                new Application("ML Detector", new SpiCombination("AM", "ML"))
        );
    }

    @Override
    public List<DataAsset> getKnownDataAssets() {
        return List.of();
    }

    @Override
    public List<IntegrationType> getKnownIntegrationTypes() {
        return List.of(
                new IntegrationType("Point 2 Point", true),
                new IntegrationType("API"),
                new IntegrationType("MQ"),
                new IntegrationType("ShareDrive", true)
        );
    }

    @Override
    public List<StructureType> getKnownStructureTypes() {
        return List.of(
                new StructureType("SQL Table"),
                new StructureType("SQL View"),
                new StructureType("Text File"),
                new StructureType("Binary File")
        );
    }

    @Override
    public List<Service> getKnownServices() {
        return List.of();
    }

    @Override
    public List<Structure> getKnownStructures() {
        return List.of();
    }

    @Override
    public List<StructureAttribute> getKnownStructuresAttributes() {
        return List.of();
    }
}
