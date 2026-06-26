package com.npbpredict.app.controller;

import com.npbpredict.app.entity.Game;
import com.npbpredict.app.entity.Prediction;
import com.npbpredict.app.repository.GameRepository;
import com.npbpredict.app.repository.PredictionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/games")
public class GameController {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PredictionRepository predictionRepository;

    @GetMapping("/{gameId}/odds")
    public ResponseEntity<Map<String, Object>> getGameOdds(@PathVariable Long gameId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found"));

        List<Prediction> predictions = predictionRepository.findByGameId(gameId);

        long homeVotes = predictions.stream()
                .filter(p -> p.getPredictedWinner() == Game.GameResult.HOME)
                .count();
        long awayVotes = predictions.stream()
                .filter(p -> p.getPredictedWinner() == Game.GameResult.AWAY)
                .count();
        long drawVotes = predictions.stream()
                .filter(p -> p.getPredictedWinner() == Game.GameResult.DRAW)
                .count();

        long totalVotes = homeVotes + awayVotes + drawVotes;

        Map<String, Object> response = new HashMap<>();
        response.put("totalVotes", totalVotes);
        if (totalVotes > 0) {
            response.put("homeRatio", (double) homeVotes / totalVotes * 100);
            response.put("awayRatio", (double) awayVotes / totalVotes * 100);
            response.put("drawRatio", (double) drawVotes / totalVotes * 100);
        } else {
            response.put("homeRatio", 0.0);
            response.put("awayRatio", 0.0);
            response.put("drawRatio", 0.0);
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{gameId}/predictions")
    public ResponseEntity<List<Map<String, String>>> getGamePredictions(@PathVariable Long gameId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found"));

        if (LocalDateTime.now(ZoneId.of("Asia/Tokyo")).isBefore(game.getStartTime())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Predictions are hidden before the game starts.");
        }

        List<Prediction> predictions = predictionRepository.findByGameIdWithUser(gameId);

        List<Map<String, String>> response = predictions.stream().map(p -> {
            Map<String, String> map = new HashMap<>();
            map.put("nickname", p.getUser().getNickname());
            map.put("predictedWinner", p.getPredictedWinner().name());
            return map;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
}
