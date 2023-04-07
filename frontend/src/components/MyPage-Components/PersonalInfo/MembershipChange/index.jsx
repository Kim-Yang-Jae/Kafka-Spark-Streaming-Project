import React, {useState} from "react";
import { Modal, Box, Typography, Button } from "@mui/material";
import "./membershipChange.scss"
const MemebershipChange = ({ onClose }) => {
  const [isMembership, setIsMembership] = useState(false);

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
          width: "30vw",
        }}
      >
        {isMembership ? (
          <form>
            <div className="membership-title" >
              <Typography className="membership-text" style={{ fontWeight: 'bold', textAlign: 'center', fontSize: '20px'}}>맴버쉽 가입하기</Typography>
            </div>
            <Typography className="password-text" style={{ fontWeight: 'bold', marginBottom: '5px', marginTop: '5px' }}>
              T:LENS 맴버쉽을 가입하시면 T:LENS에서 준비한 키워드 뉴스레터 등 저희의 서비스를 모두 받아 보실 수 있습니다.
            </Typography>
            <div membership-button>
              <Button type="submit" variant="contained" color="primary" sx={{ mt: 2 }}>
                키워드 뉴스 받아보기
              </Button>
            </div>
          </form>
        ) : (
          <form>
            <div className="membership-title" >
              <Typography className="membership-text" style={{ fontWeight: 'bold', textAlign: 'center', fontSize: '20px' }}>맴버쉽 탈퇴하기</Typography>
            </div>
              <Typography className="password-text" style={{ fontWeight: 'bold', marginBottom: '5px', marginTop: '5px' }}>
                - 맴버쉽을 탈퇴하시더라도 저희가 준비한 T:LENS의 트렌드 분석 서비스는 이용하실 수 있습니다.
              </Typography>
              <Typography className="password-text" style={{ fontWeight: 'bold', marginBottom: '5px', marginTop: '5px' }}>
                - T:LENS 제작팀은 여러분을 만족시킬 수 있는 맴버쉽을 제공할 수 있도록 더욱 노력하도록 하겠습니다.
              </Typography>
            <div membership-button>
              <Button type="submit" variant="contained" color="primary" sx={{ mt: 2 }}>
                맴버쉽 탈퇴하기
              </Button>
            </div>
          </form>
        )}
      </Box>
    </Modal>
  )
}

export default MemebershipChange