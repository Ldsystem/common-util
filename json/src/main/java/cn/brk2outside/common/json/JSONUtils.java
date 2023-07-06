package cn.brk2outside.common.json;

import cn.brk2outside.common.lang.reflect.TypeUtil;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * JSON工具, 解决字段访问问题
 */
@SuppressWarnings("unused")
public class JSONUtils {

    @SneakyThrows
    public static <T> byte[] getBytes(T t) {
        if (t instanceof Map)
            return getMapper().writerFor(new TypeReference<Map<String, Object>>() {
            }).writeValueAsBytes(t);
        else if (t instanceof Collection)
            return getMapper().writerFor(new TypeReference<Collection<Object>>() {
            }).writeValueAsBytes(t);
        else
            return getMapper().writerFor(t.getClass())
                    .writeValueAsBytes(t);
    }

    /**
     * 反序列化的时候不能用TypeReference, 会优先反序列化成LinkedHashMap
     */
    @SneakyThrows
    public static <T> T readObject(String json, Class<T> tClass) {
        return getMapper().readerFor(tClass).readValue(json);
    }

    @SneakyThrows
    public static <T> T readObject(String json, TypeReference<T> typeReference) {
        return readObject(typeReference, json);
    }

    /**
     *  读取泛型对象
     * @param json              json字符串
     * @param wrapper           泛型包装类
     * @param parameterClasses  泛型参数
     * @return                  泛型对象
     * @param <T> T            泛型
     */
    @SneakyThrows
    public static <T> T readObject(String json, Class<T> wrapper, Class<?>... parameterClasses) {
        return readObject(new TypeReference<T>() {
            @Override
            public Type getType() {
                return TypeUtil.parameterizedType(wrapper, parameterClasses);
            }
        }, json);
    }

    @SneakyThrows
    public static <K, V> Map<K, V> readMap(String json) {
        return readObject(new TypeReference<Map<K, V>>() {
        }, json);
    }

    public static <T> List<T> readList(String json) {
        return readList(json, null);
    }


    @SneakyThrows
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

    @SneakyThrows
    public static <T> String toJsonString(T t) {
        return writeJsonString(new TypeReference<T>() {
        }, t);
    }

    public static <K, V> String toJsonString(List<Map<K, V>> array) {
        return writeJsonString(new TypeReference<List<Map<K, V>>>() {
        }, array);
    }
    @SneakyThrows
    public static <K, V> String toJsonString(Map<K, V> map) {
        return writeJsonString(new TypeReference<Map<K, V>>() {
        }, map);
    }

    @SneakyThrows
    private static String writeJsonString(TypeReference<?> typeReference, Object value) {
        return getMapper().writerFor(typeReference)
                .writeValueAsString(value);
    }

    private static <T> T readObject(TypeReference<T> typeReference, String json) throws JsonProcessingException {
        return getMapper().readerFor(typeReference).readValue(json);
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
