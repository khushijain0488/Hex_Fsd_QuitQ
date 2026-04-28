import React, { useState } from 'react'
import { useEffect } from 'react'
import axios from 'axios'
import { useNavigate } from 'react-router-dom'
const Navbar = () => {
  // get logged in user
  const[user,setUser]=useState({})
  const ApiPath="http://localhost:8080/users/api/v2/loggedInUser"
  const navigate=useNavigate()
  useEffect(() => {
const FetchedUser=async()=>{
  const config={
    headers:{
      "Authorization":`Bearer `+localStorage.getItem("token")
    }
  }
const response=await axios.get(ApiPath,config)
console.log(response.data)
setUser(response.data)
}
FetchedUser()
  }, [])
  const HandleHomeNavigation=()=>{
    navigate("/")
  }
  
  return (
    <div>
      <nav style={{ background: '#1a4fa0', padding: '0 32px', height: '60px', display: 'flex', alignItems: 'center', justifyContent: 'space-between' }}>

      {/* Logo */}
      <a href="#" style={{ display: 'flex', alignItems: 'center', gap: '8px', textDecoration: 'none' }}>
        <div style={{ background: '#FFD700', borderRadius: '6px', padding: '5px 11px' }}>
          <span style={{ fontSize: '22px', fontWeight: '700', color: '#1a4fa0' }}>Q</span>
        </div>
        <div style={{ display: 'flex', flexDirection: 'column' }}>
          <span style={{ fontSize: '20px', fontWeight: '700', color: '#FFD700', letterSpacing: '-0.5px', lineHeight: 1 }}>QuitQ</span>
          <span style={{ fontSize: '10px', color: '#FFD700', fontWeight: '500', opacity: 0.8 }}>shop smart</span>
        </div>
      </a>

      {/* Right Side */}
      <div style={{ display: 'flex', alignItems: 'center', gap: '32px' }}>
        <a href="#" style={{ color: '#FFD700', textDecoration: 'none', fontSize: '14px', fontWeight: '500', borderBottom: '2px solid #FFD700' }}
        onClick={()=>HandleHomeNavigation()}>Home</a>
        <button style={{ background: '#FFD700', color: '#1a4fa0', border: 'none', borderRadius: '4px', padding: '8px 22px', fontSize: '14px', fontWeight: '700', cursor: 'pointer' }}>
     {user.username}
        </button>
      </div>

    </nav>
    </div>
  )
}

export default Navbar