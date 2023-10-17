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
package org.aoju.bus.core.beans.copier.provider;

import org.aoju.bus.core.beans.DynamicBean;
import org.aoju.bus.core.beans.copier.ValueProvider;
import org.aoju.bus.core.convert.Convert;

import java.lang.reflect.Type;

/**
 * DynaBean值提供者
 *
 * @author Kimi Liu
 * @since Java 17+
 */
public class DynamicProvider implements ValueProvider<String> {

    private final DynamicBean dynaBean;
    private final boolean ignoreError;

    /**
     * 构造
     *
     * @param dynaBean    DynaBean
     * @param ignoreError 是否忽略错误
     */
    public DynamicProvider(DynamicBean dynaBean, boolean ignoreError) {
        this.dynaBean = dynaBean;
        this.ignoreError = ignoreError;
    }

    @Override
    public Object value(String key, Type valueType) {
        final Object value = dynaBean.get(key);
        return Convert.convertWithCheck(valueType, value, null, this.ignoreError);
    }

    @Override
    public boolean containsKey(String key) {
        return dynaBean.contains(key);
    }

}
