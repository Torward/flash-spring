import React from 'react'
import {Box} from "@mui/material";
import Post from "../Post/Post";

const BookmarksContent = (props) => {
    let postComponents = props.savedPosts.map(p => <Post
        id={p.postId}
        userName={p.username}
        location={p.location}
        userAvatar={p.userAvatar}
        postImage={p.postImageURL}
        likes={p.likes}
    />)
    return (
        <Box className={'w-full flex-col'}>{postComponents}</Box>
    )
}
export default BookmarksContent
