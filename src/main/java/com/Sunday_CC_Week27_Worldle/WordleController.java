package com.Sunday_CC_Week27_Worldle;

import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class WordleController {
    private final WordleService service;

    public WordleController(WordleService service) {
        this.service = service;
    }

    @PostMapping("/new-game")
    public void newGame() {
        service.startNewGame();
    }

    @GetMapping("/guess")
    public String[] guess(@RequestParam String word) {
        return service.checkGuess(word);
    }
}
