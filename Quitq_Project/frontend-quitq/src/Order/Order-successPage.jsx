import React from 'react';
import { useNavigate } from 'react-router-dom';

const OrderSuccess = () => {
  const navigate = useNavigate();

  return (
    <div style={{ backgroundColor: '#fff', minHeight: '100vh', display: 'flex', justifyContent: 'center', alignItems: 'center', padding: '2rem 1rem' }}>

      <div className="rounded-3 overflow-hidden" style={{ width: '100%', maxWidth: '460px', backgroundColor: '#2874f0' }}>

        {/* Top Section */}
        <div className="d-flex flex-column align-items-center gap-2 p-4">

          {/* Check Circle */}
          <div className="rounded-circle d-flex align-items-center justify-content-center"
            style={{ width: '60px', height: '60px', backgroundColor: '#fff' }}>
            <svg width="28" height="28" viewBox="0 0 28 28" fill="none">
              <path d="M6 14.5L11 19.5L22 8.5" stroke="#2874f0" strokeWidth="2.5" strokeLinecap="round" strokeLinejoin="round" />
            </svg>
          </div>

          <p className="mb-0" style={{ fontSize: '18px', color: '#fff', fontWeight: '500' }}>
            Order placed successfully!
          </p>
          <p className="mb-0" style={{ fontSize: '13px', color: 'rgba(255,255,255,0.8)' }}>
            Thank you for shopping with QuitQ
          </p>
        </div>

        {/* Body */}
        <div className="px-4 pb-4 d-flex flex-column gap-2">

          {/* Buttons */}
          <button
            className="btn w-100 py-2 fw-500"
            style={{ backgroundColor: '#fff', color: '#2874f0', border: 'none', borderRadius: '8px', fontWeight: '500', fontSize: '15px' }}
            onClick={() => navigate("/user-dashboard/myorders")}
          >
            View my orders
          </button>

          <button
            className="btn w-100 py-2"
            style={{ backgroundColor: 'transparent', color: '#fff', border: '1.5px solid #fff', borderRadius: '8px', fontWeight: '500', fontSize: '15px' }}
            onClick={() => navigate("/home")}
          >
            Continue shopping
          </button>

        </div>
      </div>
    </div>
  );
};

export default OrderSuccess;