import ReactDOM from "react-dom/client";
import React from "react";
import {DevSupport} from "@react-buddy/ide-toolbox";
import {ComponentPreviews, useInitial} from "../dev";
import App from "../App";
import {addMessage, addPost, addUser, updateMessageHandler} from "./state";


import '../index.css';
import reportWebVitals from "../reportWebVitals";

const root = ReactDOM.createRoot(document.getElementById('root'));
export let rerenderEntireTree = (state) =>{

    root.render(
            <DevSupport ComponentPreviews={ComponentPreviews}
                        useInitialHook={useInitial}>
                <App state={state}
                     addUser={addUser}
                     addPost={addPost}
                     addMessage={addMessage}
                     updateMessageHandler={updateMessageHandler}/>

            </DevSupport>
    );
}

reportWebVitals();