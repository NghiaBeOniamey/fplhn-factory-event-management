
const WrapperContainner = ({ children }) => {
    return (
        <div style={{
            borderRadius: '0.5rem', boxShadow: '0.1rem 0.1rem 1rem rgba(0, 0, 0, 0.1)', padding: '1.4rem',
            marginTop: '0.5rem', marginBottom: '1.4rem', paddingBottom: '0.4rem',
        }}>
            {children}
        </div>
    );
}

export default WrapperContainner;