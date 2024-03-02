import React, {useEffect, useState} from "react";
import {Box, Icon, Stack} from "@mui/material";
import "./MainContent.css";
import StatusBar from "../StatusBar/StatusBar";
import Post from "../Post/Post";
import AddPostForm from "../Post/AddPost/AddPostForm";
import {useDispatch, useSelector} from "react-redux";
import {getAllPosts} from "../../../store/Post/Action";

const MainContent = (props) => {
    const dispatch = useDispatch()
    let [modalActive, setModalActive] = useState(false);
    const {post} = useSelector((state) => state);
    console.log(post)
        let postComponents = post.posts.map(p => <Post
            key={p.postId}
            post={p}
            // id={p.postId}
            // comments={p.comments.map(c => c.comment)}
            // userName={p.username}
            // location={p.location}
            // userAvatar={p.userAvatar}
            // postImage={p.postImageURL}
            // postContent={p.postContent}
            // likes={p.likes}
        />)
        let statusComponents = props.statuses.map(s => <StatusBar
            key={s.postId}
            id={s.postId}
            userName={s.username}
            imageURL={s.imageURL}
            userAvatar={s.userAvatar}

        />)
    useEffect(() => {
        dispatch(getAllPosts())
    }, [])
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
