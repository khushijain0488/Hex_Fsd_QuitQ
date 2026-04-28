import axios from 'axios'
import React from 'react'
import { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
const SignUp = () => {
  const navigate=useNavigate();
    const [userName, setuserName] = useState("")
    const [email, setEmail] = useState("")
    const [password, setPassword] = useState("")
    const [role, setRole] = useState("")
    const [contactNumber, setContactNumber] = useState("")
    const [address, setAddress] = useState("")
    const [gender, setGender] = useState("")
    const ApiPath="http://localhost:8080/users/register"
   const ProcessSignUp=async(e)=>{
e.preventDefault();
await axios.post(ApiPath,{
    "username":userName,
    "email":email,
    "password":password,
    "role":role,
    "contactNumber":contactNumber,
    "address":address,
    "gender":gender
})
navigate("/login")



   }
  return (
    <div className='container'>
 <form onSubmit={(e) => ProcessSignUp(e)}>
  <div className="card" style={{ maxWidth: 520, margin: "2rem auto" }}>
    <div className="card-header bg-primary text-white">
      <h5 className="mb-0">Create an account</h5>
    </div>
    <div className="card-body">
      <div className="row g-3">
        <div className="col-12">
          <label className="form-label">Username</label>
          <input type="text" className="form-control" onChange={(e) => setuserName(e.target.value)} />
        </div>
        <div className="col-12">
          <label className="form-label">Email</label>
          <input type="email" className="form-control" onChange={(e) => setEmail(e.target.value)} />
        </div>
        <div className="col-12">
          <label className="form-label">Password</label>
          <input type="password" className="form-control" onChange={(e) => setPassword(e.target.value)} />
        </div>
        <div className="col-sm-6">
          <label className="form-label">Role</label>
          <select className="form-select" onChange={(e) => setRole(e.target.value)}>
            <option value="">Select role</option>
            <option>USER</option>
            <option>SELLER</option>
            <option>ADMIN</option>
          </select>
        </div>
        <div className="col-sm-6">
          <label className="form-label">Contact Number</label>
          <input type="tel" className="form-control" onChange={(e) => setContactNumber(e.target.value)} />
        </div>
        <div className="col-12">
          <label className="form-label">Address</label>
          <textarea className="form-control" rows={2} onChange={(e) => setAddress(e.target.value)} />
        </div>
        <div className="col-12">
          <label className="form-label d-block">Gender</label>
          <div className="d-flex gap-2">
            {["Male", "Female"].map((g) => (
              <div key={g} className="form-check border rounded px-3 py-2 flex-fill">
                <input className="form-check-input" type="radio" name="gender" value={g} onChange={(e) => setGender(e.target.value)} id={g} />
                <label className="form-check-label" htmlFor={g}>{g}</label>
              </div>
            ))}
          </div>
        </div>
      </div>
      <hr />
      <button type="submit" className="btn btn-primary w-100">Create account</button>
    </div>
    <div className='container mt-2'>
        If you have account? <Link to={"/login"}>Login</Link>
    </div>
  </div>
</form>    </div>
  )
}

export default SignUp
