import axios from 'axios'
import React, { useState, useEffect } from 'react'

const AllOrder = () => {
  const [order, setOrder] = useState([])
const [currentPage,setCurrentPage]=useState(0)
const[totalPage,settotalPage]=useState(0)
const limit=5
  const FetchFullOrder = async (page) => {
    const response = await axios.get(`http://localhost:8080/orders/v2/getAll?page=${page}&limit=${limit}`, {
      headers: {
        "Authorization": `Bearer ` + localStorage.getItem("token")
      }
    })
    console.log(response.data.data)
    settotalPage(response.data.totalPages)
    setOrder(response.data.data)
  }

  useEffect(() => {
    FetchFullOrder(currentPage)
  }, [currentPage])

  const getStatusBadge = (status) => {
    const map = {
      PENDING: 'warning',
      SHIPPED: 'info',
      DELIVERED: 'success',
      CANCELLED: 'danger',
    }
    return map[status] || 'secondary'
  }

  return (
    <div className="container my-4">
      <h2 className="mb-4 fw-bold">All Orders</h2>

      {order.length === 0 ? (
        <div className="alert alert-info">No orders found.</div>
      ) : (
        order.map((o) => (
          <div key={o.orderId} className="card mb-3 shadow-sm">
            <div className="card-header d-flex justify-content-between align-items-center">
              <span className="fw-semibold">Order #{o.orderId}</span>
              <span className={`badge bg-${getStatusBadge(o.status)}`}>
                {o.status}
              </span>
            </div>

            <div className="card-body">
              <div className="row mb-2">
                <div className="col-md-4">
                  <small className="text-muted">User ID</small>
                  <div className="fw-medium">{o.userId}</div>
                </div>
                <div className="col-md-4">
                  <small className="text-muted">Shipping Address</small>
                  <div className="fw-medium">{o.shippingAddress}</div>
                </div>
                <div className="col-md-4">
                  <small className="text-muted">Ordered At</small>
                  <div className="fw-medium">
                    {new Date(o.orderedAt).toLocaleString()}
                  </div>
                </div>
              </div>

              <div className="mb-2">
                <small className="text-muted">Total Amount</small>
                <div className="fw-bold text-success fs-5">₹{o.totalAmount.toLocaleString()}</div>
              </div>

              {o.items.length > 0 ? (
                <div>
                  <small className="text-muted d-block mb-1">Items</small>
                  <table className="table table-sm table-bordered mb-0">
                    <thead className="table-light">
                      <tr>
                        <th>Product</th>
                        <th>Qty</th>
                        <th>Price</th>
                        <th>Subtotal</th>
                      </tr>
                    </thead>
                    <tbody>
                      {o.items.map((oi) => (
                        <tr key={oi.productId}>
                          <td>{oi.productName}</td>
                          <td>{oi.quantity}</td>
                          <td>₹{oi.priceAtPurchase}</td>
                          <td>₹{oi.subTotal}</td>
                        </tr>
                      ))}
                    </tbody>
                  </table>
                </div>
              ) : (
                <small className="text-muted fst-italic">No items in this order.</small>
              )}
            </div>
          </div>
        ))
      )}
      <div className='mt-4'>
        <ul className='pagination justify-content-center'>
            <li><button onClick={()=>setCurrentPage(currentPage-1)} disabled={currentPage===0}>Prev</button></li>
            <li>
                {
                    [...Array(totalPage)].map((_,i)=>(
                        <button onClick={()=>setCurrentPage(i)}>{i}</button>
                    ))
                }
            </li>
            <li>
                <button onClick={()=>setCurrentPage(currentPage+1)} disabled={currentPage===totalPage-1}>Next</button>
            </li>
        </ul>
      </div>
    </div>
   
  )
}

export default AllOrder