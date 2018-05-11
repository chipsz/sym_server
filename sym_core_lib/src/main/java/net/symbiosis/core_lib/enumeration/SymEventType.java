package net.symbiosis.core_lib.enumeration;

/***************************************************************************
 * *
 * Created:     18 / 10 / 2016                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/

public enum SymEventType {
    REGISTRATION, LOGIN, LOGOUT,
    CREATE_USER, UPDATE_USER, UPDATE_PASSWORD, RESET_PASSWORD, RESET_PIN,
    LOAD_WALLET, CASHOUT, UPDATE_MERCHANT, VOUCHER_IMPORT,
    CREATE_WALLET_GROUP, UPDATE_WALLET_GROUP,
    CREATE_VOUCHER, UPDATE_VOUCHER,
    CREATE_VOUCHER_TYPE, UPDATE_VOUCHER_TYPE,
    CREATE_SERVICE_PROVIDER, UPDATE_SERVICE_PROVIDER,
    CREATE_VOUCHER_PROVIDER, UPDATE_VOUCHER_PROVIDER,
    UPDATE_WALLET_GROUP_VOUCHER, UPDATE_POS_MACHINE,
}
