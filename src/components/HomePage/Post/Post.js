
import React from "react";

import {Avatar, Box, Button, Icon, Menu, MenuItem, Stack, TextField, Typography} from "@mui/material";
import "./Post.css";
import {useNavigate} from "react-router-dom";
import {Bookmark, BookmarkBorder, Chat, Favorite, MoreVert, Repeat, Share} from "@mui/icons-material";
import VideoPlayer from "../../../widgets/VideoPlayer/VideoPlayer";
import MusicPlayer from "../../../widgets/MusicPlayer/MusicPlayer";
import {CommentList} from "../../../widgets/CommentList/CommentList";
import * as Yup from "yup";
import {useFormik} from "formik";
import {useDispatch} from "react-redux";
import {addComment, bookmark, deletePost, likePost, repost} from "../../../store/Post/Action";

const validationSchema = Yup.object({
    content: Yup.string().required('Пустую строку отправить нельзя'),
})
const Post = ({post}) => {
    const navigate = useNavigate()
    const [anchorEl, setAnchorEl] = React.useState(null);
    const open = Boolean(anchorEl);
    const dispatch = useDispatch();

    const formik = useFormik({
        initialValues: {
            content: '',
        },
        validationSchema,
        onSubmit: (values) => {
            console.log("Значения ответа переданые для отправки", values);
            dispatch(addComment(values))
        },
    })

    const handleLikePost = (e) => {
        e.preventDefault();
        console.log(post.id);
        dispatch(likePost(post.id))
    }
    const handleDeletePost = (e) => {
        e.preventDefault();
        console.log(post.id);
        dispatch(deletePost(post.id))
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
        if (n >= 5 && n <= 20) {return five;}
        n %= 10;
        if (n === 1) {return one;}
        if (n >= 2 && n <= 4) {return two;}
        return five;
    }

    const elapsedTime = calculateTimeElapsed(post.createdAt);

    function handleClick(event) {
        setAnchorEl(event.currentTarget);
    }

    function handleClose() {
        setAnchorEl(null);
    }

    function handleCreateRepost(e) {
        e.preventDefault();
        console.log(post.id);
        dispatch(repost(post.id))
    }

    function handleOpenModalComments() {

    }


    function handleSavePost(e) {
        e.preventDefault();
        console.log(post.id);
        dispatch(bookmark(post.id))
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
                            <MenuItem>Изменить</MenuItem>
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
                <div className="post-container__analitics">


                    <Box
                        className={'flex justify-around items-center text-center bg-blue-300 rounded-[10px] px-2 py-1'}>
                        <Box className={'flex justify-around items-center text-center'}>
                            {!post.liked ?
                                (
                                    <Icon className="text-blue-950 cursor-pointer" onClick={handleLikePost}>
                                        favorite_border_outlined
                                    </Icon>
                                )
                                :
                                (
                                    <Favorite className="favorite cursor-pointer"
                                              color="error"
                                              onClick={handleLikePost}/>
                                )
                            }
                            <Typography className={'text-center text-blue-950'}>{post.totalLikes}</Typography>
                        </Box>
                        <Box
                            className={'flex justify-center items-center w-[45px] text-blue-950 text-center rounded-full'}>
                            <Chat className="chat ml-1" onClick={handleOpenModalComments}/>
                            <Typography>{post.totalReplies}</Typography>
                        </Box>
                        <Box
                            className={'flex justify-center items-center text-center w-[45px] text-blue-950 rounded-full'}>
                            <Share className="share"/>
                            <Typography>{post.totalShares}</Typography>
                        </Box>


                        <Box
                            className={'flex justify-center items-center text-center w-[45px] text-blue-950 rounded-full'}>
                            {post.reposted ?
                                <Repeat className="cursor-pointer text-red-500" onClick={handleCreateRepost}/>
                                :
                                <Repeat className="cursor-pointer text-blue-950" onClick={handleCreateRepost}/>
                            }
                            <Typography>{post.totalReposts}</Typography>
                        </Box>
                        {/*<div className="appLikes-block">*/}
                        {/*    {" "}*/}
                        {/*    <span>{props.likes}</span>{" "}*/}
                        {/*</div>*/}
                    </Box>
                </div>
                {post.commentsCount > 0 &&
                    <div className="post-container__comments">
                        <CommentList comments={post.posts.map().replyPost}/>
                    </div>
                }
                {post.bookmarked ?
                    <Bookmark className="cursor-pointer text-red-500" onClick={handleSavePost}/>
                    :
                    <BookmarkBorder className="cursor-pointer text-blue-950" onClick={handleSavePost}/>
                }
                <div className="post-container__comment-input">
                    <form onSubmit={formik.handleSubmit}>
                        <TextField
                            id={'content'}
                            type="text"
                            multiline
                            fullWidth
                            name="content"
                            variant={"filled"}
                            value={formik.values.content}
                            editing={formik.isSubmitting}
                            error={formik.touched.content && Boolean(formik.errors.content)}
                            helperText={formik.touched.content && formik.errors.content}
                            onChange={formik.handleChange}
                            onBlur={formik.handleBlur}
                            InputProps={{
                                style: {
                                    padding: '3px 40px',
                                    '& ::before': {
                                        outline: 'none!important',
                                        border: 'none!important',
                                    },
                                    '& ::after': {
                                        outline: 'none!important',
                                        border: 'none!important',
                                    },
                                    '&.MuiInputBase': {
                                        border: 'none',
                                        outline: 'none',
                                    },
                                },
                                sx: {
                                    border: 'none!important',
                                    outline: 'none!important',
                                    '&.MuiInputBase': {
                                        outline: 'none!important',
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
                                    height: '20px',
                                    border: 'none!important',
                                    outline: 'none!important',
                                    color: 'rgb(51, 153, 255)',
                                    '&::placeholder': {
                                        color: 'rgb(109,174,234)',
                                    },

                                    '& :hover': {
                                        border: 'none',
                                        outline: 'none',
                                    },
                                    '&.MuiInputBase': {
                                        outline: 'none!important',
                                        border: 'none!important',
                                    },
                                    '& :focus': {
                                        border: 'none',
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
