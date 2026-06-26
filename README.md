# NPB予想コミュニティサイト (NPB Prediction App)

## 1. システム概要
プロ野球（NPB）の試合結果をユーザーが予想し、的中ポイントを競い合うランキング型コミュニティサイトです。投票受付中は「みんなの予想比率（オッズ）」をリアルタイムで確認でき、試合開始後は「誰がどちらに賭けたか」の内訳が解禁されることで、カンニングを防ぎつつ高いエンタメ性を実現します。

## 2. システムアーキテクチャ
フロントエンドとバックエンドが完全に分離された構成を採用しています。

- **フロントエンド**: React (TypeScript / Vite), Tailwind CSS
- **バックエンド**: Spring Boot (Java), Spring Security (OAuth2), Spring Data JPA
- **データベース**: PostgreSQL (ローカル開発用: DB `npb_prediction`, User `npb_user`, Password `npb_password`)
- **Webサーバー / ネットワーク**: Apache (リバースプロキシ), Tailscale Funnel (外部公開用)

## 3. ディレクトリ構成と役割
プロジェクトは、ルート直下で `frontend/` と `backend/` に分かれています。

```text
project-root/
├── frontend/                 # React (Vite / TypeScript) フロントエンド
│   ├── package.json
│   ├── vite.config.ts
│   └── src/
│       ├── components/       # 共通UIコンポーネント (Button, Card, Graph等)
│       ├── pages/            # 画面コンポーネント (Home, GameDetail, MyPage等)
│       └── api/              # バックエンドとのAPI通信処理 (fetch/axios等)
│
└── backend/                  # Spring Boot (Java) バックエンド
    ├── pom.xml               # 依存ライブラリ設定
    ├── mvnw                  # Mavenラッパー
    └── src/
        └── main/
            ├── resources/
            │   └── application.yml  # DB, OAuth2, サーバーポート等の設定
            └── java/
                └── com/npbpredict/app/
                    ├── NpbPredictApplication.java    # Spring Boot 起動クラス
                    ├── config/                       # セキュリティ (SecurityConfig), CORS, バッチ設定等
                    ├── controller/                   # REST APIエンドポイント (画面用HTMLは返さずJSONのみ)
                    ├── service/                      # ビジネスロジック (ポイント集計、オッズ計算、バッチ処理等)
                    ├── repository/                   # データベースアクセス (Spring Data JPA)
                    └── entity/                       # データベースのテーブル定義 (User, Game, Prediction等)
```

### バックエンドの各レイヤーの役割
- **Entity**: データベースのテーブルと1対1で対応するJavaクラスです。データの構造を定義します。
- **Repository**: Entityを使ってデータベースとやり取り（CRUD操作）を行うインターフェースです。
- **Service**: アプリケーションのビジネスロジックを担います。Controllerから呼び出され、Repositoryを使ってデータを操作し、必要な計算（オッズ計算など）を行います。
- **Controller**: フロントエンドからのHTTPリクエストを受け取り、Serviceに処理を依頼し、その結果をJSON形式でフロントエンドに返します。
- **Config**: アプリケーション全体の設定（セキュリティ、CORS、OAuth2、バッチスケジューリングなど）を行います。

## 4. 環境構築・起動手順

### 前提条件
- Node.js (v18以降推奨)
- Java 17以降
- PostgreSQL

### データベースの設定 (PostgreSQL)
ローカルでPostgreSQLを起動し、以下のデータベースとユーザーを作成してください。
```sql
CREATE DATABASE npb_prediction;
CREATE USER npb_user WITH PASSWORD 'npb_password';
GRANT ALL PRIVILEGES ON DATABASE npb_prediction TO npb_user;
```

### バックエンドの起動
ターミナルを開き、`backend` ディレクトリに移動して以下のコマンドを実行します。
```bash
cd backend
# 依存関係のインストールとビルド (初回のみ、またはコード変更時)
./mvnw clean install

# Spring Boot アプリケーションの起動
./mvnw spring-boot:run
```
バックエンドはデフォルトで `http://localhost:8080` で起動します。

### フロントエンドの起動
別のターミナルを開き、`frontend` ディレクトリに移動して以下のコマンドを実行します。
```bash
cd frontend
# 依存関係のインストール (初回のみ)
npm install

# 開発用サーバーの起動
npm run dev
```
フロントエンドはデフォルトで Vite のポート (例: `http://localhost:5173`) で起動します。

## 5. デバッグ・開発の仕組み
- **API通信**: フロントエンドは `frontend/src/api/` 以下の関数から、バックエンドのController (例: `/api/games/{gameId}/odds`) に対してリクエストを送信します。
- **CORS対策**: 開発環境ではViteのプロキシ設定を用いてバックエンドにリクエストを転送します。本番環境ではApacheのリバースプロキシを利用し、同じドメインで動作させます (`/` はフロント、`/api/*` はバックエンド)。
- **認証**: 初回アクセス時にGoogle OAuth2によるログインを行い、バックエンド側でセッションまたはJWTを発行してユーザーを識別します。パスワードは保持しません。
- **バッチ処理**: `backend/src/main/java/com/npbpredict/app/config/` または `service/` にScheduledアノテーションを使ったジョブを定義し、毎日定時に試合結果の判定とポイント付与を行います。
- **カンニング防止の仕組み**: 試合開始前は `/api/games/{gameId}/predictions` API が `403 Forbidden` を返すか、匿名化されたオッズデータのみを返すように制御します。試合開始時刻を過ぎると、ユーザー個別の予想データが解禁されます。
