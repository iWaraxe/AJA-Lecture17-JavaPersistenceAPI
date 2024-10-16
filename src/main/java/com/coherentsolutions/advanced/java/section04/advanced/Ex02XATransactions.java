package com.coherentsolutions.advanced.java.section04.advanced;

import com.mysql.cj.jdbc.MysqlXADataSource;

import javax.transaction.xa.XAResource;
import javax.transaction.xa.XAException;
import javax.transaction.xa.Xid;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Ex02XATransactions.java
 * Demonstrates implementing distributed transactions (XA Transactions) across multiple databases.
 */
public class Ex02XATransactions {

    public static void main(String[] args) throws Exception {
        // Configure XADataSource for two different databases
        MysqlXADataSource dataSource1 = new MysqlXADataSource();
        dataSource1.setUrl("jdbc:mysql://localhost:3306/db1");
        dataSource1.setUser("bestuser");
        dataSource1.setPassword("bestuser");

        MysqlXADataSource dataSource2 = new MysqlXADataSource();
        dataSource2.setUrl("jdbc:mysql://localhost:3306/db2");
        dataSource2.setUser("bestuser");
        dataSource2.setPassword("bestuser");

        // XA transaction identifiers
        Xid xid1 = XidUtil.createXid(1);
        Xid xid2 = XidUtil.createXid(2);

        XAResource xaResource1 = dataSource1.getXAConnection().getXAResource();
        XAResource xaResource2 = dataSource2.getXAConnection().getXAResource();

        // Begin distributed transaction on both resources
        try (Connection conn1 = dataSource1.getXAConnection().getConnection();
             Connection conn2 = dataSource2.getXAConnection().getConnection()) {

            xaResource1.start(xid1, XAResource.TMNOFLAGS);
            xaResource2.start(xid2, XAResource.TMNOFLAGS);

            try (PreparedStatement pstmt1 = conn1.prepareStatement("UPDATE accounts SET balance = balance - 100 WHERE user_id = 1");
                 PreparedStatement pstmt2 = conn2.prepareStatement("UPDATE accounts SET balance = balance + 100 WHERE user_id = 2")) {

                // Execute updates
                pstmt1.executeUpdate();
                pstmt2.executeUpdate();

                // End distributed transaction and prepare for commit
                xaResource1.end(xid1, XAResource.TMSUCCESS);
                xaResource2.end(xid2, XAResource.TMSUCCESS);

                int prepare1 = xaResource1.prepare(xid1);
                int prepare2 = xaResource2.prepare(xid2);

                // Commit the transaction if both resources are ready
                if (prepare1 == XAResource.XA_OK && prepare2 == XAResource.XA_OK) {
                    xaResource1.commit(xid1, false);
                    xaResource2.commit(xid2, false);
                    System.out.println("Distributed transaction committed successfully.");
                }

            } catch (Exception e) {
                // Rollback both transactions in case of failure
                xaResource1.rollback(xid1);
                xaResource2.rollback(xid2);
                System.out.println("Distributed transaction rolled back due to an error.");
                e.printStackTrace();
            }

        } catch (XAException e) {
            e.printStackTrace();
        }
    }
}
