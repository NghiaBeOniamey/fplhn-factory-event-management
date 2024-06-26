package com.portalevent.util;

import java.util.Objects;

/**
 * @author SonPT
 */
public class CompareUtils {

    /**
     * Sử dụng để lấy ra message khi 2 thuộc tính hoặc biến có sự khác nhau (thay đổi giá trị)
     * @param fieldName: tên trường cần so sánh
     * @param valueOld: giá trị cũ
     * @param newValue: giá trị mới
     * @param customMessageWhenEmpty: Message tùy chỉnh khi 1 trong thuộc tính null hoặc rỗng
     *                              (VD: khi bộ môn cha là null thì customMessageWhenEmpty = "chưa có bộ môn cha"
     * @return: message khi có sự thay đổi hoặc rỗng nếu không có sự thay đổi
     * **Lưu ý: không sử dụng kiểu dữ liệu nguyên thủy
     */
    public static String getMessageProperyChange(String fieldName, Object valueOld, Object newValue, String customMessageWhenEmpty) {
        if (areValuesDifferent(valueOld, newValue)) {
            return convertMessage(fieldName, valueOld, newValue, customMessageWhenEmpty);
        }
        return "";
    }

    private static boolean areValuesDifferent(Object oldValue, Object newValue) {
        if (oldValue == null && newValue == null) {
            return false;
        }
        if (oldValue == null || newValue == null) {
            return true;
        }
        if (oldValue instanceof Boolean && newValue instanceof Boolean) {
            return (Boolean)oldValue != (Boolean)newValue;
        }
        return !oldValue.equals(newValue);
    }

    private static String convertMessage(String nameField, Object oldValue, Object newValue, String customMessageWhenEmpty) {
        StringBuilder message = new StringBuilder(nameField).append(" từ ");
        message.append(getDefaultMessageWhenNullOrBlank(oldValue, customMessageWhenEmpty)).append(" thành ")
            .append(getDefaultMessageWhenNullOrBlank(newValue, customMessageWhenEmpty)).append("; ");
        return message.toString();
    }

    private static String getDefaultMessageWhenNullOrBlank(Object value, String customMessageWhenEmpty) {
        if (value == null) {
            return customMessageWhenEmpty.isBlank() ? " chưa có" : customMessageWhenEmpty;
        }
        if (Objects.equals(value, "")) {
            return customMessageWhenEmpty.isBlank() ? " rỗng" : customMessageWhenEmpty;
        }
        return value.toString();
    }

}
