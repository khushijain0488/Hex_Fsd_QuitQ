import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate, useParams } from 'react-router-dom';

const ProductDetail = () => {
  const navigate = useNavigate();
  const { id } = useParams();
  const [quantity, setQuantity] = useState(1);
  const [p, setProduct] = useState(null);
  const [loading, setLoading] = useState(true);

  const cartApiPath = "http://localhost:8080/cart/add";

  const GetProduct = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/products/get-by-id/${id}`);
      setProduct(response.data);
    } catch (err) {
      console.log("Product fetch error:", err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    GetProduct();
  }, []);

  const handleDecrease = () => setQuantity(q => Math.max(1, q - 1));
  const handleIncrease = () => setQuantity(q => Math.min(p.stockQuantity, q + 1));

  const HandleAddToCart = async (ProductId) => {
    const token = localStorage.getItem("token");
    if (!token) {
     
      navigate("/login");
      return;
    }

    try {
      const config = {
        headers: { "Authorization": `Bearer ` + token }
      };

      const response = await axios.post(cartApiPath, {
        "productId": ProductId,
        "quantity": quantity
      }, config);

      console.log("Added to cart:", response.data);
      navigate("/cartpage");

    } catch (err) {
      console.log("Add to cart error:", err.response?.data);

      if (err.response?.status === 401 || err.response?.status === 403) {
        alert("Session expired, please login again");
        navigate("/login");
      } else {
        alert(err.response?.data?.message ?? "Failed to add to cart");
      }
    }
  };

  if (loading) {
    return (
      <div className="d-flex justify-content-center align-items-center" style={{ minHeight: '100vh' }}>
        <div className="spinner-border text-primary" role="status">
          <span className="visually-hidden">Loading...</span>
        </div>
      </div>
    );
  }

  if (!p) {
    return (
      <div className="text-center py-5">
        <p className="text-muted">Product not found.</p>
        <button className="btn btn-primary" onClick={() => navigate("/")}>Go Back</button>
      </div>
    );
  }

  return (
    <div style={{ fontFamily: 'Arial, sans-serif', backgroundColor: '#f1f3f6', minHeight: '100vh' }}>

      {/* Navbar */}
      <nav className="navbar" style={{ backgroundColor: '#2874f0', padding: '12px 24px' }}>
        <span className="fw-bold fs-4" style={{ color: '#ffe500' }}>QuitQ</span>
      </nav>

      {/* Back */}
      <div className="px-4 py-3">
        <span
          onClick={() => navigate(-1)}
          style={{ color: '#2874f0', cursor: 'pointer', fontSize: '14px' }}
        >
          ← Back to Products
        </span>
      </div>

      {/* Main Card */}
      <div className="bg-white rounded mx-4 mb-4 p-4">
        <div className="row g-5">

          {/* Left: Image */}
          <div className="col-12 col-md-4">
            <div className="d-flex align-items-center justify-content-center rounded p-4" style={{ backgroundColor: '#f7f7f7' }}>
              <img
                src={`http://localhost:8080${p.imageUrl}`}
                alt={p.name}
                className="img-fluid"
                style={{ maxHeight: '280px', objectFit: 'contain' }}
                onError={(e) => { e.target.src = 'https://via.placeholder.com/280?text=No+Image'; }}
              />
            </div>
          </div>

          {/* Right: Info */}
          <div className="col-12 col-md-8">

            {/* Breadcrumb */}
            <p className="text-muted small mb-1">{p.category?.name}</p>

            {/* Name */}
            <h2 className="fw-bold mb-1">{p.name}</h2>

            {/* Seller */}
            <p className="text-secondary small mb-3">Sold by {p.seller?.email}</p>

            {/* Price */}
            <h3 className="fw-bold mb-3">Rs {p.price?.toLocaleString()}</h3>

            {/* Description */}
            <p className="mb-3" style={{ color: '#444', fontSize: '15px' }}>{p.description}</p>

            {/* Stock */}
            <p className={`fw-semibold mb-3 ${p.status === 'AVAILABLE' ? 'text-success' : 'text-danger'}`}>
              {p.status === 'AVAILABLE'
                ? `✓ In Stock (${p.stockQuantity} units)`
                : '✗ Out of Stock'}
            </p>

            {/* Quantity Selector */}
            {p.status === 'AVAILABLE' && (
              <div className="d-flex align-items-center gap-3 mb-4">
                <span className="text-secondary small">Qty:</span>
                <button
                  onClick={handleDecrease}
                  className="btn btn-outline-secondary"
                  style={{ width: '32px', height: '32px', padding: 0, fontSize: '18px', lineHeight: 1 }}
                >-</button>
                <span className="fw-bold fs-5">{quantity}</span>
                <button
                  onClick={handleIncrease}
                  className="btn btn-outline-secondary"
                  style={{ width: '32px', height: '32px', padding: 0, fontSize: '18px', lineHeight: 1 }}
                >+</button>
              </div>
            )}

            {/* Action Buttons */}
            <div className="d-flex gap-3 flex-wrap mb-4">
              <button
                disabled={p.status !== 'AVAILABLE'}
                className="btn fw-bold text-white px-5 py-3"
                style={{ backgroundColor: p.status === 'AVAILABLE' ? '#ff9f00' : '#ccc', border: 'none' }}
                onClick={() => HandleAddToCart(p.id)}
              >
                ADD TO CART
              </button>
              <button
                disabled={p.status !== 'AVAILABLE'}
                className="btn fw-bold text-white px-5 py-3"
                style={{ backgroundColor: p.status === 'AVAILABLE' ? '#fb641b' : '#ccc', border: 'none' }}
              >
                BUY NOW
              </button>
            </div>

            {/* Trust Badges */}
            <div className="text-muted small pt-3 border-top">
              Free Delivery &nbsp;|&nbsp; 10-day Returns &nbsp;|&nbsp; Secure Payment
            </div>

          </div>
        </div>
      </div>
    </div>
  );
};

export default ProductDetail;