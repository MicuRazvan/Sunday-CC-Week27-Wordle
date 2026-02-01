package com.Sunday_CC_Week27_Worldle;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import java.io.*;
import java.util.*;

@Service
public class WordleService {
    private List<String> wordList = new ArrayList<>();
    private String secretWord;

    @PostConstruct
    public void init() throws IOException {
        InputStream is = getClass().getClassLoader().getResourceAsStream("words.csv");

        if (is == null) {
            throw new FileNotFoundException("Could not find words.csv in src/main/resources/");
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = br.readLine()) != null) {
                String word = line.trim().toUpperCase();
                if (word.length() == 5) {
                    wordList.add(word);
                }
            }
        }

        if (wordList.isEmpty()) {
            throw new RuntimeException("words.csv is empty or contains no 5-letter words");
        }

        startNewGame();
    }

    public void startNewGame() {
        secretWord = wordList.get(new Random().nextInt(wordList.size()));
        System.out.println("Secret word is: " + secretWord);
    }

    public String[] checkGuess(String guess) {
        guess = guess.toUpperCase();
        String[] result = new String[5];
        boolean[] secretUsed = new boolean[5];
        boolean[] guessUsed = new boolean[5];

        for (int i = 0; i < 5; i++) {
            if (guess.charAt(i) == secretWord.charAt(i)) {
                result[i] = "correct";
                secretUsed[i] = true;
                guessUsed[i] = true;
            }
        }

        for (int i = 0; i < 5; i++) {
            if (guessUsed[i]) continue;
            for (int j = 0; j < 5; j++) {
                if (!secretUsed[j] && guess.charAt(i) == secretWord.charAt(j)) {
                    result[i] = "present";
                    secretUsed[j] = true;
                    break;
                }
            }
            if (result[i] == null) result[i] = "absent";
        }
        return result;
    }
}