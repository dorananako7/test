package com.npbpredict.app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * ユーザー情報を表すEntityクラス。
 * DBの "users" テーブルと紐付きます。
 */
@Entity // このクラスがJPAのEntityであることを示します
@Table(name = "users") // マッピングするテーブル名を "users" に指定します
@Getter // Lombok: すべてのフィールドのgetterを自動生成します
@Setter // Lombok: すべてのフィールドのsetterを自動生成します
@NoArgsConstructor // Lombok: 引数なしのデフォルトコンストラクタを自動生成します
@AllArgsConstructor // Lombok: 全フィールドを引数に取るコンストラクタを自動生成します
public class User {

    /**
     * 内部ユーザーID（プライマリキー）。
     * データベース側で自動的に連番が振られます(GenerationType.IDENTITY)。
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Google OAuth2認証時に取得する一意の識別子。
     * 重複を許さず(unique = true)、必須項目(nullable = false)とします。
     */
    @Column(name = "google_sub_id", unique = true, nullable = false)
    private String googleSubId;

    /**
     * アプリケーション内で表示されるユーザーのニックネーム。
     * 重複を許さず、必須項目とします。
     */
    @Column(unique = true, nullable = false)
    private String nickname;

    /**
     * ユーザーのお気に入り球団。
     */
    @Column(name = "favorite_team")
    private String favoriteTeam;

    /**
     * ユーザーが獲得した累計ポイント。
     * デフォルト値は0で、必須項目です。
     */
    @Column(name = "total_points", nullable = false)
    private Integer totalPoints = 0;
}
