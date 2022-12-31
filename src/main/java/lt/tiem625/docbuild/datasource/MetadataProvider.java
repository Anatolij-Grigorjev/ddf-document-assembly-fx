package lt.tiem625.docbuild.datasource;

import lt.tiem625.docbuild.data.*;

import java.util.List;

public interface MetadataProvider {

    default List<Organization> getKnownOrganizations() {
        return List.of();
    }

    default List<Application> getKnownApplications() {
        return List.of();
    }

    default List<DataAsset> getKnownDataAssets() {
        return List.of();
    }

    default List<IntegrationType> getKnownIntegrationTypes() {
        return List.of();
    }

    default List<StructureType> getKnownStructureTypes() {
        return List.of();
    }

    default List<Service> getKnownServices() {
        return List.of();
    }

    default List<Structure> getKnownStructures() {
        return List.of();
    }

    default List<StructureAttribute> getKnownStructuresAttributes() {
        return List.of();
    }

}
