import React from "react";
import { Navigate } from "react-router-dom";
import {jwtUtils} from "../utils/jwtUtils"; // 토큰 관련 유틸리티

const PrivateRoute = ({ component: RouteComponent, ...rest }) => {
  const token = localStorage.getItem("Authorization");

  if (!jwtUtils.isAuth(token)) {
    // 로그인되어 있지 않으면 로그인 페이지로 리디렉션
    return <Navigate to="/auth" />;
  }

  // 로그인되어 있으면 보호된 경로에 접근
  return <RouteComponent {...rest} />;
};

export default PrivateRoute;