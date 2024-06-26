import React from 'react';
import JoditEditor from 'jodit-react';
const FormJoditEditor = ({value, onChange}) => {
    const config = {};
    return (
        <JoditEditor
            ref={null}
            value={value}
            onBlur={onChange}
            tabIndex={1}
            config={config}
        />
    );
};

export default FormJoditEditor;