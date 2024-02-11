import React, {useState} from "react";
import {Box, Icon, Stack} from "@mui/material";
import "./MainContent.css";
import StatusBar from "../StatusBar/StatusBar";
import Post from "../Post/Post";
import AddPostForm from "../Post/AddPost/AddPostForm";

const MainContent = (props) => {
    let [modalActive, setModalActive] = useState(false);
        let postComponents = props.posts.map(p => <Post
            id={p.postId}
            userName={p.username}
            location={p.location}
            userAvatar={p.userAvatar}
            postImage={p.postImageURL}
            postContent={p.postContent}
            likes={p.likes}
        />)
        let statusComponents = props.statuses.map(s => <StatusBar
            id={s.postId}
            userName={s.username}
            imageURL={s.imageURL}
            userAvatar={s.userAvatar}

        />)
       const openWindow = () => setModalActive(true);
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
                        <Box className={'status_section'} sx={{
                            justifyContent: 'center',
                        }}>
                            {statusComponents}
                        </Box>
                    </div>
                    <div className={'add_new_link'}  onClick={openWindow} >
                            <span>Поделись впечатлениями</span>
                            <Icon>add</Icon>
                    </div>
                    <div>
                        {postComponents}
                    </div>
                </Stack>
                <AddPostForm active={modalActive} setActive={setModalActive} addPost={props.addPost}/>
            </div>
        );
}

export default MainContent;
