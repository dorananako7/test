# ProgramInformation.md

本ファイルでは、プロジェクトの各ファイルについて「1行1行」完全に解説します。

## docker-compose.yml
```yaml
1: # Docker Composeのバージョン指定
2: version: '3.8'
3:
4: # 定義するサービス（コンテナ）のリスト
5: services:
6:   # データベース用のPostgreSQLコンテナ
7:   postgres:
8:     # 使用する公式のPostgreSQLイメージとバージョン
9:     image: postgres:15
10:     # コンテナに付ける名前
11:     container_name: npb_postgres
12:     # PostgreSQLコンテナに渡す環境変数（初期DB名やパスワードなど）
13:     environment:
14:       # 起動時に作成されるデフォルトのデータベース名
15:       POSTGRES_DB: npb_prediction
16:       # データベースに接続するためのユーザー名
17:       POSTGRES_USER: npb_user
18:       # データベースに接続するためのパスワード
19:       POSTGRES_PASSWORD: npb_password
20:     # ポートマッピング: ホストの5432ポートをコンテナの5432ポートに紐付け
21:     ports:
22:       - "5432:5432"
23:     # データを永続化するためのボリュームマッピング
24:     volumes:
25:       - postgres_data:/var/lib/postgresql/data
26:
27:   # Spring Bootバックエンド用のコンテナ
28:   backend:
29:     # ビルド設定
30:     build:
31:       # ビルドのコンテキスト（ルートディレクトリからのパス）
32:       context: ./backend
33:       # 使用するDockerfileの名前
34:       dockerfile: Dockerfile
35:     # コンテナに付ける名前
36:     container_name: npb_backend
37:     # ポートマッピング: ホストの8080ポートをコンテナの8080ポートに紐付け
38:     ports:
39:       - "8080:8080"
40:     # 起動順序: postgresコンテナが起動した後に起動する
41:     depends_on:
42:       - postgres
43:     # Spring Bootアプリケーションに渡す環境変数
44:     environment:
45:       # データベース接続URL（ホスト名を'postgres'にしてコンテナ名で名前解決）
46:       - SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/npb_prediction
47:       # データベース接続ユーザー名
48:       - SPRING_DATASOURCE_USERNAME=npb_user
49:       # データベース接続パスワード
50:       - SPRING_DATASOURCE_PASSWORD=npb_password
51:
52:   # React/Viteフロントエンド用のコンテナ
53:   frontend:
54:     # ビルド設定
55:     build:
56:       # ビルドのコンテキスト（ルートディレクトリからのパス）
57:       context: ./frontend
58:       # 使用するDockerfileの名前
59:       dockerfile: Dockerfile
60:     # コンテナに付ける名前
61:     container_name: npb_frontend
62:     # ポートマッピング: ホストの5173ポートをコンテナの5173ポートに紐付け
63:     ports:
64:       - "5173:5173"
65:     # 起動順序: backendコンテナが起動した後に起動する
66:     depends_on:
67:       - backend
68:     # ファイルの変更を検知するためのボリュームマッピング
69:     volumes:
70:       # ホストの./frontendディレクトリをコンテナの/appディレクトリにマウント
71:       - ./frontend:/app
72:       # コンテナ内のnode_modulesをホスト側に上書きされないようにするための設定
73:       - /app/node_modules
74:
75: # データ永続化のために定義されたボリューム
76: volumes:
77:   # PostgreSQLのデータを保存するボリューム
78:   postgres_data:
```
- 1行目: コメント。Docker Composeのバージョンを指定することを示します。
- 2行目: Docker Composeファイルの文法バージョンを「3.8」に指定します。
- 3行目: 空行。
- 4行目: コメント。
- 5行目: 立ち上げるコンテナ（サービス）の一覧の定義を開始します。
- 6行目: コメント。
- 7行目: `postgres`という名前のサービス（コンテナ）の定義を開始します。
- 8行目: コメント。
- 9行目: ベースとなるDockerイメージとして、公式の`postgres:15`を使用することを指定します。
- 10行目: コメント。
- 11行目: 生成されるコンテナの名前を明示的に`npb_postgres`に設定します。
- 12行目: コメント。
- 13行目: このコンテナに渡す環境変数の定義を開始します。
- 14行目: コメント。
- 15行目: PostgreSQLが起動時に自動作成するデータベース名を`npb_prediction`に指定します。
- 16行目: コメント。
- 17行目: データベースのユーザー名を`npb_user`に指定します。
- 18行目: コメント。
- 19行目: データベースユーザーのパスワードを`npb_password`に指定します。
- 20行目: コメント。
- 21行目: コンテナのポートとホストのポートを紐付ける設定を開始します。
- 22行目: ホスト側のポート5432をコンテナ内のポート5432に転送します。
- 23行目: コメント。
- 24行目: コンテナのディレクトリをホスト側のボリュームにマウントする設定を開始します。
- 25行目: Dockerが管理する名前付きボリューム`postgres_data`を、コンテナ内の`/var/lib/postgresql/data`にマウントし、データを永続化します。
- 26行目: 空行。
- 27行目: コメント。
- 28行目: `backend`という名前のサービス（コンテナ）の定義を開始します。
- 29行目: コメント。
- 30行目: 既存のイメージを使わず、Dockerfileからビルドする設定を開始します。
- 31行目: コメント。
- 32行目: ビルドのコンテキスト（基準となるディレクトリ）を`./backend`に指定します。
- 33行目: コメント。
- 34行目: 使用するDockerfileの名前を`Dockerfile`に指定します。
- 35行目: コメント。
- 36行目: コンテナの名前を`npb_backend`に指定します。
- 37行目: コメント。
- 38行目: ポート設定を開始します。
- 39行目: ホスト側のポート8080をコンテナ内のポート8080に転送します。
- 40行目: コメント。
- 41行目: 依存関係の設定を開始します。
- 42行目: `postgres`コンテナが起動した後に、このコンテナを起動するように指定します。
- 43行目: コメント。
- 44行目: バックエンドコンテナに渡す環境変数の設定を開始します。
- 45行目: コメント。
- 46行目: Spring BootのDB接続URL環境変数。ホスト名を`postgres`（サービス名）にし、`npb_prediction`データベースに接続します。
- 47行目: コメント。
- 48行目: DB接続ユーザー名の環境変数を`npb_user`に指定します。
- 49行目: コメント。
- 50行目: DB接続パスワードの環境変数を`npb_password`に指定します。
- 51行目: 空行。
- 52行目: コメント。
- 53行目: `frontend`という名前のサービス（コンテナ）の定義を開始します。
- 54行目: コメント。
- 55行目: ビルド設定を開始します。
- 56行目: コメント。
- 57行目: ビルドのコンテキストを`./frontend`に指定します。
- 58行目: コメント。
- 59行目: 使用するDockerfileの名前を`Dockerfile`に指定します。
- 60行目: コメント。
- 61行目: コンテナの名前を`npb_frontend`に指定します。
- 62行目: コメント。
- 63行目: ポート設定を開始します。
- 64行目: ホスト側のポート5173をコンテナ内のポート5173に転送します。
- 65行目: コメント。
- 66行目: 依存関係の設定を開始します。
- 67行目: `backend`コンテナが起動した後に、このコンテナを起動するように指定します。
- 68行目: コメント。
- 69行目: ボリュームマウントの設定を開始します。
- 70行目: コメント。
- 71行目: ホスト側の`./frontend`ディレクトリを、コンテナ内の`/app`ディレクトリにマウントし、リアルタイムなコード変更の反映を可能にします。
- 72行目: コメント。
- 73行目: コンテナ内の`/app/node_modules`を匿名ボリュームとしてマウントし、ホスト環境に上書きされないよう保護します。
- 74行目: 空行。
- 75行目: コメント。
- 76行目: コンテナ間で共有、またはホスト側で永続化するためのボリュームの定義を開始します。
- 77行目: コメント。
- 78行目: `postgres_data`という名前のボリュームを定義します（詳細な設定を省略しているため、デフォルトのローカルドライバで作成されます）。

## backend/Dockerfile
```dockerfile
1: # ステージ1: アプリケーションのビルド
2: # ベースイメージとして軽量なAlpine LinuxベースのJDK 17を使用
3: FROM eclipse-temurin:17-jdk-alpine AS build
4:
5: # コンテナ内の作業ディレクトリを/appに設定
6: WORKDIR /app
7:
8: # Mavenラッパーと依存関係定義ファイル(pom.xml)をコピー
9: COPY mvnw .
10: COPY .mvn .mvn
11: COPY pom.xml .
12:
13: # 依存関係をダウンロード (pom.xmlが変更されない限り、このレイヤーはキャッシュされる)
14: RUN ./mvnw dependency:go-offline -B
15:
16: # ソースコードをコンテナ内にコピー
17: COPY src src
18:
19: # テストをスキップしてMavenでパッケージ化（JARファイルを生成）
20: RUN ./mvnw clean package -DskipTests
21:
22: # ステージ2: アプリケーションの実行
23: # 実行用にはより軽量なJREイメージを使用
24: FROM eclipse-temurin:17-jre-alpine
25:
26: # コンテナ内の作業ディレクトリを/appに設定
27: WORKDIR /app
28:
29: # ビルドステージ（ステージ1）で生成されたJARファイルをapp.jarとしてコピー
30: COPY --from=build /app/target/*.jar app.jar
31:
32: # Spring Bootのデフォルトポート(8080)を公開することを明示
33: EXPOSE 8080
34:
35: # コンテナ起動時に実行されるコマンド（Javaアプリケーションを起動）
36: ENTRYPOINT ["java", "-jar", "app.jar"]
```
- 1行目: コメント。以降がビルド用のステージであることを示します。
- 2行目: コメント。
- 3行目: ビルド環境のベースイメージとして、JDK 17を含むAlpine Linux版のTemurinを指定し、このステージに`build`という名前を付けます。
- 4行目: 空行。
- 5行目: コメント。
- 6行目: 以降のコマンドを実行するための作業ディレクトリを`/app`に指定します。
- 7行目: 空行。
- 8行目: コメント。
- 9行目: ホスト側の`mvnw`（Maven Wrapper）ファイルを、コンテナの現在の作業ディレクトリにコピーします。
- 10行目: ホスト側の`.mvn`ディレクトリを、コンテナ内にコピーします。
- 11行目: ホスト側の`pom.xml`を、コンテナ内にコピーします。
- 12行目: 空行。
- 13行目: コメント。
- 14行目: コンテナ内で`./mvnw dependency:go-offline -B`を実行し、依存ライブラリを事前にすべてダウンロードします。
- 15行目: 空行。
- 16行目: コメント。
- 17行目: ホスト側の`src`ディレクトリ（ソースコード本体）を、コンテナ内の`src`ディレクトリにコピーします。
- 18行目: 空行。
- 19行目: コメント。
- 20行目: コンテナ内で`./mvnw clean package -DskipTests`を実行し、コンパイルとJARファイルの作成を行います（テストはスキップします）。
- 21行目: 空行。
- 22行目: コメント。以降が実行用のステージであることを示します。
- 23行目: コメント。
- 24行目: 実行環境のベースイメージとして、軽量なJRE 17を含むAlpine Linux版のTemurinを指定します（これにより最終イメージサイズが小さくなります）。
- 25行目: 空行。
- 26行目: コメント。
- 27行目: 以降のコマンドを実行するための作業ディレクトリを`/app`に指定します。
- 28行目: 空行。
- 29行目: コメント。
- 30行目: `build`ステージで作成された`/app/target/`配下のJARファイルを、現在のイメージの`app.jar`という名前でコピーします。
- 31行目: 空行。
- 32行目: コメント。
- 33行目: コンテナがポート8080で通信待ち受けをすることを、Dockerシステム側に明示します。
- 34行目: 空行。
- 35行目: コメント。
- 36行目: コンテナ起動時のデフォルトコマンドとして、`java -jar app.jar`を実行するように指定します。

## frontend/Dockerfile
```dockerfile
1: # ベースイメージとして軽量なAlpine LinuxベースのNode.js 18を使用
2: FROM node:18-alpine
3:
4: # コンテナ内の作業ディレクトリを/appに設定
5: WORKDIR /app
6:
7: # パッケージ管理ファイル（package.json, package-lock.json等）をコピー
8: COPY package*.json ./
9:
10: # 依存関係パッケージ（node_modules）をインストール
11: RUN npm install
12:
13: # 残りのすべてのフロントエンドのソースコードをコピー
14: COPY . .
15:
16: # Vite開発サーバーのデフォルトポート(5173)を公開することを明示
17: EXPOSE 5173
18:
19: # コンテナ起動時にVite開発サーバーを起動するコマンド
20: # --host オプションを付けることで、コンテナ外部（ホストPC等）からのアクセスを許可する
21: CMD ["npm", "run", "dev", "--", "--host"]
```
- 1行目: コメント。
- 2行目: ベースイメージとして、Node.js 18がインストール済みのAlpine Linuxイメージを使用します。
- 3行目: 空行。
- 4行目: コメント。
- 5行目: 以降のコマンドを実行するための作業ディレクトリを`/app`に指定します。
- 6行目: 空行。
- 7行目: コメント。
- 8行目: `package.json` と `package-lock.json`（存在する場合）を、コンテナの現在の作業ディレクトリにコピーします。
- 9行目: 空行。
- 10行目: コメント。
- 11行目: コンテナ内で`npm install`を実行し、依存パッケージをダウンロード・インストールします。
- 12行目: 空行。
- 13行目: コメント。
- 14行目: ホスト側の現在のディレクトリのすべてのファイルを、コンテナの作業ディレクトリにコピーします（ただし`docker-compose.yml`側のマウントで上書きされる前提の開発用設定）。
- 15行目: 空行。
- 16行目: コメント。
- 17行目: コンテナがポート5173で通信待ち受けをすることを明示します。
- 18行目: 空行。
- 19行目: コメント。
- 20行目: コメント。
- 21行目: コンテナ起動時に実行するコマンドとして、`npm run dev -- --host`を指定し、外部アクセス可能な状態でViteサーバーを立ち上げます。

## frontend/package.json
```json
1: {
2:   "name": "frontend",
3:   "private": true,
4:   "version": "0.0.0",
5:   "type": "module",
6:   "scripts": {
7:     "dev": "vite",
8:     "build": "tsc -b && vite build",
9:     "lint": "oxlint",
10:     "preview": "vite preview"
11:   },
12:   "dependencies": {
13:     "react": "^19.2.7",
14:     "react-dom": "^19.2.7"
15:   },
16:   "devDependencies": {
17:     "@types/node": "^24.13.2",
18:     "@types/react": "^19.2.17",
19:     "@types/react-dom": "^19.2.3",
20:     "@vitejs/plugin-react": "^6.0.2",
21:     "oxlint": "^1.69.0",
22:     "typescript": "~6.0.2",
23:     "vite": "^8.1.0"
24:   }
25: }
```
- 1行目: JSONオブジェクトの開始中括弧。
- 2行目: プロジェクトの名前を`"frontend"`と定義します。
- 3行目: プロジェクトを非公開（npmリポジトリに誤って公開されない）に設定します。
- 4行目: プロジェクトの初期バージョンを`"0.0.0"`とします。
- 5行目: Node.jsのモジュールシステムとしてES Modules (`import`/`export`構文) を使うことを明記します。
- 6行目: `npm run <script-name>` で実行できるコマンド（スクリプト）の定義を開始します。
- 7行目: `dev` スクリプトを実行すると、`vite` コマンドが実行され、開発サーバーが立ち上がります。
- 8行目: `build` スクリプトを実行すると、TypeScriptのコンパイル(`tsc -b`)を行った後、Viteによる本番向けビルド(`vite build`)が走ります。
- 9行目: `lint` スクリプトを実行すると、高速なリンターである `oxlint` が実行されます。
- 10行目: `preview` スクリプトを実行すると、ビルド後のファイルを確認するためのViteのプレビューサーバーが起動します。
- 11行目: `scripts` オブジェクトの終了。
- 12行目: アプリケーションの実行（本番環境）に必要なライブラリ群の定義を開始します。
- 13行目: `react` ライブラリ（バージョン19.2.7以上で、メジャーバージョンが変わらない範囲の最新版）を指定します。
- 14行目: `react-dom` ライブラリ（DOMレンダリング用）のバージョンを指定します。
- 15行目: `dependencies` オブジェクトの終了。
- 16行目: 開発環境でのみ必要なライブラリ（型定義ファイルやビルドツール）の定義を開始します。
- 17行目: Node.jsのAPIに対するTypeScriptの型定義ファイルを指定します。
- 18行目: ReactのAPIに対するTypeScriptの型定義ファイルを指定します。
- 19行目: React DOMのAPIに対するTypeScriptの型定義ファイルを指定します。
- 20行目: ViteでReactを扱うための公式プラグインを指定します。
- 21行目: 高速な静的解析ツール(Linter)である`oxlint`を指定します。
- 22行目: TypeScriptコンパイラを指定します。
- 23行目: Viteビルドツール本体を指定します。
- 24行目: `devDependencies` オブジェクトの終了。
- 25行目: JSONオブジェクトの終了中括弧。

## frontend/vite.config.ts
```typescript
1: import { defineConfig } from 'vite'
2: import react from '@vitejs/plugin-react'
3:
4: // Viteの公式ドキュメントへのリンク
5: // https://vite.dev/config/
6:
7: // Viteの開発・ビルド設定をエクスポートする
8: export default defineConfig({
9:   // Viteで使用するプラグインの配列
10:   // react()プラグインを有効化することで、JSXの変換やReactの高速なHMR(ホットモジュールリプレイスメント)が利用可能になる
11:   plugins: [react()],
12: })
```
- 1行目: `vite` パッケージから、設定オブジェクトの型推論を助ける関数 `defineConfig` をインポートします。
- 2行目: `@vitejs/plugin-react` から、React用のViteプラグインをインポートします。
- 3行目: 空行。
- 4行目: コメント。
- 5行目: コメント。
- 6行目: 空行。
- 7行目: コメント。
- 8行目: `defineConfig` を呼び出し、その結果（Viteの設定オブジェクト）をデフォルトエクスポートします。
- 9行目: コメント。
- 10行目: コメント。
- 11行目: `plugins` プロパティに、先ほどインポートした `react()` プラグインを実行した結果を配列に入れて設定します。
- 12行目: `defineConfig` に渡すオブジェクトと関数の終了中括弧・括弧。

## backend/pom.xml
```xml
1: <?xml version="1.0" encoding="UTF-8"?>
2: <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
3:          xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
4:     <modelVersion>4.0.0</modelVersion>
5:     <parent>
6:         <groupId>org.springframework.boot</groupId>
7:         <artifactId>spring-boot-starter-parent</artifactId>
8:         <version>3.2.0</version>
9:         <relativePath/> <!-- lookup parent from repository -->
10:     </parent>
11:     <groupId>com.npbpredict</groupId>
12:     <artifactId>app</artifactId>
13:     <version>0.0.1-SNAPSHOT</version>
14:     <name>NpbPredict App</name>
15:     <description>NPB Prediction Community Site Backend</description>
16:     <properties>
17:         <java.version>17</java.version>
18:     </properties>
19:     <dependencies>
20:         <!-- REST API（コントローラー等）を作成するためのSpring Web機能 -->
21:         <dependency>
22:             <groupId>org.springframework.boot</groupId>
23:             <artifactId>spring-boot-starter-web</artifactId>
24:         </dependency>
25:
26:         <!-- データベース(PostgreSQL)へアクセスするためのJPA/Hibernate機能 -->
27:         <dependency>
28:             <groupId>org.springframework.boot</groupId>
29:             <artifactId>spring-boot-starter-data-jpa</artifactId>
30:         </dependency>
31:
32:         <!-- アプリケーションを保護するためのSpring Security -->
33:         <dependency>
34:             <groupId>org.springframework.boot</groupId>
35:             <artifactId>spring-boot-starter-security</artifactId>
36:         </dependency>
37:
38:         <!-- Googleアカウント等を利用したOAuth2ログインを実装するための機能 -->
39:         <dependency>
40:             <groupId>org.springframework.boot</groupId>
41:             <artifactId>spring-boot-starter-oauth2-client</artifactId>
42:         </dependency>
43:
44:         <!-- PostgreSQLデータベースに接続するための公式JDBCドライバー -->
45:         <dependency>
46:             <groupId>org.postgresql</groupId>
47:             <artifactId>postgresql</artifactId>
48:             <scope>runtime</scope>
49:         </dependency>
50:
51:         <!-- Getter/Setterなどの定型コードを自動生成してくれるLombok -->
52:         <dependency>
53:             <groupId>org.projectlombok</groupId>
54:             <artifactId>lombok</artifactId>
55:             <optional>true</optional>
56:         </dependency>
57:
58:         <!-- Test -->
59:         <dependency>
60:             <groupId>org.springframework.boot</groupId>
61:             <artifactId>spring-boot-starter-test</artifactId>
62:             <scope>test</scope>
63:         </dependency>
64:         <dependency>
65:             <groupId>org.springframework.security</groupId>
66:             <artifactId>spring-security-test</artifactId>
67:             <scope>test</scope>
68:         </dependency>
69:     </dependencies>
70:
71:     <build>
72:         <plugins>
73:             <plugin>
74:                 <groupId>org.springframework.boot</groupId>
75:                 <artifactId>spring-boot-maven-plugin</artifactId>
76:                 <configuration>
77:                     <excludes>
78:                         <exclude>
79:                             <groupId>org.projectlombok</groupId>
80:                             <artifactId>lombok</artifactId>
81:                         </exclude>
82:                     </excludes>
83:                 </configuration>
84:             </plugin>
85:         </plugins>
86:     </build>
87:
88: </project>
```
- 1行目: XML宣言。バージョン1.0で文字エンコーディングがUTF-8であることを示します。
- 2行目: Mavenプロジェクトのルート要素 `<project>` を開始し、XML名前空間を指定します。
- 3行目: XMLスキーマの場所を指定します。
- 4行目: POMのモデルバージョンを指定します（現在は常に4.0.0）。
- 5行目: 親プロジェクトの定義 `<parent>` を開始します。
- 6行目: 親プロジェクトの `groupId` を指定します。
- 7行目: 親プロジェクトの `artifactId` に `spring-boot-starter-parent` を指定します（Spring Boot標準の依存ライブラリバージョン管理を継承します）。
- 8行目: Spring Bootのバージョン `3.2.0` を指定します。
- 9行目: ローカルパスからではなく、リモートリポジトリから親POMを探すように指定します。
- 10行目: 親プロジェクトの定義終了。
- 11行目: 当プロジェクトの `groupId`（一意の識別子）を `com.npbpredict` と定義します。
- 12行目: 当プロジェクトの `artifactId`（プロジェクト名）を `app` と定義します。
- 13行目: 当プロジェクトのバージョンを `0.0.1-SNAPSHOT` と定義します。
- 14行目: プロジェクトの表示名（人間が読む用）を定義します。
- 15行目: プロジェクトの説明文を定義します。
- 16行目: Mavenのカスタムプロパティの定義を開始します。
- 17行目: プロパティ `java.version` に `17` を設定し、コンパイル時等に使用するJavaのバージョンを指定します。
- 18行目: プロパティ定義の終了。
- 19行目: 依存ライブラリ `<dependencies>` の定義を開始します。
- 20行目: コメント。
- 21行目: 依存ライブラリ定義開始。
- 22行目: `groupId`。
- 23行目: `artifactId` が `spring-boot-starter-web` (REST APIなどのWeb機能群)。
- 24行目: 依存ライブラリ定義終了。
- 25行目: 空行。
- 26行目: コメント。
- 27行目: 依存ライブラリ定義開始。
- 28行目: `groupId`。
- 29行目: `artifactId` が `spring-boot-starter-data-jpa` (データベースアクセス機能)。
- 30行目: 依存ライブラリ定義終了。
- 31行目: 空行。
- 32行目: コメント。
- 33行目: 依存ライブラリ定義開始。
- 34行目: `groupId`。
- 35行目: `artifactId` が `spring-boot-starter-security` (認証・認可機能)。
- 36行目: 依存ライブラリ定義終了。
- 37行目: 空行。
- 38行目: コメント。
- 39行目: 依存ライブラリ定義開始。
- 40行目: `groupId`。
- 41行目: `artifactId` が `spring-boot-starter-oauth2-client` (OAuth2クライアント機能)。
- 42行目: 依存ライブラリ定義終了。
- 43行目: 空行。
- 44行目: コメント。
- 45行目: 依存ライブラリ定義開始。
- 46行目: `groupId`。
- 47行目: `artifactId` が `postgresql` (PostgreSQL接続ドライバ)。
- 48行目: `scope` が `runtime` (コンパイル時には不要で、実行時にのみ必要)。
- 49行目: 依存ライブラリ定義終了。
- 50行目: 空行。
- 51行目: コメント。
- 52行目: 依存ライブラリ定義開始。
- 53行目: `groupId`。
- 54行目: `artifactId` が `lombok` (ボイラープレート削減ツール)。
- 55行目: `optional` を `true` にし、このライブラリが他のプロジェクトに推移的に依存しないよう設定します。
- 56行目: 依存ライブラリ定義終了。
- 57行目: 空行。
- 58行目: コメント。
- 59行目: 依存ライブラリ定義開始。
- 60行目: `groupId`。
- 61行目: `artifactId` が `spring-boot-starter-test` (テストフレームワーク群)。
- 62行目: `scope` が `test` (テスト時にのみ必要)。
- 63行目: 依存ライブラリ定義終了。
- 64行目: 依存ライブラリ定義開始。
- 65行目: `groupId`。
- 66行目: `artifactId` が `spring-security-test` (セキュリティのテスト機能)。
- 67行目: `scope` が `test`。
- 68行目: 依存ライブラリ定義終了。
- 69行目: 依存ライブラリ一覧 `<dependencies>` の終了。
- 70行目: 空行。
- 71行目: ビルドプロセスの設定 `<build>` の開始。
- 72行目: ビルドプラグイン `<plugins>` の開始。
- 73行目: プラグイン `<plugin>` の開始。
- 74行目: `groupId`。
- 75行目: `artifactId` が `spring-boot-maven-plugin` (Spring Bootアプリケーションをパッキングするプラグイン)。
- 76行目: プラグインの設定 `<configuration>` の開始。
- 77行目: 除外設定 `<excludes>` の開始。
- 78行目: 除外対象 `<exclude>` の開始。
- 79行目: `groupId`。
- 80行目: `artifactId` が `lombok`。これにより、最終的な実行用JARファイルにLombokのコードが含まれず、容量を削減します。
- 81行目: 除外対象の終了。
- 82行目: 除外設定の終了。
- 83行目: 設定の終了。
- 84行目: プラグインの終了。
- 85行目: プラグイン一覧の終了。
- 86行目: ビルド設定の終了。
- 87行目: 空行。
- 88行目: Mavenプロジェクト要素 `<project>` の終了。

## backend/src/main/resources/application.yml
```yaml
1: # Spring Bootアプリケーション全体の設定ファイル
2: spring:
3:   # データベース接続に関する設定
4:   datasource:
5:     # 接続先URL。docker-composeで設定したサービス名 'postgres' をホストとして指定。
6:     url: jdbc:postgresql://postgres:5432/npb_prediction
7:     # データベースにアクセスするためのユーザー名
8:     username: npb_user
9:     # データベースにアクセスするためのパスワード
10:     password: npb_password
11:     # 使用するJDBCドライバーのクラス名
12:     driver-class-name: org.postgresql.Driver
13:
14:   # JPA (Java Persistence API) と Hibernate の設定
15:   jpa:
16:     hibernate:
17:       # アプリケーション起動時に、Entityクラスの定義からDBのテーブル構造を自動で更新(update)する
18:       ddl-auto: update
19:     # 実行されたSQL文をコンソールに表示する設定
20:     show-sql: true
21:     properties:
22:       hibernate:
23:         # コンソールに出力されるSQLを整形（フォーマット）して読みやすくする
24:         format_sql: true
25:         # PostgreSQLに特化したSQLを生成するためのDialect（方言）を指定
26:         dialect: org.hibernate.dialect.PostgreSQLDialect
27:
28:   # セキュリティ (OAuth2ログイン) の設定
29:   security:
30:     oauth2:
31:       client:
32:         registration:
33:           # Googleを使用したOAuth2ログインの登録情報
34:           google:
35:             # Google Cloud Consoleで取得したクライアントID (プレースホルダー)
36:             client-id: YOUR_GOOGLE_CLIENT_ID
37:             # Google Cloud Consoleで取得したクライアントシークレット (プレースホルダー)
38:             client-secret: YOUR_GOOGLE_CLIENT_SECRET
39:             # 取得する情報の範囲（プロフィール情報とメールアドレス）
40:             scope: profile, email
41:
42: # サーバーに関する設定
43: server:
44:   # Spring Bootアプリケーションが起動してリクエストを待ち受けるポート番号
45:   port: 8080
```
- 1行目: コメント。
- 2行目: Spring frameworkのコア設定プロパティ群を開始します。
- 3行目: コメント。
- 4行目: データソース（データベース接続）に関する設定を開始します。
- 5行目: コメント。
- 6行目: データベース接続文字列(URL)を設定します。Dockerのネットワークにより `postgres` というホスト名で接続できます。
- 7行目: コメント。
- 8行目: データベースのユーザー名を設定します。
- 9行目: コメント。
- 10行目: データベースのパスワードを設定します。
- 11行目: コメント。
- 12行目: PostgreSQLのJDBCドライバクラスを明示的に指定します。
- 13行目: 空行。
- 14行目: コメント。
- 15行目: JPA(Java Persistence API)の設定を開始します。
- 16行目: JPAの実装であるHibernate固有の設定を開始します。
- 17行目: コメント。
- 18行目: アプリ起動時に、DBのスキーマをJavaコード（Entity）に合わせて自動更新（`update`）するよう設定します。
- 19行目: コメント。
- 20行目: Hibernateが生成・実行したSQL文をログに出力する設定を有効(`true`)にします。
- 21行目: Hibernateのより詳細なプロパティ設定を開始します。
- 22行目: `hibernate`プレフィックスを持つプロパティ。
- 23行目: コメント。
- 24行目: ログに出力されるSQL文を改行・インデントして読みやすくフォーマットする設定を有効(`true`)にします。
- 25行目: コメント。
- 26行目: Hibernateが生成するSQLの方言としてPostgreSQL用のDialectを指定します。
- 27行目: 空行。
- 28行目: コメント。
- 29行目: セキュリティ関連の設定を開始します。
- 30行目: OAuth2機能の設定を開始します。
- 31行目: OAuth2クライアントとしての設定を開始します。
- 32行目: 各プロバイダ（Googleなど）の登録情報設定を開始します。
- 33行目: コメント。
- 34行目: プロバイダ `google` 用の設定を開始します。
- 35行目: コメント。
- 36行目: Googleログイン用のクライアントIDを設定します（プレースホルダー）。
- 37行目: コメント。
- 38行目: Googleログイン用のクライアントシークレットを設定します（プレースホルダー）。
- 39行目: コメント。
- 40行目: 認証時にGoogleから取得を要求する情報（スコープ）を `profile` と `email` に指定します。
- 41行目: 空行。
- 42行目: コメント。
- 43行目: Tomcat内蔵サーバーの起動設定を開始します。
- 44行目: コメント。
- 45行目: サーバーがリッスンするポートを `8080` に設定します。

## backend/src/main/java/com/npbpredict/app/entity/User.java
```java
1: package com.npbpredict.app.entity;
2:
3: import jakarta.persistence.*;
4: import lombok.Getter;
5: import lombok.Setter;
6: import lombok.NoArgsConstructor;
7: import lombok.AllArgsConstructor;
8:
9: /**
10:  * ユーザー情報を表すEntityクラス。
11:  * DBの "users" テーブルと紐付きます。
12:  */
13: @Entity // このクラスがJPAのEntityであることを示します
14: @Table(name = "users") // マッピングするテーブル名を "users" に指定します
15: @Getter // Lombok: すべてのフィールドのgetterを自動生成します
16: @Setter // Lombok: すべてのフィールドのsetterを自動生成します
17: @NoArgsConstructor // Lombok: 引数なしのデフォルトコンストラクタを自動生成します
18: @AllArgsConstructor // Lombok: 全フィールドを引数に取るコンストラクタを自動生成します
19: public class User {
20:
21:     /**
22:      * 内部ユーザーID（プライマリキー）。
23:      * データベース側で自動的に連番が振られます(GenerationType.IDENTITY)。
24:      */
25:     @Id
26:     @GeneratedValue(strategy = GenerationType.IDENTITY)
27:     private Long id;
28:
29:     /**
30:      * Google OAuth2認証時に取得する一意の識別子。
31:      * 重複を許さず(unique = true)、必須項目(nullable = false)とします。
32:      */
33:     @Column(name = "google_sub_id", unique = true, nullable = false)
34:     private String googleSubId;
35:
36:     /**
37:      * アプリケーション内で表示されるユーザーのニックネーム。
38:      * 重複を許さず、必須項目とします。
39:      */
40:     @Column(unique = true, nullable = false)
41:     private String nickname;
42:
43:     /**
44:      * ユーザーのお気に入り球団。
45:      */
46:     @Column(name = "favorite_team")
47:     private String favoriteTeam;
48:
49:     /**
50:      * ユーザーが獲得した累計ポイント。
51:      * デフォルト値は0で、必須項目です。
52:      */
53:     @Column(name = "total_points", nullable = false)
54:     private Integer totalPoints = 0;
55: }
```
- 1行目: このファイルが所属するパッケージ（ディレクトリ構造）を宣言します。
- 2行目: 空行。
- 3行目: JPA(Java Persistence API)のアノテーション等（Entity, Table, Idなど）を一括でインポートします。
- 4行目: Lombokの `@Getter` アノテーションをインポートします。
- 5行目: Lombokの `@Setter` アノテーションをインポートします。
- 6行目: Lombokの `@NoArgsConstructor` アノテーションをインポートします。
- 7行目: Lombokの `@AllArgsConstructor` アノテーションをインポートします。
- 8行目: 空行。
- 9-12行目: Javadocコメント。このクラスがユーザー情報を表すEntityであることを説明します。
- 13行目: このクラスがDBのテーブルとマッピングされるJPAエンティティであることを指定する `@Entity` アノテーション。
- 14行目: このエンティティがマッピングされるDB上のテーブル名を `"users"` と指定する `@Table` アノテーション。
- 15行目: クラス内の全フィールドに対するGetterメソッドをコンパイル時に自動生成する `@Getter` アノテーション。
- 16行目: クラス内の全フィールドに対するSetterメソッドを自動生成する `@Setter` アノテーション。
- 17行目: 引数のないデフォルトコンストラクタを自動生成する `@NoArgsConstructor` アノテーション（JPAでは必須）。
- 18行目: すべてのフィールドを引数に取るコンストラクタを自動生成する `@AllArgsConstructor` アノテーション。
- 19行目: `User` クラスの定義を開始します。
- 20行目: 空行。
- 21-24行目: Javadocコメント。IDフィールドの説明。
- 25行目: フィールド `id` がこのエンティティの主キー(PK)であることを示す `@Id` アノテーション。
- 26行目: 主キーの値がデータベースの自動増分カラム（連番）によって生成されることを示す `@GeneratedValue` アノテーション。
- 27行目: 内部ユーザーIDを保持する `Long` 型のフィールド変数 `id`。
- 28行目: 空行。
- 29-32行目: Javadocコメント。Googleの識別子フィールドの説明。
- 33行目: テーブル上のカラム名を `google_sub_id`、一意制約(`unique=true`)、非Null制約(`nullable=false`)とする `@Column` アノテーション。
- 34行目: Google認証の一意なIDを保持する `String` 型のフィールド変数 `googleSubId`。
- 35行目: 空行。
- 36-39行目: Javadocコメント。ニックネームフィールドの説明。
- 40行目: 一意制約(`unique=true`)と非Null制約(`nullable=false`)を持つ `@Column` アノテーション（カラム名はフィールド名から自動推定され `nickname` となる）。
- 41行目: ユーザーのニックネームを保持する `String` 型のフィールド変数 `nickname`。
- 42行目: 空行。
- 43-45行目: Javadocコメント。お気に入り球団フィールドの説明。
- 46行目: カラム名を `favorite_team` とする `@Column` アノテーション。
- 47行目: お気に入り球団の名前を保持する `String` 型のフィールド変数 `favoriteTeam`。
- 48行目: 空行。
- 49-52行目: Javadocコメント。累計ポイントフィールドの説明。
- 53行目: カラム名を `total_points`、非Null制約とする `@Column` アノテーション。
- 54行目: ユーザーのポイントを保持する `Integer` 型のフィールド変数 `totalPoints`。初期値を `0` に設定しています。
- 55行目: クラス定義の終了中括弧。

## backend/src/main/java/com/npbpredict/app/entity/Game.java
```java
1: package com.npbpredict.app.entity;
2:
3: import jakarta.persistence.*;
4: import lombok.Getter;
5: import lombok.Setter;
6: import lombok.NoArgsConstructor;
7: import lombok.AllArgsConstructor;
8: import java.time.LocalDate;
9: import java.time.LocalDateTime;
10:
11: /**
12:  * 試合情報を表すEntityクラス。
13:  * DBの "games" テーブルと紐付きます。
14:  */
15: @Entity // このクラスがJPAのEntityであることを示します
16: @Table(name = "games") // マッピングするテーブル名を "games" に指定します
17: @Getter // Lombok: getterを自動生成
18: @Setter // Lombok: setterを自動生成
19: @NoArgsConstructor // Lombok: デフォルトコンストラクタを生成
20: @AllArgsConstructor // Lombok: 全引数コンストラクタを生成
21: public class Game {
22:
23:     /**
24:      * 試合ID（プライマリキー）。自動採番されます。
25:      */
26:     @Id
27:     @GeneratedValue(strategy = GenerationType.IDENTITY)
28:     private Long id;
29:
30:     /**
31:      * 試合が開催される日付。
32:      */
33:     @Column(name = "game_date", nullable = false)
34:     private LocalDate gameDate;
35:
36:     /**
37:      * 試合開始時刻。この時刻を過ぎると予想の投票・変更が締め切られます。
38:      */
39:     @Column(name = "start_time", nullable = false)
40:     private LocalDateTime startTime;
41:
42:     /**
43:      * ホームチームの名前。
44:      */
45:     @Column(name = "home_team", nullable = false)
46:     private String homeTeam;
47:
48:     /**
49:      * ビジター（アウェイ）チームの名前。
50:      */
51:     @Column(name = "away_team", nullable = false)
52:     private String awayTeam;
53:
54:     /**
55:      * 試合の現在の状態（試合前、投票締切後、試合終了）。
56:      * 列挙型(Enum)の文字列表現としてデータベースに保存します。
57:      * デフォルトは BEFORE_GAME（試合前）です。
58:      */
59:     @Enumerated(EnumType.STRING)
60:     @Column(nullable = false)
61:     private GameStatus status = GameStatus.BEFORE_GAME;
62:
63:     /**
64:      * 試合結果（ホーム勝ち、アウェイ勝ち、引き分け）。
65:      * 試合終了後に管理者が入力するまで null になります。
66:      */
67:     @Enumerated(EnumType.STRING)
68:     private GameResult result;
69:
70:     /**
71:      * 試合の状態を表す列挙型
72:      */
73:     public enum GameStatus {
74:         BEFORE_GAME,   // 試合前（投票受付中）
75:         VOTING_CLOSED, // 投票締切後（試合開始〜終了前）
76:         GAME_END       // 試合終了（結果確定）
77:     }
78:
79:     /**
80:      * 試合結果を表す列挙型
81:      */
82:     public enum GameResult {
83:         HOME, // ホームチームの勝利
84:         AWAY, // アウェイチームの勝利
85:         DRAW  // 引き分け
86:     }
87: }
```
- 1行目: パッケージの宣言。
- 2行目: 空行。
- 3行目: JPAのアノテーション群のインポート。
- 4-7行目: Lombok関連のアノテーションをインポート。
- 8行目: 日付のみを扱う `LocalDate` クラスをインポート。
- 9行目: 日付と時刻を扱う `LocalDateTime` クラスをインポート。
- 10行目: 空行。
- 11-14行目: Javadocコメント。
- 15行目: JPAエンティティであることを示す `@Entity`。
- 16行目: DB上のテーブル名を `games` に指定する `@Table`。
- 17-20行目: Lombokによるボイラープレート自動生成の設定群。
- 21行目: `Game` クラスの定義開始。
- 22行目: 空行。
- 23-25行目: Javadocコメント。
- 26行目: 主キーを示す `@Id`。
- 27行目: 連番により自動生成されることを示す `@GeneratedValue`。
- 28行目: 試合の内部IDを保持する `Long` 型フィールド `id`。
- 29行目: 空行。
- 30-32行目: Javadocコメント。
- 33行目: カラム名 `game_date`、非Nullとする `@Column`。
- 34行目: 試合日を保持する `LocalDate` 型フィールド `gameDate`。
- 35行目: 空行。
- 36-38行目: Javadocコメント。
- 39行目: カラム名 `start_time`、非Nullとする `@Column`。
- 40行目: 試合開始時刻を保持する `LocalDateTime` 型フィールド `startTime`。
- 41行目: 空行。
- 42-44行目: Javadocコメント。
- 45行目: カラム名 `home_team`、非Nullとする `@Column`。
- 46行目: ホームチーム名を保持する `String` 型フィールド `homeTeam`。
- 47行目: 空行。
- 48-50行目: Javadocコメント。
- 51行目: カラム名 `away_team`、非Nullとする `@Column`。
- 52行目: アウェイチーム名を保持する `String` 型フィールド `awayTeam`。
- 53行目: 空行。
- 54-58行目: Javadocコメント。
- 59行目: Enum（列挙型）の値を、DBに保存する際「数値(0,1,2..)」ではなく「文字列表現(文字列の"BEFORE_GAME"等)」として保存するよう指示する `@Enumerated(EnumType.STRING)` アノテーション。
- 60行目: 非Null制約を示す `@Column`。
- 61行目: 試合のステータスを保持する `GameStatus` 型のフィールド。初期値を `BEFORE_GAME` に設定しています。
- 62行目: 空行。
- 63-66行目: Javadocコメント。
- 67行目: 文字列表現でEnumをDB保存する `@Enumerated(EnumType.STRING)`。
- 68行目: 試合結果を保持する `GameResult` 型のフィールド。
- 69行目: 空行。
- 70-72行目: Javadocコメント。
- 73行目: 試合ステータスを表すインナーEnum（列挙型） `GameStatus` の定義開始。
- 74行目: `BEFORE_GAME` (試合前) の列挙子。
- 75行目: `VOTING_CLOSED` (投票締切後) の列挙子。
- 76行目: `GAME_END` (試合終了) の列挙子。
- 77行目: Enum `GameStatus` の定義終了。
- 78行目: 空行。
- 79-81行目: Javadocコメント。
- 82行目: 試合結果を表すインナーEnum `GameResult` の定義開始。
- 83行目: `HOME` (ホーム勝利) の列挙子。
- 84行目: `AWAY` (アウェイ勝利) の列挙子。
- 85行目: `DRAW` (引き分け) の列挙子。
- 86行目: Enum `GameResult` の定義終了。
- 87行目: `Game` クラスの定義終了。

## backend/src/main/java/com/npbpredict/app/entity/Prediction.java
```java
1: package com.npbpredict.app.entity;
2:
3: import jakarta.persistence.*;
4: import lombok.Getter;
5: import lombok.Setter;
6: import lombok.NoArgsConstructor;
7: import lombok.AllArgsConstructor;
8:
9: /**
10:  * ユーザーの予想データを表すEntityクラス。
11:  * DBの "predictions" テーブルと紐付きます。
12:  */
13: @Entity
14: @Table(name = "predictions")
15: @Getter // Lombok: getterを自動生成
16: @Setter // Lombok: setterを自動生成
17: @NoArgsConstructor // Lombok: デフォルトコンストラクタを生成
18: @AllArgsConstructor // Lombok: 全引数コンストラクタを生成
19: public class Prediction {
20:
21:     /**
22:      * 予想ID（プライマリキー）。自動採番されます。
23:      */
24:     @Id
25:     @GeneratedValue(strategy = GenerationType.IDENTITY)
26:     private Long id;
27:
28:     /**
29:      * 予想を行ったユーザーへの参照（外部キー）。
30:      * 遅延ロード(FetchType.LAZY)を指定し、必要な時だけユーザー情報を取得します。
31:      */
32:     @ManyToOne(fetch = FetchType.LAZY)
33:     @JoinColumn(name = "user_id", nullable = false)
34:     private User user;
35:
36:     /**
37:      * 予想対象の試合への参照（外部キー）。
38:      * 遅延ロード(FetchType.LAZY)を指定します。
39:      */
40:     @ManyToOne(fetch = FetchType.LAZY)
41:     @JoinColumn(name = "game_id", nullable = false)
42:     private Game game;
43:
44:     /**
45:      * ユーザーが予想した勝者（ホーム、アウェイ、引き分け）。
46:      * Game.GameResult 列挙型の文字列表現として保存されます。
47:      */
48:     @Enumerated(EnumType.STRING)
49:     @Column(name = "predicted_winner", nullable = false)
50:     private Game.GameResult predictedWinner;
51:
52:     /**
53:      * 予想が的中したかどうかを示すフラグ。
54:      * 試合結果が確定するまでは null になります。
55:      */
56:     @Column(name = "is_correct")
57:     private Boolean isCorrect;
58: }
```
- 1行目: パッケージの宣言。
- 2行目: 空行。
- 3行目: JPAアノテーション群のインポート。
- 4-7行目: Lombok関連アノテーションのインポート。
- 8行目: 空行。
- 9-12行目: Javadocコメント。
- 13行目: JPAエンティティであることを示す `@Entity`。
- 14行目: マッピングするテーブル名を `predictions` とする `@Table`。
- 15-18行目: Lombokによるボイラープレート自動生成の設定群。
- 19行目: `Prediction` クラスの定義開始。
- 20行目: 空行。
- 21-23行目: Javadocコメント。
- 24行目: 主キーを示す `@Id`。
- 25行目: 自動採番を示す `@GeneratedValue`。
- 26行目: 内部IDを保持する `Long` 型フィールド `id`。
- 27行目: 空行。
- 28-31行目: Javadocコメント。ユーザーとのリレーションを説明。
- 32行目: 「多対一」のリレーションを示す `@ManyToOne`。`FetchType.LAZY` は、`Prediction` を取得した際に `User` の情報をDBから即座には引かず、`getUser()` が呼ばれたタイミングで初めてSQLを実行する設定（パフォーマンス向上策）です。
- 33行目: 外部キーのカラム名を `user_id` とし、非Nullとする `@JoinColumn` アノテーション。
- 34行目: この予想を行ったユーザーを表す `User` 型フィールド `user`。
- 35行目: 空行。
- 36-39行目: Javadocコメント。試合とのリレーションを説明。
- 40行目: 試合に対する「多対一」のリレーションを示す `@ManyToOne`。遅延ロード設定。
- 41行目: 外部キーのカラム名を `game_id` とし、非Nullとする `@JoinColumn`。
- 42行目: 対象の試合を表す `Game` 型フィールド `game`。
- 43行目: 空行。
- 44-47行目: Javadocコメント。予想内容の説明。
- 48行目: Enumの文字列表現保存を示す `@Enumerated`。
- 49行目: カラム名を `predicted_winner` とし非Nullとする `@Column`。
- 50行目: 予想した勝敗を表す `Game.GameResult` 型フィールド `predictedWinner`。
- 51行目: 空行。
- 52-55行目: Javadocコメント。結果フラグの説明。
- 56行目: カラム名を `is_correct` とする `@Column`。
- 57行目: 予想が当たったかを示す `Boolean` 型フィールド `isCorrect`（nullを取り得るためプリミティブ型の `boolean` ではなくラッパークラス）。
- 58行目: クラス定義終了。

## backend/src/main/java/com/npbpredict/app/controller/GameController.java
```java
1: package com.npbpredict.app.controller;
2:
3: import com.npbpredict.app.entity.Game;
4: import com.npbpredict.app.entity.Prediction;
5: import com.npbpredict.app.repository.GameRepository;
6: import com.npbpredict.app.repository.PredictionRepository;
7: import org.springframework.beans.factory.annotation.Autowired;
8: import org.springframework.http.HttpStatus;
9: import org.springframework.http.ResponseEntity;
10: import org.springframework.web.bind.annotation.*;
11: import org.springframework.web.server.ResponseStatusException;
12:
13: import java.time.LocalDateTime;
14: import java.time.ZoneId;
15: import java.util.HashMap;
16: import java.util.List;
17: import java.util.Map;
18: import java.util.stream.Collectors;
19:
20: /**
21:  * 試合に関するリクエストを処理するREST APIコントローラー。
22:  * 全てのエンドポイントは /api/games をベースにします。
23:  */
24: @RestController // JSONレスポンスを返すControllerであることを示します
25: @RequestMapping("/api/games")
26: public class GameController {
27:
28:     // Gameデータのデータベース操作を行うためのリポジトリを自動で注入(DI)します
29:     @Autowired
30:     private GameRepository gameRepository;
31:
32:     // 予想データのデータベース操作を行うためのリポジトリを自動で注入(DI)します
33:     @Autowired
34:     private PredictionRepository predictionRepository;
35:
36:     /**
37:      * 試合開始前（または後）のオッズ（投票比率）を取得するAPI。
38:      * ユーザー個別の予想は含まず、集計データのみを返します。
39:      * URL: GET /api/games/{gameId}/odds
40:      */
41:     @GetMapping("/{gameId}/odds")
42:     public ResponseEntity<Map<String, Object>> getGameOdds(@PathVariable Long gameId) {
43:         // 指定されたIDの試合が存在するか確認し、なければ404 NotFoundエラーを返します
44:         Game game = gameRepository.findById(gameId)
45:                 .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found"));
46:
47:         // その試合に対する全ユーザーの予想データを取得
48:         List<Prediction> predictions = predictionRepository.findByGameId(gameId);
49:
50:         // ホーム、アウェイ、引き分けのそれぞれの投票数をカウント
51:         long homeVotes = predictions.stream()
52:                 .filter(p -> p.getPredictedWinner() == Game.GameResult.HOME)
53:                 .count();
54:         long awayVotes = predictions.stream()
55:                 .filter(p -> p.getPredictedWinner() == Game.GameResult.AWAY)
56:                 .count();
57:         long drawVotes = predictions.stream()
58:                 .filter(p -> p.getPredictedWinner() == Game.GameResult.DRAW)
59:                 .count();
60:
61:         // 総投票数を計算
62:         long totalVotes = homeVotes + awayVotes + drawVotes;
63:
64:         // レスポンス用のMapを作成し、総投票数と比率(%)を計算して格納
65:         Map<String, Object> response = new HashMap<>();
66:         response.put("totalVotes", totalVotes);
67:         if (totalVotes > 0) {
68:             response.put("homeRatio", (double) homeVotes / totalVotes * 100);
69:             response.put("awayRatio", (double) awayVotes / totalVotes * 100);
70:             response.put("drawRatio", (double) drawVotes / totalVotes * 100);
71:         } else {
72:             response.put("homeRatio", 0.0);
73:             response.put("awayRatio", 0.0);
74:             response.put("drawRatio", 0.0);
75:         }
76:
77:         // HTTP 200 OK と共にJSON形式でデータを返却
78:         return ResponseEntity.ok(response);
79:     }
80:
81:     /**
82:      * 試合開始後に、「誰がどちらに投票したか」の具体的な内訳を取得するAPI。
83:      * カンニング防止のため、試合開始前はアクセスが拒否されます。
84:      * URL: GET /api/games/{gameId}/predictions
85:      */
86:     @GetMapping("/{gameId}/predictions")
87:     public ResponseEntity<List<Map<String, String>>> getGamePredictions(@PathVariable Long gameId) {
88:         // 試合情報の取得
89:         Game game = gameRepository.findById(gameId)
90:                 .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found"));
91:
92:         // 【カンニング防止ロジック】
93:         // 日本時間(Asia/Tokyo)で現在時刻を取得し、試合開始前であれば 403 Forbidden エラーを返す
94:         if (LocalDateTime.now(ZoneId.of("Asia/Tokyo")).isBefore(game.getStartTime())) {
95:             throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Predictions are hidden before the game starts.");
96:         }
97:
98:         // 試合開始後であれば、ユーザー情報も含めて予想データをDBから取得
99:         List<Prediction> predictions = predictionRepository.findByGameIdWithUser(gameId);
100:
101:         // ユーザー名(nickname)と予想内容(predictedWinner)のリストに変換して返す
102:         List<Map<String, String>> response = predictions.stream().map(p -> {
103:             Map<String, String> map = new HashMap<>();
104:             map.put("nickname", p.getUser().getNickname());
105:             map.put("predictedWinner", p.getPredictedWinner().name());
106:             return map;
107:         }).collect(Collectors.toList());
108:
109:         return ResponseEntity.ok(response);
110:     }
111: }
```
- 1行目: パッケージの宣言。
- 2行目: 空行。
- 3-6行目: 自作のEntityクラスやRepositoryインターフェースをインポート。
- 7-11行目: Spring Web関連のアノテーション（`@Autowired`, `@RestController` など）や例外クラスをインポート。
- 12行目: 空行。
- 13-18行目: 日付処理用のクラス群や、リスト操作、Stream APIなどの標準Javaクラスをインポート。
- 19行目: 空行。
- 20-23行目: Javadocコメント。
- 24行目: このクラスがREST APIのエンドポイント（JSONを返す）であることを示す `@RestController`。
- 25行目: クラス全体に対するベースURLを `/api/games` に設定する `@RequestMapping`。
- 26行目: `GameController` クラスの定義開始。
- 27行目: 空行。
- 28行目: コメント。
- 29行目: SpringのDI（依存性の注入）を利用してリポジトリのインスタンスを注入させる `@Autowired`。
- 30行目: DBから試合情報を取得するための `GameRepository` フィールド。
- 31行目: 空行。
- 32行目: コメント。
- 33行目: `@Autowired`。
- 34行目: 予想情報を取得するための `PredictionRepository` フィールド。
- 35行目: 空行。
- 36-40行目: Javadocコメント。オッズ取得APIの説明。
- 41行目: HTTP GETメソッドで URLパスが `/{gameId}/odds` へのリクエストを処理することを示す `@GetMapping`。
- 42行目: `getGameOdds` メソッド定義。URLパス内の `{gameId}` を引数 `gameId` として受け取ります(`@PathVariable`)。レスポンスはJSONオブジェクト(`Map`)を含んだHTTPレスポンス。
- 43行目: コメント。
- 44行目: `gameRepository` を使ってIDで試合を検索します。
- 45行目: もし見つからなければ、HTTPステータス `404 NOT_FOUND` を返す `ResponseStatusException` をスロー（エラー終了）します。
- 46行目: 空行。
- 47行目: コメント。
- 48行目: `predictionRepository` を使い、その試合に関連するすべての予想レコードをリストとして取得します。
- 49行目: 空行。
- 50行目: コメント。
- 51行目: 取得した予想リストからStream（パイプライン処理）を生成します。
- 52行目: 予想結果が `HOME` のものだけをフィルター（抽出）します。
- 53行目: その件数をカウントし、`homeVotes` に代入します。
- 54-56行目: 同様にして、`AWAY` と予想された件数をカウントし、`awayVotes` に代入します。
- 57-59行目: 同様にして、`DRAW` と予想された件数をカウントし、`drawVotes` に代入します。
- 60行目: コメント。
- 61行目: ホーム、アウェイ、引き分けの票数をすべて足して、総投票数 `totalVotes` を計算します。
- 62行目: 空行。
- 63行目: コメント。
- 64行目: クライアントに返すJSONの元となる `HashMap` (キーが文字列、値が任意のオブジェクト) を生成します。
- 65行目: レスポンスマップに総投票数(`totalVotes`)を格納します。
- 66行目: もし総投票数が0より大きい場合（誰かが投票している場合）の分岐。
- 67行目: ホームの得票比率(%)を計算し、`homeRatio` として格納します。（`double`キャストによって小数の割り算を行います）。
- 68行目: アウェイの得票比率(%)を計算して格納します。
- 69行目: 引き分けの得票比率(%)を計算して格納します。
- 70行目: 誰も投票していない（0票）場合の `else` 分岐。
- 71-73行目: エラー（ゼロ除算）を防ぐため、すべての比率に `0.0` を明示的に設定します。
- 74行目: `if-else` ブロックの終了。
- 75行目: 空行。
- 76行目: コメント。
- 77行目: 作成したレスポンス用マップを、HTTPステータス `200 OK` とともに返却します。Springが自動的にJSONに変換してくれます。
- 78行目: `getGameOdds` メソッドの終了。
- 79行目: 空行。
- 80-84行目: Javadocコメント。投票内訳取得APIの説明。
- 85行目: HTTP GETで `/{gameId}/predictions` へのリクエストを処理する `@GetMapping`。
- 86行目: `getGamePredictions` メソッド定義。返り値は、配列(List)の中にオブジェクト(Map)が入ったJSON構造です。
- 87行目: コメント。
- 88-89行目: 先ほどと同様に、IDから対象の試合を検索し、なければ404エラー。
- 90行目: 空行。
- 91行目: コメント（カンニング防止）。
- 92行目: コメント。
- 93行目: 日本時間(`Asia/Tokyo`)での「現在時刻」を取得し、それが試合の「開始時刻」より前(`isBefore`)であるかを判定します。
- 94行目: 試合前であれば、HTTPステータス `403 FORBIDDEN` を返す例外をスローし、クライアントに情報を隠蔽します。
- 95行目: `if`文の終了。
- 96行目: 空行。
- 97行目: コメント。
- 98行目: 試合開始後なので、N+1問題を回避する独自クエリメソッド（`findByGameIdWithUser`）を呼び出し、予想一覧と「誰がしたか(User)」の情報を同時に取得します。
- 99行目: 空行。
- 100行目: コメント。
- 101行目: 取得した予想EntityのリストをStreamに変換し、`map` 操作によって別の形（この場合はレスポンス用の `Map`）に変換します。
- 102行目: 個々の要素を処理するためのラムダ式の開始。空の `HashMap` を作ります。
- 103行目: 予想データからUserエンティティをたどり、ニックネームを取得して `nickname` キーに格納します。
- 104行目: 予想先(`HOME`等)のEnumを文字列(`.name()`)に変換し、`predictedWinner` キーに格納します。
- 105行目: 作成したMapをラムダ式の戻り値として返します。
- 106行目: Streamの `map` 操作の終了と、結果を再び `List` としてまとめる `.collect(Collectors.toList())` を実行し、結果を `response` に代入します。
- 107行目: 空行。
- 108行目: 出来上がったリストを HTTPステータス `200 OK` で返却します。
- 109行目: `getGamePredictions` メソッドの終了。
- 110行目: `GameController` クラスの終了。

## backend/src/main/java/com/npbpredict/app/repository/GameRepository.java
```java
1: package com.npbpredict.app.repository;
2:
3: import com.npbpredict.app.entity.Game;
4: import org.springframework.data.jpa.repository.JpaRepository;
5: import org.springframework.stereotype.Repository;
6:
7: /**
8:  * Gameエンティティに対するデータベース操作（CRUDなど）を提供するRepositoryインターフェース。
9:  * Spring Data JPAにより、基本的なメソッドは自動的に実装されます。
10:  */
11: @Repository
12: public interface GameRepository extends JpaRepository<Game, Long> {
13: }
```
- 1行目: パッケージの宣言。
- 2行目: 空行。
- 3-5行目: EntityクラスおよびSpring Data JPA関連クラスのインポート。
- 6行目: 空行。
- 7-10行目: Javadocコメント。
- 11行目: このインターフェースがSpringのRepository（データアクセス層のコンポーネント）であることを示す `@Repository` アノテーション。
- 12行目: `Game` エンティティ（主キーの型は `Long`）を扱うリポジトリとして、`JpaRepository` を継承します。これにより `save`, `findById`, `findAll` 等のSQL実装コードを書かずに使えるようになります。
- 13行目: インターフェースの終了中括弧。独自メソッドを定義していないため中は空です。

## backend/src/main/java/com/npbpredict/app/repository/PredictionRepository.java
```java
1: package com.npbpredict.app.repository;
2:
3: import com.npbpredict.app.entity.Prediction;
4: import org.springframework.data.jpa.repository.JpaRepository;
5: import org.springframework.data.jpa.repository.Query;
6: import org.springframework.data.repository.query.Param;
7: import org.springframework.stereotype.Repository;
8:
9: import java.util.List;
10:
11: /**
12:  * Prediction(予想)エンティティに対するデータベース操作を提供するRepositoryインターフェース。
13:  */
14: @Repository
15: public interface PredictionRepository extends JpaRepository<Prediction, Long> {
16:
17:     /**
18:      * 指定された試合IDに関連するすべての予想を取得します。
19:      * メソッドの命名規則により、Spring Data JPAが自動でクエリを生成します。
20:      */
21:     List<Prediction> findByGameId(Long gameId);
22:
23:     /**
24:      * 指定された試合IDに関連する予想と、その予想を行ったユーザー情報を一度に取得します。
25:      * JOIN FETCH を使用することで、N+1問題を回避し、パフォーマンスを最適化しています。
26:      */
27:     @Query("SELECT p FROM Prediction p JOIN FETCH p.user WHERE p.game.id = :gameId")
28:     List<Prediction> findByGameIdWithUser(@Param("gameId") Long gameId);
29: }
```
- 1行目: パッケージの宣言。
- 2行目: 空行。
- 3-7行目: 必要なクラスやアノテーション(`@Query` など)のインポート。
- 8行目: 空行。
- 9行目: Java標準の `List` をインポート。
- 10行目: 空行。
- 11-13行目: Javadocコメント。
- 14行目: `@Repository` アノテーション。
- 15行目: `Prediction` を扱うリポジトリインターフェースの宣言。
- 16行目: 空行。
- 17-20行目: Javadocコメント。
- 21行目: Spring Data JPAの「クエリメソッド（命名規則に従ってメソッド名を書くと自動でSQLになる機能）」。引数の `gameId` をキーに予想のリストを検索・取得します。
- 22行目: 空行。
- 23-26行目: Javadocコメント。N+1問題回避の説明。
- 27行目: `@Query` アノテーション。Spring Dataに自動生成させるのではなく、指定したJPQL（Java Persistence Query Language）でDB検索を行うよう指示します。`JOIN FETCH p.user` と書くことで、`Prediction` を取得する1回のSQL実行の中で関連する `User` テーブルも結合して取得させます。
- 28行目: メソッドのシグネチャ。`@Param("gameId")` により、引数で渡した値が上のJPQL内の `:gameId` に埋め込まれます。
- 29行目: インターフェースの終了中括弧。

## backend/src/main/java/com/npbpredict/app/NpbPredictApplication.java
```java
1: package com.npbpredict.app;
2:
3: import org.springframework.boot.SpringApplication;
4: import org.springframework.boot.autoconfigure.SpringBootApplication;
5: import org.springframework.scheduling.annotation.EnableScheduling;
6:
7: /**
8:  * Spring Bootアプリケーションのエントリーポイント（起動クラス）。
9:  */
10: @SpringBootApplication // Spring Bootの自動設定やコンポーネントスキャンを有効にします
11: @EnableScheduling // 定期実行バッチ（例: 夜間のポイント集計など）のアノテーション(@Scheduled)を有効にします
12: public class NpbPredictApplication {
13:
14:     /**
15:      * アプリケーションのメインメソッド。
16:      * ここからSpring Bootが起動し、内蔵のWebサーバー(Tomcat)が立ち上がります。
17:      */
18:     public static void main(String[] args) {
19:         SpringApplication.run(NpbPredictApplication.class, args);
20:     }
21:
22: }
```
- 1行目: パッケージ宣言。
- 2行目: 空行。
- 3-5行目: Spring Bootの起動に必要なクラスやアノテーションをインポートします。
- 6行目: 空行。
- 7-9行目: Javadocコメント。
- 10行目: このクラスがSpring Bootの起動設定の中心であることを示す `@SpringBootApplication`。
- 11行目: アプリケーション内でスケジュール実行機能（定時バッチなど）を使えるようにする `@EnableScheduling` アノテーション。
- 12行目: クラスの定義開始。
- 13行目: 空行。
- 14-17行目: Javadocコメント。
- 18行目: Javaアプリケーションの実行開始地点となる `public static void main` メソッドの定義。
- 19行目: `SpringApplication.run` を呼び出すことで、SpringのDIコンテナの初期化、内蔵サーバー(Tomcat)の起動など、Webアプリケーションとしてのすべてが開始されます。
- 20行目: `main` メソッドの終了。
- 21行目: 空行。
- 22行目: `NpbPredictApplication` クラスの終了。
