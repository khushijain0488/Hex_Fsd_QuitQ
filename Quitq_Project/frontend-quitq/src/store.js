import  {createStore, applyMiddleware ,combineReducers} from 'redux'
import {thunk} from 'redux-thunk'
import AdminReducer from './Redux/Reducer/AdminReducer'
const reducers = combineReducers({
AdminReducer:AdminReducer
 })
export const store=createStore(reducers,applyMiddleware(thunk))