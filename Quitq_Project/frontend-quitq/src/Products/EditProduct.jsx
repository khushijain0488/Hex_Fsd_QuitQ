import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate, useParams } from 'react-router-dom';

const EditProduct = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [imageFile, setImageFile] = useState(null);
  const [imagePreview, setImagePreview] = useState("");
  const [form, setForm] = useState({
    name: '', price: '', stockQuantity: '', description: '', categoryId: ''
  });

  useEffect(() => {
    const fetchProduct = async () => {
      const config = {
        headers: { "Authorization": `Bearer ` + localStorage.getItem("token") }
      };
      try {
        const res = await axios.get(`http://localhost:8080/products/get-by-id/${id}`, config);
        const p = res.data;
        setForm({
          name: p.name ?? '',
          price: p.price ?? '',
          stockQuantity: p.stockQuantity ?? '',
          description: p.description ?? '',
          categoryId: p.category?.id ?? ''
        });
        // Show existing image as preview
        if (p.imageUrl) {
          setImagePreview(`http://localhost:8080${p.imageUrl}`);
        }
      } catch (err) {
        console.log("Fetch product error:", err.response?.data);
      } finally {
        setLoading(false);
      }
    };
    fetchProduct();
  }, [id]);

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleImageChange = (e) => {
    const file = e.target.files[0];
    if (file) {
      setImageFile(file);
      setImagePreview(URL.createObjectURL(file));
    }
  };

  const handleSubmit = async () => {
    setSaving(true);
    try {
      const formData = new FormData();

      formData.append("product", new Blob([JSON.stringify({
        name: form.name,
        price: parseFloat(form.price),
        stockQuantity: parseInt(form.stockQuantity),
        description: form.description,
        categoryId: parseInt(form.categoryId)
      })], { type: "application/json" }));

      if (imageFile) {
        formData.append("image", imageFile);
      }

      await axios.put(`http://localhost:8080/products/${id}`, formData, {
        headers: {
          "Authorization": `Bearer ` + localStorage.getItem("token")
          // Don't set Content-Type — axios sets it automatically
        }
      });

      navigate("/seller-dashboard");
    } catch (err) {
      console.log("Update error:", err.response?.data);
      alert("Failed to update product. Please try again.");
    } finally {
      setSaving(false);
    }
  };

  if (loading) {
    return (
      <div className="d-flex justify-content-center align-items-center" style={{ minHeight: '100vh' }}>
        <div className="spinner-border text-primary"></div>
      </div>
    );
  }

  return (
    <div style={{ backgroundColor: '#f1f3f6', minHeight: '100vh' }}>

      <nav className="navbar px-4" style={{ backgroundColor: '#2874f0' }}>
        <span className="navbar-brand fw-bold fs-4" style={{ color: '#ffe500' }}>QuitQ</span>
      </nav>

      <div className="container py-5" style={{ maxWidth: '600px' }}>
        <div className="card border-0 shadow-sm p-4">
          <h5 className="fw-bold mb-4">Edit Product</h5>

          <div className="mb-3">
            <label className="form-label fw-bold small">Product Name</label>
            <input className="form-control" name="name" value={form.name} onChange={handleChange} />
          </div>

          <div className="mb-3">
            <label className="form-label fw-bold small">Price (Rs)</label>
            <input className="form-control" type="number" name="price" value={form.price} onChange={handleChange} />
          </div>

          <div className="mb-3">
            <label className="form-label fw-bold small">Stock Quantity</label>
            <input className="form-control" type="number" name="stockQuantity" value={form.stockQuantity} onChange={handleChange} />
          </div>

          <div className="mb-3">
            <label className="form-label fw-bold small">Description</label>
            <textarea className="form-control" rows={3} name="description" value={form.description} onChange={handleChange} />
          </div>

          {/* Image Upload */}
          <div className="mb-4">
            <label className="form-label fw-bold small">Product Image</label>
            <input
              type="file"
              className="form-control"
              accept="image/*"
              onChange={handleImageChange}
            />
            {/* Preview — shows existing or newly selected image */}
            {imagePreview && (
              <div className="mt-2 text-center p-2 border rounded bg-white">
                <img
                  src={imagePreview}
                  alt="preview"
                  style={{ width: '100px', height: '100px', objectFit: 'contain' }}
                />
                <p className="text-muted small mb-0 mt-1">
                  {imageFile ? "New Image Preview" : "Current Image"}
                </p>
              </div>
            )}
          </div>

          <div className="d-flex gap-2">
            <button
              className="btn fw-bold text-white w-100"
              style={{ backgroundColor: '#2874f0' }}
              onClick={handleSubmit}
              disabled={saving}
            >
              {saving ? 'Saving...' : 'Save Changes'}
            </button>
            <button
              className="btn btn-outline-secondary w-100"
              onClick={() => navigate("/seller-dashboard")}
              disabled={saving}
            >
              Cancel
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default EditProduct;