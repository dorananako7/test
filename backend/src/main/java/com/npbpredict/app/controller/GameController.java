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

/**
 * 試合に関するリクエストを処理するREST APIコントローラー。
 * 全てのエンドポイントは /api/games をベースにします。
 */
@RestController // JSONレスポンスを返すControllerであることを示します
@RequestMapping("/api/games")
public class GameController {

    // Gameデータのデータベース操作を行うためのリポジトリを自動で注入(DI)します
    @Autowired
    private GameRepository gameRepository;

    // 予想データのデータベース操作を行うためのリポジトリを自動で注入(DI)します
    @Autowired
    private PredictionRepository predictionRepository;

    /**
     * 試合開始前（または後）のオッズ（投票比率）を取得するAPI。
     * ユーザー個別の予想は含まず、集計データのみを返します。
     * URL: GET /api/games/{gameId}/odds
     */
    @GetMapping("/{gameId}/odds")
    public ResponseEntity<Map<String, Object>> getGameOdds(@PathVariable Long gameId) {
        // 指定されたIDの試合が存在するか確認し、なければ404 NotFoundエラーを返します
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found"));

        // その試合に対する全ユーザーの予想データを取得
        List<Prediction> predictions = predictionRepository.findByGameId(gameId);

        // ホーム、アウェイ、引き分けのそれぞれの投票数をカウント
        long homeVotes = predictions.stream()
                .filter(p -> p.getPredictedWinner() == Game.GameResult.HOME)
                .count();
        long awayVotes = predictions.stream()
                .filter(p -> p.getPredictedWinner() == Game.GameResult.AWAY)
                .count();
        long drawVotes = predictions.stream()
                .filter(p -> p.getPredictedWinner() == Game.GameResult.DRAW)
                .count();

        // 総投票数を計算
        long totalVotes = homeVotes + awayVotes + drawVotes;

        // レスポンス用のMapを作成し、総投票数と比率(%)を計算して格納
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

        // HTTP 200 OK と共にJSON形式でデータを返却
        return ResponseEntity.ok(response);
    }

    /**
     * 試合開始後に、「誰がどちらに投票したか」の具体的な内訳を取得するAPI。
     * カンニング防止のため、試合開始前はアクセスが拒否されます。
     * URL: GET /api/games/{gameId}/predictions
     */
    @GetMapping("/{gameId}/predictions")
    public ResponseEntity<List<Map<String, String>>> getGamePredictions(@PathVariable Long gameId) {
        // 試合情報の取得
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found"));

        // 【カンニング防止ロジック】
        // 日本時間(Asia/Tokyo)で現在時刻を取得し、試合開始前であれば 403 Forbidden エラーを返す
        if (LocalDateTime.now(ZoneId.of("Asia/Tokyo")).isBefore(game.getStartTime())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Predictions are hidden before the game starts.");
        }

        // 試合開始後であれば、ユーザー情報も含めて予想データをDBから取得
        List<Prediction> predictions = predictionRepository.findByGameIdWithUser(gameId);

        // ユーザー名(nickname)と予想内容(predictedWinner)のリストに変換して返す
        List<Map<String, String>> response = predictions.stream().map(p -> {
            Map<String, String> map = new HashMap<>();
            map.put("nickname", p.getUser().getNickname());
            map.put("predictedWinner", p.getPredictedWinner().name());
            return map;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
}
