import React from "react";
import { Navigate } from "react-router-dom";
import { jwtUtils } from "../utils/jwtUtils";

const PublicRoute = ({ component: RouteComponent, ...rest }) => {
  const token = localStorage.getItem("Authorization");


  if (jwtUtils.isAuth(token)) {
    // 이미 로그인된 사용자일 경우, 메인 페이지로 리다이렉트
    return <Navigate to="/main" />;
  }

  return <RouteComponent {...rest} />;
};

export default PublicRoute;
