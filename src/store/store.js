import {applyMiddleware, combineReducers, legacy_createStore} from "redux";
import {thunk} from "redux-thunk";
import {authReducer} from "./Auth/Reducer";
import {postReducer} from "./Post/Reducer";
import {userReducer} from "./User/Reducer";

export const rootReducers = combineReducers({
   auth: authReducer,
   post: postReducer,
   user: userReducer
})
export const store = legacy_createStore(rootReducers, applyMiddleware(thunk));