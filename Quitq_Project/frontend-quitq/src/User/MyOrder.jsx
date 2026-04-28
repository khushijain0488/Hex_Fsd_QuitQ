import React, { useState, useEffect } from 'react'
import axios from 'axios'

const MyOrder = () => {
  const [orders, setOrders] = useState([])
  const ApiPath = "http://localhost:8080/orders/getAllOrdersOfUser"

  useEffect(() => {
    const FetchOrders = async () => {
      const config = {
        headers: {
          "Authorization": `Bearer ` + localStorage.getItem("token")
        }
      }
      const response = await axios.get(ApiPath, config)
      setOrders(response.data)
    }
    FetchOrders()
  }, [])

  return (
    <div className="container py-4">
      <h3 className="mb-4 fw-semibold">🛒 My Orders</h3>

      {orders.length === 0 ? (
        <div className="text-center text-muted py-5">
          <h5>No orders found</h5>
        </div>
      ) : (
        orders.map((order) => (
          <div className="card shadow-sm mb-4 border-0" key={order.orderId}>

            {/* Order Header */}
            <div className="card-header bg-dark text-white d-flex justify-content-between align-items-center">
              <span className="fw-semibold fs-6">Order #{order.orderId}</span>
              <span className={`badge ${order.status === 'PENDING' ? 'bg-warning text-dark' : 'bg-success'}`}>
                {order.status}
              </span>
            </div>

            <div className="card-body">

              {/* Order Info Row */}
              <div className="row mb-3">
                <div className="col-md-6">
                  <small className="text-muted">Shipping Address</small>
                  <p className="mb-0 fw-semibold">📍 {order.shippingAddress}</p>
                </div>
                <div className="col-md-6 text-md-end">
                  <small className="text-muted">Ordered At</small>
                  <p className="mb-0 fw-semibold">
                    🗓 {new Date(order.orderedAt).toLocaleDateString('en-IN', {
                      day: '2-digit', month: 'short', year: 'numeric'
                    })}
                  </p>
                </div>
              </div>

              {/* Items Table */}
              <div className="table-responsive">
                <table className="table table-bordered table-hover align-middle mb-0">
                  <thead className="table-light">
                    <tr>
                      <th>#</th>
                      <th>Product</th>
                      <th className="text-center">Quantity</th>
                      <th className="text-end">Price</th>
                      <th className="text-end">Subtotal</th>
                    </tr>
                  </thead>
                  <tbody>
                    {order.items.map((item, i) => (
                      <tr key={i}>
                        <td>{i + 1}</td>
                        <td className="fw-semibold">{item.productName}</td>
                        <td className="text-center">
                          <span className="badge bg-secondary">{item.quantity}</span>
                        </td>
                        <td className="text-end">₹{item.priceAtPurchase.toLocaleString('en-IN')}</td>
                        <td className="text-end text-success fw-semibold">
                          ₹{item.subTotal.toLocaleString('en-IN')}
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            </div>

            {/* Order Footer */}
            <div className="card-footer bg-light d-flex justify-content-between align-items-center">
              <span className="text-muted small">{order.items.length} item(s) in this order</span>
              <span className="fw-bold fs-6 text-dark">
                Total: <span className="text-success">₹{order.totalAmount.toLocaleString('en-IN')}</span>
              </span>
            </div>

          </div>
        ))
      )}
    </div>
  )
}

export default MyOrder