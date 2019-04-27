package net.symbiosis.common.utilities;

import net.symbiosis.core_lib.structure.Pair;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static net.symbiosis.common.utilities.Reflection.invoke;
import static net.symbiosis.common.utilities.Reflection.resolveMethod;
import static net.symbiosis.core_lib.structure.Pair.p;

public class TransformerService {

    static Map<Pair<Class<?>, Class<?>>, TypeTransformer<?, ?>> CONVERSION_REGISTRY;

    static {
        registerConversion(String.class, Long.class, Long::valueOf);
        registerConversion(String.class, Long.TYPE, Long::valueOf);
        registerConversion(String.class, Integer.class, Integer::valueOf);
        registerConversion(String.class, Integer.TYPE, Integer::valueOf);
        registerConversion(String.class, Byte.class, Byte::valueOf);
        registerConversion(String.class, Byte.TYPE, Byte::valueOf);
        registerConversion(String.class, Short.class, Short::valueOf);
        registerConversion(String.class, Short.TYPE, Short::valueOf);
        registerConversion(String.class, Boolean.class, SymTransformer::toBoolean);
        registerConversion(String.class, Boolean.TYPE, SymTransformer::toBoolean);
        registerConversion(String.class, Float.class, Float::valueOf);
        registerConversion(String.class, Float.TYPE, Float::valueOf);
        registerConversion(String.class, Double.class, Double::valueOf);
        registerConversion(String.class, Double.TYPE, Double::valueOf);
        registerConversion(String.class, Character.class, (instance) -> (instance != null) ? instance.charAt(0) : (char) (byte) 0);
        registerConversion(String.class, Character.TYPE, (instance) -> (instance != null) ? instance.charAt(0) : (char) (byte) 0);
        registerConversion(String.class, Double.TYPE, Double::valueOf);
        registerConversion(String.class, BigDecimal.class, BigDecimal::new);
        registerConversion(Number.class, Long.class, (instance) -> SymTransformer.toNumber(instance, Long.class));
        registerConversion(Number.class, Long.TYPE, (instance) -> SymTransformer.toNumber(instance, Long.class));
        registerConversion(Number.class, Integer.class, (instance) -> SymTransformer.toNumber(instance, Integer.class));
        registerConversion(Number.class, Integer.TYPE, (instance) -> SymTransformer.toNumber(instance, Integer.class));
        registerConversion(Number.class, Byte.class, (instance) -> SymTransformer.toNumber(instance, Byte.class));
        registerConversion(Number.class, Byte.TYPE, (instance) -> SymTransformer.toNumber(instance, Byte.class));
        registerConversion(Number.class, Short.class, (instance) -> SymTransformer.toNumber(instance, Short.class));
        registerConversion(Number.class, Short.TYPE, (instance) -> SymTransformer.toNumber(instance, Short.class));
        registerConversion(Number.class, Boolean.class, SymTransformer::toBoolean);
        registerConversion(Number.class, Boolean.TYPE, SymTransformer::toBoolean);
        registerConversion(Number.class, Float.class, (instance) -> SymTransformer.toNumber(instance, Float.class));
        registerConversion(Number.class, Float.TYPE, (instance) -> SymTransformer.toNumber(instance, Float.class));
        registerConversion(Number.class, Double.class, (instance) -> SymTransformer.toNumber(instance, Double.class));
        registerConversion(Number.class, Double.TYPE, (instance) -> SymTransformer.toNumber(instance, Double.class));
        registerConversion(Number.class, BigDecimal.class, SymTransformer::toBigDecimal);
        registerConversion(Object.class, String.class, Object::toString);
        registerConversion(Character.class, Boolean.class, SymTransformer::toBoolean);
        registerConversion(Number.class, Boolean.class, SymTransformer::toBoolean);
        registerConversion(Date.class, LocalDate.class, SymTransformer::dateToLocalDate);
        registerConversion(Date.class, LocalDateTime.class, SymTransformer::dateToLocalDateTime);
        registerConversion(LocalDate.class, Date.class, SymTransformer::localDateToDate);
        registerConversion(LocalDateTime.class, Date.class, SymTransformer::localDateTimeToDate);
        registerConversion(LocalDateTime.class, Long.class, SymTransformer::localDateTimeToLong);
        registerConversion(LocalDateTime.class, String.class, SymTransformer::localDateTimeToString);
        registerConversion(java.sql.Date.class, LocalDate.class, SymTransformer::sqlDateToLocalDate);
        registerConversion(Timestamp.class, LocalDateTime.class, SymTransformer::timestampToLocalDateTime);
        registerConversion(LocalDateTime.class, Timestamp.class, SymTransformer::localDateTimeToTimestamp);
        registerConversion(Long.class, LocalDateTime.class, SymTransformer::longTolocalDateTime);
        registerConversion(LocalDate.class, java.sql.Date.class, SymTransformer::localDateToSqlDate);
    }

    static Map<Pair<Class<?>, Class<?>>, TypeTransformer<?, ?>> getConversionRegistry() {
        if (CONVERSION_REGISTRY == null) {
            CONVERSION_REGISTRY = new HashMap<>();
        }
        return CONVERSION_REGISTRY;
    }

    public static <T, K> void registerConversion(Class<T> source, Class<K> target, TypeTransformer<T, K> transformer) {
        getConversionRegistry().put(p(source, target), transformer);
    }

    static TypeTransformer resolveTransformer(boolean isNull, Class<?> source, Class<?> target) {

        //special hack


        TypeTransformer<?, ?> typeTransformer = findWideningTransformer(source, target);
        if (typeTransformer == null) {
            return instance -> {
                if (target.isPrimitive()) {
                    try {
                        return target.getField("TYPE").get(null);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else if (isNull) {
                    return null;
                } else {
                    throw new IllegalArgumentException("No Type Transformer registered for " + source.getName() + "->" + target.getName());
                }
            };
        }
        return typeTransformer;
    }

    @SuppressWarnings("unchecked")
    private static TypeTransformer<?, ?> findWideningTransformer(Class<?> source, Class<?> target) {
        TypeTransformer<?, ?> typeTransformer = getConversionRegistry().get(p(source, target));
        if (typeTransformer != null) {
            return typeTransformer;
        }

        //do a widening search
        Pair<Class<?>, Class<?>> transformerKey = getConversionRegistry()
                .keySet()
                .stream()
                .filter(key -> {
                    boolean sourceAssignable = key.getLeft().isAssignableFrom(source);
                    boolean targetAssignable = key.getRight().isAssignableFrom(target);
                    return sourceAssignable && targetAssignable;
                })
                .findFirst().orElse(null);
        return getConversionRegistry().get(transformerKey);
    }

    @SuppressWarnings("unchecked")
    public static <T> T convert(Object instance, Class<T> target) {
        boolean isNull = instance == null;

        if (!isNull && (target.equals(instance.getClass()) || target.isAssignableFrom(instance.getClass()))) {
            return (T) instance;
        }

        if (instance instanceof String && Enum.class.isAssignableFrom(target)) {
            return (T) invoke(resolveMethod(Enum.class, "valueOf", Class.class, String.class), null, target, instance);
        }

        return (T) resolveTransformer(isNull, (isNull) ? null : instance.getClass(), target).transform(instance);
    }

}
