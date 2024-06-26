import { MagnifyingGlass } from "react-loader-spinner";

const MagnifyingGlassLoading = () => {
  return (
    <div
      style={{
        position: "fixed",
        top: "0",
        left: "0",
        width: "100%",
        height: "100%",
        backgroundColor: "rgba(0, 0, 0, 0.5)",
        zIndex: 9999,
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        backdropFilter: "blur(0.1px)",
      }}
    >
      <MagnifyingGlass
        visible={true}
        height='80'
        width='80'
        ariaLabel='magnifying-glass-loading'
        wrapperStyle={{}}
        wrapperClass='magnifying-glass-wrapper'
        glassColor='#c0efff'
        color='#052C65'
      />
    </div>
  );
};

export default MagnifyingGlassLoading;
