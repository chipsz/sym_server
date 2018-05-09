package net.beyondtelecom.bt_core_lib.enumeration;

public enum BTChannel
{
	DESKTOP, WEB, POS_MACHINE, POS_TILL, USSD, SMART_PHONE;

	public static net.beyondtelecom.bt_core_lib.enumeration.BTChannel fromString(String channel) {
		for (net.beyondtelecom.bt_core_lib.enumeration.BTChannel channelEnum : net.beyondtelecom.bt_core_lib.enumeration.BTChannel.values()) {
			if (channelEnum.name().equals(channel.toUpperCase())) {
				return channelEnum;
			}
		}
		return null;
	}
}
