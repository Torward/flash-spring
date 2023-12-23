import React, { Component } from "react";
import { Avatar, Icon, Stack } from "@mui/material";
import {NavLink} from "react-router-dom";

import style from "./ItemDialog.module.css";


const ItemDialog =(props) => {
    let path = "/dialogs/"+ props.id
        return (
            <div>
                <NavLink to={path} className={style.link_dialog}>
                    <div className={style.suggestion_body__element}>
                        <Avatar
                            className={style.element__img}
                            src={props.avatarImg}
                            sx={{
                                width: 36,
                                height: 36,
                            }}
                        />
                        <div className={style.element__info}>
                            <span className={style.element__info__username}>{props.username}</span>
                            <span className={style.element__info__details}>{props.details}</span>
                            <span className={style.element__info__location}>{props.location}</span>
                        </div>
                    </div>
                </NavLink>
            </div>
        );

}

export default ItemDialog;
