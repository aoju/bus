/*********************************************************************************
 *                                                                               *
 * The MIT License (MIT)                                                         *
 *                                                                               *
 * Copyright (c) 2015-2023 aoju.org and other contributors.                      *
 *                                                                               *
 * Permission is hereby granted, free of charge, to any person obtaining a copy  *
 * of this software and associated documentation files (the "Software"), to deal *
 * in the Software without restriction, including without limitation the rights  *
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell     *
 * copies of the Software, and to permit persons to whom the Software is         *
 * furnished to do so, subject to the following conditions:                      *
 *                                                                               *
 * The above copyright notice and this permission notice shall be included in    *
 * all copies or substantial portions of the Software.                           *
 *                                                                               *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR    *
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,      *
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE   *
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER        *
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, *
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN     *
 * THE SOFTWARE.                                                                 *
 *                                                                               *
 ********************************************************************************/
package org.aoju.bus.core.convert;

import org.aoju.bus.core.exception.ConvertException;
import org.aoju.bus.core.toolkit.ObjectKit;
import org.aoju.bus.core.toolkit.StringKit;

import java.util.function.Function;

/**
 * 原始类型转换器<br>
 * 支持类型为：<br>
 * <ul>
 * 		<li>{@code byte}</li>
 * 		<li>{@code short}</li>
 * 		 <li>{@code int}</li>
 * 		 <li>{@code long}</li>
 * 		<li>{@code float}</li>
 * 		<li>{@code double}</li>
 * 		<li>{@code char}</li>
 * 		<li>{@code boolean}</li>
 * </ul>
 *
 * @author Kimi Liu
 * @since Java 17+
 */
public class PrimitiveConverter extends AbstractConverter {

    private static final long serialVersionUID = 1L;

    /**
     * 单例对象
     */
    public static final PrimitiveConverter INSTANCE = new PrimitiveConverter();

    /**
     * 将指定值转换为原始类型的值
     *
     * @param value          值
     * @param primitiveClass 原始类型
     * @param func           当无法直接转换时，转为字符串后再转换的函数
     * @return 转换结果
     */
    protected static Object convert(final Object value, final Class<?> primitiveClass, final Function<Object, String> func) {
        if (byte.class == primitiveClass) {
            return ObjectKit.defaultIfNull(NumberConverter.convert(value, Byte.class, func), 0);
        } else if (short.class == primitiveClass) {
            return ObjectKit.defaultIfNull(NumberConverter.convert(value, Short.class, func), 0);
        } else if (int.class == primitiveClass) {
            return ObjectKit.defaultIfNull(NumberConverter.convert(value, Integer.class, func), 0);
        } else if (long.class == primitiveClass) {
            return ObjectKit.defaultIfNull(NumberConverter.convert(value, Long.class, func), 0);
        } else if (float.class == primitiveClass) {
            return ObjectKit.defaultIfNull(NumberConverter.convert(value, Float.class, func), 0);
        } else if (double.class == primitiveClass) {
            return ObjectKit.defaultIfNull(NumberConverter.convert(value, Double.class, func), 0);
        } else if (char.class == primitiveClass) {
            return Convert.convert(Character.class, value);
        } else if (boolean.class == primitiveClass) {
            return Convert.convert(Boolean.class, value);
        }

        throw new ConvertException("Unsupported target type: {}", primitiveClass);
    }

    @Override
    protected Object convertInternal(final Class<?> targetClass, final Object value) {
        return PrimitiveConverter.convert(value, targetClass, this::convertToString);
    }

    @Override
    protected String convertToString(final Object value) {
        return StringKit.trim(super.convertToString(value));
    }

}
