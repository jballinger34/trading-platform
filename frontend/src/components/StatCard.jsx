function StatCard({
                      title,
                      value,
                      color = "#60a5fa"
                  }) {
    return (
        <div
            style={{
                background:
                    "rgba(30,41,59,0.6)",
                backdropFilter:
                    "blur(12px)",
                border:
                    "1px solid rgba(255,255,255,0.08)",
                padding: "25px",
                borderRadius: "20px",
                transition: "0.3s",
                boxShadow:
                    "0 0 25px rgba(37,99,235,0.15)"
            }}
        >
            <p
                style={{
                    color: "#94a3b8"
                }}
            >
                {title}
            </p>

            <h2
                style={{
                    color,
                    marginTop: "10px"
                }}
            >
                {value}
            </h2>
        </div>
    );
}

export default StatCard;