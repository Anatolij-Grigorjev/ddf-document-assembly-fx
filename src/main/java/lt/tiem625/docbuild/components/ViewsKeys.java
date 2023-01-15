package lt.tiem625.docbuild.components;

import lt.tiem625.docbuild.components.applicationsflow.ApplicationsFlowViewController;
import lt.tiem625.docbuild.components.entitycreate.TextAndItemEntityCreateViewController;
import lt.tiem625.docbuild.components.selectableitempicker.SelectableItemPickerController;
import lt.tiem625.docbuild.components.structuresflow.StructureMapsFlowViewController;

public enum ViewsKeys {

    SCREEN_APPLICATIONS_FLOW(
            "fxml/flow-applications-mapping.fxml",
            ApplicationsFlowViewController.class
    ),
    DIALOG_SELECT_KNOWN_ENTITY(
            "fxml/selectable-item-pane.fxml",
            SelectableItemPickerController.class
    ),
    DIALOG_CREATE_NEW_ENTITY(
            "fxml/text-and-dependant-entity-builder.fxml",
            TextAndItemEntityCreateViewController.class
    ),
    SCREEN_STRUCTURES_MAPPINGS(
            "fxml/flow-structure-maps.fxml",
            StructureMapsFlowViewController.class
    );

    private final String fxmlPath;
    private final Class<?> controllerClazz;

    ViewsKeys(String fxmlPath, Class<?> controllerClazz) {

        this.fxmlPath = fxmlPath;
        this.controllerClazz = controllerClazz;
    }


    public String getFxmlPath() {
        return fxmlPath;
    }

    public Class<?> getControllerClazz() {
        return controllerClazz;
    }
}
