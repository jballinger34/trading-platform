import api from "./api";

export const getWallet = async (userId) => {
    const response = await api.get(`/wallet/${userId}`);
    return response.data;
};