import { useState } from "react";
import Sidebar from "../components/Sidebar";
import Modal from "../components/Modal";
import StatCard from "../components/StatCard";
import TopNavbar from "../components/TopNavbar";
function Wallet() {

    const [depositOpen, setDepositOpen] =
        useState(false);

    const [withdrawOpen, setWithdrawOpen] =
        useState(false);

    return (
        <div
            style={{
                display: "flex",
                minHeight: "100vh"
            }}
        >
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
                        fontSize: "36px",
                        marginBottom: "30px",
                        background:
                            "linear-gradient(90deg,#60a5fa,#22c55e)",
                        WebkitBackgroundClip: "text",
                        WebkitTextFillColor: "transparent"
                    }}
                >
                    Wallet Management
                </h1>

                <div
                    style={{
                        display: "grid",
                        gridTemplateColumns:
                            "repeat(auto-fit,minmax(250px,1fr))",
                        gap: "20px"
                    }}
                >
                    <StatCard
                        title="Available Balance"
                        value="₹ 50,000"
                    />

                    <StatCard
                        title="Reserved Funds"
                        value="₹ 12,500"
                    />

                    <StatCard
                        title="Buying Power"
                        value="₹ 62,500"
                        color="#22c55e"
                    />
                </div>

                <div
                    style={{
                        marginTop: "30px",
                        display: "flex",
                        gap: "20px"
                    }}
                >
                    <button
                        onClick={() =>
                            setDepositOpen(true)
                        }
                        style={{
                            padding:
                                "12px 24px",
                            border: "none",
                            borderRadius:
                                "12px",
                            cursor: "pointer",
                            background:
                                "#22c55e",
                            color: "white"
                        }}
                    >
                        Deposit Funds
                    </button>

                    <button
                        onClick={() =>
                            setWithdrawOpen(true)
                        }
                        style={{
                            padding:
                                "12px 24px",
                            border: "none",
                            borderRadius:
                                "12px",
                            cursor: "pointer",
                            background:
                                "#ef4444",
                            color: "white"
                        }}
                    >
                        Withdraw Funds
                    </button>
                </div>

                <div
                    style={{
                        marginTop: "30px",
                        background:
                            "#111827",
                        borderRadius:
                            "20px",
                        padding: "25px"
                    }}
                >
                    <h2>
                        Recent Transactions
                    </h2>

                    <table
                        style={{
                            width: "100%",
                            marginTop:
                                "20px"
                        }}
                    >
                        <thead>
                        <tr>
                            <th align="left">
                                Type
                            </th>
                            <th align="left">
                                Amount
                            </th>
                            <th align="left">
                                Date
                            </th>
                        </tr>
                        </thead>

                        <tbody>
                        <tr>
                            <td>
                                Deposit
                            </td>
                            <td
                                style={{
                                    color:
                                        "#22c55e"
                                }}
                            >
                                +₹10,000
                            </td>
                            <td>
                                12 Jun
                            </td>
                        </tr>

                        <tr>
                            <td>
                                Withdrawal
                            </td>
                            <td
                                style={{
                                    color:
                                        "#ef4444"
                                }}
                            >
                                -₹5,000
                            </td>
                            <td>
                                11 Jun
                            </td>
                        </tr>

                        <tr>
                            <td>
                                Deposit
                            </td>
                            <td
                                style={{
                                    color:
                                        "#22c55e"
                                }}
                            >
                                +₹20,000
                            </td>
                            <td>
                                08 Jun
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>

                <Modal
                    isOpen={
                        depositOpen
                    }
                    onClose={() =>
                        setDepositOpen(false)
                    }
                    title="Deposit Funds"
                >
                    <input
                        placeholder="Amount"
                        style={{
                            width: "100%",
                            padding:
                                "12px",
                            borderRadius:
                                "10px",
                            border:
                                "1px solid #374151",
                            background:
                                "#1f2937",
                            color:
                                "white"
                        }}
                    />

                    <button
                        style={{
                            marginTop:
                                "15px",
                            width:
                                "100%",
                            padding:
                                "12px",
                            background:
                                "#22c55e",
                            border:
                                "none",
                            borderRadius:
                                "10px",
                            color:
                                "white"
                        }}
                    >
                        Confirm Deposit
                    </button>
                </Modal>

                <Modal
                    isOpen={
                        withdrawOpen
                    }
                    onClose={() =>
                        setWithdrawOpen(false)
                    }
                    title="Withdraw Funds"
                >
                    <input
                        placeholder="Amount"
                        style={{
                            width: "100%",
                            padding:
                                "12px",
                            borderRadius:
                                "10px",
                            border:
                                "1px solid #374151",
                            background:
                                "#1f2937",
                            color:
                                "white"
                        }}
                    />

                    <button
                        style={{
                            marginTop:
                                "15px",
                            width:
                                "100%",
                            padding:
                                "12px",
                            background:
                                "#ef4444",
                            border:
                                "none",
                            borderRadius:
                                "10px",
                            color:
                                "white"
                        }}
                    >
                        Confirm Withdrawal
                    </button>
                </Modal>
            </div>
        </div>
    );
}

export default Wallet;