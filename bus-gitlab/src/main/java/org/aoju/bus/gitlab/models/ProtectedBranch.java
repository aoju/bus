/*********************************************************************************
 *                                                                               *
 * The MIT License (MIT)                                                         *
 *                                                                               *
 * Copyright (c) 2015-2021 aoju.org Greg Messner and other contributors.         *
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
package org.aoju.bus.gitlab.models;

import org.aoju.bus.gitlab.JacksonJson;

import java.util.List;

/**
 * @author Kimi Liu
 * @version 6.1.8
 * @since JDK 1.8+
 */
public class ProtectedBranch {

    private String name;
    private List<BranchAccessLevel> pushAccessLevels;
    private List<BranchAccessLevel> mergeAccessLevels;
    private List<BranchAccessLevel> unprotectAccessLevels;
    private Boolean codeOwnerApprovalRequired;

    public static final boolean isValid(ProtectedBranch branch) {
        return (branch != null && branch.getName() != null);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BranchAccessLevel> getPushAccessLevels() {
        return this.pushAccessLevels;
    }

    public void setPushAccessLevels(List<BranchAccessLevel> pushAccessLevels) {
        this.pushAccessLevels = pushAccessLevels;
    }

    public List<BranchAccessLevel> getMergeAccessLevels() {
        return this.mergeAccessLevels;
    }

    public void setMergeAccessLevels(List<BranchAccessLevel> mergeAccessLevels) {
        this.mergeAccessLevels = mergeAccessLevels;
    }

    public List<BranchAccessLevel> getUnprotectAccessLevels() {
        return unprotectAccessLevels;
    }

    public void setUnprotectAccessLevels(List<BranchAccessLevel> unprotectAccessLevels) {
        this.unprotectAccessLevels = unprotectAccessLevels;
    }

    public ProtectedBranch withName(String name) {
        this.name = name;
        return this;
    }

    public ProtectedBranch withPushAccessLevels(List<BranchAccessLevel> pushAccessLevels) {
        this.pushAccessLevels = pushAccessLevels;
        return this;
    }

    public ProtectedBranch withMergeAccessLevels(List<BranchAccessLevel> mergeAccessLevels) {
        this.mergeAccessLevels = mergeAccessLevels;
        return this;
    }

    public Boolean getCodeOwnerApprovalRequired() {
        return codeOwnerApprovalRequired;
    }

    public void setCodeOwnerApprovalRequired(Boolean codeOwnerApprovalRequired) {
        this.codeOwnerApprovalRequired = codeOwnerApprovalRequired;
    }

    public ProtectedBranch withCodeOwnerApprovalRequired(Boolean codeOwnerApprovalRequired) {
        this.codeOwnerApprovalRequired = codeOwnerApprovalRequired;
        return this;
    }

    @Override
    public String toString() {
        return (JacksonJson.toJsonString(this));
    }
}
