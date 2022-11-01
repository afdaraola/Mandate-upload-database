package com.ecobank.utils;

import com.ecobank.mandate.repository.implementation.MandateRepositoryImplementation;
import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.Arrays;

public class RepositoryUtils {
    private static final Logger logger =  Logger.getLogger(RepositoryUtils.class);
    public static Connection getConnection() {
        Context initCtx;
        DataSource ds;
        Connection conn = null;
        try {
            initCtx = new InitialContext();
            String JndiConnection = InterbankBankingProperties.getMessage("ENG");
        //    String JndiConnection = "ENG";
            logger.info("JDBC: "+ "jdbc/" + JndiConnection + "FlexDataSourceFWS");
            ds = (DataSource)initCtx.lookup("jdbc/" + JndiConnection + "FlexDataSourceFWS");
            conn = ds.getConnection();
        } catch (SQLException ex) {
            logger.info("SQLException thrown in getDataBaseConnection. Reason:" + ex.getMessage());
            logger.info("SQLException " + Arrays.toString((Object[])ex.getStackTrace()).replaceAll(", ", "\n"));
        } catch (NamingException ex) {
            logger.info("Exception thrown in getDataBaseConnection. Reason:" + ex.getMessage());
            logger.info("Exception " + Arrays.toString((Object[])ex.getStackTrace()).replaceAll(", ", "\n"));
        } catch (Throwable ex) {
            logger.info("Exception thrown in getDataBaseConnection. Reason:" + ex.getMessage());
            logger.info("Exception " + Arrays.toString((Object[])ex.getStackTrace()).replaceAll(", ", "\n"));
        }
        return conn;
    }

//    public static Connection getDataBaseConnectionFlex() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, Exception {
//        Connection conn = null;   //10.8.184.141:1521/FCUBSNIGRPT
//        String host = "10.8.184.140";
//       // String host ="localhost";
//        String passwords = "MULEAPPUSER";
//        String Instance = "FCNIGUAT";
//        String usernames = "MULEAPPUSER";
//        Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
//        System.out.println("Connecting to the database..." + host + " : ---- : " + usernames);
//        String connect = ("jdbc:oracle:thin:@" + host + ":1521/" + Instance );
//        System.out.println("----Connect "+ connect);
//        conn = DriverManager.getConnection("jdbc:oracle:thin:@" + host + ":1521/" + Instance , usernames, passwords);
//        System.out.println("Connected to the database " + conn);
//        return conn;
//    }



    public static void closeFinally(Connection conn, CallableStatement callStmt, ResultSet resultSet) {

        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (Exception ex) {
            }
        }
        if (callStmt != null) {
            try {
                callStmt.close();
            } catch (Exception ex) {
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception ex) {
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception ex) {
                }
            }
        }
    }
}
