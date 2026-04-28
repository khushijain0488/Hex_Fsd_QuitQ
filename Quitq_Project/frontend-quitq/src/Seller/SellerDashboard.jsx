import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const SellerDashboard = () => {
  const navigate = useNavigate();
  const [seller, setSeller] = useState({});
  const [products, setProducts] = useState([]);
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(true);

  const config = {
    headers: { "Authorization": `Bearer ` + localStorage.getItem("token") }
  };

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (!token) { navigate("/login"); return; }

    const fetchData = async () => {
      try {
        const userRes = await axios.get("http://localhost:8080/users/api/v2/loggedInUser", config);
        setSeller(userRes.data);

        const prodRes = await axios.get("http://localhost:8080/products/get-product", config);
        setProducts(prodRes.data.filter(p=>!(p.status=="INACTIVE")));

        const orderRes = await axios.get("http://localhost:8080/orders/api/v2/loggedInseller", config);
        setOrders(orderRes.data);
        
        
        
        

      } catch (err) {
        console.log("Dashboard fetch error:", err.response?.data);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, []);

  const totalRevenue = orders
    .filter(o => o.status === 'DELIVERED')
    .reduce((sum, o) => sum + (o.totalAmount ?? 0), 0);

  const avgRating = products.length > 0
    ? (products.reduce((sum, p) => sum + (p.rating ?? 0), 0) / products.length).toFixed(1)
    : "N/A";

  const getStatusBadge = (status) => {
    switch (status) {
      case 'DELIVERED': return 'success';
      case 'SHIPPED': return 'primary';
      case 'PENDING': return 'danger';
      case 'PROCESSING': return 'warning';
      default: return 'secondary';
    }
  };

const handleRemoveProduct = async (productId) => {
 

  const deleteApiPath = `http://localhost:8080/products/${productId}`;
  

await axios.delete(deleteApiPath, {
      headers: {
        "Authorization": `Bearer ` + localStorage.getItem("token")
      }
    });
    
    
    
   
    
    setProducts(products.filter(p => !(p.id == productId)));
    

 
}
const HandleSeeDetail=(OrderId)=>{
  console.log(OrderId);
  
  navigate(`/seeDetail/${OrderId}`)
}

const handleAddProduct=()=>{
  navigate("/add-product")
}
const handleEditingProduct = (productId) => {
  console.log(productId)
  navigate(`/edit-product/${productId}`);
}
  if (loading) {
    return (
      <div className="d-flex justify-content-center align-items-center" style={{ minHeight: '100vh' }}>
        <div className="text-center">
          <div className="spinner-border text-primary mb-3"></div>
          <p className="text-muted">Loading dashboard...</p>
        </div>
      </div>
    );
  }

  return (
    <div style={{ backgroundColor: '#f1f3f6', minHeight: '100vh' }}>

      {/* Navbar */}
      <nav className="navbar navbar-expand-lg px-4" style={{ backgroundColor: '#2874f0' }}>
        <span className="navbar-brand fw-bold fs-4" style={{ color: '#ffe500' }}>QuitQ</span>
        <div className="ms-auto d-flex align-items-center gap-3">
          <span className="text-white fw-bold">👤 {seller.username}</span>
          <button
            className="btn btn-sm fw-bold"
            style={{ backgroundColor: '#ffe500' }}
            onClick={() => { localStorage.removeItem("token"); navigate("/login"); }}
          >
            Logout
          </button>
        </div>
      </nav>

      <div className="container-fluid px-4 py-4">

        {/* Header */}
        <div className="mb-4">
          <h4 className="fw-bold mb-0">
            Seller Dashboard{' '}
            <span className="text-muted fw-normal fs-6">— {seller.name}</span>
          </h4>
        </div>

        {/* Stats Cards */}
        <div className="row g-3 mb-4">
          <div className="col-6 col-md-3">
            <div className="card border-0 shadow-sm text-center py-3">
              <p className="fw-bold mb-1" style={{ fontSize: '22px', color: '#2874f0' }}>
                Rs {totalRevenue.toLocaleString()}
              </p>
              <p className="text-muted small mb-0">Revenue</p>
            </div>
          </div>

          <div className="col-6 col-md-3">
            <div className="card border-0 shadow-sm text-center py-3">
              <p className="fw-bold mb-1" style={{ fontSize: '22px', color: '#2874f0' }}>
                {products.length}
              </p>
              <p className="text-muted small mb-0">Products</p>
            </div>
          </div>

          <div className="col-6 col-md-3">
            <div className="card border-0 shadow-sm text-center py-3">
              <p className="fw-bold mb-1" style={{ fontSize: '22px', color: '#2874f0' }}>
                {orders.length}
              </p>
              <p className="text-muted small mb-0">Orders</p>
            </div>
          </div>

          <div className="col-6 col-md-3">
            <div className="card border-0 shadow-sm text-center py-3">
              <p className="fw-bold mb-1" style={{ fontSize: '22px', color: '#2874f0' }}>
                {avgRating}
              </p>
              <p className="text-muted small mb-0">Avg Rating</p>
            </div>
          </div>
        </div>

        {/* Main Content */}
        <div className="row g-3">

          {/* My Products */}
          <div className="col-lg-8">
            <div className="card border-0 shadow-sm">
              <div className="card-body">
                <div className="d-flex justify-content-between align-items-center mb-3">
                  <h6 className="fw-bold mb-0">My Products</h6>
                  <button
                    className="btn btn-sm fw-bold text-white"
                    style={{ backgroundColor: '#2874f0' }}
                    onClick={() => handleAddProduct()}
                  >
                    + Add Product
                  </button>
                </div>

                <table className="table table-hover align-middle mb-0">
                  <thead className="table-light">
                    <tr>
                      <th className="fw-normal text-muted small border-0">Product</th>
                      <th className="fw-normal text-muted small border-0">Price</th>
                      <th className="fw-normal text-muted small border-0">Stock</th>
                      <th className="fw-normal text-muted small border-0">Action</th>
                    </tr>
                  </thead>
                  <tbody>
                    {products.length === 0 ? (
                      <tr>
                        <td colSpan={4} className="text-center text-muted py-4">
                          No products yet. Add your first product!
                        </td>
                      </tr>
                    ) : (
                      products.map((product, index) => (
                        <tr key={index}>
                          <td>
                            <div className="d-flex align-items-center gap-2">
                              <img
                                src={`http://localhost:8080${product.imageUrl}`}
                                alt={product.name}
                                style={{ width: '35px', height: '35px', objectFit: 'contain', borderRadius: '4px' }}
                                
                              />
                              <span className="fw-bold small">{product.name}</span>
                            </div>
                          </td>
                          <td className="small">Rs {product.price?.toLocaleString()}</td>
                          <td>
                            <span className={`badge bg-${product.stockQuantity > 10 ? 'success' : 'danger'}`}>
                              {product.stockQuantity} units
                            </span>
                          </td>
                          <td>
                            
                            <button
                              className="btn btn-sm btn-outline-danger"
                              onClick={() => handleRemoveProduct(product.id)}
                            >
                              Remove
                            </button>
                             <button
                              className="btn btn-sm btn-outline-primary"
                              onClick={() => handleEditingProduct(product.id)}
                            >
                              Edit
                            </button>
                            
                          </td>
                        </tr>
                      ))
                    )}
                  </tbody>
                </table>
              </div>
            </div>
          </div>

          {/* Recent Orders */}
          <div className="col-lg-4">
            <div className="card border-0 shadow-sm">
              <div className="card-body">
                <h6 className="fw-bold mb-3">Recent Orders</h6>

                <table className="table table-hover align-middle mb-0">
                  <thead className="table-light">
                    <tr>
                      <th className="fw-normal text-muted small border-0">Order ID</th>
                      <th className="fw-normal text-muted small border-0">Product</th>
                      <th className="fw-normal text-muted small border-0">Status</th>
                      <th className="fw-normal text-muted small border-0">Detail</th>
                    </tr>
                  </thead>
                  <tbody>
                    {orders.length === 0 ? (
                      <tr>
                        <td colSpan={3} className="text-center text-muted py-4">
                          No orders yet
                        </td>
                      </tr>
                    ) : (
                      orders.map((order, index) => (
                        <tr key={index}>
                          <td className="small text-muted">
                            {/* #ORD-{String(order.id).padStart(3, '0')} */}
                            {order.orderId}
                          </td>
                          <td className="small">
                            {order.items?.[0]?.productName ?? 'N/A'}
                          </td>
                          <td>
                            <span className={`badge bg-${getStatusBadge(order.status)}`}>
                              {order.status}
                            </span>
                          </td>
                          <td><button onClick={()=>HandleSeeDetail(order.orderId)}>See Detail</button></td>
                        </tr>
                      ))
                    )}
                  </tbody>
                </table>
              </div>
            </div>
          </div>

        </div>
      </div>
    </div>
  );
};

export default SellerDashboard;