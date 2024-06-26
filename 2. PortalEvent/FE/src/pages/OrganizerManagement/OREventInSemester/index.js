import { Button, Col, Form, Row, Select, Table } from "antd";
import { Option } from "antd/es/mentions";
import Title from "antd/es/typography/Title";
import { useEffect } from "react";
import { useState } from "react";
import { OREventInSemesterApi } from "./OREventInSemesterApi";
import { Tag, Tooltip } from "antd/lib";
import Input from "antd/lib/input/Input";
import {
  FileExcelOutlined,
  EyeOutlined,
  FilterOutlined,
} from "@ant-design/icons";
import * as XLSX from "xlsx/xlsx.mjs";
import { Link } from "react-router-dom";

export default function OREventInSemester() {
  const [params, setParams] = useState({});
  const [listEvent, setListEvent] = useState([]);
  const [totalPages, setTotalPages] = useState(0);
  const [currentPage, setCurrentPage] = useState(1); // Trang hiện tại
  const [pageSize, setPageSize] = useState(10); // Số mục trên mỗi trang
  const [semester, setSemester] = useState([]);
  const [listExport, setListExport] = useState([]);
  const [form] = Form.useForm();

  const [organizers, setOrganizers] = useState([]);
  useEffect(() => {
    OREventInSemesterApi.fetchAll({})
      .then((response) => {
        setListEvent(response.data.data);
        setTotalPages(response.data.totalPages);
      })
      .catch((e) => {
        //  //console.log(e);
      });
    OREventInSemesterApi.getListExport({})
      .then((response) => {
        setListExport(response.data.data);
      })
      .catch((e) => {
        //  //console.log(e);
      });
    OREventInSemesterApi.getAllSemester().then((response) => {
      setSemester(response.data);
    });
    OREventInSemesterApi.getAllOrganizer().then((response) => {
      setOrganizers(response.data);
    });
  }, []);
  useEffect(() => {
    OREventInSemesterApi.fetchAll(params)
      .then((response) => {
        setListEvent(response.data.data);
        setTotalPages(response.data.totalPages);
      })
      .catch((e) => {
        //  //console.log(e);
      });
    OREventInSemesterApi.getListExport(params)
      .then((response) => {
        setListExport(response.data.data);
      })
      .catch((e) => {
        //  //console.log(e);
      });
  }, [params]);

  const columns = [
    {
      title: "#",
      dataIndex: "index",
      key: "index",
    },
    {
      title: "Trạng thái",
      dataIndex: "status",
      key: "status",
      render: (x) => (
        <Tag
          color={
            x === 0
              ? "#f50"
              : x === 1
                ? "#55acee"
                : x === 2
                  ? "#f50"
                  : x === 3
                    ? "#55acee"
                    : x === 4
                      ? "#55acee"
                      : x === 5
                        ? "#87d068"
                        : "-"
          }
        >
          {x === 0
            ? "Đã đóng"
            : x === 1
              ? "Dự kiến tổ chức"
              : x === 2
                ? "Cần sửa"
                : x === 3
                  ? "Chờ phê duyệt"
                  : x === 4
                    ? "Đã phê duyệt"
                    : x === 5
                      ? "Đã tổ chức"
                      : "-"}
        </Tag>
      ),
    },
    {
      title: "Thể loại",
      dataIndex: "category",
      key: "category",
    },
    {
      title: "Tên sự kiện",
      dataIndex: "name",
      key: "name",
    },
    {
      title: "Đối tượng",
      dataIndex: "object",
      key: "object",
    },
    {
      title: "SLSV dự kiến",
      dataIndex: "expectedParticipant",
      key: "expectedParticipant",
      render: (x) => (x === null ? 0 : x),
    },
    {
      title: "SLSV tham gia",
      dataIndex: "numberParticipant",
      key: "numberParticipant",
      render: (x) => (x === null ? 0 : x),
    },
    {
      title: "Hình thức",
      dataIndex: "formality",
      key: "formality",
    },
    {
      title: "Người tổ chức",
      dataIndex: "organizer",
      key: "organizer",
    },
    {
      title: "Hành động",
      dataIndex: "id",
      key: "action",
      render: (x) => (
        <Link to={`/organizer-management/event-detail/${x}`}>
          <Tooltip title="Xem chi tiết">
            <Button type="primary">
              <EyeOutlined />
            </Button>
          </Tooltip>
        </Link>
      ),
    },
  ];

  const handleExportExcel = () => {
    //  //console.log(listExport);
    const excelData = listExport.map((item) => ({
      STT: item.index,
      "Trạng thái":
        item.status === 0
          ? "Đã đóng"
          : item.status === 1
            ? "Dự kiến tổ chức"
            : item.status === 2
              ? "Cần sửa"
              : item.status === 3
                ? "Chờ phê duyệt"
                : item.status === 4
                  ? "Đã phê duyệt"
                  : item.status === 5
                    ? "Đã tổ chức"
                    : "-",
      "Thể loại": item.category,
      "Tên sự kiện": item.name,
      "Đối tượng": item.object || "",
      "SL sinh viên dự kiến": item.expectedParticipant || "",
      "SL sinh viên tham gia": item.numberParticipant || "",
      "Hình thức": item.formality || "",
      "Người tổ chức": item.organizer || "",
    }));
    const ws = XLSX.utils.json_to_sheet(excelData);
    ws["!auto"] = 1;
    const wb = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, "Sheet1");
    XLSX.writeFile(wb, "data.xlsx");
  };

  return (
    <>
      <div
        style={{
          backgroundColor: "#FFF",
          padding: "10px",
          borderRadius: "10px",
          marginBottom: "10px",
          boxShadow: "0 2px 4px rgba(0, 0, 0, 0.1)",
        }}
      >
        <Title level={5}>
          <FilterOutlined /> Bộ lọc
        </Title>
        <Form
          layout="vertical"
          onFinish={(data) => setParams(data)}
          form={form}
        >
          <Row gutter={12} style={{ marginBottom: "10px" }}>
            <Col flex="1 1 200px">
              <Form.Item name={"name"} label="Tên sự kiện">
                <Input />
              </Form.Item>
            </Col>
            <Col flex="1 1 50px">
              <Form.Item name={"semester"} label="Học kỳ">
                <Select
                  showSearch
                  placeholder="Nhập học kỳ..."
                  optionFilterProp="children"
                  filterOption={(input, option) =>
                    (option?.name ?? "")
                      .toLowerCase()
                      .includes(input.toLowerCase())
                  }
                  options={semester}
                />
              </Form.Item>
            </Col>
            <Col flex="1 1 100px">
              <Form.Item name={"organizer"} label="Người tổ chức">
                <Select
                  showSearch
                  placeholder="Nhập tên người tổ chức..."
                  optionFilterProp="children"
                  filterOption={(input, option) =>
                    (option?.label ?? "")
                      .toLowerCase()
                      .includes(input.toLowerCase())
                  }
                  options={organizers}
                />
              </Form.Item>
            </Col>
            <Col>
              <Form.Item label="​">
                <Button
                  type="primary"
                  danger
                  onClick={() => form.resetFields()}
                  style={{ marginRight: "3px" }}
                >
                  Làm mới
                </Button>
                <Button
                  type="primary"
                  htmlType="submit"
                  icon={<FilterOutlined />}
                >
                  Lọc
                </Button>
              </Form.Item>
            </Col>
          </Row>
        </Form>
      </div>
      <div
        style={{
          backgroundColor: "#FFF",
          padding: "10px",
          borderRadius: "10px",
        }}
      >
        <Row gutter={12}>
          <Col flex="8 1">
            <Title level={4}>Danh sách sự kiện trong kỳ</Title>
          </Col>
          <Col flex="1 1">
            <Button
              type="primary"
              style={{ backgroundColor: "#1E6E43" }}
              onClick={() => handleExportExcel()}
            >
              <FileExcelOutlined /> Export Excel
            </Button>
          </Col>
        </Row>
        <Table
          id="my-table"
          style={{ marginTop: "10px" }}
          dataSource={listEvent}
          columns={columns}
          pagination={{
            showSizeChanger: true,
            current: currentPage,
            pageSize: pageSize,
            pageSizeOptions: [10, 20, 50, 100],
            showQuickJumper: true,
            total: totalPages * 10,
            onChange: (page, pageSize) => {
              const data = { ...params };
              data.size = pageSize;
              data.page = page - 1;
              setCurrentPage(page);
              setPageSize(pageSize);
              setParams(data);
            },
          }}
          scroll={{
            x: 1300,
          }}
        />
      </div>
    </>
  );
}
