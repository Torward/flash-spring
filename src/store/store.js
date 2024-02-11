import {applyMiddleware, combineReducers, legacy_createStore} from "redux";
import {thunk} from "redux-thunk";
import state from "../redux/state";

export const rootReducers = combineReducers({
    // user: (state = {
    //     user: null,
    //     isAuthenticated: false,
    //     isAuthenticating: false,
    //     isAuthenticatingError: null,
    //     isAuthenticatingSuccess: false,
    //     isAuthenticatingSuccessMessage: null,
    //     isAuthenticatingSuccessMessageColor: null,
    //     isAuthenticatingSuccessMessageDuration: null,
    //     isAuthenticatingSuccessMessageIcon: null,
    //     isAuthenticatingSuccessMessageTitle: null,
    // }),
})
export const store = legacy_createStore(rootReducers, applyMiddleware(thunk));