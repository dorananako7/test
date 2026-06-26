

FROM node:20-alpine


# コンテナ内の作業ディレクトリを/appに設定
WORKDIR /app

# パッケージ管理ファイル（package.json, package-lock.json等）をコピー
COPY package*.json ./

# 依存関係パッケージ（node_modules）をインストール
RUN npm install

# 残りのすべてのフロントエンドのソースコードをコピー
COPY . .

# Vite開発サーバーのデフォルトポート(5173)を公開することを明示
EXPOSE 5173

# コンテナ起動時にVite開発サーバーを起動するコマンド
# --host オプションを付けることで、コンテナ外部（ホストPC等）からのアクセスを許可する
CMD ["npm", "run", "dev", "--", "--host"]
