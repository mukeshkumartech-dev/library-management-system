import { useEffect, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { Book, Users, BookOpen, Clock } from 'lucide-react';
import api from '../services/api';

export default function Dashboard() {
  const [stats, setStats] = useState<any>(null);
  const navigate = useNavigate();
  const user = JSON.parse(localStorage.getItem('user') || '{}');

  useEffect(() => {
    if (!user.token) {
      navigate('/login');
      return;
    }
    api.get('/dashboard/statistics').then(res => {
      setStats(res.data);
    }).catch(() => {
      navigate('/login');
    });
  }, [navigate, user.token]);

  const handleLogout = () => {
    localStorage.removeItem('user');
    navigate('/login');
  };

  if (!stats) return <div className="p-8">Loading...</div>;

  return (
    <div className="flex h-screen bg-gray-50">
      {/* Sidebar */}
      <div className="w-64 bg-gray-900 text-white flex flex-col">
        <div className="p-6 text-2xl font-bold border-b border-gray-800 flex items-center gap-2">
          <BookOpen /> Library
        </div>
        <nav className="flex-1 p-4">
          <Link to="/dashboard" className="block p-3 bg-gray-800 rounded mb-2">Dashboard</Link>
          <Link to="/books" className="block p-3 hover:bg-gray-800 rounded mb-2">Books</Link>
          <Link to="/members" className="block p-3 hover:bg-gray-800 rounded mb-2">Members</Link>
        </nav>
        <div className="p-4 border-t border-gray-800">
          <button onClick={handleLogout} className="w-full text-left p-3 hover:bg-gray-800 rounded text-red-400">
            Logout
          </button>
        </div>
      </div>

      {/* Main Content */}
      <div className="flex-1 overflow-auto p-8">
        <div className="flex justify-between items-center mb-8">
          <h1 className="text-3xl font-bold text-gray-800">Dashboard</h1>
          <div className="text-gray-600">Welcome, <span className="font-semibold">{user.username}</span></div>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-4 gap-6">
          <div className="bg-white p-6 rounded-lg shadow-sm border border-gray-100 flex items-center gap-4">
            <div className="p-4 bg-blue-100 text-blue-600 rounded-full"><Book size={24} /></div>
            <div>
              <div className="text-gray-500 text-sm">Total Books</div>
              <div className="text-2xl font-bold">{stats.totalBooks}</div>
            </div>
          </div>
          <div className="bg-white p-6 rounded-lg shadow-sm border border-gray-100 flex items-center gap-4">
            <div className="p-4 bg-green-100 text-green-600 rounded-full"><Users size={24} /></div>
            <div>
              <div className="text-gray-500 text-sm">Total Members</div>
              <div className="text-2xl font-bold">{stats.totalMembers}</div>
            </div>
          </div>
          <div className="bg-white p-6 rounded-lg shadow-sm border border-gray-100 flex items-center gap-4">
            <div className="p-4 bg-purple-100 text-purple-600 rounded-full"><BookOpen size={24} /></div>
            <div>
              <div className="text-gray-500 text-sm">Issued Books</div>
              <div className="text-2xl font-bold">{stats.issuedBooks}</div>
            </div>
          </div>
          <div className="bg-white p-6 rounded-lg shadow-sm border border-gray-100 flex items-center gap-4">
            <div className="p-4 bg-red-100 text-red-600 rounded-full"><Clock size={24} /></div>
            <div>
              <div className="text-gray-500 text-sm">Overdue Books</div>
              <div className="text-2xl font-bold">{stats.overdueBooks}</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
