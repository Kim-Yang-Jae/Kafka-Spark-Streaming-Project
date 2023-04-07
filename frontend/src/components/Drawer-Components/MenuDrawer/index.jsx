import * as React from "react";
import { useNavigate } from "react-router-dom";

//MUI
import Box from "@mui/material/Box";
import List from "@mui/material/List";
import Divider from "@mui/material/Divider";
import ListItem from "@mui/material/ListItem";
import ListItemButton from "@mui/material/ListItemButton";
import ListItemIcon from "@mui/material/ListItemIcon";
import ListItemText from "@mui/material/ListItemText";
import WebhookIcon from "@mui/icons-material/Webhook";
import WorkIcon from "@mui/icons-material/Work";
import TextFieldsIcon from "@mui/icons-material/TextFields";
import SouthAmericaIcon from "@mui/icons-material/SouthAmerica";

const MenuDrawer = ({ anchor, toggleDrawer, handleNavigate }) => {
  const navigate = useNavigate();

  return (
    <Box
      sx={{ width: 250 }}
      role="presentation"
      onClick={toggleDrawer(anchor, false)}
      onKeyDown={toggleDrawer(anchor, false)}
    >
      <List>
        <ListItem>
          <ListItemButton
            onClick={() => {
              navigate("/main");
            }}
          >
            <img src={"/images/tlens_logo.png"} alt="" />
          </ListItemButton>
        </ListItem>
      </List>
      <Divider sx={{ backgroundColor: "#0066CC", height: "3px" }} />
      <List>
        {["분야별", "지역별"].map((text, index) => (
          <ListItem key={text} disablePadding>
            <ListItemButton
              onClick={() => {
                handleNavigate(text);
              }}
            >
              <ListItemIcon>
                {index % 2 === 0 ? (
                  <TextFieldsIcon color="primary" />
                ) : (
                  <SouthAmericaIcon color="primary" />
                )}
              </ListItemIcon>
              <ListItemText sx={{ color: "#0066CC" }} primary={text} />
            </ListItemButton>
          </ListItem>
        ))}
      </List>
      <Divider />
      <List>
        {["기업 분석", "언론사 분석"].map((text, index) => (
          <ListItem key={text} disablePadding>
            <ListItemButton
              onClick={() => {
                handleNavigate(text);
              }}
            >
              <ListItemIcon>
                {index % 2 === 0 ? (
                  <WebhookIcon color="primary" />
                ) : (
                  <WorkIcon color="primary" />
                )}
              </ListItemIcon>
              <ListItemText sx={{ color: "#0066CC" }} primary={text} />
            </ListItemButton>
          </ListItem>
        ))}
        <Divider />
      </List>
    </Box>
  );
};

export default MenuDrawer;
