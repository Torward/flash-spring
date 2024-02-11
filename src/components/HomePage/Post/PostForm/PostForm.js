import React, {Component} from "react"
import {EditorState} from "draft-js"
import {Editor} from 'react-draft-wysiwyg'
import {Route, Routes} from "react-router-dom";
import style from "./PostForm.module.css"
import 'react-draft-wysiwyg/dist/react-draft-wysiwyg.css';
import AddPostForm from "../AddPost/AddPostForm";
import EditPost from "../EditPost/EditPost";



// import {render} from "react-dom";


class PostForm extends Component {
    constructor(props) {
        super(props);
        this.state = {
            editorState: EditorState.createEmpty(),
        }
    };

    onEditorStateChange: Function = (editorState) => {
        this.setState({
            editorState,
        });
    };

    render() {
        const {editorState} = this.state;
        function uploadImageCallBack(file) {
            return new Promise(
                (resolve, reject) => {
                    const xhr = new XMLHttpRequest();
                    xhr.open('POST', 'https://api.imgur.com/3/image');
                    xhr.setRequestHeader('Authorization', 'Client-ID XXXXX');
                    const data = new FormData();
                    data.append('image', file);
                    xhr.send(data);
                    xhr.addEventListener('load', () => {
                        const response = JSON.parse(xhr.responseText);
                        resolve(response);
                    });
                    xhr.addEventListener('error', () => {
                        const error = JSON.parse(xhr.responseText);
                        reject(error);
                    });
                }
            );
        }

        return (
            <div className={style.post_input_bar}>
                <Routes>
                    <Route  path={'add_post'} element={<AddPostForm/>}/>
                    <Route  path={'edit_post/:postID'} element={<EditPost/>}/>
                </Routes>
            </div>
        )
    }
}

export default PostForm