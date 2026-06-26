package com.npbpredict.app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 試合情報を表すEntityクラス。
 * DBの "games" テーブルと紐付きます。
 */
@Entity // このクラスがJPAのEntityであることを示します
@Table(name = "games") // マッピングするテーブル名を "games" に指定します
@Getter // Lombok: getterを自動生成
@Setter // Lombok: setterを自動生成
@NoArgsConstructor // Lombok: デフォルトコンストラクタを生成
@AllArgsConstructor // Lombok: 全引数コンストラクタを生成
public class Game {

    /**
     * 試合ID（プライマリキー）。自動採番されます。
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 試合が開催される日付。
     */
    @Column(name = "game_date", nullable = false)
    private LocalDate gameDate;

    /**
     * 試合開始時刻。この時刻を過ぎると予想の投票・変更が締め切られます。
     */
    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    /**
     * ホームチームの名前。
     */
    @Column(name = "home_team", nullable = false)
    private String homeTeam;

    /**
     * ビジター（アウェイ）チームの名前。
     */
    @Column(name = "away_team", nullable = false)
    private String awayTeam;

    /**
     * 試合の現在の状態（試合前、投票締切後、試合終了）。
     * 列挙型(Enum)の文字列表現としてデータベースに保存します。
     * デフォルトは BEFORE_GAME（試合前）です。
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GameStatus status = GameStatus.BEFORE_GAME;

    /**
     * 試合結果（ホーム勝ち、アウェイ勝ち、引き分け）。
     * 試合終了後に管理者が入力するまで null になります。
     */
    @Enumerated(EnumType.STRING)
    private GameResult result;

    /**
     * 試合の状態を表す列挙型
     */
    public enum GameStatus {
        BEFORE_GAME,   // 試合前（投票受付中）
        VOTING_CLOSED, // 投票締切後（試合開始〜終了前）
        GAME_END       // 試合終了（結果確定）
    }

    /**
     * 試合結果を表す列挙型
     */
    public enum GameResult {
        HOME, // ホームチームの勝利
        AWAY, // アウェイチームの勝利
        DRAW  // 引き分け
    }
}
