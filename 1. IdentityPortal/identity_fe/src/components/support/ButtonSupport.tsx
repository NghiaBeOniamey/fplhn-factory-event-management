import { openOrCloseModal } from "#context/redux/slice/ModalSlice";
import { Button } from "antd";
import { FaRegMessage } from "react-icons/fa6";
import { useDispatch } from "react-redux";

export default function ButtonSupport() {
  const dispatch = useDispatch();

  const handleClickOpen = () => {
    dispatch(openOrCloseModal());
  };

  return (
    <div>
      <Button
        type='primary'
        shape='round'
        size='large'
        className='m-4 bg-[#253741] border-[#253741] fixed-button flex items-center justify-center'
        onClick={handleClickOpen}
        icon={<FaRegMessage />}
      >
        Hỗ trợ kỹ thuật
      </Button>
    </div>
  );
}
