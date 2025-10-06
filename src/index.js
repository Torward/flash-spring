
import './index.css';
import reportWebVitals from './reportWebVitals';
import state from "./redux/state";
import {rerenderEntireTree} from "./redux/render";


rerenderEntireTree(state)
reportWebVitals();
if (module.hot) {
  module.hot.accept('./redux/render', () => {
    rerenderEntireTree(state)
  })
}

