import React, { Component } from "react";
import "./SignUp.css";

class SignUp extends Component {
  constructor(props) {
    super(props);

    this.state = {};
  }

  render() {
    return (
      <div>
        {" "}
        <input
          className="signup-page__sign-in__text"
          type="text"
          placeholder="Номер телефона, бо почта"
        />
        <input
          className="signup-page__sign-in__text"
          type="text"
          placeholder="ФИО"
        />
        <input
          className="signup-page__sign-in__text"
          type="text"
          placeholder="Псевдоним"
        />
        <input
          className="signup-page__sign-in__text"
          type="password"
          placeholder="Пароль"
        />
        <button className="login-page__sign-in__button">Регистрируюсь</button>{" "}
      </div>
    );
  }
}

export default SignUp;
