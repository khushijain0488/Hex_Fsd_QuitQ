import React, { useEffect, useState } from 'react'
import { useDispatch, useSelector } from "react-redux"
import { getAllUser } from '../Redux/Action/AdminAction'
import { useNavigate } from 'react-router-dom'

const AdminDashboard = () => {
  const [filter, setFilter] = useState('ALL')
  const [search, setSearch] = useState('')
  const [currentPage, setCurrentPage] = useState(1)
  const usersPerPage = 8
const navigate=useNavigate()
  const dispatch = useDispatch()
  const { users = [] } = useSelector(state => state.AdminReducer)

  useEffect(() => {
    dispatch(getAllUser())
  }, [dispatch])

  useEffect(() => {
    setCurrentPage(1)
  }, [filter, search])

  const filtered = users.filter(u => {
    const role = u.authority?.replace(/[\[\]]/g, '')
    const matchRole = filter === 'ALL' || role === filter
    const matchSearch =
      u.email.toLowerCase().includes(search.toLowerCase()) ||
      (u.name && u.name.toLowerCase().includes(search.toLowerCase()))
    return matchRole && matchSearch
  })

  const totalPages = Math.ceil(filtered.length / usersPerPage)
  const paginated = filtered.slice((currentPage - 1) * usersPerPage, currentPage * usersPerPage)
  const count = (role) => users.filter(u => u.authority?.replace(/[\[\]]/g, '') === role).length

  const getRoleBadge = (role) => {
    const map = {
      ADMIN: 'danger',
      SELLER: 'warning',
      USER: 'primary',
    }
    return map[role] || 'secondary'
  }
const handleDetailNavigation=(Id)=>{
  navigate(`/userdetail/${Id}`)
}
const handleAddCategory=()=>{
  navigate("/add-category")
}
const handleCategoriesNavigation=()=>{
  navigate("/allcategory")
}
  return (
    <div style={{ fontFamily: "'Poppins', sans-serif" }}>

      {/* Navbar */}
      <nav className="navbar navbar-expand-lg" style={{ background: '#2874f0' }}>
        <div className="container-fluid px-4">
          <span className="navbar-brand fw-bold text-white fs-5">
            
            QuitQ <small className="fst-italic text-warning" style={{ fontSize: '10px' }}>Admin Panel</small>
          </span>
          <div className="d-flex align-items-center gap-2">
            <span onClick={()=>handleCategoriesNavigation()} style={{cursor: 'pointer'}}>All categories</span>
            <div className="rounded-pill px-3 py-1 d-flex align-items-center gap-2"
              style={{ background: 'rgba(255,255,255,0.15)', border: '1px solid rgba(255,255,255,0.25)' }}>
              <div className="rounded-circle d-flex align-items-center justify-content-center fw-bold text-white"
                style={{ width: '26px', height: '26px', background: '#ff9f00', fontSize: '11px' }}>A</div>
              <span className="text-white" style={{ fontSize: '12px' }}>Admin</span>
            </div>
          </div>
        </div>
      </nav>

      <div className="d-flex" style={{ minHeight: 'calc(100vh - 56px)', background: '#f1f3f6' }}>

        {/* Sidebar */}
        <div className="bg-white border-end" style={{ width: '210px', flexShrink: 0 }}>
          <div className="py-3">
            {[
              { icon: '👥', label: 'Users', active: true },
         
              { icon: '🛒', label: 'Orders' }
            ].map(item => (
              <div key={item.label}
           
                className="d-flex align-items-center gap-2 px-3 py-2"
                style={{
                  fontSize: '13px',
                  fontWeight: item.active ? 600 : 500,
                  color: item.active ? '#2874f0' : '#212121',
                  background: item.active ? '#e8f0fe' : 'transparent',
                  borderLeft: item.active ? '3px solid #2874f0' : '3px solid transparent',
                  cursor: 'pointer'
                }}>
                  <div onClick={()=>navigate("/allorder")}>
                <span>{item.icon}</span> {item.label}
                </div>
              </div>
            ))}
          </div>
        </div>

        {/* Main Content */}
        <div className="flex-fill p-4">

          {/* Page Header */}
          <div className="d-flex justify-content-between align-items-start mb-4">
            <div>
              <h5 className="fw-bold mb-1" style={{ color: '#212121' }}>User Management</h5>
              <small style={{ color: '#878787' }}>Manage all registered accounts on QuitQ</small>
          
            </div>
          <button onClick={()=>handleAddCategory()}>Add Category</button>  
          </div>

          {/* Stats Cards */}
          <div className="row g-3 mb-4">
            {[
              { icon: '👥', label: 'Total Users', value: users.length, color: '#2874f0', bg: '#e8f0fe' },
              { icon: '🛡️', label: 'Admins', value: count('ADMIN'), color: '#ff6161', bg: '#ffebee' },
              { icon: '🏪', label: 'Sellers', value: count('SELLER'), color: '#e68900', bg: '#fff8e1' },
              { icon: '🙋', label: 'Customers', value: count('USER'), color: '#388e3c', bg: '#e8f5e9' },
            ].map(stat => (
              <div className="col-md-3" key={stat.label}>
                <div className="card border-0 shadow-sm h-100">
                  <div className="card-body d-flex align-items-center gap-3">
                    <div className="rounded d-flex align-items-center justify-content-center"
                      style={{ width: '48px', height: '48px', background: stat.bg, fontSize: '22px', flexShrink: 0 }}>
                      {stat.icon}
                    </div>
                    <div>
                      <div style={{ fontSize: '11px', color: '#878787', fontWeight: 500 }}>{stat.label}</div>
                      <div style={{ fontSize: '26px', fontWeight: 700, color: stat.color, lineHeight: 1 }}>{stat.value}</div>
                    </div>
                  </div>
                </div>
              </div>
            ))}
          </div>

          {/* Search + Filter */}
          <div className="card border-0 shadow-sm mb-0" style={{ borderRadius: '4px 4px 0 0' }}>
            <div className="card-body d-flex align-items-center gap-3 flex-wrap py-2 px-3">
              <input
                type="text"
                className="form-control form-control-sm"
                placeholder="🔍  Search by email or name..."
                value={search}
                onChange={e => setSearch(e.target.value)}
                style={{ maxWidth: '280px', fontSize: '13px' }}
              />
              <div className="d-flex gap-2">
                {['ALL', 'ADMIN', 'SELLER', 'USER'].map(role => {
                  const activeColors = {
                    ALL: 'btn-primary',
                    ADMIN: 'btn-danger',
                    SELLER: 'btn-warning',
                    USER: 'btn-primary',
                  }
                  const inactiveColors = 'btn-outline-secondary'
                  return (
                    <button key={role}
                      className={`btn btn-sm rounded-pill fw-semibold ${filter === role ? activeColors[role] : inactiveColors}`}
                      style={{ fontSize: '11px', padding: '4px 14px' }}
                      onClick={() => setFilter(role)}>
                      {role}
                    </button>
                  )
                })}
              </div>
              <span className="ms-auto text-muted" style={{ fontSize: '12px' }}>
                {filtered.length} users found
              </span>
            </div>
          </div>

          {/* Table */}
          <div className="card border-0 shadow-sm" style={{ borderRadius: '0 0 4px 4px' }}>
            <div className="table-responsive">
              <table className="table table-hover align-middle mb-0" style={{ fontSize: '13px' }}>
                <thead style={{ background: '#f8f9fa' }}>
                  <tr>
                    <th className="text-muted fw-semibold ps-3" style={{ fontSize: '11px', letterSpacing: '0.8px' }}>#ID</th>
                    <th className="text-muted fw-semibold" style={{ fontSize: '11px', letterSpacing: '0.8px' }}>EMAIL</th>
                    <th className="text-muted fw-semibold" style={{ fontSize: '11px', letterSpacing: '0.8px' }}>NAME</th>
                    <th className="text-muted fw-semibold" style={{ fontSize: '11px', letterSpacing: '0.8px' }}>ROLE</th>
                    <th className="text-muted fw-semibold text-end pe-3" style={{ fontSize: '11px', letterSpacing: '0.8px' }}>ACTIONS</th>
                  </tr>
                </thead>
                <tbody>
                  {paginated.length === 0 ? (
                    <tr>
                      <td colSpan={5} className="text-center text-muted py-5">No users found.</td>
                    </tr>
                  ) : paginated.map(u => {
                    const roleStyle = {
                      ADMIN:  { color: '#ff6161', bg: '#ffebee' },
                      SELLER: { color: '#e68900', bg: '#fff8e1' },
                      USER:   { color: '#2874f0', bg: '#e8f0fe' },
                    }
                    const role = u.authority?.replace(/[\[\]]/g, '')  // ✅ "[ADMIN]" → "ADMIN"
                    const rc = roleStyle[role] ?? { color: '#757575', bg: '#f5f5f5' }
                    return (
                      <tr key={u.id}>
                        <td className="ps-3 text-muted" style={{ fontFamily: 'monospace', fontSize: '12px' }}>#{u.id}</td>
                        <td>
                          <div className="d-flex align-items-center gap-2">
                            <div className="rounded-circle d-flex align-items-center justify-content-center fw-bold"
                              style={{ width: '34px', height: '34px', background: rc.bg, color: rc.color, fontSize: '13px', flexShrink: 0 }}>
                              {u.email.charAt(0).toUpperCase()}
                            </div>
                            <span className="fw-medium">{u.email}</span>
                          </div>
                        </td>
                        <td className="text-muted" style={{ fontFamily: 'monospace', fontSize: '12px' }}>
                          {u.name || '—'}  
                        </td>
                        <td>
                          <span className={`badge bg-${getRoleBadge(role)} bg-opacity-10 fw-semibold`}
                            style={{ color: rc.color, padding: '4px 10px', fontSize: '11px' }}>
                            ● {role}  
                          </span>
                        </td>
                        <td className="text-end pe-3">
                          <button className="btn btn-sm btn-outline-primary me-1" style={{ fontSize: '11px', padding: '3px 12px' }} onClick={()=>handleDetailNavigation(u.id)}>Detail</button>
                          
                        </td>
                      </tr>
                    )
                  })}
                </tbody>
              </table>
            </div>
          </div>

          {/* Pagination */}
          {totalPages > 1 && (
            <div className="d-flex justify-content-between align-items-center mt-3">
              <small className="text-muted">
                Showing {(currentPage - 1) * usersPerPage + 1}–{Math.min(currentPage * usersPerPage, filtered.length)} of {filtered.length} users
              </small>
              <nav>
                <ul className="pagination pagination-sm mb-0">
                  <li className={`page-item ${currentPage === 1 ? 'disabled' : ''}`}>
                    <button className="page-link" onClick={() => setCurrentPage(p => p - 1)}>‹ Prev</button>
                  </li>
                  {Array.from({ length: totalPages }, (_, i) => i + 1).map(page => (
                    <li key={page} className={`page-item ${currentPage === page ? 'active' : ''}`}>
                      <button className="page-link"
                        style={currentPage === page ? { background: '#2874f0', borderColor: '#2874f0' } : {}}
                        onClick={() => setCurrentPage(page)}>
                        {page}
                      </button>
                    </li>
                  ))}
                  <li className={`page-item ${currentPage === totalPages ? 'disabled' : ''}`}>
                    <button className="page-link" onClick={() => setCurrentPage(p => p + 1)}>Next ›</button>
                  </li>
                </ul>
              </nav>
            </div>
          )}

        </div>
      </div>
    </div>
  )
}

export default AdminDashboard