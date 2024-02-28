import "./App.css";
import React, {useEffect} from 'react';
import {BrowserRouter, Routes, Route, useNavigate} from 'react-router-dom';
import LoginPage from "./components/LoginPage/LoginPage";
import Home from "./components/HomePage/Home";
import {Provider, useDispatch, useSelector} from "react-redux";
import { StyledEngineProvider } from '@mui/material/styles';
import {getUserProfile} from "./store/Auth/Action";

function App(props) {

    const { dialogsPage, loginPage, feedPage} = props.state;
    const jwt = localStorage.getItem('jwt');
    const {auth} = useSelector(store => store)
    const dispatch = useDispatch();
    const navigate = useNavigate();

    useEffect(() => {
        if (auth.jwt) {
           dispatch(getUserProfile(jwt))
            navigate('/')
        }
    },[auth.jwt, dispatch, jwt])
    return (
        <div className="App">

                    <StyledEngineProvider injectFirst>
                    <Routes>
                        <Route path='/*' element={auth.user? <Home
                                dialogsState={dialogsPage}
                                feedState={feedPage}
                                addPost={props.addPost}
                                addMessage={props.addMessage}
                                updateMessageHandler={props.updateMessageHandler}
                            />
                            :
                            <LoginPage loginState={loginPage}
                               addUser={props.addUser}/>
                        }/>
                    </Routes>
                    </StyledEngineProvider>
        </div>
    );
}

export default App;