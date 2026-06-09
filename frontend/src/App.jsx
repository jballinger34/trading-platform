import { BrowserRouter, Routes, Route } from "react-router-dom";

import Dashboard from "./pages/Dashboard";
import Portfolio from "./pages/Portfolio";
import Wallet from "./pages/Wallet";
import Orders from "./pages/Orders";
import Watchlist from "./pages/Watchlist";

function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Dashboard />} />
                <Route path="/portfolio" element={<Portfolio />} />
                <Route path="/wallet" element={<Wallet />} />
                <Route path="/orders" element={<Orders />} />
                <Route path="/watchlist" element={<Watchlist />} />
            </Routes>
        </BrowserRouter>
    );
}

export default App;