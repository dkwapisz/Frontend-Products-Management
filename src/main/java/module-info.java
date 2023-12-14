module org.pk.lab3 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires lombok;
    requires spring.web;
    requires spring.core;
    requires com.github.benmanes.caffeine;

    opens org.pk.lab3 to javafx.fxml;
    exports org.pk.lab3;
    exports org.pk.lab3.viewModel;
    exports org.pk.lab3.model;
}