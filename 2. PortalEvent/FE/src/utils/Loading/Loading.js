import {Spin} from 'antd';

const loadingStyle = {
    position: "fixed",
    top: 0,
    left: 0,
    width: "100%",
    height: "100%",
    backgroundColor: "rgba(0, 0, 0, 0.5)",
    zIndex: 9999,
    display: "flex",
    alignItems: "center",
    justifyContent: "center",
};

const Loading = () => {
    return <div style={loadingStyle}>
        <Spin size="large" tip="Loading..."/>
    </div>;
};

export default Loading;