module com.example.src {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens com.example.src to javafx.fxml;
    exports com.example.src;
}