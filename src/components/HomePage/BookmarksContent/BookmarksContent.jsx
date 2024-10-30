import React, {useEffect} from 'react'
import {Box} from "@mui/material";
import Post from "../Post/Post";
import {useDispatch, useSelector} from "react-redux";
import {findPostByBookmarksContainsUser} from "../../../store/Post/Action";

const BookmarksContent = () => {
    const {post} = useSelector((state) => state);
    const dispatch = useDispatch();
    useEffect(()=>{
        dispatch(findPostByBookmarksContainsUser())
    },[])
    let postComponents = post.posts.map(p => <Post
        key={p.postId}
        post={p}
    />)
    return (
        <Box className={'w-full flex-col'}>{postComponents}</Box>
    )
}
export default BookmarksContent
