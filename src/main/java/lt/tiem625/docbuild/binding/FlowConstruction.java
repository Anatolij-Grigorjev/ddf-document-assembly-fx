package lt.tiem625.docbuild.binding;

import lt.tiem625.docbuild.data.BusinessApplication;
import lt.tiem625.docbuild.data.Service;
import lt.tiem625.docbuild.data.Structure;
import lt.tiem625.docbuild.data.StructureAttribute;

import java.util.ArrayList;
import java.util.List;

/**
 * Binder facility to put together raw data of the kind described in the
 * data.* package into the required shape of a flow
 */
public class FlowConstruction {

    public FlowConstruction() {
        serviceFeeds = new ArrayList<>();
    }



    private BusinessApplication source;
    private List<ServiceFeed> serviceFeeds;
    private BusinessApplication target;

    private static class ServiceFeed {

        private Service flowService;
        List<StructuresMap> structuresMaps;

        public ServiceFeed() {
            structuresMaps = new ArrayList<>();
        }

        private static class StructuresMap {

            private StructureWithAttributes source;
            private StructureWithAttributes target;

            private static class StructureWithAttributes {

                Structure structure;
                List<StructureAttribute> attributes;

                public StructureWithAttributes() {
                    attributes = new ArrayList<>();
                }
            }
        }
    }
}
