import {
    FOLLOW_USER_FAILURE,
    FOLLOW_USER_REQUEST,
    FOLLOW_USER_SUCCESS, GET_USER_BY_ID_FAILURE,
    GET_USER_BY_ID_REQUEST,
    GET_USER_BY_ID_SUCCESS, UPDATE_USER_FAILURE,
    UPDATE_USER_REQUEST, UPDATE_USER_SUCCESS
} from "./ActionType";

const initialState = {
    isAuthenticated: false,
    user: null,
    loading: false,
    error: null,
    jwt: null,
}
export const userReducer = (state = initialState, action) => {
    switch (action.type) {
        case UPDATE_USER_REQUEST:
        case GET_USER_BY_ID_REQUEST:
        case FOLLOW_USER_REQUEST:
            return {...state, loading: true, error: null}

        case GET_USER_BY_ID_SUCCESS:
            return {...state, loading: false, error: null, getUser: action.payload}
        case FOLLOW_USER_SUCCESS:
            return {...state, loading: false, error: null, followUser: action.payload}
        case UPDATE_USER_SUCCESS:
            return {...state, loading: false, error: null, user: action.payload, updateUser: true}

        case UPDATE_USER_FAILURE:
        case GET_USER_BY_ID_FAILURE:
        case FOLLOW_USER_FAILURE:
            return {...state, loading: false, error: action.payload}

        default:
            return state;
    }
}


