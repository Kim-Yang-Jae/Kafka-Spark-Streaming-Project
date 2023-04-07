import React, {useState} from 'react'
import {Formik} from "formik";
import { toast, ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import * as Yup from "yup";
import { useNavigate } from 'react-router-dom';

import {Button, TextField, FormControl, MenuItem, Select, Typography, FormControlLabel, Checkbox} from "@mui/material";
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import "./signUp.scss"
import axios from 'axios';

const SignUp = () => {
  
  const navigate = useNavigate()

  const submit = async (values) => {
    const { email, nickname, password, gender, birthday, membership, age } = values;

    //signup data

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
        const response = await axios.post("https://j8c206.p.ssafy.io/api/v1/users", signupData);
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



  const validationSchema = Yup.object().shape({
    email: Yup.string()
      .email("올바른 이메일 형식이 아닙니다!")
      .required("이메일을 입력하세요!"),
    nickname: Yup.string()
      .min(2, "닉네임은 최소 2글자 이상입니다!")
      .max(10, "닉네임은 최대 10글자입니다!")
      .matches(
        /^[가-힣a-zA-Z][^!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?\s]*$/,
        "닉네임에 특수문자가 포함되면 안되고 숫자로 시작하면 안됩니다!"
      )
      .required("닉네임을 입력하세요!"),
    password: Yup.string()
      .min(8, "비밀번호는 최소 8자리 이상입니다")
      .max(20, "비밀번호는 최대 20자리입니다!")
      .required("패스워드를 입력하세요!")
      .matches(
        /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?])[^\s]*$/,
        "알파벳, 숫자, 공백을 제외한 특수문자를 모두 포함해야 합니다!"
      ),
    password2: Yup.string()
      .oneOf([Yup.ref("password"), null], "비밀번호가 일치하지 않습니다!")
      .required("필수 입력 값입니다!"),
    gender: Yup.string()
      .required("필수 입력 값입니다!"),
  });


  return (
    <Formik
      initialValues={{
        email: "",
        nickname: "",
        password: "",
        password2: "",
        gender:"",
        birthday:null,
        age:null,
        membership: null,
      }}
      validationSchema={validationSchema}
      
      // 확인용
      // onSubmit={(values) => {
      //   console.log(values);
      // }}
      
      onSubmit={submit}
      validateOnMount={true}
    >
    {({values, handleSubmit, handleChange, errors, setFieldValue}) => (
      
    <div className="signup-wrapper">
      <ToastContainer/>
      <div className="container">
        <form 
        onSubmit={handleSubmit} autoComplete="off"
        >
          <div className="input-forms">
            <div className="input-forms-item">
              <div className="input-label">
                이메일 : 
              </div>
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
              {errors.email}
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
                  {errors.password}
                </div>
              <div className="input-forms-item">
                <div className="input-label">비밀번호 확인 : </div>
                <TextField
                  value={values.password2}
                  name="password2"
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
                  {errors.password2}
                </div>

              <div className="input-forms-item">
                <div className="input-label">닉네임 : </div>
                <TextField
                  value={values.nickname}
                  name="nickname"
                  variant="outlined"
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
                  {errors.nickname}
                </div>

              <div className="input-combo">
                <div className="input-label">성별 : </div>
                <FormControl>
                  <Select
                    value={values.gender}
                    name="gender"
                    label="성별"
                    variant="outlined"
                    onChange={handleChange}
                    style={{ height: '35px', width: '100px', marginLeft: '35px' }}
                  >
                    <MenuItem value={"male"}>남성</MenuItem>
                    <MenuItem value={"female"}>여성</MenuItem>
                  </Select>
                </FormControl>
              </div>
                <div className="error-message">{errors.gender}</div>

                <div className="input-forms-item">
                  <div className="input-label">생년월일 : </div>
                    <div className='input-datepicker'>
                      <DatePicker
                        selected={values.birthday}
                        onChange={(date) => {
                          setFieldValue('birthday', date);
                          const ageDiffMs = Date.now() - date.getTime();
                          const ageDate = new Date(ageDiffMs);
                          const age = Math.abs(ageDate.getUTCFullYear() - 1970);
                          setFieldValue('age', age);
                        }}
                        dateFormat="yyyy-MM-dd"
                        placeholderText="YYYY-MM-DD"
                      />
                    </div>
                  </div>
                  
                  <div className="membership-check" style={{ display: "flex", alignItems: "center", marginTop: "10px" }}>
                    <div className="input-label">
                      <Typography style={{ fontWeight: "bold", marginLeft: "5px", fontSize: "15px" }}>T:LENS 맴버쉽에 가입하시겠습니까?</Typography>
                    </div>
                      <Checkbox
                        name="membership" 
                        value={values.membership} 
                        onChange={handleChange} 
                        id="membership-checkbox"
                      />
                    </div>

              <div className='submit-button'>
                <Button
                  color="primary"
                  variant="contained"
                  type="submit"
                  style={{width:'250px'}}
                >
                  회원가입
                </Button>
              </div>
            </div>
          </form>
        </div>
      </div>
      )}
    </Formik>    
  )
}
export default SignUp
