package com.example.androidcryptography;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText inputText, outputText, keyEditText;
    private RadioButton scytaleRadioButton, caesarRadioButton, vigenereRadioButton;
    private Button encryptButton, decryptButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputText = findViewById(R.id.inputText);
        outputText = findViewById(R.id.outputText);
        keyEditText = findViewById(R.id.keyEditText);
        scytaleRadioButton = findViewById(R.id.scytaleRadioButton);
        caesarRadioButton = findViewById(R.id.caesarRadioButton);
        vigenereRadioButton = findViewById(R.id.vigenereRadioButton);
        encryptButton = findViewById(R.id.encryptButton);
        decryptButton = findViewById(R.id.decryptButton);

        encryptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                encryptMessage();
            }
        });

        decryptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decryptMessage();
            }
        });
    }

    private void encryptMessage() {   //When one of the radio buttons is checked then it go to that method and encrypt/decrypt the code
        String inputMessage = inputText.getText().toString().toUpperCase().replaceAll("[^A-Z]", "");

        if (scytaleRadioButton.isChecked()) {
            // Scytale encryption
            outputText.setText(encryptScytale(inputMessage, Integer.parseInt(keyEditText.getText().toString())));
        } else if (caesarRadioButton.isChecked()) {
            // Caesar encryption
            outputText.setText(encryptCaesar(inputMessage, Integer.parseInt(keyEditText.getText().toString())));
        } else if (vigenereRadioButton.isChecked()) {
            // Vigenere encryption
            outputText.setText(encryptVigenere(inputMessage, keyEditText.getText().toString()));
        } else {
            // Handle error, no cipher selected
            Toast.makeText(this, "Select a cipher", Toast.LENGTH_SHORT).show();
        }
    }

    private void decryptMessage() { // The exact same thing but just for the decrypt message
        String inputMessage = inputText.getText().toString().toUpperCase().replaceAll("[^A-Z]", "");

        if (scytaleRadioButton.isChecked()) {
            // Scytale decryption
            outputText.setText(decryptScytale(inputMessage, Integer.parseInt(keyEditText.getText().toString())));
        } else if (caesarRadioButton.isChecked()) {
            // Caesar decryption
            outputText.setText(decryptCaesar(inputMessage, Integer.parseInt(keyEditText.getText().toString())));
        } else if (vigenereRadioButton.isChecked()) {
            // Vigenere decryption
            outputText.setText(decryptVigenere(inputMessage, keyEditText.getText().toString()));
        } else {
            // Handle error, no cipher selected
            Toast.makeText(this, "Select a cipher", Toast.LENGTH_SHORT).show();
        }
    }

    // Scytale Cipher
    private String encryptScytale(String message, int rows) {
        int cols = (int) Math.ceil((double) message.length() / rows); //Divide the number of characters by the number of rows. Math.ceil rounds it up.
        char[][] grid = new char[rows][cols]; /* Empty 2d array */

        int index = 0;
        //Fills in the characters one at a time in each column once
        for (int col = 0; col < cols; col++) {
            for (int row = 0; row < rows; row++) {
                if (index < message.length()) {
                    grid[row][col] = message.charAt(index++);
                } else {
                    grid[row][col] = '@'; // Padding character
                }
            }
        }

        StringBuilder encrypted = new StringBuilder();        // String builder will take take the characters in the columns and connect it to the next set of characters in the columns in the next row
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                encrypted.append(grid[row][col]);
            }
        }

        return encrypted.toString();//Returns the new string built string
    }

    private String decryptScytale(String message, int rows) {
        int cols = (int) Math.ceil((double) message.length() / rows);
        char[][] grid = new char[rows][cols];

        int index = 0;
        //Exactly the same but fills the characters one at a time in each row
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                grid[row][col] = message.charAt(index++);
            }
        }

        StringBuilder decrypted = new StringBuilder();
        for (int col = 0; col < cols; col++) {
            for (int row = 0; row < rows; row++) {
                if (grid[row][col] != '@') {
                    decrypted.append(grid[row][col]);
                }
            }
        }

        return decrypted.toString();
    }

    // Caesar Cipher
    private String encryptCaesar(String message, int shift) {
        StringBuilder encrypted = new StringBuilder();

        for (int i = 0; i < message.length(); i++) {
            char originalChar = message.charAt(i);
            if (Character.isLetter(originalChar)) {
                char encryptedChar = (char) (((originalChar - 'A' + shift) % 26) + 'A');
                encrypted.append(encryptedChar);
            }
        }

        return encrypted.toString();
    }

    private String decryptCaesar(String message, int shift) {
        return encryptCaesar(message, 26 - shift); // Decryption is the opposite shift
    }

    // Vigenere Cipher
    private String encryptVigenere(String message, String keyword) {
        StringBuilder encrypted = new StringBuilder();

        for (int i = 0, j = 0; i < message.length(); i++) {
            char originalChar = message.charAt(i);
            if (Character.isLetter(originalChar)) {
                char keyChar = keyword.charAt(j % keyword.length());
                char encryptedChar = (char) (((originalChar - 'A' + keyChar - 'A') % 26) + 'A');
                encrypted.append(encryptedChar);
                j++;
            }
        }

        return encrypted.toString();
    }

    private String decryptVigenere(String message, String keyword) {
        StringBuilder decrypted = new StringBuilder();

        for (int i = 0, j = 0; i < message.length(); i++) {
            char originalChar = message.charAt(i);
            if (Character.isLetter(originalChar)) {
                char keyChar = keyword.charAt(j % keyword.length());
                char decryptedChar = (char) (((originalChar - keyChar + 26) % 26) + 'A');
                decrypted.append(decryptedChar);
                j++;
            }
        }

        return decrypted.toString();
    }
}