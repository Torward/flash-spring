import {applyMiddleware, combineReducers, legacy_createStore} from "redux";
import {thunk} from "redux-thunk";

export const rootReducers = combineReducers({


})
export const store = legacy_createStore(rootReducers, applyMiddleware(thunk));