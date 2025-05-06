module csd2522.wrm.mavenproject1 {
    requires javafx.controls;
    requires javafx.fxml;

    opens csd2522.wrm.mavenproject1 to javafx.fxml;
    exports csd2522.wrm.mavenproject1;
}
