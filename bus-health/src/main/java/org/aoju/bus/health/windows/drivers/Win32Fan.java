/*********************************************************************************
 *                                                                               *
 * The MIT License (MIT)                                                         *
 *                                                                               *
 * Copyright (c) 2015-2021 aoju.org OSHI and other contributors.                 *
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
package org.aoju.bus.health.windows.drivers;

import com.sun.jna.platform.win32.COM.WbemcliUtil.WmiQuery;
import com.sun.jna.platform.win32.COM.WbemcliUtil.WmiResult;
import org.aoju.bus.core.annotation.ThreadSafe;
import org.aoju.bus.health.windows.WmiQueryHandler;

/**
 * Utility to query WMI class {@code Win32_Fan}
 *
 * @author Kimi Liu
 * @version 6.2.6
 * @since JDK 1.8+
 */
@ThreadSafe
public final class Win32Fan {

    private static final String WIN32_FAN = "Win32_Fan";

    private Win32Fan() {
    }

    /**
     * Queries the fan speed.
     *
     * @return Currently requested fan speed, defined in revolutions per minute,
     * when a variable speed fan is supported.
     */
    public static WmiResult<SpeedProperty> querySpeed() {
        WmiQuery<SpeedProperty> fanQuery = new WmiQuery<>(WIN32_FAN, SpeedProperty.class);
        return WmiQueryHandler.createInstance().queryWMI(fanQuery);
    }

    /**
     * Fan speed property.
     */
    public enum SpeedProperty {
        DESIREDSPEED
    }

}
