import { Link, useLocation } from "react-router-dom";
import { Home, UserPlus, BookOpen, LogIn, PackageOpen } from "lucide-react";

export default function Navbar() {
  const location = useLocation();

  const menuItems = [
    { path: "/", label: "Beranda", icon: <Home size={20} /> },
    { path: "/registrasi", label: "Registrasi", icon: <UserPlus size={20} /> },
    { path: "/spo-pmb", label: "SPO PMB", icon: <BookOpen size={20} /> },
    { path: "/spo-pmb/login", label: "Login", icon: <LogIn size={20} /> },
    { path: "/MahasiswaPage", label: "Admin", icon: <LogIn size={20} /> },
    { path: "/mahasiswaInput", label: "List Maha", icon: <PackageOpen size={20} /> },
    { path: "/MahasiswaList", label: "DB", icon: <PackageOpen size={20} /> },
  ];

  return (
   <header className="header">
  <h1 className="logo">PMB POLINELA</h1>
  <nav className="menu">
    {menuItems.map((item) => (
      <Link
        key={item.path}
        to={item.path}
        className={`nav-link ${
          location.pathname === item.path
            ? "active"
            : ""
        }`}
      >
        {item.icon}
        <span>{item.label}</span>
      </Link>
    ))}
  </nav>
</header>

  );
}
