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
package org.aoju.bus.health.mac.software;

import org.aoju.bus.core.annotation.Immutable;
import org.aoju.bus.health.builtin.software.AbstractOSThread;
import org.aoju.bus.health.builtin.software.OSProcess;

/**
 * OSThread implementation
 *
 * @author Kimi Liu
 * @version 6.3.2
 * @since JDK 1.8+
 */
@Immutable
public class MacOSThread extends AbstractOSThread {

    private final int threadId;
    private final OSProcess.State state;
    private final long kernelTime;
    private final long userTime;
    private final long startTime;
    private final long upTime;
    private final int priority;

    public MacOSThread(int pid, int threadId, OSProcess.State state, long kernelTime, long userTime, long startTime, long upTime,
                       int priority) {
        super(pid);
        this.threadId = threadId;
        this.state = state;
        this.kernelTime = kernelTime;
        this.userTime = userTime;
        this.startTime = startTime;
        this.upTime = upTime;
        this.priority = priority;
    }

    @Override
    public int getThreadId() {
        return threadId;
    }

    @Override
    public OSProcess.State getState() {
        return state;
    }

    @Override
    public long getKernelTime() {
        return kernelTime;
    }

    @Override
    public long getUserTime() {
        return userTime;
    }

    @Override
    public long getStartTime() {
        return startTime;
    }

    @Override
    public long getUpTime() {
        return upTime;
    }

    @Override
    public int getPriority() {
        return priority;
    }

}
