import { memo } from "react";

const Welcome = () => {
  return (
    <div className='flex justify-center items-center h-[80vh] shadow-xl rounded-lg'>
      <h1 className='text-4xl font-medium text-center text-gray-800'>
        Portal Identity
      </h1>
    </div>
  );
};

export default memo(Welcome);
