import React from "react"
import "./style.scss"
import DoubleArrowOutlinedIcon from '@mui/icons-material/DoubleArrowOutlined';

const Page2= () =>{
  return(
    <div className="page-container">
      <div className="box1">
        <img className="page2-image" src="img/reporters.png" style={{width: "35vw", height: "100%", marginRight: "50px"}} alt="image1" />
      </div>
      <div className="box1">
        <p style={{ "font-family": 'Jua, sans-serif', fontSize:"45px", fontWeight: 'bold', color:'#0066cc'}}>
          기사 작성 패턴 분석!
        </p>
        <h5 style={{ "font-family": 'Jua, sans-serif', fontSize:"30px" }}>
          <DoubleArrowOutlinedIcon /> 악의적 의도를 가진 기사, 제목낚시로 뉴스 보기가 싫어진 적이 있지 않으신가요?  
        </h5>
        <h5 style={{ "font-family": 'Jua, sans-serif', fontSize:"30px" }}>
          <DoubleArrowOutlinedIcon /> T:LENS에서는 언론사별, 기자별 페이지를 마련하여, 자신의 스타일에 맞는 기사를 챙겨보실 수 있습니다.
        </h5>
        <h5 style={{ "font-family": 'Jua, sans-serif', fontSize:"30px" }}>
          <DoubleArrowOutlinedIcon /> 지금 T:LENS의 기자 구독, 기사 스크랩 기능을 이용해보세요.
        </h5>
      </div>
    </div>
  )
}
export default Page2
