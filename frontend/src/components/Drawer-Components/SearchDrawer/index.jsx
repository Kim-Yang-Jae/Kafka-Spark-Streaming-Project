import * as React from "react";

//MUI
import Box from "@mui/material/Box";
import Input from "@mui/joy/Input";

const SearchDrawer = ({ anchor, toggleDrawer1, handleKeyword }) => {
  return (
    <Box sx={{ width: "auto" }} role="presentation">
      <br />
      <br />
      <Box sx={{ display: "flex", justifyContent: "center" }}>
        <Box sx={{ width: "70%" }}>
          <Input
            sx={{ marginLeft: "10%", marginRight: "1%" }}
            color="primary"
            variant="outlined"
            placeholder="ex)삼성전자, 카카오, 네이버"
            autoFocus={true}
            onKeyUp={toggleDrawer1(anchor, false)}
            onKeyPress={handleKeyword}
          />
        </Box>
      </Box>
      <br />
      <br />
    </Box>
  );
};

export default SearchDrawer;
