import React from "react";

import {Avatar, Box, Button, Icon, Menu, MenuItem, Stack, TextField, Typography} from "@mui/material";
import "./Post.css";
import {useNavigate} from "react-router-dom";
import {Chat, Favorite, MoreVert, Repeat, Share} from "@mui/icons-material";
import VideoPlayer from "../../../widgets/VideoPlayer/VideoPlayer";
import MusicPlayer from "../../../widgets/MusicPlayer/MusicPlayer";
import {CommentList} from "../../../widgets/CommentList/CommentList";
import * as Yup from "yup";
import {useFormik} from "formik";

// import postImage from "../../images/postfoto.jpg";
const validationSchema = Yup.object({
    content: Yup.string().required('Пустую строку отправить нельзя'),
})
const Post = ({post}) => {
    const navigate = useNavigate()
    const [anchorEl, setAnchorEl] = React.useState(null);
    const open = Boolean(anchorEl);

    const formik = useFormik({
        initialValues: {
            content: '',
        },
        validationSchema,
        onSubmit: (values) => {
            console.log("Значения ответа переданые для отправки", values);
        },
    })

    const handleLikePost = (e) => {
        e.preventDefault();
        console.log(post.id);
    }
    const handleDeletePost = (e) => {
        e.preventDefault();
        console.log(post.id);
        handleClose()
    }

    function calculateTimeElapsed(createdAt) {
        const now = new Date();
        const created = new Date(createdAt);
        const diffMs = now - created;
        const diffSec = Math.round(diffMs / 1000);
        const diffMin = Math.round(diffSec / 60);
        const diffHrs = Math.round(diffMin / 60);
        const diffDays = Math.round(diffHrs / 24);
        const diffMonths = Math.round(diffDays / 30);
        const diffYears = Math.round(diffMonths / 12);

        if (diffYears > 0) {
            return `${diffYears} ${getCorrectWord(diffYears, 'год', 'года', 'лет')} назад`;
        } else if (diffMonths > 0) {
            return `${diffMonths} ${getCorrectWord(diffMonths, 'месяц', 'месяца', 'месяцев')} назад`;
        } else if (diffDays > 0) {
            return `${diffDays} ${getCorrectWord(diffDays, 'день', 'дня', 'дней')} назад`;
        } else if (diffHrs > 0) {
            return `${diffHrs} ${getCorrectWord(diffHrs, 'час', 'часа', 'часов')} назад`;
        } else if (diffMin > 0) {
            return `${diffMin} ${getCorrectWord(diffMin, 'минута', 'минуты', 'минут')} назад`;
        } else {
            return 'только что';
        }
    }

    function getCorrectWord(number, one, two, five) {
        let n = Math.abs(number);
        n %= 100;
        if (n >= 5 && n <= 20) {
            return five;
        }
        n %= 10;
        if (n === 1) {
            return one;
        }
        if (n >= 2 && n <= 4) {
            return two;
        }
        return five;
    }

    const elapsedTime = calculateTimeElapsed(post.createdAt);

    function handleClick(event) {
        setAnchorEl(event.currentTarget);
    }

    function handleClose() {
        setAnchorEl(null);
    }

    function handleCreateRepost() {

    }

    function handleOpenModalComments() {

    }

    return (
        <div>
            <Stack className="post-container">
                <div className="post-container__header">
                    <div className="post-container__header__user-info">
                        <Avatar
                            src={post.user.image}
                            alt={post.user.fullName}
                            sx={{
                                width: 24,
                                height: 24,
                            }}
                            className="post-avatar"
                            onClick={() => navigate(`/profile/${post.userId}`)}
                        />{" "}
                        <div className="post-data">
                            <span className="post-username">{post.user.fullName}</span>
                            <span className="post-location">{post.location}
                                <Box className="flex justify-end items-center">
                                    <Typography fontSize={'10px'} fontWeight={500}>{elapsedTime}</Typography>
                                </Box>
                            </span>
                        </div>


                    </div>
                    <Box>
                        <Button
                            id={'basic-button'}
                            aria-controls={open ? "basic-menu" : undefined}
                            aria-haspopup="true"
                            aria-expanded={open ? "true" : undefined}
                            onClick={handleClick}
                            sx={{
                                marginRight: 1,
                            }}
                        >
                            <MoreVert/>
                        </Button>
                        <Menu
                            id="basic-menu"
                            anchorEl={anchorEl}
                            open={open}
                            onClose={handleClose}
                            MenuListProps={{
                                'aria-labelledby': 'basic-button',
                            }}
                        >
                            <MenuItem onClick={handleDeletePost}>Удалить</MenuItem>
                            {/*<MenuItem onClick={handleClose}>Another action</MenuItem>*/}
                            {/*<MenuItem onClick={handleClose}>Something else here</MenuItem>*/}
                        </Menu>
                    </Box>

                </div>
                <div className="post-container__img-block">
                    {post.postImage && <img
                        className="img-block__img"
                        src={post.postImage}
                        alt="post_image"
                    ></img>}

                    {post.video && <VideoPlayer src={post.video}/>}

                    {post.audio && <MusicPlayer src={post.audio}/>}
                </div>
                <Box
                    className={'content-field w-full flex justify-start items-center py-6 px-5 overflow-auto '}
                    sx={{
                        margin: 'auto 0',
                        borderBottom: '1px solid rgb(30, 73, 118)',
                    }}>
                    <Typography className={'flex justify-start items-center'}>{post.content}</Typography>
                </Box>
                <div className="post-container__header__time">
                    {/*<span>{post.time}</span>*/}
                </div>
                <div className="post-container__analitics">
                    <Box
                        className={'flex justify-around items-center text-center w-[70px] bg-blue-300 rounded-full py-0.5'}>
                        {!post.liked ?
                            (
                                <Icon className="text-blue-950"
                                      onClick={handleLikePost}
                                >
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
                        <Typography className={'text-center text-blue-950'}>{post.totalLikes}</Typography>
                    </Box>

                    <Chat className="chat" sx={{margin: 1,}} onClick={handleOpenModalComments}/>
                    <Share
                        className="share"
                        // sx={{
                        //     margin: 1,
                        // }}
                    />
                    <Icon
                        className="bookmark"
                        sx={{
                            margin: 1,
                        }}
                    >
                        bookmark_border_outlined
                    </Icon>{" "}
                    <Repeat className="cursor-pointer" onClick={handleCreateRepost}/>

                    {/*<div className="appLikes-block">*/}
                    {/*    {" "}*/}
                    {/*    <span>{props.likes}</span>{" "}*/}
                    {/*</div>*/}
                </div>
                {post.commentsCount > 0 &&
                    <div className="post-container__comments">
                        <CommentList comments={post.posts.map().replyPost}/>
                    </div>
                }

                <div className="post-container__comment-input">
                    <form onSubmit={formik.handleSubmit}>
                        <TextField
                            multiline
                            fullWidth
                            name="content"
                            value={formik.values.content}
                            editing={formik.isSubmitting}
                            error={formik.touched.content && Boolean(formik.errors.content)}
                            helperText={formik.touched.content && formik.errors.content}
                            onChange={formik.handleChange}
                            onBlur={formik.handleBlur}
                            InputProps={{
                                style: {
                                    padding: '3px 40px',
                                    outline: 'none',
                                    '&.MuiInputBase': {
                                        outline: 'none',
                                    },
                                },
                                sx: {
                                    border: 'none!important',
                                    outline: 'none!important',
                                    '&.MuiInputBase': {
                                        outline: 'none',
                                        border: 'none!important',
                                    },
                                    '&:hover fieldset': {
                                        border: 'none!important',
                                        outline: 'none!important',
                                    },
                                    '&:focus-within fieldset, &:focus-visible fieldset': {
                                        border: 'none!important',
                                        outline: 'none!important',
                                    },
                                },
                            }}
                            inputProps={{
                                style: {
                                    color: 'rgb(51, 153, 255)',
                                    '&::placeholder': {
                                        color: 'rgb(109,174,234)',
                                    },
                                    '& :hover': {
                                        border: 'none',
                                        outline: 'none',
                                    },
                                    '& :focus': {
                                        border: 'none',
                                        outline: 'none',
                                    },
                                }
                            }}
                            placeholder="Напиши чёнить..."
                        />
                        <button
                            className="comment-input__button"
                            type="submit"
                        >
                            <Icon className="send-icon">send</Icon>
                        </button>
                    </form>
                </div>
            </Stack>
        </div>
    );
}


export default Post;
