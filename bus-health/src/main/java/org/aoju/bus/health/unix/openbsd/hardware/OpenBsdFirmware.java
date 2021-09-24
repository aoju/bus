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
package org.aoju.bus.health.unix.openbsd.hardware;

import org.aoju.bus.core.lang.Normal;
import org.aoju.bus.core.lang.tuple.Triple;
import org.aoju.bus.core.toolkit.StringKit;
import org.aoju.bus.health.Builder;
import org.aoju.bus.health.Executor;
import org.aoju.bus.health.Memoize;
import org.aoju.bus.health.builtin.hardware.AbstractFirmware;

import java.util.List;
import java.util.function.Supplier;

/**
 * OpenBSD Firmware implementation
 *
 * @author Kimi Liu
 * @version 6.2.9
 * @since JDK 1.8+
 */
public class OpenBsdFirmware extends AbstractFirmware {

    private final Supplier<Triple<String, String, String>> manufVersRelease = Memoize.memoize(OpenBsdFirmware::readDmesg);

    private static Triple<String, String, String> readDmesg() {
        String version = null;
        String vendor = null;
        String releaseDate = Normal.EMPTY;

        List<String> dmesg = Executor.runNative("dmesg");
        for (String line : dmesg) {
            // bios0 at mainbus0: SMBIOS rev. 2.7 @ 0xdcc0e000 (67 entries)
            // bios0: vendor LENOVO version "GLET90WW (2.44 )" date 09/13/2017
            // bios0: LENOVO 20AWA08J00
            if (line.startsWith("bios0: vendor")) {
                version = Builder.getStringBetween(line, '"');
                releaseDate = Builder.parseMmDdYyyyToYyyyMmDD(Builder.parseLastString(line));
                vendor = line.split("vendor")[1].trim();
            }
        }
        return Triple.of(StringKit.isBlank(vendor) ? Normal.UNKNOWN : vendor,
                StringKit.isBlank(version) ? Normal.UNKNOWN : version,
                StringKit.isBlank(releaseDate) ? Normal.UNKNOWN : releaseDate);
    }

    @Override
    public String getManufacturer() {
        return manufVersRelease.get().getLeft();
    }

    @Override
    public String getVersion() {
        return manufVersRelease.get().getMiddle();
    }

    @Override
    public String getReleaseDate() {
        return manufVersRelease.get().getRight();
    }

}
