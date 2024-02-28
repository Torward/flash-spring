import React, {Component} from "react";
import Grid from "@mui/material/Grid";

import {Avatar, Box, Icon, Stack, Typography} from "@mui/material";
import "./Post.css";
import {useNavigate} from "react-router-dom";
import {Favorite} from "@mui/icons-material";
import VideoPlayer from "../../../widgets/VideoPlayer/VideoPlayer";

// import postImage from "../../images/postfoto.jpg";

const Post = (
    // {post}
    props
) => {
    const navigate = useNavigate()
    const handleLikePost = (e) => {
        e.preventDefault();
        console.log(props.id);
    }
    const handleDeletePost = (e) => {
        e.preventDefault();
        console.log(props.id);
    }

    return (
        <div>
            <Stack className="post-container">
                <div className="post-container__header">
                    <div className="post-container__header__user-info">
                        <Avatar
                            src={props.userAvatar}
                            sx={{
                                width: 24,
                                height: 24,
                            }}
                            className="post-avatar"
                            onClick={() => navigate(`/profile/${props.userId}`)}
                        />{" "}
                        <div className="post-data">
                            <span className="post-username">{props.userName}</span>
                            <span className="post-location">{props.location}</span>
                        </div>
                    </div>

                </div>
                <div className="post-container__img-block">
                        {props.postImage && <img
                            className="img-block__img"
                            src={props.postImage}
                            alt="post_image"
                        ></img>}

                    {props.video && <VideoPlayer src={props.video} />}

                    {props.audio && <AudioPlayer src={props.audio} />}
                </div>
                <Box
                    className={'content-field w-1/3 h-[40px] flex justify-end items-center pt-6 pr-5 overflow-auto '}
                    sx={{margin: 'auto 0'}}>
                    <Typography className={'flex justify-start items-center'}>{props.postContent}</Typography>
                </Box>
                <div className="post-container__header__time">
                    {/*<span>{post.time}</span>*/}
                </div>
                <div className="post-container__analitics">
                    {props.likes === 0 ?
                        (
                            <Icon className="favorite"
                                  onClick={handleLikePost}
                                  sx={{marginRight: 2,}}>
                                favorite_border_outlined
                            </Icon>
                        )
                        :
                        (
                            <Favorite className="favorite"
                                      color="error"
                                      onClick={handleLikePost}/>
                        )
                    }
                    <Icon className="chat" sx={{marginRight: 2,}}>
                        chat_bubble_outline_outlined
                    </Icon>
                    <Icon
                        className="share"
                        sx={{
                            marginRight: 1,
                        }}
                    >
                        share_outlined
                    </Icon>
                    <Icon
                        className="bookmark"
                        sx={{
                            marginRight: 2,
                        }}
                    >
                        bookmark_border_outlined
                    </Icon>{" "}
                    <div className="appLikes-block">
                        {" "}
                        <span>{props.likes}</span>{" "}
                    </div>
                    {" "}
                </div>
                {" "}
                <div className="post-container__comments">
                    {props.comments.map((item, index) => (
                        <div className="comment-payload">
                            {item.username}:{item.description}
                        </div>
                    ))}
                </div>
                <div className="post-container__comment-input">
                    <input
                        className="comment-input__input"
                        type="text"
                        placeholder="Напиши чёнить..."
                    />
                </div>
            </Stack>
        </div>
    );
}


export default Post;
