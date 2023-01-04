package lt.tiem625.docbuild.components;

import lt.tiem625.docbuild.PrimaryController;
import lt.tiem625.docbuild.components.selectableitempicker.SelectableItemPickerController;

public enum ViewsKeys {

    EXAMPLE("fxml/primary.fxml", "primary1", PrimaryController.class),
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
