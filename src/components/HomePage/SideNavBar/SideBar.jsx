import React from "react";
import Icon from '@mui/material/Icon';
import "./SideBar.css";

import {Avatar, Box, Stack} from "@mui/material";
import Stories from "../Stories/Stories";
import {NavLink} from "react-router-dom";
import {useSelector} from "react-redux";

const SideBar = (props) => {
    const {auth} = useSelector(store => store)
    return (
        <div className="sidebar-content ">
        <Stack container>

            <div className="sidebar-content__avatar">
                <Box className={'flex w-full justify-center xl:mt-20 lg:mt-20 md:mt-6 sm:mt-6'}>
                    <div className="avatar__block">
                        <Avatar
                            src={auth.user?.image}
                            sx={{
                                width: 125, height: 125,
                            }}
                        />
                    </div>
                </Box>
                <div className="sidebar-content__name">
                    <span className="username">{auth.user.fullName}</span>
                    <span className="details">{props.details}</span>
                </div>
            </div>
            <div className="sidebar-content__albums">
                <Stories/>
            </div>
            <div className="sidebar-content__statistic">
                <div className="sidebar-content__statistic__posts analytic">
                    <span className="statistic">Статьи</span>
                    <span>{props.posts}</span>
                </div>
                <div className="sidebar-content__statistic__followers analytic">

                    <span className="statistic">Наблюдатели</span>
                    <span>{props.followers}</span>
                </div>

                <div className="sidebar-content__statistic__following analytic">

                    <span className="statistic">Наблюдаешь</span>
                    <span>{props.following}</span>
                </div>
            </div>
            <div className="sidebar-content__tools items-center xl:space-y-3 lg:space-y-3 space-y-4 xl:text-lg lg:text-lg md:space-y-6 sm:space-y-6 md:text-2xl sm:text-2xl">
                <NavLink to={'/profile'} className="link w-1/4">
                    <div className="sidebar-content__tools__profile  content flex">
                        <div className={'flex items-start '}>
                            <Icon sx={{marginRight: 1}}>person</Icon>
                        </div>

                        <span className={'flex w-full'}>Ты</span>
                    </div>
                </NavLink>
                <NavLink to={'/feed'} className="link w-1/4">
                    <div className="sidebar-content__tools__feed content flex">
                        <Icon sx={{marginRight: 1}}>feed</Icon>
                        <span>Лента</span>
                    </div>
                </NavLink>
                <NavLink to={'/explore'} className="link w-1/4">
                    <div className="sidebar-content__tools__explore content flex">
                        <Icon sx={{marginRight: 1}}>explore</Icon>
                        <span>Исследуй</span>
                    </div>
                </NavLink>
                <NavLink to={'/bookmarks'} className="link w-1/4">
                    <div className="sidebar-content__tools__saved content flex">
                        <Icon sx={{marginRight: 1}}>bookmarks</Icon>
                        <span>Закладки</span>
                    </div>
                </NavLink>
                <NavLink to={'/dialogs'} className="link w-1/4">
                    <div className="sidebar-content__tools__trending content flex">
                        <Icon sx={{marginRight: 1}}>hub</Icon>
                        <span>Гонец</span>
                    </div>
                </NavLink>

                <div className="sidebar-content__tools__insights content flex w-1/4">
                    <Icon sx={{marginRight: 1,}}>insights</Icon>
                    <span>Развитие</span>{" "}
                </div>

                {/* <div className="sidebar-content__tools__direct">
              <Icon>direct</Icon>
              <span>Направление</span>
            </div> */}
            </div>
            {" "}
        </Stack>{" "}
    </div>);

}

export default SideBar;
