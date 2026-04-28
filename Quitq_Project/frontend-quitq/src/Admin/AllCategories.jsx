import axios from 'axios'
import React, { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'

const AllCategories = () => {
    const [categories, setCategories] = useState([])
const navigate=useNavigate()
    useEffect(() => {
        const FetchAllCategories = async () => {
            const response = await axios.get("http://localhost:8080/categories/get-all")
            console.log(response.data)
            setCategories(response.data)
        }
        FetchAllCategories()
    }, [])
const handleEdit=(catId)=>{
navigate(`/edit-category/${catId}`)
}
const handleDelete=async(catId)=>{
await axios.delete(`http://localhost:8080/categories/delete/${catId}`,{
    headers:{
        "Authorization":`Bearer `+localStorage.getItem("token")
    }
})
// deleting from ui
const temp=categories.filter((c)=>!(c.id==catId))
setCategories(temp)

}
    return (
        <div className="container mt-4">
            <h2 className="mb-4 fw-bold text-primary">All Categories</h2>
            <div className="row">
                {
                    categories.map((c, index) => (
                        <div className="col-md-4 mb-4" key={index}>
                            <div className="card h-100 shadow-sm border-0">
                                <div className="card-body">
                                    <h3 className="card-title fw-semibold">{c.name}</h3>
                                    <p className="card-text text-muted">{c.description}</p>
                                </div>
                                <div>
                                    <button type="button" class="btn btn-primary" onClick={()=>handleEdit(c.id)}>Edit</button>
                                    <button type="button" class="btn btn-danger" onClick={()=>handleDelete(c.id)}>Delete</button>
                                
                            </div>
                            </div>
                            
                        </div>
                    ))
                }
            </div>
        </div>
    )
}

export default AllCategories