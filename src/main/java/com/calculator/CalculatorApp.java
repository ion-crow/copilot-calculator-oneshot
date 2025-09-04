package com.calculator;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main application class for the Java Calculator.
 * This class serves as the entry point for the JavaFX application.
 * 
 * @author Java Calculator Team
 * @version 1.0.0
 */
public class CalculatorApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Create the calculator model and controller
            CalculatorModel model = new CalculatorModel();
            CalculatorController controller = new CalculatorController(model);
            
            // Create and configure the main scene
            Scene scene = new Scene(controller.getView(), 300, 400);
            scene.getStylesheets().add(getClass().getResource("/calculator.css").toExternalForm());
            
            // Configure the primary stage
            primaryStage.setTitle("Java Calculator v1.0.0");
            primaryStage.setScene(scene);
            primaryStage.setMinWidth(300);
            primaryStage.setMinHeight(400);
            primaryStage.setResizable(true);
            
            // Show the application
            primaryStage.show();
            
            // Request focus on the view for keyboard input
            controller.getView().requestFocus();
            
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to start calculator application: " + e.getMessage());
        }
    }

    /**
     * Main method to launch the JavaFX application.
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
