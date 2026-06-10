import api from "./api";

const USER_ID =
    "30648e52-10aa-4338-b132-d1665feba926";

export const getDashboardSummary = async () => {
    try {

        const response = await api.get(
            `/api/v1/portfolio/summary/${USER_ID}`
        );

        console.log(
            "Dashboard Summary:",
            response.data
        );

        return response.data;

    } catch (error) {

        console.error(
            "Dashboard API Error:",
            error
        );

        return {
            totalPositions: 0,
            totalQuantity: 0,
            totalInvestment: 0
        };
    }
};