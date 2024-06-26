import "./style.css";
import {portIdentity} from "../ApiUrl";
import {useLocation} from "react-router-dom";
import logoUDPM from '../../assets/img/logo-udpm-dark.png';
import logoFpoly from '../../assets/img/Logo_FPoly.png';

export default function NotAceptable() {
    const location = useLocation();
    const status = location.pathname.split('=')[1];
    let message = '';
    if (status === 'session-expired') {
        message = 'Phiên đăng nhập hết hạn';
    }
    if (status === 'role-has-change') {
        message = 'Quyền truy cập của bạn đã bị thay đổi';
    }
    return (
        <div className="content-error">
            <div className="logo">
                <img width="20%" src={logoFpoly} alt="Logo"/>
                <img width="20%" src={logoUDPM} alt="Logo"/>
            </div>
            <h1>NOT ACEPTABLE!</h1>
            <h2>{message}</h2>
            <a href={portIdentity}>Đăng nhập lại</a>
        </div>
    )
}