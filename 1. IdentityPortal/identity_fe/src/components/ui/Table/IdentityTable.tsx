import useCalcTableScroll from "#hooks/useCalTableScroll";
import { PaginationParams } from "#type/index.t";
import { Empty, Pagination, Table, TableProps } from "antd";
import { Dispatch, SetStateAction, memo } from "react";
import { twMerge } from "tailwind-merge";

export interface IdentityTableProps<T = Record<string, unknown>>
  extends Omit<TableProps<T>, "pagination"> {
  title?: any;
  columns: any[];
  dataSource: any | [];
  handleTableChange?: (pagination: any, filters: any, sorter: any) => void;
  paginationParams: PaginationParams | any;
  totalPages: any;
  setPaginationParams: Dispatch<SetStateAction<PaginationParams>>;
  tableLayout?: "auto" | "fixed";
  size?: any;
  loading?: boolean;
  [key: string]: any;
  showTotal?: boolean;
  className?: string;
  showSizeChanger?: boolean;
  isPagination?: boolean;
  scroll?: any;
  wrapperClassName?: string | null | undefined;
}

const IdentityTable = ({
  title,
  columns,
  dataSource,
  handleTableChange,
  paginationParams,
  totalPages,
  setPaginationParams,
  tableLayout,
  className,
  size = "small",
  loading,
  showSizeChanger = true,
  showTotal = false,
  isPagination = true,
  scroll,
  wrapperClassName = "min-h-[210px]",
  ...rest
}: IdentityTableProps) => {
  const { scroll: calcTableScroll } = useCalcTableScroll({
    className: wrapperClassName,
  });

  return (
    <>
      <div className={twMerge("flex-1", wrapperClassName)}>
        <Table
          className={twMerge("", className)}
          title={title}
          columns={columns}
          dataSource={dataSource || []}
          onChange={handleTableChange}
          pagination={false}
          loading={loading}
          tableLayout={tableLayout || "auto"}
          scroll={{
            x: calcTableScroll,
            ...scroll,
          }}
          size={size || "small"}
          sticky={true}
          locale={{
            emptyText: (
              <Empty
                image={Empty.PRESENTED_IMAGE_SIMPLE}
                description='Không có dữ liệu'
              />
            ),
          }}
          showSorterTooltip={false}
          {...rest}
        />
      </div>
      {isPagination && dataSource?.length > 0 && (
        <div className='mt-3 flex w-full justify-end'>
          <Pagination
            current={paginationParams.page}
            total={
              isNaN(totalPages * paginationParams.size)
                ? 0
                : totalPages * paginationParams.size
            }
            showSizeChanger={!isNaN(totalPages * paginationParams.size)}
            pageSizeOptions={
              isNaN(totalPages * paginationParams.size)
                ? []
                : ["5", "10", "15", "20"]
            }
            defaultPageSize={paginationParams.size}
            onChange={(page, pageSize) => {
              setPaginationParams((draft) => {
                draft.page = page;
                draft.size = pageSize;
                return draft;
              });
            }}
            showQuickJumper={showSizeChanger}
            locale={{
              jump_to: "Đến",
              page: "Trang",
              prev_page: "Trang trước",
              next_page: "Trang sau",
              items_per_page: " / trang",
            }}
            showTotal={(total, range) =>
              showTotal
                ? `Hiển thị ${range[0]}-${range[1]} trong tổng số ${total} bản ghi`
                : ""
            }
            responsive
          />
        </div>
      )}
    </>
  );
};

export default memo(
  IdentityTable,
  (prevProps: IdentityTableProps, nextProps: IdentityTableProps) =>
    prevProps.dataSource === nextProps.dataSource &&
    prevProps.loading === nextProps.loading &&
    prevProps.paginationParams === nextProps.paginationParams &&
    prevProps.totalPages === nextProps.totalPages &&
    prevProps.columns === nextProps.columns &&
    prevProps.size === nextProps.size &&
    prevProps.showTotal === nextProps.showTotal &&
    prevProps.showSizeChanger === nextProps.showSizeChanger &&
    prevProps.isPagination === nextProps.isPagination &&
    prevProps.scroll === nextProps.scroll &&
    prevProps.wrapperClassName === nextProps.wrapperClassName &&
    prevProps.className === nextProps.className &&
    prevProps.tableLayout === nextProps.tableLayout &&
    prevProps.title === nextProps.title &&
    prevProps.handleTableChange === nextProps.handleTableChange &&
    prevProps.setPaginationParams === nextProps.setPaginationParams &&
    prevProps.showTotal === nextProps.showTotal &&
    prevProps.showSizeChanger === nextProps.showSizeChanger &&
    prevProps.isPagination === nextProps.isPagination &&
    prevProps.scroll === nextProps.scroll &&
    prevProps.wrapperClassName === nextProps.wrapperClassName
);
