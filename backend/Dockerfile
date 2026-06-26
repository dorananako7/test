# ステージ1: アプリケーションのビルド
# ベースイメージとして軽量なAlpine LinuxベースのJDK 17を使用
FROM eclipse-temurin:17-jdk-alpine AS build

# コンテナ内の作業ディレクトリを/appに設定
WORKDIR /app

# Mavenラッパーと依存関係定義ファイル(pom.xml)をコピー
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# 依存関係をダウンロード (pom.xmlが変更されない限り、このレイヤーはキャッシュされる)
RUN ./mvnw dependency:go-offline -B

# ソースコードをコンテナ内にコピー
COPY src src

# テストをスキップしてMavenでパッケージ化（JARファイルを生成）
RUN ./mvnw clean package -DskipTests

# ステージ2: アプリケーションの実行
# 実行用にはより軽量なJREイメージを使用
FROM eclipse-temurin:17-jre-alpine

# コンテナ内の作業ディレクトリを/appに設定
WORKDIR /app

# ビルドステージ（ステージ1）で生成されたJARファイルをapp.jarとしてコピー
COPY --from=build /app/target/*.jar app.jar

# Spring Bootのデフォルトポート(8080)を公開することを明示
EXPOSE 8080

# コンテナ起動時に実行されるコマンド（Javaアプリケーションを起動）
ENTRYPOINT ["java", "-jar", "app.jar"]
