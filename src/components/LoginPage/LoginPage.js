import React, {Component, useState} from "react";
import Grid from "@mui/material/Grid";
import inst_image from "../../images/9364675fb26a.svg";
import go from "../../images/google.svg";
import "./LoginPage.css";
import SignIn from "./SignIn/SignIn";
import SignUp from "./SignUp/SignUp";

const LoginPage = (props) => {

  const [isLogin, setLoginState] = useState(true);
 const changeLoginState = () => {
    if (isLogin) setLoginState(false);
    else setLoginState(true);
  };

    return (
      <Grid container>
        <Grid item xs={3}></Grid>
        <Grid item xs={6}>
          <div className="login-page__main">
            <div>
              <img src={inst_image} width="454px" alt="" />
            </div>
            <div>
              <div className="login-page__right-component">
                <span className="login-page__logo">ВСПЫШКА</span>
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
        <Grid item xs={3}></Grid>
      </Grid>
    );
  }


export default LoginPage;
