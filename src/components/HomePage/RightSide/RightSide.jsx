import React, {useEffect, useRef, useState} from "react";
import {Avatar, Button, Icon, IconButton, Stack} from "@mui/material";
import Groups from "../Groups/Groups";
import Advertisment from "../Advertisment/Advertisment";
import Suggestions from "../Suggestions/Suggestions";
import {NavLink, useNavigate} from "react-router-dom";
import DropdownItem from "../../../widgets/DropdownItem/DropdownItem";
import "./RightSide.css";
import {AddBox} from "@mui/icons-material";
import AddPostForm from "../Post/AddPost/AddPostForm";
import {useDispatch} from "react-redux";
import {logoutUser} from "../../../store/Auth/Action";

const RightSide = (props) => {
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const [open, setOpen] = useState(false);
    const [modalActive, setModalActive] = useState(false);
    const menuRef = useRef();

    useEffect(() => {
        const handler = (event) => {
            if (!menuRef.current.contains(event.target)) {
                setOpen(false);
            }
        };
        document.addEventListener("mousedown", handler);
        return () => {
            document.removeEventListener("mousedown", handler);
        };
    }, []);

    const openMenu = () => setOpen(!open);
    const openWindow = () => setModalActive(true);
    const logoutHandler = () => {
        console.log("logout");
        dispatch(logoutUser());
    };

    return (
        <Stack>
            <div className="navbar h-[50px] xl:top-0 lg:top-0 fixed px-3 md:bottom-0 sm:bottom-0 xl:left-1/4 lg:left-1/4 md:left-0 sm:left-0 m-0 xl:w-[50%] lg:w-[50%] md:w-[100%] sm:w-[100%] bg-white shadow-md z-10">
                <div className={'flex'}>
                    <div className="sidebar-content__logo xl:flex lg:flex md:absolute sm:absolute md:hidden sm:hidden top-0 left-4">ВСПЫШКА</div>
                    <div className="navbar__iconblock w-full justify-center items-center">

                        <div className={'menu_container'} ref={menuRef}>
                            <div className={'dropdown_trigger'} onClick={openMenu}>
                                <Avatar
                                    className={'iconblock__avatar md:text-2xl md:mx-4'}
                                    src={props.avatarImg}
                                    sx={{
                                        width: 36,
                                        height: 36,
                                    }}
                                />
                            </div>
                            <div className={`dropdown_menu ${open ? 'active' : 'inactive'}`}>
                                <h3 className={'menu_username'}>Портос
                                    <span className={'menu_location'}>Париж</span></h3>
                                <ul>
                                    <DropdownItem text={'твой профиль'}/>
                                    <DropdownItem text={'редактировать'}/>
                                    <DropdownItem text={'настройки'}/>
                                    <Button variant={'text'} sx={{
                                        color: 'rgb(147,196,241)',
                                        marginLeft: '5px',
                                        borderTop: '1px solid rgb(30, 73, 118)'
                                    }} onClick={logoutHandler}>выход</Button>
                                </ul>
                            </div>
                        </div>
                        <IconButton type={'button'} onClick={openWindow} aria-label="open add post form"
                                    className={'open_modal_btn ml-3'}>
                            <AddBox className={'modal_btn__icon'}/>
                        </IconButton>
                        <AddPostForm active={modalActive} setActive={setModalActive} addPost={props.addPost}/>
                        <Icon className="navbar__button ">home</Icon>
                        <Icon className="navbar__button ">notifications</Icon>
                        <NavLink to={'/dialogs/' + props.id} className="navbar__button">
                            <Icon>message</Icon>
                        </NavLink>
                        <Icon className="navbar__button">favorite</Icon>
                    </div>
                </div>
            </div>
            <div className="hidden md:block xl:mt-20 lg:mt-20">
                <div className="groups">
                    <Stack container>
                        <div className="suggestion-header">
                            <span>ТВОИ ГРУППЫ</span>
                        </div>
                        <div className="groups-body">
                            <Groups/>
                            <span className="suggestion-footer">Ещё предложений</span>
                        </div>
                    </Stack>
                </div>
                <div className="advertisment">
                    <Advertisment/>
                </div>
                <div className="suggestions">
                    <Stack container>
                        <div className="suggestion-header">
                            <span>ВЫБИРАЙ ДРУЗЕЙ</span>
                        </div>
                        <div className="suggestion-body">
                            <Suggestions/>
                            <span className="suggestion-footer">Ещё предложений</span>
                        </div>
                    </Stack>
                </div>
            </div>
            {/*<div className="md:hidden fixed bottom-0 left-0 w-full bg-white shadow-md p-4">*/}
            {/*    <div className="navbar__iconblock">*/}
            {/*        <div className={'menu_container'} ref={menuRef}>*/}
            {/*            <div className={'dropdown_trigger'} onClick={openMenu}>*/}
            {/*                <Avatar*/}
            {/*                    className={'iconblock__avatar'}*/}
            {/*                    src={props.avatarImg}*/}
            {/*                    sx={{*/}
            {/*                        width: 36,*/}
            {/*                        height: 36,*/}
            {/*                    }}*/}
            {/*                />*/}
            {/*            </div>*/}
            {/*            <div className={`dropdown_menu ${open ? 'active' : 'inactive'}`}>*/}
            {/*                <h3 className={'menu_username'}>Портос*/}
            {/*                    <span className={'menu_location'}>Париж</span></h3>*/}
            {/*                <ul>*/}
            {/*                    <DropdownItem text={'твой профиль'} />*/}
            {/*                    <DropdownItem text={'редактировать'} />*/}
            {/*                    <DropdownItem text={'настройки'} />*/}
            {/*                    <Button variant={'text'} sx={{*/}
            {/*                        color: 'rgb(147,196,241)',*/}
            {/*                        marginLeft: '5px',*/}
            {/*                        borderTop: '1px solid rgb(30, 73, 118)'*/}
            {/*                    }} onClick={logoutHandler}>выход</Button>*/}
            {/*                </ul>*/}
            {/*            </div>*/}
            {/*        </div>*/}
            {/*        <IconButton type={'button'} onClick={openWindow} aria-label="open add post form"*/}
            {/*                    className={'open_modal_btn'}>*/}
            {/*            <AddBox className={'modal_btn__icon'} />*/}
            {/*        </IconButton>*/}
            {/*        <AddPostForm active={modalActive} setActive={setModalActive} addPost={props.addPost} />*/}
            {/*        <Icon className="navbar__button">home</Icon>*/}
            {/*        <Icon className="navbar__button">notifications</Icon>*/}
            {/*        <NavLink to={'/dialogs/' + props.id} className="navbar__button">*/}
            {/*            <Icon>message</Icon>*/}
            {/*        </NavLink>*/}
            {/*        <Icon className="navbar__button">favorite</Icon>*/}
            {/*    </div>*/}
            {/*</div>*/}
        </Stack>
    );
}

export default RightSide;