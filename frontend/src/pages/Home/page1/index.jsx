import React from "react"
import "./style.scss"
import DoubleArrowOutlinedIcon from '@mui/icons-material/DoubleArrowOutlined';

const Page1 = () =>{
  return(
    <div className="page-container">
      <div className="box1">
        <p style={{ "font-family": 'Jua, sans-serif', fontSize:"45px", fontWeight: 'bold', color:'#0066cc'}}>
          뉴스 트렌드 분석 및 시각화!
        </p>
        <h5 style={{ "font-family": 'Jua, sans-serif', fontSize:"30px" }}>
          <DoubleArrowOutlinedIcon /> 많은 관심을 받는 최신 트렌드에는 어떤 것이 있을까요?
        </h5>
        <h5 style={{ "font-family": 'Jua, sans-serif', fontSize:"30px" }}>
          <DoubleArrowOutlinedIcon /> T:LENS에서는 지역별, 분야별, 기업별 트렌드를 분석하여, 여러분이 원하시는 최신 트렌드를 얻을 수 있습니다.
        </h5>
      </div>
      <div className="box2">
        <img className="page1-image" src="img/visualization.png" art=""/>
      </div>
    </div>
  )
}
export default Page1
