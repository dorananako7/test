import React, { useEffect, useState } from 'react';
import { fetchGameOdds, fetchGamePredictions } from '../api/gameApi';
import type { Game, GameOdds, PredictionDetail } from '../api/gameApi';
import { Clock, Users, Lock, Unlock, CheckCircle } from 'lucide-react';

interface GameCardProps {
  game: Game;
}

const GameCard: React.FC<GameCardProps> = ({ game }) => {
  const [odds, setOdds] = useState<GameOdds | null>(null);
  const [predictions, setPredictions] = useState<PredictionDetail[] | null>(null);
  const [isLoading, setIsLoading] = useState(true);

  // Parse game time
  const gameDate = new Date(game.startTime);
  const timeString = `${gameDate.getHours().toString().padStart(2, '0')}:${gameDate.getMinutes().toString().padStart(2, '0')}`;

  useEffect(() => {
    const loadData = async () => {
      try {
        const fetchedOdds = await fetchGameOdds(game.id);
        setOdds(fetchedOdds);

        if (game.status !== 'BEFORE_GAME') {
          const fetchedPredictions = await fetchGamePredictions(game.id);
          setPredictions(fetchedPredictions);
        }
      } catch (e) {
        console.error(e);
      } finally {
        setIsLoading(false);
      }
    };

    loadData();
  }, [game.id, game.status]);

  return (
    <div className="bg-white rounded-xl shadow-sm border border-gray-100 overflow-hidden mb-6 transition-all hover:shadow-md">

      {/* Card Header (Teams and Time) */}
      <div className="bg-gradient-to-r from-gray-50 to-gray-100 px-4 py-3 border-b border-gray-100 flex justify-between items-center">
        <div className="flex items-center space-x-2 text-gray-500">
          <Clock className="w-4 h-4" />
          <span className="text-sm font-semibold">{timeString} プレイボール</span>
        </div>
        <div className="text-xs font-bold px-2 py-1 rounded bg-gray-200 text-gray-600">
          {game.status === 'BEFORE_GAME' ? '投票受付中' : game.status === 'VOTING_CLOSED' ? '試合中 (投票締切)' : '試合終了'}
        </div>
      </div>

      <div className="p-5">

        {/* Teams Display */}
        <div className="flex justify-between items-center mb-6">
          <div className="flex-1 text-center">
            <h3 className="text-xl font-bold text-gray-800">{game.homeTeam}</h3>
            <p className="text-xs text-gray-400 mt-1">HOME</p>
          </div>
          <div className="px-4 text-gray-400 font-bold text-lg">VS</div>
          <div className="flex-1 text-center">
            <h3 className="text-xl font-bold text-gray-800">{game.awayTeam}</h3>
            <p className="text-xs text-gray-400 mt-1">AWAY</p>
          </div>
        </div>

        {/* Voting Panel (Only if BEFORE_GAME) */}
        {game.status === 'BEFORE_GAME' && (
          <div className="mb-6">
            <p className="text-sm font-semibold text-gray-600 mb-2 text-center">あなたの予想は？</p>
            <div className="flex gap-2">
              <button className="flex-1 bg-blue-50 text-blue-700 hover:bg-blue-600 hover:text-white border border-blue-200 font-bold py-2 px-4 rounded transition-colors text-sm">
                {game.homeTeam}の勝ち
              </button>
              <button className="flex-1 bg-gray-50 text-gray-700 hover:bg-gray-600 hover:text-white border border-gray-200 font-bold py-2 px-4 rounded transition-colors text-sm">
                引き分け
              </button>
              <button className="flex-1 bg-red-50 text-red-700 hover:bg-red-600 hover:text-white border border-red-200 font-bold py-2 px-4 rounded transition-colors text-sm">
                {game.awayTeam}の勝ち
              </button>
            </div>
            <p className="text-xs text-center text-gray-400 mt-2 flex items-center justify-center">
              <Lock className="w-3 h-3 mr-1" /> ログインして投票に参加しよう
            </p>
          </div>
        )}

        {/* Odds Section */}
        {isLoading ? (
          <div className="h-16 flex items-center justify-center text-gray-400 text-sm">読み込み中...</div>
        ) : odds ? (
          <div className="bg-gray-50 rounded-lg p-4">
            <div className="flex justify-between text-xs font-semibold text-gray-500 mb-2">
              <div className="flex items-center"><Users className="w-3 h-3 mr-1"/> みんなの予想オッズ ({odds.totalVotes}票)</div>
              {game.status === 'BEFORE_GAME' ? (
                <div className="flex items-center text-orange-500"><Lock className="w-3 h-3 mr-1"/> 誰が投票したかは試合開始まで秘密</div>
              ) : (
                <div className="flex items-center text-green-600"><Unlock className="w-3 h-3 mr-1"/> 投票内訳公開中</div>
              )}
            </div>

            {/* Progress Bar for Odds */}
            <div className="h-4 w-full flex rounded-full overflow-hidden">
              <div style={{ width: `${odds.homeRatio}%` }} className="bg-blue-500 h-full flex justify-center items-center text-[10px] text-white font-bold transition-all duration-500">
                {odds.homeRatio > 10 ? `${odds.homeRatio}%` : ''}
              </div>
              <div style={{ width: `${odds.drawRatio}%` }} className="bg-gray-400 h-full flex justify-center items-center text-[10px] text-white font-bold transition-all duration-500">
                {odds.drawRatio > 10 ? `${odds.drawRatio}%` : ''}
              </div>
              <div style={{ width: `${odds.awayRatio}%` }} className="bg-red-500 h-full flex justify-center items-center text-[10px] text-white font-bold transition-all duration-500">
                {odds.awayRatio > 10 ? `${odds.awayRatio}%` : ''}
              </div>
            </div>

            <div className="flex justify-between mt-2 text-xs font-bold">
              <span className="text-blue-700">{game.homeTeam}: {odds.homeRatio}%</span>
              <span className="text-gray-500">引分: {odds.drawRatio}%</span>
              <span className="text-red-700">{game.awayTeam}: {odds.awayRatio}%</span>
            </div>
          </div>
        ) : null}

        {/* Revealed Predictions (Only if after game started) */}
        {game.status !== 'BEFORE_GAME' && predictions && (
          <div className="mt-4 pt-4 border-t border-gray-100">
            <h4 className="text-sm font-semibold text-gray-700 mb-3 flex items-center">
              <CheckCircle className="w-4 h-4 mr-1 text-green-500" /> 最新の投票内訳 (一部)
            </h4>
            <div className="flex flex-wrap gap-2">
              {predictions.map((p, idx) => (
                <span key={idx} className="inline-flex items-center px-2 py-1 rounded bg-white border border-gray-200 text-xs text-gray-600 shadow-sm">
                  <span className="font-semibold mr-1">{p.nickname}</span>
                  <span className={`px-1 rounded text-[10px] text-white ${
                    p.predictedWinner === 'HOME' ? 'bg-blue-500' : p.predictedWinner === 'AWAY' ? 'bg-red-500' : 'bg-gray-500'
                  }`}>
                    {p.predictedWinner === 'HOME' ? game.homeTeam : p.predictedWinner === 'AWAY' ? game.awayTeam : '引分'}
                  </span>
                </span>
              ))}
            </div>
          </div>
        )}

      </div>
    </div>
  );
};

export default GameCard;
