module com.effibot.robind_manipolator {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires peasycam;
    requires core;
    requires jogl.all;
    requires java.desktop;
    requires javafx.swing;
    requires commons.math3;
    requires commons.lang;
    opens com.effibot.robind_manipolator to javafx.fxml;
    exports com.effibot.robind_manipolator;
    exports com.effibot.robind_manipolator.Processing;
    opens com.effibot.robind_manipolator.Processing to javafx.fxml;
    exports com.effibot.robind_manipolator.TCP;
    opens com.effibot.robind_manipolator.TCP to javafx.fxml;

}