package com.coherentsolutions.advanced.java.section04.advanced;

import javax.transaction.xa.Xid;
import java.util.Arrays;

/**
 * XidImpl.java
 * Custom implementation of the Xid interface for XA Transactions.
 */
public class XidImpl implements Xid {

    private int formatId;
    private byte[] globalTransactionId;
    private byte[] branchQualifier;

    public XidImpl(int formatId, byte[] globalTransactionId, byte[] branchQualifier) {
        this.formatId = formatId;
        this.globalTransactionId = globalTransactionId;
        this.branchQualifier = branchQualifier;
    }

    @Override
    public int getFormatId() {
        return formatId;
    }

    @Override
    public byte[] getGlobalTransactionId() {
        return globalTransactionId;
    }

    @Override
    public byte[] getBranchQualifier() {
        return branchQualifier;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof XidImpl)) {
            return false;
        }
        XidImpl other = (XidImpl) obj;
        return formatId == other.formatId &&
                Arrays.equals(globalTransactionId, other.globalTransactionId) &&
                Arrays.equals(branchQualifier, other.branchQualifier);
    }

    @Override
    public int hashCode() {
        return formatId ^ Arrays.hashCode(globalTransactionId) ^ Arrays.hashCode(branchQualifier);
    }
}
