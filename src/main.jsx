import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App";
import "./styles.css";

const disableDevTools = () => {
  document.addEventListener("keydown", (e) => {
    if (
      e.key === "F12" ||
      (e.ctrlKey && e.shiftKey && (e.key === "I" || e.key === "J")) ||
      (e.ctrlKey && e.key === "U")
    ) {
      e.preventDefault();
    }
  });
  // Klik kanan dinonaktifkan sebagai opsi, aktifkan jika diperlukan
  // document.addEventListener("contextmenu", (e) => e.preventDefault());
};

disableDevTools();

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);