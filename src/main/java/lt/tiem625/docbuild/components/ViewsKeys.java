package lt.tiem625.docbuild.components;

import lt.tiem625.docbuild.components.applicationsflow.ApplicationsFlowViewController;
import lt.tiem625.docbuild.components.selectableitempicker.SelectableItemPickerController;

public enum ViewsKeys {

    SCREEN_APPLICATIONS_FLOW(
            "fxml/flow-applications-mapping.fxml",
            ApplicationsFlowViewController.class
    ),
    DIALOG_SELECT_KNOWN_ENTITY(
            "fxml/selectable-item-pane.fxml",
            SelectableItemPickerController.class
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
