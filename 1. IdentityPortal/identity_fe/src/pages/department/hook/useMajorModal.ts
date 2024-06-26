import {useState} from "react";

export const useMajorModal = () => {
    const [open, setOpen] = useState(false);
    const [departmentId, setDepartmentId] = useState<number | null>(null);

    const handleClose = () => {
        setOpen(false);
    };

    const handleOpen = (departmentId: number) => {
        setDepartmentId(departmentId);
        setOpen(true);
    };

    return {
        open,
        departmentId,
        handleClose,
        handleOpen,
    };
};
