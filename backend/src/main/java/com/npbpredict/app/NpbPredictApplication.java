package com.npbpredict.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Spring Bootアプリケーションのエントリーポイント（起動クラス）。
 */
@SpringBootApplication // Spring Bootの自動設定やコンポーネントスキャンを有効にします
@EnableScheduling // 定期実行バッチ（例: 夜間のポイント集計など）のアノテーション(@Scheduled)を有効にします
public class NpbPredictApplication {

    /**
     * アプリケーションのメインメソッド。
     * ここからSpring Bootが起動し、内蔵のWebサーバー(Tomcat)が立ち上がります。
     */
    public static void main(String[] args) {
        SpringApplication.run(NpbPredictApplication.class, args);
    }

}
