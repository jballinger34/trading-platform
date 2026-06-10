import api from "./api";

const USER_ID =
    "30648e52-10aa-4338-b132-d1665feba926";

export const getPortfolioHoldings = async () => {
    const response =
        await api.get(
            `/api/v1/portfolio/user/${USER_ID}`
        );

    return response.data;
};

export const getPortfolioSummary = async () => {
    const response =
        await api.get(
            `/api/v1/portfolio/summary/${USER_ID}`
        );

    return response.data;
};