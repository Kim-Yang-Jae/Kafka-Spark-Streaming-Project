import "./App.scss";

import React from "react";
import { Routes, Route, useLocation } from "react-router-dom";

import Header from "./layout/Header";
import Footer from "./layout/Footer";
import Home from "./pages/Home";
import Auth from "./pages/Auth";
import Main from "./pages/Main";
import SearchResult from "./pages/SearchResult";
import Company from "./pages/Company";
import Region from "./pages/Region";
import Reporter from "./pages/Reporter";
import CompanyDetail from "./components/Company-Components/CompanyDetail";
import Mypage from "./pages/Mypage";
import ReporterDetail from "./components/Reporter-Components/ReporterDetail";

// 로그인한 경우에만 이용가능하게 하는 루트
import PrivateRoute from "./routes/PrivateRoutes";
// 로그인한 경우에는 접근이 불가능하게 만든 루트
import PublicRoute from "./routes/PublicRoutes";

function App() {
  const location = useLocation();
  const { pathname } = useLocation();
  const isHomePage = pathname === "/";
  const showHeader = !isHomePage;
  const showFooter = !isHomePage;

  return (
    <React.Fragment>
      {showHeader && <Header />}
      <div className="App">
        <Routes>
          <Route path="/" element={<Home />} />
          <Route
            path="/auth"
            element={<PublicRoute path="/auth" component={Auth} />}
          />
          <Route path="/main" element={<Main />} />
          <Route path="/search/:keyword" element={<SearchResult />} />
          <Route path="/company" element={<Company />} />
          <Route path="/region" element={<Region />} />
          {/* <Route path="/mypage" element={<Mypage />} /> */}
          <Route
            path="/mypage"
            element={<PrivateRoute path="/mypage" component={Mypage} />}
          />
          <Route path="/reporter" element={<Reporter />} />
          <Route path="/company/:name" element={<CompanyDetail />} />
          <Route path="/reporter/:name" element={<ReporterDetail />} />
        </Routes>
      </div>
       {location.pathname !== "/reporter" && showFooter && <Footer />}
    </React.Fragment>
  );
}

export default App;
