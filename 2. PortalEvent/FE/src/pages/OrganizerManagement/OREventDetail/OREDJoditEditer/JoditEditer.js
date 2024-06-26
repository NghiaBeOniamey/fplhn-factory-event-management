import React from 'react';
import JoditEditor from 'jodit-react';

const FormEditor = ({value, onchange, isDisable}) => {
    const config = isDisable
        ? {
            readonly: true,
            toolbar: false,
            showCharsCounter: false,
            showWordsCounter: false,
            showStatusbar: false,
            showPoweredBy: false
        }
        : {};
    return (
        <JoditEditor
            ref={null}
            value={value}
            onBlur={onchange}
            tabIndex={1}
            config={config}
        />
    );
};

export default FormEditor;


