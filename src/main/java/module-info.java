module lt.tiem625 {
    requires javafx.controls;
    requires javafx.fxml;

    opens lt.tiem625 to javafx.fxml;
    exports lt.tiem625;
}
