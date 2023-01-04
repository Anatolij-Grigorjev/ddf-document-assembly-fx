package lt.tiem625.docbuild.components.dialogutils;

import lt.tiem625.docbuild.components.ValueDialogViewController;
import lt.tiem625.docbuild.components.ViewWithController;
import lt.tiem625.docbuild.components.ViewsKeys;
import lt.tiem625.docbuild.components.ViewsRepository;

public class DialogRunner {

    private DialogRunner() {
        throw new UnsupportedOperationException("static helper");
    }

    /**
     * Run dialog and wait for result value.
     * The viewKey at <code>viewKey</code> should already be loaded before this call
     * @param viewKey the view loader key referencing a loaded view in {@link ViewsRepository}
     * @return the acquired value from running the dialog to completion
     * @param <T> type of return value after dialog is run
     */
    public static <T> T runValueDialog(ViewsKeys viewKey) {
        ViewWithController<?> viewAndController = ViewsRepository.getAt(viewKey);
        var dialogWindow = DialogBuilder.newDialog()
                .withTitle(viewKey.getViewName())
                .withScene(viewAndController.view())
                .build();
        ValueDialogViewController<T> controller = (ValueDialogViewController<T>) viewAndController.controller();
        var valueChanges = controller.beginValueDialogContext(dialogWindow);
        dialogWindow.showAndWait();
        return valueChanges.getValue();
    }
}
