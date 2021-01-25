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
package org.aoju.bus.extra.captcha.provider;

import org.aoju.bus.core.toolkit.ImageKit;
import org.aoju.bus.core.toolkit.ObjectKit;
import org.aoju.bus.core.toolkit.RandomKit;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 普通干扰线验证码
 *
 * @author Kimi Liu
 * @version 6.1.8
 * @since JDK 1.8+
 */
public class LineProvider extends AbstractProvider {

    /**
     * 构造，默认5位验证码，150条干扰线
     *
     * @param width  图片宽
     * @param height 图片高
     */
    public LineProvider(int width, int height) {
        this(width, height, 5, 150);
    }

    /**
     * 构造
     *
     * @param width     图片宽
     * @param height    图片高
     * @param codeCount 字符个数
     * @param lineCount 干扰线条数
     */
    public LineProvider(int width, int height, int codeCount, int lineCount) {
        super(width, height, codeCount, lineCount);
    }


    @Override
    public Image createImage(String code) {
        // 图像buffer
        final BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        final Graphics2D g = org.aoju.bus.core.image.Graphics.createGraphics(image, ObjectKit.defaultIfNull(this.background, Color.WHITE));

        // 干扰线
        drawInterfere(g);

        // 字符串
        drawString(g, code);

        return image;
    }

    /**
     * 绘制字符串
     *
     * @param g    {@link Graphics}画笔
     * @param code 验证码
     */
    private void drawString(Graphics2D g, String code) {
        // 指定透明度
        if (null != this.textAlpha) {
            g.setComposite(this.textAlpha);
        }
        org.aoju.bus.core.image.Graphics.drawStringColourful(g, code, this.font, this.width, this.height);
    }

    /**
     * 绘制干扰线
     *
     * @param g {@link Graphics2D}画笔
     */
    private void drawInterfere(Graphics2D g) {
        final ThreadLocalRandom random = RandomKit.getRandom();
        // 干扰线
        for (int i = 0; i < this.interfereCount; i++) {
            int xs = random.nextInt(width);
            int ys = random.nextInt(height);
            int xe = xs + random.nextInt(width / 8);
            int ye = ys + random.nextInt(height / 8);
            g.setColor(ImageKit.randomColor(random));
            g.drawLine(xs, ys, xe, ye);
        }
    }

}