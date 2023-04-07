import * as React from "react";
import { useState, useLocation } from "react";
import MainComponent from "../../components/Main-Components/Main";
import "./Main.scss";

// MUI
import Box from "@mui/material/Box";
import Tabs, { tabsClasses } from "@mui/material/Tabs";
import Tab from "@mui/material/Tab";
import TabPanel from "@mui/lab/TabPanel";
import TabContext from "@mui/lab/TabContext";
import Divider from "@mui/material/Divider";

// MUI Icons
import AppsIcon from "@mui/icons-material/Apps";
import HandshakeIcon from "@mui/icons-material/Handshake";
import MonetizationOnIcon from "@mui/icons-material/MonetizationOn";
import LanguageIcon from "@mui/icons-material/Language";
import GroupsIcon from "@mui/icons-material/Groups";
import StarIcon from "@mui/icons-material/Star";
import AutoAwesomeOutlinedIcon from "@mui/icons-material/AutoAwesomeOutlined";
import EmojiEventsOutlinedIcon from "@mui/icons-material/EmojiEventsOutlined";
import ImportantDevicesOutlinedIcon from "@mui/icons-material/ImportantDevicesOutlined";
import PsychologyAltIcon from "@mui/icons-material/PsychologyAlt";

const Main = () => {
  const [value, setValue] = useState("1");

  const handleChange = (event, newValue) => {
    setValue(newValue);
  };

  return (
    <TabContext value={value}>
      <Box className="main-container">
        <Tabs
          value={value}
          onChange={handleChange}
          variant="scrollable"
          scrollButtons
          aria-label="visible arrows tabs example"
          sx={{
            [`& .${tabsClasses.scrollButtons}`]: {
              "&.Mui-disabled": { opacity: 0.3 },
            },
          }}
        >
          <Tab icon={<AppsIcon fontSize="large" />} label="전체" value="1" />
          <Tab
            icon={<ImportantDevicesOutlinedIcon fontSize="large" />}
            sx={{ width: "10%" }}
            label="IT"
            value="2"
          />
          <Tab
            icon={<MonetizationOnIcon fontSize="large" />}
            label="경제"
            value="3"
          />
          <Tab icon={<GroupsIcon fontSize="large" />} label="사회" value="4" />
          <Tab
            icon={<HandshakeIcon fontSize="large" />}
            label="정치"
            value="5"
          />

          <Tab
            icon={<LanguageIcon fontSize="large" />}
            label="세계"
            value="6"
          />

          <Tab icon={<StarIcon fontSize="large" />} label="생활" value="7" />
          <Tab
            icon={<EmojiEventsOutlinedIcon fontSize="large" />}
            label="스포츠"
            value="8"
          />
          <Tab
            icon={<AutoAwesomeOutlinedIcon fontSize="large" />}
            label="연예"
            value="9"
          />
          <Tab
            icon={<PsychologyAltIcon fontSize="large" />}
            label="오피니언"
            value="10"
          />
        </Tabs>
        <Divider />
      </Box>
      <TabPanel value={value}>
        <MainComponent value={value} />
      </TabPanel>
    </TabContext>
  );
};

export default Main;
