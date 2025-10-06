import {api} from "../../config/api";
import {
    DELETE_USER_FAILURE,
    DELETE_USER_SUCCESS,
    FOLLOW_USER_FAILURE,
    FOLLOW_USER_SUCCESS, GET_ALL_USERS_FAILURE, GET_ALL_USERS_SUCCESS,
    GET_USER_BY_ID_FAILURE,
    GET_USER_BY_ID_SUCCESS, UNFOLLOW_USER_FAILURE, UNFOLLOW_USER_SUCCESS,
    UPDATE_USER_FAILURE,
    UPDATE_USER_SUCCESS
} from "./ActionType";

export const getUserById = (userId) => async (dispatch) => {
    try {
        const {data} = await api.get(`/api/users/${userId}`);
        console.log("Найден пользователь: ", data);
        dispatch({type: GET_USER_BY_ID_SUCCESS, payload: data})
    } catch (error) {
        console.log("getUserById ошибка: ", error);
        dispatch({type: GET_USER_BY_ID_FAILURE, payload: error.message});
    }
}
export const updateUser = (userData) => async (dispatch) => {
    try {
        const {data} = await api.put(`/api/users`, userData);
        console.log("Данные пользователя дополнены: ", data);
        dispatch({type: UPDATE_USER_SUCCESS, payload: data})
    } catch (error) {
        console.log("updateUser ошибка: ", error);
        dispatch({type: UPDATE_USER_FAILURE, payload: error.message});
    }
}

export const followUser = (userId) => async (dispatch) => {
    try {
        const {data} = await api.put(`/api/users/${userId}/follow`);
        console.log("Отслеживается пользователь: ", data);
        dispatch({type: FOLLOW_USER_SUCCESS, payload: data})
    } catch (error) {
        console.log("followUser ошибка: ", error);
        dispatch({type: FOLLOW_USER_FAILURE, payload: error.message});
    }
}

export const unFollowUser = (userId) => async (dispatch) => {
    try {
        const {data} = await api.put(`/api/users/${userId}/unfollow`);
        console.log("Пользователь более не отслеживается: ", data);
        dispatch({type: UNFOLLOW_USER_SUCCESS, payload: data})
    } catch (error) {
        console.log("unFollowUser ошибка: ", error);
        dispatch({type: UNFOLLOW_USER_FAILURE, payload: error.message});
    }
}

export const getUsers = (page) => async (dispatch) => {
    try {
        const {data} = await api.get(`/api/users?page=${page}`);
        console.log("Получены пользователи: ", data);
        dispatch({type: GET_ALL_USERS_SUCCESS, payload: data})
    } catch (error) {
        console.log("getUsers ошибка: ", error);
        dispatch({type: GET_ALL_USERS_FAILURE, payload: error.message});
    }
}

export const getUsersBySearch = (search, page) => async (dispatch) => {
    try {
        const {data} = await api.get(`/api/users/search?search=${search}&page=${page}`);
        console.log("Получены пользователи: ", data);
        dispatch({type: GET_ALL_USERS_SUCCESS, payload: data})
    } catch (error) {
        console.log("getUsersBySearch ошибка: ", error);
        dispatch({type: GET_ALL_USERS_FAILURE, payload: error.message});
    }
}

export const getUsersByFollowers = (userId, page) => async (dispatch) => {
    try {
        const {data} = await api.get(`/api/users/${userId}/followers?page=${page}`);
        console.log("Получены пользователи: ", data);
        dispatch({type: GET_ALL_USERS_SUCCESS, payload: data})
    } catch (error) {
        console.log("getUsersByFollowers ошибка: ", error);
        dispatch({type: GET_ALL_USERS_FAILURE, payload: error.message});
    }
}

export const getUsersByFollowing = (userId, page) => async (dispatch) => {
    try {
        const {data} = await api.get(`/api/users/${userId}/following?page=${page}`);
        console.log("Получены пользователи: ", data);
        dispatch({type: GET_ALL_USERS_SUCCESS, payload: data})
    } catch (error) {
        console.log("getUsersByFollowing ошибка: ", error);
        dispatch({type: GET_ALL_USERS_FAILURE, payload: error.message});
    }
}

export const getUsersByFollowings = (userId, page) => async (dispatch) => {
    try {
        const {data} = await api.get(`/api/users/${userId}/followings?page=${page}`);
        console.log("Получены пользователи: ", data);
        dispatch({type: GET_ALL_USERS_SUCCESS, payload: data})
    } catch (error) {
        console.log("getUsersByFollowings ошибка: ", error);
        dispatch({type: GET_ALL_USERS_FAILURE, payload: error.message});
    }
}

export const deleteUser = (userId) => async (dispatch) => {
    try {
        const {data} = await api.delete(`/api/users/${userId}`);
        console.log("Пользователь удалён: ", data);
        dispatch({type: DELETE_USER_SUCCESS, payload: userId})
    } catch (error) {
        console.log("deleteUser ошибка: ", error);
        dispatch({type: DELETE_USER_FAILURE, payload: error.message});
    }
}
