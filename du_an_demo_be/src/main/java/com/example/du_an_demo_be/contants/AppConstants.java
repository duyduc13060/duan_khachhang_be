package com.example.du_an_demo_be.contants;

public class AppConstants {

    public static final String EXTENSION_DOC = ".doc";
    public static final String EXTENSION_XLSX = "xlsx";
    public static final String EXTENSION_XLS = "xls";
    public static final String COMMA_DELIMITER = ",";
    public static final String ENCODING_UTF8 = "UTF-8";
    public static final String NO = "NO";
    public static final String CENTER = "CENTER";

    public static final String TMP_DIR = System.getProperty("java.io.tmpdir");
    public static final String MIME_TYPE_XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet; charset=UTF-8";
    public static final String ALIGN_LEFT = "LEFT";
    public static final String ALIGN_RIGHT = "RIGHT";
    public static final String STRING = "STRING";
    public static final String NUMBER = "NUMBER";
    public static final String LIST = "LIST";
    public static final String DOUBLE = "DOUBLE";
    public static final String ARRAY = "ARRAY";
    public static final String ERRORS = "ERRORS";

    public static final String SORT_ASC = "ASC";
    public static final String SORT_DESC = "DESC";
    public static final String FORMULA_NUMBER = "FORMULA_NUMBER";
    public static final String YYYY_MM = "yyyy/MM";
    public static final String YYYY = "yyyy";
    public static final String YYYY_MM_DD = "yyyy/MM/dd";
    public static final String DD_MM_YYYY = "dd/MM/yyyy";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy/MM/dd HH:mm:ss";
    public static final String MM_DD = "MMdd";
    public static final String YYYYMMDD = "yyyyMMdd";
    public static final String YYYYMM = "yyyyMM";
    public static final String YYYYMMDDHHSS = "yyyyMMddHHss";
    public static final String CONTENT_DISPOSITION = "Content-Disposition";
    public static final String CONTENT_LENGTH = "Content-Length";
    public static final String ATTACHMENT_FILENAME = "attachment; filename=%s";
    public static final String Encoding_UTF8 = "UTF-8";
    public static final String DOT = ".";
    public static final String DASH = "-";
    public static final String MIME_TYPE_XLS = "application/octet-stream";
    public static final String NEXT_LINE = "\n";
    public static final Long EXPORT_TEMPLATE = 0l;
    public static final Long EXPORT_DATA = 1l;
    public static final Long EXPORT_ERRORS = 2l;
    public static final Long IMPORT_INSERT = 0l;
    public static final Long IMPORT_UPDATE = 1l;
    public static final String REGEX_VN = "^[0-9A-Za-z`!@#$%^&*()<>\\?\"\\]\\[|'~_:,.+-={}]{1,250}$";
    public static final String REGEX_SPACE = "\\s";
    public static final String CHAR_SPACE = " ";
    public static final String CHAR_OR = "||";
    public static final String REGEX_NUMBER = "^[0-9]$";
    public static final String REGEX_EMAIL = "^(.+)@(.+)$";
    public static final String PASS_DEFAULT = "123456aA@";
    public static final String CHAR_STAR = "*";

    public static class ACCOUNT_LINK_CONSTANT {
        public static final String AUTHORIZATION = "Authorization"; // Authorization
        public static final String SUCCESS = "00000"; // Thành công
        public static final String RESPONSE_CODE = "responseCode"; // responseCode
        public static final String MESSAGE = "message"; // responseCode
        public static final String OTP_NOT_VALID = "10005"; // responseCode
        public static final String OTP_USED = "10006"; // Mã OTP đã được xử dụng
        public static final String OTP_EXPIRED = "10006"; // Mã OTP hết hạn
        public static final String NOT_REGISTER = "10119"; // Số điện thoại chưa đăng ký ví
        public static final String NOT_REGISTER2 = "10122"; // Số điện thoại chưa kich hoạt
        public static final String NOT_ACTIVATED = "10183"; // Chưa được kích hoạt tài khoản
        public static final String NOT_STANDARDIZED = "10180"; // tài khoản chưa được chuẩn hoá
    }

    public class DateFormat {

        public static final String YYYY_MM = "yyyy/MM";
        public static final String YYYY_MM_DD = "yyyy/MM/dd";
        public static final String MM_DD = "MM/dd";
    }

    public static class StudentStatus {

        public static final Integer STUDYING = 0;
        public static final Integer RESERVE = 1;
        public static final Integer LEAVE = 2;
        public static final Integer TRANSFERRED_SCHOOL = 3;
    }
    public static class Revenue {

        public static final String BUY_TIME = "buyTime";
        public static final String PURCHASE_CODE = "purchaseCode";
        public static final String BUYER_NAME = "buyerName";
        public static final String UNITEL_REVENUE = "unitelRevenue";
        public static final String PRODUCT_NAME = "productName";
        public static final String PURCHASE_VALUE = "purchaseValue";
        public static final String ASC = "ASC";
        public static final String DESC = "DESC";

    }
    public static class RegisterPackage{
        // Thao tác: 0 - Hủy dv, 1 - đăng ký mới, 2 - Hủy tự động
        public static final Long ACTION_CANCEL = 0L;
        public static final Long ACTION_REGISTER_NEW = 1L;
        public static final Long ACTION_AUTO_CANCEL = 2L;

        //Trạng thái:  1-Đăng ký, 2-Đã kích hoạt, 3- Tạm dừng dịch vụ
        public static final Long REGISTER_STATUS = 1L;
        public static final Long ACTIVATED_STATUS = 2L;
        public static final Long CANCEL_STATUS = 3L;

        // Người thực hiện: 1- nhà trường, 2- Phụ huynh học sinh
        public static final String CREATOR_SCHOOL = "1";
        public static final String CREATOR_PARENT = "2";


    }
    public static class RedisConstant {
        public static final String KEY_REGIS_PACKAGE =  "SCHOOL_PUSH";
        public static final String KEY_ACTIVE_PACKAGE="UNITEL_PUSH";
        public static final String KEY_CANCEL_PACKAGE =  "SCHOOL_CANCEL";

        public static final String KEY_CANCEL_AUTO_EXPIRE_PACKAGE =  "SCHOOL_EXPIRE_PACKAGE";
    }
    public static class ReserveLeaveType {

        public static final Long TYPE_LEAVE = 0L;
        public static final Long TYPE_RESERVE = 1L;
    }

    public static class TeacherRatingCodeStatus {

        public static final String NOT_RATE = "not_rate";
        public static final String SELF_RATE = "self_rate";
        public static final String RATED = "Rated";
        public static final String APPROVED = "Approved";
    }

    public static class ContactPrimary {

        public static final Long FIRST_CONTACT = 1L;
        public static final Long SECOND_CONTACT = 0L;
    }

    public static class User {

        public static final Boolean ACTIVATED = true;
        public static final Boolean INACTIVATED = false;
    }

    public static class Subject {

        public static final Long TYPE_ELECTIVE = 0L;
        public static final Long TYPE_REQUIRED = 1L;
    }

    public static class RwDclStudent {

        public static final Long TYPE_REWARD_ = 1L;
        public static final Long TYPE_DISCIPLINE = 0L;
    }

    public static final Integer TRANSFER_SCHOOL = 0;
    public static final Integer TRANSFER_CLASS = 1;
    // Sample file
    public static final String SAMPLE_FILE_NAME = "DS_lophoc_ddmmyy.xls";
    public static final String SAMPLE_FILE_NAME_TKB = "DS_TKB_ddmmyy2.xls";
    public static final String SAMPLE_MON_HOC_THUOC_TRUONG = "DS_monhocthuoctruong.xls";
    public static final String SAMPLE_FILE_STUDENT = "DS_Hocsinh.xls";
    public static final String SAMPLE_FILE_TEACHER_RATING = "Danhgialaodong_[namhoc].doc";
    public static final String SAMPLE_FILE_TEACHING_ASSIGNMENT1 = "DS_Phanconggiangday1.xls";
    public static final String SAMPLE_FILE_TEACHING_ASSIGNMENT2 = "DS_Phanconggiangday2.xls";
    public static final String SAMPLE_FILE_TEACHING_ASSIGNMENT3 = "DS_Phanconggiangday3.xls";
    public static final String SAMPLE_FILE_TEACHING_ASSIGNMENT4 = "DS_Phanconggiangday4.xls";

    public static final String FILE_NAME_RATE = "danhgialaodong";

    public static final String SAMPLE_FILE_TOPIC_EXAM_PACKAGE_VN = "Import_Chu_de_goi_de_thi.xls";

    public static final String SAMPLE_FILE_TOPIC_EXAM_PACKAGE_LA = "Import_Topic_of_exam_package_la.xls";

    public static final String SAMPLE_FILE_TOPIC_EXAM_PACKAGE_EN = "Import_Topic_of_exam_package.xls";

    public static class Notification {

        public static final Integer APPROVE_ADD_SERVICE = 13;
        public static final Integer NOTIFICATION_FROM_ULEARN = 0;
        public static final Integer TYPE_NOTIFICATION_HISTORY = 11;
        public static final Integer OPEN_NOTIFICATION_HISTORY = 0;
        public static final Integer TOPIC_NOTIFICATION_HISTORY = 0;
        public static final Integer APPROVE_OR_REFUSE_REFUND_BUYER = 10;
        public static final Integer APPROVE_OR_REFUSE_REFUND_SELLER = 9;
    }

    public static class BlockHistory{
        public static final Integer PRODUCT_LOCK = 0;
        public static final Integer PRODUCT_UNLOCK = 1;

    }

    public static class TeacherManagement {

        public static final Long BONUS = 1l;
        public static final Long PUNISH = 0l;
    }

    public static class JobTransferOut {

        public static final String CODE = "JTHO_";
    }
    public static class TopicExamPackage {

        public static final Long ACTIVE = 1l;
        public static final Long INACTIVE = 0l;

        public static final String TYPE_AP_PARAM = "TYPE_TOPIC_EXAM_PACKAGE";
    }

    public static final int MONTH_END_OF_YEAR = 12;
    public static final String SCHOOL = "SCHOOL";
    public static final String KEY_ACTIVE_PACKAGE="UNITEL";
    public static final String KEY_CANCEL_PACKAGE =  "SCHOOL_CANCEL";

    public static final String KEY_CANCEL_AUTO_EXPIRE_PACKAGE =  "SCHOOL_EXPIRE_PACKAGE";
    public static class ErrorCodeJwtResponse {

        public static final String SUCCESS = "00"; //Thành công
        public static final String ACCOUNT_INVALID = "01"; // Tài khoản mật khẩu không chính xác
        public static final String ACCOUNT_NOT_ACTIVE = "02"; // Tài khoản đã ngừng hoạt động
        public static final String ACCOUNT_EXPIRED = "03"; // Tài khoản đã ngừng kích hoạt
        public static final String FAIL = "04";// Lỗi Authen
        private ErrorCodeJwtResponse() {}
    }

    public static class BuyerManager {
        public static final String EXPORT_PARAM = "listBuyer";
    }

    public static class Voucher {
        public static final String EXPORT_PARAM = "listVoucher";
    }

    public static final class LIB_CATEGORY {
        public static final Integer MAX_LEVEL = 4;
    }

    // Type đăng ký
    public static class REGISTER_TYPE {
        public static final Long PHONE = 1L; // Số điện thoại
        public static final Long GOOGLE = 2L; // Tài khoản google
        public static final Long LAOSEDU = 3L; // Tài khoản LaosEdu
        public static final Long APPLE = 4L; // Tài khoản Apple
    }
}
