package net.symbiosis.core_lib.enumeration;

/***************************************************************************
 * *
 * Created:     18 / 10 / 2016                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/

public enum SymEventType {

    //AUTHENTICATION
    USER_REGISTRATION, USER_ASSIGN_CHANNEL, USER_CREATE, USER_LOGIN, USER_LOGOUT, USER_UPDATE,
    USER_PASSWORD_UPDATE, USER_PASSWORD_RESET, USER_PIN_RESET,

    //WALLET
    WALLET_LOAD, WALLET_CASHOUT, WALLET_UPDATE, WALLET_GET_CASHOUT_ACCOUNTS, WALLET_ADD_CASHOUT_ACCOUNT, WALLET_DISABLE_CASHOUT_ACCOUNT,
    WALLET_GROUP_CREATE, WALLET_GROUP_UPDATE, WALLET_GROUP_VOUCHER_DISCOUNT_UPDATE, WALLET_GROUP_WALLET_CHARGE_UPDATE,
    WALLET_SWIPE_IN, WALLET_TRANSFER, WALLET_HISTORY,

    //VOUCHER
    VOUCHER_IMPORT, VOUCHER_CREATE, VOUCHER_PURCHASE, VOUCHER_UPDATE,
    VOUCHER_TYPE_CREATE, VOUCHER_TYPE_UPDATE,
    VOUCHER_PROVIDER_CREATE, VOUCHER_PROVIDER_UPDATE,
    SERVICE_PROVIDER_CREATE, SERVICE_PROVIDER_UPDATE,

    //DEVICE
    DEVICE_POS_MACHINE_UPDATE, DEVICE_PHONE_UPDATE,
}
