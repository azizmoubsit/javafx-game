module myGame {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
	requires javafx.media;

    opens application to javafx.fxml;
    exports application;
}