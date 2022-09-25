module com.effibot.robind_manipolator {
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires peasycam;
    requires core;
    requires jogl.all;
    requires java.desktop;
    requires commons.math3;
    requires commons.lang;
    requires com.dlsc.workbenchfx.core;
    requires org.kordamp.ikonli.materialdesign;
    requires org.slf4j;
    requires com.jfoenix;
    requires org.kordamp.ikonli.javafx;
    requires javafx.swing;

    opens com.effibot.robind_manipolator ;
    exports com.effibot.robind_manipolator;
    exports com.effibot.robind_manipolator.processing;
    opens com.effibot.robind_manipolator.processing;
    exports com.effibot.robind_manipolator.tcp;
    exports com.effibot.robind_manipolator.bean;
    opens com.effibot.robind_manipolator.tcp;

}