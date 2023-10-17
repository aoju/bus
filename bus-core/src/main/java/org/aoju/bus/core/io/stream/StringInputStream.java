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
package org.aoju.bus.core.io.stream;

import org.aoju.bus.core.toolkit.StringKit;

import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;

/**
 * 基于字符串的InputStream
 *
 * @author Kimi Liu
 * @since Java 17+
 */
public class StringInputStream extends ByteArrayInputStream {

    /**
     * 构造
     *
     * @param text    字符串
     * @param charset 编码
     */
    public StringInputStream(final CharSequence text, final Charset charset) {
        super(StringKit.bytes(text, charset));
    }

    /**
     * 创建StrInputStream
     *
     * @param text    字符串
     * @param charset 编码
     * @return StrInputStream
     */
    public static StringInputStream of(final CharSequence text, final Charset charset) {
        return new StringInputStream(text, charset);
    }

}
