import { Link, useLocation } from "react-router-dom";
import { Home, UserPlus, BookOpen, LogIn } from "lucide-react";

export default function Sidebar() {
  const location = useLocation();

  const menuItems = [
    { path: "/", label: "Beranda", icon: <Home size={18} /> },
    { path: "/registrasi", label: "Registrasi", icon: <UserPlus size={18} /> },
    { path: "/spo-pmb", label: "SPO PMB", icon: <BookOpen size={18} /> },
    { path: "/spo-pmb/login", label: "Login", icon: <LogIn size={18} /> },
    { path: "/MahasiswaTable", label: "Admin", icon: <LogIn size={18} /> },
  ];

  return (
    <aside className="fixed top-0 left-0 h-full w-64 bg-blue-700 text-white shadow-lg z-40 hidden md:block">
      <div className="p-6 text-center border-b border-blue-600">
        <h1 className="text-2xl font-bold tracking-wider">PMB POLINELA</h1>
      </div>

      <nav className="mt-6 space-y-2 px-4">
        {menuItems.map((item) => (
          <Link
            key={item.path}
            to={item.path}
            className={`flex items-center px-4 py-2 rounded-md transition-all ${
              location.pathname === item.path
                ? "bg-blue-600 font-semibold"
                : "hover:bg-blue-600"
            }`}
          >
            <span className="mr-3">{item.icon}</span>
            {item.label}
          </Link>
        ))}
      </nav>
    </aside>
  );
}