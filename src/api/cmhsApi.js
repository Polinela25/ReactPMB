import axios from "axios";

const API_BASE_URL = "http://localhost:8080/api/cmhsbaru";

const axiosInstance = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    Authorization: "Basic " + btoa("admin:password"),
  },
});

export const getAllMahasiswa = async (page = 0, size = 10, sort = "idcmhsbaru,asc") => {
  try {
    const response = await axiosInstance.get("", { params: { page, size, sort } });
    return response.data;
  } catch (error) {
    const message = error.response?.data?.message || "Failed to fetch mahasiswa data";
    throw new Error(message);
  }
};

export const getNamaTglLahir = async (idcmhsbaru) => {
  try {
    const response = await axiosInstance.get(`/nama-tgllahir/${idcmhsbaru}`);
    return response.data;
  } catch (error) {
    const message = error.response?.data?.message || "Failed to fetch nama and tgllahir";
    throw new Error(message);
  }
};