function SummaryCard({
                         title,
                         value,
                         color
                     }) {
    return (
        <div
            style={{
                background:
                    "linear-gradient(145deg,#111827,#1e293b)",
                padding: "24px",
                borderRadius: "20px",
                minWidth: "230px",
                flex: 1,
                boxShadow:
                    "0 8px 25px rgba(0,0,0,0.3)"
            }}
        >
            <p
                style={{
                    color: "#94a3b8",
                    marginBottom: "10px"
                }}
            >
                {title}
            </p>

            <h2
                style={{
                    color: color || "white"
                }}
            >
                {value}
            </h2>
        </div>
    );
}

export default SummaryCard;