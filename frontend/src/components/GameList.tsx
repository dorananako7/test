import React, { useEffect, useState } from 'react';
import { fetchGames } from '../api/gameApi';
import type { Game } from '../api/gameApi';
import GameCard from './GameCard';
import { Calendar } from 'lucide-react';

const GameList: React.FC = () => {
  const [games, setGames] = useState<Game[]>([]);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const loadGames = async () => {
      try {
        const fetchedGames = await fetchGames();
        setGames(fetchedGames);
      } catch (e) {
        console.error(e);
      } finally {
        setIsLoading(false);
      }
    };
    loadGames();
  }, []);

  if (isLoading) {
    return (
      <div className="flex justify-center items-center py-20">
        <div className="animate-spin rounded-full h-10 w-10 border-b-2 border-blue-800"></div>
      </div>
    );
  }

  return (
    <div className="max-w-2xl mx-auto py-6 px-4">
      <div className="flex items-center space-x-2 mb-6 text-gray-800">
        <Calendar className="w-5 h-5 text-blue-800" />
        <h2 className="text-xl font-bold">今日の試合 ＆ 予想オッズ</h2>
      </div>

      {games.length === 0 ? (
        <div className="text-center py-10 bg-white rounded-lg shadow-sm border border-gray-100">
          <p className="text-gray-500">本日の試合予定はありません。</p>
        </div>
      ) : (
        games.map(game => (
          <GameCard key={game.id} game={game} />
        ))
      )}
    </div>
  );
};

export default GameList;
