function TradeModal({
                        isOpen,
                        onClose
                    }) {
    if (!isOpen) return null;

    return (
        <div
            style={{
                position: "fixed",
                inset: 0,
                background:
                    "rgba(0,0,0,.6)",
                display: "flex",
                justifyContent: "center",
                alignItems: "center"
            }}
        >
            <div
                className="glass-card"
                style={{
                    width: "400px"
                }}
            >
                <h2>Place Order</h2>

                <input
                    placeholder="Symbol"
                    style={{
                        width: "100%",
                        marginTop: "15px",
                        padding: "12px"
                    }}
                />

                <input
                    placeholder="Quantity"
                    style={{
                        width: "100%",
                        marginTop: "15px",
                        padding: "12px"
                    }}
                />

                <button
                    style={{
                        marginTop: "20px",
                        width: "100%",
                        padding: "12px",
                        background:
                            "#22c55e",
                        border: "none",
                        borderRadius: "12px",
                        color: "white"
                    }}
                >
                    Buy
                </button>

                <button
                    onClick={onClose}
                    style={{
                        marginTop: "10px",
                        width: "100%",
                        padding: "12px"
                    }}
                >
                    Close
                </button>
            </div>
        </div>
    );
}

export default TradeModal;