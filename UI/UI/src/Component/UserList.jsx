import React, { useEffect } from 'react'
import axios from 'axios'
import { useState } from 'react'
import { Link } from 'react-router-dom'
const UserList = () => {
    const [users,setUsers]=useState([])
    const ApiPath="https://jsonplaceholder.typicode.com/users"
const handleDelete=async(userId)=>{
    setUsers(users.filter((u)=>!(u.id==userId)))
await axios.delete(`https://jsonplaceholder.typicode.com/users/${userId}`)
// updating ui



} 
    useEffect(() => {
        const FetchUser=async()=>{
const response=await axios.get(ApiPath)
console.log(response.data)
setUsers(response.data)
        }
FetchUser()
    }, [])


  return (
    <div>
<div>
    <h3>User Info</h3>
</div>
      <table className="table">
  <thead>
    <tr>
      <th scope="col">#</th>
      <th scope="col">Name</th>
      <th scope="col">Email</th>
      <th scope="col">Phone</th>
      <th scope="col">Company Name</th>
      <th scope="col">Action</th>
    </tr>
  </thead>
  <tbody>
    {
        users.map((u,index)=>(
    <tr key={index}>
     
      <td>{u.id}</td>
      <td>{u.name}</td>
      <td>{u.email}</td>
      <td>{u.phone}</td>
      <td>{u.company.name}</td>
      <td>
        <button type="button" className="btn btn-danger" onClick={()=>handleDelete(u.id)}>Delete</button>
      </td>
    </tr>
        ))
    }


  </tbody>
</table>
    </div>
  )
}

export default UserList
