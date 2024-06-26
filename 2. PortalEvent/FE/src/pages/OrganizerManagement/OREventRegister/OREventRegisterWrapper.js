import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Button, Col, DatePicker, Form, Input, Modal, Row, Select, Space, TimePicker, Upload, message, Table, Checkbox, Radio } from 'antd';
import React, { useContext, useEffect, useMemo, useState } from 'react';
import JoditEditor, { Jodit } from 'jodit-react';
import FormItem from 'antd/es/form/FormItem';
import { faCheck, faCircleInfo, faPlus, faTrashCan } from '@fortawesome/free-solid-svg-icons';
import TextArea from 'rc-textarea';
import dayjs from 'dayjs';
import { AppContextregister } from './context';
import OREventRegisterApi from './OREventRegisterApi';
import { render } from '@testing-library/react';
import { Option } from 'rc-select';
import {useNavigate} from "react-router-dom";
import WrapperContainner from './WrapperContainner';
import FormComponent from './FormComponent';

const OREventRegisterWrapper = () => {
    const {
        listSemester, setListSemester,
        listObject, setListObject,
        listMajor, setListMajor,
        listMajorShow, setListMajorShow,
        listDepartment, setListDepartment,
        listDepartmentByCampus, setListDepartmentByCampus,
        listMajorCampus, setListMajorCampus,
        listCategory, setListCategory,
    } = useContext(AppContextregister);

    useEffect(() => {
        getCategories();
    }, [], [listCategory]);

    useEffect(() => {
        getSemesters();
    }, [], [listSemester]);

    useEffect(() => {
        getMajors();
    }, [], [listMajor]);

    useEffect(() => {
        getMajors();
    }, [], [listMajorShow]);

    useEffect(() => {
        getDepartments();
    }, [], [listDepartment]);

    useEffect(() => {
        getDepartmentsByCampus();
    }, [], [listDepartmentByCampus]);

    useEffect(() => {
        getMajorsCampus();
    }, [], [listMajorCampus]);

    useEffect(() => {
        getObjects();
    }, [], [listObject]);

    //init category
    const getCategories = () => {
        OREventRegisterApi.getAllCategory()
            .then((response) => {
                setListCategory(response.data.data);
            })
    }

    //init semesters
    const getSemesters = () => {
        OREventRegisterApi.getSemesters()
            .then((response) => {
                setListSemester(response.data.data);
            })
    }

    //init objects
    const getObjects = () => {
        OREventRegisterApi.getObjects()
            .then((response) => {
                setListObject(response.data.data);
            })
    }

    //init major
    const getMajors = () => {
        OREventRegisterApi.getMajors()
            .then((response) => {
                setListMajor(response.data.data);
                setListMajorShow(response.data.data);
            })
    }

    //init department
    const getDepartments = () => {
        OREventRegisterApi.getDepartments()
            .then((response) => {
                setListDepartment(response.data.data);
            })
    }

    //init departmentByCampus
    const getDepartmentsByCampus = () => {
        OREventRegisterApi.getDepartmentsByCampus()
            .then((response) => {
                //console.log(response);
                setListDepartmentByCampus(response.data.data);
            })
    }

    //init majorsCampus
    const getMajorsCampus = () => {
        OREventRegisterApi.getMajorsCampus()
            .then((response) => {
                setListMajorCampus(response.data.data);
            })
    }

    return (
        <div style={{
            backgroundColor: 'white', borderRadius: '0.5rem', boxShadow: '0.1rem 0.1rem 1rem rgba(0, 0, 0, 0.1)'
            , padding: '2rem'
        }}>
            <h3 style={{ margin: '1rem 0 1rem 0' }}>Đăng ký sự kiện</h3>
            <WrapperContainner>
                <h3>
                <FontAwesomeIcon icon={faCircleInfo} style={{marginRight: '0.5rem'}}/>
                    Thông tin của sự kiện</h3>
                <FormComponent />
            </WrapperContainner>
        </div>
    )
}

export default OREventRegisterWrapper;