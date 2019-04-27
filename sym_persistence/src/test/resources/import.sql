# system settings
insert ignore into sym_config(config_id,is_enabled,config_name,config_value,config_description) values (1,1,'system_name','Jarvis','System Name');
insert ignore into sym_config(config_id,is_enabled,config_name,config_value,config_description) values (2,1,'config_update_duration','1440','How often switch re-reads configs from the database');
insert ignore into sym_config(config_id,is_enabled,config_name,config_value,config_description) values (3,1,'thread_core_pool_size','20','Number of initial threads available');
insert ignore into sym_config(config_id,is_enabled,config_name,config_value,config_description) values (4,1,'thread_max_pool_size','50','Maximum number threads available');
insert ignore into sym_config(config_id,is_enabled,config_name,config_value,config_description) values (5,1,'mutex_lock_wait_time','10000','Milliseconds to wait for mutex');
insert ignore into sym_config(config_id,is_enabled,config_name,config_value,config_description) values (6,1,'mutex_lock_wait_interval','1000','Milliseconds between checking mutex lock');
# email settings
insert ignore into sym_config(config_id,is_enabled,config_name,config_value,config_description) values (51,1,'email_protocol','smtp','Email protocol');
insert ignore into sym_config(config_id,is_enabled,config_name,config_value,config_description) values (52,1,'email_disable','false','Are all emails disabled?');
insert ignore into sym_config(config_id,is_enabled,config_name,config_value,config_description) values (53,1,'email_host','localhost','SMTP host');
insert ignore into sym_config(config_id,is_enabled,config_name,config_value,config_description) values (54,1,'email_port','25','SMTP port');
insert ignore into sym_config(config_id,is_enabled,config_name,config_value,config_description) values (55,1,'email_username','empowerttl','SMTP username');
insert ignore into sym_config(config_id,is_enabled,config_name,config_value,config_description) values (56,1,'email_password','3mp0wER@dm1n','SMTP password');
insert ignore into sym_config(config_id,is_enabled,config_name,config_value,config_description) values (57,1,'email_smtp_auth','false','Authenticate on SMTP');
insert ignore into sym_config(config_id,is_enabled,config_name,config_value,config_description) values (58,1,'email_smtp_starttls_enable','false','Enable TLS Authentication');
insert ignore into sym_config(config_id,is_enabled,config_name,config_value,config_description) values (59,1,'email_smtp_debug','false','Show SMTP debug messages?');
insert ignore into sym_config(config_id,is_enabled,config_name,config_value,config_description) values (60,1,'email_from','empowerttl@gmail.com','What is shown as sender from in emails from system');
insert ignore into sym_config(config_id,is_enabled,config_name,config_value,config_description) values (61,1,'email_alert_to','empowerttl@gmail.com','Where email is sent when there is a system alert');
#localization settings
insert ignore into sym_config(config_id,is_enabled,config_name,config_value,config_description) values (80,1,'default_country','Ghana','Default country for user registrations');
insert ignore into sym_config(config_id,is_enabled,config_name,config_value,config_description) values (81,1,'default_country_code','233','Default country code for phone numbers');
insert ignore into sym_config(config_id,is_enabled,config_name,config_value,config_description) values (82,1,'default_currency_symbol','GHS','Default currency for transactions');
insert ignore into sym_config(config_id,is_enabled,config_name,config_value,config_description) values (83,1,'default_language','English','Default language for user registrations');
#registration settings
# insert ignore into sym_config(config_id,is_enabled,config_name,config_value,config_description) values (100,1,'default_desktop_group','DESKTOP_AGENT','Default auth group for desktop user registrations');
insert ignore into sym_config(config_id,is_enabled,config_name,config_value,config_description) values (101,1,'default_web_auth_group','WEB_AGENT','Default auth group for web user registrations');
insert ignore into sym_config(config_id,is_enabled,config_name,config_value,config_description) values (102,1,'default_pos_machine_group','POS_AGENT','Default auth group for pos machine user registrations');
# insert ignore into sym_config(config_id,is_enabled,config_name,config_value,config_description) values (103,1,'default_pos_till_group','POS_TILL_AGENT','Default auth group for pos till user registrations');
# insert ignore into sym_config(config_id,is_enabled,config_name,config_value,config_description) values (104,1,'default_ussd_auth_group','USSD_USER','Default auth group for ussd user registrations');
insert ignore into sym_config(config_id,is_enabled,config_name,config_value,config_description) values (105,1,'default_smart_phone_auth_group','MOBILE_USER','Default auth group for mobile user registrations');
insert ignore into sym_config(config_id,is_enabled,config_name,config_value,config_description) values (106,1,'default_wallet_group','DEFAULT_WALLET_GROUP','Default wallet group for user registrations');
#company settings
insert ignore into sym_config(config_id,is_enabled,config_name,config_value,config_description) values (120,1,'domain_name','empowerttl.com','Domain name for where the system is hosted');
insert ignore into sym_config(config_id,is_enabled,config_name,config_value,config_description) values (121,1,'contact_address','Plot 87, Spintex Road, Accra, Ghana','Company physical address');
insert ignore into sym_config(config_id,is_enabled,config_name,config_value,config_description) values (122,1,'support_email','empowerttl@gmail.com','Company support email');
insert ignore into sym_config(config_id,is_enabled,config_name,config_value,config_description) values (123,1,'support_phone','+233242279182','Company support phone number');
#voucher settings
insert ignore into sym_config(config_id,is_enabled,config_name,config_value,config_description) values (140,1,'default_voucher_expiry_days','365','Time before prepaid vouchers are considered expired');
insert ignore into sym_config(config_id,is_enabled,config_name,config_value,config_description) values (141,1,'low_stock_threshold','50','Threshold before sending out low stock email');
#security settings
insert ignore into sym_config(config_id,is_enabled,config_name,config_value,config_description) values (160,1,'max_password_tries','5','Maximum password tries before blocking an account');
#web settings
insert ignore into sym_config(config_id,is_enabled,config_name,config_value,config_description) values (181,1,'default_reporting_days','7','Number of days to show in history by default');
#pos settings
insert ignore into sym_config(config_id,is_enabled,config_name,config_value,config_description) values (200,1,'falcon_pos_binary_name','7','Name of the Falcon POS binary file');
insert ignore into sym_config(config_id,is_enabled,config_name,config_value,config_description) values (201,1,'falcon_pos_binary_location','7','Location of the Falcon POS binary file');
insert ignore into sym_config(config_id,is_enabled,config_name,config_value,config_description) values (202,1,'falcon_pos_binary_version','7','Version of the Falcon POS binary file');

insert ignore into sym_country(id,name,is_enabled,iso_code_2,iso_code_3,dialing_code) values (1,'GHANA',1,'GH','GHA',233);
insert ignore into sym_country(id,name,is_enabled,iso_code_2,iso_code_3,dialing_code) values (2,'ZIMBABWE',0,'ZW','ZWE',263);
insert ignore into sym_country(id,name,is_enabled,iso_code_2,iso_code_3,dialing_code) values (3,'SOUTH AFRICA',0,'ZA','ZAF',27);

insert ignore into sym_channel(channel_id,name,is_enabled,is_pin_based) values (1,'DESKTOP',1,0);
insert ignore into sym_channel(channel_id,name,is_enabled,is_pin_based) values (2,'WEB',1,0);
insert ignore into sym_channel(channel_id,name,is_enabled,is_pin_based) values (3,'POS_MACHINE',1,1);
insert ignore into sym_channel(channel_id,name,is_enabled,is_pin_based) values (4,'POS_TILL',1,1);
insert ignore into sym_channel(channel_id,name,is_enabled,is_pin_based) values (5,'USSD',1,1);
insert ignore into sym_channel(channel_id,name,is_enabled,is_pin_based) values (6,'SMART_PHONE',1,1);

insert ignore into sym_currency(currency_id,currency_name,iso_4217_code,iso_4217_num) values (1,'United States Dollar','USD','840');
insert ignore into sym_currency(currency_id,currency_name,iso_4217_code,iso_4217_num) values (2,'South African Rand','ZAR','710');
insert ignore into sym_currency(currency_id,currency_name,iso_4217_code,iso_4217_num) values (3,'Botswana Pula','BWP','072');
insert ignore into sym_currency(currency_id,currency_name,iso_4217_code,iso_4217_num) values (4,'Pound Sterling','GBP','826');
insert ignore into sym_currency(currency_id,currency_name,iso_4217_code,iso_4217_num) values (5,'Euro','EUR','978');
insert ignore into sym_currency(currency_id,currency_name,iso_4217_code,iso_4217_num) values (6,'Zambian Kwacha','ZMW','967');

insert ignore into sym_event_type(id,name,is_enabled) values (1000,'USER_REGISTRATION',1);
insert ignore into sym_event_type(id,name,is_enabled) values (1001,'USER_ASSIGN_CHANNEL',1);
insert ignore into sym_event_type(id,name,is_enabled) values (1002,'USER_CREATE',1);
insert ignore into sym_event_type(id,name,is_enabled) values (1003,'USER_LOGIN',1);
insert ignore into sym_event_type(id,name,is_enabled) values (1004,'USER_LOGOUT',1);
insert ignore into sym_event_type(id,name,is_enabled) values (1005,'USER_UPDATE',1);
insert ignore into sym_event_type(id,name,is_enabled) values (1006,'USER_PASSWORD_UPDATE',1);
insert ignore into sym_event_type(id,name,is_enabled) values (1007,'USER_PASSWORD_RESET',1);
insert ignore into sym_event_type(id,name,is_enabled) values (1008,'USER_PIN_RESET',1);

insert ignore into sym_event_type(id,name,is_enabled) values (1100,'WALLET_LOAD',1);
insert ignore into sym_event_type(id,name,is_enabled) values (1101,'WALLET_CASHOUT',1);
insert ignore into sym_event_type(id,name,is_enabled) values (1102,'WALLET_UPDATE',1);
insert ignore into sym_event_type(id,name,is_enabled) values (1103,'WALLET_GET_CASHOUT_ACCOUNTS',1);
insert ignore into sym_event_type(id,name,is_enabled) values (1104,'WALLET_ADD_CASHOUT_ACCOUNT',1);
insert ignore into sym_event_type(id,name,is_enabled) values (1105,'WALLET_DISABLE_CASHOUT_ACCOUNT',1);
insert ignore into sym_event_type(id,name,is_enabled) values (1106,'WALLET_GROUP_CREATE',1);
insert ignore into sym_event_type(id,name,is_enabled) values (1107,'WALLET_GROUP_UPDATE',1);
insert ignore into sym_event_type(id,name,is_enabled) values (1108,'WALLET_GROUP_VOUCHER_UPDATE',1);
insert ignore into sym_event_type(id,name,is_enabled) values (1109,'WALLET_SWIPE_IN',1);
insert ignore into sym_event_type(id,name,is_enabled) values (1110,'WALLET_TRANSFER',1);

insert ignore into sym_event_type(id,name,is_enabled) values (1200,'VOUCHER_IMPORT',1);
insert ignore into sym_event_type(id,name,is_enabled) values (1201,'VOUCHER_CREATE',1);
insert ignore into sym_event_type(id,name,is_enabled) values (1202,'VOUCHER_PURCHASE',1);
insert ignore into sym_event_type(id,name,is_enabled) values (1203,'VOUCHER_UPDATE',1);
insert ignore into sym_event_type(id,name,is_enabled) values (1204,'VOUCHER_TYPE_CREATE',1);
insert ignore into sym_event_type(id,name,is_enabled) values (1205,'VOUCHER_TYPE_UPDATE',1);
insert ignore into sym_event_type(id,name,is_enabled) values (1206,'VOUCHER_PROVIDER_CREATE',1);
insert ignore into sym_event_type(id,name,is_enabled) values (1207,'VOUCHER_PROVIDER_UPDATE',1);
insert ignore into sym_event_type(id,name,is_enabled) values (1208,'SERVICE_PROVIDER_CREATE',1);
insert ignore into sym_event_type(id,name,is_enabled) values (1209,'SERVICE_PROVIDER_UPDATE',1);

insert ignore into sym_event_type(id,name,is_enabled) values (1300,'DEVICE_POS_MACHINE_UPDATE',1);
insert ignore into sym_event_type(id,name,is_enabled) values (1301,'DEVICE_PHONE_UPDATE',1);

insert ignore into sym_financial_institution_type(institution_type_id,name,is_enabled) values (1,'BANK',1);
insert ignore into sym_financial_institution_type(institution_type_id,name,is_enabled) values (2,'MOBILE_BANK',1);
insert ignore into sym_financial_institution_type(institution_type_id,name,is_enabled) values (3,'ONLINE_BANK',1);

insert ignore into sym_language(name,is_enabled) values ('ENGLISH',1);

insert ignore into sym_preference(name,is_enabled) values ('PF_WEB_THEME',1);

insert ignore into sym_response_code(id,name,is_enabled,response_message) values(1, 'GENERAL_ERROR',1,'A general error occurred');
insert ignore into sym_response_code(id,name,is_enabled,response_message) values(-1,'CONFIGURATION_INVALID',1,'Specified configuration is not valid');
insert ignore into sym_response_code(id,name,is_enabled,response_message) values(15,'DATA_NOT_FOUND',1,'Data does not exist');
insert ignore into sym_response_code(id,name,is_enabled,response_message) values(16,'NOT_SUPPORTED',1,'Not supported');

insert ignore into sym_response_code(id,name,is_enabled,response_message) values(2,'INPUT_INCOMPLETE_REQUEST',1,'Incomplete request specified');
insert ignore into sym_response_code(id,name,is_enabled,response_message) values(3,'INPUT_INVALID_REQUEST',1,'Invalid request specified');
insert ignore into sym_response_code(id,name,is_enabled,response_message) values(4,'INPUT_INVALID_EMAIL',1,'Email provided was not valid');
insert ignore into sym_response_code(id,name,is_enabled,response_message) values(5,'INPUT_INVALID_MSISDN',1,'Phone number provided was not valid');
insert ignore into sym_response_code(id,name,is_enabled,response_message) values(6,'INPUT_INVALID_FIRST_NAME',1,'First name provided was not valid');
insert ignore into sym_response_code(id,name,is_enabled,response_message) values(7,'INPUT_INVALID_LAST_NAME',1,'Last name provided was not valid');
insert ignore into sym_response_code(id,name,is_enabled,response_message) values(8,'INPUT_INVALID_USERNAME',1,'Username provided was not valid');
insert ignore into sym_response_code(id,name,is_enabled,response_message) values(9,'INPUT_INVALID_PASSWORD',1,'Password provided was not valid');
insert ignore into sym_response_code(id,name,is_enabled,response_message) values(10,'INPUT_INVALID_NAME',1,'Name provided was not valid');
insert ignore into sym_response_code(id,name,is_enabled,response_message) values(11,'INPUT_INVALID_AMOUNT',1,'Invalid amount specified');
insert ignore into sym_response_code(id,name,is_enabled,response_message) values(12,'INPUT_INVALID_WALLET',1,'Invalid wallet specified');
insert ignore into sym_response_code(id,name,is_enabled,response_message) values(13,'INPUT_INVALID_CASHIER',1,'Invalid cashier name specified');

insert ignore into sym_response_code(id,name,is_enabled,response_message) values(20,'AUTH_INSUFFICIENT_PRIVILEGES',1,'Insufficient privileges for current operation');
insert ignore into sym_response_code(id,name,is_enabled,response_message) values(21,'AUTH_AUTHENTICATION_FAILED',1,'Authentication failed');
insert ignore into sym_response_code(id,name,is_enabled,response_message) values(22,'AUTH_INCORRECT_PASSWORD',1,'Password is incorrect');
insert ignore into sym_response_code(id,name,is_enabled,response_message) values(23,'AUTH_NON_EXISTENT',1,'Account does not exist');

insert ignore into sym_response_code(id,name,is_enabled,response_message) values(30,'ACC_ACTIVE',1,'Account is active');
insert ignore into sym_response_code(id,name,is_enabled,response_message) values(31,'ACC_INACTIVE',1,'Account is inactive');
insert ignore into sym_response_code(id,name,is_enabled,response_message) values(32,'ACC_SUSPENDED',1,'Account has been suspended');
insert ignore into sym_response_code(id,name,is_enabled,response_message) values(33,'ACC_CLOSED',1,'Account has been closed');
insert ignore into sym_response_code(id,name,is_enabled,response_message) values(34,'ACC_PASSWORD_TRIES_EXCEEDED',1,'Password tries exceeded');
insert ignore into sym_response_code(id,name,is_enabled,response_message) values(35,'ACC_PASSWORD_EXPIRED',1,'Password expired');

insert ignore into sym_response_code(id,name,is_enabled,response_message) values(40,'CONNECTION_FAILED',1,'Connection failed');
insert ignore into sym_response_code(id,name,is_enabled,response_message) values(41,'UNKNOWN_HOST',1,'Unknown host');
insert ignore into sym_response_code(id,name,is_enabled,response_message) values(42,'CONNECTION_REFUSED',1,'Connection Refused');
insert ignore into sym_response_code(id,name,is_enabled,response_message) values(43,'TIMEOUT',1,'Timeout elapsed before transaction completion');

insert ignore into sym_response_code(id,name,is_enabled,response_message) values(51,'INSUFFICIENT_FUNDS',1,'Insufficient funds');

insert ignore into sym_response_code(id,name,is_enabled,response_message) values(60,'INSUFFICIENT_STOCK',1,'Insufficient stock');

insert ignore into sym_response_code(id,name,is_enabled,response_message) values(80,'EXISTING_DATA_FOUND',1,'Existing data found');

insert ignore into sym_response_code(id,name,is_enabled,response_message) values(351,'REGISTRATION_FAILED',1,'Registration Failed');
insert ignore into sym_response_code(id,name,is_enabled,response_message) values(352,'PREVIOUS_USERNAME_FOUND',1,'Username has been previously registered');
insert ignore into sym_response_code(id,name,is_enabled,response_message) values(353,'PREVIOUS_MSISDN_FOUND',1,'Mobile number has been previously registered');
insert ignore into sym_response_code(id,name,is_enabled,response_message) values(354,'PREVIOUS_EMAIL_FOUND',1,'Email has been previously registered');
insert ignore into sym_response_code(id,name,is_enabled,response_message) values(355,'PREVIOUS_REGISTRATION_FOUND',1,'Previous registration found');

insert ignore into sym_response_code(id,name,is_enabled,response_message) values(0, 'SUCCESS',1,'Successful');
update sym_response_code set id = 0 where name = 'SUCCESS';

insert ignore into sym_auth_group(auth_group_id, name, is_enabled) values (1, 'SUPER_USER',1);
insert ignore into sym_auth_group(auth_group_id, name, is_enabled) values (2, 'WEB_ADMIN',1);
insert ignore into sym_auth_group(auth_group_id, name, is_enabled) values (3, 'WEB_CLERK',1);
insert ignore into sym_auth_group(auth_group_id, name, is_enabled) values (4, 'WEB_AGENT',1);
insert ignore into sym_auth_group(auth_group_id, name, is_enabled) values (5, 'POS_AGENT',1);
insert ignore into sym_auth_group(auth_group_id, name, is_enabled) values (6, 'POS_ADMIN',1);
insert ignore into sym_auth_group(auth_group_id, name, is_enabled) values (7, 'MOBILE_USER',1);
insert ignore into sym_auth_group(auth_group_id, name, is_enabled) values (8, 'MOBILE_ADMIN',1);

insert ignore into sym_role(name, is_enabled) values ('ROLE_WEB_MANAGE_EVD', 1);
insert ignore into sym_role(name, is_enabled) values ('ROLE_WEB_MANAGE_STOCK', 1);
insert ignore into sym_role(name, is_enabled) values ('ROLE_WEB_MANAGE_PROVIDERS', 1);
insert ignore into sym_role(name, is_enabled) values ('ROLE_WEB_MANAGE_VOUCHERS', 1);
insert ignore into sym_role(name, is_enabled) values ('ROLE_WEB_MANAGE_SYSTEM', 1);
insert ignore into sym_role(name, is_enabled) values ('ROLE_WEB_MANAGE_USERS', 1);
insert ignore into sym_role(name, is_enabled) values ('ROLE_WEB_ADVANCED_MANAGE_USERS', 1);
insert ignore into sym_role(name, is_enabled) values ('ROLE_WEB_VIEW_REPORTS', 1);
insert ignore into sym_role(name, is_enabled) values ('ROLE_WEB_VIEW_SYSTEM_REPORTS', 1);
insert ignore into sym_role(name, is_enabled) values ('ROLE_WEB_VIEW_AUTHENTICATION_REPORTS', 1);
insert ignore into sym_role(name, is_enabled) values ('ROLE_WEB_VIEW_PAYMENT_REPORTS', 1);
insert ignore into sym_role(name, is_enabled) values ('ROLE_WEB_VIEW_TRANSACTION_REPORTS', 1);
insert ignore into sym_role(name, is_enabled) values ('ROLE_WEB_VIEW_SINGLE_REPORTS', 1);
insert ignore into sym_role(name, is_enabled) values ('ROLE_WEB_VIEW_SINGLE_AUTHENTICATION_REPORTS', 1);
insert ignore into sym_role(name, is_enabled) values ('ROLE_WEB_VIEW_SINGLE_PAYMENT_REPORTS', 1);
insert ignore into sym_role(name, is_enabled) values ('ROLE_WEB_VIEW_SINGLE_TRANSACTION_REPORTS', 1);

insert ignore into sym_role(name, is_enabled) values ('ROLE_WEB_MANAGE_SETTINGS', 1);

insert ignore into sym_role(name, is_enabled) values ('ROLE_POS_MANAGE_SETTINGS', 1);

insert ignore into sym_role(name, is_enabled) values ('ROLE_MOBILE_MANAGE_SETTINGS', 1);

/* Insert all roles for SUPER_USER */
insert ignore into sym_auth_group_role(auth_group_id,role_id,name,is_enabled) select sg.auth_group_id,sr.role_id,CONCAT(sg.name,'_',sr.name),1 from sym_auth_group sg,sym_role sr where sg.name = 'SUPER_USER' and sr.name LIKE '%';

/* Insert roles for EMPOWER_SYSTEM_ADMIN */
insert ignore into sym_auth_group_role(auth_group_id,role_id,name,is_enabled) select sg.auth_group_id,sr.role_id,CONCAT(sg.name,'_',sr.name),1 from sym_auth_group sg,sym_role sr where sg.name = 'WEB_ADMIN' and sr.name LIKE 'ROLE_WEB_%';

/* Insert roles for EMPOWER_SYSTEM_CLERK */
insert ignore into sym_auth_group_role(auth_group_id,role_id,name,is_enabled) select sg.auth_group_id,sr.role_id,CONCAT(sg.name,'_',sr.name),1 from sym_auth_group sg,sym_role sr where sg.name = 'WEB_CLERK' and (sr.name IN ('ROLE_WEB_MANAGE_SYSTEM', 'ROLE_WEB_MANAGE_EVD', 'ROLE_WEB_MANAGE_STOCK', 'ROLE_WEB_MANAGE_USERS', 'ROLE_WEB_MANAGE_SETTINGS') or sr.name LIKE 'ROLE_WEB_VIEW_%');

/* Insert roles for EMPOWER_SYSTEM_AGENT */
insert ignore into sym_auth_group_role(auth_group_id,role_id,name,is_enabled) select sg.auth_group_id,sr.role_id,CONCAT(sg.name,'_',sr.name),1 from sym_auth_group sg,sym_role sr where sg.name = 'WEB_AGENT' and (sr.name LIKE 'ROLE_WEB_VIEW_SINGLE_%' or sr.name = 'ROLE_WEB_MANAGE_SETTINGS');

/* Insert roles for POS_ADMIN */
insert ignore into sym_auth_group_role(auth_group_id,role_id,name,is_enabled) select sg.auth_group_id,sr.role_id,CONCAT(sg.name,'_',sr.name),1 from sym_auth_group sg,sym_role sr where sg.name = 'POS_ADMIN' and sr.name LIKE ('ROLE_POS_%');

/* Insert roles for MOBILE_ADMIN */
insert ignore into sym_auth_group_role(auth_group_id,role_id,name,is_enabled) select sg.auth_group_id,sr.role_id,CONCAT(sg.name,'_',sr.name),1 from sym_auth_group sg,sym_role sr where sg.name = 'MOBILE_ADMIN' and sr.name LIKE ('ROLE_MOBILE_%');

insert ignore into sym_voucher_type(voucher_type_id,is_enabled,name) values (1, 1, 'AIRTIME');
insert ignore into sym_voucher_type(voucher_type_id,is_enabled,name) values (2, 0, 'INTERNET');
insert ignore into sym_voucher_type(voucher_type_id,is_enabled,name) values (3, 0, 'DSTV');
insert ignore into sym_voucher_type(voucher_type_id,is_enabled,name) values (4, 0, 'RESULT CHECKER PIN');

insert ignore into sym_voucher_status(voucher_status_id,is_enabled,name) values (1, 1, 'NEW');
insert ignore into sym_voucher_status(voucher_status_id,is_enabled,name) values (2, 1, 'SOLD');
insert ignore into sym_voucher_status(voucher_status_id,is_enabled,name) values (3, 1, 'EXPIRED');

insert ignore into sym_distribution_channel(distribution_channel_id,is_enabled,name) values (1, 1, 'RECEIPT');
insert ignore into sym_distribution_channel(distribution_channel_id,is_enabled,name) values (2, 1, 'SMS');

insert ignore into sym_deposit_type(deposit_type_id,is_enabled,name) values (1, 1, 'CASH');
insert ignore into sym_deposit_type(deposit_type_id,is_enabled,name) values (2, 1, 'BANK_DEPOSIT');
insert ignore into sym_deposit_type(deposit_type_id,is_enabled,name) values (3, 1, 'CREDIT_CARD');
insert ignore into sym_deposit_type(deposit_type_id,is_enabled,name) values (4, 1, 'DEBIT_CARD');
insert ignore into sym_deposit_type(deposit_type_id,is_enabled,name) values (5, 1, 'BANK_TRANSFER');

insert ignore into sym_service_provider (service_provider_id, is_enabled, service_provider_name) values (1, 1, 'Airtel');
insert ignore into sym_service_provider (service_provider_id, is_enabled, service_provider_name) values (2, 0, 'Glo');
insert ignore into sym_service_provider (service_provider_id, is_enabled, service_provider_name) values (3, 1, 'MTN');
insert ignore into sym_service_provider (service_provider_id, is_enabled, service_provider_name) values (4, 1, 'Vodafone');
insert ignore into sym_service_provider (service_provider_id, is_enabled, service_provider_name) values (5, 1, 'Tigo');
insert ignore into sym_service_provider (service_provider_id, is_enabled, service_provider_name) values (6, 0, 'Multichoice');
insert ignore into sym_service_provider (service_provider_id, is_enabled, service_provider_name) values (7, 1, 'WAEC');

insert ignore into sym_voucher_provider (voucher_provider_id, is_enabled, voucher_provider_name) values (1, 1, 'Airtel');
insert ignore into sym_voucher_provider (voucher_provider_id, is_enabled, voucher_provider_name) values (2, 0, 'Glo');
insert ignore into sym_voucher_provider (voucher_provider_id, is_enabled, voucher_provider_name) values (3, 1, 'MTN');
insert ignore into sym_voucher_provider (voucher_provider_id, is_enabled, voucher_provider_name) values (4, 1, 'Vodafone');
insert ignore into sym_voucher_provider (voucher_provider_id, is_enabled, voucher_provider_name) values (5, 1, 'Tigo');
insert ignore into sym_voucher_provider (voucher_provider_id, is_enabled, voucher_provider_name) values (6, 0, 'CowryPay');
insert ignore into sym_voucher_provider (voucher_provider_id, is_enabled, voucher_provider_name) values (7, 1, 'WAEC');

insert ignore into sym_pin_import_config (is_enabled,name,divide_amount_by,amount_in_contents,amount_in_filename,amount_line_num,amount_pos,amount_regex,batch_id_in_contents,batch_id_in_filename,batch_id_line_num,batch_id_pos,batch_id_regex,delimiter,expiry_date_format,expiry_in_contents,expiry_in_filename,expiry_line_num,expiry_pos,expiry_regex,filename_regex,pin_length,pin_pos,pin_start_line,serial_length,serial_pos,total_num_in_contents,total_num_in_filename,total_num_line_num,total_num_pos,total_num_regex,service_provider_id,voucher_provider_id,voucher_type_id,pgp_private_key_file,pgp_key_pass) VALUES (1,'AIRTEL_AIRTEL',100,1,0,2,3,'(\\d+),(\\d+),(\\d+),(.*)',0,1,NULL,2,'VOUCHER_BATCH_(\\d+)_(\\d+)',',','yyyyMMdd',1,0,2,5,'(\\d+),(\\d+),(\\d+),(\\d+),(\\d+),(.*)','VOUCHER_BATCH_(\\d+)_(\\d+)',14,1,2,12, 2,1,0,1,1,'(\\d+),(\\d+),(.*)',1,1,1,NULL,NULL);
insert ignore into sym_pin_import_config (is_enabled,name,divide_amount_by,amount_in_contents,amount_in_filename,amount_line_num,amount_pos,amount_regex,batch_id_in_contents,batch_id_in_filename,batch_id_line_num,batch_id_pos,batch_id_regex,delimiter,expiry_date_format,expiry_in_contents,expiry_in_filename,expiry_line_num,expiry_pos,expiry_regex,filename_regex,pin_length,pin_pos,pin_start_line,serial_length,serial_pos,total_num_in_contents,total_num_in_filename,total_num_line_num,total_num_pos,total_num_regex,service_provider_id,voucher_provider_id,voucher_type_id,pgp_private_key_file,pgp_key_pass) VALUES (1,'VODAFONE_VODAFONE',100,0,1,NULL,0,NULL,0,1,NULL,2,'Voms(\\d+)_(\\d+).dec','##',NULL,0,0,NULL,NULL,NULL,'Voms(\\d+)_(\\d+).dec',14,2,2,15,3,1,0,1,1,'Total Vouchers=(\\d+),(.*)',4,4,1,NULL,NULL);
insert ignore into sym_pin_import_config (is_enabled,name,divide_amount_by,amount_in_contents,amount_in_filename,amount_line_num,amount_pos,amount_regex,batch_id_in_contents,batch_id_in_filename,batch_id_line_num,batch_id_pos,batch_id_regex,delimiter,expiry_date_format,expiry_in_contents,expiry_in_filename,expiry_line_num,expiry_pos,expiry_regex,filename_regex,pin_length,pin_pos,pin_start_line,serial_length,serial_pos,total_num_in_contents,total_num_in_filename,total_num_line_num,total_num_pos,total_num_regex,service_provider_id,voucher_provider_id,voucher_type_id,pgp_private_key_file,pgp_key_pass) VALUES (1,'TIGO_TIGO',10000,1,0,2,1,'FaceValue:(\\d+)',1,0,1,1,'Batch:(\\d+)',' ','yyyyMMdd',1,0,4,1,'StopDate:(\\d+)','(\\d+)K.txt',16,2,6,11,1,0,0,NULL,NULL,NULL,5,5,1,NULL,NULL);
insert ignore into sym_pin_import_config (is_enabled,name,divide_amount_by,amount_in_contents,amount_in_filename,amount_line_num,amount_pos,amount_regex,batch_id_in_contents,batch_id_in_filename,batch_id_line_num,batch_id_pos,batch_id_regex,delimiter,expiry_date_format,expiry_in_contents,expiry_in_filename,expiry_line_num,expiry_pos,expiry_regex,filename_regex,pin_length,pin_pos,pin_start_line,serial_length,serial_pos,total_num_in_contents,total_num_in_filename,total_num_line_num,total_num_pos,total_num_regex,service_provider_id,voucher_provider_id,voucher_type_id,pgp_private_key_file,pgp_key_pass) VALUES (1,'MTN_MTN',100,1,0,1,3,'(\\d+),(\\d+),(\\d+),(.*)',0,1,NULL,1,'(.*)_(.*)_(.*)_(.*)_(.*)',',','yyyyMMdd',1,0,1,5,'(\\d+),(\\d+),(\\d+),(\\d+),(\\d+),(.*)','GHA(.*)',14,1,1,10,2,0,0,NULL,NULL,NULL,3,3,1,'mtn_pgp_private.key','[ETTL*PGPk3y]');

insert ignore into sym_voucher (is_active,is_fixed,is_pin_based,units,voucher_provider_discount,voucher_type_id,voucher_value,service_provider_id,voucher_provider_id) values (1, 1, 1, 'GHS', 8, 1, '1.00', 1, 1);
insert ignore into sym_voucher (is_active,is_fixed,is_pin_based,units,voucher_provider_discount,voucher_type_id,voucher_value,service_provider_id,voucher_provider_id) values (1, 1, 1, 'GHS', 8, 1, '2.00', 1, 1);
insert ignore into sym_voucher (is_active,is_fixed,is_pin_based,units,voucher_provider_discount,voucher_type_id,voucher_value,service_provider_id,voucher_provider_id) values (1, 1, 1, 'GHS', 8, 1, '5.00', 1, 1);
insert ignore into sym_voucher (is_active,is_fixed,is_pin_based,units,voucher_provider_discount,voucher_type_id,voucher_value,service_provider_id,voucher_provider_id) values (1, 1, 1, 'GHS', 8, 1, '10.00', 1, 1);
insert ignore into sym_voucher (is_active,is_fixed,is_pin_based,units,voucher_provider_discount,voucher_type_id,voucher_value,service_provider_id,voucher_provider_id) values (1, 1, 1, 'GHS', 8, 1, '20.00', 1, 1);
insert ignore into sym_voucher (is_active,is_fixed,is_pin_based,units,voucher_provider_discount,voucher_type_id,voucher_value,service_provider_id,voucher_provider_id) values (1, 1, 1, 'GHS', 6.5, 1, '1.00', 3, 3);
insert ignore into sym_voucher (is_active,is_fixed,is_pin_based,units,voucher_provider_discount,voucher_type_id,voucher_value,service_provider_id,voucher_provider_id) values (1, 1, 1, 'GHS', 6.5, 1, '2.00', 3, 3);
insert ignore into sym_voucher (is_active,is_fixed,is_pin_based,units,voucher_provider_discount,voucher_type_id,voucher_value,service_provider_id,voucher_provider_id) values (1, 1, 1, 'GHS', 6.5, 1, '5.00', 3, 3);
insert ignore into sym_voucher (is_active,is_fixed,is_pin_based,units,voucher_provider_discount,voucher_type_id,voucher_value,service_provider_id,voucher_provider_id) values (1, 1, 1, 'GHS', 6.5, 1, '10.00', 3, 3);
insert ignore into sym_voucher (is_active,is_fixed,is_pin_based,units,voucher_provider_discount,voucher_type_id,voucher_value,service_provider_id,voucher_provider_id) values (1, 1, 1, 'GHS', 6.5, 1, '20.00', 3, 3);
insert ignore into sym_voucher (is_active,is_fixed,is_pin_based,units,voucher_provider_discount,voucher_type_id,voucher_value,service_provider_id,voucher_provider_id) values (1, 1, 1, 'GHS', 10, 1, '1.00', 4, 4);
insert ignore into sym_voucher (is_active,is_fixed,is_pin_based,units,voucher_provider_discount,voucher_type_id,voucher_value,service_provider_id,voucher_provider_id) values (1, 1, 1, 'GHS', 10, 1, '2.00', 4, 4);
insert ignore into sym_voucher (is_active,is_fixed,is_pin_based,units,voucher_provider_discount,voucher_type_id,voucher_value,service_provider_id,voucher_provider_id) values (1, 1, 1, 'GHS', 10, 1, '5.00', 4, 4);
insert ignore into sym_voucher (is_active,is_fixed,is_pin_based,units,voucher_provider_discount,voucher_type_id,voucher_value,service_provider_id,voucher_provider_id) values (1, 1, 1, 'GHS', 10, 1, '10.00', 4, 4);
insert ignore into sym_voucher (is_active,is_fixed,is_pin_based,units,voucher_provider_discount,voucher_type_id,voucher_value,service_provider_id,voucher_provider_id) values (1, 1, 1, 'GHS', 10, 1, '20.00', 4, 4);
insert ignore into sym_voucher (is_active,is_fixed,is_pin_based,units,voucher_provider_discount,voucher_type_id,voucher_value,service_provider_id,voucher_provider_id) values (1, 1, 1, 'GHS', 8, 1, '1.00', 5, 5);
insert ignore into sym_voucher (is_active,is_fixed,is_pin_based,units,voucher_provider_discount,voucher_type_id,voucher_value,service_provider_id,voucher_provider_id) values (1, 1, 1, 'GHS', 8, 1, '2.00', 5, 5);
insert ignore into sym_voucher (is_active,is_fixed,is_pin_based,units,voucher_provider_discount,voucher_type_id,voucher_value,service_provider_id,voucher_provider_id) values (1, 1, 1, 'GHS', 8, 1, '5.00', 5, 5);
insert ignore into sym_voucher (is_active,is_fixed,is_pin_based,units,voucher_provider_discount,voucher_type_id,voucher_value,service_provider_id,voucher_provider_id) values (1, 1, 1, 'GHS', 8, 1, '10.00', 5, 5);
insert ignore into sym_voucher (is_active,is_fixed,is_pin_based,units,voucher_provider_discount,voucher_type_id,voucher_value,service_provider_id,voucher_provider_id) values (1, 1, 1, 'GHS', 10, 4, '7.50', 7, 7);

insert ignore into sym_wallet_group (wallet_group_id,name,is_enabled,default_discount) values (1, 'DEFAULT_WALLET_GROUP', 1, 3.5);
insert ignore into sym_wallet_group (wallet_group_id,name,is_enabled,default_discount) values (2, 'MEDIUM_VOLUME_WALLET_GROUP', 1, 4.0);
insert ignore into sym_wallet_group (wallet_group_id,name,is_enabled,default_discount) values (3, 'HIGH_VOLUME_WALLET_GROUP', 1, 5.0);

insert ignore into sym_wallet_group_voucher (wallet_group_id,voucher_id,wallet_discount) select 1, v.voucher_id, 3.5 from sym_voucher v;
insert ignore into sym_wallet_group_voucher (wallet_group_id,voucher_id,wallet_discount) select 2, v.voucher_id, 4.0 from sym_voucher v;
insert ignore into sym_wallet_group_voucher (wallet_group_id,voucher_id,wallet_discount) select 3, v.voucher_id, 5.0 from sym_voucher v;

insert ignore into sym_company(company_id,company_name,address_line_1,address_line_2,address_city,address_country_id,phone1,phone2) values (1,'T3raTech','4 Janeen Close','Groombridge, Mt. Pleasant','Harare',2,'263785107830','27627938765');
insert ignore into sym_user(first_name,last_name,username,email,msisdn,msisdn2,salt,user_status_id,country_id,language_id,password,password_tries,pin,pin_tries) values ('Tsungai','Kaviya','admin','tsungai.kaviya@gmail.com','263785107830','27627938765','b4ou790Xz4jBfY0B',30,2,1,'659f6d313bb6fb10ae238ed2ecd4f3365a6a72b8ba8fbe891265a17a6a7335',0,NULL,0);
insert ignore into sym_auth_user (sym_user_id,channel_id,auth_group_id,device_id,registration_date,last_auth_date,last_login_date) SELECT su.sym_user_id,2,1,null,sysdate(),NULL,NULL FROM sym_user su WHERE su.username = 'admin';
insert ignore into sym_wallet (wallet_id, current_balance, company_id, wallet_admin_user_id, wallet_group_id) values (1, '0.00', 1, 1, 1);
update sym_user set wallet_id = 1 where sym_user_id = 1;