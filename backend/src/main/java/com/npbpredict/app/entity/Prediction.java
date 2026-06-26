package com.npbpredict.app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * ユーザーの予想データを表すEntityクラス。
 * DBの "predictions" テーブルと紐付きます。
 */
@Entity
@Table(name = "predictions")
@Getter // Lombok: getterを自動生成
@Setter // Lombok: setterを自動生成
@NoArgsConstructor // Lombok: デフォルトコンストラクタを生成
@AllArgsConstructor // Lombok: 全引数コンストラクタを生成
public class Prediction {

    /**
     * 予想ID（プライマリキー）。自動採番されます。
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 予想を行ったユーザーへの参照（外部キー）。
     * 遅延ロード(FetchType.LAZY)を指定し、必要な時だけユーザー情報を取得します。
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * 予想対象の試合への参照（外部キー）。
     * 遅延ロード(FetchType.LAZY)を指定します。
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    /**
     * ユーザーが予想した勝者（ホーム、アウェイ、引き分け）。
     * Game.GameResult 列挙型の文字列表現として保存されます。
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "predicted_winner", nullable = false)
    private Game.GameResult predictedWinner;

    /**
     * 予想が的中したかどうかを示すフラグ。
     * 試合結果が確定するまでは null になります。
     */
    @Column(name = "is_correct")
    private Boolean isCorrect;
}
