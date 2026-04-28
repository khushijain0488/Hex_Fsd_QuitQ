import axios from "axios";
export const GET_ALL_USER="GET_ALL_USER"
export const GET_USER_BY_ID="GET_USER_BY_ID"
export const GET_ALL_PRODUCTS="GET_ALL_PRODUCTS"
export const getAllUser=()=>{
    return async(dispatch)=>{
        const response=await axios.get("http://localhost:8080/users/get-all",{
            headers:{
                "Authorization":`Bearer `+localStorage.getItem("token")
            }
        })
dispatch({
    type:GET_ALL_USER,
    payload:response.data
})
    }
}
export const getUserById=(Id)=>{
    return async(dispatch)=>{
        const response=await axios.get(`http://localhost:8080/users/api/v1/getUserById/${Id}`,{
            headers:{
                "Authorization":`Bearer `+localStorage.getItem("token")
            }
        })
        dispatch({
            type:GET_USER_BY_ID,
            payload:response.data
        })
    }
}
export const getAllProducts=()=>{
return async(dispatch)=>{
    const response=await axios.get("http://localhost:8080/products/get")
    dispatch({
        type:GET_ALL_PRODUCTS,
        payload:response.data
    })
}
}