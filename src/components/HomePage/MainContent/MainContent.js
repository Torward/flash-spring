import React, {Component} from "react";
import {Icon, Stack} from "@mui/material";
import "./MainContent.css";
import StatusBar from "../StatusBar/StatusBar";
import PostForm from "../Post/PostForm/PostForm";
import {NavLink} from "react-router-dom";
import ItemDialog from "../Dialogs/ItemDialog/ItemDialog";
import Post from "../Post/Post";

class MainContent extends Component {
    constructor(props) {
        super(props);

        this.state = {};
    }

    render() {
        let postComponents = this.props.posts.map(p => <Post
            id={p.postId}
            userName={p.username}
            location={p.location}
            userAvatar={p.userAvatar}
            postImage={p.postImageURL}
            likes={p.likes}
        />)
        let statusComponents = this.props.statuses.map(s => <StatusBar
            id={s.postId}
            userName={s.username}
            imageURL={s.imageURL}
            userAvatar={s.userAvatar}

        />)
        return (
            <div className="main">
                <div className="main__searchbar">
                    <input
                        className="searchbar-input"
                        type="text"
                        placeholder="Ищи здесь..."
                    />
                </div>
                <Stack container>
                    <div>
                        <div className="statusbar-header">
                            <span>ИСТОРИИ</span>
                        </div>
                        <div className={'status_section'}>
                            {statusComponents}
                        </div>
                    </div>
                    <div>
                        {/*<PostForm />*/}
                        <NavLink to={'/add_post'} className={'add_new_link'}>
                            <span>Поделись впечатлениями</span>
                            <Icon>add</Icon>
                        </NavLink>
                    </div>
                    <div>
                        {postComponents}
                    </div>
                </Stack>
            </div>
        );
    }
}

export default MainContent;
