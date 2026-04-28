import axios from 'axios'
import React, { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'

const AddProduct = () => {
  const navigate = useNavigate()
  const AddProductApiPath = "http://localhost:8080/products/add"
  const [name, setname] = useState("")
  const [description, setdescription] = useState("")
  const [imageFile, setImageFile] = useState(null)
  const [imagePreview, setImagePreview] = useState("")
  const [price, setprice] = useState(0)
  const [quantity, setquantity] = useState(0)
  const [categoryId, setcategoryId] = useState("")
  const [seller, setseller] = useState({})
  const [loading, setloading] = useState(false)
  const[category,setCategory]=useState([])
  useEffect(() => {
    const LoggedInSeller = async () => {
      const response = await axios.get("http://localhost:8080/users/api/v2/loggedInUser", {
        headers: { "Authorization": `Bearer ` + localStorage.getItem("token") }
      })
      setseller(response.data)
    }
    const GetAllCategory=async()=>{
      const response=await axios.get("http://localhost:8080/categories/get-all",{
        headers:{"Authorization":`Bearer `+localStorage.getItem("token")}
      })
      console.log(response.data);
     setCategory(response.data)
      
    }
    GetAllCategory();
    LoggedInSeller();
  }, [])

  const handleImageChange = (e) => {
    const file = e.target.files[0]
    if (file) {
      setImageFile(file)
      setImagePreview(URL.createObjectURL(file))
    }
  }

  const handleAddProduct = async (e) => {
    e.preventDefault();
    setloading(true)

    try {
      const formData = new FormData()

      formData.append("product", new Blob([JSON.stringify({
        name,
        description,
        price: parseFloat(price),
        stockQuantity: parseInt(quantity),
        categoryId: parseInt(categoryId)
      })], { type: "application/json" }))

      if (imageFile) {
        formData.append("image", imageFile)
      }

      const response = await axios.post(AddProductApiPath, formData, {
        headers: {
          "Authorization": `Bearer ` + localStorage.getItem("token")
          
        }
      });

      console.log("Product added:", response.data);
      alert("Product added successfully!");
      navigate("/seller-dashboard")

    } catch (err) {
      console.log("Error:", err.response?.data);
      alert(err.response?.data?.message ?? "Failed to add product");
    } finally {
      setloading(false)
    }
  }

  return (
    <div style={{ backgroundColor: '#f1f3f6', minHeight: '100vh' }}>

      {/* Navbar */}
      <nav className="navbar px-4" style={{ backgroundColor: '#2874f0' }}>
        <span className="navbar-brand fw-bold fs-4" style={{ color: '#ffe500' }}>QuitQ</span>
        <button
          className="btn btn-sm fw-bold ms-auto"
          style={{ backgroundColor: '#ffe500' }}
          onClick={() => navigate("/seller-dashboard")}
        >
          ← Back to Dashboard
        </button>
      </nav>

      <div className="container py-5" style={{ maxWidth: '600px' }}>
        <div className="card border-0 shadow-sm">
          <div className="card-body p-4">

            <h5 className="fw-bold mb-1">Add New Product</h5>
            <p className="text-muted small mb-4">
              Selling as <strong>{seller.name}</strong>
            </p>

            <form onSubmit={handleAddProduct}>

              {/* Product Name */}
              <div className="mb-3">
                <label className="form-label small fw-bold">Product Name *</label>
                <input
                  type="text"
                  className="form-control"
                  placeholder="e.g. iPhone 15 Pro"
                  onChange={(e) => setname(e.target.value)}
                />
              </div>

              {/* Description */}
              <div className="mb-3">
                <label className="form-label small fw-bold">Description</label>
                <textarea
                  className="form-control"
                  placeholder="Describe your product..."
                  rows={3}
                  onChange={(e) => setdescription(e.target.value)}
                />
              </div>

              {/* Price + Stock */}
              <div className="row g-3 mb-3">
                <div className="col-6">
                  <label className="form-label small fw-bold">Price (Rs) *</label>
                  <div className="input-group">
                    <span className="input-group-text">Rs</span>
                    <input
                      type="number"
                      className="form-control"
                      placeholder="89999"
                      onChange={(e) => setprice(e.target.value)}
                    />
                  </div>
                </div>
                <div className="col-6">
                  <label className="form-label small fw-bold">Stock Quantity *</label>
                  <input
                    type="number"
                    className="form-control"
                    placeholder="e.g. 50"
                    onChange={(e) => setquantity(e.target.value)}
                  />
                </div>
              </div>

              {/* Category */}
              <div className="mb-3">
                <label className="form-label small fw-bold">Category *</label>
                <select
                  className="form-select"
                  onChange={(e) => setcategoryId(e.target.value)}
                  value={categoryId}
                >
                  {
                    category.map((c)=>(
                      <option value={c.id}>{c.name}</option>
                    ))
                  }
                </select>
              </div>

              {/* Image Upload */}
              <div className="mb-4">
                <label className="form-label small fw-bold">Product Image</label>
                <input
                  type="file"
                  className="form-control"
                  accept="image/*"
                  onChange={handleImageChange}
                />
                {/* Image Preview */}
                {imagePreview && (
                  <div className="mt-2 text-center p-2 border rounded bg-white">
                    <img
                      src={imagePreview}
                      alt="preview"
                      style={{ width: '100px', height: '100px', objectFit: 'contain' }}
                    />
                    <p className="text-muted small mb-0 mt-1">Image Preview</p>
                  </div>
                )}
              </div>

              {/* Submit */}
              <button
                type="submit"
                className="btn w-100 fw-bold text-white mt-2"
                style={{ backgroundColor: '#2874f0', padding: '12px' }}
                disabled={loading}
              >
                {loading ? (
                  <>
                    <span className="spinner-border spinner-border-sm me-2"></span>
                    Adding Product...
                  </>
                ) : "ADD PRODUCT"}
              </button>

            </form>
          </div>
        </div>
      </div>
    </div>
  )
}

export default AddProduct