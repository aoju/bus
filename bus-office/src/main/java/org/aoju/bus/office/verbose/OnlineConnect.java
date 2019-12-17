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
package org.aoju.bus.office.verbose;

import org.aoju.bus.http.HttpClient;
import org.aoju.bus.office.metric.RequestConfig;

/**
 * 保存与LibreOffice在线服务器通信的请求配置.
 *
 * @author Kimi Liu
 * @version 3.6.6
 * @since JDK 1.8+
 */
public class OnlineConnect implements OnlineContext {

    private final HttpClient httpClient;
    private final RequestConfig requestConfig;

    /**
     * 使用指定的客户端和URL构造新连接.
     *
     * @param httpClient    用于与LibreOffice在线服务器通信的HTTP客户机(已初始化).
     * @param requestConfig 转换的请求配置.
     */
    public OnlineConnect(final HttpClient httpClient, final RequestConfig requestConfig) {

        this.httpClient = httpClient;
        this.requestConfig = requestConfig;
    }

    @Override
    public HttpClient getHttpClient() {
        return httpClient;
    }

    @Override
    public RequestConfig getRequestConfig() {
        return requestConfig;
    }

}
