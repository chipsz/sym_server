package net.symbiosis.common.utilities;


@FunctionalInterface
public interface TypeTransformer<T, K> {

    K transform(T instance);

}
