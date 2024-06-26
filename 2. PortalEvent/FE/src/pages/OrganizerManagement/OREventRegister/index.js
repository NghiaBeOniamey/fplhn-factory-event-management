// import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
// import { Button, Col, DatePicker, Form, Input, Modal, Row, Select, message, Table } from 'antd';
import React, { useEffect, useState } from 'react';
// import JoditEditor, { Jodit } from 'jodit-react';
// import FormItem from 'antd/es/form/FormItem';
// import { faCheck, faPlus, faTrashCan } from '@fortawesome/free-solid-svg-icons';
// import TextArea from 'rc-textarea';
// import dayjs from 'dayjs';
import { AppContextregister } from './context';
// import OREventRegisterApi from './OREventRegisterApi';
// import { render } from '@testing-library/react';
// import { Option } from 'rc-select';
import OREventRegisterWrapper from './OREventRegisterWrapper';
import { ACTOR_CNBM, ACTOR_GV, ACTOR_TM } from '../../../constants/ActorConstant';
import { getCurrentUser } from '../../../utils/Common';

export default function OREventRegister() {
    const [name, setName] = useState("");
    const [nameBlock, setNameBlock] = useState("");
    const [nameSemester, setNameSemester] = useState("");
    const [formality, setFormality] = useState();
    const [startTime, setStartTime] = useState("");
    const [endTime, setEndTime] = useState("");
    const [idCategory, setIdCategory] = useState("");
    const [idSemester, setIdSemester] = useState("");
    const [idObject, setIdObject] = useState([]);
    const [idMajor, setIdMajor] = useState([]);
    const [idDepartment, setIdDepartment] = useState([]);
    const [location, setLocation] = useState("");
    const [contentEditor, setContentEditor] = useState("");
    const [listResources, setListResources] = useState([]);
    const [bannerPath, setBannerPath] = useState("");
    const [backfroundPath, setBackgroundPath] = useState("");
    const [listSemester, setListSemester] = useState([]);
    const [listObject, setListObject] = useState([]);
    const [listMajor, setListMajor] = useState([]);
    const [listMajorShow, setListMajorShow] = useState([]);
    const [listDepartment, setListDepartment] = useState([]);
    const [listDepartmentByCampus, setListDepartmentByCampus] = useState([]);
    const [listMajorCampus, setListMajorCampus] = useState([]);
    const [listCategory, setListCategory] = useState([]);
    const [listAgenda, setListAgenda] = useState([]);
    const [listLocation, setListLocation] = useState([]);
    const [expectedParticipants, setExpectedParticipants] = useState(null);
    const [eventType, setEventType] = useState(null);
    const [standeePath, setStandeePath] = useState('');
    const [blockNumber, setBlockNumber] = useState(false);
    const [isCNBM, setIsCNBM] = useState(false);
    const [isGV, setIsGV] = useState(false);
    const [subjectCode, setSubjectCode] = useState();
    const [trainingFacilityCode, setTrainingFacilityCode] = useState();

    const enableRole = () => {
        const user = getCurrentUser();
        if (typeof user.role === 'object') {
            if (user.role.includes(ACTOR_GV)) {
                setIsGV(true);
            }
            if (user.role.includes(ACTOR_TM) || user.role.includes(ACTOR_CNBM)) {
                setIsCNBM(true);
                setIsGV(true);
            }
            // if (user.role.includes(ACTOR_BDT_CS)) {
            //     setIsAdmin(true);
            //     setIsApprover(true);
            //     setIsOrganizer(true);
            // }
            // if (user.role.includes(ACTOR_TBDT_CS)) {
            //     setIsAdmin(true);
            //     setIsApprover(true);
            //     setIsOrganizer(true);
            //     setIsAdminHO(true);
            // }
        } else if (typeof user.role === 'string') {
            user.role.includes(ACTOR_GV) ? setIsGV(true) : setIsGV(false);
            user.role.includes(ACTOR_CNBM) ? setIsCNBM(true) && setIsGV(true) : setIsCNBM(false);
        }
        setSubjectCode(user.subjectCode);
        setTrainingFacilityCode(user.trainingFacilityCode);
    }

    useEffect(() => {
        enableRole();
    }, []);

    return (
        <AppContextregister.Provider
            value={{
                name, setName,
                idCategory, setIdCategory,
                startTime, setStartTime,
                endTime, setEndTime,
                formality, setFormality,
                idSemester, setIdSemester,
                idObject, setIdObject,
                idMajor, setIdMajor,
                idDepartment, setIdDepartment,
                contentEditor, setContentEditor,
                bannerPath, setBannerPath,
                backfroundPath, setBackgroundPath,
                listResources, setListResources,
                listSemester, setListSemester,
                listObject, setListObject,
                listMajor, setListMajor,
                listMajorShow, setListMajorShow,
                listDepartment, setListDepartment,
                listDepartmentByCampus, setListDepartmentByCampus,
                listMajorCampus, setListMajorCampus,
                listCategory, setListCategory,
                location, setLocation,
                listAgenda, setListAgenda,
                listLocation, setListLocation,
                eventType, setEventType,
                standeePath, setStandeePath,
                blockNumber, setBlockNumber,
                expectedParticipants, setExpectedParticipants,
                isCNBM, setIsCNBM,
                isGV, setIsGV,
                subjectCode, setSubjectCode,
                trainingFacilityCode, setTrainingFacilityCode
            }}>
            <OREventRegisterWrapper />
        </AppContextregister.Provider>
    );
}
