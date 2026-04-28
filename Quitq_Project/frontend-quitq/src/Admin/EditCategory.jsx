import axios from 'axios'
import React, { useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'

const EditCategory = () => {
    const { id } = useParams()
    const [formData, setFormData] = useState({ name: '', description: '' })
    const navigate = useNavigate()

    useEffect(() => {
        const FetchDetail = async () => {
            const response = await axios.get(`http://localhost:8080/categories/get/${id}`)
            console.log(response.data)
            setFormData({
                name: response.data.name,
                description: response.data.description
            })
        }
        FetchDetail()
    }, [id])

const handleChange = (e) => {
    const { name, value } = e.target
    setFormData(prev => ({ ...prev, [name]: value }))
}

    const handleSubmit = async (e) => {
        e.preventDefault()
        const response = await axios.put(`http://localhost:8080/categories/update/${id}`, formData, {
            headers: {
                "Authorization": `Bearer ` + localStorage.getItem("token")
            }
        })
        console.log("Updated:", response.data)
        alert("Category updated successfully!")
        navigate("/allcategory")
    }

    return (
        <div className="container mt-4">
            <div className="card shadow-sm border-0 p-4">
                <h2 className="mb-4 fw-bold text-primary">Edit Category</h2>
                <form onSubmit={handleSubmit}>
                    <div className="mb-3">
                        <label className="form-label fw-semibold">Enter The Name:</label>
                        <input
                            type="text"
                            className="form-control"
                            name="name"
                            value={formData.name}
                            onChange={handleChange}
                        />
                    </div>
                    <div className="mb-3">
                        <label className="form-label fw-semibold">Enter The Description:</label>
                        <input
                            type="text"
                            className="form-control"
                            name="description"
                            value={formData.description}
                            onChange={handleChange}
                        />
                    </div>
                    <div>
                        <button type="submit" className="btn btn-primary px-4">
                            Update Category
                        </button>
                    </div>
                </form>
            </div>
        </div>
    )
}

export default EditCategory