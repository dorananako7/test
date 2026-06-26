export interface Game {
  id: number;
  gameDate: string;
  startTime: string; // ISO format e.g. "2024-10-01T18:00:00"
  homeTeam: string;
  awayTeam: string;
  status: 'BEFORE_GAME' | 'VOTING_CLOSED' | 'GAME_END';
  result: 'HOME' | 'AWAY' | 'DRAW' | null;
}

export interface GameOdds {
  totalVotes: number;
  homeRatio: number;
  awayRatio: number;
  drawRatio: number;
}

export interface PredictionDetail {
  nickname: string;
  predictedWinner: 'HOME' | 'AWAY' | 'DRAW';
}

// Dummy data for initial UI implementation
const DUMMY_GAMES: Game[] = [
  {
    id: 1,
    gameDate: '2024-10-01',
    startTime: new Date(new Date().getTime() + 1000 * 60 * 60 * 2).toISOString(), // 2 hours in the future
    homeTeam: '巨人',
    awayTeam: '阪神',
    status: 'BEFORE_GAME',
    result: null,
  },
  {
    id: 2,
    gameDate: '2024-10-01',
    startTime: new Date(new Date().getTime() - 1000 * 60 * 60 * 1).toISOString(), // 1 hour ago
    homeTeam: 'ソフトバンク',
    awayTeam: 'オリックス',
    status: 'VOTING_CLOSED',
    result: null,
  },
  {
    id: 3,
    gameDate: '2024-10-01',
    startTime: new Date(new Date().getTime() - 1000 * 60 * 60 * 5).toISOString(), // 5 hours ago
    homeTeam: 'DeNA',
    awayTeam: 'ヤクルト',
    status: 'GAME_END',
    result: 'HOME',
  }
];

export const fetchGames = async (): Promise<Game[]> => {
  // Simulate network delay
  await new Promise(resolve => setTimeout(resolve, 500));
  return DUMMY_GAMES;
};

export const fetchGameOdds = async (gameId: number): Promise<GameOdds> => {
  await new Promise(resolve => setTimeout(resolve, 500));

  if (gameId === 1) {
    return { totalVotes: 120, homeRatio: 60.5, awayRatio: 35.0, drawRatio: 4.5 };
  } else if (gameId === 2) {
    return { totalVotes: 85, homeRatio: 40.0, awayRatio: 55.0, drawRatio: 5.0 };
  } else {
    return { totalVotes: 210, homeRatio: 50.0, awayRatio: 45.0, drawRatio: 5.0 };
  }
};

export const fetchGamePredictions = async (gameId: number): Promise<PredictionDetail[]> => {
  await new Promise(resolve => setTimeout(resolve, 500));

  const game = DUMMY_GAMES.find(g => g.id === gameId);
  if (game && game.status === 'BEFORE_GAME') {
    throw new Error("Predictions are hidden before the game starts.");
  }

  return [
    { nickname: '野球太郎', predictedWinner: 'HOME' },
    { nickname: 'ベースボールファン', predictedWinner: 'AWAY' },
    { nickname: 'NPBマニア', predictedWinner: 'HOME' },
    { nickname: 'スワローズ命', predictedWinner: 'DRAW' },
  ];
};
