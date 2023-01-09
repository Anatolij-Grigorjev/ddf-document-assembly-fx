package lt.tiem625.docbuild.datasource;

import lt.tiem625.docbuild.data.*;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class KnownDataRepository implements KnownDataProvider {

    private final List<Organization> knownOrganizations;
    private final List<Application> knownApplications;
    private final List<DataAsset> knownDataAssets;
    private final List<IntegrationType> knownIntegrationTypes;
    private final List<StructureType> knownStructureTypes;
    private final List<Service> knownServices;
    private final List<Structure> knownStructures;
    private final List<StructureAttribute> knownStructuresAttributes;

    public KnownDataRepository(KnownDataProvider metadataProvider) {
        
        this.knownOrganizations = new CopyOnWriteArrayList<>(metadataProvider.getKnownOrganizations());
        this.knownApplications = new CopyOnWriteArrayList<>(metadataProvider.getKnownApplications());
        this.knownDataAssets = new CopyOnWriteArrayList<>(metadataProvider.getKnownDataAssets());
        this.knownIntegrationTypes = new CopyOnWriteArrayList<>(metadataProvider.getKnownIntegrationTypes());
        this.knownStructureTypes = new CopyOnWriteArrayList<>(metadataProvider.getKnownStructureTypes());
        this.knownServices = new CopyOnWriteArrayList<>(metadataProvider.getKnownServices());
        this.knownStructures = new CopyOnWriteArrayList<>(metadataProvider.getKnownStructures());
        this.knownStructuresAttributes = new CopyOnWriteArrayList<>(metadataProvider.getKnownStructuresAttributes());
    }

    public void registerDataAsset(DataAsset dataAsset) {
        knownDataAssets.add(dataAsset);
    }

    public void registerService(Service service) {
        knownServices.add(service);
    }

    public void registerStructure(Structure structure) {
        knownStructures.add(structure);
    }

    public void registerStructureAttribute(StructureAttribute structureAttribute) {
        knownStructuresAttributes.add(structureAttribute);
    }

    @Override
    public List<Organization> getKnownOrganizations() {
        return knownOrganizations;
    }

    @Override
    public List<Application> getKnownApplications() {
        return knownApplications;
    }

    @Override
    public List<DataAsset> getKnownDataAssets() {
        return knownDataAssets;
    }

    @Override
    public List<IntegrationType> getKnownIntegrationTypes() {
        return knownIntegrationTypes;
    }

    @Override
    public List<StructureType> getKnownStructureTypes() {
        return knownStructureTypes;
    }

    @Override
    public List<Service> getKnownServices() {
        return knownServices;
    }

    @Override
    public List<Structure> getKnownStructures() {
        return knownStructures;
    }

    @Override
    public List<StructureAttribute> getKnownStructuresAttributes() {
        return knownStructuresAttributes;
    }
}
