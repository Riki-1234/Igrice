package memorygame;

import javafx.util.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MemoryGame extends Application {

    private Button[] buttons = new Button[16];
    private int firstButtonIndex = -1;
    private ArrayList<Character> pairs = new ArrayList<>(Arrays.asList('a', 'a', 'b', 'b', 'c', 'c', 'd', 'd', 'e', 'e', 'f', 'f', 'g', 'g', 'h', 'h'));
    private Label timeDisplay = new Label("");
    private int counter = 0;
    private Font font = new Font("Monospaced", 40);
    private long startTime = System.currentTimeMillis();

    @Override
    public void start(Stage primaryStage) {
        long seed = System.nanoTime();
        Collections.shuffle(pairs, new Random(seed));
        
        GridPane gridPane = new GridPane();
        gridPane.setMinSize(600, 600);

        int buttonIndex = 0;
        for (int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                buttons[buttonIndex] = new Button();
                buttons[buttonIndex].setMinSize(150, 150);
                buttons[buttonIndex].setFont(font);
                
                int final_index = buttonIndex;
                buttons[buttonIndex].setOnAction(event -> handleButtonClick(final_index, buttons[final_index]));
                
                gridPane.add(buttons[buttonIndex], j, i);
                buttonIndex++;
            }
        }
        
        timeDisplay.setMinHeight(50);
        timeDisplay.setFont(font);
        
        
        VBox vbox = new VBox();
        vbox.getChildren().addAll(timeDisplay, gridPane);

        Scene scene = new Scene(vbox, 600, 650);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Memory Game");
        primaryStage.show();
    }

private void handleButtonClick(int buttonIndex, Button button) {
    button.setText(String.valueOf(pairs.get(buttonIndex)));
    if (firstButtonIndex != -1) {       
        if (pairs.get(firstButtonIndex).equals(pairs.get(buttonIndex))) {
            System.out.println("Match");
            buttons[firstButtonIndex].setDisable(true);
            button.setDisable(true);
            
            counter++;
            if (counter == pairs.size() / 2) {
                long endTime = System.currentTimeMillis();
                long durationMilliseconds = endTime - startTime;
                long minutes = durationMilliseconds / 1000 / 60;
                long seconds = durationMilliseconds / 1000 % 60;
                
                timeDisplay.setText(String.format("%02d:%02d", minutes, seconds));
    }
        } 
        else {
            Duration duration = Duration.seconds(1);
            PauseTransition delay = new PauseTransition(duration);
            delay.setOnFinished(event -> {
                buttons[firstButtonIndex].setText("");
                button.setText("");
                
                firstButtonIndex = -1;
            });
            delay.play();
            return;
        }
        firstButtonIndex = -1;
    } 
    else {
        firstButtonIndex = buttonIndex;
    }
}

    public static void main(String[] args) {
        launch(args);
    }
}
