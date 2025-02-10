import { useEffect, useState } from "react";
import axios from "axios";
import { Link, useNavigate } from "react-router-dom";

export default function BoardList() {
    const [datas, setDatas] = useState([]);

    const navigate = useNavigate();

    useEffect(() => {
        const token = sessionStorage.getItem("token");

        axios
        .get("http://localhost:8080/api/v2/board", {headers: {"Authorization": `Bearer ${token}`}})
        .then(res => {
            console.log(res);
            res && res.data && setDatas(res.data);
        })
        .catch(err => {
            console.log(err);
            if (err.status === 401) {
                alert("[인증 토큰 누락] 로그인 후 다시 시도해 주세요.");
            } else if (err.status === 403) {
                alert("[인증 토큰 오류] 로그인 후 다시 시도해 주세요.");
            }
            navigate("/");
        });
    }, []);

    return (
        <>
            <div className="container">
                <h2>게시판 목록</h2>
                <table className="board_list">
                    <colgroup>
                        <col width="15%" />
                        <col width="*" />
                        <col width="15%" />
                        <col width="20%" />
                    </colgroup>
                    <thead>
                        <tr>
                            <th scope="col">글번호</th>
                            <th scope="col">제목</th>
                            <th scope="col">조회수</th>
                            <th scope="col">작성일</th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            datas.length > 0 && datas.map(board => (
                                <tr key={board.boardIdx}>
                                    <td>{board.boardIdx}</td>
                                    <td className="title">
                                        <Link to={`/detail/${board.boardIdx}`}>{board.title}</Link>
                                    </td>
                                    <td>{board.hitCnt}</td>
                                    <td>{board.createdDt}</td>
                                </tr>
                            ))
                        }
                        {
                            datas.length === 0 && (
                                <tr>
                                    <td colSpan="4">조회된 결과가 없습니다.</td>
                                </tr>
                            )
                        }
                    </tbody>
                </table>
                <Link to="/write" className="btn">글쓰기</Link>
            </div>
        </>
    );
}