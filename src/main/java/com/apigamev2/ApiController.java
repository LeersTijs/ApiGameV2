package com.apigamev2;

import com.apigamev2.Factory.ConnectFourFactory;
import com.apigamev2.Factory.Factory;
import com.apigamev2.Factory.MineSweeperFactory;
import com.apigamev2.Games.game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/Game")
public class ApiController {

    private List<game> games;
    private final Map<String, Factory> availableGames = new HashMap<>();

    @Autowired
    ApiController() {
        availableGames.put("connectFour", new ConnectFourFactory());
        availableGames.put("mineSweeper", new MineSweeperFactory());
    }

    @GetMapping("/CreateNewGame")
    public Map<Integer, String[]> createNewGame(@RequestParam String game) {
        if (!availableGames.containsKey(game))
            return null;
        int id = games.size();

        return null;
    }
}
