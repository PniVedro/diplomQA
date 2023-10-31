package ru.netology.data;

import lombok.*;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Timestamp;
import java.util.List;

public class SQLHelper {
    private static String url = System.getProperty("dbUrl");
    private static String user = System.getProperty("dbUser");
    private static String password = System.getProperty("dbPassword");


    private static final QueryRunner runner = new QueryRunner();
    private SQLHelper() {
    }

    @SneakyThrows
    public static Connection getConn() {
        return DriverManager.getConnection(url, user, password);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PaymentEntity {
        private String id;
        public  int amount;
        private Timestamp created;
        private String status;
        private String transaction_id;

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreditRequestEntity {
        private String id;
        private String bank_id;
        private Timestamp created;
        private String status;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderEntity {
        private String id;
        private Timestamp created;
        private String credit_id;
        private String payment_id;
    }

    @SneakyThrows
    public static List<PaymentEntity> getPayments() {
        getConn();
        var querySQL = "SELECT * FROM payment_entity;";
        ResultSetHandler<List<PaymentEntity>> result = new BeanListHandler<>(PaymentEntity.class);
        return runner.query(getConn(), querySQL, result);
    }

    @SneakyThrows
    public static List<CreditRequestEntity> getCreditRequests() {
        getConn();
        var querySQL = "SELECT * FROM credit_request_entity;";
        ResultSetHandler<List<CreditRequestEntity>> result = new BeanListHandler<>(CreditRequestEntity.class);
        return runner.query(getConn(), querySQL, result);
    }

    @SneakyThrows
    public static List<OrderEntity> getOrders() {
        getConn();
        var querySQL = "SELECT * FROM order_entity;";
        ResultSetHandler<List<OrderEntity>> result = new BeanListHandler<>(OrderEntity.class);
        return runner.query(getConn(), querySQL, result);
    }

    @SneakyThrows
    public static void cleanDB() {
        var conn = getConn();
        runner.execute(conn,"DELETE FROM credit_request_entity;");
        runner.execute(conn, "DELETE FROM payment_entity;");
        runner.execute(conn, "DELETE FROM order_entity;");
    }
}
