package cn.brk2outside.common.json;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Data;

/**
 * 2023/7/6
 *
 * @author liushenglong_8597@outlook.com
 */
public class JSONUtilTests {

    @Data
    static class GenericWrapper<T> {
        private T value;
    }

    @Data
    static class Body {
        private String name;
        private int age;
    }

    static void testReadObj() {
        String json = "{\"value\":{\"name\":\"brk2\",\"age\":18}}";
        GenericWrapper<Body> body = JSONUtils.readObject(json, new TypeReference<GenericWrapper<Body>>() {});
        System.out.println(body);
        @SuppressWarnings("rawtypes")
        GenericWrapper genericWrapper = JSONUtils.readObject(json, GenericWrapper.class, Body.class);
        System.out.println(genericWrapper);
    }

    public static void main(String[] args) {
        testReadObj();
    }

}
