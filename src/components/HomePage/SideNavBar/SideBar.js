import React, {Component} from "react";
import Icon from '@mui/material/Icon';
import "./SideBar.css";

import {Avatar, Box, Stack} from "@mui/material";
import Stories from "../Stories/Stories";
import {NavLink} from "react-router-dom";

class SideBar extends Component {
    constructor(props) {
        super(props);

        this.state = {};
    }

    render() {
        return (<div className="sidebar-content">

            <Stack container>
                <div className="sidebar-content__logo">ВСПЫШКА</div>
                <div className="sidebar-content__avatar">
                    <Box className={'w-full'}>
                        <div className="avatar__block">
                            <Avatar
                                src={this.props.avatarImg}
                                sx={{
                                    width: 125, height: 125,
                                }}
                            />
                        </div>
                    </Box>

                    <div className="sidebar-content__name">
                        <span className="username">{this.props.username}</span>
                        <span className="details">{this.props.details}</span>
                    </div>
                </div>
                <div className="sidebar-content__albums">
                    <Stories/>
                </div>
                <div className="sidebar-content__statistic">
                    <div className="sidebar-content__statistic__posts analytic">
                        <span className="statistic">Статьи</span>
                        <span>{this.props.posts}</span>
                    </div>
                    <div className="sidebar-content__statistic__followers analytic">

                        <span className="statistic">Наблюдатели</span>
                        <span>{this.props.followers}</span>
                    </div>

                    <div className="sidebar-content__statistic__following analytic">

                        <span className="statistic">Наблюдаешь</span>
                        <span>{this.props.following}</span>
                    </div>
                </div>
                <div className="sidebar-content__tools">
                    <NavLink to={'/profile' } className="link">
                        <div className="sidebar-content__tools__profile content">
                            <Icon sx={{marginRight: 1}}>person</Icon>
                            <span>Ты</span>
                        </div>
                    </NavLink>
                    <NavLink to={'/feed'} className="link" >
                        <div className="sidebar-content__tools__feed content">
                            <Icon sx={{marginRight: 1}}>feed</Icon>
                            <span>Лента</span>
                        </div>
                    </NavLink>
                    <NavLink to={'/explore'} className="link">
                    <div className="sidebar-content__tools__explore content">
                        <Icon sx={{marginRight: 1}}>explore</Icon>
                        <span>Исследуй</span>
                    </div>
                    </NavLink>
                    <NavLink to={'/bookmarks'} className="link">
                    <div className="sidebar-content__tools__saved content">
                        <Icon sx={{marginRight: 1}}>bookmarks</Icon>
                        <span>Закладки</span>
                    </div>
                    </NavLink>
                    <NavLink to={'/dialogs'} className="link">
                    <div className="sidebar-content__tools__trending content">
                        <Icon sx={{marginRight: 1}}>hub</Icon>
                        <span>Сообщальник</span>
                    </div>
                    </NavLink>

                    <div className="sidebar-content__tools__insights content">
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
}

export default SideBar;
