function MarketTicker() {
    const stocks = [
        "AAPL +2.4%",
        "NVDA +4.1%",
        "TSLA -1.3%",
        "MSFT +0.9%",
        "AMZN +1.8%",
        "GOOGL +2.1%"
    ];

    return (
        <div
            style={{
                background: "#111827",
                padding: "12px",
                borderRadius: "12px",
                overflow: "hidden",
                whiteSpace: "nowrap",
                marginBottom: "20px"
            }}
        >
            <marquee>
                {stocks.join("   •   ")}
            </marquee>
        </div>
    );
}

export default MarketTicker;