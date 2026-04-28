import React from 'react'
import { useState } from 'react'
import axios from 'axios'
import { Link, useNavigate } from 'react-router-dom'
const LoginForm = () => {
    const [userName, setuserName] = useState("")
    const [password, setpassword] = useState("")
    const ApiPath="http://localhost:8080/auth/login"
    const GetLoggedInUser="http://localhost:8080/users/api/v2/loggedInUser"
    const navigate=useNavigate();
    const ProcessLogin=async(e)=>{
        e.preventDefault();
// step-1 creating the random string or genrating
const encodedString=window.btoa(userName+ ":"+ password)
// step-2 passing in headers
const config={
    headers:{
        "Authorization": `Basic ` + encodedString
    }
}
// console.log(encodedString);
const response= await axios.get(ApiPath,config);
localStorage.setItem("token",response.data.token)
// getting info of logged in user 
const userInfo=await axios.get(GetLoggedInUser,{
  headers:{
    "Authorization":`Bearer `+localStorage.getItem("token")
  }
})
const FetchedRole=userInfo.data.role
switch(FetchedRole){

  case "USER":
    navigate("/user-dashboard")
    break
  case "SELLER":
    navigate("/seller-dashboard")
    break
  case "ADMIN":
    navigate("/admin-dashboard")

}



    }
  return (
    <div className='container mt-5'>
   <div className="d-flex justify-content-center align-items-center" style={{minHeight: '100vh', background: '#f5f5f5'}}>
  <div className="bg-white p-4 rounded-3 shadow-sm" style={{width: '100%', maxWidth: '380px'}}>
    
    <h4 className="fw-semibold mb-1">QuitQ</h4>
    <p className="text-muted mb-3" style={{fontSize: '14px'}}>Login or create your account</p>

    <ul className="nav nav-underline mb-4">
      <li className="nav-item">
        <a className="nav-link active" href="#">Login</a>
      </li>
      <li className="nav-item">
        <a className="nav-link text-muted" href="/signup">Register</a>
      </li>
    </ul>

    <form onSubmit={(e) => ProcessLogin(e)}>
      <div className="mb-3">
        <label className="form-label text-muted" style={{fontSize: '13px'}}>Username</label>
        <input
          type="text"
          className="form-control"
          placeholder="johndoe_#"
          onChange={(e) => setuserName(e.target.value)}
        />
      </div>

      <div className="mb-2">
        <label className="form-label text-muted" style={{fontSize: '13px'}}>Password</label>
        <input
          type="password"
          className="form-control"
          placeholder="Enter password"
          onChange={(e) => setpassword(e.target.value)}
        />
      </div>

     

      <button
        type="submit"
        className="btn w-100 fw-semibold text-white"
        style={{background: '#E8601A', letterSpacing: '0.5px'}}
      >
        LOGIN
      </button>
    </form>

    <p className="text-center text-muted mt-3" style={{fontSize: '13px'}}>
      New to QuitQ? <Link to={"/signup"}>create account</Link>
    </p>

  </div>
</div>
     
    </div>
  )
}

export default LoginForm
