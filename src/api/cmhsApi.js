import axios from "axios";

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || "http://localhost:8080/api/cmhsbaru";
const username = import.meta.env.VITE_API_USERNAME || "admin";
const password = import.meta.env.VITE_API_PASSWORD || "password";

const axiosInstance = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    Authorization: `Basic ${btoa(`${username}:${password}`)}`,
    "Content-Type": "application/json",
  },
});

export const getAllMahasiswa = async (page = 0, size = 10, sort = "idcmhsbaru,asc", npmPrefix = "") => {
  try {
    const response = await axiosInstance.get("", { params: { page, size, sort, npmPrefix } });
    return response.data;
  } catch (error) {
    console.error("API error:", error.response?.data || error.message);
    throw new Error(error.response?.data?.message || "Gagal mengambil data mahasiswa");
  }
};

export const saveMahasiswa = async (mahasiswaData) => {
  try {
    const response = await axiosInstance.post("", mahasiswaData);
    return response.data;
  } catch (error) {
    console.error("API error:", error.response?.data || error.message);
    throw new Error(error.response?.data?.message || "Gagal menyimpan data mahasiswa");
  }
};

export const getMahasiswaCountByYearAndProdi = async (tahun, kdprodi) => {
  try {
    const response = await axiosInstance.get("/count", { params: { tahun, kdprodi } });
    return response.data;
  } catch (error) {
    console.error("API error:", error.response?.data || error.message);
    throw new Error(error.response?.data?.message || "Gagal mengambil jumlah mahasiswa");
  }
};