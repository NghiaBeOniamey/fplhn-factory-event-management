package com.portalevent.infrastructure.constant;

/**
 * @author SonPT
 */
public enum EventStatus {
    CLOSE, // Đóng 0

    SCHEDULED_HELD, // Dự kiến tổ chức 1

    EDITING, // Cần sửa 2

    WAITING_APPROVAL, // Chờ phê duyệt 3

    APPROVED, // Đã phê duyệt 4

    ORGANIZED, // Đã tổ chức 5

    WAITING_APPROVAL_TBD_CS, // Đã thông qua CNBM 6

    EDITED // sự kiện đã tổ chức và mong muốn sửa 7
}
