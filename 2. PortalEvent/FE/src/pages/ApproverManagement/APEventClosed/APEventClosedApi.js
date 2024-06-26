import { request } from "../../../helper/Request.helper";
import { URL_API_APRROVER_MANAGEMENT } from "../../ApiUrl";

export class APEventClosedApi {
    // Lấy ra danh sách sự kiện đã đóng
    static fetchAll = (data) => {
        return request({
            method: "GET",
            url: URL_API_APRROVER_MANAGEMENT + `/event-closed`,
            params: {
                category: data?.category,
                object: data?.object,
                major: data?.major,
                name: data?.name,
                semester: data?.semester,
                page: data?.page,
                size: data?.size,
            }
        });
    };

    
    // Lấy ra danh sách thể loại
    static fetchCategory = () => {
        return request({
            method: "GET",
            url: URL_API_APRROVER_MANAGEMENT + `/event-closed/category-list`
        });
    }
    
    // Lấy ra danh sách đối tượng
    static fetchObject = () => {
        return request({
            method: "GET",
            url: URL_API_APRROVER_MANAGEMENT + `/event-closed/object-list`
        });
    }
        
    // Lấy ra danh sách bộ môn
    static fetchMajor = () => {
        return request({
            method: "GET",
            url: URL_API_APRROVER_MANAGEMENT + `/event-closed/major-list`
        });
    }
    
    // Lấy ra danh sách học kỳ
    static fetchSemester = () => {
        return request({
            method: "GET",
            url: URL_API_APRROVER_MANAGEMENT + `/event-closed/semester-list`
        });
    }
}