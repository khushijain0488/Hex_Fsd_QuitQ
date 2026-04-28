import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const HomePage = () => {
  const [user, setUser] = useState({});
  const [cart,setCart]=useState(0)
  const [cartCount, setCartCount] = useState(0);
  const [product, setProduct] = useState([]);
  const [searchKeyword, setSearchKeyword] = useState('');
  const [activeCategory, setActiveCategory] = useState('All');

  const [sortBy, setSortBy] = useState('');
  const navigate = useNavigate();

  const ApiPath = "http://localhost:8080/users/api/v2/loggedInUser";
  const ProductPath = "http://localhost:8080/products/v1/get";
  const cartApiPath="http://localhost:8080/cart/api/v2/get"
 
  const categories = ['All', 'Electronics', 'Fashion', 'Mobile', 'Home & Kitchen', 'Books', 'Sports','OnePlus'];

  useEffect(() => {
    const config = {
      headers: { "Authorization": `Bearer ` + localStorage.getItem("token") }
    };
    const FetchUser = async () => {
      try {
        const response = await axios.get(ApiPath, config);
        console.log(response.data);
        
        setUser(response.data);
      } catch (err) {
        console.error("User fetch failed", err);
      }
    };
const FetchCart = async () => {
  try {                    
    const cartResponse = await axios.get(cartApiPath, config)
    setCart(cartResponse.data);
    setCartCount(cartResponse.data.items?.length || 0)
  } catch (err) {
    console.log("Cart fetch failed:", err.response?.data);
    // don't crash if cart fails
  }
}



FetchCart()
    FetchUser();
    
  }, []);

  useEffect(() => {
    const fetchProduct = async () => {
      try {
        const response = await axios.get(ProductPath);
        setProduct(response.data);
      } catch (err) {
        console.error("Product fetch failed", err);
      }
    };
    fetchProduct();
  }, []);

  const ProcessLogout = () => {
    localStorage.removeItem("token");
    navigate("/login");
  };
const ProcessCartClick=()=>{
  navigate("/cartpage")
    
}
  


// processing navigation
const DashboardNavigation=()=>{
  switch (user.role){
    case "SELLER":
      navigate("/seller-dashboard")
      break
    case "USER":
      navigate("/user-dashboard")
      break
      case "ADMIN":
        navigate("/admin-dashboard")
        
  }
}
// prcess become seller
const HandleBecomeSeller=()=>{
  navigate("/signup")
}
  const filteredProducts = product
    .filter(p => activeCategory === 'All' || p.category?.name === activeCategory)
    .filter(p => p.name?.toLowerCase().includes(searchKeyword.toLowerCase()))
    .sort((a, b) => {
      if (sortBy === 'low') return a.price - b.price;
      if (sortBy === 'high') return b.price - a.price;
      return 0;
    });

  return (
    <div style={{ fontFamily: 'Arial, sans-serif' }}>

      {/* Navbar */}
      <nav className="navbar navbar-expand-lg" style={{ backgroundColor: '#2874f0' }}>
        <div className="container-fluid px-4">
          <span className="navbar-brand fw-bold fs-4" style={{ color: '#ffe500' }}>QuitQ</span>
          <div className="d-flex flex-grow-1 mx-4">
            <input
              type="text"
              className="form-control"
              placeholder="Search products, brands..."
              value={searchKeyword}
              onChange={(e) => setSearchKeyword(e.target.value)}
            />
            <button className="btn ms-2 fw-bold px-4" style={{ backgroundColor: '#ffe500' }}>Search</button>
          </div>
          <div className="d-flex gap-3 align-items-center">
            <button className="btn text-white fw-bold">Home</button>
            <button className="btn text-white fw-bold" onClick={()=>ProcessCartClick()}>
              
              Cart ({cartCount})</button>
            <button className="btn text-white fw-bold" onClick={()=>navigate("/user-dashboard/myorders")}>Orders</button>
            {localStorage.getItem("token") && user?.username ? (
              <button className="btn text-white fw-bold" onClick={()=>DashboardNavigation()}>👤 {user.username}</button>
            ) : (
              <button className="btn text-white fw-bold" onClick={() => navigate("/login")}>Login</button>
            )}
            <button className="btn btn-danger fw-bold" onClick={ProcessLogout}>Logout</button>
          </div>
        </div>
      </nav>

      {/* Hero */}
      <div className="text-white text-center py-5" style={{ backgroundColor: '#2874f0' }}>
        <h1 className="fw-bold display-5">Shop Smart. Save More.</h1>
        <p className="lead">Millions of products from trusted sellers across India</p>
        <div className="d-flex justify-content-center gap-3 mt-3">
          <button className="btn fw-bold px-4 py-2" style={{ backgroundColor: '#ffe500' }}>Browse Products</button>
          <button className="btn fw-bold px-4 py-2" style={{ backgroundColor: '#ffe500' }} onClick={()=>HandleBecomeSeller()}>Become a Seller</button>
        </div>
      </div>

      {/* Category Filter */}
      <div className="container-fluid px-4 py-3 bg-white border-bottom">
        <div className="d-flex gap-2 flex-wrap">
          {categories.map((cat) => (
            <button
              key={cat}
              className="btn btn-sm rounded-pill px-3"
              style={{
                backgroundColor: activeCategory === cat ? '#2874f0' : 'white',
                color: activeCategory === cat.name ? 'white' : 'black',
                border: '1px solid #ddd'
              }}
              onClick={() => setActiveCategory(cat)}
            >
              {cat}
            </button>
          ))}
        </div>
      </div>

      {/* Products */}
      <div className="container-fluid px-4 py-4" style={{ backgroundColor: '#f1f3f6' }}>
        <div className="bg-white p-4 rounded">
          <h5 className="fw-bold mb-3">
            Featured Products
            <span className="text-muted fs-6 ms-2 fw-normal">({filteredProducts.length} results)</span>
          </h5>

          {/* Sort */}
          <div className="d-flex gap-2 mb-4">
            <select className="form-select w-auto" value={sortBy} onChange={(e) => setSortBy(e.target.value)}>
              <option value="">Sort By</option>
              <option value="low">Price: Low to High</option>
              <option value="high">Price: High to Low</option>
            </select>
          </div>

          {/* Product Grid */}
          {product.length === 0 ? (
            <div className="text-center py-5 text-muted">
              <div className="spinner-border text-primary mb-3" role="status"></div>
              <p>Loading products...</p>
            </div>
          ) : filteredProducts.length === 0 ? (
            <div className="text-center py-5 text-muted">No products found.</div>
          ) : (
            <div className="row g-3">
              {filteredProducts.map((p) => (
                <div
                  key={p.id}
                  className="col-6 col-md-4 col-lg-3"
                  onClick={() => navigate(`/productdetail/${p.id}`)}
                >
                  <div className="card h-100 border" style={{ cursor: 'pointer' }}>
                    <div className="card-body text-center p-2">

                      {/* Product Image */}
<img
  src={`http://localhost:8080${p.imageUrl}`}
  alt={p.name}
  style={{ width: '100%', height: '160px', objectFit: 'contain', marginBottom: '10px' }}
  onError={(e) => { e.target.style.display = 'none' }}
/>

                      {/* Category Badge */}
                      <div className="text-start mb-1">
                        <span
                          className="badge"
                          style={{ backgroundColor: '#e3f0ff', color: '#2874f0', fontSize: '11px' }}
                        >
                          {p.category?.name}
                        </span>
                      </div>

                      {/* Name */}
                      <h6 className="fw-bold text-start mb-1">{p.name}</h6>

                      {/* Seller */}
                      <p className="text-muted small text-start mb-1">
                        by {p.seller?.email}
                      </p>

                      {/* Description - truncated */}
                      <p className="text-muted small text-start mb-2" style={{
                        display: '-webkit-box',
                        WebkitLineClamp: 2,
                        WebkitBoxOrient: 'vertical',
                        overflow: 'hidden'
                      }}>
                        {p.description}
                      </p>

                      {/* Price */}
                      <div className="text-start">
                        <span className="fw-bold fs-6">Rs {p.price?.toLocaleString()}</span>
                      </div>

                      {/* Stock Status */}
                      <div className="text-start mt-1">
                        <span style={{
                          fontSize: '12px',
                          color: p.status === 'AVAILABLE' ? '#388e3c' : '#d32f2f'
                        }}>
                          {p.status === 'AVAILABLE' ? `✓ In Stock (${p.stockQuantity})` : '✗ Out of Stock'}
                        </span>
                      </div>

                    </div>

                    {/* Add to Cart Button */}
                    <div className="card-footer bg-white border-0 pt-0 pb-2 px-2">
                      <button
                        className="btn w-100 fw-bold"
                        style={{ backgroundColor: '#ff9f00', color: 'white' }}
                        onClick={(e) => e.stopPropagation()}
                      >
                        Detail
                      </button>
                    </div>
                  </div>
                </div>
              ))}
            </div>
          )}
        </div>
      </div>

      {/* Footer */}
      <footer className="text-white text-center py-3" style={{ backgroundColor: '#2874f0' }}>
        <p className="mb-0">© 2026 QuitQ. All rights reserved.</p>
      </footer>
    </div>
  );
};

export default HomePage;