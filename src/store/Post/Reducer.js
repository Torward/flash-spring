import {
    ADD_POST_COMMENT_FAILURE,
    ADD_POST_COMMENT_REQUEST,
    ADD_POST_COMMENT_SUCCESS,
    ADD_POST_FAILURE,
    ADD_POST_REQUEST,
    ADD_POST_SUCCESS, BOOKMARK_POST_FAILURE,
    BOOKMARK_POST_REQUEST,
    BOOKMARK_POST_SUCCESS,
    DELETE_POST_FAILURE,
    DELETE_POST_REQUEST,
    DELETE_POST_SUCCESS,
    GET_POST_BY_ID_FAILURE,
    GET_POST_BY_ID_REQUEST,
    GET_POST_BY_ID_SUCCESS,
    GET_POST_LIST_BY_USER_SUCCESS, GET_POST_LIST_REQUEST,
    GET_POST_LIST_SUCCESS,
    LIKE_POST_FAILURE,
    LIKE_POST_REQUEST,
    LIKE_POST_SUCCESS,
    REPOST_POST_FAILURE,
    REPOST_POST_REQUEST,
    REPOST_POST_SUCCESS,
    USER_BOOKMARK_POST_FAILURE,
    USER_BOOKMARK_POST_REQUEST,
    USER_BOOKMARK_POST_SUCCESS,
    USER_LIKE_POST_FAILURE,
    USER_LIKE_POST_REQUEST,
    USER_LIKE_POST_SUCCESS
} from "./ActionType";

const initialState = {
    loading: false,
    data: null,
    error: null,
    posts: [],
    post: null,
}
export const postReducer = (state = initialState, action) => {
    switch (action.type) {
        case ADD_POST_REQUEST:
        case GET_POST_LIST_REQUEST:
        case DELETE_POST_REQUEST:
        case USER_LIKE_POST_REQUEST:
        case LIKE_POST_REQUEST:
        case USER_BOOKMARK_POST_REQUEST:
        case BOOKMARK_POST_REQUEST:
        case REPOST_POST_REQUEST:
        case GET_POST_BY_ID_REQUEST:
            return {...state, loading: true, error: null}

        case ADD_POST_COMMENT_REQUEST:
            return {...state, loading: false, error: null}

        case ADD_POST_SUCCESS:
            return {...state, loading: false, error: null, posts: [...state.posts, action.payload]}

        case GET_POST_LIST_SUCCESS:
        case GET_POST_LIST_BY_USER_SUCCESS:
            return {...state, loading: false, error: null, posts: action.payload}

        case USER_LIKE_POST_SUCCESS:
            return {...state, loading: false, error: null, likedPosts: action.payload}

        case LIKE_POST_SUCCESS:
            return {...state, loading: false, error: null, like: action.payload}

        case USER_BOOKMARK_POST_SUCCESS:
            return {...state, loading: false, error: null, bookmarkedPosts: action.payload}

        case BOOKMARK_POST_SUCCESS:
            return {...state, loading: false, error: null, bookmark: action.payload}

        case DELETE_POST_SUCCESS:
            return {...state, loading: false, error: null, posts: state.posts.filter(post => post.id !== action.payload)}

        case GET_POST_BY_ID_SUCCESS:
        case ADD_POST_COMMENT_SUCCESS:
            return {...state, loading: false, error: null, post: action.payload}

        case REPOST_POST_SUCCESS:
            return {...state, loading: false, error: null, repost: action.payload}

        case ADD_POST_FAILURE:
        case DELETE_POST_FAILURE:
        case USER_LIKE_POST_FAILURE:
        case LIKE_POST_FAILURE:
        case USER_BOOKMARK_POST_FAILURE:
        case BOOKMARK_POST_FAILURE:
        case REPOST_POST_FAILURE:
        case GET_POST_BY_ID_FAILURE:
        case ADD_POST_COMMENT_FAILURE:
            return {...state, loading: false, error: action.payload}
        default:
            return state
    }
}