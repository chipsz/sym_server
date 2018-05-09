package net.symbiosis.core_lib.enumeration;

public enum SymChannel {
    DESKTOP, WEB, POS_MACHINE, POS_TILL, USSD, SMART_PHONE;

    public static SymChannel fromString(String channel) {
        for (SymChannel channelEnum : SymChannel.values()) {
            if (channelEnum.name().equals(channel.toUpperCase())) {
                return channelEnum;
            }
        }
        return null;
    }
}
