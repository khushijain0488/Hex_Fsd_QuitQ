import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

const CartPage = () => {
  const navigate = useNavigate();
  const [cart, setCart] = useState(null);
  const [loading, setLoading] = useState(true);
  const [loadingItems, setLoadingItems] = useState({});
  const BASE = "http://localhost:8080";
  const config = {
    headers: { "Authorization": `Bearer ` + localStorage.getItem("token") }
  };

  useEffect(() => {
    const FetchCart = async () => {
      try {
        const response = await axios.get(`${BASE}/cart/api/v2/get`, config);
        console.log(response.data);
        setCart(response.data);
      } catch (err) {
        console.log("Cart fetch error:", err);
      } finally {
        setLoading(false);
      }
    };
    FetchCart();
  }, []);

  if (loading) {
    return <div className="text-center py-5">Loading cart...</div>;
  }

  if (!cart || !cart.items || cart.items.length === 0) {
    return (
      <div className="text-center py-5">
        <p className="text-muted">Your cart is empty.</p>
        <button className="btn btn-primary" onClick={() => navigate("/home")}>
          Continue Shopping
        </button>
      </div>
    );
  }

  const setItemLoading = (productId, val) =>
    setLoadingItems(prev => ({ ...prev, [productId]: val }));

  const HandleQuantityChange = async (productId, type) => {
    const item = cart.items.find(i => i.productId === productId);
    if (!item) return;

    const newQty = type === 'inc' ? item.quantity + 1 : Math.max(1, item.quantity - 1);
    if (newQty === item.quantity) return;

    setItemLoading(productId, true);
    try {
      const response = await axios.put(
        `${BASE}/cart/update/${productId}?quantity=${newQty}`,
        {},
        config
      );
      setCart(response.data);
    } catch (err) {
      console.log("Quantity update error:", err.response?.data);
      alert("Failed to update quantity");
    } finally {
      setItemLoading(productId, false);
    }
  };

  const HandleRemove = async (productId) => {
    setItemLoading(productId, true);
    try {
      const response = await axios.delete(`${BASE}/cart/remove/${productId}`, config);
      const updatedCart = response.data;

      if (!updatedCart || !updatedCart.items || updatedCart.items.length === 0) {
        setCart(null);
      } else {
        setCart(updatedCart);
      }
    } catch (err) {
      console.log("Remove error:", err.response?.data);
      alert("Failed to remove item");
    } finally {
      setItemLoading(productId, false);
    }
  };

  const discount = Math.round(cart.totalPrice * 0.05);
  const finalPrice = cart.totalPrice - discount;

  const ProcessOrder = async () => {
    const token = localStorage.getItem("token");
    if (!token) { alert("Please login first!"); navigate("/login"); return; }

    try {
      const orderPayload = {
        items: cart.items.map(item => ({
          productId: item.productId,
          quantity: item.quantity
        }))
      };

      await axios.post(`${BASE}/orders/place`, orderPayload, config);

      try {
        await axios.delete(`${BASE}/cart/clear`, config);
      } catch (e) {
        console.log("Cart clear failed:", e);
      }

navigate("/order-success")

    } catch (err) {
      console.log("Error:", err.response?.data);
      alert(err.response?.data?.message ?? "Failed to place order");
    }
  };

  return (
    <div style={{ fontFamily: 'Arial, sans-serif', backgroundColor: '#f1f3f6', minHeight: '100vh' }}>

      <nav className="navbar" style={{ backgroundColor: '#2874f0', padding: '12px 24px' }}>
        <span className="fw-bold fs-4" style={{ color: '#ffe500' }}>QuitQ</span>
      </nav>

      <div style={{ margin: '24px 32px', display: 'flex', gap: '24px', flexWrap: 'wrap' }}>

        <div style={{ flex: 2, minWidth: '300px' }}>
          <div style={{ backgroundColor: 'white', borderRadius: '8px', padding: '24px' }}>
            <h5 className="fw-bold mb-4">Shopping Cart</h5>

            {cart.items.map((item, index) => (
              <div key={index} style={{
                display: 'flex', alignItems: 'center', gap: '16px',
                borderBottom: '1px solid #eee', paddingBottom: '20px', marginBottom: '20px',
                opacity: loadingItems[item.productId] ? 0.5 : 1,
                transition: 'opacity 0.2s'
              }}>

                <img
                  src={item.imageUrl ? `${BASE}${item.imageUrl}` : 'https://via.placeholder.com/70'}
                  alt={item.productName}
                  style={{ width: '70px', height: '70px', objectFit: 'contain', borderRadius: '4px', backgroundColor: '#f7f7f7', padding: '4px' }}
                />

                <div style={{ flex: 1 }}>
                  <p className="fw-bold mb-0">{item.productName}</p>
                  <p className="text-muted small mb-2">{item.sellerName ?? 'by Seller'}</p>
                  <p className="fw-bold mb-2" style={{ color: '#2874f0' }}>
                    Rs {item.price?.toLocaleString()}
                  </p>

                  <div style={{ display: 'flex', alignItems: 'center', gap: '8px' }}>
                    <button
                      onClick={() => HandleQuantityChange(item.productId, 'dec')}
                      disabled={loadingItems[item.productId]}
                      style={{ width: '28px', height: '28px', border: '1px solid #ddd', borderRadius: '4px', backgroundColor: 'white', cursor: 'pointer', fontSize: '16px' }}
                    >-</button>
                    <span style={{ minWidth: '20px', textAlign: 'center', fontWeight: 'bold' }}>{item.quantity}</span>
                    <button
                      onClick={() => HandleQuantityChange(item.productId, 'inc')}
                      disabled={loadingItems[item.productId]}
                      style={{ width: '28px', height: '28px', border: '1px solid #ddd', borderRadius: '4px', backgroundColor: 'white', cursor: 'pointer', fontSize: '16px' }}
                    >+</button>

                    <button
                      onClick={() => HandleRemove(item.productId)}
                      disabled={loadingItems[item.productId]}
                      style={{ marginLeft: '8px', background: 'none', border: 'none', color: '#e53935', cursor: 'pointer', fontSize: '13px', fontWeight: 'bold' }}
                    >
                      {loadingItems[item.productId] ? '...' : 'Remove'}
                    </button>
                  </div>
                </div>

                <div style={{ textAlign: 'right', minWidth: '100px' }}>
                  <p className="text-muted small mb-0">Rs {item.price?.toLocaleString()} each</p>
                  <p className="fw-bold mb-0">Rs {item.subTotal?.toLocaleString()}</p>
                </div>
              </div>
            ))}
          </div>
        </div>

        <div style={{ flex: 1, minWidth: '250px' }}>
          <div style={{ backgroundColor: 'white', borderRadius: '8px', padding: '24px' }}>
            <h6 className="fw-bold mb-3" style={{ color: '#777', textTransform: 'uppercase', fontSize: '13px' }}>Price Details</h6>
            <div className="d-flex justify-content-between mb-2" style={{ fontSize: '14px' }}>
              <span>Price ({cart.items.length} items)</span>
              <span>Rs {cart.totalPrice?.toLocaleString()}</span>
            </div>
            <div className="d-flex justify-content-between mb-2" style={{ fontSize: '14px' }}>
              <span>Delivery</span><span style={{ color: '#388e3c' }}>FREE</span>
            </div>
            <div className="d-flex justify-content-between mb-2" style={{ fontSize: '14px' }}>
              <span>Discount (5%)</span>
              <span style={{ color: '#e53935' }}>-Rs {discount.toLocaleString()}</span>
            </div>
            <hr />
            <div className="d-flex justify-content-between fw-bold" style={{ fontSize: '16px' }}>
              <span>Total Amount</span><span>Rs {finalPrice.toLocaleString()}</span>
            </div>
            <p style={{ color: '#388e3c', fontSize: '13px', marginTop: '8px' }}>
              You will save Rs {discount.toLocaleString()} on this order
            </p>
            <button
              className="btn w-100 fw-bold mt-3 text-white"
              style={{ backgroundColor: '#fb641b', padding: '12px' }}
              onClick={ProcessOrder}
            >
              PROCEED TO CHECKOUT
            </button>
          </div>
        </div>

      </div>
    </div>
  );
};

export default CartPage;