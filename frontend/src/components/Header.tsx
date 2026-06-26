import React from 'react';
import { Trophy, UserCircle } from 'lucide-react';

const Header: React.FC = () => {
  return (
    <header className="bg-blue-900 text-white shadow-md sticky top-0 z-50">
      <div className="container mx-auto px-4 py-3 flex justify-between items-center">

        {/* Logo / App Name */}
        <div className="flex items-center space-x-2">
          <Trophy className="text-yellow-400 w-6 h-6" />
          <h1 className="text-xl font-bold tracking-wider">NPB Predict</h1>
        </div>

        {/* Navigation / User Actions */}
        <nav className="flex items-center space-x-6">
          <a href="#" className="hover:text-blue-200 transition-colors text-sm font-medium">試合一覧</a>
          <a href="#" className="hover:text-blue-200 transition-colors text-sm font-medium">ランキング</a>

          <div className="flex items-center bg-blue-800 rounded-full px-3 py-1.5 cursor-pointer hover:bg-blue-700 transition-colors">
            <UserCircle className="w-5 h-5 mr-2" />
            <span className="text-sm font-medium">ログイン</span>
          </div>
        </nav>

      </div>
    </header>
  );
};

export default Header;
