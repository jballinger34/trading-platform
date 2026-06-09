import Sidebar from "../components/Sidebar";
import TopNavbar from "../components/TopNavbar";

const orders = [
    {
        id: "ORD-1001",
        symbol: "AAPL",
        type: "BUY",
        quantity: 10,
        price: "₹190",
        status: "Completed"
    },
    {
        id: "ORD-1002",
        symbol: "NVDA",
        type: "BUY",
        quantity: 5,
        price: "₹450",
        status: "Pending"
    },
    {
        id: "ORD-1003",
        symbol: "TSLA",
        type: "SELL",
        quantity: 3,
        price: "₹230",
        status: "Cancelled"
    },
    {
        id: "ORD-1004",
        symbol: "MSFT",
        type: "BUY",
        quantity: 8,
        price: "₹420",
        status: "Completed"
    }
];

function Orders() {
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
                        fontSize: "36px",
                        marginBottom: "30px",
                        background:
                            "linear-gradient(90deg,#60a5fa,#22c55e)",
                        WebkitBackgroundClip: "text",
                        WebkitTextFillColor: "transparent"
                    }}
                >
                    Orders Management
                </h1>

                <div
                    style={{
                        display: "grid",
                        gridTemplateColumns:
                            "repeat(auto-fit,minmax(250px,1fr))",
                        gap: "20px",
                        marginBottom: "30px"
                    }}
                >
                    <div
                        style={{
                            background: "#111827",
                            padding: "25px",
                            borderRadius: "20px"
                        }}
                    >
                        <p>Total Orders</p>
                        <h2>124</h2>
                    </div>

                    <div
                        style={{
                            background: "#111827",
                            padding: "25px",
                            borderRadius: "20px"
                        }}
                    >
                        <p>Completed</p>
                        <h2 style={{ color: "#22c55e" }}>
                            95
                        </h2>
                    </div>

                    <div
                        style={{
                            background: "#111827",
                            padding: "25px",
                            borderRadius: "20px"
                        }}
                    >
                        <p>Pending</p>
                        <h2 style={{ color: "#f59e0b" }}>
                            20
                        </h2>
                    </div>

                    <div
                        style={{
                            background: "#111827",
                            padding: "25px",
                            borderRadius: "20px"
                        }}
                    >
                        <p>Cancelled</p>
                        <h2 style={{ color: "#ef4444" }}>
                            9
                        </h2>
                    </div>
                </div>

                <div
                    style={{
                        background: "#111827",
                        borderRadius: "20px",
                        padding: "25px"
                    }}
                >
                    <h2>Recent Orders</h2>

                    <table
                        style={{
                            width: "100%",
                            marginTop: "20px"
                        }}
                    >
                        <thead>
                        <tr>
                            <th align="left">Order ID</th>
                            <th align="left">Symbol</th>
                            <th align="left">Type</th>
                            <th align="left">Qty</th>
                            <th align="left">Price</th>
                            <th align="left">Status</th>
                        </tr>
                        </thead>

                        <tbody>
                        {orders.map((order) => (
                            <tr key={order.id}>
                                <td>{order.id}</td>
                                <td>{order.symbol}</td>
                                <td
                                    style={{
                                        color:
                                            order.type === "BUY"
                                                ? "#22c55e"
                                                : "#ef4444"
                                    }}
                                >
                                    {order.type}
                                </td>
                                <td>{order.quantity}</td>
                                <td>{order.price}</td>
                                <td>
                                    <span
                                        style={{
                                            padding:
                                                "6px 12px",
                                            borderRadius:
                                                "20px",
                                            background:
                                                order.status === "Completed"
                                                    ? "#14532d"
                                                    : order.status === "Pending"
                                                        ? "#78350f"
                                                        : "#7f1d1d"
                                        }}
                                    >
                                        {order.status}
                                    </span>
                                </td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    );
}

export default Orders;