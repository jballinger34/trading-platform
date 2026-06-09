function Watchlist() {
    const stocks = [
        { symbol: "AAPL", price: "$190", change: "+2.4%" },
        { symbol: "NVDA", price: "$450", change: "+4.1%" },
        { symbol: "TSLA", price: "$230", change: "-1.3%" },
        { symbol: "MSFT", price: "$420", change: "+0.9%" }
    ];

    return (
        <div
            style={{
                background: "#111827",
                padding: "25px",
                borderRadius: "20px",
                border: "1px solid #1e293b"
            }}
        >
            <h2>Watchlist</h2>

            {stocks.map(stock => (
                <div
                    key={stock.symbol}
                    style={{
                        display: "flex",
                        justifyContent: "space-between",
                        marginTop: "20px"
                    }}
                >
                    <span>{stock.symbol}</span>

                    <span>{stock.price}</span>

                    <span
                        style={{
                            color:
                                stock.change.includes("+")
                                    ? "#22c55e"
                                    : "#ef4444"
                        }}
                    >
                        {stock.change}
                    </span>
                </div>
            ))}
        </div>
    );
}

export default Watchlist;