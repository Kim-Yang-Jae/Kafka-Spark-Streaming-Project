import axios from "axios";
import { toast } from "react-toastify";
import authInstance from "../api/interceptor";

// const BASE_URL = "http://localhost:8080/api/v1";
const BASE_URL = "https://j8c206.p.ssafy.io/api/v1";

// 단순 get요청으로 인증값이 필요없는 경우
const axiosApi = (url, options) => {
  const instance = axios.create({ baseURL: url, ...options });
  return instance;
};

// Post, Put, Delete 등 요청으로 인증값이 필요한 경우
const axiosAuthApi = (url, token, options) => {
  // console.log("확인", token);
  const instance = axios.create({
    baseURL: url,
    headers: {
      Authorization: token,
    },
    ...options,
  });
  return instance;
};

// axios 인스턴스를 내보낸다.
export const defaultInstance = axiosApi(BASE_URL);

// 회원가입
const Register = async (values) => {
  const { email, nickname, password, gender, birthday, membership, age } = values;
  const signupData = {
    age: age,
    email: email,
    nickname: nickname,
    password: password,
    gender: gender,
    birthday: birthday,
    membership: membership,
  };

    try {
      const response = await defaultInstance.post("/users", signupData);
      console.log(response)
      if (response.data.code === 200) {
        toast.success(
          <h3>
            회원가입이 완료되었습니다.
            <br/>
            로그인 하세요😎
          </h3>,
          {
            position: "top-center",
            autoClose: 2000,
            onClose: () => {
              window.location.reload();
            }
          }
        );
      } else {
        toast.error(
          <h3>이미 있는 회원입니다😭.</h3>,
          {
            position: "top-center",
            autoClose: 2000,
          }
        );
      }
    } catch (e) {
      // 서버에서 받은 에러 메시지 출력
      toast.error(e.response.data.message + "😭", {
        position: "top-center",
      });
    }
  };


// 로그인
export const login = async (values) => {
  const { email, password } = values;

  const loginData = {
    email: email,
    password: password,
  };

  try {
    const response = await defaultInstance.post(`users/login`, loginData);
    localStorage.setItem("Authorization", response.headers.atk);
    localStorage.setItem("refresh-token", response.headers.rtk);
    localStorage.setItem("userId", response.data.content.userId);

    toast.success(<h3>로그인 성공😎</h3>, {
      position: "top-center",
      autoClose: 2000,
    });
  } catch (error) {
    console.log(error);
    toast.error(<h3>로그인정보를 확인해주세요😭</h3>, {
      position: "top-center",
      autoClose: 2000,
    });
  }
};





// 로그아웃
export const logout = async () => {
  try {
    const token = localStorage.getItem("Authorization");
    const authAxios = axiosAuthApi(BASE_URL, token);
    await authAxios.get("/users/logout");
    localStorage.removeItem("Authorization");
    localStorage.removeItem("refresh-token");
    localStorage.removeItem("userId");
    toast.success(<h3>로그아웃 성공👋</h3>, {
      position: "top-center",
      autoClose: 2000,
    });
  } catch (error) {
    console.log(error);
  }
};


// 비밀번호 확인
export const passwordCheck = async (password) => {
  try {
    const token = localStorage.getItem("Authorization");
    const authInstance = axiosAuthApi(BASE_URL, token);
    const response = await authInstance.get(`/users/${password}`, {
      params: { rawPwd: password },
    });
    console.log(response)
    
    return response
  } catch (error) {
    console.log(error);
    return false;
  }
};


// 비밀번호 변경
export const passwordChange = async (password) => {
  try {
    const token = localStorage.getItem("Authorization");
    const authInstance = axiosAuthApi(BASE_URL, token);
    const response = await authInstance.put(`/users/${password}`, [], {
      params: { rawPwd: password },
    });
    toast.success(<h3>비밀번호가 변경되었습니다.😎</h3>, {
      position: "top-center",
      autoClose: 2000,
    });
    return response
  } catch (error) {
    console.log(error);
  }
  
};


// 회원 탈퇴

export const withdrawalUser = async () => {
  try {
    const token = localStorage.getItem("Authorization");
    const authInstance = axiosAuthApi(BASE_URL, token);
    const response = await authInstance.delete("/users");
    console.log(response)
  }
   catch (error) {
    console.log(error);
  }
};
