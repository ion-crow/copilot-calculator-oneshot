package com.calculator;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Model class for the calculator that handles all business logic and calculations.
 * This class maintains the state of the calculator and performs arithmetic operations.
 * 
 * @author Java Calculator Team
 * @version 1.0.0
 */
public class CalculatorModel {
    
    private static final int MAX_DISPLAY_LENGTH = 15;
    private static final MathContext MATH_CONTEXT = new MathContext(15, RoundingMode.HALF_UP);
    
    private BigDecimal currentValue;
    private BigDecimal storedValue;
    private Operation currentOperation;
    private String currentInput;
    private String displayExpression;
    private boolean hasError;
    private boolean justCalculated;
    private boolean inputStarted;
    
    /**
     * Enumeration of supported arithmetic operations.
     */
    public enum Operation {
        ADD("+"), SUBTRACT("-"), MULTIPLY("×"), DIVIDE("÷");
        
        private final String symbol;
        
        Operation(String symbol) {
            this.symbol = symbol;
        }
        
        public String getSymbol() {
            return symbol;
        }
    }
    
    /**
     * Constructor initializes the calculator to its default state.
     */
    public CalculatorModel() {
        clear();
    }
    
    /**
     * Clears all calculator state (All Clear operation).
     */
    public void clear() {
        currentValue = BigDecimal.ZERO;
        storedValue = null;
        currentOperation = null;
        currentInput = "0";
        displayExpression = "";
        hasError = false;
        justCalculated = false;
        inputStarted = false;
    }
    
    /**
     * Clears only the current entry (Clear Entry operation).
     */
    public void clearEntry() {
        if (hasError) {
            clear();
            return;
        }
        
        currentInput = "0";
        currentValue = BigDecimal.ZERO;
        inputStarted = false;
    }
    
    /**
     * Removes the last digit from the current input (Backspace operation).
     */
    public void backspace() {
        if (hasError || justCalculated) {
            clear();
            return;
        }
        
        if (currentInput.length() > 1) {
            currentInput = currentInput.substring(0, currentInput.length() - 1);
        } else {
            currentInput = "0";
            inputStarted = false;
        }
        
        try {
            currentValue = new BigDecimal(currentInput);
        } catch (NumberFormatException e) {
            currentValue = BigDecimal.ZERO;
            currentInput = "0";
        }
    }
    
    /**
     * Adds a digit to the current input.
     * 
     * @param digit the digit to add (0-9)
     */
    public void inputDigit(String digit) {
        if (hasError) {
            clear();
        }
        
        if (justCalculated) {
            clear();
        }
        
        if (!inputStarted || currentInput.equals("0")) {
            currentInput = digit.equals("0") && !inputStarted ? "0" : digit;
            inputStarted = true;
        } else {
            if (currentInput.length() < MAX_DISPLAY_LENGTH) {
                currentInput += digit;
            }
        }
        
        try {
            currentValue = new BigDecimal(currentInput);
        } catch (NumberFormatException e) {
            // Should not happen with valid digits, but handle gracefully
            currentInput = "0";
            currentValue = BigDecimal.ZERO;
        }
    }
    
    /**
     * Adds a decimal point to the current input.
     */
    public void inputDecimal() {
        if (hasError) {
            clear();
        }
        
        if (justCalculated) {
            clear();
        }
        
        if (!inputStarted) {
            currentInput = "0.";
            inputStarted = true;
        } else if (!currentInput.contains(".") && currentInput.length() < MAX_DISPLAY_LENGTH - 1) {
            currentInput += ".";
        }
    }
    
    /**
     * Changes the sign of the current number (positive/negative toggle).
     */
    public void toggleSign() {
        if (hasError) {
            return;
        }
        
        if (currentValue.equals(BigDecimal.ZERO)) {
            return;
        }
        
        currentValue = currentValue.negate();
        currentInput = formatNumber(currentValue);
        inputStarted = true;
    }
    
    /**
     * Sets the current operation and prepares for the next operand.
     * 
     * @param operation the operation to set
     */
    public void setOperation(Operation operation) {
        if (hasError) {
            return;
        }
        
        if (currentOperation != null && inputStarted && !justCalculated) {
            // Chain operations: calculate current result first
            calculate();
            if (hasError) {
                return;
            }
        }
        
        storedValue = currentValue;
        currentOperation = operation;
        displayExpression = formatNumber(currentValue) + " " + operation.getSymbol();
        inputStarted = false;
        justCalculated = false;
    }
    
    /**
     * Performs the calculation using the stored operation and operands.
     */
    public void calculate() {
        if (hasError || currentOperation == null || storedValue == null) {
            return;
        }
        
        try {
            BigDecimal result;
            
            switch (currentOperation) {
                case ADD:
                    result = storedValue.add(currentValue, MATH_CONTEXT);
                    break;
                case SUBTRACT:
                    result = storedValue.subtract(currentValue, MATH_CONTEXT);
                    break;
                case MULTIPLY:
                    result = storedValue.multiply(currentValue, MATH_CONTEXT);
                    break;
                case DIVIDE:
                    if (currentValue.equals(BigDecimal.ZERO)) {
                        setError("Error: Division by zero");
                        return;
                    }
                    result = storedValue.divide(currentValue, MATH_CONTEXT);
                    break;
                default:
                    return;
            }
            
            // Update expression to show complete calculation
            displayExpression = formatNumber(storedValue) + " " + currentOperation.getSymbol() + 
                               " " + formatNumber(currentValue) + " =";
            
            currentValue = result;
            currentInput = formatNumber(result);
            justCalculated = true;
            inputStarted = false;
            
            // Clear operation state
            currentOperation = null;
            storedValue = null;
            
        } catch (ArithmeticException e) {
            setError("Error: Calculation overflow");
        } catch (Exception e) {
            setError("Error: Invalid operation");
        }
    }
    
    /**
     * Sets an error state with the given message.
     * 
     * @param errorMessage the error message to display
     */
    private void setError(String errorMessage) {
        hasError = true;
        currentInput = errorMessage;
        displayExpression = "";
        currentOperation = null;
        storedValue = null;
    }
    
    /**
     * Formats a BigDecimal for display, removing unnecessary trailing zeros.
     * 
     * @param number the number to format
     * @return formatted string representation
     */
    private String formatNumber(BigDecimal number) {
        String formatted = number.stripTrailingZeros().toPlainString();
        
        // Limit display length
        if (formatted.length() > MAX_DISPLAY_LENGTH) {
            // Try scientific notation for very large/small numbers
            formatted = number.toString();
            if (formatted.length() > MAX_DISPLAY_LENGTH) {
                formatted = formatted.substring(0, MAX_DISPLAY_LENGTH);
            }
        }
        
        return formatted;
    }
    
    // Getters for the view
    
    /**
     * Gets the current display value.
     * 
     * @return the current display value
     */
    public String getCurrentDisplay() {
        return currentInput;
    }
    
    /**
     * Gets the current expression display.
     * 
     * @return the current expression
     */
    public String getExpressionDisplay() {
        return displayExpression;
    }
    
    /**
     * Checks if the calculator is in an error state.
     * 
     * @return true if there's an error, false otherwise
     */
    public boolean hasError() {
        return hasError;
    }
    
    /**
     * Gets the current numeric value for testing purposes.
     * 
     * @return the current numeric value
     */
    public BigDecimal getCurrentValue() {
        return currentValue;
    }
    
    /**
     * Gets the current operation for testing purposes.
     * 
     * @return the current operation, or null if none
     */
    public Operation getCurrentOperation() {
        return currentOperation;
    }
}
