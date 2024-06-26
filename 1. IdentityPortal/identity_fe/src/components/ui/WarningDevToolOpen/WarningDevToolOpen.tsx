import { Result } from "antd";
import "./WarningDevToolOpen.css";

const WarningDevToolOpen = () => {
  return (
    <div>
      <div className='mt-20'>
        <Result
          status='warning'
          title='Chế độ này dành cho nhà phát triển'
          extra={<h3>Vui lòng tải lại trang web để tiếp tục.</h3>}
        />
      </div>
    </div>
  );
};

export default WarningDevToolOpen;
