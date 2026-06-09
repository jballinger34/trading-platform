import {
    PieChart,
    Pie,
    Cell,
    Tooltip,
    ResponsiveContainer
} from "recharts";

const data = [
    { name: "AAPL", value: 35 },
    { name: "NVDA", value: 25 },
    { name: "TSLA", value: 20 },
    { name: "MSFT", value: 20 }
];

const COLORS = [
    "#2563eb",
    "#22c55e",
    "#f59e0b",
    "#ef4444"
];

function AllocationChart() {
    return (
        <div
            style={{
                background: "#111827",
                borderRadius: "20px",
                padding: "25px",
                border: "1px solid #1e293b",
                height: "350px"
            }}
        >
            <h2>Portfolio Allocation</h2>

            <ResponsiveContainer width="100%" height="90%">
                <PieChart>
                    <Pie
                        data={data}
                        dataKey="value"
                        nameKey="name"
                        outerRadius={100}
                    >
                        {data.map((entry, index) => (
                            <Cell
                                key={index}
                                fill={COLORS[index]}
                            />
                        ))}
                    </Pie>

                    <Tooltip />
                </PieChart>
            </ResponsiveContainer>
        </div>
    );
}

export default AllocationChart;