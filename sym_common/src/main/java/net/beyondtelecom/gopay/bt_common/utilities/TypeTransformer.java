package net.beyondtelecom.gopay.bt_common.utilities;


@FunctionalInterface
public interface TypeTransformer<T, K> {

    K transform(T instance);

}
