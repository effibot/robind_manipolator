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
    requires commons.io;
    requires com.squareup.gifencoder;
    requires com.dlsc.workbenchfx.core;
    requires org.kordamp.ikonli.materialdesign;
    opens com.effibot.robind_manipolator ;
    exports com.effibot.robind_manipolator;
    exports com.effibot.robind_manipolator.Processing;
    opens com.effibot.robind_manipolator.Processing;
    exports com.effibot.robind_manipolator.tcp;
    opens com.effibot.robind_manipolator.tcp;
//    opens com.effibot.robind_manipolator.modules.setting ;

}