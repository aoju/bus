/*********************************************************************************
 *                                                                               *
 * The MIT License (MIT)                                                         *
 *                                                                               *
 * Copyright (c) 2015-2021 aoju.org and other contributors.                      *
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
package org.aoju.bus.core.image.painter;

import org.aoju.bus.core.image.element.AbstractElement;
import org.aoju.bus.core.image.element.ImageElement;
import org.aoju.bus.core.image.element.TextElement;

/**
 * @author Kimi Liu
 * @version 6.2.1
 * @since JDK 1.8+
 */
public class PainterFactory {

    private static ImagePainter imagePainter;
    private static TextPainter textPainter;

    public static Painter newInstance(AbstractElement element) throws Exception {
        // 考虑到性能，这里用单件，先不lock了
        if (element instanceof ImageElement) {
            if (imagePainter == null) {
                imagePainter = new ImagePainter();
            }
            return imagePainter;
        } else if (element instanceof TextElement) {
            if (textPainter == null) {
                textPainter = new TextPainter();
            }
            return textPainter;
        } else {
            throw new Exception("不支持的Painter类型");
        }
    }

}
