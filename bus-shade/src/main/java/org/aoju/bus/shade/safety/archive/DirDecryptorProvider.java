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
package org.aoju.bus.shade.safety.archive;

import org.aoju.bus.shade.safety.Complex;
import org.aoju.bus.shade.safety.algorithm.Key;
import org.aoju.bus.shade.safety.provider.DecryptorProvider;
import org.aoju.bus.shade.safety.provider.EntryDecryptorProvider;

import java.io.File;
import java.io.IOException;

/**
 * 文件夹解密器
 *
 * @author Kimi Liu
 * @version 6.2.0
 * @since JDK 1.8+
 */
public class DirDecryptorProvider extends EntryDecryptorProvider<File> implements DecryptorProvider {

    public DirDecryptorProvider(DecryptorProvider xEncryptor) {
        this(xEncryptor, null);
    }

    public DirDecryptorProvider(DecryptorProvider decryptorProvider, Complex<File> filter) {
        super(decryptorProvider, filter);
    }

    @Override
    public void decrypt(Key key, File src, File dest) throws IOException {
        if (src.isFile()) {
            DecryptorProvider decryptor = on(src) ? decryptorProvider : xNopDecryptor;
            decryptor.decrypt(key, src, dest);
        } else if (src.isDirectory()) {
            File[] files = src.listFiles();
            for (int i = 0; files != null && i < files.length; i++) {
                decrypt(key, files[i], new File(dest, files[i].getName()));
            }
        }
    }
}