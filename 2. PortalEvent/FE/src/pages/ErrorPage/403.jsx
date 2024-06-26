import "./style.css";
import {Link} from "react-router-dom";
import logoFpoly from "../../assets/img/Logo_FPoly.png";
import logoUDPM from "../../assets/img/logo-udpm-dark.png";
import {portIdentity} from "../ApiUrl";

export default function Forbidden(){
    return(
        <div className="content-error">
            <div className="logo">
                <img width="20%" src={logoFpoly} alt="Logo"/>
                <img width="20%" src={logoUDPM} alt="Logo"/>
            </div>
            <h1>403!</h1>
            <h2>Forbidden</h2>
            <a href={portIdentity}>Đăng nhập lại</a>
        </div>
    )
  }