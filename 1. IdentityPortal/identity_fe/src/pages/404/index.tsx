import { Button, Result } from "antd";
import { useNavigate } from "react-router-dom";

export default function NotFound() {
  const navigate = useNavigate();

  return (
    <div className='mt-5'>
      <Result
        status='404'
        title='404'
        subTitle='Xin lỗi, trang bạn truy cập không tồn tại.'
        extra={
          <Button
            type='primary'
            onClick={() => navigate("/")}
            size='large'
            className='mt-4 bg-[#253741] border-[#253741] text-white'
          >
            Quay lại
          </Button>
        }
      />
    </div>
  );
}
