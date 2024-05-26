package calculator;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Calculator extends Application {
    private Label operationLabel = new Label("");
    private Font font = new Font("Monospaced", 30);

    @Override
    public void start(Stage primaryStage) {
        operationLabel.setMinHeight(50);
        operationLabel.setFont(font);

        GridPane gridPane = new GridPane();
        gridPane.setMinSize(400, 400);

        String[] buttonLabels = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "C", "0", "=", "+"
        };

        int buttonIndex = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Button button = new Button(buttonLabels[buttonIndex]);
                button.setMinSize(100, 100);
                button.setFont(font);
                button.setOnAction(event -> handleButtonClick(button.getText()));
                gridPane.add(button, j, i);
                buttonIndex++;
            }
        }

        VBox vbox = new VBox();
        vbox.getChildren().addAll(operationLabel, gridPane);

        Scene scene = new Scene(vbox, 400, 450);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Calculator");
        primaryStage.show();
    }

    private void handleButtonClick(String buttonText) {
        if(Character.isDigit(buttonText.charAt(0))) {
            operationLabel.setText(operationLabel.getText() + buttonText);
        }
        if ("+-*/".contains(buttonText)) {
            operationLabel.setText(operationLabel.getText() + " " + buttonText + " ");    
        }
        else if ("C".equals(buttonText)) {
            operationLabel.setText("");
        }
        else if ("=".equals(buttonText)) {
            String[] operation = operationLabel.getText().split(" ");
            
            
            String operator = operation[1];
            double firstNumber = Double.parseDouble(operation[0]);
            double secondNumber = Double.parseDouble(operation[2]);
    
            switch (operator) {
                case "+":
                    operationLabel.setText(String.valueOf(firstNumber + secondNumber));
                    break;
                case "-":
                    operationLabel.setText(String.valueOf(firstNumber - secondNumber));
                    break;
                case "*":
                    operationLabel.setText(String.valueOf(firstNumber * secondNumber));
                    break;
                case "/":
                    operationLabel.setText(secondNumber != 0 ? String.format("%.3f", firstNumber / secondNumber) : "Ne može se dijeliti nulom");
                    break;
            }
        } 
    }

    public static void main(String[] args) {
        launch(args);
    }
}
