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
package org.aoju.bus.core.toolkit;

import org.aoju.bus.core.exception.InternalException;
import org.aoju.bus.core.io.watcher.WatchMonitor;
import org.aoju.bus.core.io.watcher.Watcher;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.*;

/**
 * 监听工具类
 * 主要负责文件监听器的快捷创建
 *
 * @author Kimi Liu
 * @since Java 17+
 */
public class WatchKit {

    /**
     * 创建并初始化监听
     *
     * @param url    URL
     * @param events 监听的事件列表
     * @return 监听对象
     */
    public static WatchMonitor of(final URL url, final WatchEvent.Kind<?>... events) {
        return of(url, 0, events);
    }

    /**
     * 创建并初始化监听
     *
     * @param url      URL
     * @param events   监听的事件列表
     * @param maxDepth 当监听目录时，监听目录的最大深度，当设置值为1（或小于1）时，表示不递归监听子目录
     * @return 监听对象
     */
    public static WatchMonitor of(final URL url, final int maxDepth, final WatchEvent.Kind<?>... events) {
        return of(UriKit.toURI(url), maxDepth, events);
    }

    /**
     * 创建并初始化监听
     *
     * @param uri    URI
     * @param events 监听的事件列表
     * @return 监听对象
     */
    public static WatchMonitor of(final URI uri, final WatchEvent.Kind<?>... events) {
        return of(uri, 0, events);
    }

    /**
     * 创建并初始化监听
     *
     * @param uri      URI
     * @param events   监听的事件列表
     * @param maxDepth 当监听目录时，监听目录的最大深度，当设置值为1（或小于1）时，表示不递归监听子目录
     * @return 监听对象
     */
    public static WatchMonitor of(final URI uri, final int maxDepth, final WatchEvent.Kind<?>... events) {
        return of(Paths.get(uri), maxDepth, events);
    }

    /**
     * 创建并初始化监听
     *
     * @param file   文件
     * @param events 监听的事件列表
     * @return 监听对象
     */
    public static WatchMonitor of(final File file, final WatchEvent.Kind<?>... events) {
        return of(file, 0, events);
    }

    /**
     * 创建并初始化监听
     *
     * @param file     文件
     * @param events   监听的事件列表
     * @param maxDepth 当监听目录时，监听目录的最大深度，当设置值为1（或小于1）时，表示不递归监听子目录
     * @return 监听对象
     */
    public static WatchMonitor of(final File file, final int maxDepth, final WatchEvent.Kind<?>... events) {
        return of(file.toPath(), maxDepth, events);
    }

    /**
     * 创建并初始化监听
     *
     * @param path   路径
     * @param events 监听的事件列表
     * @return 监听对象
     */
    public static WatchMonitor of(final String path, final WatchEvent.Kind<?>... events) {
        return of(path, 0, events);
    }

    /**
     * 创建并初始化监听
     *
     * @param path     路径
     * @param events   监听的事件列表
     * @param maxDepth 当监听目录时，监听目录的最大深度，当设置值为1（或小于1）时，表示不递归监听子目录
     * @return 监听对象
     */
    public static WatchMonitor of(final String path, final int maxDepth, final WatchEvent.Kind<?>... events) {
        return of(Paths.get(path), maxDepth, events);
    }

    /**
     * 创建并初始化监听
     *
     * @param path   路径
     * @param events 监听事件列表
     * @return 监听对象
     */
    public static WatchMonitor of(final Path path, final WatchEvent.Kind<?>... events) {
        return of(path, 0, events);
    }

    /**
     * 创建并初始化监听
     *
     * @param path     路径
     * @param events   监听事件列表
     * @param maxDepth 当监听目录时，监听目录的最大深度，当设置值为1（或小于1）时，表示不递归监听子目录
     * @return 监听对象
     */
    public static WatchMonitor of(final Path path, final int maxDepth, final WatchEvent.Kind<?>... events) {
        return new WatchMonitor(path, maxDepth, events);
    }

    /**
     * 创建并初始化监听，监听所有事件
     *
     * @param url     URL
     * @param watcher {@link Watcher}
     * @return {@link WatchMonitor}
     */
    public static WatchMonitor ofAll(final URL url, final Watcher watcher) {
        return ofAll(url, 0, watcher);
    }

    /**
     * 创建并初始化监听，监听所有事件
     *
     * @param url      URL
     * @param maxDepth 当监听目录时，监听目录的最大深度，当设置值为1（或小于1）时，表示不递归监听子目录
     * @param watcher  {@link Watcher}
     * @return {@link WatchMonitor}
     */
    public static WatchMonitor ofAll(final URL url, final int maxDepth, final Watcher watcher) {
        return ofAll(UriKit.toURI(url), maxDepth, watcher);
    }

    /**
     * 创建并初始化监听，监听所有事件
     *
     * @param uri     URI
     * @param watcher {@link Watcher}
     * @return {@link WatchMonitor}
     */
    public static WatchMonitor ofAll(final URI uri, final Watcher watcher) {
        return ofAll(uri, 0, watcher);
    }

    /**
     * 创建并初始化监听，监听所有事件
     *
     * @param uri      URI
     * @param maxDepth 当监听目录时，监听目录的最大深度，当设置值为1（或小于1）时，表示不递归监听子目录
     * @param watcher  {@link Watcher}
     * @return {@link WatchMonitor}
     */
    public static WatchMonitor ofAll(final URI uri, final int maxDepth, final Watcher watcher) {
        return ofAll(Paths.get(uri), maxDepth, watcher);
    }

    /**
     * 创建并初始化监听，监听所有事件
     *
     * @param file    被监听文件
     * @param watcher {@link Watcher}
     * @return {@link WatchMonitor}
     */
    public static WatchMonitor ofAll(final File file, final Watcher watcher) {
        return ofAll(file, 0, watcher);
    }

    /**
     * 创建并初始化监听，监听所有事件
     *
     * @param file     被监听文件
     * @param maxDepth 当监听目录时，监听目录的最大深度，当设置值为1（或小于1）时，表示不递归监听子目录
     * @param watcher  {@link Watcher}
     * @return {@link WatchMonitor}
     */
    public static WatchMonitor ofAll(final File file, final int maxDepth, final Watcher watcher) {
        return ofAll(file.toPath(), maxDepth, watcher);
    }

    /**
     * 创建并初始化监听，监听所有事件
     *
     * @param path    路径
     * @param watcher {@link Watcher}
     * @return {@link WatchMonitor}
     */
    public static WatchMonitor ofAll(final String path, final Watcher watcher) {
        return ofAll(path, 0, watcher);
    }

    /**
     * 创建并初始化监听，监听所有事件
     *
     * @param path     路径
     * @param maxDepth 当监听目录时，监听目录的最大深度，当设置值为1（或小于1）时，表示不递归监听子目录
     * @param watcher  {@link Watcher}
     * @return {@link WatchMonitor}
     */
    public static WatchMonitor ofAll(final String path, final int maxDepth, final Watcher watcher) {
        return ofAll(Paths.get(path), maxDepth, watcher);
    }

    /**
     * 创建并初始化监听，监听所有事件
     *
     * @param path    路径
     * @param watcher {@link Watcher}
     * @return {@link WatchMonitor}
     */
    public static WatchMonitor ofAll(final Path path, final Watcher watcher) {
        return ofAll(path, 0, watcher);
    }

    /**
     * 创建并初始化监听，监听所有事件
     *
     * @param path     路径
     * @param maxDepth 当监听目录时，监听目录的最大深度，当设置值为1（或小于1）时，表示不递归监听子目录
     * @param watcher  {@link Watcher}
     * @return {@link WatchMonitor}
     */
    public static WatchMonitor ofAll(final Path path, final int maxDepth, final Watcher watcher) {
        final WatchMonitor watchMonitor = of(path, maxDepth, WatchMonitor.EVENTS_ALL);
        watchMonitor.setWatcher(watcher);
        return watchMonitor;
    }

    /**
     * 创建并初始化监听，监听修改事件
     *
     * @param url     URL
     * @param watcher {@link Watcher}
     * @return {@link WatchMonitor}
     */
    public static WatchMonitor createModify(final URL url, final Watcher watcher) {
        return createModify(url, 0, watcher);
    }

    /**
     * 创建并初始化监听，监听修改事件
     *
     * @param url      URL
     * @param maxDepth 当监听目录时，监听目录的最大深度，当设置值为1（或小于1）时，表示不递归监听子目录
     * @param watcher  {@link Watcher}
     * @return {@link WatchMonitor}
     */
    public static WatchMonitor createModify(final URL url, final int maxDepth, final Watcher watcher) {
        return createModify(UriKit.toURI(url), maxDepth, watcher);
    }

    /**
     * 创建并初始化监听，监听修改事件
     *
     * @param uri     URI
     * @param watcher {@link Watcher}
     * @return {@link WatchMonitor}
     */
    public static WatchMonitor createModify(final URI uri, final Watcher watcher) {
        return createModify(uri, 0, watcher);
    }

    /**
     * 创建并初始化监听，监听修改事件
     *
     * @param uri      URI
     * @param maxDepth 当监听目录时，监听目录的最大深度，当设置值为1（或小于1）时，表示不递归监听子目录
     * @param watcher  {@link Watcher}
     * @return {@link WatchMonitor}
     */
    public static WatchMonitor createModify(final URI uri, final int maxDepth, final Watcher watcher) {
        return createModify(Paths.get(uri), maxDepth, watcher);
    }

    /**
     * 创建并初始化监听，监听修改事件
     *
     * @param file    被监听文件
     * @param watcher {@link Watcher}
     * @return {@link WatchMonitor}
     */
    public static WatchMonitor createModify(final File file, final Watcher watcher) {
        return createModify(file, 0, watcher);
    }

    /**
     * 创建并初始化监听，监听修改事件
     *
     * @param file     被监听文件
     * @param maxDepth 当监听目录时，监听目录的最大深度，当设置值为1（或小于1）时，表示不递归监听子目录
     * @param watcher  {@link Watcher}
     * @return {@link WatchMonitor}
     */
    public static WatchMonitor createModify(final File file, final int maxDepth, final Watcher watcher) {
        return createModify(file.toPath(), maxDepth, watcher);
    }

    /**
     * 创建并初始化监听，监听修改事件
     *
     * @param path    路径
     * @param watcher {@link Watcher}
     * @return {@link WatchMonitor}
     */
    public static WatchMonitor createModify(final String path, final Watcher watcher) {
        return createModify(path, 0, watcher);
    }

    /**
     * 创建并初始化监听，监听修改事件
     *
     * @param path     路径
     * @param maxDepth 当监听目录时，监听目录的最大深度，当设置值为1（或小于1）时，表示不递归监听子目录
     * @param watcher  {@link Watcher}
     * @return {@link WatchMonitor}
     */
    public static WatchMonitor createModify(final String path, final int maxDepth, final Watcher watcher) {
        return createModify(Paths.get(path), maxDepth, watcher);
    }

    /**
     * 创建并初始化监听，监听修改事件
     *
     * @param path    路径
     * @param watcher {@link Watcher}
     * @return {@link WatchMonitor}
     */
    public static WatchMonitor createModify(final Path path, final Watcher watcher) {
        return createModify(path, 0, watcher);
    }

    /**
     * 创建并初始化监听，监听修改事件
     *
     * @param path     路径
     * @param maxDepth 当监听目录时，监听目录的最大深度，当设置值为1（或小于1）时，表示不递归监听子目录
     * @param watcher  {@link Watcher}
     * @return {@link WatchMonitor}
     */
    public static WatchMonitor createModify(final Path path, final int maxDepth, final Watcher watcher) {
        final WatchMonitor watchMonitor = of(path, maxDepth, WatchMonitor.ENTRY_MODIFY);
        watchMonitor.setWatcher(watcher);
        return watchMonitor;
    }

    /**
     * 注册Watchable对象到WatchService服务
     *
     * @param watchable 可注册对象
     * @param watcher   WatchService对象
     * @param events    监听事件
     * @return {@link WatchKey}
     */
    public static WatchKey register(final Watchable watchable, final WatchService watcher, final WatchEvent.Kind<?>... events) {
        try {
            return watchable.register(watcher, events);
        } catch (final IOException e) {
            throw new InternalException(e);
        }
    }

}
