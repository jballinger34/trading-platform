import Sidebar from "../components/Sidebar";
import TopNavbar from "../components/TopNavbar";
import {
    PieChart,
    Pie,
    Cell,
    Tooltip,
    ResponsiveContainer
} from "recharts";

const data = [
    { name: "AAPL", value: 35 },
    { name: "NVDA", value: 30 },
    { name: "TSLA", value: 20 },
    { name: "MSFT", value: 15 }
];

const COLORS = [
    "#60a5fa",
    "#22c55e",
    "#f59e0b",
    "#ef4444"
];

function Portfolio() {
    return (
        <div style={{ display: "flex", minHeight: "100vh" }}> <Sidebar />

            ```
            <div style={{ flex: 1, padding: "35px" }}>
                <TopNavbar />

                <h1
                    style={{
                        marginTop: "25px",
                        marginBottom: "25px",
                        fontSize: "40px",
                        background:
                            "linear-gradient(90deg,#60a5fa,#22c55e)",
                        WebkitBackgroundClip: "text",
                        WebkitTextFillColor: "transparent"
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
                        <p>Total Value</p>
                        <h2>₹ 2,45,000</h2>
                    </div>

                    <div className="glass-card">
                        <p>Total Gain</p>
                        <h2 style={{ color: "#22c55e" }}>
                            +₹24,500
                        </h2>
                    </div>

                    <div className="glass-card">
                        <p>Total Holdings</p>
                        <h2>8</h2>
                    </div>
                </div>

                <div
                    style={{
                        display: "grid",
                        gridTemplateColumns: "1fr 1fr",
                        gap: "20px",
                        marginTop: "25px"
                    }}
                >
                    <div className="glass-card">
                        <h2>Portfolio Allocation</h2>

                        <ResponsiveContainer
                            width="100%"
                            height={320}
                        >
                            <PieChart>
                                <Pie
                                    data={data}
                                    dataKey="value"
                                    outerRadius={120}
                                >
                                    {data.map((entry, index) => (
                                        <Cell
                                            key={index}
                                            fill={
                                                COLORS[index %
                                                COLORS.length]
                                            }
                                        />
                                    ))}
                                </Pie>
                                <Tooltip />
                            </PieChart>
                        </ResponsiveContainer>
                    </div>

                    <div className="glass-card">
                        <h2>Top Holdings</h2>

                        <div style={{ marginTop: "20px" }}>
                            <p>AAPL — 35%</p>
                            <p>NVDA — 30%</p>
                            <p>TSLA — 20%</p>
                            <p>MSFT — 15%</p>
                        </div>
                    </div>
                </div>

                <div
                    className="glass-card"
                    style={{ marginTop: "25px" }}
                >
                    <h2>Holdings</h2>

                    <table
                        style={{
                            width: "100%",
                            marginTop: "20px"
                        }}
                    >
                        <thead>
                        <tr>
                            <th align="left">Symbol</th>
                            <th align="left">Qty</th>
                            <th align="left">Avg Price</th>
                            <th align="left">P&L</th>
                        </tr>
                        </thead>

                        <tbody>
                        <tr>
                            <td>AAPL</td>
                            <td>25</td>
                            <td>₹190</td>
                            <td style={{ color: "#22c55e" }}>
                                +₹1200
                            </td>
                        </tr>

                        <tr>
                            <td>TSLA</td>
                            <td>10</td>
                            <td>₹230</td>
                            <td style={{ color: "#ef4444" }}>
                                -₹500
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    );


}

export default Portfolio;

