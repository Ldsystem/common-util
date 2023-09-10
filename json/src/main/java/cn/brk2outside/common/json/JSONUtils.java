package cn.brk2outside.common.json;

import cn.brk2outside.common.lang.reflect.TypeUtil;
import cn.brk2outside.common.lang.unsafe.Unsafe;
import cn.brk2outside.common.lang.unsafe.UnsafeBiFunction;
import cn.brk2outside.common.lang.unsafe.UnsafeTriFunction;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * JSON工具, 解决字段访问问题
 */
@SuppressWarnings("unused")
public class JSONUtils {

    public static <T> byte[] getBytes(T t) {
        return Unsafe.sneakyThrows(input -> {
            if (null == t)
                return new byte[0];
            if (input instanceof Map)
                return getMapper().writerFor(new TypeReference<Map<String, Object>>() {
                }).writeValueAsBytes(input);
            else if (input instanceof Collection)
                return getMapper().writerFor(new TypeReference<Collection<Object>>() {
                }).writeValueAsBytes(input);
            else
                return getMapper().writerFor(input.getClass())
                        .writeValueAsBytes(input);
        }, t);
    }

    /**
     * 反序列化的时候不能用TypeReference, 会优先反序列化成LinkedHashMap
     */
    public static <T> T readObject(String json, Class<T> tClass) {
        return Unsafe.sneakyThrows(
                (UnsafeBiFunction<? super Class<T>, ? super String, ? extends T>) (clz, str) -> getMapper().readerFor(clz).readValue(str),
                tClass, json);
    }

    public static <T> T readObject(String json, TypeReference<T> typeReference) {
        return Unsafe.sneakyThrows(
                (UnsafeBiFunction<? super TypeReference<T>, ? super String, ? extends T>) (ref, str) -> getMapper().readerFor(ref).readValue(str),
                typeReference, json);
    }

    /**
     *  读取泛型对象
     * @param json              json字符串
     * @param wrapper           泛型包装类
     * @param parameterClasses  泛型参数
     * @return                  泛型对象
     * @param <T> T            泛型
     */
    public static <T> T readObject(String json, Class<T> wrapper, Class<?>... parameterClasses) {
        return Unsafe.sneakyThrows(
                (UnsafeTriFunction<? super Class<T>, ? super Class<?>[], ? super String, ? extends T>) (w, p, s) -> {
                    Type type = TypeUtil.parameterizedType(w, p);
                    return getMapper().readerFor(getMapper().getTypeFactory().constructType(type)).readValue(s);
                },
                wrapper, parameterClasses, json);
    }

    public static <K, V> Map<K, V> readMap(String json) {
        return Unsafe.sneakyThrows(
                (UnsafeBiFunction<? super TypeReference<Map<K, V>>, ? super String, ? extends Map<K, V>>) (ref, str) -> getMapper().readerFor(ref).readValue(str),
                new TypeReference<Map<K, V>>() {
                }, json);
    }

    public static <T> List<T> readList(String json) {
        return readList(json, null);
    }


    public static <T> List<T> readList(String json, final Class<T> clz) {
        TypeReference<List<T>> reference = clz == null ? new TypeReference<List<T>>() {
        } : new TypeReference<List<T>>() {
            @Override
            public Type getType() {
                return TypeUtil.parameterizedType(List.class, clz);
            }
        };

        return readObject(reference, json);
    }

    public static <T> String toJsonString(T t) {
        return writeJsonString(new TypeReference<T>() {
        }, t);
    }

    public static <K, V> String toJsonString(List<Map<K, V>> array) {
        return writeJsonString(new TypeReference<List<Map<K, V>>>() {
        }, array);
    }
    public static <K, V> String toJsonString(Map<K, V> map) {
        return writeJsonString(new TypeReference<Map<K, V>>() {
        }, map);
    }

    private static String writeJsonString(TypeReference<?> typeReference, Object value) {
        return Unsafe.sneakyThrows(
                (UnsafeBiFunction<? super TypeReference<?>, ? super Object, ? extends String>) (ref, obj) -> getMapper().writerFor(ref).writeValueAsString(obj),
                typeReference, value);
    }

    private static <T> T readObject(TypeReference<T> typeReference, String json) {
        return Unsafe.sneakyThrows(
                (UnsafeBiFunction<? super TypeReference<T>, ? super String, ? extends T>) (ref, str) -> getMapper().readerFor(ref).readValue(str),
                typeReference, json);
    }

    /**
     * 使用成员变量进行序列化/反序列化, 不使用getter/setter, 会导致变量名大小写与原来不一致
     */
    private static ObjectMapper getMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.setVisibility(
                objectMapper.getVisibilityChecker()
                        .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                        .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                        .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                        .withCreatorVisibility(JsonAutoDetect.Visibility.NONE)
                        .withIsGetterVisibility(JsonAutoDetect.Visibility.NONE)
        );
        return objectMapper;
    }

}
