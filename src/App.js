import "./App.css";
import React from 'react';
import {BrowserRouter, Routes, Route} from 'react-router-dom';
import LoginPage from "./components/LoginPage/LoginPage";
import Home from "./components/HomePage/Home";
import {updateMessageHandler} from "./redux/state";
import {Provider} from "react-redux";
import {store} from "./store/store";
import { StyledEngineProvider } from '@mui/material/styles';

function App(props) {
    const { dialogsPage, loginPage, feedPage, updateMessageHandler } = props.state;
    return (
        <div className="App">
            <BrowserRouter>
                <Provider store={store}>
                    <StyledEngineProvider injectFirst>
                    <Routes>
                        <Route path='/login' element={<LoginPage loginState={loginPage} addUser={props.addUser}/>}/>
                        <Route path={'/*'} element={<Home
                            dialogsState={dialogsPage}
                            feedState={feedPage}
                            addPost={props.addPost}
                            addMessage={props.addMessage}
                            updateMessageHandler={props.updateMessageHandler}
                        />}/>
                    </Routes>
                    </StyledEngineProvider>
                </Provider>
            </BrowserRouter>
        </div>
    );
}

export default App;