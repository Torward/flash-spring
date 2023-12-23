import ReactDOM from "react-dom/client";
import React from "react";
import {DevSupport} from "@react-buddy/ide-toolbox";
import {ComponentPreviews, useInitial} from "../dev";
import App from "../App";
import {addMessage, addPost, updateMessageHandler} from "./state";

import '../index.css';
import reportWebVitals from "../reportWebVitals";

const root = ReactDOM.createRoot(document.getElementById('root'));
export let rerenderEntireTree = (state) =>{
    root.render(
        <React.StrictMode>
            <DevSupport ComponentPreviews={ComponentPreviews}
                        useInitialHook={useInitial}>

                <App state={state}
                     addPost={addPost}
                     addMessage={addMessage}
                     updateMessageHandler={updateMessageHandler}/>

            </DevSupport>
        </React.StrictMode>
    );
}
reportWebVitals();