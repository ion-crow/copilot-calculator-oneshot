/**
 * Module definition for the Java Calculator application.
 * 
 * This module provides a desktop calculator application built with JavaFX.
 * It requires JavaFX controls and FXML modules for the user interface.
 * 
 * @author Java Calculator Team
 * @version 1.0.0
 */
module com.calculator {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    
    exports com.calculator;
}
