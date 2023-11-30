package com.example.du_an_demo_be.config;

/**
 * Application constants.
 */
public final class Constants {

    // Regex for acceptable logins
    public static final String LOGIN_REGEX = "^(?>[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*)|(?>[_.@A-Za-z0-9-]+)$";

    public static final String SYSTEM = "system";
    public static final String DEFAULT_LANGUAGE = "en";

    public static final char DEFAULT_ESCAPE_CHAR_QUERY = '\\';

    private Constants() {}

    public static final String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";
    public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String DATE_FORMAT_DDMMYYY = "dd/MM/yyyy";
    public static final String TIME_FORMAT_TO_SECOND = "yyyyMMddhhmmss";
    public static final String TIME_FORMAT_TO_MILISECOND = "yyyyMMddHHmmssSSS";
    public static final Integer TIME_TYPE_DATE = 1;
    public static final Integer TIME_TYPE_MONTH = 2;
    public static final Integer TIME_TYPE_QUARTER = 3;
    public static final Integer TIME_TYPE_YEAR = 4;

    public static final int WIDTH = 255;
    public static final String TIME_ZONE_DEFAULT = "GMT+7";

    public static final String FILE_UP = "fileUp";
    public static final String FILE_ID = "fileId";
    public static final String CODE_SUCCESS = "00";
    public static final String CODE_FAIL = "-1";

    // todo: Loại câu hỏi
    public static final String TL = "TL";
    public static final String TN = "TN";
    public static final String DEFAULT_DDL = "Choose an item.";

    // todo: Status
    public static final Long ACTIVE = 1L;
    public static final Long INACTIVE = 0L;

    // todo: action
    public static final String CREATE = "CREATE";
    public static final String UPDATE = "UPDATE";

    //todo: type
    public static final String QUESTION_TYPE = "QUESTION_TYPE";
    public static final String QUESTION_LEVEL = "QUESTION_LEVEL";

    //todo: order
    public static final String ASC = "ASC";
    public static final String DESC = "DESC";

    public static final String NAME = "NAME";
    public static final String CODE = "CODE";


    public static final Integer MAX_ANSwERS = 4;

    // todo: typeSearch
    public static final Long ALL_EXAM_AND_SOLVE_EXAM = 0L;
    public static final Long EXAM_PACKAGE = 1L;
    public static final Long SOLVE_EXAM_PACKAGE = 2L;

    // todo: object
    public static final Long BUYER_ = 0L;
    public static final Long SELLER_ = 1L;

    // todo: productType
    public static final Long TYPE_EXAM_PACKAGE = 0L;
    public static final Long TYPE_SOLVE_EXAM_PACKAGE = 1L;

    public static final String SELLER = "ROLE_SELLER";
    public static final String BUYER = "ROLE_BUYER";

    public static final String ROLE_SELLER = "ROLE_SELLER";
    public static final String ROLE_BUYER = "ROLE_BUYER";

    public static final String ERROR_RETURN = "errorReturn";

    public static final Long ALL = -1L;

    // todo: error code
    public static final String S01 = "S01";
    public static final String S02 = "S02";

    // todo: language
    public static final String LA = "la";
    public static final String VN = "vn";
    public static final String EN = "en";

    // todo: appParam
    public static final String TYPE_TOPIC_EXAM_PACKAGE = "TYPE_TOPIC_EXAM_PACKAGE";
    public static final String TYPE_TOPIC_COURSE = "TYPE_TOPIC_COURSE";
    public static final String TYPE_TOPIC_BOOK = "TYPE_TOPIC_BOOK";
    public static final String TYPE_BANNER = "BANNER_LANDING_PAGE";

    // Object seller
    private static final Long[] LIST_OBJECT_SELLER = { 0L, 1L};


    // todo: ExamType
    public static final Long EXAM_FREE = 0L;
    public static final Long EXAM_FEE = 1L;

    // todo: productType
    public static final Long EXAM_PACKAGE_0 = 0L;
    public static final Long SOLVE_EXAM_PACKAGE_1 = 1L;
    public static final Long COURSE = 2L;
    public static final Long BOOK = 3L;

    public static class CODE_SOCKET {
        public static final String NOTIFICATION_REFUND = "notificationRefund";

    }

    public static class METHOD_API {
        public static final String POST = "POST";
        public static final String GET = "GET";
    }

    public static class RESPONSE_API_UNIID_STATUS {
        public static final Integer SUCCESS = 100;
        public static final Integer CODE_NOTE_EXIST = 120;
    }

    public static class PRODUCT_TYPE {
        public static final Long EXAM_PACKAGE = 0L;
        public static final Long SOLVE_EXAM_PACKAGE = 1L;
        public static final Long COURSE = 2L;
        public static final Long BOOK = 3L;
    }

    public static class SEARCH_SORT {
        public static final String CREATE_TIME = "createTime";
        public static final String NAME_PRODUCT = "nameProduct";
        public static final String PURCHASE_VALUE = "purchaseValue";
        public static final String PURCHASE_VALUE_SELLER = "purchaseValueSeller";
    }
}
