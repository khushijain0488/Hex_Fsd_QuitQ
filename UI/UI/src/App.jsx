import React from 'react'
import UserList from './Component/UserList'
import { Link, Outlet } from 'react-router-dom'
const App = () => {
  return (
    <div className='container mb-10'>
<nav className="navbar navbar-expand navbar-light bg-light px-4">
  <span className="navbar-brand fw-bold text-dark">User Dashboard</span>
  <div className="navbar">
    <Link
      to="/users"
      className="nav-link text-dark fw-medium text-decoration-none"
    >
      User List
    </Link>
    <Link
      to="/add-user"
      className="nav-link text-dark fw-medium text-decoration-none ms-3"
    >
      Add User
    </Link>
  </div>
</nav>

<Outlet/>

    </div>
  )
}

export default App
