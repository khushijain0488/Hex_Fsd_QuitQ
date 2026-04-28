import axios from 'axios'
import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom'

const AddCategory = () => {
    const [name, setName] = useState("")
    const [description, setdescription] = useState("")
    const [success, setSuccess] = useState(false)
    const [error, setError] = useState("")
    const navigate=useNavigate()
    const handleSubmitevent = async (e) => {
        e.preventDefault();
        setSuccess(false);
        setError("");
        try {
            await axios.post("http://localhost:8080/categories/add", {
                "name": name,
                "description": description
            }, {
                headers: {
                    "Authorization": `Bearer ` + localStorage.getItem("token")
                }
            });
            setSuccess(true);
            setName("");
            setdescription("");
            navigate("/allcategory")
        } catch (err) {
            setError(err.response?.data?.message ?? "Failed to add category");
        }
    }

    return (
        <div className="d-flex justify-content-center align-items-center" style={{ minHeight: '100vh', backgroundColor: '#f1f3f6' }}>
            <div className="card shadow-sm" style={{ width: '100%', maxWidth: '480px', borderRadius: '12px', border: 'none' }}>

                {/* Card Header */}
                <div className="card-header text-white fw-bold fs-5 py-3 px-4" style={{ backgroundColor: '#2874f0', borderRadius: '12px 12px 0 0' }}>
                    Add New Category
                </div>

                {/* Card Body */}
                <div className="card-body p-4">

                    {/* Success Alert */}
                    {success && (
                        <div className="alert alert-success py-2 px-3" role="alert">
                             Category added successfully!
                        </div>
                    )}

                    {/* Error Alert */}
                    {error && (
                        <div className="alert alert-danger py-2 px-3" role="alert">
                             {error}
                        </div>
                    )}

                    <form onSubmit={(e) => handleSubmitevent(e)}>

                        {/* Category Name */}
                        <div className="mb-3">
                            <label className="form-label fw-semibold text-secondary small">Category Name</label>
                            <input
                                type="text"
                                className="form-control"
                                placeholder="e.g. Electronics"
                                value={name}
                                onChange={(e) => setName(e.target.value)}
                                required
                            />
                        </div>

                        {/* Description */}
                        <div className="mb-4">
                            <label className="form-label fw-semibold text-secondary small">Description</label>
                            <textarea
                                className="form-control"
                                placeholder="Enter category description..."
                                rows={3}
                                value={description}
                                onChange={(e) => setdescription(e.target.value)}
                                required
                            />
                        </div>

                        {/* Submit Button */}
                        <button
                            type="submit"
                            className="btn w-100 fw-bold text-white py-2"
                            style={{ backgroundColor: '#fb641b', border: 'none', borderRadius: '6px' }}
                        >
                            Add Category
                        </button>

                    </form>
                </div>
            </div>
        </div>
    )
}

export default AddCategory