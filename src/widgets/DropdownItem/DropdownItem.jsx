import React from "react";
import { Icon } from "@mui/material";
import {NavLink} from "react-router-dom";

import style from "./DropdownItem.module.css"


const DropdownItem = (props) => {
  return(
      <li className={style.dropdown_item}>
            <NavLink to={"/"}>
                {props.text}
            </NavLink>
      </li>
  );
}
export default DropdownItem;