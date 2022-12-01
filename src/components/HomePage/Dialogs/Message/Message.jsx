import React, {Component} from "react";
import {Avatar, Icon, Stack} from "@mui/material";
import style from "./Message.module.css";
import {NavLink} from "react-router-dom";

const Message = (props) => {
    return (
        <div>
            <div className={style.user_chat_content}>

                    <div className={style.messages_text_item}>
                        <div className={style.ufo}>

                        </div>
                        <div className={style.user_info}>
                            <h4>{props.username}</h4>
                            <Avatar
                                src={props.avatarImg}
                            />
                        </div>
                        <p className="mb-0">
                            {props.text}
                        </p>

                        <p className={style.chat_time}><i className="ri-time-line align-middle"></i> <span
                            className="align-middle">{props.time}</span></p>
                    </div>

                    {/*<div className="dropdown align-self-start">*/}
                    {/*    <a className="dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown"*/}
                    {/*       aria-haspopup="true" aria-expanded="false">*/}
                    {/*        <i className="ri-more-2-fill"></i>*/}
                    {/*    </a>*/}
                    {/*    /!*<div className="dropdown-menu">*!/*/}
                    {/*    /!*    <a className="dropdown-item" href="#">Copy <i*!/*/}
                    {/*    /!*        className="ri-file-copy-line float-end text-muted"></i></a>*!/*/}
                    {/*    /!*    <a className="dropdown-item" href="#">Save <i*!/*/}
                    {/*    /!*        className="ri-save-line float-end text-muted"></i></a>*!/*/}
                    {/*    /!*    <a className="dropdown-item" href="#">Forward <i*!/*/}
                    {/*    /!*        className="ri-chat-forward-line float-end text-muted"></i></a>*!/*/}
                    {/*    /!*    <a className="dropdown-item" href="#">Delete <i*!/*/}
                    {/*    /!*        className="ri-delete-bin-line float-end text-muted"></i></a>*!/*/}
                    {/*    /!*</div>*!/*/}
                    {/*</div>*/}

            </div>
        </div>
    )

}
export default Message