insert ignore into sym_country (name,is_enabled,iso_code_2,iso_code_3,dialing_code) values ('ZIMBABWE',0,'ZW','ZWE',263);
insert ignore into sym_country (name,is_enabled,iso_code_2,iso_code_3,dialing_code) values ('SOUTH AFRICA',0,'ZA','ZAF',27);

insert ignore into sym_channel (name,is_enabled,is_pin_based) values ('DESKTOP',1,0);
insert ignore into sym_channel (name,is_enabled,is_pin_based) values ('WEB',1,0);
insert ignore into sym_channel (name,is_enabled,is_pin_based) values ('POS_MACHINE',1,1);
insert ignore into sym_channel (name,is_enabled,is_pin_based) values ('POS_TILL',1,1);
insert ignore into sym_channel (name,is_enabled,is_pin_based) values ('USSD',1,1);
insert ignore into sym_channel (name,is_enabled,is_pin_based) values ('SMART_PHONE',1,1);

insert ignore into sym_currency (currency_name,iso_4217_code,iso_4217_num) values ('United States Dollar','USD','840');
insert ignore into sym_currency (currency_name,iso_4217_code,iso_4217_num) values ('South African Rand','ZAR','710');
insert ignore into sym_currency (currency_name,iso_4217_code,iso_4217_num) values ('Botswana Pula','BWP','072');
insert ignore into sym_currency (currency_name,iso_4217_code,iso_4217_num) values ('Pound Sterling','GBP','826');
insert ignore into sym_currency (currency_name,iso_4217_code,iso_4217_num) values ('Euro','EUR','978');
insert ignore into sym_currency (currency_name,iso_4217_code,iso_4217_num) values ('Zambian Kwacha','ZMW','967');

insert ignore into sym_event_type (id,name,is_enabled) values (1000,'USER_REGISTRATION',1);
insert ignore into sym_event_type (id,name,is_enabled) values (1001,'USER_ASSIGN_CHANNEL',1);
insert ignore into sym_event_type (id,name,is_enabled) values (1002,'USER_CREATE',1);
insert ignore into sym_event_type (id,name,is_enabled) values (1003,'USER_LOGIN',1);
insert ignore into sym_event_type (id,name,is_enabled) values (1004,'USER_LOGOUT',1);
insert ignore into sym_event_type (id,name,is_enabled) values (1005,'USER_UPDATE',1);
insert ignore into sym_event_type (id,name,is_enabled) values (1006,'USER_PASSWORD_UPDATE',1);
insert ignore into sym_event_type (id,name,is_enabled) values (1007,'USER_PASSWORD_RESET',1);
insert ignore into sym_event_type (id,name,is_enabled) values (1008,'USER_PIN_RESET',1);

insert ignore into sym_event_type (id,name,is_enabled) values (1100,'WALLET_LOAD',1);
insert ignore into sym_event_type (id,name,is_enabled) values (1101,'WALLET_CASHOUT',1);
insert ignore into sym_event_type (id,name,is_enabled) values (1102,'WALLET_UPDATE',1);
insert ignore into sym_event_type (id,name,is_enabled) values (1103,'WALLET_GET_CASHOUT_ACCOUNTS',1);
insert ignore into sym_event_type (id,name,is_enabled) values (1104,'WALLET_ADD_CASHOUT_ACCOUNT',1);
insert ignore into sym_event_type (id,name,is_enabled) values (1105,'WALLET_DISABLE_CASHOUT_ACCOUNT',1);
insert ignore into sym_event_type (id,name,is_enabled) values (1106,'WALLET_GROUP_CREATE',1);
insert ignore into sym_event_type (id,name,is_enabled) values (1107,'WALLET_GROUP_UPDATE',1);
insert ignore into sym_event_type (id,name,is_enabled) values (1108,'WALLET_GROUP_VOUCHER_UPDATE',1);
insert ignore into sym_event_type (id,name,is_enabled) values (1109,'WALLET_SWIPE_IN',1);
insert ignore into sym_event_type (id,name,is_enabled) values (1110,'WALLET_TRANSFER',1);

insert ignore into sym_event_type (id,name,is_enabled) values (1200,'VOUCHER_IMPORT',1);
insert ignore into sym_event_type (id,name,is_enabled) values (1201,'VOUCHER_CREATE',1);
insert ignore into sym_event_type (id,name,is_enabled) values (1202,'VOUCHER_PURCHASE',1);
insert ignore into sym_event_type (id,name,is_enabled) values (1203,'VOUCHER_UPDATE',1);
insert ignore into sym_event_type (id,name,is_enabled) values (1204,'VOUCHER_TYPE_CREATE',1);
insert ignore into sym_event_type (id,name,is_enabled) values (1205,'VOUCHER_TYPE_UPDATE',1);
insert ignore into sym_event_type (id,name,is_enabled) values (1206,'VOUCHER_PROVIDER_CREATE',1);
insert ignore into sym_event_type (id,name,is_enabled) values (1207,'VOUCHER_PROVIDER_UPDATE',1);
insert ignore into sym_event_type (id,name,is_enabled) values (1208,'SERVICE_PROVIDER_CREATE',1);
insert ignore into sym_event_type (id,name,is_enabled) values (1209,'SERVICE_PROVIDER_UPDATE',1);

insert ignore into sym_event_type (id,name,is_enabled) values (1300,'DEVICE_POS_MACHINE_UPDATE',1);
insert ignore into sym_event_type (id,name,is_enabled) values (1301,'DEVICE_PHONE_UPDATE',1);

insert ignore into sym_financial_institution_type (institution_type_id,name,is_enabled) values (1,'BANK',1);
insert ignore into sym_financial_institution_type (institution_type_id,name,is_enabled) values (2,'MOBILE_BANK',1);
insert ignore into sym_financial_institution_type (institution_type_id,name,is_enabled) values (3,'ONLINE_BANK',1);

insert ignore into sym_language (name,is_enabled) values ('ENGLISH',1);

insert ignore into sym_preference (name,is_enabled) values ('PF_WEB_THEME',1);

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

insert ignore into sym_financial_institution (institution_name,short_name,institution_type_id) values ('Agricultural Development Bank of Zimbabwe','Agribank',1);
insert ignore into sym_financial_institution (institution_name,short_name,institution_type_id) values ('BancABC Zimbabwe','BancABC',1);
insert ignore into sym_financial_institution (institution_name,short_name,institution_type_id) values ('Barclays Bank of Zimbabwe','Barclays Bank',1);
insert ignore into sym_financial_institution (institution_name,short_name,institution_type_id) values ('CABS','CABS',1);
insert ignore into sym_financial_institution (institution_name,short_name,institution_type_id) values ('CBZ Bank Limited','CBZ',1);
insert ignore into sym_financial_institution (institution_name,short_name,institution_type_id) values ('Ecobank Zimbabwe','Ecobank',1);
insert ignore into sym_financial_institution (institution_name,short_name,institution_type_id) values ('FBC Bank Limited','FBC',1);
insert ignore into sym_financial_institution (institution_name,short_name,institution_type_id) values ('Metbank','Metbank',1);
insert ignore into sym_financial_institution (institution_name,short_name,institution_type_id) values ('Nedbank Limited','Nedbank',1);
insert ignore into sym_financial_institution (institution_name,short_name,institution_type_id) values ('NMB Bank Limited','NMB',1);
insert ignore into sym_financial_institution (institution_name,short_name,institution_type_id) values ('Peoples Own Savings Bank','POSB',1);
insert ignore into sym_financial_institution (institution_name,short_name,institution_type_id) values ('Stanbic Bank Zimbabwe Limited','Stanbic Bank',1);
insert ignore into sym_financial_institution (institution_name,short_name,institution_type_id) values ('Standard Chartered Zimbabwe','Standard Chartered',1);
insert ignore into sym_financial_institution (institution_name,short_name,institution_type_id) values ('Steward Bank','Steward Bank',1);
insert ignore into sym_financial_institution (institution_name,short_name,institution_type_id) values ('Womens Bank','Womens Bank',1);
insert ignore into sym_financial_institution (institution_name,short_name,institution_type_id) values ('ZB Bank Limited','ZB Bank',1);

insert ignore into sym_financial_institution (institution_id,institution_name,short_name,institution_type_id) values (100,'Symbiosis Wallet','SymWallet',2);
insert ignore into sym_financial_institution (institution_id,institution_name,short_name,institution_type_id) values (101,'EcoCash','EcoCash',2);
insert ignore into sym_financial_institution (institution_id,institution_name,short_name,institution_type_id) values (102,'Telecash','Telecash',2);
insert ignore into sym_financial_institution (institution_id,institution_name,short_name,institution_type_id) values (103,'OneMoney','OneMoney',2);
insert ignore into sym_financial_institution (institution_id,institution_name,short_name,institution_type_id) values (104,'GetCash','GetCash',2);

insert ignore into sym_financial_institution (institution_id,institution_name,short_name,institution_type_id) values (200,'PayPal','PayPal',3);
insert ignore into sym_financial_institution (institution_id,institution_name,short_name,institution_type_id) values (201,'Skrill','Skrill',3);

insert ignore into sym_auth_group (name, is_enabled) values ('SUPER_USER',1);
insert ignore into sym_auth_group (name, is_enabled) values ('WEB_ADMIN',1);
insert ignore into sym_auth_group (name, is_enabled) values ('WEB_CLERK',1);
insert ignore into sym_auth_group (name, is_enabled) values ('WEB_AGENT',1);
insert ignore into sym_auth_group (name, is_enabled) values ('POS_AGENT',1);
insert ignore into sym_auth_group (name, is_enabled) values ('POS_ADMIN',1);
insert ignore into sym_auth_group (name, is_enabled) values ('MOBILE_USER',1);

insert ignore into sym_role (name, is_enabled) values ('ROLE_WEB_MANAGE_EVD', 1);
insert ignore into sym_role (name, is_enabled) values ('ROLE_WEB_MANAGE_STOCK', 1);
insert ignore into sym_role (name, is_enabled) values ('ROLE_WEB_MANAGE_PROVIDERS', 1);
insert ignore into sym_role (name, is_enabled) values ('ROLE_WEB_MANAGE_VOUCHERS', 1);

insert ignore into sym_role (name, is_enabled) values ('ROLE_WEB_MANAGE_SYSTEM', 1);

insert ignore into sym_role (name, is_enabled) values ('ROLE_WEB_MANAGE_USERS', 1);
insert ignore into sym_role (name, is_enabled) values ('ROLE_WEB_ADVANCED_MANAGE_USERS', 1);

insert ignore into sym_role (name, is_enabled) values ('ROLE_WEB_VIEW_REPORTS', 1);
insert ignore into sym_role (name, is_enabled) values ('ROLE_WEB_VIEW_SYSTEM_REPORTS', 1);
insert ignore into sym_role (name, is_enabled) values ('ROLE_WEB_VIEW_AUTHENTICATION_REPORTS', 1);
insert ignore into sym_role (name, is_enabled) values ('ROLE_WEB_VIEW_PAYMENT_REPORTS', 1);
insert ignore into sym_role (name, is_enabled) values ('ROLE_WEB_VIEW_TRANSACTION_REPORTS', 1);

insert ignore into sym_role (name, is_enabled) values ('ROLE_WEB_VIEW_SINGLE_REPORTS', 1);
insert ignore into sym_role (name, is_enabled) values ('ROLE_WEB_VIEW_SINGLE_AUTHENTICATION_REPORTS', 1);
insert ignore into sym_role (name, is_enabled) values ('ROLE_WEB_VIEW_SINGLE_PAYMENT_REPORTS', 1);
insert ignore into sym_role (name, is_enabled) values ('ROLE_WEB_VIEW_SINGLE_TRANSACTION_REPORTS', 1);

insert ignore into sym_role (name, is_enabled) values ('ROLE_WEB_MANAGE_SETTINGS', 1);

insert ignore into sym_role (name, is_enabled) values ('ROLE_POS_MANAGE_SETTINGS', 1);

insert ignore into sym_role (name, is_enabled) values ('ROLE_MOBILE_MANAGE_SETTINGS', 1);

insert ignore into sym_auth_group_role (auth_group_id,role_id,name,is_enabled) select sg.auth_group_id,sr.role_id,CONCAT(sg.name,'_',sr.name),1 from sym_auth_group sg,sym_role sr where sg.name = 'SUPER_USER' and sr.name LIKE '%';

insert ignore into sym_auth_group_role (auth_group_id,role_id,name,is_enabled) select sg.auth_group_id,sr.role_id,CONCAT(sg.name,'_',sr.name),1 from sym_auth_group sg,sym_role sr where sg.name = 'WEB_ADMIN' and sr.name LIKE 'ROLE_WEB_%';

insert ignore into sym_auth_group_role (auth_group_id,role_id,name,is_enabled) select sg.auth_group_id,sr.role_id,CONCAT(sg.name,'_',sr.name),1 from sym_auth_group sg,sym_role sr where sg.name = 'WEB_CLERK' and (sr.name IN ('ROLE_WEB_MANAGE_SYSTEM', 'ROLE_WEB_MANAGE_EVD', 'ROLE_WEB_MANAGE_STOCK', 'ROLE_WEB_MANAGE_USERS', 'ROLE_WEB_MANAGE_SETTINGS') or sr.name LIKE 'ROLE_WEB_VIEW_%');

insert ignore into sym_auth_group_role (auth_group_id,role_id,name,is_enabled) select sg.auth_group_id,sr.role_id,CONCAT(sg.name,'_',sr.name),1 from sym_auth_group sg,sym_role sr where sg.name = 'WEB_AGENT' and (sr.name LIKE 'ROLE_WEB_VIEW_SINGLE_%' or sr.name = 'ROLE_WEB_MANAGE_SETTINGS');

insert ignore into sym_auth_group_role (auth_group_id,role_id,name,is_enabled) select sg.auth_group_id,sr.role_id,CONCAT(sg.name,'_',sr.name),1 from sym_auth_group sg,sym_role sr where sg.name = 'POS_ADMIN' and sr.name LIKE ('ROLE_POS_%');

insert ignore into sym_auth_group_role (auth_group_id,role_id,name,is_enabled) select sg.auth_group_id,sr.role_id,CONCAT(sg.name,'_',sr.name),1 from sym_auth_group sg,sym_role sr where sg.name = 'MOBILE_USER' and sr.name LIKE ('ROLE_MOBILE_%');

insert ignore into sym_voucher_type (voucher_type_id,is_enabled,name) values (1, 1, 'AIRTIME');
insert ignore into sym_voucher_type (voucher_type_id,is_enabled,name) values (2, 0, 'ELECTRICITY');
insert ignore into sym_voucher_type (voucher_type_id,is_enabled,name) values (3, 0, 'PAY_TV');
insert ignore into sym_voucher_type (voucher_type_id,is_enabled,name) values (4, 0, 'INTERNET');

insert ignore into sym_voucher_status (voucher_status_id,is_enabled,name) values (1, 1, 'NEW');
insert ignore into sym_voucher_status (voucher_status_id,is_enabled,name) values (2, 1, 'SOLD');
insert ignore into sym_voucher_status (voucher_status_id,is_enabled,name) values (3, 1, 'EXPIRED');

insert ignore into sym_distribution_channel (distribution_channel_id,is_enabled,name) values (1, 1, 'RECEIPT');
insert ignore into sym_distribution_channel (distribution_channel_id,is_enabled,name) values (2, 1, 'SMS');

insert ignore into sym_deposit_type (deposit_type_id,is_enabled,name) values (1, 1, 'CASH');
insert ignore into sym_deposit_type (deposit_type_id,is_enabled,name) values (2, 1, 'BANK_DEPOSIT');
insert ignore into sym_deposit_type (deposit_type_id,is_enabled,name) values (3, 1, 'CREDIT_CARD');
insert ignore into sym_deposit_type (deposit_type_id,is_enabled,name) values (4, 1, 'DEBIT_CARD');
insert ignore into sym_deposit_type (deposit_type_id,is_enabled,name) values (5, 1, 'BANK_TRANSFER');

insert ignore into sym_service_provider (service_provider_id, is_enabled, service_provider_name) values (1, 1, 'Econet');
insert ignore into sym_service_provider (service_provider_id, is_enabled, service_provider_name) values (2, 0, 'NetOne');
insert ignore into sym_service_provider (service_provider_id, is_enabled, service_provider_name) values (3, 1, 'Telecel');
insert ignore into sym_service_provider (service_provider_id, is_enabled, service_provider_name) values (4, 1, 'ZESA');
insert ignore into sym_service_provider (service_provider_id, is_enabled, service_provider_name) values (5, 0, 'DSTV');
insert ignore into sym_service_provider (service_provider_id, is_enabled, service_provider_name) values (6, 1, 'ZOL');
insert ignore into sym_service_provider (service_provider_id, is_enabled, service_provider_name) values (7, 1, 'TelOne');

insert ignore into sym_voucher_provider (voucher_provider_id, is_enabled, voucher_provider_name) values (1, 1, 'Econet');
insert ignore into sym_voucher_provider (voucher_provider_id, is_enabled, voucher_provider_name) values (2, 0, 'NetOne');
insert ignore into sym_voucher_provider (voucher_provider_id, is_enabled, voucher_provider_name) values (3, 1, 'Telecel');
insert ignore into sym_voucher_provider (voucher_provider_id, is_enabled, voucher_provider_name) values (4, 1, 'ZETDC');
insert ignore into sym_voucher_provider (voucher_provider_id, is_enabled, voucher_provider_name) values (5, 1, 'Multichoice');
insert ignore into sym_voucher_provider (voucher_provider_id, is_enabled, voucher_provider_name) values (6, 0, 'ZOL');
insert ignore into sym_voucher_provider (voucher_provider_id, is_enabled, voucher_provider_name) values (7, 0, 'TelOne');

insert ignore into sym_voucher (is_active,is_fixed,is_pin_based,units,voucher_provider_discount,voucher_type_id,voucher_value,service_provider_id,voucher_provider_id) values (1, 1, 1, '$', 8, 1, '1.00', 1, 1);
insert ignore into sym_voucher (is_active,is_fixed,is_pin_based,units,voucher_provider_discount,voucher_type_id,voucher_value,service_provider_id,voucher_provider_id) values (1, 1, 1, '$', 8, 1, '2.00', 1, 1);
insert ignore into sym_voucher (is_active,is_fixed,is_pin_based,units,voucher_provider_discount,voucher_type_id,voucher_value,service_provider_id,voucher_provider_id) values (1, 1, 1, '$', 8, 1, '5.00', 1, 1);
insert ignore into sym_voucher (is_active,is_fixed,is_pin_based,units,voucher_provider_discount,voucher_type_id,voucher_value,service_provider_id,voucher_provider_id) values (1, 1, 1, '$', 8, 1, '10.00', 1, 1);
insert ignore into sym_voucher (is_active,is_fixed,is_pin_based,units,voucher_provider_discount,voucher_type_id,voucher_value,service_provider_id,voucher_provider_id) values (1, 1, 1, '$', 8, 1, '20.00', 1, 1);

insert ignore into sym_voucher (is_active,is_fixed,is_pin_based,units,voucher_provider_discount,voucher_type_id,voucher_value,service_provider_id,voucher_provider_id) values (1, 1, 1, '$', 10, 1, '1.00', 2, 2);
insert ignore into sym_voucher (is_active,is_fixed,is_pin_based,units,voucher_provider_discount,voucher_type_id,voucher_value,service_provider_id,voucher_provider_id) values (1, 1, 1, '$', 10, 1, '2.00', 2, 2);
insert ignore into sym_voucher (is_active,is_fixed,is_pin_based,units,voucher_provider_discount,voucher_type_id,voucher_value,service_provider_id,voucher_provider_id) values (1, 1, 1, '$', 10, 1, '5.00', 2, 2);
insert ignore into sym_voucher (is_active,is_fixed,is_pin_based,units,voucher_provider_discount,voucher_type_id,voucher_value,service_provider_id,voucher_provider_id) values (1, 1, 1, '$', 10, 1, '10.00', 2, 2);
insert ignore into sym_voucher (is_active,is_fixed,is_pin_based,units,voucher_provider_discount,voucher_type_id,voucher_value,service_provider_id,voucher_provider_id) values (1, 1, 1, '$', 10, 1, '20.00', 2, 2);

insert ignore into sym_voucher (is_active,is_fixed,is_pin_based,units,voucher_provider_discount,voucher_type_id,voucher_value,service_provider_id,voucher_provider_id) values (1, 1, 1, '$', 6.5, 1, '1.00', 3, 3);
insert ignore into sym_voucher (is_active,is_fixed,is_pin_based,units,voucher_provider_discount,voucher_type_id,voucher_value,service_provider_id,voucher_provider_id) values (1, 1, 1, '$', 6.5, 1, '2.00', 3, 3);
insert ignore into sym_voucher (is_active,is_fixed,is_pin_based,units,voucher_provider_discount,voucher_type_id,voucher_value,service_provider_id,voucher_provider_id) values (1, 1, 1, '$', 6.5, 1, '5.00', 3, 3);
insert ignore into sym_voucher (is_active,is_fixed,is_pin_based,units,voucher_provider_discount,voucher_type_id,voucher_value,service_provider_id,voucher_provider_id) values (1, 1, 1, '$', 6.5, 1, '10.00', 3, 3);
insert ignore into sym_voucher (is_active,is_fixed,is_pin_based,units,voucher_provider_discount,voucher_type_id,voucher_value,service_provider_id,voucher_provider_id) values (1, 1, 1, '$', 6.5, 1, '20.00', 3, 3);

insert ignore into sym_voucher (is_active,is_fixed,is_pin_based,units,voucher_provider_discount,voucher_type_id,voucher_value,service_provider_id,voucher_provider_id) values (1, 0, 1, '$', 8, 2, '1.00', 4, 4);

insert ignore into sym_voucher (is_active,is_fixed,is_pin_based,units,voucher_provider_discount,voucher_type_id,voucher_value,service_provider_id,voucher_provider_id) values (1, 0, 1, '$', 8, 3, '10.00', 5, 5);

insert ignore into sym_voucher (is_active,is_fixed,is_pin_based,units,voucher_provider_discount,voucher_type_id,voucher_value,service_provider_id,voucher_provider_id) values (1, 1, 0, '$', 7, 4, '29.00', 6, 6);
insert ignore into sym_voucher (is_active,is_fixed,is_pin_based,units,voucher_provider_discount,voucher_type_id,voucher_value,service_provider_id,voucher_provider_id) values (1, 1, 0, '$', 7, 4, '89.00', 6, 6);
insert ignore into sym_voucher (is_active,is_fixed,is_pin_based,units,voucher_provider_discount,voucher_type_id,voucher_value,service_provider_id,voucher_provider_id) values (1, 1, 0, '$', 7, 4, '150.00', 6, 6);

insert ignore into sym_voucher (is_active,is_fixed,is_pin_based,units,voucher_provider_discount,voucher_type_id,voucher_value,service_provider_id,voucher_provider_id) values (1, 1, 0, '$', 10, 2, '30.00', 4, 4);
insert ignore into sym_voucher (is_active,is_fixed,is_pin_based,units,voucher_provider_discount,voucher_type_id,voucher_value,service_provider_id,voucher_provider_id) values (1, 1, 0, '$', 10, 2, '89.00', 4, 4);

insert ignore into sym_wallet_group (wallet_group_id,name,is_enabled,default_discount) values (1, 'DEFAULT_WALLET_GROUP', 1, 3.5);
insert ignore into sym_wallet_group (wallet_group_id,name,is_enabled,default_discount) values (2, 'MEDIUM_VOLUME_WALLET_GROUP', 1, 4.0);
insert ignore into sym_wallet_group (wallet_group_id,name,is_enabled,default_discount) values (3, 'HIGH_VOLUME_WALLET_GROUP', 1, 5.0);

insert ignore into sym_wallet_group_voucher (wallet_group_id,voucher_id,wallet_discount) select 1, v.voucher_id, 3.5 from sym_voucher v;
insert ignore into sym_wallet_group_voucher (wallet_group_id,voucher_id,wallet_discount) select 2, v.voucher_id, 4.0 from sym_voucher v;
insert ignore into sym_wallet_group_voucher (wallet_group_id,voucher_id,wallet_discount) select 3, v.voucher_id, 5.0 from sym_voucher v;


insert ignore into sym_company(company_id,company_name,address_line_1,address_line_2,address_city,address_country_id,phone1,phone2) values (1,'Symbiosis','Groombridge','Mt. Pleasant','Harare',2,'263785107830','27627938765');
insert ignore into sym_user(first_name,last_name,username,email,msisdn,msisdn2,salt,user_status_id,country_id,language_id,password,password_tries,pin,pin_tries) values ('Symbiosis','Admin','admin','tsungai.kaviya@gmail.com','263785107830','27627938765','b4ou790Xz4jBfY0B',30,2,1,'659f6d313bb6fb10ae238ed2ecd4f3365a6a72b8ba8fbe891265a17a6a7335',0,NULL,0);
insert ignore into sym_auth_user (sym_user_id,channel_id,auth_group_id,device_id,registration_date,last_auth_date,last_login_date) SELECT su.sym_user_id,2,1,null,sysdate(),NULL,NULL FROM sym_user su WHERE su.username = 'admin';
insert ignore into sym_wallet (wallet_id, current_balance, company_id, wallet_admin_user_id, wallet_group_id) values (1, '0.00', 1, 1, 1);
update sym_user set wallet_id = 1 where sym_user_id = 1;