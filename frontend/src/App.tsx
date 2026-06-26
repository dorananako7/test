import Header from './components/Header';
import GameList from './components/GameList';

function App() {
  return (
    <div className="min-h-screen font-sans text-gray-900 bg-[#f3f4f6]">
      <Header />
      <main>
        <GameList />
      </main>

      <footer className="mt-12 py-6 text-center text-sm text-gray-400 border-t border-gray-200">
        &copy; {new Date().getFullYear()} NPB Predict App. Not affiliated with Nippon Professional Baseball.
      </footer>
    </div>
  );
}

export default App;
