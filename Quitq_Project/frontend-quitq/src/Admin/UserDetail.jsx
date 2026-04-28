import React, { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { useParams } from 'react-router-dom'
import { getUserById } from '../Redux/Action/AdminAction'

const UserDetail = () => {
    const { id } = useParams()
    const dispatch = useDispatch()
    const { user } = useSelector(state => state.AdminReducer)

    useEffect(() => {
        dispatch(getUserById(id))
        console.log(user)
    }, [dispatch])

    return (
        <div className="container py-5">
            <div className="row justify-content-center">
                <div className="col-md-6">
                    <div className="card shadow-sm border-0">
                        <div className="card-header bg-primary text-white text-center py-4">
                            <div
                                className="rounded-circle bg-white d-inline-flex align-items-center justify-content-center mb-2"
                                style={{ width: 64, height: 64 }}
                            >
                                <span className="text-primary fs-3 fw-bold">
                                    {user.email ? user.email[0].toUpperCase() : 'U'}
                                </span>
                            </div>
                            <h5 className="mb-0 mt-1">{user.email || 'User Profile'}</h5>
                        </div>

                        <div className="card-body px-4 py-3">
                            <ul className="list-group list-group-flush">
                                <li className="list-group-item d-flex justify-content-between align-items-center px-0">
                                    <span className="text-muted fw-semibold">
                                        <i className="bi bi-envelope me-2"></i>Email
                                    </span>
                                    <span>{user.email || '—'}</span>
                                </li>
                                <li className="list-group-item d-flex justify-content-between align-items-center px-0">
                                    <span className="text-muted fw-semibold">
                                        <i className="bi bi-shield me-2"></i>Role
                                    </span>
                                    <span className="badge bg-primary text-capitalize">{user.role || '—'}</span>
                                </li>
                                <li className="list-group-item d-flex justify-content-between align-items-center px-0">
                                    <span className="text-muted fw-semibold">
                                        <i className="bi bi-telephone me-2"></i>Contact
                                    </span>
                                    <span>{user.contactNumber || '—'}</span>
                                </li>
                                <li className="list-group-item d-flex justify-content-between align-items-center px-0">
                                    <span className="text-muted fw-semibold">
                                        <i className="bi bi-person me-2"></i>Gender
                                    </span>
                                    <span className="text-capitalize">{user.gender || '—'}</span>
                                </li>
                                <li className="list-group-item d-flex justify-content-between align-items-center px-0">
                                    <span className="text-muted fw-semibold">
                                        <i className="bi bi-geo-alt me-2"></i>Address
                                    </span>
                                    <span>{user.address || '—'}</span>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default UserDetail