import { useState } from "react";
import { Button, Divider, Typography } from "@mui/material";
import "./personalInfo.scss";

import PasswordChangeModal from "./PasswordChange";
import MemebershipChange from "./MembershipChange";
import QuitMember from "./QuitMember";

const PersonalInfo = ({ userInfo }) => {
  // 비밀번호 변경 모달
  const [passwordModalOpen, setPassModalOpen] = useState(false);
  const ChangeButtonClick = () => {
    setPassModalOpen(true);
  };
  const ChangeCloseModal = () => {
    setPassModalOpen(false);
  };

  // 맴버쉽 상태 변경 모달
  const [memberModalOpen, setMemberModalOpen] = useState(false);
  const MemberButtonClick = () => {
    setMemberModalOpen(true);
  };

  const MemberCloseModal = () => {
    setMemberModalOpen(false);
  };

  // 회원 탈퇴 모달
  const [quitModalOpen, setquitModalOpen] = useState(false);
  const QuitButtonClick = () => {
    setquitModalOpen(true);
  };

  const QuitCloseModal = () => {
    setquitModalOpen(false);
  };

  // 맴버쉽 가입 현황
  const MembershipStatus = () => {
    const membershipText =
      userInfo.membership === 1 ? "가입 중" : "가입 안함";
    return <Typography variant="h4">- 현재 맴버쉽 가입 상태 : {membershipText}</Typography>;
  };


  return(
    <div className="personal-info"  style={{ "font-family": 'Jua, sans-serif' }} >
      <div className="basic-Info">
        <h2>안녕하세요! {userInfo.nickname} 님.</h2>
        <h2>이메일 : {userInfo.email}</h2>
      </div>
      <Divider />
      <div className="personal-change">
        <h2>비밀번호 변경하기</h2>
        <h4>- 비밀번호는 3개월마다 변경하시는 것을 추천합니다.</h4>
        <h4>- 비밀번호 변경 시, 영어(대소문자)와 특수문자를 포함하여 8자 이상을 적어주셔야 합니다.</h4>
        <Button variant="contained" color="primary" sx={{width:'180px'}} onClick={ChangeButtonClick}>
          비밀번호 변경하기
        </Button>
        {passwordModalOpen && <PasswordChangeModal onClose={ChangeCloseModal} />}
      </div>
      <Divider />
      <div className="personal-change">
        <h2>맴버쉽 내용 변경하기</h2>
        <h4>- 현재 맴버쉽 가입 상태 : {MembershipStatus} </h4>
        <h4>- T:LENS Friends에 가입하시면 T:LENS에서 준비한 뉴스레터를 받아보실 수 있습니다.</h4>
        <Button variant="contained" color="primary" sx={{width:'180px'}} onClick={MemberButtonClick} >
          맴버쉽 상태 변경
        </Button>
        {memberModalOpen && <MemebershipChange onClose={MemberCloseModal} />}
      </div>
      <Divider />
      <div className="personal-change">
        <h2>회원 탈퇴하기</h2>
        <h4>- 회원 탈퇴를 하시더라도 T:LENS 맴버쉽의 다음 뉴스레터 서비스가 전달될 수 도 있습니다</h4>
        <h4>- 2번 이상의 뉴스레터가 전달 되었을 경우, T:LENS의 고객 상담 센터에 문의해주시길 바랍니다.</h4>
        <Button variant="contained" color="primary" sx={{width:'180px'}} onClick={QuitButtonClick} >
          회원 탈퇴하기
        </Button>
        {quitModalOpen && <QuitMember onClose={QuitCloseModal} />}
      </div>

    </div>
  )
}

export default PersonalInfo