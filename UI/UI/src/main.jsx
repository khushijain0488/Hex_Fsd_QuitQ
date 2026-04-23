import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'

import App from './App.jsx'
import {createBrowserRouter, RouterProvider} from 'react-router-dom'
import UserList from './Component/UserList.jsx'
import AddUser from './Component/AddUser.jsx'
const routes=createBrowserRouter([
  {
    path:"/",
    element:<App/>,
    children:[
        {
    path:"users",
    element:<UserList/>
  },
  {
    path:"add-user",
    element:<AddUser/>
  }
    ]

  }
])
createRoot(document.getElementById('root')).render(
  <RouterProvider router={routes}>
  <StrictMode>
    <App />
  </StrictMode>
  </RouterProvider>

)
