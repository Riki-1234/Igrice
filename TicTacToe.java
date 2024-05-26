package tictactoe;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class TicTacToe extends Application {
    private char player = 'X';
    private Button[] board = new Button[9];
    private Label label = new Label("X je na redu.");
    private boolean gameActive = true;
    private Font font = new Font("Monospaced", 30);

    @Override
    public void start(Stage primaryStage) {
        GridPane gridPane = new GridPane();

        int buttonIndex = 0;
        for (int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
               Button button = new Button();
               button.setPrefSize(150, 150);
               button.setFont(font);
            
               final int index = buttonIndex;
               button.setOnAction(e -> handleButtonClick(index));
               board[buttonIndex] = button;
               gridPane.add(button, j, i);
               
               buttonIndex++;
            }
        }
        
        label.setFont(font);
        VBox vbox = new VBox(gridPane, label);
        Scene scene = new Scene(vbox, 450, 500);
        
        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleButtonClick(int index) {
        if (!gameActive || !board[index].getText().isEmpty()) {
            return;
        }

        board[index].setText(String.valueOf(player));
        if (hasWon()) {
            label.setText(player + " je pobijedio.");
            gameActive = false;
        } else if (isBoardFull()) {
            label.setText("Izjednačeno je.");
            gameActive = false;
        } else {
            player = player == 'X' ? 'O' : 'X';
            label.setText(player + " je na redu.");
        }
    }

    private boolean hasWon() {
        int[][] conditions = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, 
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, 
            {0, 4, 8}, {2, 4, 6}             
        };
        for(int i = 0; i < 8; i++) {
            if(!board[conditions[i][0]].getText().isEmpty() 
             && board[conditions[i][0]].getText().equals(board[conditions[i][1]].getText()) 
             && board[conditions[i][1]].getText().equals(board[conditions[i][2]].getText())) {
                return true;
            }
        }
        return false;
    }

    private boolean isBoardFull() {
        for (Button button : board) {
            if (button.getText().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
