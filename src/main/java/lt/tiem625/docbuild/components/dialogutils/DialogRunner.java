package lt.tiem625.docbuild.components.dialogutils;

import lt.tiem625.docbuild.components.ViewWithController;
import lt.tiem625.docbuild.components.ViewsKeys;
import lt.tiem625.docbuild.components.ViewsRepository;

import java.util.Optional;

public class DialogRunner {

    private DialogRunner() {
        throw new UnsupportedOperationException("static helper");
    }

    /**
     * Run dialog and wait for result value.
     * The dialogView at <code>dialogView</code> should already be loaded before this call
     * @param dialogView the view loader key referencing a loaded view in {@link ViewsRepository}
     * @return maybe the acquired value from running the dialog to completion - might be nothing
     * @param <T> type of return value after dialog is run
     */
    public static <T> Optional<T> runValueDialog(ViewsKeys dialogView, String title) {
        ViewWithController<?> viewAndController = ViewsRepository.getAt(dialogView);
        var dialogWindow = DialogBuilder.newDialog()
                .withTitle(title)
                .withScene(viewAndController.view())
                .build();
        ValueDialogViewController<T> controller = (ValueDialogViewController<T>) viewAndController.controller();
        var valueChanges = controller.beginValueDialogContext(dialogWindow);
        dialogWindow.showAndWait();
        return Optional.ofNullable(valueChanges.getValue());
    }
}
