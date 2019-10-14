/*
 * The MIT License
 *
 * Copyright (c) 2017 aoju.org All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.aoju.bus.http.cache;

import org.aoju.bus.core.io.segment.Buffer;

import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * <ul>
 * <li><strong>Read/write:</strong> read and write using the same operator.
 * <li><strong>Random access:</strong> access any position within the file.
 * <li><strong>Shared channels:</strong> read and write a file channel that's shared between
 * multiple operators. Note that although the underlying {@code FileChannel} may be shared,
 * each {@code FileOperator} should not be.
 * </ul>
 *
 * @author Kimi Liu
 * @version 5.0.2
 * @since JDK 1.8+
 */
final class FileOperator {

    private final FileChannel fileChannel;

    FileOperator(FileChannel fileChannel) {
        this.fileChannel = fileChannel;
    }

    public void write(long pos, Buffer source, long byteCount) throws IOException {
        if (byteCount < 0 || byteCount > source.size()) throw new IndexOutOfBoundsException();

        while (byteCount > 0L) {
            long bytesWritten = fileChannel.transferFrom(source, pos, byteCount);
            pos += bytesWritten;
            byteCount -= bytesWritten;
        }
    }

    public void read(long pos, Buffer sink, long byteCount) throws IOException {
        if (byteCount < 0) throw new IndexOutOfBoundsException();

        while (byteCount > 0L) {
            long bytesRead = fileChannel.transferTo(pos, byteCount, sink);
            pos += bytesRead;
            byteCount -= bytesRead;
        }
    }

}