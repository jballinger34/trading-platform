import Sidebar from "../components/Sidebar";
import TopNavbar from "../components/TopNavbar";

function Settings() {
    return (
        <div style={{ display: "flex" }}>
            <Sidebar />

            <div
                style={{
                    flex: 1,
                    padding: "35px"
                }}
            >
                <TopNavbar />

                <h1
                    style={{
                        marginTop: "25px"
                    }}
                >
                    Settings
                </h1>

                <div
                    className="glass-card"
                    style={{
                        marginTop: "25px"
                    }}
                >
                    <h3>User Profile</h3>

                    <p>Name: Avantee Singh</p>

                    <p>Email: avantee280209@gmail.com</p>

                    <p>Role: Trader</p>
                </div>
            </div>
        </div>
    );
}

export default Settings;