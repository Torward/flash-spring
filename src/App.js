import "./App.css";
import React from 'react';
import {BrowserRouter, Routes, Route} from 'react-router-dom';
import LoginPage from "./components/LoginPage/LoginPage";
import Home from "./components/HomePage/Home";
import {updateMessageHandler} from "./adapters/state";
import {Provider} from "react-redux";
import {store} from "./store/store";

function App(props) {
    return (
        <div className="App">
            <BrowserRouter>
                <Provider store={store}>
                    <Routes>
                        <Route path='/login' element={<LoginPage/>}/>
                        <Route path={'/*'} element={<Home
                            dialogsState={props.state.dialogsPage}
                            feedState={props.state.feedPage}
                            addPost={props.addPost}
                            addMessage={props.addMessage}
                            updateMessageHandler={props.updateMessageHandler}
                        />}/>
                    </Routes>
                </Provider>
            </BrowserRouter>
        </div>
    );
}

export default App;