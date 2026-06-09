function Navbar() {
    return (
        <div
            style={{
                display: "flex",
                justifyContent: "space-between",
                alignItems: "center",
                marginBottom: "30px"
            }}
        >
            <div>
                <h1
                    style={{
                        fontSize: "30px",
                        fontWeight: "700"
                    }}
                >
                    TradeFlow Pro
                </h1>

                <p
                    style={{
                        color: "#94a3b8"
                    }}
                >
                    Professional Trading Platform
                </p>
            </div>

            <div
                style={{
                    display: "flex",
                    gap: "20px",
                    alignItems: "center"
                }}
            >
                <input
                    placeholder="Search stocks..."
                    style={{
                        padding: "12px",
                        width: "250px",
                        borderRadius: "12px",
                        border: "1px solid #334155",
                        background: "#111827",
                        color: "white"
                    }}
                />

                <div
                    style={{
                        width: "45px",
                        height: "45px",
                        borderRadius: "50%",
                        background:
                            "linear-gradient(135deg,#60a5fa,#22c55e)"
                    }}
                />
            </div>
        </div>
    );
}

export default Navbar;