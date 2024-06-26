// import React from "react";
// import * as FileSaver from "file-saver";
// import * as XLSX from "xlsx";
// import { Button } from "antd";
// import moment from "moment";
//
// const ExportCSV = ({ csvData, fileName }) => {
//     const formattedData = csvData.map(item => ({
//         ...item,
//         status: item.status === "1" ? "Vừa đăng ký" : item.status === "2" ? "Cần sửa" : item.status === "3" ? "Chờ phê duyệt" : "",
//         startTime: moment(item.startTime).format('YYYY-MM-DD HH:mm:ss'),
//         endTime: moment(item.endTime).format('YYYY-MM-DD HH:mm:ss')
//     }));
//
//     const fileType =
//       "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8";
//     const fileExtension = ".xlsx";
//
//     const exportToCSV = (formattedData, fileName) => {
//       const ws = XLSX.utils.json_to_sheet(formattedData);
//       const wb = { Sheets: { data: ws }, SheetNames: ["data"] };
//
//       const excelBuffer = XLSX.write(wb, { bookType: "xlsx", type: "array" });
//       const data = new Blob([excelBuffer], { type: fileType });
//
//       FileSaver.saveAs(data, fileName + fileExtension);
//     };
//
//     return (
//       <Button onClick={() => exportToCSV(formattedData, fileName)}>
//         Export data
//       </Button>
//     );
//   };
//
// export default ExportCSV;
