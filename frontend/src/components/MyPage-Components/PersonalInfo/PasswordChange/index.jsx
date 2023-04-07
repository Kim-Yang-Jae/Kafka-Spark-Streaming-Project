import { useState } from "react";
import { Modal, Box, Typography, TextField, Button } from "@mui/material";
import './passwordChange.scss'
import { ToastContainer } from "react-toastify";
// API
import { passwordCheck, passwordChange } from "../../../../apis/users";

const PasswordChangeModal = ({ onClose, userInfo }) => {
  const [currentPassword, setCurrentPassword] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [isAuthed, setIsAuthed] = useState(false);
  const [errorMessage, setErrorMessage] = useState(null);

  const handlePasswordChange = (e) => {
    setPassword(e.target.value);
  };

  const handleCurrentPasswordChange = (event) => {
    setCurrentPassword(event.target.value);
  };

  const handleConfirmPasswordChange = (event) => {
    setConfirmPassword(event.target.value);
  };

  const handleAuthSubmit = async (event) => {
    event.preventDefault();
    try {
      const response = await passwordCheck(currentPassword);
      if (response.data.code === 200) {
        setIsAuthed(true);
      } else {
        setErrorMessage("비밀번호 인증에 실패했습니다.");
      }
    } catch (error) {
      setErrorMessage("비밀번호 인증에 실패했습니다.");
    }
  };

  const handleFormSubmit = async (event) => {
    event.preventDefault();
    if (password !== confirmPassword) {
      setErrorMessage("새 비밀번호와 비밀번호 확인이 일치하지 않습니다.");
      return;
    }
    try {
      const response = await passwordChange(password);
      if (response.data.code === 200) {
        onClose();
      } else {
        setErrorMessage("비밀번호 변경에 실패했습니다.");
      }
    } catch (error) {
      setErrorMessage("비밀번호 변경에 실패했습니다.");
    }
  };
  
  return (
    <Modal open={true} onClose={onClose}>
      <Box
        sx={{
          position: "absolute",
          top: "50%",
          left: "50%",
          transform: "translate(-50%, -50%)",
          bgcolor: "background.paper",
          boxShadow: 24,
          p: 4,
          width: '30vw',
        }}
      >
        <div className="password-verification" >
          <Typography className="password-text" style={{ fontWeight: 'bold', textAlign: 'center',  fontSize: '20px' }}>비밀번호 입력하기</Typography>
        </div>
        {!isAuthed ? (
          <form onSubmit={handleAuthSubmit} className="password-form">
            <Typography className="password-text" style={{ fontWeight: 'bold', marginBottom: '5px' }}>사용중이신 비밀번호를 입력해주십시오.</Typography>
            <TextField
              className="password-fields"
              type="password"
              label="현재 비밀번호"
              value={currentPassword}
              onChange={handleCurrentPasswordChange}
              fullWidth
              required
              InputProps={{
                classes: { root: "blue-outline" },
                style: {
                  borderRadius: '50px',
                  justifyContent: 'center',
                  width: '100%',
                  height: '45px',
                }
              }}
            />
            <div className="auth-button">
              <Button className="button" type="submit" variant="contained" color="primary" sx={{ mt: 2, justifyContent: 'center', width: '160px'}}>
                인증하기
              </Button>
            </div>
          </form>
        ) : (
          <form onSubmit={handleFormSubmit} className="password-form">
            <ToastContainer />
            <Typography className="password-text" style={{ fontWeight: 'bold', marginBottom: '5px' }}>새 비밀번호를 입력해주세요.</Typography>
            <TextField
              className="password-fields"
              type="password"
              label="새 비밀번호"
              value={password}
              onChange={handlePasswordChange}
              fullWidth
              required
              InputProps={{
                classes: { root: "blue-outline" },
                style: {
                  borderRadius: '50px',
                  justifyContent: 'center',
                  width: '100%',
                  height: '45px',
                }
              }}
            />
            <TextField
              className="password-fields"
              type="password"
              label="새 비밀번호 확인"
              value={confirmPassword}
              onChange={handleConfirmPasswordChange}
              fullWidth
              required
              InputProps={{
                classes: { root: "blue-outline" },
                style: {
                  borderRadius: '50px',
                  justifyContent: 'center',
                  width: '100%',
                  height: '45px',
                }
              }}
            />
            <div className="auth-button">
              <Button className="button" type="submit" variant="contained" color="primary" sx={{ mt: 2, justifyContent: 'center', width: '160px'}}>
                변경하기
              </Button>
            </div>
          </form>
        )}
        {errorMessage && (
          <Typography className="password-text" style={{ fontWeight: 'bold', marginTop: '10px', color: 'red' }}>
            {errorMessage}
          </Typography>
        )}
      </Box>
    </Modal>
  );
}

export default PasswordChangeModal