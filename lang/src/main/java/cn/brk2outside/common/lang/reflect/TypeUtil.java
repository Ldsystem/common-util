package cn.brk2outside.common.lang.reflect;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.Objects;

/**
 * create from {@link org.apache.commons.lang3.reflect.TypeUtils}, with minimal dependencies.
 *
 * @author liushenglong_8597@outlook.com
 */
public class TypeUtil {

    /**
     * parameterized type from given generic type and actual type arguments
     */
    public static ParameterizedType parameterizedType(Class<?> raw, Type... actualTypeArguments) {
        Type useOwner;
        if (raw.getEnclosingClass() == null) {
            useOwner = null;
        } else {
            useOwner = raw.getEnclosingClass();
        }
        return new ParameterizedTypeImpl(raw, useOwner, actualTypeArguments);
    }

    private static boolean equals(final ParameterizedType p, final Type t) {
        if (t instanceof ParameterizedType other) {
            if (equals(p.getRawType(), other.getRawType()) && equals(p.getOwnerType(), other.getOwnerType())) {
                return equals(p.getActualTypeArguments(), other.getActualTypeArguments());
            }
        }
        return false;
    }

    public static boolean equals(final Type t1, final Type t2) {
        if (Objects.equals(t1, t2)) {
            return true;
        }
        if (t1 instanceof ParameterizedType) {
            return equals((ParameterizedType) t1, t2);
        }
        if (t1 instanceof GenericArrayType) {
            return equals(t1, t2);
        }
        if (t1 instanceof WildcardType) {
            return equals(t1, t2);
        }
        return false;
    }

    private static boolean equals(final Type[] t1, final Type[] t2) {
        if (t1.length == t2.length) {
            for (int i = 0; i < t1.length; i++) {
                if (!equals(t1[i], t2[i])) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * ParameterizedType implementation class.
     *
     * @since 3.2
     */
    private record ParameterizedTypeImpl(Class<?> raw, Type useOwner,
                                         Type[] typeArguments) implements ParameterizedType {
        /**
         * Constructor
         *
         * @param raw           type
         * @param useOwner      owner type to use, if any
         * @param typeArguments formal type arguments
         */
        private ParameterizedTypeImpl(final Class<?> raw, final Type useOwner, final Type[] typeArguments) {
            this.raw = raw;
            this.useOwner = useOwner;
            this.typeArguments = Arrays.copyOf(typeArguments, typeArguments.length, Type[].class);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Type getRawType() {
            return raw;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Type getOwnerType() {
            return useOwner;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Type[] getActualTypeArguments() {
            return typeArguments.clone();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(final Object obj) {
            return obj == this || obj instanceof ParameterizedType && TypeUtil.equals(this, (ParameterizedType) obj);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            int result = 71 << 4;
            result |= raw.hashCode();
            result <<= 4;
            result |= Objects.hashCode(useOwner);
            result <<= 8;
            result |= Arrays.hashCode(typeArguments);
            return result;
        }
    }

}
