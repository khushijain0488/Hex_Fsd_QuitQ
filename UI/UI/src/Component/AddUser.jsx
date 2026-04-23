import React, { useState } from 'react'
import axios from 'axios'
import { useNavigate } from 'react-router-dom'


const AddUser = () => {
  const ApiPath = "https://jsonplaceholder.typicode.com/users"
  const [name, setName] = useState('')
  const [email, setEmail] = useState('')
  const [phone, setPhone] = useState('')
  const [companyName, setCompanyName] = useState('')
const navigate=useNavigate()
  const handleSubmit = async (e) => {
    e.preventDefault()
    const response = await axios.post(ApiPath, {
      name,
      email,
      phone,
      company: { name: companyName }
    })
    console.log("Successfully registered:", response.data)
    navigate("/users")
  }

  return (
    <div className="min-vh-100 bg-light d-flex align-items-center justify-content-center">
      <div className="card shadow-sm p-4" style={{ width: '100%', maxWidth: '440px' }}>

        <h4 className="fw-semibold mb-1">Add New User</h4>
        <p className="text-muted small mb-4">Fill in the details to register a user.</p>

        <form onSubmit={handleSubmit}>
          <div className="mb-3">
            <label className="form-label fw-medium">Full Name</label>
            <input type="text" className="form-control" 
              onChange={(e) => setName(e.target.value)} />
          </div>

          <div className="mb-3">
            <label className="form-label fw-medium">Email Address</label>
            <input type="email" className="form-control" 
              onChange={(e) => setEmail(e.target.value)} />
          </div>

          <div className="mb-3">
            <label className="form-label fw-medium">Phone Number</label>
            <input type="tel" className="form-control" 
              onChange={(e) => setPhone(e.target.value)} />
          </div>

          <div className="mb-4">
            <label className="form-label fw-medium">Company Name</label>
            <input type="text" className="form-control" 
              onChange={(e) => setCompanyName(e.target.value)} />
          </div>

          <button type="submit" className="btn btn-primary w-100">
            Register User
          </button>
        </form>

      </div>
    </div>
  )
}

export default AddUser