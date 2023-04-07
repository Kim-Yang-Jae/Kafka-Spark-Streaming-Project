import * as React from "react";
import "./Header.scss";
import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";

//Component
import MenuDrawer from "../../components/Drawer-Components/MenuDrawer";
import SearchDrawer from "../../components/Drawer-Components/SearchDrawer";
import PersonalDrawer from "../../components/Drawer-Components/PersonalDrawer";

//MUI
import AppBar from "@mui/material/AppBar";
import Box from "@mui/material/Box";
import Toolbar from "@mui/material/Toolbar";
import Button from "@mui/material/Button";
import IconButton from "@mui/material/IconButton";
import MenuIcon from "@mui/icons-material/Menu";
import PersonIcon from "@mui/icons-material/Person";
import SearchIcon from "@mui/icons-material/Search";
import Drawer from "@mui/material/Drawer";
import UploadIcon from "@mui/icons-material/Upload";

const Header = () => {
  const navigate = useNavigate();

  const [state, setState] = useState({
    top: false,
    left: false,
    right: false,
  });

  const toggleDrawer = (anchor, open) => (event) => {
    if (event && (event.key === "Tab" || event.key === "Shift")) {
      return;
    }

    setState({ ...state, [anchor]: open });
  };

  // TOP버튼
  const scrollToTop = () => {
    window.scrollTo({
      top: 0,
      behavior: "smooth",
    });
  };

  const handleNavigate = (text) => {
    if (text === "분야별") {
      navigate("/main");
    } else if (text === "지역별") {
      navigate("/region");
    } else if (text === "언론사 분석") {
      navigate("/reporter");
    } else if (text === "기업 분석") {
      navigate("/company");
    } else {
      navigate("/statistics");
    }
  };

  const list = (anchor) => (
    <MenuDrawer
      anchor={anchor}
      toggleDrawer={toggleDrawer}
      handleNavigate={handleNavigate}
    />
  );

  const [keyword, setKeyword] = useState("");

  // 검색 키워드 상태관리
  const handleKeyword = (e) => {
    setKeyword(e.target.value);
  };

  const toggleDrawer1 = (anchor, open) => (event) => {
    if (event.key === "Enter") {
      setState({ ...state, [anchor]: open });
      navigate(`/search/${keyword}`);
    }
  };

  const search = (anchor) => (
    <SearchDrawer
      anchor={anchor}
      toggleDrawer1={toggleDrawer1}
      handleKeyword={handleKeyword}
    />
  );

  const personalInfo = (anchor) => (
    <PersonalDrawer
      anchor={anchor}
      toggleDrawer={toggleDrawer}
      handleKeyword={handleKeyword}
    />
  );

  return (
    <div className="header-main">
      <AppBar position="static" color="inherit">
        <Toolbar>
          <React.Fragment key={"left"}>
            <IconButton
              onClick={toggleDrawer("left", true)}
              size="large"
              edge="start"
              color="primary"
              aria-label="menu"
              sx={{ mr: 2 }}
            >
              <MenuIcon />
            </IconButton>
            <Drawer
              anchor={"left"}
              open={state["left"]}
              onClose={toggleDrawer("left", false)}
            >
              {list("left")}
            </Drawer>
          </React.Fragment>
          <Button
            onClick={() => {
              navigate("/main");
            }}
          >
            <img src={"/images/tlens_logo.png"} alt="" />
          </Button>
          <Box>
            <React.Fragment key={"top"}>
              <IconButton
                size="large"
                edge="start"
                color="primary"
                sx={{ marginRight: "10px" }}
                onClick={toggleDrawer("top", true)}
              >
                <SearchIcon />
              </IconButton>
              <Drawer
                anchor={"top"}
                open={state["top"]}
                onClose={toggleDrawer("top", false)}
              >
                {search("top")}
              </Drawer>
            </React.Fragment>

            <React.Fragment key={"right"}>
              <IconButton
                size="large"
                edge="start"
                color="primary"
                sx={{ marginRight: "10px" }}
                onClick={toggleDrawer("right", true)}
              >
                <PersonIcon />
              </IconButton>
              <Drawer
                anchor={"right"}
                open={state["right"]}
                onClose={toggleDrawer("right", false)}
              >
                {personalInfo("right")}
              </Drawer>
            </React.Fragment>
          </Box>
        </Toolbar>
      </AppBar>
      <div
        style={{
          position: "fixed",
          right: "3%",
          bottom: "3%",
        }}
      >
        <IconButton
          size="large"
          edge="start"
          color="primary"
          onClick={scrollToTop}
        >
          <UploadIcon fontSize="large" />
        </IconButton>
      </div>
    </div>
  );
};

export default Header;
