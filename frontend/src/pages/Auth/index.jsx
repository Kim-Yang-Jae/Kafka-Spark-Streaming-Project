import * as React from 'react';
import Box from '@mui/material/Box';
import Tab from '@mui/material/Tab';
import TabContext from '@mui/lab/TabContext';
import TabList from '@mui/lab/TabList';
import TabPanel from '@mui/lab/TabPanel';
import { Button } from '@mui/material';
import { useNavigate } from 'react-router-dom';

import SignUp from '../Auth/Signup';
import Login from '../Auth/Login';

import './auth.scss';

export default function Auth() {
  const navigate = useNavigate()
  
  const [value, setValue] = React.useState('1');

  const handleChange = (event, newValue) => {
    setValue(newValue);
  };

  return (
    <div className="auth-wrapper">
      <div className="auth-container">
        <div className="auth-box1">

        </div>
        <div className="auth-box2">
          <Box sx={{ width: '100%', typography: 'body1' }}>
            <TabContext value={value}>
              <Box sx={{ borderBottom: 1, borderColor: 'divider' }}>
                <TabList onChange={handleChange} aria-label="lab API tabs example">
                  <Tab label="Login" value="1"   style={{marginInline: '50px'}}/>
                  <Tab label="Sign Up" value="2" />
                </TabList>
              </Box>
              <TabPanel value="1"><Login/></TabPanel>
              <TabPanel value="2"><SignUp /></TabPanel>
            </TabContext>
          </Box>
        </div>
      </div>
    </div>
  );
}
