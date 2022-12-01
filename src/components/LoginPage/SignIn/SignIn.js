import React,
    {
    Component
}

from "react";
import "./SignIn.css";



class SignIn extends Component {
    constructor(props) {
        super(props);

        this.state= {}

        ;
    }

    render() {
        return (<div> <input className="login-page__sign-in__text"
            type="text"
            placeholder="Номер, бо почта, бо имя пользователя"
            /> <input className="login-page__sign-in__text"
            type="password"
            placeholder="Вводи пароль"
            /> <button className="login-page__sign-in__button">Входи</button> </div>);
    }
}

export default SignIn;