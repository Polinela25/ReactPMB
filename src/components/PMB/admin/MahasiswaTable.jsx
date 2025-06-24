import { useState } from "react";

export default function MahasiswaTable({ data, npmPrefix, setNpmPrefix }) {
  const formatDate = (date) => {
    if (!date) return null;
    try {
      const d = new Date(date);
      return d.toLocaleDateString("id-ID", {
        day: "2-digit",
        month: "2-digit",
        year: "numeric",
      });
    } catch {
      return null;
    }
  };

  return (
    <div className="p-6 max-w-4xl mx-auto">
      <h1 className="text-2xl font-bold mb-4">Data Mahasiswa</h1>

      <div className="mb-4">
        <label htmlFor="npmPrefix" className="block font-medium mb-1">
          Awalan NPM:
        </label>
        <input
          type="text"
          id="npmPrefix"
          value={npmPrefix}
          onChange={(e) => setNpmPrefix(e.target.value)}
          className="border px-3 py-1 rounded w-40"
        />
      </div>

      {data.length === 0 ? (
        <p className="text-gray-500">No data available</p>
      ) : (
        <table className="w-full border-collapse">
          <thead>
            <tr>
              <th className="border p-2">ID</th>
              <th className="border p-2">Nama</th>
              <th className="border p-2">Tanggal Lahir</th>
              <th className="border p-2">NPM</th>
            </tr>
          </thead>
          <tbody>
            {data.map((item) => (
              <tr
                key={item.idcmhsbaru}
                className={item.alert ? "bg-red-100 text-red-700" : ""}
              >
                <td className="border p-2">{item.idcmhsbaru}</td>
                <td className="border p-2">{item.namaasli}</td>
                <td className="border p-2">
                  {formatDate(item.tgllahir) || (
                    <span className="italic text-red-600">
                      Tanggal lahir tidak tersedia
                    </span>
                  )}
                </td>
                <td className="border p-2 font-mono">{item.npm}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}