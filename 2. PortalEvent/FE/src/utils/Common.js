import Cookies from "js-cookie";
import jwtDecode from "jwt-decode";
import { ACTOR_TM, ACTOR_CNBM, ACTOR_GV, ACTOR_SV, ACTOR_BDT_CS, ACTOR_BDT, ACTOR_TBDT_CS } from "../constants/ActorConstant";

export function getCurrentUser() {
    const token = Cookies.get("token");
    if (token) {
        try {
            const payload = jwtDecode(token);
            return {
                exp: payload.exp,
                email: payload.email,
                id: payload.userId,
                name: payload.fullName,
                avatar: payload.pictureUrl,
                role: payload.rolesCode,
                userName: payload.userName,
                subjectCode: payload.subjectCode,
                trainingFacilityCode: payload.trainingFacilityCode

            };
        } catch (err) {
            window.location.href = "/not-authorization";
        }
    } else {
        window.location.href = "/not-authorization";
    }
    return undefined;
}

export function convertRole(role) {
    if (role === ACTOR_TM) {
        return 'Trưởng môn'
    } else if (role === ACTOR_CNBM) {
        return 'Chủ nhiệm bộ môn';
    } else if (role === ACTOR_GV) {
        return 'Giảng viên';
    } else if (role === ACTOR_SV) {
        return 'Sinh viên';
    } else if (role === ACTOR_BDT_CS) {
        return 'Quản Lý Đào Tạo'
    } else if (role === ACTOR_TBDT_CS) {
        return 'Trưởng ban đào tạo cơ sở'
    } else if (role === ACTOR_BDT) {
        return 'Ban tào tạo'
    }
    // else if (role === ACTOR_ADMINISTRATIVE) {
    //     return 'Hành chính';
    // } 

}