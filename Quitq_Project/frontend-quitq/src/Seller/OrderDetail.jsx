import React, { useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import { useEffect } from 'react';
import axios from 'axios';

const OrderDetail = () => {
  const [order, setOrders] = useState(null)
  const { id } = useParams();
  const [status, setStatus] = useState("PENDING")
  const navigate = useNavigate()
  const ApiPath = "http://localhost:8080/orders/getById"

  const FetchOrderById = async () => {
    const config = {
      headers: {
        "Authorization": `Bearer ` + localStorage.getItem("token")
      }
    }
    const response = await axios.get(`${ApiPath}/${id}`, config)
    console.log(response.data)
    setOrders(response.data)
  }

  const handleUpdateStatus = async (e) => {
    e.preventDefault();
    await axios.put(`http://localhost:8080/orders/status/${id}?status=${status}`, {}, {
      headers: {
        "Authorization": `Bearer ` + localStorage.getItem("token")
      }
    })
    navigate("/seller-dashboard")
  }

  useEffect(() => {
    FetchOrderById()
  }, [])

  if (!order) return (
    <div className="d-flex justify-content-center align-items-center vh-100">
      <div className="spinner-border text-primary" role="status">
        <span className="visually-hidden">Loading...</span>
      </div>
    </div>
  )

  return (
    <div className="container py-4">
      <div className="card shadow-sm mb-4">
        <div className="card-header bg-primary text-white">
          <h5 className="mb-0">Order ID: #{order.orderId}</h5>
        </div>

        <div className="card-body">
          {order.items.map((item) => (
            <div key={item.productId} className="border rounded p-3 mb-3 bg-light">
              <div className="row">
                <div className="col-6 col-md-3 mb-2">
                  <small className="text-muted d-block">Product Name</small>
                  <strong>{item.productName}</strong>
                </div>
                <div className="col-6 col-md-3 mb-2">
                  <small className="text-muted d-block">Quantity</small>
                  <strong>{item.quantity}</strong>
                </div>
                <div className="col-6 col-md-3 mb-2">
                  <small className="text-muted d-block">Each Price</small>
                  <strong>₹{item.priceAtPurchase}</strong>
                </div>
                <div className="col-6 col-md-3 mb-2">
                  <small className="text-muted d-block">Subtotal</small>
                  <strong className="text-success">₹{item.subTotal}</strong>
                </div>
              </div>
            </div>
          ))}

          <div className="d-flex align-items-center gap-2 mt-2">
            <span className="text-muted">Current Status:</span>
            <span className={`badge fs-6 ${
              order.status === "DELIVERED" ? "bg-success" :
              order.status === "CANCELLED" ? "bg-danger" :
              order.status === "SHIPPED" ? "bg-info" :
              order.status === "PROCESSING" ? "bg-warning text-dark" :
              "bg-secondary"
            }`}>
              {order.status}
            </span>
          </div>
        </div>
      </div>

      {/* Update Status Form */}
      <div className="card shadow-sm">
        <div className="card-header bg-secondary text-white">
          <h6 className="mb-0">Update Order Status</h6>
        </div>
        <div className="card-body">
          <form onSubmit={(e) => handleUpdateStatus(e)}>
            <div className="mb-3">
              <label className="form-label fw-semibold">Select New Status</label>
              <select
                className="form-select"
                value={status}
                onChange={(e) => setStatus(e.target.value)}
              >
                <option value="PENDING">PENDING</option>
                <option value="PROCESSING">PROCESSING</option>
                <option value="SHIPPED">SHIPPED</option>
                <option value="DELIVERED">DELIVERED</option>
                <option value="CANCELLED">CANCELLED</option>
              </select>
            </div>
            <button type="submit" className="btn btn-primary w-100">
              Update Status
            </button>
          </form>
        </div>
      </div>
    </div>
  )
}

export default OrderDetail