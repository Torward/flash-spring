import {api} from "../../config/api";
import {
    ADD_POST_COMMENT_FAILURE,
    ADD_POST_COMMENT_SUCCESS,
    ADD_POST_FAILURE,
    ADD_POST_SUCCESS, BOOKMARK_POST_FAILURE, BOOKMARK_POST_SUCCESS,
    DELETE_POST_FAILURE,
    DELETE_POST_SUCCESS,
    GET_POST_BY_ID_FAILURE,
    GET_POST_BY_ID_SUCCESS,
    GET_POST_COMMENT_LIST_FAILURE,
    GET_POST_COMMENT_LIST_SUCCESS,
    GET_POST_LIST_BY_USER_FAILURE,
    GET_POST_LIST_BY_USER_SUCCESS,
    GET_POST_LIST_FAILURE,
    GET_POST_LIST_SUCCESS, LIKE_POST_FAILURE, LIKE_POST_SUCCESS,
    REPOST_POST_FAILURE,
    REPOST_POST_SUCCESS, USER_BOOKMARK_POST_FAILURE,
    USER_BOOKMARK_POST_SUCCESS,
    USER_LIKE_POST_FAILURE,
    USER_LIKE_POST_SUCCESS
} from "./ActionType";

export const getAllPosts = () => async (dispatch) => {
    try {
        const {data} = await api.get('/api/posts');
        console.log("Найдены посты: ", data);
        dispatch({type: GET_POST_LIST_SUCCESS, payload: data})
    } catch (error) {
        console.log("getAllPosts ошибка: ", error);
        dispatch({type: GET_POST_LIST_FAILURE, payload: error.message});
    }

}

export const getAllPostsByUser = (userId) => async (dispatch) => {
    try {
        const {data} = await api.get(`/api/posts/user/${userId}`);
        console.log("Найдены посты пользователя: ", data);
        dispatch({type: GET_POST_LIST_BY_USER_SUCCESS, payload: data})
    } catch (error) {
        console.log("getAllPostsByUser ошибка: ", error);
        dispatch({type: GET_POST_LIST_BY_USER_FAILURE, payload: error.message});
    }

}

export const findPostByLikesContainsUser = (userId) => async (dispatch) => {
    try {
        const {data} = await api.get(`/api/posts/user/${userId}/likes`);
        console.log("Пользователю понравился пост: ", data);
        dispatch({type: USER_LIKE_POST_SUCCESS, payload: data})
    } catch (error) {
        console.log("findPostByLikesContainsUser ошибка: ", error);
        dispatch({type: USER_LIKE_POST_FAILURE, payload: error.message});
    }

}
export const findPostByBookmarksContainsUser = () => async (dispatch) => {
    try {
        const {data} = await api.get(`/api/posts/user/bookmarks`);
        console.log("Пользователю понравился пост: ", data);
        dispatch({type: USER_BOOKMARK_POST_SUCCESS, payload: data})
    } catch (error) {
        console.log("findPostByLikesContainsUser ошибка: ", error);
        dispatch({type: USER_BOOKMARK_POST_FAILURE, payload: error.message});
    }

}
export const findPostByRepliesContainsUser = (userId) => async (dispatch) => {
    try {
        const {data} = await api.get(`/api/posts/user/${userId}/replies`);
        console.log("Посты на которые ответил пользователь: ", data);
        dispatch({type: GET_POST_COMMENT_LIST_SUCCESS, payload: data})
    } catch (error) {
        console.log("findPostByRepliesContainsUser ошибка: ", error);
        dispatch({type: GET_POST_COMMENT_LIST_FAILURE, payload: error.message});
    }

}
export const findPostByPostId = (postId) => async (dispatch) => {
    try {
        const {data} = await api.get(`/api/posts/${postId}`);
        console.log("Найден следующий пост: ", data);
        dispatch({type: GET_POST_BY_ID_SUCCESS, payload: data})
    } catch (error) {
        console.log("findPostByPostId ошибка: ", error);
        dispatch({type: GET_POST_BY_ID_FAILURE, payload: error.message});
    }

}

export const addPost = (postData) => async (dispatch) => {
    try {
        const {data} = await api.post('/api/posts', postData);
        console.log("Опубликован пост: ", data);
        dispatch({type: ADD_POST_SUCCESS, payload: data})
    } catch (error) {
        console.log("addPost ошибка: ", error);
        dispatch({type: ADD_POST_FAILURE, payload: error.message});
    }
}
export const addComment = (commentData) => async (dispatch) => {
    try {
        const {data} = await api.post('/api/posts/reply', commentData);
        console.log("Опубликован комментарий: ", data);
        dispatch({type: ADD_POST_COMMENT_SUCCESS, payload: data})
    } catch (error) {
        console.log("addPost ошибка: ", error);
        dispatch({type: ADD_POST_COMMENT_FAILURE, payload: error.message});
    }
}
export const likePost = (postId) => async (dispatch) => {
    try {
        const {data} = await api.post(`/api/${postId}/like`);
        console.log("Пользователю понравился пост: ", data);
        dispatch({type: LIKE_POST_SUCCESS, payload: data})
    } catch (error) {
        console.log("likePost ошибка: ", error);
        dispatch({type: LIKE_POST_FAILURE, payload: error.message});
    }
}

export const bookmark = (postId) => async (dispatch) => {
    try {
        const {data} = await api.put(`/api/posts/${postId}/save`);
        console.log("Пользователь запомнил пост: ", data);
        dispatch({type: BOOKMARK_POST_SUCCESS, payload: data})
    } catch (error) {
        console.log("BookmarkPost ошибка: ", error);
        dispatch({type: BOOKMARK_POST_FAILURE, payload: error.message});
    }
}
// export const unbookmark = (postId) => async (dispatch) => {
//     try {
//         const {data} = await api.delete(`/api/bookmarks/${postId}`);
//         console.log("Пользователь забыл пост: ", data);
//         dispatch({type: BOOKMARK_POST_SUCCESS, payload: data})
//     } catch (error) {
//         console.log("BookmarkPost ошибка: ", error);
//         dispatch({type: BOOKMARK_POST_FAILURE, payload: error.message});
//     }
// }

export const sharePost = (postId) => async (dispatch) => {
    try {
        const {data} = await api.put(`/api/${postId}/share`);
        console.log("Пользователю поделился постом: ", data);
        dispatch({type: USER_LIKE_POST_SUCCESS, payload: data})
    } catch (error) {
        console.log("sharePost ошибка: ", error);
        dispatch({type: USER_LIKE_POST_FAILURE, payload: error.message});
    }
}

export const repost = (postId) => async (dispatch) => {
    try {
        const {data} = await api.put(`/api/posts/${postId}/repost`);
        console.log("Репост поста: ", data);
        dispatch({type: REPOST_POST_SUCCESS, payload: data})
    } catch (error) {
        console.log("repost ошибка: ", error);
        dispatch({type: REPOST_POST_FAILURE, payload: error.message});
    }
}

export const deletePost = (postId) => async (dispatch) => {
    try {
        const {data} = await api.delete(`/api/posts/${postId}`);
        console.log("Удалён пост: ", data);
        dispatch({type: DELETE_POST_SUCCESS, payload: postId})
    } catch (error) {
        console.log("deletePost ошибка: ", error);
        dispatch({type: DELETE_POST_FAILURE, payload: error.message});
    }
}
