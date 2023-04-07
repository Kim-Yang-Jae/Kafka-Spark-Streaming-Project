import "./Footer.scss";
import * as React from "react";

const Footer = () => {
  return (
    <>
      <div className="footer-wrapper">
        <div className="footer-title">
          <span>T:LENS</span>
        </div>
        <div className="footer-contents">
          삼성 청년 SW아카데미 8기 광주_2반_C206
        </div>
        <div className="my-website">
          <div className="my-website-title">Our Projects</div>
          <a href="https://lab.ssafy.com/s08-bigdata-dist-sub2/S08P22C206">
            🏴GitLab
          </a>
          <br />
        </div>
        <br />
      </div>
    </>
  );
};
export default Footer;
