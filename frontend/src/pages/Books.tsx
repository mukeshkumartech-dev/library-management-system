import { useEffect, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { BookOpen } from 'lucide-react';
import api from '../services/api';

export default function Books() {
  const [books, setBooks] = useState<any[]>([]);
  const navigate = useNavigate();

  useEffect(() => {
    api.get('/books').then(res => setBooks(res.data)).catch(() => navigate('/login'));
  }, [navigate]);

  return (
    <div className="flex h-screen bg-gray-50">
      <div className="w-64 bg-gray-900 text-white flex flex-col">
        <div className="p-6 text-2xl font-bold border-b border-gray-800 flex items-center gap-2">
          <BookOpen /> Library
        </div>
        <nav className="flex-1 p-4">
          <Link to="/dashboard" className="block p-3 hover:bg-gray-800 rounded mb-2">Dashboard</Link>
          <Link to="/books" className="block p-3 bg-gray-800 rounded mb-2">Books</Link>
          <Link to="/members" className="block p-3 hover:bg-gray-800 rounded mb-2">Members</Link>
        </nav>
      </div>
      <div className="flex-1 p-8 overflow-auto">
        <div className="flex justify-between items-center mb-6">
          <h1 className="text-3xl font-bold text-gray-800">Books Management</h1>
          <button className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700">Add New Book</button>
        </div>
        <div className="bg-white rounded-lg shadow-sm border border-gray-100 overflow-hidden">
          <table className="w-full text-left">
            <thead className="bg-gray-50 border-b border-gray-100 text-gray-600">
              <tr>
                <th className="p-4">ISBN</th>
                <th className="p-4">Title</th>
                <th className="p-4">Author</th>
                <th className="p-4">Category</th>
                <th className="p-4">Available</th>
              </tr>
            </thead>
            <tbody>
              {books.map(book => (
                <tr key={book.id} className="border-b border-gray-50 hover:bg-gray-50">
                  <td className="p-4">{book.isbn}</td>
                  <td className="p-4 font-medium text-gray-900">{book.title}</td>
                  <td className="p-4 text-gray-500">{book.author}</td>
                  <td className="p-4"><span className="bg-gray-100 text-gray-700 px-2 py-1 rounded text-sm">{book.category}</span></td>
                  <td className="p-4">
                    <span className={book.availableCopies > 0 ? "text-green-600 font-semibold" : "text-red-600 font-semibold"}>
                      {book.availableCopies} / {book.totalCopies}
                    </span>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
          {books.length === 0 && <div className="p-8 text-center text-gray-500">No books found.</div>}
        </div>
      </div>
    </div>
  );
}
