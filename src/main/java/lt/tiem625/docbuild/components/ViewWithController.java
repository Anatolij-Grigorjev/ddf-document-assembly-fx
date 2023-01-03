package lt.tiem625.docbuild.components;

import javafx.scene.Parent;

public record ViewWithController<T>(Parent view, T controller) {
}
