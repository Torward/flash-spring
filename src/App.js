import "./App.css";
import React from 'react';
import {BrowserRouter, Routes, Route} from 'react-router-dom';
import LoginPage from "./components/LoginPage/LoginPage";
import Home from "./components/HomePage/Home";

function App(props) {
    return (
        <div className="App">
            <BrowserRouter>
                <Routes>
                    <Route path='/login' element={<LoginPage/>}/>
                    <Route path={'/*'} element={<Home
                        dialogsState={props.state.dialogsPage}
                        feedState={props.state.feedPage}
                    />}/>
                </Routes>
            </BrowserRouter>
        </div>
    );
}

export default App;