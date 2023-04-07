import {Formik, ErrorMessage} from "formik";
import * as Yup from "yup";
import {Button, TextField, Divider} from "@mui/material";
import { toast, ToastContainer } from "react-toastify";
import { useNavigate } from "react-router-dom";
import {defaultInstance} from "../../../apis/api/axiosinstance"
import "./login.scss";



const Login = () => {
  const navigate = useNavigate()
  const validationSchema = Yup.object().shape({
    email: Yup.string()
      .email("올바른 이메일 형식이 아닙니다!")
      .required("이메일을 입력하세요!"),
    password: Yup.string()
      .required("패스워드를 입력하세요!")
  });

  const login = async (values) => {
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
      
      setTimeout(() => {
        navigate("/main");
      }, 2000);
      
    } catch (error) {
      console.log(error);
      toast.error(<h3>로그인정보를 확인해주세요😭</h3>, {
        position: "top-center",
        autoClose: 2000,
      });
    }
  };

  return (
    <Formik
      initialValues={{
        email: "",
        password: "",
      }}
      validationSchema={validationSchema}
      onSubmit={login}
      // 확인용
      // onSubmit={(values) => {
      //   console.log(values);
      // }}
    >
      {({values, handleSubmit, handleChange}) => (
        <div className="login-wrapper">
          <ToastContainer />
          <div >
            <form onSubmit={handleSubmit} autoComplete="off" className="container">
              <div className="input-forms">
                <img src="img/login.png" alt="로고"/>
                <Divider
                  sx={{
                    border: "2px solid #0066CC"
                  }}
                />
                <div className="input-forms-item">
                  <div className="input-label">이메일 : </div>
                  <TextField
                    className="input-text"
                    value={values.email}
                    name="email"
                    variant="outlined"
                    onChange={handleChange}
                    InputProps={{
                      style: {
                        borderRadius: '50px',
                        height:'35px',
                        borderBlockColor: "#0066cc",
                      }
                    }}
                  />
                </div>
                <div className="error-message">
                  <ErrorMessage name="email"/>
                </div>
                <div className="input-forms-item">
                  <div className="input-label">비밀번호 : </div>
                  <TextField
                      sx={{
                        borderRadius: '50px',
                      }}
                      value={values.password}
                      name="password"
                      variant="outlined"
                      type="password"
                      onChange={handleChange}
                      InputProps={{
                        style: {
                          borderRadius: '50px',
                          height:'35px',
                        }
                      }}
                    />
                </div>
                <div className="error-message">
                  <ErrorMessage name="password"/>
                </div>
                <Button
                  color="primary"
                  variant="contained"
                  fullWidth
                  type="submit"
                  sx={{
                    borderRadius: "50px",
                    width: "150px",
                    marginTop: '20px'
                  }}
                  // onClick={navigate("/main")}
                >
                  로그인
                </Button>
              </div>
            </form>
          </div> 
        </div>
      )}
    </Formik>
  );
};

export default Login;