# NPB予想コミュニティサイト (NPB Prediction App)

## 1. システム概要
プロ野球（NPB）の試合結果をユーザーが予想し、的中ポイントを競い合うランキング型コミュニティサイトです。投票受付中は「みんなの予想比率（オッズ）」をリアルタイムで確認でき、試合開始後は「誰がどちらに賭けたか」の内訳が解禁されることで、カンニングを防ぎつつ高いエンタメ性を実現します。

## 2. システムアーキテクチャ
フロントエンドとバックエンドが完全に分離された構成を採用しています。

- **フロントエンド**: React (TypeScript / Vite), Tailwind CSS
- **バックエンド**: Spring Boot (Java), Spring Security (OAuth2), Spring Data JPA
- **データベース**: PostgreSQL
- **インフラ・環境**: Docker & Docker Compose (開発環境の一括起動用)

## 3. ディレクトリ構成と役割
プロジェクトは、ルート直下に `docker-compose.yml` とフロント/バックエンドの各ディレクトリが分かれています。

```text
project-root/
├── docker-compose.yml        # DB, Backend, Frontendを一括起動する設定ファイル
├── frontend/                 # React (Vite / TypeScript) フロントエンド
│   ├── Dockerfile            # フロントエンド用Docker定義
│   ├── package.json
│   ├── vite.config.ts
│   └── src/
│       ├── components/       # 共通UIコンポーネント
│       ├── pages/            # 画面コンポーネント
│       └── api/              # API通信処理
│
└── backend/                  # Spring Boot (Java) バックエンド
    ├── Dockerfile            # バックエンド用Docker定義
    ├── pom.xml               # 依存ライブラリ設定
    ├── mvnw                  # Mavenラッパー
    └── src/
        └── main/
            ├── resources/
            │   └── application.yml  # Docker内のDB(PostgreSQL)へ接続する設定
            └── java/
                └── com/npbpredict/app/
                    ├── NpbPredictApplication.java
                    ├── config/
                    ├── controller/
                    ├── service/
                    ├── repository/
                    └── entity/
```

## 4. 環境構築・起動手順 (Docker Compose)

本プロジェクトの開発環境は、Docker Composeを使用して完全にコンテナ化されています。**手動でPostgreSQLをインストールしたり、データベースを作成したりする必要はありません。**

### 前提条件
- Docker
- Docker Compose

### 起動方法
ターミナルを開き、プロジェクトのルートディレクトリ（`docker-compose.yml`があるディレクトリ）で以下のコマンドを実行します。

```bash
# 全てのコンテナ（DB、バックエンド、フロントエンド）をバックグラウンドで起動
docker compose up -d --build
```

このコマンド1つで以下の処理が自動で行われます：
1. **PostgreSQLコンテナの起動**: データベース `npb_prediction` とユーザー `npb_user` / `npb_password` が自動的に作成されます。
2. **Spring Bootバックエンドの起動**: Mavenによるビルドが行われ、`http://localhost:8080` でAPIサーバーが立ち上がります（自動的にDBコンテナへ接続します）。
3. **Reactフロントエンドの起動**: 依存関係がインストールされ、`http://localhost:5173` で開発サーバーが立ち上がります。

### コンテナの停止
開発を終了する場合は、以下のコマンドでコンテナを停止・削除します。

```bash
docker compose down
```
※データベースのデータはDockerボリューム (`postgres_data`) に永続化されているため、再度起動した際もデータは保持されます。

## 5. デバッグ・開発の仕組み
- **API通信**: フロントエンドは `frontend/src/api/` 以下の関数から、バックエンドのController (例: `/api/games/{gameId}/odds`) に対してリクエストを送信します。
- **バッチ処理**: `backend/src/main/java/com/npbpredict/app/config/` または `service/` にScheduledアノテーションを使ったジョブを定義し、毎日定時に試合結果の判定とポイント付与を行います。
- **カンニング防止の仕組み**: 試合開始前は `/api/games/{gameId}/predictions` API が `403 Forbidden` を返すか、匿名化されたオッズデータのみを返すように制御します。試合開始時刻を過ぎると、ユーザー個別の予想データが解禁されます。
