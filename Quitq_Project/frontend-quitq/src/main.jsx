import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.jsx'
import {createBrowserRouter, RouterProvider} from 'react-router-dom'
import LoginForm from './Auth/LoginForm.jsx'
import SignUp from './Auth/SignUp.jsx'
import HomePage from './HomeFolder/Home.jsx'
import ProductDetail from './Products/ProductDetail.jsx'
import CartPage from './Products/CartPage.jsx'
import OrdersuccessPage from './Order/Order-successPage.jsx'
import SellerDashboard from './Seller/SellerDashboard.jsx'
import AddProduct from './Products/AddProduct.jsx'
import AdminDashboard from './Admin/AdminDashboard.jsx'
import EditProduct from './Products/EditProduct.jsx'
import UserDashboard from './User/UserDashboard.jsx'
import MyOrder from './User/MyOrder.jsx'
import MyCarts from './User/MyCarts.jsx'
import OrderDetail from './Seller/OrderDetail.jsx'
import UpdateStatus from './Seller/UpdateStatus.jsx'
import { Provider } from 'react-redux'
import { store } from './store.js'
import UserDetail from './Admin/UserDetail.jsx'
import AddCategory from './Admin/AddCategory.jsx'
import AllCategories from './Admin/AllCategories.jsx'
import EditCategory from './Admin/EditCategory.jsx'
import AllOrder from './Admin/AllOrder.jsx'

const routes=createBrowserRouter([
  {
    path:"/",
    element:<App/>
  },
  {
    path:"/login",
    element:<LoginForm/>
  },
  {
    path:"/signup",
    element:<SignUp/>
  },
  {
    path:"/home",
    element:<HomePage/>
  },
  {
    path:"/productdetail/:id",
    element:<ProductDetail/>
  },
  {
    path:"/cartpage",
    element:<CartPage/>
  },
  {
    path:"/order-success",
    element:<OrdersuccessPage/>
  },
  {
    path:"/seller-dashboard",
    element:<SellerDashboard/>
  },
      {
        path:"/add-product",
        element:<AddProduct/>
      }
    ,
    {
      path:"/admin-dashboard",
      element:<AdminDashboard/>
    },
    {
      path:"/edit-product/:id",
      element:<EditProduct/>
    },
    {
      path:"/user-dashboard",
      element:<UserDashboard/>,
      children:[
        {
          path:"myorders",
          element:<MyOrder/>
        },
        {
          path:"mycarts",
          element:<MyCarts/>
        }
      ]
    }
  ,{
    path:"/seeDetail/:id",
    element:<OrderDetail/>
  },
  {
    path:"/userdetail/:id",
    element:<UserDetail/>
  },
  {
    path:"/add-category",
    element:<AddCategory/>
  },
  {
    path:"/allcategory",
    element:<AllCategories/>
  },
  {
    path:"/edit-category/:id",
    element:<EditCategory/>
  },
  {
    path:"/allorder",
    element:<AllOrder/>
  }
])
createRoot(document.getElementById('root')).render(
 <Provider store={store}>
  <RouterProvider router={routes}>
     <App />
  </RouterProvider>
  </Provider>
 
)
