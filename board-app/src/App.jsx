import { createBrowserRouter, Outlet, RouterProvider, Link } from 'react-router-dom';
import './App.css';
import BoardList from './board/BoardList';
import BoardDetail from './board/BoardDetail';
import BoardWrite from './board/BoardWrite';

const Layout = () => (
  <>
    <nav>
      <Link to="/list">게시판 목록</Link>
      :
      <Link to="/detail/8">게시판 상세</Link>
      :
      <Link to="/write">게시판 글쓰기</Link>
    </nav>
    <Outlet />
  </>
);

const router = createBrowserRouter([
  {
    path: "/", 
    element: <Layout />,
    children: [
      { path: "", element: <BoardList /> }, 
      { path: "list", element: <BoardList /> }, 
      { path: "detail/:boardIdx", element: <BoardDetail /> }, 
      { path: "write", element: <BoardWrite /> }, 
    ]
  }
])

export default function App() {
  return <RouterProvider router={router} />
}


