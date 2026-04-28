import axios from 'axios'
import React, { useState, useEffect } from 'react'

const MyCarts = () => {
  const [cart, setCart] = useState(null)
  const ApiPath = "http://localhost:8080/cart/api/v2/get"

  useEffect(() => {
    const FetchCart = async () => {
      const config = {
        headers: {
          "Authorization": `Bearer ` + localStorage.getItem("token")
        }
      }
      const response = await axios.get(ApiPath, config)
      setCart(response.data)
    }
    FetchCart()
  }, [])

  return (
    <div className="container py-4">
      <h4 className="mb-4 fw-semibold">🛒 My Cart</h4>

    
      {cart?.items?.map((item, i) => (
        <div key={i} className="card mb-3 border-0 shadow-sm">
          <div className="card-body d-flex justify-content-between align-items-center">
            <div>
              <h5 className="mb-1">{item.productName}</h5>
              <small className="text-muted">Qty: {item.quantity}</small>
            </div>
            <div className="text-end">
              <p className="mb-0 fw-semibold text-success">₹{item.price.toLocaleString('en-IN')}</p>
              <small className="text-muted">Subtotal: ₹{item.subTotal.toLocaleString('en-IN')}</small>
            </div>
          </div>
        </div>
      ))}

      {/* Total */}
      {cart && (
        <div className="card border-0 bg-dark text-white mt-3">
          <div className="card-body d-flex justify-content-between">
            <span className="fw-semibold">Total Amount</span>
            <span className="fw-bold text-success fs-5">
              ₹{cart.totalPrice.toLocaleString('en-IN')}
            </span>
          </div>
        </div>
      )}

      {/* Loading state */}
      {!cart && (
        <div className="text-center py-5 text-muted">
          <div className="spinner-border mb-3" role="status" />
          <p>Loading your cart...</p>
        </div>
      )}
    </div>
  )
}

export default MyCarts