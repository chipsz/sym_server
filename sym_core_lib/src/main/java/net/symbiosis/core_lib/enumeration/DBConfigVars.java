package net.symbiosis.core_lib.enumeration;

/***************************************************************************
 * Created:     3/5/2018                                                  *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     kaviyat@payserv.co.zw                                      *
 **************************************************************************/

public class DBConfigVars {

    //SYSTEM SETTINGS
    public static final String CONFIG_SYSTEM_NAME = "system_name";
    public static final String CONFIG_CONFIG_UPDATE_DURATION = "config_update_duration";
    public static final String CONFIG_THREAD_CORE_POOL_SIZE = "thread_core_pool_size";
    public static final String CONFIG_THREAD_MAX_POOL_SIZE = "thread_max_pool_size";
    public static final String CONFIG_MUTEX_LOCK_WAIT_TIME = "mutex_lock_wait_time";
    public static final String CONFIG_MUTEX_LOCK_WAIT_INTERVAL = "mutex_lock_wait_interval";

    //MAIL SETTINGS
    public static final String CONFIG_EMAIL_PROTOCOL = "email_protocol";
    public static final String CONFIG_EMAIL_DISABLE = "email_disable";
    public static final String CONFIG_EMAIL_HOST = "email_host";
    public static final String CONFIG_EMAIL_PORT = "email_port";
    public static final String CONFIG_EMAIL_USERNAME = "email_username";
    public static final String CONFIG_EMAIL_PASSWORD = "email_password";
    public static final String CONFIG_EMAIL_SMTP_AUTH = "email_smtp_auth";
    public static final String CONFIG_EMAIL_SMTP_STARTTLS_ENABLE = "email_smtp_starttls_enable";
    public static final String CONFIG_EMAIL_SMTP_DEBUG = "email_smtp_debug";
    public static final String CONFIG_EMAIL_FROM = "email_from";
    public static final String CONFIG_EMAIL_ALERT_TO = "email_alert_to";

    //LOCALIZATION SETTINGS
    public static final String CONFIG_DEFAULT_COUNTRY = "default_country";
    public static final String CONFIG_DEFAULT_COUNTRY_CODE = "default_country_code";
    public static final String CONFIG_DEFAULT_CURRENCY_SYMBOL = "default_currency_symbol";
    public static final String CONFIG_DEFAULT_LANGUAGE = "default_language";

    //REGISTRATION SETTINGS
    public static final String CONFIG_DEFAULT_WEB_AUTH_GROUP = "default_web_auth_group";
    public static final String CONFIG_DEFAULT_POS_MACHINE_AUTH_GROUP = "default_web_auth_group";
    public static final String CONFIG_DEFAULT_SMART_PHONE_AUTH_GROUP = "default_smart_phone_auth_group";
    public static final String CONFIG_DEFAULT_WALLET_GROUP = "default_wallet_group";

    //COMPANY SETTINGS
    public static final String CONFIG_DOMAIN_NAME = "domain_name";
    public static final String CONFIG_CONTACT_ADDRESS = "contact_address";
    public static final String CONFIG_SUPPORT_EMAIL = "support_email";
    public static final String CONFIG_SUPPORT_PHONE = "support_phone";

    //VOUCHER SETTINGS
    public static final String CONFIG_DEFAULT_VOUCHER_EXPIRY_DAYS = "default_voucher_expiry_days";
    public static final String CONFIG_LOW_STOCK_THRESHHOLD = "low_stock_threshold";

    //SECURITY SETTINGS
    public static final String CONFIG_MAX_PASSWORD_TRIES = "max_password_tries";

    //WEB SETTINGS
    public static final String CONFIG_DEFAULT_REPORTING_DAYS = "default_reporting_days";

    //POS SETTINGS
    public static final String CONFIG_FALCON_POS_BINARY_NAME = "falcon_pos_binary_name";
    public static final String CONFIG_FALCON_POS_BINARY_LOCATION = "falcon_pos_binary_location";
    public static final String CONFIG_FALCON_POS_BINARY_VERSION = "falcon_pos_binary_version";

    //INTEGRATION SETTINGS
    public static final String CONFIG_GLO_SERVICE_REQUEST_TIMEOUT = "glo_service_request_timeout";
    public static final String CONFIG_GLO_SERVICE_CLIENT_ID = "glo_service_client_id";
    public static final String CONFIG_GLO_SERVICE_USER_ID = "glo_service_user_id";
    public static final String CONFIG_GLO_SERVICE_PASSWORD = "glo_service_password";
    public static final String CONFIG_GLO_SERVICE_LOW_BALANCE_THRESHOLD = "glo_service_low_balance_threshold";
}
