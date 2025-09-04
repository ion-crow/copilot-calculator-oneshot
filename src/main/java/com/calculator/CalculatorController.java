package com.calculator;

import com.calculator.CalculatorModel.Operation;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

/**
 * Controller class for the calculator that handles user interactions and updates the model and view.
 * This class implements the MVC pattern by coordinating between the model and view.
 * 
 * @author Java Calculator Team
 * @version 1.0.0
 */
public class CalculatorController {
    
    private CalculatorModel model;
    private CalculatorView view;
    
    /**
     * Constructor initializes the controller with the given model.
     * Creates the view and sets up event handlers.
     * 
     * @param model the calculator model
     */
    public CalculatorController(CalculatorModel model) {
        this.model = model;
        this.view = new CalculatorView();
        setupEventHandlers();
        updateDisplay();
    }
    
    /**
     * Sets up event handlers for all buttons in the view.
     */
    private void setupEventHandlers() {
        setupNumberButtonHandlers();
        setupOperationButtonHandlers();
        setupFunctionButtonHandlers();
    }
    
    /**
     * Sets up event handlers for number buttons (0-9).
     */
    private void setupNumberButtonHandlers() {
        Button[][] numberButtons = view.getNumberButtons();
        
        for (int row = 0; row < numberButtons.length; row++) {
            for (int col = 0; col < numberButtons[row].length; col++) {
                Button button = numberButtons[row][col];
                if (button != null) {
                    button.setOnAction(this::handleNumberInput);
                }
            }
        }
    }
    
    /**
     * Sets up event handlers for operation buttons (+, -, ×, ÷).
     */
    private void setupOperationButtonHandlers() {
        Button[] operationButtons = view.getOperationButtons();
        
        operationButtons[0].setOnAction(e -> handleOperation(Operation.DIVIDE));
        operationButtons[1].setOnAction(e -> handleOperation(Operation.MULTIPLY));
        operationButtons[2].setOnAction(e -> handleOperation(Operation.SUBTRACT));
        operationButtons[3].setOnAction(e -> handleOperation(Operation.ADD));
    }
    
    /**
     * Sets up event handlers for function buttons (clear, equals, etc.).
     */
    private void setupFunctionButtonHandlers() {
        view.getClearButton().setOnAction(this::handleClear);
        view.getClearEntryButton().setOnAction(this::handleClearEntry);
        view.getBackspaceButton().setOnAction(this::handleBackspace);
        view.getDecimalButton().setOnAction(this::handleDecimal);
        view.getSignButton().setOnAction(this::handleSign);
        view.getEqualsButton().setOnAction(this::handleEquals);
    }
    
    /**
     * Handles number button input.
     * 
     * @param event the action event from the button click
     */
    private void handleNumberInput(ActionEvent event) {
        Button source = (Button) event.getSource();
        String digit = source.getText();
        
        model.inputDigit(digit);
        updateDisplay();
    }
    
    /**
     * Handles operation button input.
     * 
     * @param operation the operation to perform
     */
    private void handleOperation(Operation operation) {
        model.setOperation(operation);
        updateDisplay();
    }
    
    /**
     * Handles the clear (AC) button.
     * 
     * @param event the action event
     */
    private void handleClear(ActionEvent event) {
        model.clear();
        updateDisplay();
    }
    
    /**
     * Handles the clear entry (CE) button.
     * 
     * @param event the action event
     */
    private void handleClearEntry(ActionEvent event) {
        model.clearEntry();
        updateDisplay();
    }
    
    /**
     * Handles the backspace button.
     * 
     * @param event the action event
     */
    private void handleBackspace(ActionEvent event) {
        model.backspace();
        updateDisplay();
    }
    
    /**
     * Handles the decimal point button.
     * 
     * @param event the action event
     */
    private void handleDecimal(ActionEvent event) {
        model.inputDecimal();
        updateDisplay();
    }
    
    /**
     * Handles the sign toggle (±) button.
     * 
     * @param event the action event
     */
    private void handleSign(ActionEvent event) {
        model.toggleSign();
        updateDisplay();
    }
    
    /**
     * Handles the equals button.
     * 
     * @param event the action event
     */
    private void handleEquals(ActionEvent event) {
        model.calculate();
        updateDisplay();
    }
    
    /**
     * Updates the display based on the current model state.
     */
    private void updateDisplay() {
        view.getPrimaryDisplay().setText(model.getCurrentDisplay());
        view.getSecondaryDisplay().setText(model.getExpressionDisplay());
        
        // Apply error styling if needed
        if (model.hasError()) {
            view.getPrimaryDisplay().getStyleClass().removeAll("primary-display");
            view.getPrimaryDisplay().getStyleClass().add("error-display");
        } else {
            view.getPrimaryDisplay().getStyleClass().removeAll("error-display");
            if (!view.getPrimaryDisplay().getStyleClass().contains("primary-display")) {
                view.getPrimaryDisplay().getStyleClass().add("primary-display");
            }
        }
    }
    
    /**
     * Gets the view for use by the main application.
     * 
     * @return the calculator view
     */
    public CalculatorView getView() {
        return view;
    }
    
    /**
     * Gets the model for testing purposes.
     * 
     * @return the calculator model
     */
    public CalculatorModel getModel() {
        return model;
    }
}
