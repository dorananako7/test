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
frontend/index.html
```
<!doctype html>
// HTML5の文書型宣言。このファイルがHTML5で書かれていることをブラウザに伝えます。
<html lang="en">
// HTML文書のルート要素。言語(lang)属性を英語(en)に設定しています。
  <head>
// メタデータやタイトル、外部リソースのリンクなどを定義するheadセクションの開始。
    <meta charset="UTF-8" />
// 文字エンコーディングをUTF-8に指定し、文字化けを防ぎます。
    <link rel="icon" type="image/svg+xml" href="/favicon.svg" />
// ブラウザのタブに表示されるアイコン（ファビコン）としてSVGファイルを指定します。
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
// スマートフォンなどのモバイル端末で適切に表示されるよう、表示領域（ビューポート）の幅と初期倍率を指定します（レスポンシブ対応に必須）。
    <title>frontend</title>
// ブラウザのタブに表示されるウェブページのタイトルです。
  </head>
// headセクションの終了。
  <body>
// ユーザーが実際にブラウザ画面で見るコンテンツを定義するbodyセクションの開始。
    <div id="root"></div>
// Reactアプリケーションがレンダリング（描画）されるマウントポイントとなる空のdivタグです。
    <script type="module" src="/src/main.tsx"></script>
// ReactアプリケーションのエントリーポイントとなるTypeScriptファイルをモジュールとして読み込みます。
  </body>
// bodyセクションの終了。
</html>
// html要素の終了。
```

frontend/src/main.tsx
```
import { StrictMode } from 'react'
// Reactから、開発時に潜在的な問題を洗い出すためのコンポーネントであるStrictModeをインポートします。
import { createRoot } from 'react-dom/client'
// React 18以降の新しいレンダリングAPIであるcreateRootをインポートします。
import './index.css'
// アプリケーション全体に適用されるグローバルなCSSファイルを読み込みます。
import App from './App.tsx'
// アプリケーションのメインコンポーネントであるAppを読み込みます。

// 空行。
createRoot(document.getElementById('root')!).render(
// HTMLファイル内のid="root"を持つDOM要素を取得し、それをReactのルートとしてレンダリングを開始します（"!"はTypeScriptでこの要素が必ず存在することを示すアサーションです）。
  <StrictMode>
// 開発モードでのみ子コンポーネントを2回レンダリングし、副作用のバグなどを検知しやすくします。
    <App />
// アプリケーションのメインコンポーネントを描画します。
  </StrictMode>,
// StrictModeの終了。
)
// render関数の終了。
```

frontend/src/App.tsx
```
import { useState } from 'react'
// Reactから状態（State）を管理するためのフック（Hook）であるuseStateをインポートします。
import reactLogo from './assets/react.svg'
// assetsフォルダからReactのロゴ画像をインポートし、reactLogoという変数に割り当てます。
import viteLogo from './assets/vite.svg'
// Viteのロゴ画像をインポートし、viteLogoという変数に割り当てます。
import heroImg from './assets/hero.png'
// メインビジュアル用の画像(hero.png)をインポートします。
import './App.css'
// このコンポーネント専用のスタイルシート(App.css)をインポートします。

// 空行。
function App() {
// Appという名前のReactの関数コンポーネントを定義します。
  const [count, setCount] = useState(0)
// useStateフックを呼び出し、状態変数count（初期値0）とその状態を更新するための関数setCountを定義します。

// 空行。
  return (
// このコンポーネントが描画するJSX（HTMLに似た構文）を返し始めます。
    <>
// Reactフラグメント（Fragment）の開始。複数の要素を親のdiv等で囲むことなく返すための特別なタグです。
      <section id="center">
// IDが"center"のsection（区切り）要素を開始します。
        <div className="hero">
// "hero"というCSSクラスを持つdiv要素を開始します（JSXではclassの代わりにclassNameを使用します）。
          <img src={heroImg} className="base" width="170" height="179" alt="" />
// 先ほどインポートしたheroImgを変数としてsrcに指定し、画像を表示します。
          <img src={reactLogo} className="framework" alt="React logo" />
// reactLogoを変数としてsrcに指定し、Reactのロゴを表示します。
          <img src={viteLogo} className="vite" alt="Vite logo" />
// viteLogoを変数としてsrcに指定し、Viteのロゴを表示します。
        </div>
// divの終了。
        <div>
// 新しいdiv要素の開始。
          <h1>Get started</h1>
// 見出し(h1)を表示します。
          <p>
// 段落(p)要素の開始。
            Edit <code>src/App.tsx</code> and save to test <code>HMR</code>
// テキストを表示します。codeタグで囲まれた部分は等幅フォントなどで強調表示されます。HMRはHot Module Replacementの略です。
          </p>
// 段落の終了。
        </div>
// divの終了。
        <button
// ボタン要素の開始。
          type="button"
// ボタンのタイプを指定します。
          className="counter"
// スタイル付けのためのCSSクラスを指定します。
          onClick={() => setCount((count) => count + 1)}
// ボタンがクリックされたときのイベントハンドラ。クリック時にsetCountを呼び出し、現在のcountに1を足して状態を更新します。
        >
// 終了タグ。
          Count is {count}
// ボタン内に表示するテキスト。波括弧 {} を使って、JavaScriptの変数(count)の値を動的に表示します。
        </button>
// ボタン要素の終了。
      </section>
// sectionの終了。

// 空行。
      <div className="ticks"></div>
// スタイル付け用の空のdiv要素。

// 空行。
      <section id="next-steps">
// 次のステップを示すセクションの開始。
        <div id="docs">
// ドキュメントリンクをまとめるdiv要素の開始。
          <svg className="icon" role="presentation" aria-hidden="true">
// SVGアイコンを表示するための要素。スクリーンリーダーからは隠す設定(aria-hidden)をしています。
            <use href="/icons.svg#documentation-icon"></use>
// publicフォルダ内にあるSVGスプライトから特定のアイコンを読み込みます。
          </svg>
// SVG要素の終了。
          <h2>Documentation</h2>
// 小見出しを表示。
          <p>Your questions, answered</p>
// 段落を表示。
          <ul>
// 順序なしリスト(ul)の開始。
            <li>
// リスト項目(li)の開始。
              <a href="https://vite.dev/" target="_blank">
// リンク(a)の開始。別タブ(_blank)で開くように設定しています。
                <img className="logo" src={viteLogo} alt="" />
// リンク内にViteのロゴ画像を表示。
                Explore Vite
// リンクのテキスト。
              </a>
// リンク要素の終了。
            </li>
// リスト項目の終了。
            <li>
// リスト項目の開始。
              <a href="https://react.dev/" target="_blank">
// リンクの開始。
                <img className="button-icon" src={reactLogo} alt="" />
// リンク内にReactのロゴを表示。
                Learn more
// リンクのテキスト。
              </a>
// リンクの終了。
            </li>
// リスト項目の終了。
          </ul>
// リストの終了。
        </div>
// divの終了。
        <div id="social">
// ソーシャルリンクをまとめるdiv要素の開始。
          <svg className="icon" role="presentation" aria-hidden="true">
// SVGアイコンの開始。
            <use href="/icons.svg#social-icon"></use>
// ソーシャル用のアイコンを読み込みます。
          </svg>
// SVGの終了。
          <h2>Connect with us</h2>
// 小見出し。
          <p>Join the Vite community</p>
// 段落。
          <ul>
// リストの開始。
            <li>
// リスト項目の開始。
              <a href="https://github.com/vitejs/vite" target="_blank">
// GitHubへのリンク。
                <svg
// アイコン用のSVG開始。
                  className="button-icon"
// CSSクラス。
                  role="presentation"
// ロール。
                  aria-hidden="true"
// スクリーンリーダー無効。
                >
// 閉じタグ。
                  <use href="/icons.svg#github-icon"></use>
// GitHubアイコン読み込み。
                </svg>
// SVG終了。
                GitHub
// テキスト。
              </a>
// リンク終了。
            </li>
// リスト項目終了。
            <li>
// リスト項目の開始。
              <a href="https://chat.vite.dev/" target="_blank">
// Discordへのリンク。
                <svg
// アイコン用のSVG開始。
                  className="button-icon"
// CSSクラス。
                  role="presentation"
// ロール。
                  aria-hidden="true"
// スクリーンリーダー無効。
                >
// 閉じタグ。
                  <use href="/icons.svg#discord-icon"></use>
// Discordアイコン読み込み。
                </svg>
// SVG終了。
                Discord
// テキスト。
              </a>
// リンク終了。
            </li>
// リスト項目終了。
            <li>
// リスト項目開始。
              <a href="https://x.com/vite_js" target="_blank">
// X(旧Twitter)へのリンク。
                <svg
// SVG開始。
                  className="button-icon"
// CSSクラス。
                  role="presentation"
// ロール。
                  aria-hidden="true"
// スクリーンリーダー無効。
                >
// 閉じタグ。
                  <use href="/icons.svg#x-icon"></use>
// Xアイコン読み込み。
                </svg>
// SVG終了。
                X.com
// テキスト。
              </a>
// リンク終了。
            </li>
// リスト項目終了。
            <li>
// リスト項目開始。
              <a href="https://bsky.app/profile/vite.dev" target="_blank">
// Blueskyへのリンク。
                <svg
// SVG開始。
                  className="button-icon"
// CSSクラス。
                  role="presentation"
// ロール。
                  aria-hidden="true"
// スクリーンリーダー無効。
                >
// 閉じタグ。
                  <use href="/icons.svg#bluesky-icon"></use>
// Blueskyアイコン読み込み。
                </svg>
// SVG終了。
                Bluesky
// テキスト。
              </a>
// リンク終了。
            </li>
// リスト項目終了。
          </ul>
// リスト終了。
        </div>
// div終了。
      </section>
// セクション終了。

// 空行。
      <div className="ticks"></div>
// スタイル付け用のdiv。
      <section id="spacer"></section>
// スタイル付け用のセクション。
    </>
// フラグメントの終了。
  )
// return文の終了。
}
// コンポーネント関数の終了。

// 空行。
export default App
// このファイルがインポートされたときに、デフォルトで提供するモジュールとしてAppコンポーネントをエクスポートします。
```

frontend/src/api/gameApi.ts
```
export interface Game {
// 試合の基本情報を表すTypeScriptの型（インターフェース）を定義・公開します。
  id: number;
// 試合ID（数値）。
  gameDate: string;
// 試合日（文字列）。
  startTime: string; // ISO format e.g. "2024-10-01T18:00:00"
// 試合開始時刻（ISO形式の文字列）。
  homeTeam: string;
// ホームチーム名。
  awayTeam: string;
// アウェイチーム名。
  status: 'BEFORE_GAME' | 'VOTING_CLOSED' | 'GAME_END';
// 試合のステータス。3つの文字列のいずれかしか許容しません。
  result: 'HOME' | 'AWAY' | 'DRAW' | null;
// 試合結果。3つの文字列またはnull（未確定）。
}
// Gameインターフェースの終了。

// 空行。
export interface GameOdds {
// 予想オッズの集計データを表すインターフェースを定義・公開します。
  totalVotes: number;
// 総投票数。
  homeRatio: number;
// ホーム勝利の得票比率。
  awayRatio: number;
// アウェイ勝利の得票比率。
  drawRatio: number;
// 引き分けの得票比率。
}
// GameOddsインターフェースの終了。

// 空行。
export interface PredictionDetail {
// 個別の予想データ（試合開始後に公開される内訳）を表すインターフェース。
  nickname: string;
// 予想したユーザーのニックネーム。
  predictedWinner: 'HOME' | 'AWAY' | 'DRAW';
// ユーザーが予想した勝敗。
}
// PredictionDetailインターフェースの終了。

// 空行。
// Dummy data for initial UI implementation
// コメント。UI実装用のダミーデータであることを示します。
const DUMMY_GAMES: Game[] = [
// モックとして使用する固定の試合データ配列を定義します。
  {
// 1つ目の試合データ。
    id: 1,
// IDは1。
    gameDate: '2024-10-01',
// 試合日は2024年10月1日。
    startTime: new Date(new Date().getTime() + 1000 * 60 * 60 * 2).toISOString(), // 2 hours in the future
// 現在時刻の2時間後の時刻をISO文字列として設定（試合前テスト用）。
    homeTeam: '巨人',
// ホームチーム。
    awayTeam: '阪神',
// アウェイチーム。
    status: 'BEFORE_GAME',
// ステータスは「試合前」。
    result: null,
// 結果は未確定。
  },
// 1つ目のデータ終了。
  {
// 2つ目の試合データ。
    id: 2,
// IDは2。
    gameDate: '2024-10-01',
// 試合日。
    startTime: new Date(new Date().getTime() - 1000 * 60 * 60 * 1).toISOString(), // 1 hour ago
// 現在時刻の1時間前の時刻を設定（試合中テスト用）。
    homeTeam: 'ソフトバンク',
// ホームチーム。
    awayTeam: 'オリックス',
// アウェイチーム。
    status: 'VOTING_CLOSED',
// ステータスは「投票締切済」。
    result: null,
// 結果は未確定。
  },
// 2つ目のデータ終了。
  {
// 3つ目の試合データ。
    id: 3,
// IDは3。
    gameDate: '2024-10-01',
// 試合日。
    startTime: new Date(new Date().getTime() - 1000 * 60 * 60 * 5).toISOString(), // 5 hours ago
// 現在時刻の5時間前の時刻を設定（試合終了テスト用）。
    homeTeam: 'DeNA',
// ホームチーム。
    awayTeam: 'ヤクルト',
// アウェイチーム。
    status: 'GAME_END',
// ステータスは「試合終了」。
    result: 'HOME',
// ホームチームの勝利と設定。
  }
// 3つ目のデータ終了。
];
// ダミーデータ配列の終了。

// 空行。
export const fetchGames = async (): Promise<Game[]> => {
// 試合一覧を取得する非同期関数(fetchGames)を定義し公開します。返り値はPromise<Game[]>。
  // Simulate network delay
// コメント。
  await new Promise(resolve => setTimeout(resolve, 500));
// setTimeoutを使って500ミリ秒待機し、実際のネットワーク通信の遅延をシミュレートします。
  return DUMMY_GAMES;
// 待機後、ダミーデータ配列を返します。
};
// 関数の終了。

// 空行。
export const fetchGameOdds = async (gameId: number): Promise<GameOdds> => {
// 特定の試合IDに対するオッズを取得する関数(fetchGameOdds)を定義。
  await new Promise(resolve => setTimeout(resolve, 500));
// 500ミリ秒待機。

// 空行。
  if (gameId === 1) {
// 試合IDが1の場合。
    return { totalVotes: 120, homeRatio: 60.5, awayRatio: 35.0, drawRatio: 4.5 };
// ID=1用のダミーオッズデータを返します。
  } else if (gameId === 2) {
// 試合IDが2の場合。
    return { totalVotes: 85, homeRatio: 40.0, awayRatio: 55.0, drawRatio: 5.0 };
// ID=2用のダミーオッズデータを返します。
  } else {
// それ以外（ID=3）の場合。
    return { totalVotes: 210, homeRatio: 50.0, awayRatio: 45.0, drawRatio: 5.0 };
// ダミーオッズデータを返します。
  }
// if文の終了。
};
// 関数の終了。

// 空行。
export const fetchGamePredictions = async (gameId: number): Promise<PredictionDetail[]> => {
// 特定の試合の投票内訳（ユーザーごとの予想）を取得する関数(fetchGamePredictions)を定義。
  await new Promise(resolve => setTimeout(resolve, 500));
// 500ミリ秒待機。

// 空行。
  const game = DUMMY_GAMES.find(g => g.id === gameId);
// 渡されたIDに一致する試合データをダミー配列から探し、game変数に格納します。
  if (game && game.status === 'BEFORE_GAME') {
// もし試合が存在し、かつステータスが「試合前」であれば。
    throw new Error("Predictions are hidden before the game starts.");
// バックエンドのカンニング防止ロジックと同じように、エラー（例外）を発生させて取得を拒否します。
  }
// if文の終了。

// 空行。
  return [
// エラーにならなかった場合（試合開始後）、ダミーのユーザー予想配列を返します。
    { nickname: '野球太郎', predictedWinner: 'HOME' },
// ユーザー1。
    { nickname: 'ベースボールファン', predictedWinner: 'AWAY' },
// ユーザー2。
    { nickname: 'NPBマニア', predictedWinner: 'HOME' },
// ユーザー3。
    { nickname: 'スワローズ命', predictedWinner: 'DRAW' },
// ユーザー4。
  ];
// 配列の終了。
};
// 関数の終了。
```

frontend/src/components/Header.tsx
```
import React from 'react';
// Reactライブラリをインポートします（JSXを使うコンポーネント用）。
import { Trophy, UserCircle } from 'lucide-react';
// lucide-reactから、アイコンコンポーネント（トロフィー、ユーザー円形）をインポートします。

// 空行。
const Header: React.FC = () => {
// Headerという名前の関数コンポーネント（React.FC型）を定義します。
  return (
// 描画するJSXの返却を開始。
    <header className="bg-blue-900 text-white shadow-md sticky top-0 z-50">
// headerタグの開始。背景色、文字色、シャドウ、スクロール追従(sticky)、重なり順(z-50)をTailwindクラスで指定。
      <div className="container mx-auto px-4 py-3 flex justify-between items-center">
// コンテンツを中央寄せするコンテナ。左右にパディング、Flexboxで両端揃え(justify-between)、垂直中央揃えを指定。

// 空行。
        {/* Logo / App Name */}
// コメント。ロゴ部分。
        <div className="flex items-center space-x-2">
// フレックスコンテナで、アイコンと文字の間に少し間隔(space-x-2)を空けます。
          <Trophy className="text-yellow-400 w-6 h-6" />
// トロフィーアイコンを表示。色は黄色、サイズは24px(w-6/h-6)。
          <h1 className="text-xl font-bold tracking-wider">NPB Predict</h1>
// アプリ名を表示。文字サイズ大きめ(text-xl)、太字、文字間隔少し広め(tracking-wider)。
        </div>
// ロゴdivの終了。

// 空行。
        {/* Navigation / User Actions */}
// コメント。ナビゲーション部分。
        <nav className="flex items-center space-x-6">
// ナビゲーションメニュー。項目間の間隔を広め(space-x-6)にとります。
          <a href="#" className="hover:text-blue-200 transition-colors text-sm font-medium">試合一覧</a>
// リンク。ホバー時に薄い青色になり、アニメーション(transition)します。
          <a href="#" className="hover:text-blue-200 transition-colors text-sm font-medium">ランキング</a>
// リンク。

// 空行。
          <div className="flex items-center bg-blue-800 rounded-full px-3 py-1.5 cursor-pointer hover:bg-blue-700 transition-colors">
// ログインボタン風のdiv要素。丸角(rounded-full)と背景色、ホバー時の色変化を指定。
            <UserCircle className="w-5 h-5 mr-2" />
// ユーザーアイコン。右側にマージン(mr-2)。
            <span className="text-sm font-medium">ログイン</span>
// 「ログイン」テキスト。
          </div>
// ログインボタンdiv終了。
        </nav>
// nav終了。

// 空行。
      </div>
// container div終了。
    </header>
// header終了。
  );
// return終了。
};
// コンポーネント終了。

// 空行。
export default Header;
// Headerコンポーネントをエクスポート。
```

frontend/src/components/GameList.tsx
```
import React, { useEffect, useState } from 'react';
// Reactと、副作用処理(useEffect)・状態管理(useState)のフックをインポートします。
import { fetchGames } from '../api/gameApi';
// モックAPIから試合一覧取得関数をインポートします。
import type { Game } from '../api/gameApi';
// GameのTypeScriptの型（インターフェース）をインポートします。
import GameCard from './GameCard';
// 個別の試合を表示するカードコンポーネントをインポートします。
import { Calendar } from 'lucide-react';
// カレンダーアイコンをインポートします。

// 空行。
const GameList: React.FC = () => {
// GameList関数コンポーネントの定義。
  const [games, setGames] = useState<Game[]>([]);
// 試合データの配列を保持する状態変数games。初期値は空配列。
  const [isLoading, setIsLoading] = useState(true);
// データ読み込み中かどうかを保持するフラグ状態変数。初期値はtrue（読み込み中）。

// 空行。
  useEffect(() => {
// コンポーネントがマウントされた時（初回描画後）に1回だけ実行される副作用フック。
    const loadGames = async () => {
// 非同期でデータを取得するための内部関数。
      try {
// エラーハンドリングのためのtryブロック。
        const fetchedGames = await fetchGames();
// モックAPIを呼び出し、試合データ一覧を取得。
        setGames(fetchedGames);
// 取得したデータを状態にセット。
      } catch (e) {
// エラー発生時のcatchブロック。
        console.error(e);
// コンソールにエラーを出力。
      } finally {
// 成功・失敗にかかわらず必ず実行されるブロック。
        setIsLoading(false);
// 読み込み完了フラグを下ろす。
      }
// ブロックの終了。
    };
// 内部関数の終了。
    loadGames();
// 定義した内部関数を直ちに呼び出す。
  }, []);
// useEffectの依存配列。空なので初回マウント時のみ実行される。

// 空行。
  if (isLoading) {
// 読み込み中(isLoading=true)の場合の条件分岐。
    return (
// ローディング表示用JSXを返す。
      <div className="flex justify-center items-center py-20">
// Flexboxで上下左右中央に配置、上下パディング。
        <div className="animate-spin rounded-full h-10 w-10 border-b-2 border-blue-800"></div>
// スピナー（回転する円）。Tailwindのanimate-spinで回転アニメーションを適用。
      </div>
// ローディングdiv終了。
    );
// return終了。
  }
// if文終了。

// 空行。
  return (
// ローディング完了後に表示するJSXを返す。
    <div className="max-w-2xl mx-auto py-6 px-4">
// 横幅を制限(max-w-2xl)し、画面中央に配置(mx-auto)するコンテナ。
      <div className="flex items-center space-x-2 mb-6 text-gray-800">
// 見出しコンテナ。Flexboxでアイコンと文字を横並びに。
        <Calendar className="w-5 h-5 text-blue-800" />
// カレンダーアイコン。
        <h2 className="text-xl font-bold">今日の試合 ＆ 予想オッズ</h2>
// 見出しテキスト。
      </div>
// 見出しコンテナ終了。

// 空行。
      {games.length === 0 ? (
// 三項演算子。試合データが0件（空）の場合の分岐。
        <div className="text-center py-10 bg-white rounded-lg shadow-sm border border-gray-100">
// 空状態メッセージのコンテナ。
          <p className="text-gray-500">本日の試合予定はありません。</p>
// メッセージテキスト。
        </div>
// コンテナ終了。
      ) : (
// 試合データが1件以上ある場合の分岐。
        games.map(game => (
// games配列の各要素(game)に対して繰り返し処理(map)。
          <GameCard key={game.id} game={game} />
// 要素ごとにGameCardコンポーネントを描画。Reactが要素を識別できるようにkey(game.id)を渡し、プロパティとしてgameデータを渡す。
        ))
// map終了。
      )}
// 条件演算子終了。
    </div>
// メインコンテナ終了。
  );
// return終了。
};
// コンポーネント終了。

// 空行。
export default GameList;
// GameListコンポーネントをエクスポート。
```

frontend/src/App.tsx
```
import Header from './components/Header';
// 作成したHeaderコンポーネントをインポートします。
import GameList from './components/GameList';
// 作成したGameListコンポーネントをインポートします。

// 空行。
function App() {
// アプリケーションのルートコンポーネントであるApp関数の定義。
  return (
// アプリケーション全体のレイアウトJSXを返します。
    <div className="min-h-screen font-sans text-gray-900 bg-[#f3f4f6]">
// 全体を囲むdiv。最低でも画面の高さ(min-h-screen)を持ち、フォント、文字色、背景色（グレー）を指定。
      <Header />
// 画面上部にHeaderコンポーネントを描画します。
      <main>
// ページごとの主要なコンテンツを配置するmainタグ。
        <GameList />
// その中にGameListコンポーネントを描画します。
      </main>
// mainタグ終了。

// 空行。
      <footer className="mt-12 py-6 text-center text-sm text-gray-400 border-t border-gray-200">
// フッター要素。上部にマージンとボーダー(線)を引き、文字を中央寄せ(text-center)・小さく(text-sm)します。
        &copy; {new Date().getFullYear()} NPB Predict App. Not affiliated with Nippon Professional Baseball.
// コピーライト表示と免責事項。JavaScriptで現在の年を動的に取得・表示しています。
      </footer>
// フッター終了。
    </div>
// 外側のdiv終了。
  );
// return終了。
}
// コンポーネント終了。

// 空行。
export default App;
// Appコンポーネントをエクスポート。
```

frontend/src/components/GameCard.tsx
```
import React, { useEffect, useState } from 'react';
// Reactと、副作用処理(useEffect)・状態管理(useState)のフックをインポートします。
import { fetchGameOdds, fetchGamePredictions } from '../api/gameApi';
// モックAPIから、オッズ取得関数と予想内訳取得関数をインポートします。
import type { Game, GameOdds, PredictionDetail } from '../api/gameApi';
// モックAPIから、試合、オッズ、予想詳細の各TypeScriptの型（インターフェース）を型としてのみ(type)インポートします。
import { Clock, Users, Lock, Unlock, CheckCircle } from 'lucide-react';
// アイコンライブラリから必要なアイコンをインポートします。

// 空行。
interface GameCardProps {
// GameCardコンポーネントが受け取る引数（プロパティ）の型定義を開始。
  game: Game;
// gameという名前でGame型のオブジェクトを受け取ることを定義。
}
// インターフェースの終了。

// 空行。
const GameCard: React.FC<GameCardProps> = ({ game }) => {
// GameCardコンポーネントの定義。ジェネリクスでGameCardPropsを指定し、引数からgameオブジェクトを取り出します（分割代入）。
  const [odds, setOdds] = useState<GameOdds | null>(null);
// オッズデータを保持する状態変数。初期値はnull。
  const [predictions, setPredictions] = useState<PredictionDetail[] | null>(null);
// 予想内訳データを保持する状態変数。初期値はnull。
  const [isLoading, setIsLoading] = useState(true);
// データロード中の状態を保持するフラグ。初期値はtrue。

// 空行。
  // Parse game time
// コメント。
  const gameDate = new Date(game.startTime);
// 試合の開始時刻（文字列）からJavaScriptのDateオブジェクトを生成。
  const timeString = `${gameDate.getHours().toString().padStart(2, '0')}:${gameDate.getMinutes().toString().padStart(2, '0')}`;
// Dateオブジェクトから時間と分を取り出し、「18:00」のような文字列に整形します。

// 空行。
  useEffect(() => {
// コンポーネントのマウント時や、特定の変数が変化した際に実行されるフック。
    const loadData = async () => {
// 非同期でデータを取得する内部関数。
      try {
// エラーハンドリング開始。
        const fetchedOdds = await fetchGameOdds(game.id);
// APIから対象試合のオッズデータを取得。
        setOdds(fetchedOdds);
// 取得したオッズを状態変数にセット。

// 空行。
        if (game.status !== 'BEFORE_GAME') {
// もし試合のステータスが「試合前」でない（＝試合が始まっている）なら。
          const fetchedPredictions = await fetchGamePredictions(game.id);
// カンニング防止が解除されているはずなので、予想内訳データを取得。
          setPredictions(fetchedPredictions);
// 取得した予想内訳を状態変数にセット。
        }
// if文終了。
      } catch (e) {
// エラー捕捉。
        console.error(e);
// コンソールに出力。
      } finally {
// 成功・失敗にかかわらず実行。
        setIsLoading(false);
// ローディングフラグを下ろす。
      }
// finally終了。
    };
// 内部関数終了。

// 空行。
    loadData();
// 定義した関数を呼び出す。
  }, [game.id, game.status]);
// useEffectの依存配列。game.idかgame.statusが変わった場合のみ、このフックの中身が再実行されます。

// 空行。
  return (
// 描画するJSXを開始。
    <div className="bg-white rounded-xl shadow-sm border border-gray-100 overflow-hidden mb-6 transition-all hover:shadow-md">
// カード全体のコンテナ。白背景、角丸、影、ボーダー、ホバー時に影が濃くなる効果を指定。

// 空行。
      {/* Card Header (Teams and Time) */}
// コメント。カードヘッダー部分。
      <div className="bg-gradient-to-r from-gray-50 to-gray-100 px-4 py-3 border-b border-gray-100 flex justify-between items-center">
// ヘッダー。薄いグレーのグラデーション背景、下線、両端揃えのFlexbox。
        <div className="flex items-center space-x-2 text-gray-500">
// 時間表示コンテナ。
          <Clock className="w-4 h-4" />
// 時計アイコン。
          <span className="text-sm font-semibold">{timeString} プレイボール</span>
// 計算した試合開始時刻を表示。
        </div>
// 時間表示終了。
        <div className="text-xs font-bold px-2 py-1 rounded bg-gray-200 text-gray-600">
// ステータスバッジのコンテナ。
          {game.status === 'BEFORE_GAME' ? '投票受付中' : game.status === 'VOTING_CLOSED' ? '試合中 (投票締切)' : '試合終了'}
// 試合のステータスEnumの値によって、表示する日本語テキストを切り替えます。
        </div>
// ステータスバッジ終了。
      </div>
// カードヘッダー終了。

// 空行。
      <div className="p-5">
// カード本文のコンテナ（余白p-5）。

// 空行。
        {/* Teams Display */}
// コメント。対戦チーム表示部分。
        <div className="flex justify-between items-center mb-6">
// 横並び(flex)でチーム名とVSを表示するコンテナ。
          <div className="flex-1 text-center">
// ホームチームのコンテナ（等幅で伸縮=flex-1、中央寄せ）。
            <h3 className="text-xl font-bold text-gray-800">{game.homeTeam}</h3>
// ホームチーム名を表示。
            <p className="text-xs text-gray-400 mt-1">HOME</p>
// "HOME"というラベルを表示。
          </div>
// ホームコンテナ終了。
          <div className="px-4 text-gray-400 font-bold text-lg">VS</div>
// "VS"という文字を表示。
          <div className="flex-1 text-center">
// アウェイチームのコンテナ。
            <h3 className="text-xl font-bold text-gray-800">{game.awayTeam}</h3>
// アウェイチーム名を表示。
            <p className="text-xs text-gray-400 mt-1">AWAY</p>
// "AWAY"というラベルを表示。
          </div>
// アウェイコンテナ終了。
        </div>
// 対戦チーム表示終了。

// 空行。
        {/* Voting Panel (Only if BEFORE_GAME) */}
// コメント。投票パネル（試合前のみ表示）。
        {game.status === 'BEFORE_GAME' && (
// もしステータスがBEFORE_GAMEであれば、後続のJSXを描画します。
          <div className="mb-6">
// 投票パネルのコンテナ。
            <p className="text-sm font-semibold text-gray-600 mb-2 text-center">あなたの予想は？</p>
// メッセージテキスト。
            <div className="flex gap-2">
// ボタンを横並びに配置し、間隔(gap-2)を空けるコンテナ。
              <button className="flex-1 bg-blue-50 text-blue-700 hover:bg-blue-600 hover:text-white border border-blue-200 font-bold py-2 px-4 rounded transition-colors text-sm">
// ホームチーム勝利ボタン。青色ベースでホバー時に色反転。
                {game.homeTeam}の勝ち
// ボタンテキスト。
              </button>
// ボタン終了。
              <button className="flex-1 bg-gray-50 text-gray-700 hover:bg-gray-600 hover:text-white border border-gray-200 font-bold py-2 px-4 rounded transition-colors text-sm">
// 引き分けボタン。グレーベース。
                引き分け
// ボタンテキスト。
              </button>
// ボタン終了。
              <button className="flex-1 bg-red-50 text-red-700 hover:bg-red-600 hover:text-white border border-red-200 font-bold py-2 px-4 rounded transition-colors text-sm">
// アウェイチーム勝利ボタン。赤色ベース。
                {game.awayTeam}の勝ち
// ボタンテキスト。
              </button>
// ボタン終了。
            </div>
// ボタングループ終了。
            <p className="text-xs text-center text-gray-400 mt-2 flex items-center justify-center">
// 未ログイン時の注意書きコンテナ。
              <Lock className="w-3 h-3 mr-1" /> ログインして投票に参加しよう
// 南京錠アイコンとメッセージ。
            </p>
// 注意書き終了。
          </div>
// 投票パネル終了。
        )}
// BEFORE_GAME条件終了。

// 空行。
        {/* Odds Section */}
// コメント。オッズ表示部分。
        {isLoading ? (
// ロード中の場合。
          <div className="h-16 flex items-center justify-center text-gray-400 text-sm">読み込み中...</div>
// プレースホルダー文字列を表示。
        ) : odds ? (
// ロード完了で、オッズデータが存在する場合。
          <div className="bg-gray-50 rounded-lg p-4">
// オッズ表示エリアのコンテナ。
            <div className="flex justify-between text-xs font-semibold text-gray-500 mb-2">
// オッズヘッダー（総票数とステータス）のコンテナ。
              <div className="flex items-center"><Users className="w-3 h-3 mr-1"/> みんなの予想オッズ ({odds.totalVotes}票)</div>
// ユーザーアイコンと総投票数を表示。
              {game.status === 'BEFORE_GAME' ? (
// 試合前かどうかの条件分岐。
                <div className="flex items-center text-orange-500"><Lock className="w-3 h-3 mr-1"/> 誰が投票したかは試合開始まで秘密</div>
// 試合前なら南京錠アイコンと秘密メッセージをオレンジで表示。
              ) : (
// 試合開始後なら。
                <div className="flex items-center text-green-600"><Unlock className="w-3 h-3 mr-1"/> 投票内訳公開中</div>
// 開いた南京錠アイコンと公開中メッセージを緑で表示。
              )}
// 条件分岐終了。
            </div>
// オッズヘッダー終了。

// 空行。
            {/* Progress Bar for Odds */}
// コメント。オッズを視覚化するプログレスバー。
            <div className="h-4 w-full flex rounded-full overflow-hidden">
// 1本のバーとして見せるためのコンテナ。はみ出た部分を隠す(overflow-hidden)。
              <div style={{ width: `${odds.homeRatio}%` }} className="bg-blue-500 h-full flex justify-center items-center text-[10px] text-white font-bold transition-all duration-500">
// ホーム割合を示す青いバー。style属性で幅を動的に設定。
                {odds.homeRatio > 10 ? `${odds.homeRatio}%` : ''}
// 幅が小さすぎる場合は文字がはみ出るため、10%より大きい場合のみパーセントを表示。
              </div>
// ホームバー終了。
              <div style={{ width: `${odds.drawRatio}%` }} className="bg-gray-400 h-full flex justify-center items-center text-[10px] text-white font-bold transition-all duration-500">
// 引き分け割合を示すグレーのバー。
                {odds.drawRatio > 10 ? `${odds.drawRatio}%` : ''}
// 同様に幅チェックして表示。
              </div>
// 引き分けバー終了。
              <div style={{ width: `${odds.awayRatio}%` }} className="bg-red-500 h-full flex justify-center items-center text-[10px] text-white font-bold transition-all duration-500">
// アウェイ割合を示す赤いバー。
                {odds.awayRatio > 10 ? `${odds.awayRatio}%` : ''}
// 同様に表示。
              </div>
// アウェイバー終了。
            </div>
// プログレスバー終了。

// 空行。
            <div className="flex justify-between mt-2 text-xs font-bold">
// バーの下に詳細な数値を表示するコンテナ。
              <span className="text-blue-700">{game.homeTeam}: {odds.homeRatio}%</span>
// ホームチーム名と比率。
              <span className="text-gray-500">引分: {odds.drawRatio}%</span>
// 引き分け比率。
              <span className="text-red-700">{game.awayTeam}: {odds.awayRatio}%</span>
// アウェイチーム名と比率。
            </div>
// 詳細数値終了。
          </div>
// オッズ表示エリア終了。
        ) : null}
// isLoading・odds条件終了。

// 空行。
        {/* Revealed Predictions (Only if after game started) */}
// コメント。投票内訳の公開部分。
        {game.status !== 'BEFORE_GAME' && predictions && (
// 試合開始後で、かつ予想内訳データが存在する場合。
          <div className="mt-4 pt-4 border-t border-gray-100">
// 内訳表示コンテナ（上に線あり）。
            <h4 className="text-sm font-semibold text-gray-700 mb-3 flex items-center">
// 小見出しコンテナ。
              <CheckCircle className="w-4 h-4 mr-1 text-green-500" /> 最新の投票内訳 (一部)
// チェックアイコンとタイトル。
            </h4>
// 小見出し終了。
            <div className="flex flex-wrap gap-2">
// ユーザーのバッジを並べるコンテナ。折り返し可能(flex-wrap)。
              {predictions.map((p, idx) => (
// 予想データの配列をループして個々の要素(p)を描画。
                <span key={idx} className="inline-flex items-center px-2 py-1 rounded bg-white border border-gray-200 text-xs text-gray-600 shadow-sm">
// ユーザー1人分のバッジ。
                  <span className="font-semibold mr-1">{p.nickname}</span>
// ユーザー名を表示。
                  <span className={`px-1 rounded text-[10px] text-white ${
// ユーザーの予想内容を示すカラーラベル。テンプレートリテラルで色を動的設定。
                    p.predictedWinner === 'HOME' ? 'bg-blue-500' : p.predictedWinner === 'AWAY' ? 'bg-red-500' : 'bg-gray-500'
// HOMEなら青、AWAYなら赤、引き分けならグレー。
                  }`}>
// 閉じタグ。
                    {p.predictedWinner === 'HOME' ? game.homeTeam : p.predictedWinner === 'AWAY' ? game.awayTeam : '引分'}
// 予想したチーム名、または「引分」を表示。
                  </span>
// カラーラベル終了。
                </span>
// バッジ終了。
              ))}
// map終了。
            </div>
// ユーザーバッジコンテナ終了。
          </div>
// 内訳コンテナ終了。
        )}
// 条件分岐終了。

// 空行。
      </div>
// カード本文終了。
    </div>
// カード全体終了。
  );
// return終了。
};
// コンポーネント終了。

// 空行。
export default GameCard;
// GameCardコンポーネントのエクスポート。
```
