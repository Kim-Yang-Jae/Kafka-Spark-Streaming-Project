import axios from "axios";
import { toast } from "react-toastify";

// const BASE_URL = "http://localhost:8080/api/v1";
const BASE_URL = "https://j8c206.p.ssafy.io/api/v1";

// 단순 get요청으로 인증값이 필요없는 경우
const axiosApi = (url, options) => {
  const instance = axios.create({ baseURL: url, ...options });
  return instance;
};

export const defaultInstance = axiosApi(BASE_URL);

// Post, Put, Delete 등 요청으로 인증값이 필요한 경우
const axiosAuthApi = (url, token) => {
  // console.log("확인", token);
  const instance = axios.create({
    baseURL: url,
    headers: {
      Authorization: token,
    },
  });
  return instance;
};

///////////////////////////////////////////////////////////////////////// 기자 구독

export const subReporter = async (reporterId) => {
  try {
    const token = localStorage.getItem("Authorization");
    const authInstance = axiosAuthApi(BASE_URL, token);
    await authInstance.post("/subscribe", [], {
      params: { reporterId: reporterId },
    });
    toast.success(<h3>기자 구독을 하셨습니다.😎</h3>, {
      position: "top-center",
      autoClose: 2000,
    });
  } catch (error) {
    console.log(error);
  }
};

export const cancelSub = async (id) => {
  try {
    const token = localStorage.getItem("Authorization");
    const authInstance = axiosAuthApi(BASE_URL, token);
    await authInstance.delete("/subscribe", {
      params: { reporterId: id },
    });
    toast.error(<h3>구독을 취소 하셨습니다.👋</h3>, {
      position: "top-center",
      autoClose: 2000,
    });
  } catch (error) {
    console.log(error);
  }
};

export const subStatus = async (id) => {
  try {
    const token = localStorage.getItem("Authorization");
    const authInstance = axiosAuthApi(BASE_URL, token);
    const response = await authInstance.get("/subscribe/status", {
      params: { reporterId: id },
    });
    return response.data;
  } catch (error) {
    console.log(error);
  }
};

export const getSubscribe = async () => {
  try {
    const token = localStorage.getItem("Authorization");
    const authInstance = axiosAuthApi(BASE_URL, token);
    const response = await authInstance.get("/subscribe/reporter");
    // console.log(response.data.content)
    return response.data.content;
  } catch (error) {
    console.log(error);
  }
};

export const getSubscribeNews = async () => {
  try {
    const token = localStorage.getItem("Authorization");
    const authInstance = axiosAuthApi(BASE_URL, token);
    const response = await authInstance.get("/subscribe/reporter/news");
    // console.log(response.data.content)
    return response.data.content;
  } catch (error) {
    console.log(error);
  }
};

///////////////////////////////////////////////////////////////////////// 기사 스크랩

export const scrapArticle = async (id) => {
  try {
    const token = localStorage.getItem("Authorization");
    const authInstance = axiosAuthApi(BASE_URL, token);
    await authInstance.post("/scrap", [], {
      params: { newsId: id },
    });
    toast.success(<h3>기사 스크랩이 되었습니다.😎</h3>, {
      position: "top-center",
      autoClose: 2000,
    });
  } catch (error) {
    console.log(error);
  }
};

export const cancelScrap = async (id) => {
  try {
    const token = localStorage.getItem("Authorization");
    const authInstance = axiosAuthApi(BASE_URL, token);
    await authInstance.delete("/scrap", {
      params: { newsId: id },
    });
    toast.error(<h3>스크랩을 취소 하셨습니다.👋</h3>, {
      position: "top-center",
      autoClose: 2000,
    });
  } catch (error) {
    console.log(error);
  }
};

export const scrapStatus = async (id) => {
  try {
    const token = localStorage.getItem("Authorization");
    const authInstance = axiosAuthApi(BASE_URL, token);
    const response = await authInstance.get("/scrap/status", {
      params: { newsId: id },
    });
    return response;
  } catch (error) {
    console.log(error);
  }
};

export const scrapList = async () => {
  try {
    const token = localStorage.getItem("Authorization");
    const authInstance = axiosAuthApi(BASE_URL, token);
    const response = await authInstance.get("/scrap/news");
    // console.log(response.data.content.lst)
    return response.data.content;
  } catch (error) {
    console.log(error);
  }
};

////////////////////////////////////////////////////////////// 키워드 등록하기
// 키워드 상태 보기
export const keywordStatus = async (keyword) => {
  try {
    const token = localStorage.getItem("Authorization");
    const authInstance = axiosAuthApi(BASE_URL, token);
    const response = await authInstance.get("/users/keyword/status", {
      params: { name: keyword },
    });
    return response.data.content;
  } catch (error) {
    console.log(error);
  }
};

// 키워드 등록하기
export const keywordRegister = async (keyword) => {
  try {
    const token = localStorage.getItem("Authorization");
    const authInstance = axiosAuthApi(BASE_URL, token);
    await authInstance.post("/users/keyword", [], {
      params: { keyword: keyword },
    });
    toast.success(<h3>키워드가 등록되었습니다.😎</h3>, {
      position: "top-center",
      autoClose: 2000,
    });
  } catch (error) {
    console.log(error);
  }
};

// 키워드 제거하기

export const deleteKeyword = async (keyword) => {
  try {
    const token = localStorage.getItem("Authorization");
    const authInstance = axiosAuthApi(BASE_URL, token);
    await authInstance.delete("/users/keyword", {
      params: { keyword: keyword },
    });
    toast.error(<h3>스크랩을 취소 하셨습니다.👋</h3>, {
      position: "top-center",
      autoClose: 2000,
    });
  } catch (error) {
    console.log(error);
  }
};

// 키워드 가져오기

export const subKeyword = async () => {
  try {
    const token = localStorage.getItem("Authorization");
    const authInstance = axiosAuthApi(BASE_URL, token);
    const response = await authInstance.get("/users/keyword");
    return response.data.content;
  } catch (error) {
    console.log(error);
  }
};
