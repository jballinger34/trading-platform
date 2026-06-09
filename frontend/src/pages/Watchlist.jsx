import Sidebar from "../components/Sidebar";
import TopNavbar from "../components/TopNavbar";


const stocks = [
    { symbol: "AAPL", price: "$214", change: "+2.4%" },
    { symbol: "NVDA", price: "$143", change: "+4.1%" },
    { symbol: "TSLA", price: "$182", change: "-1.3%" },
    { symbol: "MSFT", price: "$467", change: "+0.9%" }
];

function Watchlist() {
    return (
        <div style={{ display: "flex", minHeight: "100vh" }}>
            <Sidebar />

            <div style={{ flex: 1, padding: "35px" }}>
                <TopNavbar />

                <h1
                    style={{
                        marginTop: "25px",
                        fontSize: "40px",
                        marginBottom: "25px"
                    }}
                >
                    Watchlist
                </h1>

                <div
                    style={{
                        display: "grid",
                        gap: "20px"
                    }}
                >
                    {stocks.map((stock) => (
                        <div
                            key={stock.symbol}
                            className="glass-card"
                        >
                            <h2>{stock.symbol}</h2>
                            <p>{stock.price}</p>

                            <p
                                style={{
                                    color:
                                        stock.change.includes("-")
                                            ? "#ef4444"
                                            : "#22c55e"
                                }}
                            >
                                {stock.change}
                            </p>
                        </div>
                    ))}
                </div>
            </div>
        </div>
    );
}

export default Watchlist;