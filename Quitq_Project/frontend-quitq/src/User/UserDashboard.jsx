import React from 'react'
import Navbar from '../HomeFolder/Navbar'
import { Outlet, useNavigate } from 'react-router-dom'

 
const UserDashboard = () => {
  const navigate=useNavigate()
  const handleMyOrder=()=>{
    navigate("/user-dashboard/myorders")
  }
  const handleMyCart=()=>{
    navigate("/user-dashboard/mycarts")
  }
  return (
    <div>
   <Navbar/>
   {/* button */}
   <button onClick={()=>handleMyOrder()}>My Order</button>
   <button onClick={()=>handleMyCart()}>MyCarts</button>
    <Outlet/>
    </div>
  )
}

export default UserDashboard
