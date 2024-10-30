import React, {useState} from "react";
import Grid from "@mui/material/Grid";
import inst_image from "../../images/logo.jpg";
import go from "../../images/google.svg";
import "./LoginPage.css";
import SignIn from "./SignIn/SignIn";
import SignUp from "./SignUp/SignUp";
import {Avatar, Box} from "@mui/material";

const LoginPage = (props) => {

  const [isLogin, setLoginState] = useState(false);
 const changeLoginState = () => {
    if (isLogin) setLoginState(false);
    else setLoginState(true);
  };


    return (
      <Grid container>
        <Grid item xs={2}></Grid>
        <Grid item xs={8} className={'flex-grow'}>
          <div className="login-page__main h-screen w-full flex justify-around items-center">
            <div>
                {isLogin?
                <Box className={'h-full flex justify-center items-center'}>
                    <Box className={'p-3 rounded-full ring-2 ring-[rgb(22, 136, 250)] dark:ring-gray-500 flex justify-center items-center'}>
                        <Avatar
                            className={'ring-2 ring-[rgb(159, 205, 252)] dark:ring-gray-500'}
                            sx={{width: '15rem', height: '15rem',}}/>
                    </Box>
                </Box>
                    :
                    <Box className={'h-[264px] w-[264px] rounded-full ring-2 ring-[rgb(30, 73, 118)] dark:ring-gray-500 flex justify-center items-center overflow-hidden'}>
                        <img className={'rounded-full ring-1 ring-[rgb(30, 73, 118)] dark:ring-gray-500'} src={inst_image} width="454px" alt="" />
                    </Box>
                }
            </div>
            <div className={' bg-transparent dark:from-gray-800 dark:to-transparent'}>
              <div className="login-page__right-component">
                <span className="login-page__logo">ПУЛЬС</span>
                <div className="login-page__sign-in">
                  {isLogin ? <SignIn /> : <SignUp addUser={props.addUser}/>}


                  <div className="login-page__ordiv">
                    <div className="login-page__divider"></div>
                    <div className="login-page__divider-text">ИЛИ</div>
                    <div className="login-page__divider"></div>
                  </div>
                  <div className="login-page__google">
                    <button className="google__google-btn" type="button">
                      <img src={go} alt="" /> Продолжить с Google
                    </button>
                  </div>
                  <div className="login-page__forgot-password">
                    Забыл пароль?
                  </div>
                </div>
              </div>

              <div className="login-page__signup-option">
                {isLogin ? (
                  <div className="login-page__sign-in-prop">
                    <span>
                      Ты ещё не с нами?{" "}
                      <button onClick={changeLoginState}>
                        Регистрируйся!
                      </button>
                    </span>
                  </div>
                ) : (
                  <div className="login-page__sign-up-prop">
                    Есть регистрация?{" "}
                    <button onClick={changeLoginState}>Зайди.</button>
                  </div>
                )}
              </div>
            </div>
          </div>
        </Grid>
        <Grid item xs={2}></Grid>
      </Grid>
    );
  }


export default LoginPage;
