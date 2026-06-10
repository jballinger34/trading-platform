import Sidebar from "../components/Sidebar";
import TopNavbar from "../components/TopNavbar";
import PortfolioChart from "../components/PortfolioChart";
import StatCard from "../components/StatCard";
import MarketTicker from "../components/MarketTicker";

import { useEffect, useState } from "react";
import { getDashboardSummary } from "../api/dashboardApi";

function Dashboard() {


const [summary, setSummary] =
    useState(null);

useEffect(() => {
    loadSummary();
}, []);

const loadSummary = async () => {
    try {
        const data =
            await getDashboardSummary();

        setSummary(data);
    } catch (err) {
        console.error(err);
    }
};

return (
    <div
        style={{
            display: "flex",
            minHeight: "100vh"
        }}
    >
        <Sidebar />

        <div
            style={{
                flex: 1,
                padding: "35px"
            }}
        >
            <TopNavbar />

            <MarketTicker />

            <h1
                style={{
                    fontSize: "42px",
                    marginTop: "30px",
                    marginBottom: "10px",
                    background:
                        "linear-gradient(90deg,#60a5fa,#22c55e)",
                    WebkitBackgroundClip: "text",
                    WebkitTextFillColor: "transparent"
                }}
            >
                TradeFlow Dashboard
            </h1>

            <p
                style={{
                    color: "#94a3b8",
                    marginBottom: "30px"
                }}
            >
                Monitor your portfolio, wallet and trading activity.
            </p>

            <div
                style={{
                    display: "grid",
                    gridTemplateColumns:
                        "repeat(auto-fit,minmax(250px,1fr))",
                    gap: "20px"
                }}
            >
                <StatCard
                    title="Portfolio Value"
                    value={`₹ ${summary?.totalInvestment ?? 0}`}
                />

                <StatCard
                    title="Total Positions"
                    value={summary?.totalPositions ?? 0}
                />

                <StatCard
                    title="Total Quantity"
                    value={summary?.totalQuantity ?? 0}
                    color="#22c55e"
                />

                <StatCard
                    title="Wallet Balance"
                    value="Coming Soon"
                />

                <StatCard
                    title="Open Orders"
                    value="Coming Soon"
                />

                <StatCard
                    title="Winning Rate"
                    value="Coming Soon"
                />

                <StatCard
                    title="Assets Held"
                    value={summary?.totalPositions ?? 0}
                />
            </div>

            <div
                style={{
                    display: "grid",
                    gridTemplateColumns: "1fr 1fr",
                    gap: "20px",
                    marginTop: "30px"
                }}
            >
                <div
                    style={{
                        background: "#111827",
                        padding: "25px",
                        borderRadius: "20px",
                        boxShadow:
                            "0 10px 30px rgba(0,0,0,.25)"
                    }}
                >
                    <h2
                        style={{
                            marginBottom: "20px"
                        }}
                    >
                        Watchlist
                    </h2>

                    <div
                        style={{
                            display: "flex",
                            flexDirection: "column",
                            gap: "15px"
                        }}
                    >
                        <div>
                            <strong>AAPL</strong>
                            <p style={{ color: "#22c55e" }}>
                                +2.4%
                            </p>
                        </div>

                        <div>
                            <strong>NVDA</strong>
                            <p style={{ color: "#22c55e" }}>
                                +4.1%
                            </p>
                        </div>

                        <div>
                            <strong>MSFT</strong>
                            <p style={{ color: "#22c55e" }}>
                                +0.9%
                            </p>
                        </div>

                        <div>
                            <strong>TSLA</strong>
                            <p style={{ color: "#ef4444" }}>
                                -1.3%
                            </p>
                        </div>
                    </div>
                </div>

                <div
                    style={{
                        background: "#111827",
                        padding: "25px",
                        borderRadius: "20px",
                        boxShadow:
                            "0 10px 30px rgba(0,0,0,.25)"
                    }}
                >
                    <h2
                        style={{
                            marginBottom: "20px"
                        }}
                    >
                        Recent Activity
                    </h2>

                    <div
                        style={{
                            display: "flex",
                            flexDirection: "column",
                            gap: "15px"
                        }}
                    >
                        <p>✅ Bought 10 AAPL</p>
                        <p>📈 Bought 5 NVDA</p>
                        <p>💰 Deposited ₹20,000</p>
                        <p>📉 Sold 3 TSLA</p>
                        <p>🔒 Reserved ₹5,000</p>
                    </div>
                </div>
            </div>

            <div
                style={{
                    marginTop: "30px",
                    background: "#111827",
                    padding: "25px",
                    borderRadius: "20px",
                    boxShadow:
                        "0 10px 30px rgba(0,0,0,.25)"
                }}
            >
                <PortfolioChart />
            </div>

            <button
                style={{
                    position: "fixed",
                    bottom: "30px",
                    right: "30px",
                    width: "70px",
                    height: "70px",
                    borderRadius: "50%",
                    border: "none",
                    background: "#22c55e",
                    color: "white",
                    fontSize: "28px",
                    cursor: "pointer",
                    boxShadow:
                        "0 10px 30px rgba(34,197,94,.4)"
                }}
            >
                +
            </button>
        </div>
    </div>
);


}

export default Dashboard;
