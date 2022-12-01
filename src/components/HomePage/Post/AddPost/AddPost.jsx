import React, {useState}from "react";
import ReactQuill from "react-quill";
import EditorToolbar, { modules, formats } from "../EditToolbar/EditToolbar"
// import {useHistory} from 'react-router-dom'
import axios from 'axios'

import style from "./AddPost.module.css"
import "react-quill/dist/quill.snow.css";



function AddPost() {
    // let history = useHistory();
    const [userInfo, setUserInfo] = useState({
        title: '',
        description: '',
        information: '',
    });
    const onChangeValue = (e) => {
        setUserInfo({
            ...userInfo,
            [e.target.name]:e.target.value
        });
    }
    const onDescription = (value) => {
        setUserInfo({ ...userInfo,
            description:value
        });
    }
    const onInformation = (value) => {
        setUserInfo({ ...userInfo,
            information:value
        });
    }
    const [isError, setError] = useState(null);
    const textAreaStyleProperties = {
        color: "white",
        backgroundColor: "rgb(0, 30, 60)",
        padding: "10px",
        fontFamily: "Arial",
        border: "1px solid rgb(30, 73, 118)!important",
        borderRadius: "0 0 10px 10px",
        placeholderColor: "rgb(0, 200, 255)"
    }
    const addDetails = async (event) => {
        try {
            event.preventDefault();
            event.persist();
            if(userInfo.description.length < 50){
                setError('Required, Add description minimum length 50 characters');
                return;
            }
            axios.post(`http://localhost:8080/addPost`, {
                title: userInfo.title,
                description: userInfo.description,
                information: userInfo.information,
            })
                .then(res => {
                    if(res.data.success === true){
                        // history.push('/');
                    }
                })
        } catch (error) { throw error;}
    }

    return(
        <div>
            <div className={style.container}>
                <div className={style.row}>
                    <form onSubmit={addDetails} className={style.update__forms}>
                        <h3 className={style.my_account__content}> Создай свои впечатления  </h3>
                        <div className={style.form_row}>
                            <div className={style.form_group}>
                                <label className={style.font_weight_bold}> Title <span className={style.required}> * </span> </label>
                                <input type="text" name="title" value={userInfo.title} onChange={onChangeValue}  className={style.form_control} placeholder="Заглавие" required />
                            </div>
                            <div className={style.clearfix}></div>
                            <div className={style.editor}>
                                <label className={style.font_weight_bold}> Description <span className={style.required}> * </span> </label>
                                <EditorToolbar toolbarId={'t1'}/>
                                <ReactQuill
                                    className={style.react_quill}
                                    theme="snow"
                                    value={userInfo.description}
                                    onChange={onDescription}
                                    placeholderColor={"rgb(0, 200, 255)"}
                                    placeholder={"Напиши, что-нибудь интересное..."}
                                    modules={modules('t1')}
                                    formats={formats}
                                    style={textAreaStyleProperties}
                                />
                            </div>
                            <br />
                            <div className="form-group col-md-12 editor">
                                <label className={style.font_weight_bold}> Additional Information  </label>
                                <EditorToolbar toolbarId={'t2'}/>
                                <ReactQuill
                                    theme="snow"
                                    value={userInfo.information}
                                    onChange={onInformation}
                                    placeholder={"Напиши, что-нибудь интересное..."}
                                    placeholderColor={"rgb(0, 200, 255)"}
                                    modules={modules('t2')}
                                    formats={formats}
                                    style={textAreaStyleProperties}
                                />
                            </div>
                            <br />
                            {isError !== null && <div className="errors"> {isError} </div>}
                            <div className="form-group col-sm-12 text-right">
                                <button type="submit" className="btn btn__theme"> Опубликовать  </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    );
}

export default AddPost;