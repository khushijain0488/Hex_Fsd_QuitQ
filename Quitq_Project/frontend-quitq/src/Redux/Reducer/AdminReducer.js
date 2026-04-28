import { GET_ALL_USER } from "../Action/AdminAction";
import { GET_USER_BY_ID } from "../Action/AdminAction";
import { GET_ALL_PRODUCTS } from "../Action/AdminAction";
const initialState={
    user:{},
    users:[],
    products:[]
}
const AdminReducer=(state=initialState,action)=>{
    switch(action.type){
        case GET_ALL_USER:
            return{
                ...state,
                users:action.payload
            }
        case GET_USER_BY_ID:
            return {
                ...state,
                user:action.payload
            }
        case GET_ALL_PRODUCTS:
            return{
                ...state,
                products:action.payload
            }
        default:
            return state
    }
}
export default AdminReducer