insert ignore into bt_country (name,is_enabled,iso_code_2,iso_code_3,dialing_code) values ('ZIMBABWE',0,'ZW','ZWE',263);
insert ignore into bt_country (name,is_enabled,iso_code_2,iso_code_3,dialing_code) values ('SOUTH AFRICA',0,'ZA','ZAF',27);

insert ignore into bt_channel (name,is_enabled,is_pin_based) values ('DESKTOP',1,0);
insert ignore into bt_channel (name,is_enabled,is_pin_based) values ('WEB',1,0);
insert ignore into bt_channel (name,is_enabled,is_pin_based) values ('POS_MACHINE',1,1);
insert ignore into bt_channel (name,is_enabled,is_pin_based) values ('POS_TILL',1,1);
insert ignore into bt_channel (name,is_enabled,is_pin_based) values ('USSD',1,1);
insert ignore into bt_channel (name,is_enabled,is_pin_based) values ('SMART_PHONE',1,1);

insert ignore into bt_currency (currency_name,iso_4217_code,iso_4217_num) values ('United States Dollar','USD','840');
insert ignore into bt_currency (currency_name,iso_4217_code,iso_4217_num) values ('South African Rand','ZAR','710');
insert ignore into bt_currency (currency_name,iso_4217_code,iso_4217_num) values ('Botswana Pula','BWP','072');
insert ignore into bt_currency (currency_name,iso_4217_code,iso_4217_num) values ('Pound Sterling','GBP','826');
insert ignore into bt_currency (currency_name,iso_4217_code,iso_4217_num) values ('Euro','EUR','978');
insert ignore into bt_currency (currency_name,iso_4217_code,iso_4217_num) values ('Zambian Kwacha','ZMW','967');

insert ignore into bt_event_type (name,is_enabled) values ('REGISTRATION',1);
insert ignore into bt_event_type (name,is_enabled) values ('LOGIN',1);
insert ignore into bt_event_type (name,is_enabled) values ('LOGOUT',1);
insert ignore into bt_event_type (name,is_enabled) values ('CASHOUT',1);
insert ignore into bt_event_type (name,is_enabled) values ('LOAD_WALLET',1);
insert ignore into bt_event_type (name,is_enabled) values ('UPDATE_USER',1);
insert ignore into bt_event_type (name,is_enabled) values ('CREATE_USER',1);
insert ignore into bt_event_type (name,is_enabled) values ('UPDATE_PASSWORD',1);
insert ignore into bt_event_type (name,is_enabled) values ('RESET_PASSWORD',1);
insert ignore into bt_event_type (name,is_enabled) values ('RESET_PIN',1);

insert ignore into bt_financial_institution_type (institution_type_id,name,is_enabled) values (1,'BANK',1);
insert ignore into bt_financial_institution_type (institution_type_id,name,is_enabled) values (2,'MOBILE_BANK',1);
insert ignore into bt_financial_institution_type (institution_type_id,name,is_enabled) values (3,'ONLINE_BANK',1);

insert ignore into bt_language (name,is_enabled) values ('ENGLISH',1);

insert ignore into bt_preference (name,is_enabled) values ('PF_WEB_THEME',1);

insert ignore into bt_response_code(id,name,is_enabled,response_message) values(1, 'GENERAL_ERROR',1,'A general error occurred');
insert ignore into bt_response_code(id,name,is_enabled,response_message) values(-1,'CONFIGURATION_INVALID',1,'Specified configuration is not valid');
insert ignore into bt_response_code(id,name,is_enabled,response_message) values(15,'DATA_NOT_FOUND',1,'Data does not exist');
insert ignore into bt_response_code(id,name,is_enabled,response_message) values(16,'NOT_SUPPORTED',1,'Not supported');

insert ignore into bt_response_code(id,name,is_enabled,response_message) values(2,'INPUT_INCOMPLETE_REQUEST',1,'Incomplete request specified');
insert ignore into bt_response_code(id,name,is_enabled,response_message) values(3,'INPUT_INVALID_REQUEST',1,'Invalid request specified');
insert ignore into bt_response_code(id,name,is_enabled,response_message) values(4,'INPUT_INVALID_EMAIL',1,'Email provided was not valid');
insert ignore into bt_response_code(id,name,is_enabled,response_message) values(5,'INPUT_INVALID_MSISDN',1,'Phone number provided was not valid');
insert ignore into bt_response_code(id,name,is_enabled,response_message) values(6,'INPUT_INVALID_FIRST_NAME',1,'First name provided was not valid');
insert ignore into bt_response_code(id,name,is_enabled,response_message) values(7,'INPUT_INVALID_LAST_NAME',1,'Last name provided was not valid');
insert ignore into bt_response_code(id,name,is_enabled,response_message) values(8,'INPUT_INVALID_USERNAME',1,'Username provided was not valid');
insert ignore into bt_response_code(id,name,is_enabled,response_message) values(9,'INPUT_INVALID_PASSWORD',1,'Password provided was not valid');
insert ignore into bt_response_code(id,name,is_enabled,response_message) values(10,'INPUT_INVALID_NAME',1,'Name provided was not valid');
insert ignore into bt_response_code(id,name,is_enabled,response_message) values(11,'INPUT_INVALID_AMOUNT',1,'Invalid amount specified');
insert ignore into bt_response_code(id,name,is_enabled,response_message) values(12,'INPUT_INVALID_WALLET',1,'Invalid wallet specified');
insert ignore into bt_response_code(id,name,is_enabled,response_message) values(13,'INPUT_INVALID_CASHIER',1,'Invalid cashier name specified');

insert ignore into bt_response_code(id,name,is_enabled,response_message) values(20,'AUTH_INSUFFICIENT_PRIVILEGES',1,'Insufficient privileges for current operation');
insert ignore into bt_response_code(id,name,is_enabled,response_message) values(21,'AUTH_AUTHENTICATION_FAILED',1,'Authentication failed');
insert ignore into bt_response_code(id,name,is_enabled,response_message) values(22,'AUTH_INCORRECT_PASSWORD',1,'Password is incorrect');
insert ignore into bt_response_code(id,name,is_enabled,response_message) values(23,'AUTH_NON_EXISTENT',1,'Account does not exist');

insert ignore into bt_response_code(id,name,is_enabled,response_message) values(30,'ACC_ACTIVE',1,'Account is active');
insert ignore into bt_response_code(id,name,is_enabled,response_message) values(31,'ACC_INACTIVE',1,'Account is inactive');
insert ignore into bt_response_code(id,name,is_enabled,response_message) values(32,'ACC_SUSPENDED',1,'Account has been suspended');
insert ignore into bt_response_code(id,name,is_enabled,response_message) values(33,'ACC_CLOSED',1,'Account has been closed');
insert ignore into bt_response_code(id,name,is_enabled,response_message) values(34,'ACC_PASSWORD_TRIES_EXCEEDED',1,'Password tries exceeded');
insert ignore into bt_response_code(id,name,is_enabled,response_message) values(35,'ACC_PASSWORD_EXPIRED',1,'Password expired');

insert ignore into bt_response_code(id,name,is_enabled,response_message) values(40,'CONNECTION_FAILED',1,'Connection failed');
insert ignore into bt_response_code(id,name,is_enabled,response_message) values(41,'UNKNOWN_HOST',1,'Unknown host');
insert ignore into bt_response_code(id,name,is_enabled,response_message) values(42,'CONNECTION_REFUSED',1,'Connection Refused');
insert ignore into bt_response_code(id,name,is_enabled,response_message) values(43,'TIMEOUT',1,'Timeout elapsed before transaction completion');

insert ignore into bt_response_code(id,name,is_enabled,response_message) values(51,'INSUFFICIENT_FUNDS',1,'Insufficient funds');

insert ignore into bt_response_code(id,name,is_enabled,response_message) values(60,'INSUFFICIENT_STOCK',1,'Insufficient stock');

insert ignore into bt_response_code(id,name,is_enabled,response_message) values(80,'EXISTING_DATA_FOUND',1,'Existing data found');

insert ignore into bt_response_code(id,name,is_enabled,response_message) values(351,'REGISTRATION_FAILED',1,'Registration Failed');
insert ignore into bt_response_code(id,name,is_enabled,response_message) values(352,'PREVIOUS_USERNAME_FOUND',1,'Username has been previously registered');
insert ignore into bt_response_code(id,name,is_enabled,response_message) values(353,'PREVIOUS_MSISDN_FOUND',1,'Mobile number has been previously registered');
insert ignore into bt_response_code(id,name,is_enabled,response_message) values(354,'PREVIOUS_EMAIL_FOUND',1,'Email has been previously registered');
insert ignore into bt_response_code(id,name,is_enabled,response_message) values(355,'PREVIOUS_REGISTRATION_FOUND',1,'Previous registration found');

insert ignore into bt_response_code(id,name,is_enabled,response_message) values(0, 'SUCCESS',1,'Successful');
update bt_response_code set id = 0 where name = 'SUCCESS';

insert ignore into bt_financial_institution (institution_name,short_name,institution_type_id) values ('Agricultural Development Bank of Zimbabwe','Agribank',1);
insert ignore into bt_financial_institution (institution_name,short_name,institution_type_id) values ('BancABC Zimbabwe','BancABC',1);
insert ignore into bt_financial_institution (institution_name,short_name,institution_type_id) values ('Barclays Bank of Zimbabwe','Barclays Bank',1);
insert ignore into bt_financial_institution (institution_name,short_name,institution_type_id) values ('CABS','CABS',1);
insert ignore into bt_financial_institution (institution_name,short_name,institution_type_id) values ('CBZ Bank Limited','CBZ',1);
insert ignore into bt_financial_institution (institution_name,short_name,institution_type_id) values ('Ecobank Zimbabwe','Ecobank',1);
insert ignore into bt_financial_institution (institution_name,short_name,institution_type_id) values ('FBC Bank Limited','FBC',1);
insert ignore into bt_financial_institution (institution_name,short_name,institution_type_id) values ('MBCA Bank Limited','MBCA',1);
insert ignore into bt_financial_institution (institution_name,short_name,institution_type_id) values ('Metbank','Metbank',1);
insert ignore into bt_financial_institution (institution_name,short_name,institution_type_id) values ('NMB Bank Limited','NMB',1);
insert ignore into bt_financial_institution (institution_name,short_name,institution_type_id) values ('People\'s Own Savings Bank','POSB',1);
insert ignore into bt_financial_institution (institution_name,short_name,institution_type_id) values ('Stanbic Bank Zimbabwe Limited','Stanbic Bank',1);
insert ignore into bt_financial_institution (institution_name,short_name,institution_type_id) values ('Standard Chartered Zimbabwe','Standard Chartered',1);
insert ignore into bt_financial_institution (institution_name,short_name,institution_type_id) values ('Steward Bank','Steward Bank',1);
insert ignore into bt_financial_institution (institution_name,short_name,institution_type_id) values ('ZB Bank Limited','ZB Bank',1);

# insert ignore into bt_financial_institution (institution_id,institution_name,short_name,institution_type_id) values (100,'Wallet','Wallet',2);
insert ignore into bt_financial_institution (institution_id,institution_name,short_name,institution_type_id) values (101,'EcoCash','EcoCash',2);
insert ignore into bt_financial_institution (institution_id,institution_name,short_name,institution_type_id) values (102,'Telecash','Telecash',2);
insert ignore into bt_financial_institution (institution_id,institution_name,short_name,institution_type_id) values (103,'OneMoney','OneMoney',2);
insert ignore into bt_financial_institution (institution_id,institution_name,short_name,institution_type_id) values (104,'GetCash','GetCash',2);

insert ignore into bt_financial_institution (institution_id,institution_name,short_name,institution_type_id) values (200,'PayPal','PayPal',3);
insert ignore into bt_financial_institution (institution_id,institution_name,short_name,institution_type_id) values (201,'Skrill','Skrill',3);

insert ignore into bt_auth_group (name, is_enabled) values ('SUPER_USER',1);
insert ignore into bt_auth_group (name, is_enabled) values ('WEB_ADMIN',1);
insert ignore into bt_auth_group (name, is_enabled) values ('WEB_CLERK',1);
insert ignore into bt_auth_group (name, is_enabled) values ('WEB_AGENT',1);
insert ignore into bt_auth_group (name, is_enabled) values ('MOBILE_USER',1);

insert ignore into bt_role (name, is_enabled) values ('ROLE_WEB_MANAGE_SYSTEM', 1);

insert ignore into bt_role (name, is_enabled) values ('ROLE_WEB_MANAGE_USERS', 1);
insert ignore into bt_role (name, is_enabled) values ('ROLE_WEB_ADVANCED_MANAGE_USERS', 1);

insert ignore into bt_role (name, is_enabled) values ('ROLE_WEB_VIEW_REPORTS', 1);
insert ignore into bt_role (name, is_enabled) values ('ROLE_WEB_VIEW_SYSTEM_REPORTS', 1);
insert ignore into bt_role (name, is_enabled) values ('ROLE_WEB_VIEW_AUTHENTICATION_REPORTS', 1);

insert ignore into bt_role (name, is_enabled) values ('ROLE_WEB_VIEW_SINGLE_REPORTS', 1);
insert ignore into bt_role (name, is_enabled) values ('ROLE_WEB_VIEW_SINGLE_AUTHENTICATION_REPORTS', 1);

insert ignore into bt_role (name, is_enabled) values ('ROLE_WEB_MANAGE_SETTINGS', 1);

/* Insert all roles for SUPER_USER */
insert ignore into bt_auth_group_role (auth_group_id,role_id,name,is_enabled) select sg.auth_group_id,sr.role_id,CONCAT(sg.name,'_',sr.name),1 from bt_auth_group sg,bt_role sr where sg.name = 'SUPER_USER' and sr.name LIKE '%';

/* Insert roles for BT_SYSTEM_ADMIN */
insert ignore into bt_auth_group_role (auth_group_id,role_id,name,is_enabled) select sg.auth_group_id,sr.role_id,CONCAT(sg.name,'_',sr.name),1 from bt_auth_group sg,bt_role sr where sg.name = 'WEB_ADMIN' and sr.name LIKE 'ROLE_WEB_%';

/* Insert roles for BT_SYSTEM_CLERK */
insert ignore into bt_auth_group_role (auth_group_id,role_id,name,is_enabled) select sg.auth_group_id,sr.role_id,CONCAT(sg.name,'_',sr.name),1 from bt_auth_group sg,bt_role sr where sg.name = 'WEB_CLERK' and (sr.name IN ('ROLE_WEB_MANAGE_SYSTEM', 'ROLE_WEB_MANAGE_USERS', 'ROLE_WEB_MANAGE_SETTINGS') or sr.name LIKE 'ROLE_WEB_VIEW_%');

/* Insert roles for BT_SYSTEM_AGENT */
insert ignore into bt_auth_group_role (auth_group_id,role_id,name,is_enabled) select sg.auth_group_id,sr.role_id,CONCAT(sg.name,'_',sr.name),1 from bt_auth_group sg,bt_role sr where sg.name = 'WEB_AGENT' and (sr.name LIKE 'ROLE_WEB_VIEW_SINGLE_%' or sr.name = 'ROLE_WEB_MANAGE_SETTINGS');

insert ignore into bt_distribution_channel (distribution_channel_id,is_enabled,name) values (1, 1, 'RECEIPT');
insert ignore into bt_distribution_channel (distribution_channel_id,is_enabled,name) values (2, 1, 'SMS');

insert ignore into bt_deposit_type (deposit_type_id,is_enabled,name) values (1, 1, 'CASH');
insert ignore into bt_deposit_type (deposit_type_id,is_enabled,name) values (2, 1, 'BANK_DEPOSIT');
insert ignore into bt_deposit_type (deposit_type_id,is_enabled,name) values (3, 1, 'CREDIT_CARD');
insert ignore into bt_deposit_type (deposit_type_id,is_enabled,name) values (4, 1, 'DEBIT_CARD');
insert ignore into bt_deposit_type (deposit_type_id,is_enabled,name) values (5, 1, 'BANK_TRANSFER');

insert ignore into bt_wallet_group (wallet_group_id,name,is_enabled,default_discount) values (1, 'DEFAULT_WALLET_GROUP', 1, 1.0);

insert ignore into bt_company(company_id,company_name,address_line_1,address_line_2,address_city,address_country_id,phone1,phone2) values (1,'Symbiosis','14 Argyle Road','Avondale','Harare',2,'263785107830','27627938765');
insert ignore into bt_user(first_name,last_name,username,email,msisdn,msisdn2,salt,user_status_id,country_id,language_id,password,password_tries,pin,pin_tries) values ('Wallet','Admin','admin','gopayapps@gmail.com','263785107830','27627938765','b4ou790Xz4jBfY0B',30,2,1,'659f6d313bb6fb10ae238ed2ecd4f3365a6a72b8ba8fbe891265a17a6a7335',0,NULL,0);
insert ignore into bt_auth_user (bt_user_id,channel_id,auth_group_id,device_id,registration_date,last_auth_date,last_login_date) SELECT su.bt_user_id,2,1,null,sysdate(),NULL,NULL FROM bt_user su WHERE su.username = 'admin';
insert ignore into bt_wallet (wallet_id, current_balance, company_id, wallet_admin_user_id, wallet_group_id) values (1, '0.00', 1, 1, 1);
update bt_user set wallet_id = 1 where bt_user_id = 1;