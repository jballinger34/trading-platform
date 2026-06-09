import {
    AreaChart,
    Area,
    XAxis,
    YAxis,
    Tooltip,
    ResponsiveContainer
} from "recharts";

const data = [
    { day: "Mon", value: 210000 },
    { day: "Tue", value: 220000 },
    { day: "Wed", value: 225000 },
    { day: "Thu", value: 235000 },
    { day: "Fri", value: 245000 }
];

function PortfolioChart() {
    return (
        <div
            style={{
                background: "#111827",
                borderRadius: "20px",
                padding: "20px",
                marginTop: "30px",
                height: "350px",
                width: "100%"
            }}
        >
            <h3
                style={{
                    marginBottom: "20px"
                }}
            >
                Portfolio Performance
            </h3>

            <ResponsiveContainer
                width="100%"
                height="85%"
            >
                <AreaChart data={data}>
                    <XAxis dataKey="day" />
                    <YAxis />
                    <Tooltip />

                    <Area
                        type="monotone"
                        dataKey="value"
                        stroke="#3b82f6"
                        fill="#3b82f6"
                    />
                </AreaChart>
            </ResponsiveContainer>
        </div>
    );
}

export default PortfolioChart;