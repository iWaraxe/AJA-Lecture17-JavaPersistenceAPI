package com.coherentsolutions.advanced.java.section04.advanced;

import javax.transaction.xa.Xid;
import java.util.UUID;

/**
 * Utility class to generate unique Xid for each transaction.
 */
public class XidUtil {

    public static Xid createXid(int formatId) {
        // Use a UUID to ensure that each transaction has a unique globalTransactionId and branchQualifier
        byte[] globalTransactionId = UUID.randomUUID().toString().getBytes();
        byte[] branchQualifier = UUID.randomUUID().toString().getBytes();
        return new XidImpl(formatId, globalTransactionId, branchQualifier);
    }
}
