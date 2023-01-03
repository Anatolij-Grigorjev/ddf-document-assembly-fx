package lt.tiem625.docbuild.components;

import lt.tiem625.docbuild.PrimaryController;
import lt.tiem625.docbuild.components.selectableitemdialog.SelectableItemDialogController;

public enum ViewsKeys {

    EXAMPLE("fxml/primary.fxml", PrimaryController.class),
    DIALOG_SELECT_STRUCTURE_TYPE("fxml/selectable-item-pane.fxml", SelectableItemDialogController.class);

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
