package com.palmcms.api.common;

public class Constants {


    public static final String APPLICATION_JSON_UTF8_VALUE = "application/json;charset=UTF-8";

    private Constants() {
    }

    public class Profile {

        private Profile() {
        }

        public static final String DEVELOPMENT = "dev";
        public static final String PRODUCTION = "prod";

    }

    public class API {

        private API() {
        }
        public static final String API_LANGUAGE = "/{language:[kor|eng|jpn|zho]+}";

        public static final String API_NOAUTH_PREFIX = "/palmcms/api/v1";
        public static final String API_LANGUAGE_NOAUTH_PREFIX = API_NOAUTH_PREFIX + API_LANGUAGE;

        public static final String API_AUTH = "/auth";
        public static final String API_CODE = "/code";

        public static final String API_PREFIX = "/palmcms/api/v1/app";
        public static final String API_LANGUAGE_PREFIX = API_PREFIX + API_LANGUAGE;


        public static final String API_USER = "/user";
        public static final String API_CMS = "/cms";
        public static final String API_CS = "/cs";
        public static final String API_NOTICE = "/notice";

        public static final String API_MANAGER = "/manager";
        public static final String API_ADMIN = "/admin";

    }
}
