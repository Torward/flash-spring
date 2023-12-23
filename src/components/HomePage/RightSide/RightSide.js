import React, {useEffect, useRef, useState} from "react";
import {Icon, IconButton} from "@mui/material";
import { Avatar, Stack } from "@mui/material";
import Groups from "../Groups/Groups";
import Advertisment from "../Advertisment/Advertisment";
import Suggestions from "../Suggestions/Suggestions";
import {NavLink} from "react-router-dom";
import DropdownItem from "../../../widgets/DropdownItem/DropdownItem";

import "./RightSide.css";
import {AddBox} from "@mui/icons-material";
import style from "../Dialogs/Dialogs.module.css";
import AddPost from "../Post/AddPost/AddPost";

const RightSide =(props) => {
    let openMenu = () => {
        setOpen(!open)
    }

    const [open, setOpen] = useState(false);
    let [modalActive, setModalActive] = useState(false);
    let menuRef = useRef();
    useEffect(() =>{
        let handler = (event) =>{
            if (!menuRef.current.contains(event.target)){
                setOpen(false);
            }

        }
        document.addEventListener("mousedown", handler);
        return() => {
            document.removeEventListener("mousedown", handler);
        }
    });
    let openWindow = () => setModalActive(true);


    return (
      <Stack>
        <div className="navbar">
          <div className="navbar__iconblock">

              <div className={'menu_container'} ref={menuRef}>
                  <div className={'dropdown_trigger'} onClick={openMenu} >
                      <Avatar
                          className={'iconblock__avatar'}
                          src={props.avatarImg}
                          sx={{
                              width: 36,
                              height: 36,
                          }}
                      />
                  </div>
                  <div className={`dropdown_menu ${open? 'active' : 'inactive'}`}>
                        <h3 className={'menu_username'}>Портос
                      <span className={'menu_location'}>Париж</span></h3>
                      <ul>
                        <DropdownItem text={'твой профиль'}/>
                        <DropdownItem text={'редактировать'}/>
                        <DropdownItem text={'настройки'}/>

                          <NavLink to={'logout'}><DropdownItem text={'выход'}/></NavLink>

                      </ul>
                  </div>
              </div>



                <IconButton type={'button'} onClick={openWindow} aria-label="open add post form" className={'open_modal_btn'}>
                    <AddBox className={'modal_btn__icon'} />
                </IconButton>
                <AddPost active={modalActive} setActive={setModalActive}/>

            <Icon className="navbar__button">home</Icon>
            <Icon className="navbar__button">notifications</Icon>
              <NavLink to={'/dialogs/' + props.id} className="navbar__button" >
                  <Icon>message</Icon>
              </NavLink>

            <Icon className="navbar__button">favorite</Icon>
          </div>
        </div>
        <div className="groups">
          <Stack container>
            <div className="suggestion-header">
              <span>ТВОИ ГРУППЫ</span>
            </div>
            <div className="groups-body">
              <Groups />
              <span className="suggestion-footer">Ещё предложений</span>
            </div>
          </Stack>
        </div>
        <div className="advertisment">
          <Advertisment />
        </div>
        <div className="suggestions">
          <Stack container>
            <div className="suggestion-header">
              <span>ВЫБИРАЙ ДРУЗЕЙ</span>
            </div>
            <div className="suggestion-body">
              <Suggestions />
              <span className="suggestion-footer">Ещё предложений</span>
            </div>
          </Stack>
        </div>
      </Stack>
    );
}

export default RightSide;
