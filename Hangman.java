package hangman;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.Random;
import javafx.geometry.Pos;
import javafx.scene.text.Font;

public class Hangman extends Application {
    private String[][] words = {
        {"kuća", "Građevina"},
        {"kutija", "Predmet"},
        {"kiša", "Vremenska pojava"},
        {"računar", "Uređaj"},
        {"podloga", "Predmet"}
    };
    private String word;
    private String theme;
    private char[] guessedWord;
    
    private int triesRemaining = 6;
    
    private Label wordLabel;
    private Label themeLabel;
    private Label triesLabel;

    @Override
    public void start(Stage primaryStage) {
        Random random = new Random();
        int index = random.nextInt(words.length);
        word = words[index][0];
        theme = words[index][1];

        guessedWord = new char[word.length()];
        for (int i = 0; i < word.length(); i++) {
            guessedWord[i] = '_';
        }

        wordLabel = new Label(new String(guessedWord));
        themeLabel = new Label("Tema: " + theme);
        triesLabel = new Label("Preostalih pokušaja: " + triesRemaining);

        Font font = Font.font("Monospaced", 24);
        wordLabel.setFont(font);
        themeLabel.setFont(font);
        triesLabel.setFont(font);

        TextField textField = new TextField();
        textField.setFont(font);
        textField.setAlignment(Pos.CENTER);
        textField.setMaxWidth(200);
        textField.setOnKeyReleased(this::handleGuess);

        VBox layout = new VBox(20, wordLabel, themeLabel, triesLabel, textField);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 600, 400);

        primaryStage.setTitle("Hangman");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

private void handleGuess(KeyEvent event) {
    TextField textField = (TextField)event.getSource();
    String text = textField.getText();
    char letter = text.charAt(text.length() - 1);

    if (!isGuessCorrect(letter)) {
        triesRemaining--;
        updateTriesLabel();
    }
    updateWordLabel();
    
    textField.clear();
}

    private boolean isGuessCorrect(char letter) {
        boolean correct = false;
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == letter) {
                guessedWord[i] = letter;
                correct = true;
            }
        }
        return correct;
    }

    private void updateWordLabel() {
        wordLabel.setText(new String(guessedWord));
        if (new String(guessedWord).equals(word)) {
            wordLabel.setText("Pogodili ste riječ! Riječ je: " + word);
        }
    }

    private void updateTriesLabel() {
        triesLabel.setText("Preostalih pokušaja: " + triesRemaining);
        if (triesRemaining == 0) {
            triesLabel.setText("Izgubili ste. Riječ je: " + word);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
