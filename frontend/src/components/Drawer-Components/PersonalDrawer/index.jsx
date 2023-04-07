import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { Box, Button, Divider } from "@mui/material";
import { ToastContainer } from "react-toastify";
import LogoutIcon from '@mui/icons-material/Logout';

// Components
import SubReportList from "../../MyPage-Components/MySubscribe/Sub-ReportList";
import "./personalDrawer.scss";

//Api
import { logout } from "../../../apis/users/";
import { getUserInfo } from "../../../apis/api/axiosinstance";

const PersonalDrawer = ({ anchor, toggleDrawer }) => {
  const navigate = useNavigate();

  const Authorization = localStorage.getItem("Authorization");
  const isLoggedIn = Authorization !== null;

  const [userInfo, setUserInfo] = useState([]);
  const userId = localStorage.getItem("userId");

  useEffect(() => {
    const fetchUserInfo = async () => {
      try {
        const id = localStorage.getItem("userId");
        const response = await getUserInfo(id);
        // 유저 정보 출력
        setUserInfo(response.data.content); // response 값을 userInfo에 설정합니다.
      } catch (error) {
        console.error(error);
      }
    };
    fetchUserInfo();
  }, [userId]);

  return (
    <div className="drawer-wrapper">
      <Box
        onClick={toggleDrawer(anchor, false)}
        sx={{ width: '300px', padding: 0  }}
        role="presentation"
      >
        <ToastContainer />
        <div>
          <img src="img/logo.png" alt="로고" style={{width:"100px", marginBlock: "10px"}}/>
          <Divider sx={{ borderWidth: "2px", color: "#0066cc" }} />
          {isLoggedIn ? (
            <h4 style={{fontWeight:"bold"}}>안녕하세요!! "{userInfo.nickname}" 님</h4>
          ) : (
            <div className="drawer-login">
              <Divider sx={{ borderWidth: "2px", color: "#0066cc" }} />
              <h5>
                <span className="t-lens">T:LENS</span>의 더 많은 서비스를
                원하시나요?
              </h5>
              <h5>지금 로그인해서 더 많은 서비스를 받아보세요</h5>
            </div>
          )}

          {isLoggedIn ? (
            <div>
              <Button
                color="primary"
                variant="contained"
                fullWidth
                type="submit"
                sx={{
                  fontSize: "10px",
                  fontWeight: "bold",
                  color: "white",
                  width: "150px",
                  marginTop: "10px",
                  marginBottom: "20px",
                }}
                onClick={() => {
                  navigate("/mypage");
                }}
              >
                <LogoutIcon /> My Page
              </Button>
              <Button
                color="error"
                variant="contained"
                fullWidth
                type="submit"
                sx={{
                  fontSize: "10px",
                  fontWeight: "bold",
                  color: "white",
                  width: "100px",
                  marginLeft:"10px",
                  marginTop: "10px",
                  marginBottom: "20px",
                }}
                onClick={async () => {
                  await logout();
                  localStorage.removeItem("Authorization");
                  localStorage.removeItem("refresh-token");
                  localStorage.removeItem("userId");
                  navigate("/main");
                }}
              >
                <LogoutIcon /> 로그아웃
              </Button>
            </div>
          ) : (
            <div>
              <Button
                color="primary"
                variant="contained"
                fullWidth
                type="submit"
                sx={{
                  width: "150px",
                  marginRight: "30px",
                  marginBottom: "20px",
                }}
                onClick={() => {
                  navigate("/auth");
                }}
              >
                로그인
              </Button>
            </div>
          )}
        </div>
          <Divider sx={{ borderWidth: "2px", color: "#0066cc" }} />
        <div>
          <Divider sx={{ borderWidth: "2px", color: "#0066cc" }} />
        </div>
        {isLoggedIn && <SubReportList key={userId} userInfo={userInfo} />}
        <Divider sx={{ marginTop: "5%", borderWidth: "2px", color: "#0066cc" }} />
        <div>
          {isLoggedIn && (
            <Button
              style={{ fontWeight: "bold" }}
              onClick={() => navigate("/mypage")}
            >
              MyPage에서 구독내용을 확인하세요
            </Button>
          )}
        </div>
      </Box>
    </div>
  );
};

export default PersonalDrawer;
