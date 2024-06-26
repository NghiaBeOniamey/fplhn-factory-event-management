import { removeAuthorization } from "#context/redux/slice/AuthSlice";
import { Button } from "antd";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import logoFpoly from "../../assets/image/Logo_FPoly.png";
import logoUDPM from "../../assets/image/logo-udpm-dark.png";
import "./index.css";

export default function NotAuthorized() {
  const dispatch = useDispatch();

  const navigate = useNavigate();

  const reLogin = () => {
    dispatch(removeAuthorization());
    navigate("/");
  };

  return (
    <div className='page-container h-[100vh] flex flex-col justify-between'>
      <div className='content-error'>
        <div className='my-4 flex justify-center items-center'>
          <img width='20%' src={logoFpoly} alt='Logo' />
          <img width='20%' src={logoUDPM} alt='Logo' />
        </div>
        <h2>403!</h2>
        <h3>Not Authorized Error</h3>
        <Button
          onClick={reLogin}
          type='primary'
          className='mt-4 bg-[#253741] border-[#253741] text-white'
          size='large'
        >
          Đăng nhập lại
        </Button>
      </div>
    </div>
  );
}
