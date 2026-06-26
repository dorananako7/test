# ProgramInformation.md

本ファイルでは、プロジェクトの各ファイルについて「1行1行」完全に解説します。


docker-compose.yml
```
# Docker Composeのバージョン指定
// コメント。Docker Composeのバージョンを指定することを示します。
version: '3.8'
// Docker Composeファイルの文法バージョンを「3.8」に指定します。

// 空行。
# 定義するサービス（コンテナ）のリスト
// コメント。
services:
// 立ち上げるコンテナ（サービス）の一覧の定義を開始します。
# データベース用のPostgreSQLコンテナ
// コメント。
postgres:
// `postgres`という名前のサービス（コンテナ）の定義を開始します。
# 使用する公式のPostgreSQLイメージとバージョン
// コメント。
image: postgres:15
// ベースとなるDockerイメージとして、公式の`postgres:15`を使用することを指定します。
# コンテナに付ける名前
// コメント。
container_name: npb_postgres
// 生成されるコンテナの名前を明示的に`npb_postgres`に設定します。
# PostgreSQLコンテナに渡す環境変数（初期DB名やパスワードなど）
// コメント。
environment:
// このコンテナに渡す環境変数の定義を開始します。
# 起動時に作成されるデフォルトのデータベース名
// コメント。
POSTGRES_DB: npb_prediction
// PostgreSQLが起動時に自動作成するデータベース名を`npb_prediction`に指定します。
# データベースに接続するためのユーザー名
// コメント。
POSTGRES_USER: npb_user
// データベースのユーザー名を`npb_user`に指定します。
# データベースに接続するためのパスワード
// コメント。
POSTGRES_PASSWORD: npb_password
// データベースユーザーのパスワードを`npb_password`に指定します。
# ポートマッピング: ホストの5432ポートをコンテナの5432ポートに紐付け
// コメント。
ports:
// コンテナのポートとホストのポートを紐付ける設定を開始します。
- "5432:5432"
// ホスト側のポート5432をコンテナ内のポート5432に転送します。
# データを永続化するためのボリュームマッピング
// コメント。
volumes:
// コンテナのディレクトリをホスト側のボリュームにマウントする設定を開始します。
- postgres_data:/var/lib/postgresql/data
// Dockerが管理する名前付きボリューム`postgres_data`を、コンテナ内の`/var/lib/postgresql/data`にマウントし、データを永続化します。

// 空行。
# Spring Bootバックエンド用のコンテナ
// コメント。
backend:
// `backend`という名前のサービス（コンテナ）の定義を開始します。
# ビルド設定
// コメント。
build:
// 既存のイメージを使わず、Dockerfileからビルドする設定を開始します。
# ビルドのコンテキスト（ルートディレクトリからのパス）
// コメント。
context: ./backend
// ビルドのコンテキスト（基準となるディレクトリ）を`./backend`に指定します。
# 使用するDockerfileの名前
// コメント。
dockerfile: Dockerfile
// 使用するDockerfileの名前を`Dockerfile`に指定します。
# コンテナに付ける名前
// コメント。
container_name: npb_backend
// コンテナの名前を`npb_backend`に指定します。
# ポートマッピング: ホストの8080ポートをコンテナの8080ポートに紐付け
// コメント。
ports:
// ポート設定を開始します。
- "8080:8080"
// ホスト側のポート8080をコンテナ内のポート8080に転送します。
# 起動順序: postgresコンテナが起動した後に起動する
// コメント。
depends_on:
// 依存関係の設定を開始します。
- postgres
// `postgres`コンテナが起動した後に、このコンテナを起動するように指定します。
# Spring Bootアプリケーションに渡す環境変数
// コメント。
environment:
// バックエンドコンテナに渡す環境変数の設定を開始します。
# データベース接続URL（ホスト名を'postgres'にしてコンテナ名で名前解決）
// コメント。
- SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/npb_prediction
// Spring BootのDB接続URL環境変数。ホスト名を`postgres`（サービス名）にし、`npb_prediction`データベースに接続します。
# データベース接続ユーザー名
// コメント。
- SPRING_DATASOURCE_USERNAME=npb_user
// DB接続ユーザー名の環境変数を`npb_user`に指定します。
# データベース接続パスワード
// コメント。
- SPRING_DATASOURCE_PASSWORD=npb_password
// DB接続パスワードの環境変数を`npb_password`に指定します。

// 空行。
# React/Viteフロントエンド用のコンテナ
// コメント。
frontend:
// `frontend`という名前のサービス（コンテナ）の定義を開始します。
# ビルド設定
// コメント。
build:
// ビルド設定を開始します。
# ビルドのコンテキスト（ルートディレクトリからのパス）
// コメント。
context: ./frontend
// ビルドのコンテキストを`./frontend`に指定します。
# 使用するDockerfileの名前
// コメント。
dockerfile: Dockerfile
// 使用するDockerfileの名前を`Dockerfile`に指定します。
# コンテナに付ける名前
// コメント。
container_name: npb_frontend
// コンテナの名前を`npb_frontend`に指定します。
# ポートマッピング: ホストの5173ポートをコンテナの5173ポートに紐付け
// コメント。
ports:
// ポート設定を開始します。
- "5173:5173"
// ホスト側のポート5173をコンテナ内のポート5173に転送します。
# 起動順序: backendコンテナが起動した後に起動する
// コメント。
depends_on:
// 依存関係の設定を開始します。
- backend
// `backend`コンテナが起動した後に、このコンテナを起動するように指定します。
# ファイルの変更を検知するためのボリュームマッピング
// コメント。
volumes:
// ボリュームマウントの設定を開始します。
# ホストの./frontendディレクトリをコンテナの/appディレクトリにマウント
// コメント。
- ./frontend:/app
// ホスト側の`./frontend`ディレクトリを、コンテナ内の`/app`ディレクトリにマウントし、リアルタイムなコード変更の反映を可能にします。
# コンテナ内のnode_modulesをホスト側に上書きされないようにするための設定
// コメント。
- /app/node_modules
// コンテナ内の`/app/node_modules`を匿名ボリュームとしてマウントし、ホスト環境に上書きされないよう保護します。

// 空行。
# データ永続化のために定義されたボリューム
// コメント。
volumes:
// コンテナ間で共有、またはホスト側で永続化するためのボリュームの定義を開始します。
# PostgreSQLのデータを保存するボリューム
// コメント。
postgres_data:
// `postgres_data`という名前のボリュームを定義します（詳細な設定を省略しているため、デフォルトのローカルドライバで作成されます）。
```

backend/Dockerfile
```
# ステージ1: アプリケーションのビルド
// コメント。以降がビルド用のステージであることを示します。
# ベースイメージとして軽量なAlpine LinuxベースのJDK 17を使用
// コメント。
FROM eclipse-temurin:17-jdk-alpine AS build
// ビルド環境のベースイメージとして、JDK 17を含むAlpine Linux版のTemurinを指定し、このステージに`build`という名前を付けます。

// 空行。
# コンテナ内の作業ディレクトリを/appに設定
// コメント。
WORKDIR /app
// 以降のコマンドを実行するための作業ディレクトリを`/app`に指定します。

// 空行。
# Mavenラッパーと依存関係定義ファイル(pom.xml)をコピー
// コメント。
COPY mvnw .
// ホスト側の`mvnw`（Maven Wrapper）ファイルを、コンテナの現在の作業ディレクトリにコピーします。
COPY .mvn .mvn
// ホスト側の`.mvn`ディレクトリを、コンテナ内にコピーします。
COPY pom.xml .
// ホスト側の`pom.xml`を、コンテナ内にコピーします。

// 空行。
# 依存関係をダウンロード (pom.xmlが変更されない限り、このレイヤーはキャッシュされる)
// コメント。
RUN ./mvnw dependency:go-offline -B
// コンテナ内で`./mvnw dependency:go-offline -B`を実行し、依存ライブラリを事前にすべてダウンロードします。

// 空行。
# ソースコードをコンテナ内にコピー
// コメント。
COPY src src
// ホスト側の`src`ディレクトリ（ソースコード本体）を、コンテナ内の`src`ディレクトリにコピーします。

// 空行。
# テストをスキップしてMavenでパッケージ化（JARファイルを生成）
// コメント。
RUN ./mvnw clean package -DskipTests
// コンテナ内で`./mvnw clean package -DskipTests`を実行し、コンパイルとJARファイルの作成を行います（テストはスキップします）。

// 空行。
# ステージ2: アプリケーションの実行
// コメント。以降が実行用のステージであることを示します。
# 実行用にはより軽量なJREイメージを使用
// コメント。
FROM eclipse-temurin:17-jre-alpine
// 実行環境のベースイメージとして、軽量なJRE 17を含むAlpine Linux版のTemurinを指定します（これにより最終イメージサイズが小さくなります）。

// 空行。
# コンテナ内の作業ディレクトリを/appに設定
// コメント。
WORKDIR /app
// 以降のコマンドを実行するための作業ディレクトリを`/app`に指定します。

// 空行。
# ビルドステージ（ステージ1）で生成されたJARファイルをapp.jarとしてコピー
// コメント。
COPY --from=build /app/target/*.jar app.jar
// `build`ステージで作成された`/app/target/`配下のJARファイルを、現在のイメージの`app.jar`という名前でコピーします。

// 空行。
# Spring Bootのデフォルトポート(8080)を公開することを明示
// コメント。
EXPOSE 8080
// コンテナがポート8080で通信待ち受けをすることを、Dockerシステム側に明示します。

// 空行。
# コンテナ起動時に実行されるコマンド（Javaアプリケーションを起動）
// コメント。
ENTRYPOINT ["java", "-jar", "app.jar"]
// コンテナ起動時のデフォルトコマンドとして、`java -jar app.jar`を実行するように指定します。
```

frontend/Dockerfile
```
# ベースイメージとして軽量なAlpine LinuxベースのNode.js 18を使用
// コメント。
FROM node:18-alpine
// ベースイメージとして、Node.js 18がインストール済みのAlpine Linuxイメージを使用します。

// 空行。
# コンテナ内の作業ディレクトリを/appに設定
// コメント。
WORKDIR /app
// 以降のコマンドを実行するための作業ディレクトリを`/app`に指定します。

// 空行。
# パッケージ管理ファイル（package.json, package-lock.json等）をコピー
// コメント。
COPY package*.json ./
// `package.json` と `package-lock.json`（存在する場合）を、コンテナの現在の作業ディレクトリにコピーします。

// 空行。
# 依存関係パッケージ（node_modules）をインストール
// コメント。
RUN npm install
// コンテナ内で`npm install`を実行し、依存パッケージをダウンロード・インストールします。

// 空行。
# 残りのすべてのフロントエンドのソースコードをコピー
// コメント。
COPY . .
// ホスト側の現在のディレクトリのすべてのファイルを、コンテナの作業ディレクトリにコピーします（ただし`docker-compose.yml`側のマウントで上書きされる前提の開発用設定）。

// 空行。
# Vite開発サーバーのデフォルトポート(5173)を公開することを明示
// コメント。
EXPOSE 5173
// コンテナがポート5173で通信待ち受けをすることを明示します。

// 空行。
# コンテナ起動時にVite開発サーバーを起動するコマンド
// コメント。
# --host オプションを付けることで、コンテナ外部（ホストPC等）からのアクセスを許可する
// コメント。
CMD ["npm", "run", "dev", "--", "--host"]
// コンテナ起動時に実行するコマンドとして、`npm run dev -- --host`を指定し、外部アクセス可能な状態でViteサーバーを立ち上げます。
```

frontend/package.json
```
{
// JSONオブジェクトの開始中括弧。
"name": "frontend",
// プロジェクトの名前を`"frontend"`と定義します。
"private": true,
// プロジェクトを非公開（npmリポジトリに誤って公開されない）に設定します。
"version": "0.0.0",
// プロジェクトの初期バージョンを`"0.0.0"`とします。
"type": "module",
// Node.jsのモジュールシステムとしてES Modules (`import`/`export`構文) を使うことを明記します。
"scripts": {
// `npm run <script-name>` で実行できるコマンド（スクリプト）の定義を開始します。
"dev": "vite",
// `dev` スクリプトを実行すると、`vite` コマンドが実行され、開発サーバーが立ち上がります。
"build": "tsc -b && vite build",
// `build` スクリプトを実行すると、TypeScriptのコンパイル(`tsc -b`)を行った後、Viteによる本番向けビルド(`vite build`)が走ります。
"lint": "oxlint",
// `lint` スクリプトを実行すると、高速なリンターである `oxlint` が実行されます。
"preview": "vite preview"
// `preview` スクリプトを実行すると、ビルド後のファイルを確認するためのViteのプレビューサーバーが起動します。
},
// `scripts` オブジェクトの終了。
"dependencies": {
// アプリケーションの実行（本番環境）に必要なライブラリ群の定義を開始します。
"react": "^19.2.7",
// `react` ライブラリ（バージョン19.2.7以上で、メジャーバージョンが変わらない範囲の最新版）を指定します。
"react-dom": "^19.2.7"
// `react-dom` ライブラリ（DOMレンダリング用）のバージョンを指定します。
},
// `dependencies` オブジェクトの終了。
"devDependencies": {
// 開発環境でのみ必要なライブラリ（型定義ファイルやビルドツール）の定義を開始します。
"@types/node": "^24.13.2",
// Node.jsのAPIに対するTypeScriptの型定義ファイルを指定します。
"@types/react": "^19.2.17",
// ReactのAPIに対するTypeScriptの型定義ファイルを指定します。
"@types/react-dom": "^19.2.3",
// React DOMのAPIに対するTypeScriptの型定義ファイルを指定します。
"@vitejs/plugin-react": "^6.0.2",
// ViteでReactを扱うための公式プラグインを指定します。
"oxlint": "^1.69.0",
// 高速な静的解析ツール(Linter)である`oxlint`を指定します。
"typescript": "~6.0.2",
// TypeScriptコンパイラを指定します。
"vite": "^8.1.0"
// Viteビルドツール本体を指定します。
}
// `devDependencies` オブジェクトの終了。
}
// JSONオブジェクトの終了中括弧。
```

frontend/vite.config.ts
```
import { defineConfig } from 'vite'
// `vite` パッケージから、設定オブジェクトの型推論を助ける関数 `defineConfig` をインポートします。
import react from '@vitejs/plugin-react'
// `@vitejs/plugin-react` から、React用のViteプラグインをインポートします。

// 空行。
// Viteの公式ドキュメントへのリンク
// コメント。
// https://vite.dev/config/
// コメント。

// 空行。
// Viteの開発・ビルド設定をエクスポートする
// コメント。
export default defineConfig({
// `defineConfig` を呼び出し、その結果（Viteの設定オブジェクト）をデフォルトエクスポートします。
// Viteで使用するプラグインの配列
// コメント。
// react()プラグインを有効化することで、JSXの変換やReactの高速なHMR(ホットモジュールリプレイスメント)が利用可能になる
// コメント。
plugins: [react()],
// `plugins` プロパティに、先ほどインポートした `react()` プラグインを実行した結果を配列に入れて設定します。
})
// `defineConfig` に渡すオブジェクトと関数の終了中括弧・括弧。
```

backend/pom.xml
```
<?xml version="1.0" encoding="UTF-8"?>
// XML宣言。バージョン1.0で文字エンコーディングがUTF-8であることを示します。
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
// Mavenプロジェクトのルート要素 `<project>` を開始し、XML名前空間を指定します。
xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
// XMLスキーマの場所を指定します。
<modelVersion>4.0.0</modelVersion>
// POMのモデルバージョンを指定します（現在は常に4.0.0）。
<parent>
// 親プロジェクトの定義 `<parent>` を開始します。
<groupId>org.springframework.boot</groupId>
// 親プロジェクトの `groupId` を指定します。
<artifactId>spring-boot-starter-parent</artifactId>
// 親プロジェクトの `artifactId` に `spring-boot-starter-parent` を指定します（Spring Boot標準の依存ライブラリバージョン管理を継承します）。
<version>3.2.0</version>
// Spring Bootのバージョン `3.2.0` を指定します。
<relativePath/> <!-- lookup parent from repository -->
// ローカルパスからではなく、リモートリポジトリから親POMを探すように指定します。
</parent>
// 親プロジェクトの定義終了。
<groupId>com.npbpredict</groupId>
// 当プロジェクトの `groupId`（一意の識別子）を `com.npbpredict` と定義します。
<artifactId>app</artifactId>
// 当プロジェクトの `artifactId`（プロジェクト名）を `app` と定義します。
<version>0.0.1-SNAPSHOT</version>
// 当プロジェクトのバージョンを `0.0.1-SNAPSHOT` と定義します。
<name>NpbPredict App</name>
// プロジェクトの表示名（人間が読む用）を定義します。
<description>NPB Prediction Community Site Backend</description>
// プロジェクトの説明文を定義します。
<properties>
// Mavenのカスタムプロパティの定義を開始します。
<java.version>17</java.version>
// プロパティ `java.version` に `17` を設定し、コンパイル時等に使用するJavaのバージョンを指定します。
</properties>
// プロパティ定義の終了。
<dependencies>
// 依存ライブラリ `<dependencies>` の定義を開始します。
<!-- REST API（コントローラー等）を作成するためのSpring Web機能 -->
// コメント。
<dependency>
// 依存ライブラリ定義開始。
<groupId>org.springframework.boot</groupId>
// `groupId`。
<artifactId>spring-boot-starter-web</artifactId>
// `artifactId` が `spring-boot-starter-web` (REST APIなどのWeb機能群)。
</dependency>
// 依存ライブラリ定義終了。

// 空行。
<!-- データベース(PostgreSQL)へアクセスするためのJPA/Hibernate機能 -->
// コメント。
<dependency>
// 依存ライブラリ定義開始。
<groupId>org.springframework.boot</groupId>
// `groupId`。
<artifactId>spring-boot-starter-data-jpa</artifactId>
// `artifactId` が `spring-boot-starter-data-jpa` (データベースアクセス機能)。
</dependency>
// 依存ライブラリ定義終了。

// 空行。
<!-- アプリケーションを保護するためのSpring Security -->
// コメント。
<dependency>
// 依存ライブラリ定義開始。
<groupId>org.springframework.boot</groupId>
// `groupId`。
<artifactId>spring-boot-starter-security</artifactId>
// `artifactId` が `spring-boot-starter-security` (認証・認可機能)。
</dependency>
// 依存ライブラリ定義終了。

// 空行。
<!-- Googleアカウント等を利用したOAuth2ログインを実装するための機能 -->
// コメント。
<dependency>
// 依存ライブラリ定義開始。
<groupId>org.springframework.boot</groupId>
// `groupId`。
<artifactId>spring-boot-starter-oauth2-client</artifactId>
// `artifactId` が `spring-boot-starter-oauth2-client` (OAuth2クライアント機能)。
</dependency>
// 依存ライブラリ定義終了。

// 空行。
<!-- PostgreSQLデータベースに接続するための公式JDBCドライバー -->
// コメント。
<dependency>
// 依存ライブラリ定義開始。
<groupId>org.postgresql</groupId>
// `groupId`。
<artifactId>postgresql</artifactId>
// `artifactId` が `postgresql` (PostgreSQL接続ドライバ)。
<scope>runtime</scope>
// `scope` が `runtime` (コンパイル時には不要で、実行時にのみ必要)。
</dependency>
// 依存ライブラリ定義終了。

// 空行。
<!-- Getter/Setterなどの定型コードを自動生成してくれるLombok -->
// コメント。
<dependency>
// 依存ライブラリ定義開始。
<groupId>org.projectlombok</groupId>
// `groupId`。
<artifactId>lombok</artifactId>
// `artifactId` が `lombok` (ボイラープレート削減ツール)。
<optional>true</optional>
// `optional` を `true` にし、このライブラリが他のプロジェクトに推移的に依存しないよう設定します。
</dependency>
// 依存ライブラリ定義終了。

// 空行。
<!-- Test -->
// コメント。
<dependency>
// 依存ライブラリ定義開始。
<groupId>org.springframework.boot</groupId>
// `groupId`。
<artifactId>spring-boot-starter-test</artifactId>
// `artifactId` が `spring-boot-starter-test` (テストフレームワーク群)。
<scope>test</scope>
// `scope` が `test` (テスト時にのみ必要)。
</dependency>
// 依存ライブラリ定義終了。
<dependency>
// 依存ライブラリ定義開始。
<groupId>org.springframework.security</groupId>
// `groupId`。
<artifactId>spring-security-test</artifactId>
// `artifactId` が `spring-security-test` (セキュリティのテスト機能)。
<scope>test</scope>
// `scope` が `test`。
</dependency>
// 依存ライブラリ定義終了。
</dependencies>
// 依存ライブラリ一覧 `<dependencies>` の終了。

// 空行。
<build>
// ビルドプロセスの設定 `<build>` の開始。
<plugins>
// ビルドプラグイン `<plugins>` の開始。
<plugin>
// プラグイン `<plugin>` の開始。
<groupId>org.springframework.boot</groupId>
// `groupId`。
<artifactId>spring-boot-maven-plugin</artifactId>
// `artifactId` が `spring-boot-maven-plugin` (Spring Bootアプリケーションをパッキングするプラグイン)。
<configuration>
// プラグインの設定 `<configuration>` の開始。
<excludes>
// 除外設定 `<excludes>` の開始。
<exclude>
// 除外対象 `<exclude>` の開始。
<groupId>org.projectlombok</groupId>
// `groupId`。
<artifactId>lombok</artifactId>
// `artifactId` が `lombok`。これにより、最終的な実行用JARファイルにLombokのコードが含まれず、容量を削減します。
</exclude>
// 除外対象の終了。
</excludes>
// 除外設定の終了。
</configuration>
// 設定の終了。
</plugin>
// プラグインの終了。
</plugins>
// プラグイン一覧の終了。
</build>
// ビルド設定の終了。

// 空行。
</project>
// Mavenプロジェクト要素 `<project>` の終了。
```

backend/src/main/resources/application.yml
```
# Spring Bootアプリケーション全体の設定ファイル
// コメント。
spring:
// Spring frameworkのコア設定プロパティ群を開始します。
# データベース接続に関する設定
// コメント。
datasource:
// データソース（データベース接続）に関する設定を開始します。
# 接続先URL。docker-composeで設定したサービス名 'postgres' をホストとして指定。
// コメント。
url: jdbc:postgresql://postgres:5432/npb_prediction
// データベース接続文字列(URL)を設定します。Dockerのネットワークにより `postgres` というホスト名で接続できます。
# データベースにアクセスするためのユーザー名
// コメント。
username: npb_user
// データベースのユーザー名を設定します。
# データベースにアクセスするためのパスワード
// コメント。
password: npb_password
// データベースのパスワードを設定します。
# 使用するJDBCドライバーのクラス名
// コメント。
driver-class-name: org.postgresql.Driver
// PostgreSQLのJDBCドライバクラスを明示的に指定します。

// 空行。
# JPA (Java Persistence API) と Hibernate の設定
// コメント。
jpa:
// JPA(Java Persistence API)の設定を開始します。
hibernate:
// JPAの実装であるHibernate固有の設定を開始します。
# アプリケーション起動時に、Entityクラスの定義からDBのテーブル構造を自動で更新(update)する
// コメント。
ddl-auto: update
// アプリ起動時に、DBのスキーマをJavaコード（Entity）に合わせて自動更新（`update`）するよう設定します。
# 実行されたSQL文をコンソールに表示する設定
// コメント。
show-sql: true
// Hibernateが生成・実行したSQL文をログに出力する設定を有効(`true`)にします。
properties:
// Hibernateのより詳細なプロパティ設定を開始します。
hibernate:
// `hibernate`プレフィックスを持つプロパティ。
# コンソールに出力されるSQLを整形（フォーマット）して読みやすくする
// コメント。
format_sql: true
// ログに出力されるSQL文を改行・インデントして読みやすくフォーマットする設定を有効(`true`)にします。
# PostgreSQLに特化したSQLを生成するためのDialect（方言）を指定
// コメント。
dialect: org.hibernate.dialect.PostgreSQLDialect
// Hibernateが生成するSQLの方言としてPostgreSQL用のDialectを指定します。

// 空行。
# セキュリティ (OAuth2ログイン) の設定
// コメント。
security:
// セキュリティ関連の設定を開始します。
oauth2:
// OAuth2機能の設定を開始します。
client:
// OAuth2クライアントとしての設定を開始します。
registration:
// 各プロバイダ（Googleなど）の登録情報設定を開始します。
# Googleを使用したOAuth2ログインの登録情報
// コメント。
google:
// プロバイダ `google` 用の設定を開始します。
# Google Cloud Consoleで取得したクライアントID (プレースホルダー)
// コメント。
client-id: YOUR_GOOGLE_CLIENT_ID
// Googleログイン用のクライアントIDを設定します（プレースホルダー）。
# Google Cloud Consoleで取得したクライアントシークレット (プレースホルダー)
// コメント。
client-secret: YOUR_GOOGLE_CLIENT_SECRET
// Googleログイン用のクライアントシークレットを設定します（プレースホルダー）。
# 取得する情報の範囲（プロフィール情報とメールアドレス）
// コメント。
scope: profile, email
// 認証時にGoogleから取得を要求する情報（スコープ）を `profile` と `email` に指定します。

// 空行。
# サーバーに関する設定
// コメント。
server:
// Tomcat内蔵サーバーの起動設定を開始します。
# Spring Bootアプリケーションが起動してリクエストを待ち受けるポート番号
// コメント。
port: 8080
// サーバーがリッスンするポートを `8080` に設定します。
```

backend/src/main/java/com/npbpredict/app/entity/User.java
```
package com.npbpredict.app.entity;
// このファイルが所属するパッケージ（ディレクトリ構造）を宣言します。

// 空行。
import jakarta.persistence.*;
// JPA(Java Persistence API)のアノテーション等（Entity, Table, Idなど）を一括でインポートします。
import lombok.Getter;
// Lombokの `@Getter` アノテーションをインポートします。
import lombok.Setter;
// Lombokの `@Setter` アノテーションをインポートします。
import lombok.NoArgsConstructor;
// Lombokの `@NoArgsConstructor` アノテーションをインポートします。
import lombok.AllArgsConstructor;
// Lombokの `@AllArgsConstructor` アノテーションをインポートします。

// 空行。
/**
// Javadocコメント。このクラスがユーザー情報を表すEntityであることを説明します。
* ユーザー情報を表すEntityクラス。
// Javadocコメント。このクラスがユーザー情報を表すEntityであることを説明します。
* DBの "users" テーブルと紐付きます。
// Javadocコメント。このクラスがユーザー情報を表すEntityであることを説明します。
*/
// Javadocコメント。このクラスがユーザー情報を表すEntityであることを説明します。
@Entity // このクラスがJPAのEntityであることを示します
// このクラスがDBのテーブルとマッピングされるJPAエンティティであることを指定する `@Entity` アノテーション。
@Table(name = "users") // マッピングするテーブル名を "users" に指定します
// このエンティティがマッピングされるDB上のテーブル名を `"users"` と指定する `@Table` アノテーション。
@Getter // Lombok: すべてのフィールドのgetterを自動生成します
// クラス内の全フィールドに対するGetterメソッドをコンパイル時に自動生成する `@Getter` アノテーション。
@Setter // Lombok: すべてのフィールドのsetterを自動生成します
// クラス内の全フィールドに対するSetterメソッドを自動生成する `@Setter` アノテーション。
@NoArgsConstructor // Lombok: 引数なしのデフォルトコンストラクタを自動生成します
// 引数のないデフォルトコンストラクタを自動生成する `@NoArgsConstructor` アノテーション（JPAでは必須）。
@AllArgsConstructor // Lombok: 全フィールドを引数に取るコンストラクタを自動生成します
// すべてのフィールドを引数に取るコンストラクタを自動生成する `@AllArgsConstructor` アノテーション。
public class User {
// `User` クラスの定義を開始します。

// 空行。
/**
// Javadocコメント。IDフィールドの説明。
* 内部ユーザーID（プライマリキー）。
// Javadocコメント。IDフィールドの説明。
* データベース側で自動的に連番が振られます(GenerationType.IDENTITY)。
// Javadocコメント。IDフィールドの説明。
*/
// Javadocコメント。IDフィールドの説明。
@Id
// フィールド `id` がこのエンティティの主キー(PK)であることを示す `@Id` アノテーション。
@GeneratedValue(strategy = GenerationType.IDENTITY)
// 主キーの値がデータベースの自動増分カラム（連番）によって生成されることを示す `@GeneratedValue` アノテーション。
private Long id;
// 内部ユーザーIDを保持する `Long` 型のフィールド変数 `id`。

// 空行。
/**
// Javadocコメント。Googleの識別子フィールドの説明。
* Google OAuth2認証時に取得する一意の識別子。
// Javadocコメント。Googleの識別子フィールドの説明。
* 重複を許さず(unique = true)、必須項目(nullable = false)とします。
// Javadocコメント。Googleの識別子フィールドの説明。
*/
// Javadocコメント。Googleの識別子フィールドの説明。
@Column(name = "google_sub_id", unique = true, nullable = false)
// テーブル上のカラム名を `google_sub_id`、一意制約(`unique=true`)、非Null制約(`nullable=false`)とする `@Column` アノテーション。
private String googleSubId;
// Google認証の一意なIDを保持する `String` 型のフィールド変数 `googleSubId`。

// 空行。
/**
// Javadocコメント。ニックネームフィールドの説明。
* アプリケーション内で表示されるユーザーのニックネーム。
// Javadocコメント。ニックネームフィールドの説明。
* 重複を許さず、必須項目とします。
// Javadocコメント。ニックネームフィールドの説明。
*/
// Javadocコメント。ニックネームフィールドの説明。
@Column(unique = true, nullable = false)
// 一意制約(`unique=true`)と非Null制約(`nullable=false`)を持つ `@Column` アノテーション（カラム名はフィールド名から自動推定され `nickname` となる）。
private String nickname;
// ユーザーのニックネームを保持する `String` 型のフィールド変数 `nickname`。

// 空行。
/**
// Javadocコメント。お気に入り球団フィールドの説明。
* ユーザーのお気に入り球団。
// Javadocコメント。お気に入り球団フィールドの説明。
*/
// Javadocコメント。お気に入り球団フィールドの説明。
@Column(name = "favorite_team")
// カラム名を `favorite_team` とする `@Column` アノテーション。
private String favoriteTeam;
// お気に入り球団の名前を保持する `String` 型のフィールド変数 `favoriteTeam`。

// 空行。
/**
// Javadocコメント。累計ポイントフィールドの説明。
* ユーザーが獲得した累計ポイント。
// Javadocコメント。累計ポイントフィールドの説明。
* デフォルト値は0で、必須項目です。
// Javadocコメント。累計ポイントフィールドの説明。
*/
// Javadocコメント。累計ポイントフィールドの説明。
@Column(name = "total_points", nullable = false)
// カラム名を `total_points`、非Null制約とする `@Column` アノテーション。
private Integer totalPoints = 0;
// ユーザーのポイントを保持する `Integer` 型のフィールド変数 `totalPoints`。初期値を `0` に設定しています。
}
// クラス定義の終了中括弧。
```

backend/src/main/java/com/npbpredict/app/entity/Game.java
```
package com.npbpredict.app.entity;
// パッケージの宣言。

// 空行。
import jakarta.persistence.*;
// JPAのアノテーション群のインポート。
import lombok.Getter;
// Lombok関連のアノテーションをインポート。
import lombok.Setter;
// Lombok関連のアノテーションをインポート。
import lombok.NoArgsConstructor;
// Lombok関連のアノテーションをインポート。
import lombok.AllArgsConstructor;
// Lombok関連のアノテーションをインポート。
import java.time.LocalDate;
// 日付のみを扱う `LocalDate` クラスをインポート。
import java.time.LocalDateTime;
// 日付と時刻を扱う `LocalDateTime` クラスをインポート。

// 空行。
/**
// Javadocコメント。
* 試合情報を表すEntityクラス。
// Javadocコメント。
* DBの "games" テーブルと紐付きます。
// Javadocコメント。
*/
// Javadocコメント。
@Entity // このクラスがJPAのEntityであることを示します
// JPAエンティティであることを示す `@Entity`。
@Table(name = "games") // マッピングするテーブル名を "games" に指定します
// DB上のテーブル名を `games` に指定する `@Table`。
@Getter // Lombok: getterを自動生成
// Lombokによるボイラープレート自動生成の設定群。
@Setter // Lombok: setterを自動生成
// Lombokによるボイラープレート自動生成の設定群。
@NoArgsConstructor // Lombok: デフォルトコンストラクタを生成
// Lombokによるボイラープレート自動生成の設定群。
@AllArgsConstructor // Lombok: 全引数コンストラクタを生成
// Lombokによるボイラープレート自動生成の設定群。
public class Game {
// `Game` クラスの定義開始。

// 空行。
/**
// Javadocコメント。
* 試合ID（プライマリキー）。自動採番されます。
// Javadocコメント。
*/
// Javadocコメント。
@Id
// 主キーを示す `@Id`。
@GeneratedValue(strategy = GenerationType.IDENTITY)
// 連番により自動生成されることを示す `@GeneratedValue`。
private Long id;
// 試合の内部IDを保持する `Long` 型フィールド `id`。

// 空行。
/**
// Javadocコメント。
* 試合が開催される日付。
// Javadocコメント。
*/
// Javadocコメント。
@Column(name = "game_date", nullable = false)
// カラム名 `game_date`、非Nullとする `@Column`。
private LocalDate gameDate;
// 試合日を保持する `LocalDate` 型フィールド `gameDate`。

// 空行。
/**
// Javadocコメント。
* 試合開始時刻。この時刻を過ぎると予想の投票・変更が締め切られます。
// Javadocコメント。
*/
// Javadocコメント。
@Column(name = "start_time", nullable = false)
// カラム名 `start_time`、非Nullとする `@Column`。
private LocalDateTime startTime;
// 試合開始時刻を保持する `LocalDateTime` 型フィールド `startTime`。

// 空行。
/**
// Javadocコメント。
* ホームチームの名前。
// Javadocコメント。
*/
// Javadocコメント。
@Column(name = "home_team", nullable = false)
// カラム名 `home_team`、非Nullとする `@Column`。
private String homeTeam;
// ホームチーム名を保持する `String` 型フィールド `homeTeam`。

// 空行。
/**
// Javadocコメント。
* ビジター（アウェイ）チームの名前。
// Javadocコメント。
*/
// Javadocコメント。
@Column(name = "away_team", nullable = false)
// カラム名 `away_team`、非Nullとする `@Column`。
private String awayTeam;
// アウェイチーム名を保持する `String` 型フィールド `awayTeam`。

// 空行。
/**
// Javadocコメント。
* 試合の現在の状態（試合前、投票締切後、試合終了）。
// Javadocコメント。
* 列挙型(Enum)の文字列表現としてデータベースに保存します。
// Javadocコメント。
* デフォルトは BEFORE_GAME（試合前）です。
// Javadocコメント。
*/
// Javadocコメント。
@Enumerated(EnumType.STRING)
// Enum（列挙型）の値を、DBに保存する際「数値(0,1,2..)」ではなく「文字列表現(文字列の"BEFORE_GAME"等)」として保存するよう指示する `@Enumerated(EnumType.STRING)` アノテーション。
@Column(nullable = false)
// 非Null制約を示す `@Column`。
private GameStatus status = GameStatus.BEFORE_GAME;
// 試合のステータスを保持する `GameStatus` 型のフィールド。初期値を `BEFORE_GAME` に設定しています。

// 空行。
/**
// Javadocコメント。
* 試合結果（ホーム勝ち、アウェイ勝ち、引き分け）。
// Javadocコメント。
* 試合終了後に管理者が入力するまで null になります。
// Javadocコメント。
*/
// Javadocコメント。
@Enumerated(EnumType.STRING)
// 文字列表現でEnumをDB保存する `@Enumerated(EnumType.STRING)`。
private GameResult result;
// 試合結果を保持する `GameResult` 型のフィールド。

// 空行。
/**
// Javadocコメント。
* 試合の状態を表す列挙型
// Javadocコメント。
*/
// Javadocコメント。
public enum GameStatus {
// 試合ステータスを表すインナーEnum（列挙型） `GameStatus` の定義開始。
BEFORE_GAME,   // 試合前（投票受付中）
// `BEFORE_GAME` (試合前) の列挙子。
VOTING_CLOSED, // 投票締切後（試合開始〜終了前）
// `VOTING_CLOSED` (投票締切後) の列挙子。
GAME_END       // 試合終了（結果確定）
// `GAME_END` (試合終了) の列挙子。
}
// Enum `GameStatus` の定義終了。

// 空行。
/**
// Javadocコメント。
* 試合結果を表す列挙型
// Javadocコメント。
*/
// Javadocコメント。
public enum GameResult {
// 試合結果を表すインナーEnum `GameResult` の定義開始。
HOME, // ホームチームの勝利
// `HOME` (ホーム勝利) の列挙子。
AWAY, // アウェイチームの勝利
// `AWAY` (アウェイ勝利) の列挙子。
DRAW  // 引き分け
// `DRAW` (引き分け) の列挙子。
}
// Enum `GameResult` の定義終了。
}
// `Game` クラスの定義終了。
```

backend/src/main/java/com/npbpredict/app/entity/Prediction.java
```
package com.npbpredict.app.entity;
// パッケージの宣言。

// 空行。
import jakarta.persistence.*;
// JPAアノテーション群のインポート。
import lombok.Getter;
// Lombok関連アノテーションのインポート。
import lombok.Setter;
// Lombok関連アノテーションのインポート。
import lombok.NoArgsConstructor;
// Lombok関連アノテーションのインポート。
import lombok.AllArgsConstructor;
// Lombok関連アノテーションのインポート。

// 空行。
/**
// Javadocコメント。
* ユーザーの予想データを表すEntityクラス。
// Javadocコメント。
* DBの "predictions" テーブルと紐付きます。
// Javadocコメント。
*/
// Javadocコメント。
@Entity
// JPAエンティティであることを示す `@Entity`。
@Table(name = "predictions")
// マッピングするテーブル名を `predictions` とする `@Table`。
@Getter // Lombok: getterを自動生成
// Lombokによるボイラープレート自動生成の設定群。
@Setter // Lombok: setterを自動生成
// Lombokによるボイラープレート自動生成の設定群。
@NoArgsConstructor // Lombok: デフォルトコンストラクタを生成
// Lombokによるボイラープレート自動生成の設定群。
@AllArgsConstructor // Lombok: 全引数コンストラクタを生成
// Lombokによるボイラープレート自動生成の設定群。
public class Prediction {
// `Prediction` クラスの定義開始。

// 空行。
/**
// Javadocコメント。
* 予想ID（プライマリキー）。自動採番されます。
// Javadocコメント。
*/
// Javadocコメント。
@Id
// 主キーを示す `@Id`。
@GeneratedValue(strategy = GenerationType.IDENTITY)
// 自動採番を示す `@GeneratedValue`。
private Long id;
// 内部IDを保持する `Long` 型フィールド `id`。

// 空行。
/**
// Javadocコメント。ユーザーとのリレーションを説明。
* 予想を行ったユーザーへの参照（外部キー）。
// Javadocコメント。ユーザーとのリレーションを説明。
* 遅延ロード(FetchType.LAZY)を指定し、必要な時だけユーザー情報を取得します。
// Javadocコメント。ユーザーとのリレーションを説明。
*/
// Javadocコメント。ユーザーとのリレーションを説明。
@ManyToOne(fetch = FetchType.LAZY)
// 「多対一」のリレーションを示す `@ManyToOne`。`FetchType.LAZY` は、`Prediction` を取得した際に `User` の情報をDBから即座には引かず、`getUser()` が呼ばれたタイミングで初めてSQLを実行する設定（パフォーマンス向上策）です。
@JoinColumn(name = "user_id", nullable = false)
// 外部キーのカラム名を `user_id` とし、非Nullとする `@JoinColumn` アノテーション。
private User user;
// この予想を行ったユーザーを表す `User` 型フィールド `user`。

// 空行。
/**
// Javadocコメント。試合とのリレーションを説明。
* 予想対象の試合への参照（外部キー）。
// Javadocコメント。試合とのリレーションを説明。
* 遅延ロード(FetchType.LAZY)を指定します。
// Javadocコメント。試合とのリレーションを説明。
*/
// Javadocコメント。試合とのリレーションを説明。
@ManyToOne(fetch = FetchType.LAZY)
// 試合に対する「多対一」のリレーションを示す `@ManyToOne`。遅延ロード設定。
@JoinColumn(name = "game_id", nullable = false)
// 外部キーのカラム名を `game_id` とし、非Nullとする `@JoinColumn`。
private Game game;
// 対象の試合を表す `Game` 型フィールド `game`。

// 空行。
/**
// Javadocコメント。予想内容の説明。
* ユーザーが予想した勝者（ホーム、アウェイ、引き分け）。
// Javadocコメント。予想内容の説明。
* Game.GameResult 列挙型の文字列表現として保存されます。
// Javadocコメント。予想内容の説明。
*/
// Javadocコメント。予想内容の説明。
@Enumerated(EnumType.STRING)
// Enumの文字列表現保存を示す `@Enumerated`。
@Column(name = "predicted_winner", nullable = false)
// カラム名を `predicted_winner` とし非Nullとする `@Column`。
private Game.GameResult predictedWinner;
// 予想した勝敗を表す `Game.GameResult` 型フィールド `predictedWinner`。

// 空行。
/**
// Javadocコメント。結果フラグの説明。
* 予想が的中したかどうかを示すフラグ。
// Javadocコメント。結果フラグの説明。
* 試合結果が確定するまでは null になります。
// Javadocコメント。結果フラグの説明。
*/
// Javadocコメント。結果フラグの説明。
@Column(name = "is_correct")
// カラム名を `is_correct` とする `@Column`。
private Boolean isCorrect;
// 予想が当たったかを示す `Boolean` 型フィールド `isCorrect`（nullを取り得るためプリミティブ型の `boolean` ではなくラッパークラス）。
}
// クラス定義終了。
```

backend/src/main/java/com/npbpredict/app/controller/GameController.java
```
package com.npbpredict.app.controller;
// パッケージの宣言。

// 空行。
import com.npbpredict.app.entity.Game;
// 自作のEntityクラスやRepositoryインターフェースをインポート。
import com.npbpredict.app.entity.Prediction;
// 自作のEntityクラスやRepositoryインターフェースをインポート。
import com.npbpredict.app.repository.GameRepository;
// 自作のEntityクラスやRepositoryインターフェースをインポート。
import com.npbpredict.app.repository.PredictionRepository;
// 自作のEntityクラスやRepositoryインターフェースをインポート。
import org.springframework.beans.factory.annotation.Autowired;
// Spring Web関連のアノテーション（`@Autowired`, `@RestController` など）や例外クラスをインポート。
import org.springframework.http.HttpStatus;
// Spring Web関連のアノテーション（`@Autowired`, `@RestController` など）や例外クラスをインポート。
import org.springframework.http.ResponseEntity;
// Spring Web関連のアノテーション（`@Autowired`, `@RestController` など）や例外クラスをインポート。
import org.springframework.web.bind.annotation.*;
// Spring Web関連のアノテーション（`@Autowired`, `@RestController` など）や例外クラスをインポート。
import org.springframework.web.server.ResponseStatusException;
// Spring Web関連のアノテーション（`@Autowired`, `@RestController` など）や例外クラスをインポート。

// 空行。
import java.time.LocalDateTime;
// 日付処理用のクラス群や、リスト操作、Stream APIなどの標準Javaクラスをインポート。
import java.time.ZoneId;
// 日付処理用のクラス群や、リスト操作、Stream APIなどの標準Javaクラスをインポート。
import java.util.HashMap;
// 日付処理用のクラス群や、リスト操作、Stream APIなどの標準Javaクラスをインポート。
import java.util.List;
// 日付処理用のクラス群や、リスト操作、Stream APIなどの標準Javaクラスをインポート。
import java.util.Map;
// 日付処理用のクラス群や、リスト操作、Stream APIなどの標準Javaクラスをインポート。
import java.util.stream.Collectors;
// 日付処理用のクラス群や、リスト操作、Stream APIなどの標準Javaクラスをインポート。

// 空行。
/**
// Javadocコメント。
* 試合に関するリクエストを処理するREST APIコントローラー。
// Javadocコメント。
* 全てのエンドポイントは /api/games をベースにします。
// Javadocコメント。
*/
// Javadocコメント。
@RestController // JSONレスポンスを返すControllerであることを示します
// このクラスがREST APIのエンドポイント（JSONを返す）であることを示す `@RestController`。
@RequestMapping("/api/games")
// クラス全体に対するベースURLを `/api/games` に設定する `@RequestMapping`。
public class GameController {
// `GameController` クラスの定義開始。

// 空行。
// Gameデータのデータベース操作を行うためのリポジトリを自動で注入(DI)します
// コメント。
@Autowired
// SpringのDI（依存性の注入）を利用してリポジトリのインスタンスを注入させる `@Autowired`。
private GameRepository gameRepository;
// DBから試合情報を取得するための `GameRepository` フィールド。

// 空行。
// 予想データのデータベース操作を行うためのリポジトリを自動で注入(DI)します
// コメント。
@Autowired
// `@Autowired`。
private PredictionRepository predictionRepository;
// 予想情報を取得するための `PredictionRepository` フィールド。

// 空行。
/**
// Javadocコメント。オッズ取得APIの説明。
* 試合開始前（または後）のオッズ（投票比率）を取得するAPI。
// Javadocコメント。オッズ取得APIの説明。
* ユーザー個別の予想は含まず、集計データのみを返します。
// Javadocコメント。オッズ取得APIの説明。
* URL: GET /api/games/{gameId}/odds
// Javadocコメント。オッズ取得APIの説明。
*/
// Javadocコメント。オッズ取得APIの説明。
@GetMapping("/{gameId}/odds")
// HTTP GETメソッドで URLパスが `/{gameId}/odds` へのリクエストを処理することを示す `@GetMapping`。
public ResponseEntity<Map<String, Object>> getGameOdds(@PathVariable Long gameId) {
// `getGameOdds` メソッド定義。URLパス内の `{gameId}` を引数 `gameId` として受け取ります(`@PathVariable`)。レスポンスはJSONオブジェクト(`Map`)を含んだHTTPレスポンス。
// 指定されたIDの試合が存在するか確認し、なければ404 NotFoundエラーを返します
// コメント。
Game game = gameRepository.findById(gameId)
// `gameRepository` を使ってIDで試合を検索します。
.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found"));
// もし見つからなければ、HTTPステータス `404 NOT_FOUND` を返す `ResponseStatusException` をスロー（エラー終了）します。

// 空行。
// その試合に対する全ユーザーの予想データを取得
// コメント。
List<Prediction> predictions = predictionRepository.findByGameId(gameId);
// `predictionRepository` を使い、その試合に関連するすべての予想レコードをリストとして取得します。

// 空行。
// ホーム、アウェイ、引き分けのそれぞれの投票数をカウント
// コメント。
long homeVotes = predictions.stream()
// 取得した予想リストからStream（パイプライン処理）を生成します。
.filter(p -> p.getPredictedWinner() == Game.GameResult.HOME)
// 予想結果が `HOME` のものだけをフィルター（抽出）します。
.count();
// その件数をカウントし、`homeVotes` に代入します。
long awayVotes = predictions.stream()
// 同様にして、`AWAY` と予想された件数をカウントし、`awayVotes` に代入します。
.filter(p -> p.getPredictedWinner() == Game.GameResult.AWAY)
// 同様にして、`AWAY` と予想された件数をカウントし、`awayVotes` に代入します。
.count();
// 同様にして、`AWAY` と予想された件数をカウントし、`awayVotes` に代入します。
long drawVotes = predictions.stream()
// 同様にして、`DRAW` と予想された件数をカウントし、`drawVotes` に代入します。
.filter(p -> p.getPredictedWinner() == Game.GameResult.DRAW)
// 同様にして、`DRAW` と予想された件数をカウントし、`drawVotes` に代入します。
.count();
// 同様にして、`DRAW` と予想された件数をカウントし、`drawVotes` に代入します。

// コメント。
// 総投票数を計算
// ホーム、アウェイ、引き分けの票数をすべて足して、総投票数 `totalVotes` を計算します。
long totalVotes = homeVotes + awayVotes + drawVotes;
// 空行。

// コメント。
// レスポンス用のMapを作成し、総投票数と比率(%)を計算して格納
// クライアントに返すJSONの元となる `HashMap` (キーが文字列、値が任意のオブジェクト) を生成します。
Map<String, Object> response = new HashMap<>();
// レスポンスマップに総投票数(`totalVotes`)を格納します。
response.put("totalVotes", totalVotes);
// もし総投票数が0より大きい場合（誰かが投票している場合）の分岐。
if (totalVotes > 0) {
// ホームの得票比率(%)を計算し、`homeRatio` として格納します。（`double`キャストによって小数の割り算を行います）。
response.put("homeRatio", (double) homeVotes / totalVotes * 100);
// アウェイの得票比率(%)を計算して格納します。
response.put("awayRatio", (double) awayVotes / totalVotes * 100);
// 引き分けの得票比率(%)を計算して格納します。
response.put("drawRatio", (double) drawVotes / totalVotes * 100);
// 誰も投票していない（0票）場合の `else` 分岐。
} else {
// エラー（ゼロ除算）を防ぐため、すべての比率に `0.0` を明示的に設定します。
response.put("homeRatio", 0.0);
// エラー（ゼロ除算）を防ぐため、すべての比率に `0.0` を明示的に設定します。
response.put("awayRatio", 0.0);
// エラー（ゼロ除算）を防ぐため、すべての比率に `0.0` を明示的に設定します。
response.put("drawRatio", 0.0);
// `if-else` ブロックの終了。
}
// 空行。

// コメント。
// HTTP 200 OK と共にJSON形式でデータを返却
// 作成したレスポンス用マップを、HTTPステータス `200 OK` とともに返却します。Springが自動的にJSONに変換してくれます。
return ResponseEntity.ok(response);
// `getGameOdds` メソッドの終了。
}
// 空行。

// Javadocコメント。投票内訳取得APIの説明。
/**
// Javadocコメント。投票内訳取得APIの説明。
* 試合開始後に、「誰がどちらに投票したか」の具体的な内訳を取得するAPI。
// Javadocコメント。投票内訳取得APIの説明。
* カンニング防止のため、試合開始前はアクセスが拒否されます。
// Javadocコメント。投票内訳取得APIの説明。
* URL: GET /api/games/{gameId}/predictions
// Javadocコメント。投票内訳取得APIの説明。
*/
// HTTP GETで `/{gameId}/predictions` へのリクエストを処理する `@GetMapping`。
@GetMapping("/{gameId}/predictions")
// `getGamePredictions` メソッド定義。返り値は、配列(List)の中にオブジェクト(Map)が入ったJSON構造です。
public ResponseEntity<List<Map<String, String>>> getGamePredictions(@PathVariable Long gameId) {
// コメント。
// 試合情報の取得
// 先ほどと同様に、IDから対象の試合を検索し、なければ404エラー。
Game game = gameRepository.findById(gameId)
// 先ほどと同様に、IDから対象の試合を検索し、なければ404エラー。
.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found"));
// 空行。

// コメント（カンニング防止）。
// 【カンニング防止ロジック】
// コメント。
// 日本時間(Asia/Tokyo)で現在時刻を取得し、試合開始前であれば 403 Forbidden エラーを返す
// 日本時間(`Asia/Tokyo`)での「現在時刻」を取得し、それが試合の「開始時刻」より前(`isBefore`)であるかを判定します。
if (LocalDateTime.now(ZoneId.of("Asia/Tokyo")).isBefore(game.getStartTime())) {
// 試合前であれば、HTTPステータス `403 FORBIDDEN` を返す例外をスローし、クライアントに情報を隠蔽します。
throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Predictions are hidden before the game starts.");
// `if`文の終了。
}
// 空行。

// コメント。
// 試合開始後であれば、ユーザー情報も含めて予想データをDBから取得
// 試合開始後なので、N+1問題を回避する独自クエリメソッド（`findByGameIdWithUser`）を呼び出し、予想一覧と「誰がしたか(User)」の情報を同時に取得します。
List<Prediction> predictions = predictionRepository.findByGameIdWithUser(gameId);
// 空行。

// コメント。
// ユーザー名(nickname)と予想内容(predictedWinner)のリストに変換して返す
// 取得した予想EntityのリストをStreamに変換し、`map` 操作によって別の形（この場合はレスポンス用の `Map`）に変換します。
List<Map<String, String>> response = predictions.stream().map(p -> {
// 個々の要素を処理するためのラムダ式の開始。空の `HashMap` を作ります。
Map<String, String> map = new HashMap<>();
// 予想データからUserエンティティをたどり、ニックネームを取得して `nickname` キーに格納します。
map.put("nickname", p.getUser().getNickname());
// 予想先(`HOME`等)のEnumを文字列(`.name()`)に変換し、`predictedWinner` キーに格納します。
map.put("predictedWinner", p.getPredictedWinner().name());
// 作成したMapをラムダ式の戻り値として返します。
return map;
// Streamの `map` 操作の終了と、結果を再び `List` としてまとめる `.collect(Collectors.toList())` を実行し、結果を `response` に代入します。
}).collect(Collectors.toList());
// 空行。

// 出来上がったリストを HTTPステータス `200 OK` で返却します。
return ResponseEntity.ok(response);
// `getGamePredictions` メソッドの終了。
}
// `GameController` クラスの終了。
}
// 空行
```

backend/src/main/java/com/npbpredict/app/repository/GameRepository.java
```
package com.npbpredict.app.repository;
// パッケージの宣言。

// 空行。
import com.npbpredict.app.entity.Game;
// EntityクラスおよびSpring Data JPA関連クラスのインポート。
import org.springframework.data.jpa.repository.JpaRepository;
// EntityクラスおよびSpring Data JPA関連クラスのインポート。
import org.springframework.stereotype.Repository;
// EntityクラスおよびSpring Data JPA関連クラスのインポート。

// 空行。
/**
// Javadocコメント。
* Gameエンティティに対するデータベース操作（CRUDなど）を提供するRepositoryインターフェース。
// Javadocコメント。
* Spring Data JPAにより、基本的なメソッドは自動的に実装されます。
// Javadocコメント。
*/
// Javadocコメント。
@Repository
// このインターフェースがSpringのRepository（データアクセス層のコンポーネント）であることを示す `@Repository` アノテーション。
public interface GameRepository extends JpaRepository<Game, Long> {
// `Game` エンティティ（主キーの型は `Long`）を扱うリポジトリとして、`JpaRepository` を継承します。これにより `save`, `findById`, `findAll` 等のSQL実装コードを書かずに使えるようになります。
}
// インターフェースの終了中括弧。独自メソッドを定義していないため中は空です。
```

backend/src/main/java/com/npbpredict/app/repository/PredictionRepository.java
```
package com.npbpredict.app.repository;
// パッケージの宣言。

// 空行。
import com.npbpredict.app.entity.Prediction;
// 必要なクラスやアノテーション(`@Query` など)のインポート。
import org.springframework.data.jpa.repository.JpaRepository;
// 必要なクラスやアノテーション(`@Query` など)のインポート。
import org.springframework.data.jpa.repository.Query;
// 必要なクラスやアノテーション(`@Query` など)のインポート。
import org.springframework.data.repository.query.Param;
// 必要なクラスやアノテーション(`@Query` など)のインポート。
import org.springframework.stereotype.Repository;
// 必要なクラスやアノテーション(`@Query` など)のインポート。

// 空行。
import java.util.List;
// Java標準の `List` をインポート。

// 空行。
/**
// Javadocコメント。
* Prediction(予想)エンティティに対するデータベース操作を提供するRepositoryインターフェース。
// Javadocコメント。
*/
// Javadocコメント。
@Repository
// `@Repository` アノテーション。
public interface PredictionRepository extends JpaRepository<Prediction, Long> {
// `Prediction` を扱うリポジトリインターフェースの宣言。

// 空行。
/**
// Javadocコメント。
* 指定された試合IDに関連するすべての予想を取得します。
// Javadocコメント。
* メソッドの命名規則により、Spring Data JPAが自動でクエリを生成します。
// Javadocコメント。
*/
// Javadocコメント。
List<Prediction> findByGameId(Long gameId);
// Spring Data JPAの「クエリメソッド（命名規則に従ってメソッド名を書くと自動でSQLになる機能）」。引数の `gameId` をキーに予想のリストを検索・取得します。

// 空行。
/**
// Javadocコメント。N+1問題回避の説明。
* 指定された試合IDに関連する予想と、その予想を行ったユーザー情報を一度に取得します。
// Javadocコメント。N+1問題回避の説明。
* JOIN FETCH を使用することで、N+1問題を回避し、パフォーマンスを最適化しています。
// Javadocコメント。N+1問題回避の説明。
*/
// Javadocコメント。N+1問題回避の説明。
@Query("SELECT p FROM Prediction p JOIN FETCH p.user WHERE p.game.id = :gameId")
// `@Query` アノテーション。Spring Dataに自動生成させるのではなく、指定したJPQL（Java Persistence Query Language）でDB検索を行うよう指示します。`JOIN FETCH p.user` と書くことで、`Prediction` を取得する1回のSQL実行の中で関連する `User` テーブルも結合して取得させます。
List<Prediction> findByGameIdWithUser(@Param("gameId") Long gameId);
// メソッドのシグネチャ。`@Param("gameId")` により、引数で渡した値が上のJPQL内の `:gameId` に埋め込まれます。
}
// インターフェースの終了中括弧。
```

backend/src/main/java/com/npbpredict/app/NpbPredictApplication.java
```
package com.npbpredict.app;
// パッケージ宣言。

// 空行。
import org.springframework.boot.SpringApplication;
// Spring Bootの起動に必要なクラスやアノテーションをインポートします。
import org.springframework.boot.autoconfigure.SpringBootApplication;
// Spring Bootの起動に必要なクラスやアノテーションをインポートします。
import org.springframework.scheduling.annotation.EnableScheduling;
// Spring Bootの起動に必要なクラスやアノテーションをインポートします。

// 空行。
/**
// Javadocコメント。
* Spring Bootアプリケーションのエントリーポイント（起動クラス）。
// Javadocコメント。
*/
// Javadocコメント。
@SpringBootApplication // Spring Bootの自動設定やコンポーネントスキャンを有効にします
// このクラスがSpring Bootの起動設定の中心であることを示す `@SpringBootApplication`。
@EnableScheduling // 定期実行バッチ（例: 夜間のポイント集計など）のアノテーション(@Scheduled)を有効にします
// アプリケーション内でスケジュール実行機能（定時バッチなど）を使えるようにする `@EnableScheduling` アノテーション。
public class NpbPredictApplication {
// クラスの定義開始。

// 空行。
/**
// Javadocコメント。
* アプリケーションのメインメソッド。
// Javadocコメント。
* ここからSpring Bootが起動し、内蔵のWebサーバー(Tomcat)が立ち上がります。
// Javadocコメント。
*/
// Javadocコメント。
public static void main(String[] args) {
// Javaアプリケーションの実行開始地点となる `public static void main` メソッドの定義。
SpringApplication.run(NpbPredictApplication.class, args);
// `SpringApplication.run` を呼び出すことで、SpringのDIコンテナの初期化、内蔵サーバー(Tomcat)の起動など、Webアプリケーションとしてのすべてが開始されます。
}
// `main` メソッドの終了。

// 空行。
}
// `NpbPredictApplication` クラスの終了。
```