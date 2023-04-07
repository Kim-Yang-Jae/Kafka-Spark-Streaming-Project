import { Divider } from "@mui/material"
import "./mySubscribe.scss"
import KeywordList from "./KyewordList"
import SubJournalist from "./Sub-Journalist"

const MySubscribe = ({userInfo}) => {
  return (
    <div className="mySubscribe" style={{ "font-family": 'Jua, sans-serif' }}>
      <div className="basic-Info">
        <h2>안녕하세요! {userInfo.nickname} 님.</h2>
        <Divider sx={{ borderBottomWidth: "3px" }} />
      </div>
      <div className="keyword-selector">
        <h2>"{userInfo.nickname}"의 T:LENS 키워드</h2>
        <KeywordList />
      </div>
      <Divider sx={{ borderBottomWidth: "3px" }} />
      <div className="subscribing-journalists">
        <SubJournalist userInfo = {userInfo}/>
      </div>

    </div>
  )
}
export default MySubscribe