export function dateTimeFromLong(time) {
    let resultDate = new Date(time);
    const day = resultDate.getDate();
    const month = resultDate.getMonth() + 1;
    const year = resultDate.getFullYear();
    const hours = resultDate.getHours();
    const minutes = resultDate.getMinutes();
    return day + '/' + month + '/' + year + ' ' + hours + ':' + minutes;
}

export function dateFromLong(time) {
    let resultDate = new Date(time);
    const day = resultDate.getDate();
    const month = resultDate.getMonth() + 1;
    const year = resultDate.getFullYear();
    const hours = resultDate.getHours();
    const minutes = resultDate.getMinutes();
    return day + '/' + month + '/' + year;
}

export function formatDate(date) {
    const day = String(date.getDate()).padStart(2, '0');
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const year = date.getFullYear();
    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');

    return `${day}/${month}/${year} ${hours}:${minutes}`;
}


export function formatDateHome(dateHome) {

    // Tách giờ và ngày thành phần từ chuỗi
    let [time, date] = dateHome.split(" ");

    // Tách giờ và phút
    let [hour, minute] = time.split(":");

    // Tách ngày, tháng và năm
    let [day, month, year] = date.split("/");

    // Tạo đối tượng Date mới với các thành phần trích xuất được
    let convertedDate = new Date(year, month - 1, day, hour, minute); // month - 1 vì tháng bắt đầu từ 0

    return convertedDate;
}

export function parseDateFromString(dateString) {
    const [day, month, yearAndTime] = dateString.split('/');
    const [year, time] = yearAndTime.split(' ');

    const [hours, minutes] = time.split(':');

    // Month trong đối tượng Date được đếm từ 0 (0 là tháng 1, 11 là tháng 12)
    const dateObject = new Date(year, month - 1, day, hours, minutes);

    return dateObject;
}