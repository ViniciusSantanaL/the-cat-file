module br.com.viniciussls.thecatfiles {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires jsch;
    requires jfoenix;

    opens br.com.viniciussls.thecatfiles to javafx.fxml;
    exports br.com.viniciussls.thecatfiles;
    exports br.com.viniciussls.thecatfiles.controllers;
    opens br.com.viniciussls.thecatfiles.controllers to javafx.fxml;
}