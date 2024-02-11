import React, {useState} from "react";
import {Avatar} from "@mui/material";
import {NavLink} from "react-router-dom";

import style from "./ItemDialog.module.css";


const ItemDialog =(props) => {
    const [active, setActive] = useState(false)
    let path = `/dialogs/`+ props.id
    const handleClick = () => {
        if (active) {
            setActive(false)
        } else {
            setActive(true)
        }

    }
        return (
            <div onClick={handleClick}>
                <NavLink to={path} className={style.link_dialog}>
                    <div className={active ? style.suggestion_body__element_active :style.suggestion_body__element}>
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
                            <span className={props.isActive ? style.element__info__location_active :style.element__info__location}>{props.location}</span>
                        </div>
                    </div>
                </NavLink>
            </div>
        );

}

export default ItemDialog;
