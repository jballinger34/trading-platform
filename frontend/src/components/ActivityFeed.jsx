function ActivityFeed() {
    const activities = [
        "Bought 10 AAPL shares",
        "Sold 5 TSLA shares",
        "Added ₹10,000 to wallet",
        "Placed NVDA order"
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
            <h2>Recent Activity</h2>

            {activities.map((item, index) => (
                <p
                    key={index}
                    style={{
                        marginTop: "18px",
                        color: "#cbd5e1"
                    }}
                >
                    • {item}
                </p>
            ))}
        </div>
    );
}

export default ActivityFeed;