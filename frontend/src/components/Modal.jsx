function Modal({
                   isOpen,
                   onClose,
                   title,
                   children
               }) {
    if (!isOpen) return null;

    return (
        <div
            style={{
                position: "fixed",
                top: 0,
                left: 0,
                width: "100%",
                height: "100%",
                background:
                    "rgba(0,0,0,0.7)",
                display: "flex",
                justifyContent: "center",
                alignItems: "center",
                zIndex: 999
            }}
        >
            <div
                style={{
                    width: "400px",
                    background: "#111827",
                    padding: "25px",
                    borderRadius: "20px",
                    border:
                        "1px solid #1f2937"
                }}
            >
                <div
                    style={{
                        display: "flex",
                        justifyContent:
                            "space-between",
                        marginBottom: "20px"
                    }}
                >
                    <h2>{title}</h2>

                    <button
                        onClick={onClose}
                        style={{
                            background:
                                "transparent",
                            border: "none",
                            color: "white",
                            cursor: "pointer"
                        }}
                    >
                        ✕
                    </button>
                </div>

                {children}
            </div>
        </div>
    );
}

export default Modal;