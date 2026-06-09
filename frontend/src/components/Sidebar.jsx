import { Link } from "react-router-dom";
import { FaStar } from "react-icons/fa";
import {
    FaChartLine,
    FaWallet,
    FaBriefcase,
    FaExchangeAlt
} from "react-icons/fa";

function Sidebar() {
    return (
        <div
            style={{
                width: "220px",
                background: "#111827",
                minHeight: "100vh",
                padding: "25px",
                borderRight: "1px solid #1f2937"
            }}
        >
            <h2
                style={{
                    fontSize: "28px",
                    fontWeight: "700",
                    background:
                        "linear-gradient(90deg,#60a5fa,#22c55e)",
                    WebkitBackgroundClip: "text",
                    WebkitTextFillColor: "transparent",
                    marginBottom: "40px"
                }}
            >
                TradeFlow
            </h2>

            <div
                style={{
                    display: "flex",
                    flexDirection: "column",
                    gap: "20px"
                }}
            >
                <Link
                    to="/"
                    style={{
                        padding: "12px",
                        borderRadius: "12px",
                        transition: "0.3s"
                    }}
                >
                    <FaChartLine /> Dashboard
                </Link>

                <Link
                    to="/portfolio"
                    style={{
                        padding: "12px",
                        borderRadius: "12px",
                        transition: "0.3s"
                    }}
                >
                    <FaBriefcase /> Portfolio
                </Link>

                <Link
                    to="/wallet"
                    style={{
                        padding: "12px",
                        borderRadius: "12px",
                        transition: "0.3s"
                    }}
                >
                    <FaWallet /> Wallet
                </Link>

                <Link
                    to="/orders"
                    style={{
                        padding: "12px",
                        borderRadius: "12px",
                        transition: "0.3s"
                    }}
                >
                    <FaExchangeAlt /> Orders
                </Link>

                <Link to="/watchlist">
                    <FaStar /> Watchlist
                </Link>

                <div
                    style={{
                        marginTop: "auto",
                        paddingTop: "40px",
                        color: "#64748b",
                        fontSize: "13px"
                    }}
                >
                    TradeFlow v1.0
                </div>
            </div>
        </div>
    );
}

export default Sidebar;