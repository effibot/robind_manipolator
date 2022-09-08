module com.effibot.robind_manipolator {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires peasycam;
    requires core;
    requires jogl.all;
    requires java.desktop;
    requires javafx.swing;
    requires commons.math3;
    requires commons.lang;
    requires commons.io;
    requires com.squareup.gifencoder;
    opens com.effibot.robind_manipolator to javafx.fxml;
    exports com.effibot.robind_manipolator;
    exports com.effibot.robind_manipolator.Processing;
    opens com.effibot.robind_manipolator.Processing to javafx.fxml;
    exports com.effibot.robind_manipolator.tcp;
    opens com.effibot.robind_manipolator.tcp to javafx.fxml;

}