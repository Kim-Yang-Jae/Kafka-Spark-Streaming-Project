import React, {useState} from "react";
import { Modal, Box, Typography, TextField, Button } from "@mui/material";
import { useNavigate } from "react-router-dom";
import './quitMember.scss'

// API
import { withdrawalUser, passwordCheck } from "../../../../apis/users";

const QuitMember = ({ onClose }) => {
  const navigate = useNavigate()
  const [currentPassword, setCurrentPassword] = useState("");
  const [isAuthed, setIsAuthed] = useState(false);
  const [inputText, setInputText] = useState('');
  const [errorMessage, setErrorMessage] = useState(null);


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
  }
  const handleCurrentPasswordChange = (event) => {
    setCurrentPassword(event.target.value);
  }
  

  const handleInputChange = (e) => {
    setInputText(e.target.value);
  }

  const handleFormSubmit = async (e) => {
    e.preventDefault();
    if (inputText === '저는 다시 한번 트렌즈의 서비스를 이용하겠습니다') {
  
      await withdrawalUser();
      localStorage.removeItem("Authorization");
      localStorage.removeItem("refresh-token");
      localStorage.removeItem("userId");
      alert('탈퇴되었습니다.');
      navigate('/main')
      onClose(); // 모달 닫기
    } else {
      alert('올바른 문구를 입력해주세요.');
    }
    setInputText('');
  }

  return(
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
        <div className="quit-verification" >
          <Typography className="quit-text" style={{ fontWeight: 'bold', textAlign: 'center',  fontSize: '20px' }}>회원 탈퇴하기</Typography>
        </div>
        {!isAuthed ? (
          <form onSubmit={handleAuthSubmit} className="password-form">
            <Typography className="password-text" style={{ fontWeight: 'bold', marginBottom: '5px', marginTop: '5px' }}>
              회원탈퇴를 위해서 비밀번호를 인증해주십시오.
            </Typography>
            <TextField
              className="password-fields"
              type="password"
              label="현재 비밀번호"
              value={currentPassword}
              onChange={handleCurrentPasswordChange}
              fullWidth
              required
              InputProps={{
                style: {
                  borderRadius: '50px',
                  justifyContent: 'center',
                  height: '50px',
                }
              }}
            />
            <div className="auth-button">
              <Button onClick={handleAuthSubmit} className="button" type="submit" variant="contained" color="primary" sx={{ mt: 5, justifyContent: 'center', width: '160px'}}>
                인증하기
              </Button>
            </div>
          </form>
        ) : (
          <form onSubmit={handleFormSubmit}>
            <Typography className="password-text" style={{ fontWeight: 'bold', marginBottom: '5px', marginTop: '5px' }}>
              매크로 방지를 위해서 아래 문구를 입력해주세요
            </Typography>
            <Typography className="password-text" style={{ fontWeight: 'bold', marginBottom: '5px', marginTop: '5px', color: '#0066cc' }}>
              저는 다시 한번 트렌즈의 서비스를 이용하겠습니다
            </Typography>
            <TextField
              placeholder="저는 다시 한번 트렌즈의 서비스를 이용하겠습니다"
              type="text" 
              value={inputText} 
              onChange={handleInputChange}
              fullWidth
              InputProps={{
                classes: { root: "blue-outline" },
                style: {
                  borderRadius: '50px',
                  justifyContent: 'center',
                  height: '50px',
                }
              }}
            />
            <div className="auth-button">
              <Button onClick={handleFormSubmit} className="button" type="submit" variant="contained" color="primary" sx={{ mt: 5, justifyContent: 'center', width: '160px'}}>
                탈퇴하기
              </Button>
            </div>
          </form>
        )}
      </Box>
    </Modal>
  )
}

export default QuitMember