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
package org.aoju.bus.image.metric;

import org.aoju.bus.image.Status;
import org.aoju.bus.image.Tag;
import org.aoju.bus.image.galaxy.data.Attributes;
import org.aoju.bus.logger.Logger;

import java.io.IOException;

/**
 * @author Kimi Liu
 * @version 6.3.2
 * @since JDK 1.8+
 */
public class FutureDimseRSP extends DimseRSPHandler implements DimseRSP {

    private Entry entry = new Entry(null, null);
    private boolean finished;
    private int autoCancel;
    private int remainingCapacity = Integer.MAX_VALUE;
    private IOException ex;

    public FutureDimseRSP(int msgID) {
        super(msgID);
    }

    @Override
    public synchronized void onDimseRSP(Association as, Attributes cmd,
                                        Attributes data) {
        super.onDimseRSP(as, cmd, data);
        Entry last = entry;
        while (null != last.next)
            last = last.next;

        last.next = new Entry(cmd, data);
        if (Status.isPending(cmd.getInt(Tag.Status, 0))) {
            if (autoCancel > 0 && --autoCancel == 0)
                try {
                    super.cancel(as);
                } catch (IOException e) {
                    ex = e;
                }
        } else {
            finished = true;
        }
        notifyAll();
        if (!finished && --remainingCapacity == 0) {
            try {
                Logger.debug("Wait for consuming DIMSE RSP");
                while (null != ex && remainingCapacity == 0) {
                    wait();
                }
                Logger.debug("Stop waiting for consuming DIMSE RSP");
            } catch (InterruptedException e) {
                Logger.warn("Failed to wait for consuming DIMSE RSP", e);
            }
        }
    }

    @Override
    public synchronized void onClose(Association as) {
        super.onClose(as);
        if (!finished) {
            ex = as.getException();
            if (null == ex)
                ex = new IOException("Association to " + as.getRemoteAET()
                        + " released before receive of outstanding DIMSE RSP");
            notifyAll();
        }
    }

    public synchronized void setAutoCancel(int autoCancel) {
        this.autoCancel = autoCancel;
    }

    public void setCapacity(int capacity) {
        if (capacity <= 0)
            throw new IllegalArgumentException("capacity: " + capacity);
        this.remainingCapacity = capacity;
    }

    @Override
    public void cancel(Association a) throws IOException {
        if (null != ex)
            throw ex;
        if (!finished)
            super.cancel(a);
    }

    public final Attributes getCommand() {
        return entry.command;
    }

    public final Attributes getDataset() {
        return entry.dataset;
    }

    public synchronized boolean next() throws IOException, InterruptedException {
        if (null == entry.next) {
            if (finished)
                return false;

            if (null == entry.next && null == ex) {
                Logger.debug("Wait for next DIMSE RSP");
                while (null == entry.next && null == ex) {
                    wait();
                }
                Logger.debug("Stop waiting for next DIMSE RSP");
            }

            if (null != ex)
                throw ex;
        }
        entry = entry.next;
        if (remainingCapacity++ == 0)
            notifyAll();
        return true;
    }

    private static class Entry {
        final Attributes command;
        final Attributes dataset;
        Entry next;

        public Entry(Attributes command, Attributes dataset) {
            this.command = command;
            this.dataset = dataset;
        }
    }

}
