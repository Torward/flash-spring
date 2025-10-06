import React from "react";
import {NavLink} from "react-router-dom";

import style from "./DropdownItem.module.css"


const DropdownItem = (props) => {
  return(
      <li className={style.dropdown_item}>
            <NavLink style={{
                color: "rgb(147,196,241)",
            }} to={`${props.link}`}>
                {props.text}
            </NavLink>
      </li>
  );
}
export default DropdownItem;