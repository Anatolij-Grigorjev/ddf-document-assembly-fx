package lt.tiem625.docbuild.components;

import lt.tiem625.docbuild.components.applicationsflow.ApplicationsFlowViewController;
import lt.tiem625.docbuild.components.selectableitempicker.SelectableItemPickerController;

public enum ViewsKeys {

    SCREEN_APPLICATIONS_FLOW(
            "fxml/flow-applications-mapping.fxml",
            "Select source/target applications",
            ApplicationsFlowViewController.class
    ),
    DIALOG_SELECT_STRUCTURE_TYPE(
            "fxml/selectable-item-pane.fxml",
            "Select Structure Type",
            SelectableItemPickerController.class
    );

    private final String fxmlPath;
    private final String viewName;
    private final Class<?> controllerClazz;

    ViewsKeys(String fxmlPath, String viewName, Class<?> controllerClazz) {

        this.fxmlPath = fxmlPath;
        this.viewName = viewName;
        this.controllerClazz = controllerClazz;
    }


    public String getFxmlPath() {
        return fxmlPath;
    }

    public String getViewName() {
        return viewName;
    }

    public Class<?> getControllerClazz() {
        return controllerClazz;
    }
}
