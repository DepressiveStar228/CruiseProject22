module com.cruiseproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    //requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires spring.context;
    requires spring.beans;
    requires spring.core;
    opens com.cruiseproject to javafx.fxml;
    exports com.cruiseproject;
//    exports com.cruiseproject.Controllers;
    opens com.cruiseproject.Controllers to javafx.fxml;
//    exports com.cruiseproject.ArrayList;
    exports com.cruiseproject.Items;
    opens com.cruiseproject.Items to javafx.fxml;
}