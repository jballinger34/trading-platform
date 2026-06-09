import { FaBell, FaSearch } from "react-icons/fa";

function TopNavbar() {
    return (
        <div
            style={{
                display: "flex",
                justifyContent: "space-between",
                alignItems: "center",
                marginBottom: "30px"
            }}
        >
            <div
                style={{
                    display: "flex",
                    alignItems: "center",
                    gap: "12px",
                    background: "#111827",
                    padding: "12px 20px",
                    borderRadius: "12px",
                    width: "350px"
                }}
            >
                <FaSearch />
                <input
                    placeholder="Search stocks..."
                    style={{
                        background: "transparent",
                        border: "none",
                        outline: "none",
                        color: "white",
                        width: "100%"
                    }}
                />
            </div>

            <div
                style={{
                    display: "flex",
                    alignItems: "center",
                    gap: "20px"
                }}
            >
                <FaBell size={20} />

                <div
                    style={{
                        display: "flex",
                        alignItems: "center",
                        gap: "12px"
                    }}
                >
                    <img
                        src="https://i.pravatar.cc/100"
                        alt=""
                        style={{
                            width: "42px",
                            height: "42px",
                            borderRadius: "50%"
                        }}
                    />

                    <div>
                        <div>Avantee Singh</div>

                        <small
                            style={{
                                color: "#94a3b8"
                            }}
                        >
                            Trader
                        </small>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default TopNavbar;