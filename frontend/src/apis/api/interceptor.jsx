import axios from 'axios';

// const API_URL = 'http://localhost:8080/api/v1';
const API_URL = "http://j8c206.p.ssafy.io/api/v1";

const authInstance = axios.create({
  baseURL: API_URL,
});

// Add request interceptor to add Authorization header with token
authInstance.interceptors.request.use((config) => {
  const token = localStorage.getItem('Authorization');
  if (token) {
    if (token.startsWith('Bearer ')) {
      config.headers.Authorization = token;
    } else {
      config.headers.Authorization = `Bearer ${token}`;
    }
  }
  return config;
}, (error) => {
  return Promise.reject(error);
});

// Add response interceptor to handle token expiration
authInstance.interceptors.response.use((response) => {
  return response;
}, async (error) => {
  const originalRequest = error.config;
  const refreshToken = localStorage.getItem('refresh_token');

  if (error.response && error.response.status === 499 && refreshToken) {
    originalRequest._retry = true;

    try {
      const response = await axios.get(`${API_URL}/users/reissue`, {
        headers: {
          Authorization: refreshToken,
        },
      });
      const { accessToken } = response.data.data;
      console.log('reissue');
      console.log(response);
      localStorage.setItem('Authorization', accessToken);

      if (accessToken.startsWith('Bearer ')) {
        originalRequest.headers.Authorization = accessToken;
      } else {
        originalRequest.headers.Authorization = `Bearer ${accessToken}`;
      }

      return authInstance(originalRequest);
    } catch (err) {
      console.error(err);
      console.error('토큰 재발급 요청 실패');
      localStorage.clear();
      window.location.replace('/auth');
    }
  }

  return Promise.reject(error);
});

export default authInstance;
