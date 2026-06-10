import Sidebar from "../components/Sidebar";
import TopNavbar from "../components/TopNavbar";
import { useEffect, useState } from "react";
import {
    PieChart,
    Pie,
    Cell,
    Tooltip,
    ResponsiveContainer
} from "recharts";

import {
    getPortfolioHoldings
} from "../api/portfolioApi";

const COLORS = [
    "#60a5fa",
    "#22c55e",
    "#f59e0b",
    "#ef4444",
    "#8b5cf6",
    "#ec4899"
];

function Portfolio() {


const [holdings, setHoldings] =
    useState([]);

const [loading, setLoading] =
    useState(true);

    console.log("CURRENT HOLDINGS:", holdings);

useEffect(() => {
    loadHoldings();
}, []);

    const loadHoldings = async () => {
        try {
            const response = await getPortfolioHoldings();

            console.log("RAW RESPONSE:", response);
            console.log("TYPE:", typeof response);
            console.log("IS ARRAY:", Array.isArray(response));

            setHoldings(response);

        } catch (err) {
            console.error("ERROR:", err);
        } finally {
            setLoading(false);
        }
    };

const chartData =
    holdings.length > 0
        ? holdings.map((holding) => ({
            name:
                holding.symbol?.trim()
                    ? holding.symbol
                    : `Stock-${holding.id}`,

            value:
                holding.quantity || 0
        }))
        : [];

const totalQuantity =
    holdings.reduce(
        (sum, holding) =>
            sum + (holding.quantity || 0),
        0
    );

const totalInvestment =
    holdings.reduce(
        (sum, holding) =>
            sum +
            ((holding.quantity || 0) *
                (holding.averagePrice || 0)),
        0
    );

if (loading) {
    return (
        <div
            style={{
                color: "white",
                padding: "40px"
            }}
        >
            Loading Portfolio...
        </div>
    );
}
    console.log("CURRENT HOLDINGS:", holdings);

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

            <h1
                style={{
                    marginTop: "25px",
                    marginBottom: "25px",
                    fontSize: "40px",
                    background:
                        "linear-gradient(90deg,#60a5fa,#22c55e)",
                    WebkitBackgroundClip:
                        "text",
                    WebkitTextFillColor:
                        "transparent"
                }}
            >
                Portfolio Overview
            </h1>

            <div
                style={{
                    display: "grid",
                    gridTemplateColumns:
                        "repeat(auto-fit,minmax(250px,1fr))",
                    gap: "20px"
                }}
            >
                <div className="glass-card">
                    <p>Total Holdings</p>
                    <h2>{holdings.length}</h2>
                </div>

                <div className="glass-card">
                    <p>Total Quantity</p>
                    <h2>{totalQuantity}</h2>
                </div>

                <div className="glass-card">
                    <p>Total Investment</p>
                    <h2>
                        ₹
                        {totalInvestment.toLocaleString()}
                    </h2>
                </div>

                <div className="glass-card">
                    <p>Status</p>
                    <h2
                        style={{
                            color:
                                "#22c55e"
                        }}
                    >
                        Active
                    </h2>
                </div>
            </div>

            <div
                style={{
                    display: "grid",
                    gridTemplateColumns:
                        "1fr 1fr",
                    gap: "20px",
                    marginTop: "25px"
                }}
            >
                <div className="glass-card">
                    <h2>
                        Portfolio Allocation
                    </h2>

                    <div
                        style={{
                            width: "100%",
                            height: "350px"
                        }}
                    >
                        {chartData.length > 0 && (
                            <ResponsiveContainer
                                width="100%"
                                height="100%"
                            >
                                <PieChart>
                                    <Pie
                                        data={chartData}
                                        dataKey="value"
                                        nameKey="name"
                                        outerRadius={120}
                                        label={({ name }) => name}
                                    >
                                        {chartData.map((entry, index) => (
                                            <Cell
                                                key={index}
                                                fill={
                                                    COLORS[index % COLORS.length]
                                                }
                                            />
                                        ))}
                                    </Pie>

                                    <Tooltip />
                                </PieChart>
                            </ResponsiveContainer>
                        )}
                    </div>
                </div>

                <div className="glass-card">
                    <h2>
                        Top Holdings
                    </h2>

                    <div
                        style={{
                            marginTop:
                                "20px"
                        }}
                    >
                        {holdings.map(
                            (
                                holding
                            ) => (
                                <div
                                    key={
                                        holding.id
                                    }
                                    style={{
                                        padding:
                                            "12px",
                                        borderBottom:
                                            "1px solid rgba(255,255,255,.08)"
                                    }}
                                >
                                    <strong>
                                        {holding.symbol?.trim()
                                            ? holding.symbol
                                            : `Stock-${holding.id}`}
                                    </strong>

                                    <p>
                                        Quantity:
                                        {" "}
                                        {
                                            holding.quantity
                                        }
                                    </p>

                                    <p>
                                        Avg Price:
                                        ₹
                                        {
                                            holding.averagePrice
                                        }
                                    </p>
                                </div>
                            )
                        )}
                    </div>
                </div>
            </div>

            <div
                className="glass-card"
                style={{
                    marginTop: "25px"
                }}
            >
                <h2>
                    Holdings Table
                </h2>

                <table
                    style={{
                        width: "100%",
                        marginTop:
                            "20px"
                    }}
                >
                    <thead>
                    <tr>
                        <th align="left">
                            Symbol
                        </th>

                        <th align="left">
                            Quantity
                        </th>

                        <th align="left">
                            Avg Price
                        </th>

                        <th align="left">
                            Investment
                        </th>
                    </tr>
                    </thead>

                    <tbody>
                    {holdings.map(
                        (
                            holding
                        ) => (
                            <tr
                                key={
                                    holding.id
                                }
                            >
                                <td>
                                    {holding.symbol?.trim()
                                        ? holding.symbol
                                        : `Stock-${holding.id}`}
                                </td>

                                <td>
                                    {
                                        holding.quantity
                                    }
                                </td>

                                <td>
                                    ₹
                                    {
                                        holding.averagePrice
                                    }
                                </td>

                                <td>
                                    ₹
                                    {(
                                        (holding.quantity || 0) *
                                        (holding.averagePrice || 0)
                                    ).toLocaleString()}
                                </td>
                            </tr>
                        )
                    )}
                    </tbody>
                </table>
            </div>
        </div>
    </div>
);


}

export default Portfolio;


