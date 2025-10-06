import ReactDOM from "react-dom/client";
import React from "react";
import {DevSupport} from "@react-buddy/ide-toolbox";
import {ComponentPreviews, useInitial} from "../dev";
import App from "../App";
import {addMessage, addPost, addUser, updateMessageHandler} from "./state";


import '../index.css';
import reportWebVitals from "../reportWebVitals";
import {store} from "../store/store";
import {Provider} from "react-redux";
import {BrowserRouter} from "react-router-dom";

const root = ReactDOM.createRoot(document.getElementById('root'));
export let rerenderEntireTree = (state) =>{

    root.render(
        <BrowserRouter>
        <Provider store={store}>
            <DevSupport ComponentPreviews={ComponentPreviews}
                        useInitialHook={useInitial}>
                <App state={state}
                     addMessage={addMessage}
                     updateMessageHandler={updateMessageHandler}/>
            </DevSupport>
        </Provider>
        </BrowserRouter>
    );
}

reportWebVitals();