package lt.tiem625.docbuild.components.dialogutils;

import javafx.scene.control.ButtonType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public enum AlertAnswer {

    IGNORED(),
    PROCEED(ButtonType.APPLY, ButtonType.OK),
    CANCEL(ButtonType.CANCEL);

    private final Set<ButtonType> compatibleAlertButtonTypes;

    AlertAnswer(ButtonType... alertButtonTypes) {
        this.compatibleAlertButtonTypes = new HashSet<>(Arrays.asList(alertButtonTypes));
    }

    static Optional<AlertAnswer> forButton(ButtonType buttonType) {
        return Arrays.stream(values())
                .filter(answer -> answer.compatibleAlertButtonTypes.contains(buttonType))
                .findAny();
    }
}
