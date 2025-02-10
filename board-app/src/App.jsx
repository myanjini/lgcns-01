import { createBrowserRouter, Outlet, RouterProvider, Link, useNavigate } from 'react-router-dom';
import './App.css';
import BoardList from './board/BoardList';
import BoardDetail from './board/BoardDetail';
import BoardWrite from './board/BoardWrite';
import Login from './user/Login';
import { jwtDecode } from 'jwt-decode';

const Layout = () => {
  const navigate = useNavigate();

  let decoded = "";
  const token = sessionStorage.getItem("token");
  if (token) {
    decoded = jwtDecode(token);
    console.log(decoded.name);
  } else {
    navigate("/");
  } 

  const doLogout = e => {
    e.preventDefault();

    sessionStorage.removeItem("token");
    navigate("/");
  };

  return (
  <>
    <nav>
      { token && (
        <>
          "{decoded.name}"님 반갑습니다.
          [<a onClick={doLogout}>로그아웃</a>]
        </>
      ) }
    </nav>
    <Outlet />
  </>
)};

const router = createBrowserRouter([
  {
    path: "/", 
    element: <Layout />,
    children: [
      { path: "", element: <Login /> }, 
      { path: "list", element: <BoardList /> }, 
      { path: "detail/:boardIdx", element: <BoardDetail /> }, 
      { path: "write", element: <BoardWrite /> }, 
    ]
  }
])

export default function App() {
  return <RouterProvider router={router} />
}


