export interface ResponseObject<T> {
    data: T;
    message: string;
    status: string;
    success: boolean;
}

export interface PageableObject<T> {
    data: T;
    totalPages: number;
    totalElements: number;
    currentPage: number;
}

export interface ModifyModuleRoleOfStaff {
    staffId: number;
    moduleId: number[];
    roleId: number[];
}
