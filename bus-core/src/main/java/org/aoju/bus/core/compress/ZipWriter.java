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
package org.aoju.bus.core.compress;

import org.aoju.bus.core.io.resource.Resource;
import org.aoju.bus.core.lang.Symbol;
import org.aoju.bus.core.lang.exception.InstrumentException;
import org.aoju.bus.core.toolkit.ArrayKit;
import org.aoju.bus.core.toolkit.FileKit;
import org.aoju.bus.core.toolkit.IoKit;
import org.aoju.bus.core.toolkit.StringKit;

import java.io.*;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Zip生成封装
 *
 * @author Kimi Liu
 * @version 6.3.1
 * @since JDK 1.8+
 */
public class ZipWriter implements Closeable {

    private final ZipOutputStream out;

    /**
     * 构造
     *
     * @param zipFile 生成的Zip文件
     * @param charset 编码
     */
    public ZipWriter(File zipFile, Charset charset) {
        this.out = getZipOutputStream(zipFile, charset);
    }

    /**
     * 构造
     *
     * @param out     {@link ZipOutputStream}
     * @param charset 编码
     */
    public ZipWriter(OutputStream out, Charset charset) {
        this.out = getZipOutputStream(out, charset);
    }

    /**
     * 构造
     *
     * @param out {@link ZipOutputStream}
     */
    public ZipWriter(ZipOutputStream out) {
        this.out = out;
    }

    /**
     * 创建{@link ZipWriter}
     *
     * @param zipFile 生成的Zip文件
     * @param charset 编码
     * @return {@link ZipWriter}
     */
    public static ZipWriter of(File zipFile, Charset charset) {
        return new ZipWriter(zipFile, charset);
    }

    /**
     * 创建{@link ZipWriter}
     *
     * @param out     Zip输出的流，一般为输出文件流
     * @param charset 编码
     * @return {@link ZipWriter}
     */
    public static ZipWriter of(OutputStream out, Charset charset) {
        return new ZipWriter(out, charset);
    }

    /**
     * 获得 {@link ZipOutputStream}
     *
     * @param zipFile 压缩文件
     * @param charset 编码
     * @return {@link ZipOutputStream}
     */
    private static ZipOutputStream getZipOutputStream(File zipFile, Charset charset) {
        return getZipOutputStream(FileKit.getOutputStream(zipFile), charset);
    }

    /**
     * 获得 {@link ZipOutputStream}
     *
     * @param out     压缩文件流
     * @param charset 编码
     * @return {@link ZipOutputStream}
     */
    private static ZipOutputStream getZipOutputStream(OutputStream out, Charset charset) {
        if (out instanceof ZipOutputStream) {
            return (ZipOutputStream) out;
        }
        return new ZipOutputStream(out, charset);
    }

    /**
     * 设置压缩级别，可选1~9，-1表示默认
     *
     * @param level 压缩级别
     * @return this
     */
    public ZipWriter setLevel(int level) {
        this.out.setLevel(level);
        return this;
    }

    /**
     * 设置注释
     *
     * @param comment 注释
     * @return this
     */
    public ZipWriter setComment(String comment) {
        this.out.setComment(comment);
        return this;
    }

    /**
     * 获取原始的{@link ZipOutputStream}
     *
     * @return {@link ZipOutputStream}
     */
    public ZipOutputStream getOut() {
        return this.out;
    }

    /**
     * 对文件或文件目录进行压缩
     *
     * @param withSrcDir 是否包含被打包目录，只针对压缩目录有效。若为false，则只压缩目录下的文件或目录，为true则将本目录也压缩
     * @param filter     文件过滤器，通过实现此接口，自定义要过滤的文件（过滤掉哪些文件或文件夹不加入压缩），{@code null}表示不过滤
     * @param files      要压缩的源文件或目录。如果压缩一个文件，则为该文件的全路径；如果压缩一个目录，则为该目录的顶层目录路径
     * @return this
     * @throws InstrumentException IO异常
     */
    public ZipWriter add(boolean withSrcDir, FileFilter filter, File... files) throws InstrumentException {
        for (File file : files) {
            // 如果只是压缩一个文件，则需要截取该文件的父目录
            String srcRootDir;
            try {
                srcRootDir = file.getCanonicalPath();
                if ((false == file.isDirectory()) || withSrcDir) {
                    // 若是文件，则将父目录完整路径都截取掉；若设置包含目录，则将上级目录全部截取掉，保留本目录名
                    srcRootDir = file.getCanonicalFile().getParentFile().getCanonicalPath();
                }
            } catch (IOException e) {
                throw new InstrumentException(e);
            }

            _add(file, srcRootDir, filter);
        }
        return this;
    }

    /**
     * 添加资源到压缩包，添加后关闭资源流
     *
     * @param resources 需要压缩的资源，资源的路径为{@link Resource#getName()}
     * @return this
     * @throws InstrumentException IO异常
     */
    public ZipWriter add(Resource... resources) throws InstrumentException {
        for (Resource resource : resources) {
            if (null != resource) {
                add(resource.getName(), resource.getStream());
            }
        }
        return this;
    }

    /**
     * 添加文件流到压缩包，添加后关闭输入文件流
     * 如果输入流为{@code null}，则只创建空目录
     *
     * @param path 压缩的路径, {@code null}和""表示根目录下
     * @param in   需要压缩的输入流，使用完后自动关闭，{@code null}表示加入空目录
     * @return this
     * @throws InstrumentException IO异常
     */
    public ZipWriter add(String path, InputStream in) throws InstrumentException {
        path = StringKit.nullToEmpty(path);
        if (null == in) {
            // 空目录需要检查路径规范性，目录以"/"结尾
            path = StringKit.addSuffixIfNot(path, Symbol.SLASH);
            if (StringKit.isBlank(path)) {
                return this;
            }
        }

        return putEntry(path, in);
    }

    @Override
    public void close() throws InstrumentException {
        try {
            out.finish();
        } catch (IOException e) {
            throw new InstrumentException(e);
        } finally {
            IoKit.close(this.out);
        }
    }

    /**
     * 递归压缩文件夹或压缩文件
     * srcRootDir决定了路径截取的位置，例如：
     * file的路径为d:/a/b/c/d.txt，srcRootDir为d:/a/b，则压缩后的文件与目录为结构为c/d.txt
     *
     * @param srcRootDir 被压缩的文件夹根目录
     * @param file       当前递归压缩的文件或目录对象
     * @param filter     文件过滤器，通过实现此接口，自定义要过滤的文件（过滤掉哪些文件或文件夹不加入压缩），{@code null}表示不过滤
     * @throws InstrumentException IO异常
     */
    private ZipWriter _add(File file, String srcRootDir, FileFilter filter) throws InstrumentException {
        if (null == file || (null != filter && false == filter.accept(file))) {
            return this;
        }

        // 获取文件相对于压缩文件夹根目录的子路径
        final String subPath = FileKit.subPath(srcRootDir, file);
        if (file.isDirectory()) {
            // 如果是目录，则压缩压缩目录中的文件或子目录
            final File[] files = file.listFiles();
            if (ArrayKit.isEmpty(files)) {
                // 加入目录，只有空目录时才加入目录，非空时会在创建文件时自动添加父级目录
                add(subPath, null);
            } else {
                // 压缩目录下的子文件或目录
                for (File childFile : files) {
                    _add(childFile, srcRootDir, filter);
                }
            }
        } else {
            // 如果是文件或其它符号，则直接压缩该文件
            putEntry(subPath, FileKit.getInputStream(file));
        }
        return this;
    }

    /**
     * 添加文件流到压缩包，添加后关闭输入文件流
     * 如果输入流为{@code null}，则只创建空目录
     *
     * @param path 压缩的路径, {@code null}和""表示根目录下
     * @param in   需要压缩的输入流，使用完后自动关闭，{@code null}表示加入空目录
     * @throws InstrumentException IO异常
     */
    private ZipWriter putEntry(String path, InputStream in) throws InstrumentException {
        try {
            out.putNextEntry(new ZipEntry(path));
            if (null != in) {
                IoKit.copy(in, out);
            }
            out.closeEntry();
        } catch (IOException e) {
            throw new InstrumentException(e);
        } finally {
            IoKit.close(in);
        }

        IoKit.flush(this.out);
        return this;
    }

}
