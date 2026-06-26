# NPB予想コミュニティサイト - プログラム詳細解説集

本ドキュメントでは、本プロジェクトの主要な設定ファイルおよびソースコードについて、1行1行（または論理的なブロックごと）の意味や役割を解説します。

---

## 1. 開発環境・コンテナ設定ファイル

### `docker-compose.yml`
ルートディレクトリに配置され、Docker Composeによってアプリケーション全体（データベース、バックエンド、フロントエンド）を立ち上げるための設定ファイルです。

- **`version: '3.8'`**: Composeファイルの文法バージョンを指定します。
- **`services:`**: 起動する各コンテナ（サービス）の定義を開始します。
- **`postgres:`**: PostgreSQLデータベースサーバーの設定です。
  - `image: postgres:15`: 公式のPostgreSQLバージョン15を使用します。
  - `container_name: npb_postgres`: コンテナ名を明示的に設定し、他のコンテナやコマンドラインから判別しやすくします。
  - `environment:`: コンテナ起動時の環境変数を定義します。ここで指定した `POSTGRES_DB`, `POSTGRES_USER`, `POSTGRES_PASSWORD` に基づいて、PostgreSQLが初期起動時に自動的にデータベースとユーザーを作成します。これにより手動のSQL実行が不要になります。
  - `ports: - "5432:5432"`: ホストのポート5432とコンテナのポート5432を繋ぎます。
  - `volumes:`: `postgres_data` というDockerボリュームをコンテナ内の `/var/lib/postgresql/data` にマウントし、コンテナを再作成してもデータが消えないように永続化します。
- **`backend:`**: Spring Bootアプリケーションの設定です。
  - `build:`: `./backend` ディレクトリ内にある `Dockerfile` を基にしてイメージをビルドするよう指定します。
  - `depends_on: - postgres`: `postgres`コンテナが起動した後にこのコンテナを起動します。
  - `environment:`: Spring Bootアプリケーション内で読み込まれるデータベース接続設定を環境変数で上書きします。ホスト名として `localhost` ではなく `postgres` (サービス名) を指定することで、Dockerの内部ネットワークを通じてDBに接続できます。
- **`frontend:`**: React(Vite)アプリケーションの設定です。
  - `build:`: `./frontend` ディレクトリ内にある `Dockerfile` を基にしてイメージをビルドします。
  - `ports: - "5173:5173"`: Viteのデフォルト開発ポートをホストに公開します。
  - `volumes:`: ホストの `./frontend` ディレクトリをコンテナの `/app` にマウントします。これにより、ホスト側でソースコードを編集すると、コンテナ内のViteのHMR(ホットリロード)が反応して即座に画面に反映されます。ただし、コンテナ内でインストールした `node_modules` をホスト側で上書きしないように、`- /app/node_modules` (匿名ボリューム) を追加で指定しています。

---

### `backend/Dockerfile`
Spring Bootアプリケーションをコンテナ化するためのマルチステージビルド定義ファイルです。

- **`FROM eclipse-temurin:17-jdk-alpine AS build`**: 第1ステージ(ビルド用)。JDK(Java Development Kit)が含まれたAlpine Linuxベースの軽量イメージを使用します。
- **`COPY mvnw .` / `COPY .mvn .mvn` / `COPY pom.xml .`**: Mavenラッパーとプロジェクトの定義ファイルをコピーします。
- **`RUN ./mvnw dependency:go-offline -B`**: 依存関係をダウンロードします。ここでソースコードより先に依存関係を処理することで、ソースコードのみが変更された際にDockerレイヤーのキャッシュを効かせ、ビルドを高速化します。
- **`COPY src src`**: 実際のJavaソースコードをコピーします。
- **`RUN ./mvnw clean package -DskipTests`**: Mavenを使用してソースコードをコンパイルし、実行可能なJARファイルを生成します。
- **`FROM eclipse-temurin:17-jre-alpine`**: 第2ステージ(実行用)。JDKではなく、より軽量なJRE(Java Runtime Environment)のみが含まれたイメージを使用し、最終的なイメージサイズを小さくします。
- **`COPY --from=build /app/target/*.jar app.jar`**: 第1ステージでビルドされたJARファイルだけを、この実行用レイヤーにコピーします。
- **`ENTRYPOINT ["java", "-jar", "app.jar"]`**: コンテナ起動時に `java -jar app.jar` コマンドを実行してアプリケーションを立ち上げます。

---

### `frontend/Dockerfile`
React(Vite)の開発サーバーをコンテナ化するための定義ファイルです。

- **`FROM node:18-alpine`**: Node.js 18がインストールされた軽量なAlpine Linuxイメージを使用します。
- **`COPY package*.json ./`**: npmのパッケージ定義ファイルを先にコピーします（キャッシュ効率向上のため）。
- **`RUN npm install`**: 必要なライブラリ（React, Vite, Tailwindなど）をインストールします。
- **`COPY . .`**: プロジェクトの全ファイルをコピーします。
- **`CMD ["npm", "run", "dev", "--", "--host"]`**: Viteの開発サーバーを起動します。`--host` オプションは、Viteがデフォルトのlocalhostだけでなく、コンテナ外部（ホストPCなどネットワーク外）からのアクセスを受け付けるようにするために必要です。

---

### `frontend/package.json`
フロントエンドのプロジェクト情報と依存関係を管理するファイルです。

- **`"name": "frontend"`**: プロジェクトの名前です。
- **`"type": "module"`**: このプロジェクトがECMAScriptモジュール(ESM)を使用していることを示します（import/export構文）。
- **`"scripts": { ... }`**: npmコマンドのエイリアスです。
  - `"dev": "vite"`: 開発用サーバーを起動します。
  - `"build": "tsc -b && vite build"`: TypeScriptの型チェック(tsc)を行った上で、本番用に最適化されたビルドを生成します。
- **`"dependencies": { ... }`**: アプリケーションの実行に必要なライブラリです（例：`react`, `react-dom`）。
- **`"devDependencies": { ... }`**: 開発時にのみ必要なライブラリです（例：TypeScript本体、Vite、React用の型定義ファイル）。

---

### `frontend/vite.config.ts`
Vite（ビルドツール兼開発サーバー）の設定ファイルです。

- **`import { defineConfig } from 'vite'`**: Viteの設定を定義するための関数を読み込みます（IDEでの型推論を助けます）。
- **`import react from '@vitejs/plugin-react'`**: ViteでReactを使用するための公式プラグインを読み込みます。
- **`export default defineConfig({ ... })`**: 設定オブジェクトをエクスポートします。
- **`plugins: [react()]`**: Reactプラグインを有効にします。これにより、JSXのコンパイルや高速なホットリロード(HMR)が可能になります。

---

## 2. バックエンド設定ファイル

### `backend/pom.xml`
Mavenのプロジェクト管理ファイルで、使用するライブラリ（依存関係）を定義します。

- **`<parent>`**: Spring Bootの親プロジェクト（`spring-boot-starter-parent`）を指定し、互換性のあるライブラリバージョンを自動管理させます。
- **`<dependencies>`**: このプロジェクトで使うライブラリのリストです。
  - `spring-boot-starter-web`: REST APIを構築するための基本機能（Tomcat, Spring MVCなど）。
  - `spring-boot-starter-data-jpa`: データベース操作（ORM）を簡略化する機能。
  - `spring-boot-starter-security`: 認証・認可など、アプリを保護するセキュリティ機能。
  - `spring-boot-starter-oauth2-client`: GoogleログインなどのOAuth2連携を行うための機能。
  - `postgresql`: PostgreSQLデータベースに接続するためのJDBCドライバー。
  - `lombok`: アノテーションをつけるだけで、Getter/Setterなどを自動生成してくれる便利ツール。

---

### `backend/src/main/resources/application.yml`
Spring Bootアプリケーションの全体的な設定（DB接続、ポート、セキュリティなど）を記述するファイルです。

- **`spring.datasource.*`**: PostgreSQLへの接続情報です。`url`には `jdbc:postgresql://postgres:5432/npb_prediction` のように、ホスト名にdocker-composeのサービス名である `postgres` を指定します。
- **`spring.jpa.hibernate.ddl-auto: update`**: EntityクラスのJavaコードから、自動的にデータベースのテーブルを作成・更新させる便利な設定です。
- **`spring.security.oauth2.client.registration.google`**: Googleログインに必要なクライアントIDやシークレットを設定する場所です（現在はプレースホルダー）。

---

## 3. バックエンド ソースコード (Java)

### `NpbPredictApplication.java`
- **`@SpringBootApplication`**: このクラスがSpring Bootの起動クラスであることを示します。
- **`@EnableScheduling`**: ポイント集計バッチなどを深夜に自動実行するためのスケジューリング機能を有効にします。
- **`SpringApplication.run(...)`**: アプリケーションを実行し、内蔵のWebサーバーを起動します。

### Entityクラス群 (`User.java`, `Game.java`, `Prediction.java`)
データベースのテーブルと1対1で対応するクラスです。
- **`@Entity`**: JPAにこのクラスがDBテーブルであることを教えます。
- **`@Table(name="...")`**: 対応するテーブル名を指定します。
- **`@Id` と `@GeneratedValue(strategy = GenerationType.IDENTITY)`**: このフィールドが主キー(PK)であり、DB側で自動的に連番が割り当てられることを示します。
- **`@Column`**: カラムの制約（名前、必須かどうか `nullable=false`、一意かどうか `unique=true`）を指定します。
- **`@Enumerated(EnumType.STRING)`**: Enum（列挙型）の値を、数値ではなく文字列としてDBに保存します（例：`"HOME"`, `"AWAY"` など）。
- **`@ManyToOne` / `@JoinColumn`**: テーブル間のリレーション（外部キー制約）を示します。例としてPredictionクラスでは「どのユーザーが」「どの試合を」予想したかを保持しています。

### Repositoryクラス群 (`GameRepository.java`, `PredictionRepository.java`)
データベースへの操作（CRUD）を行うインターフェースです。
- **`extends JpaRepository<Game, Long>`**: Spring Data JPAが提供するインターフェースを継承するだけで、基本的な保存(`save`)や検索(`findById`)のメソッドが自動的に実装されます。
- **`@Query`**: 複雑なSQL（JPQL）を直接記述する場合に使用します。`PredictionRepository`では、N+1問題を回避しつつユーザー情報を一度に取得するために `JOIN FETCH` を使っています。

### Controllerクラス (`GameController.java`)
フロントエンドからのAPIリクエストを受け取り、処理結果をJSONで返すクラスです。
- **`@RestController`**: 画面(HTML)ではなく、データ(JSON)を返すコントローラーであることを示します。
- **`@RequestMapping("/api/games")`**: このコントローラー内の全エンドポイントの共通パスを設定します。
- **`getGameOdds` メソッド (`/{gameId}/odds`)**:
  - 試合IDをもとに予想データを取得します。
  - JavaのStream API (`stream().filter(...).count()`) を使って、ホーム、アウェイ、引き分けそれぞれの投票数をカウントします。
  - 各比率を計算し、個人情報を除いた集計データだけをMapに詰めて返します（これがリアルタイムオッズになります）。
- **`getGamePredictions` メソッド (`/{gameId}/predictions`)**:
  - 試合開始後の「誰がどちらに賭けたか」の内訳を返すAPIです。
  - `LocalDateTime.now(ZoneId.of("Asia/Tokyo")).isBefore(...)` を使って、現在時刻が試合開始時刻より前であればアクセスを拒否(`403 Forbidden`)し、カンニングを防止します。
  - 試合開始後であれば、DBからデータを取り出し、ユーザーのニックネームと予想結果のリストを作成して返します。
