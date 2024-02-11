import React, {useState} from "react";
// import {useHistory} from 'react-router-dom'
import axios from 'axios'
import "./AddPost.css"
import "react-quill/dist/quill.snow.css";
import {Box, Button, TextField, Typography} from "@mui/material";


function AddPostForm({active, setActive, addPost}) {
    // let history = useHistory();
    const [userInfo, setUserInfo] = useState(''
    //     {
    //     // title: '',
    //     // content: '',
    // }
    );

    const onChangeValue = (e) => {
        setUserInfo({
            ...userInfo,
            title: e.target.value
        });
    }
    const onDescription = (e) => {
        setUserInfo(
            e.target.value
        //     {
        //     ...userInfo,
        //     content: e.target.value
        // }
        );
    }
    const onInformation = (value) => {
        setUserInfo({
            ...userInfo,
            information: value
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
    const addPostHandler = (event) => {
        event.preventDefault();
        event.persist();
        console.log(userInfo)
        addPost(userInfo)
        setUserInfo('')

        // try {
        //     event.preventDefault();
        //     event.persist();
        //     if(userInfo.content.length < 1){
        //         setError('Поле не должно оставаться пустым!');
        //         return;
        //     }
        //     axios.post(`http://localhost:8080/addPost`, {
        //         title: userInfo.title,
        //         content: userInfo.content,
        //     })
        //         .then(res => {
        //             if(res.data.success === true){
        //                 // history.push('/');
        //             }
        //         })
        // } catch (error) { throw error;}
    }
    return (
        <Box sx={{maxHeight: '100vh', width: '100vw', overflow: 'scroll'}} className={active ? 'modal active' : 'modal'}
             onClick={() => setActive(false)}>
            <div className={active ? 'modal__content active' : 'modal__content'}
                 onClick={event => event.stopPropagation()}>
                <div className={'row'}>
                    <form
                        // onSubmit={addPost}
                        className={'update__forms'}>
                        <h3 className={'my_account__content'}> Создай свои впечатления </h3>
                        <div className={'form_row'}>
                            {/*<div className={'form_group'}>*/}
                            {/*    <label className={'font_weight_bold'}> Заглавие <span className={'required'}> * </span>*/}
                            {/*    </label>*/}
                            {/*    <input type="text" name="title" value={userInfo.title} onChange={onChangeValue}*/}
                            {/*           className={'form_control'} placeholder="Назови..." required/>*/}
                            {/*</div>*/}
                            <Box className={'clearfix'} my={2}></Box>
                            <div className={'editor'}>
                                <label className={'font_weight_bold'}> Суть <span className={'required'}> * </span>
                                </label>
                                <TextField
                                    multiline
                                    type={"text"}
                                    className={'w-full'}
                                    placeholder={"Напиши что-нибудь..."}
                                    sx={{
                                        display: 'flex',
                                        outline: 'none',
                                        color: "rgb(176,176,176)",
                                        backgroundColor: "rgb(0, 30, 60)",
                                        borderRadius: '10px',
                                        border: '1px solid rgb(30, 73, 118)',
                                        '& .MuiOutlinedInput-root': {color: 'white', outline: 'none'},
                                    }}
                                    inputProps={{
                                        sx: {
                                            '&::placeholder': {
                                                color: 'rgb(0, 200, 255)',
                                                opacity: 1,
                                            }
                                        }
                                    }}
                                    value={userInfo}
                                    onChange={onDescription}/>
                            </div>
                            <br/>
                            <br/>
                            {isError !== null && <div className="errors"> {isError} </div>}
                            <Box className="form-group w-full" sx={{
                                display: 'flex',
                                justifyContent: 'end',
                                alignItems: 'center',
                            }}>
                                <Button type="submit" variant="outlined" sx={{color: 'rgb(128, 191, 255)'}} onClick={addPostHandler}> <Typography
                                    sx={{
                                        letterSpacing: '1px'
                                    }} fontWeight={600}>Опубликовать</Typography> </Button>
                            </Box>
                        </div>
                    </form>
                </div>
            </div>
        </Box>
    );
}

export default AddPostForm;