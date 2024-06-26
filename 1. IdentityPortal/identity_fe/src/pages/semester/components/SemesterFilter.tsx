import { Button, Form, Input, message } from "antd";
import { Dispatch, SetStateAction, memo, useState } from "react";

const SemesterFilter = ({
  setSearchValue,
}: {
  setSearchValue: Dispatch<SetStateAction<any>>;
}) => {
  const [name, setName] = useState<string | null | undefined>();
  // const [semesterRange, setSemesterRange] = useState<
  //   [Dayjs | null, Dayjs | null]
  // >([null, null]);
  // const [block1Range, setBlock1Range] = useState<[Dayjs | null, Dayjs | null]>([
  //   null,
  //   null,
  // ]);
  // const [block2Range, setBlock2Range] = useState<[Dayjs | null, Dayjs | null]>([
  //   null,
  //   null,
  // ]);

  const handleFilter = () => {
    if (name === "" || name === undefined || name === null) {
      message.error("Vui lòng nhập thông tin tìm kiếm");
      return;
    }

    setSearchValue({
      semesterName: name ? name?.trim() : null,
      // startTime: semesterRange[0]?.unix() ? semesterRange[0].unix() : null,
      // endTime: semesterRange[1]?.unix() ? semesterRange[1].unix() : null,
      // startTimeFirstBlock: block1Range[0]?.unix()
      //   ? block1Range[0].unix()
      //   : null,
      // endTimeFirstBlock: block1Range[1]?.unix() ? block1Range[1].unix() : null,
      // startTimeSecondBlock: block2Range[0]?.unix()
      //   ? block2Range[0].unix()
      //   : null,
      // endTimeSecondBlock: block2Range[1]?.unix() ? block2Range[1].unix() : null,
    });
  };

  return (
    <Form layout='vertical' className='semester-filter-form p-3'>
      {/* <Row gutter={16} className='mb-4'> */}
      {/* <Col span={6}> */}
      <Form.Item label='Tên Học Kỳ'>
        <Input
          placeholder='Nhập tên học kỳ'
          value={name}
          onChange={(e) => setName(e.target.value)}
          allowClear
        />
      </Form.Item>
      {/* </Col> */}
      {/* <Col span={6}>
          <Form.Item label='Thời Gian Học Kỳ'>
            <RangePicker
              onChange={(dates) =>
                setSemesterRange(dates ? [dates[0], dates[1]] : [null, null])
              }
              value={semesterRange}
              className='w-[100%]'
            />
          </Form.Item>
        </Col>
        <Col span={6}>
          <Form.Item label='Thời Gian Block 1'>
            <RangePicker
              onChange={(dates) =>
                setBlock1Range(dates ? [dates[0], dates[1]] : [null, null])
              }
              className='w-[100%]'
              value={block1Range}
            />
          </Form.Item>
        </Col>
        <Col span={6}>
          <Form.Item label='Thời Gian Block 2'>
            <RangePicker
              onChange={(dates) =>
                setBlock2Range(dates ? [dates[0], dates[1]] : [null, null])
              }
              className='w-[100%]'
              value={block2Range}
            />
          </Form.Item>
        </Col>
      </Row> */}
      <div className='container flex justify-center'>
        <Button
          type='primary'
          onClick={handleFilter}
          color='#052C65'
          className='bg-[#052C65] text-white'
        >
          Tìm Kiếm
        </Button>
        <Button
          type='primary'
          onClick={() => {
            setName(null);
            setSearchValue({
              semesterName: null,
              // startTime: null,
              // endTime: null,
              // startTimeFirstBlock: null,
              // endTimeFirstBlock: null,
              // startTimeSecondBlock: null,
              // endTimeSecondBlock: null,
            });
          }}
          className='ml-2 bg-[#052C65] text-white'
          color='#052C65'
        >
          Xóa Bộ Lọc
        </Button>
      </div>
    </Form>
  );
};

export default memo(SemesterFilter);
