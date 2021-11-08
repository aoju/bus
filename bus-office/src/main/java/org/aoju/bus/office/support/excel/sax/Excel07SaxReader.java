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
package org.aoju.bus.office.support.excel.sax;

import org.aoju.bus.core.lang.exception.InstrumentException;
import org.aoju.bus.core.toolkit.IoKit;
import org.aoju.bus.core.toolkit.ObjectKit;
import org.aoju.bus.core.toolkit.StringKit;
import org.aoju.bus.office.support.excel.ExcelSaxKit;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/**
 * Sax方式读取Excel文件
 * Excel2007格式说明见：http://www.cnblogs.com/wangmingshun/p/6654143.html
 *
 * @author Kimi Liu
 * @version 6.3.1
 * @since JDK 1.8+
 */
public class Excel07SaxReader implements ExcelSaxReader<Excel07SaxReader> {

    private final SheetSaxHandler handler;

    /**
     * 构造
     *
     * @param rowHandler 行处理器
     */
    public Excel07SaxReader(RowHandler rowHandler) {
        this.handler = new SheetSaxHandler(rowHandler);
    }

    /**
     * 设置行处理器
     *
     * @param rowHandler 行处理器
     * @return this
     */
    public Excel07SaxReader setRowHandler(RowHandler rowHandler) {
        this.handler.setRowHandler(rowHandler);
        return this;
    }

    @Override
    public Excel07SaxReader read(File file, int rid) throws InstrumentException {
        return read(file, RID_PREFIX + rid);
    }

    @Override
    public Excel07SaxReader read(File file, String idOrRidOrSheetName) throws InstrumentException {
        try {
            return read(OPCPackage.open(file), idOrRidOrSheetName);
        } catch (InvalidFormatException e) {
            throw new InstrumentException(e);
        }
    }

    @Override
    public Excel07SaxReader read(InputStream in, int rid) throws InstrumentException {
        return read(in, RID_PREFIX + rid);
    }

    @Override
    public Excel07SaxReader read(InputStream in, String idOrRidOrSheetName) throws InstrumentException {
        try (final OPCPackage opcPackage = OPCPackage.open(in)) {
            return read(opcPackage, idOrRidOrSheetName);
        } catch (IOException e) {
            throw new InstrumentException(e);
        } catch (InvalidFormatException e) {
            throw new InstrumentException(e);
        }
    }

    /**
     * 开始读取Excel，Sheet编号从0开始计数
     *
     * @param opcPackage {@link OPCPackage}，Excel包，读取后不关闭
     * @param rid        Excel中的sheet rid编号，如果为-1处理所有编号的sheet
     * @return this
     * @throws InstrumentException POI异常
     */
    public Excel07SaxReader read(OPCPackage opcPackage, int rid) throws InstrumentException {
        return read(opcPackage, RID_PREFIX + rid);
    }

    /**
     * 开始读取Excel，Sheet编号从0开始计数
     *
     * @param opcPackage         {@link OPCPackage}，Excel包，读取后不关闭
     * @param idOrRidOrSheetName Excel中的sheet id或者rid编号或sheet名，rid必须加rId前缀，例如rId1，如果为-1处理所有编号的sheet
     * @return this
     * @throws InstrumentException POI异常
     */
    public Excel07SaxReader read(OPCPackage opcPackage, String idOrRidOrSheetName) throws InstrumentException {
        try {
            return read(new XSSFReader(opcPackage), idOrRidOrSheetName);
        } catch (OpenXML4JException e) {
            throw new InstrumentException(e);
        } catch (IOException e) {
            throw new InstrumentException(e);
        }
    }

    /**
     * 开始读取Excel，Sheet编号从0开始计数
     *
     * @param xssfReader         {@link XSSFReader}，Excel读取器
     * @param idOrRidOrSheetName Excel中的sheet id或者rid编号或sheet名，rid必须加rId前缀，例如rId1，如果为-1处理所有编号的sheet
     * @return this
     * @throws InstrumentException POI异常
     */
    public Excel07SaxReader read(XSSFReader xssfReader, String idOrRidOrSheetName) throws InstrumentException {
        // 获取共享样式表，样式非必须
        try {
            this.handler.stylesTable = xssfReader.getStylesTable();
        } catch (IOException | InvalidFormatException ignore) {
            // ignore
        }

        // 获取共享字符串表
        try {
            this.handler.sharedStringsTable = xssfReader.getSharedStringsTable();
        } catch (IOException e) {
            throw new InstrumentException(e);
        } catch (InvalidFormatException e) {
            throw new InstrumentException(e);
        }

        return readSheets(xssfReader, idOrRidOrSheetName);
    }

    /**
     * 开始读取Excel，Sheet编号从0开始计数
     *
     * @param xssfReader         {@link XSSFReader}，Excel读取器
     * @param idOrRidOrSheetName Excel中的sheet id或者rid编号或sheet名，从0开始，rid必须加rId前缀，例如rId0，如果为-1处理所有编号的sheet
     * @return this
     * @throws InstrumentException POI异常
     */
    private Excel07SaxReader readSheets(XSSFReader xssfReader, String idOrRidOrSheetName) throws InstrumentException {
        this.handler.sheetIndex = getSheetIndex(xssfReader, idOrRidOrSheetName);
        InputStream sheetInputStream = null;
        try {
            if (this.handler.sheetIndex > -1) {
                // 根据 rId# 或 rSheet# 查找sheet
                sheetInputStream = xssfReader.getSheet(RID_PREFIX + (this.handler.sheetIndex + 1));
                ExcelSaxKit.readFrom(sheetInputStream, this.handler);
                this.handler.rowHandler.doAfterAllAnalysed();
            } else {
                this.handler.sheetIndex = -1;
                // 遍历所有sheet
                final Iterator<InputStream> sheetInputStreams = xssfReader.getSheetsData();
                while (sheetInputStreams.hasNext()) {
                    // 重新读取一个sheet时行归零
                    this.handler.index = 0;
                    this.handler.sheetIndex++;
                    sheetInputStream = sheetInputStreams.next();
                    ExcelSaxKit.readFrom(sheetInputStream, this.handler);
                    this.handler.rowHandler.doAfterAllAnalysed();
                }
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new InstrumentException(e);
        } finally {
            IoKit.close(sheetInputStream);
        }
        return this;
    }

    /**
     * 获取sheet索引，从0开始
     * <ul>
     *     <li>传入'rId'开头，直接去除rId前缀</li>
     *     <li>传入纯数字，表示sheetIndex，通过{@link SheetRidReader}转换为rId</li>
     *     <li>传入其它字符串，表示sheetName，通过{@link SheetRidReader}转换为rId</li>
     * </ul>
     *
     * @param xssfReader         {@link XSSFReader}，Excel读取器
     * @param idOrRidOrSheetName Excel中的sheet id或者rid编号或sheet名称，从0开始，rid必须加rId前缀，例如rId0，如果为-1处理所有编号的sheet
     * @return sheet索引，从0开始
     */
    private int getSheetIndex(XSSFReader xssfReader, String idOrRidOrSheetName) {
        // rid直接处理
        if (StringKit.startWithIgnoreCase(idOrRidOrSheetName, RID_PREFIX)) {
            return Integer.parseInt(StringKit.removePrefixIgnoreCase(idOrRidOrSheetName, RID_PREFIX));
        }

        // sheetIndex需转换为rid
        final SheetRidReader ridReader = new SheetRidReader().read(xssfReader);

        if (StringKit.startWithIgnoreCase(idOrRidOrSheetName, SHEET_NAME_PREFIX)) {
            // name:开头的被认为是sheet名称直接处理
            idOrRidOrSheetName = StringKit.removePrefixIgnoreCase(idOrRidOrSheetName, SHEET_NAME_PREFIX);
            final Integer rid = ridReader.getRidByNameBase0(idOrRidOrSheetName);
            if (null != rid) {
                return rid;
            }
        } else {
            // 尝试查找名称
            Integer rid = ridReader.getRidByNameBase0(idOrRidOrSheetName);
            if (null != rid) {
                return rid;
            }

            try {
                final int sheetIndex = Integer.parseInt(idOrRidOrSheetName);
                rid = ridReader.getRidBySheetIdBase0(sheetIndex);
                // 如果查找不到对应index，则认为用户传入的直接是rid
                return ObjectKit.defaultIfNull(rid, sheetIndex);
            } catch (NumberFormatException ignore) {
                // 非数字，说明非index，且没有对应名称，抛出异常
            }
        }

        throw new IllegalArgumentException("Invalid rId or id or sheetName: " + idOrRidOrSheetName);
    }

}
