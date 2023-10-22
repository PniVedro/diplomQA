package ru.netology.data;

import com.google.gson.Gson;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.*;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Timestamp;
import java.util.List;

import static io.restassured.RestAssured.given;

public class SQLHelper {

    private static final QueryRunner runner = new QueryRunner();
    private static final Gson gson = new Gson();
    private static final RequestSpecification spec = new RequestSpecBuilder().setBaseUri("http://localhost").setPort(9999)
            .setAccept(ContentType.JSON).setContentType(ContentType.JSON).log(LogDetail.ALL).build();

    private SQLHelper() {
    }

    public static void getData(DataHelper.CardInfo cardInfo, String url, int statusCode) {
        var body = gson.toJson(cardInfo);
        given().spec(spec).body(body)
                .when().post(url)
                .then().statusCode(statusCode);
    }

    @SneakyThrows
    public static Connection getConn() {
        return DriverManager.getConnection(System.getProperty("dbUrl"), "app", "pass");
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PaymentEntity {
        private String id;
        private int amount;
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
    public static String getPaymentStatus() {
        getConn();
        var paymentStatus = "SELECT status FROM payment_entity;";
       return runner.query(getConn(), paymentStatus, new ScalarHandler<>());

    }

    @SneakyThrows
    public static String getCreditStatus() {
        getConn();
        var creditStatus = "SELECT status FROM credit_request_entity;";
        return runner.query(getConn(), creditStatus, new ScalarHandler<>());
    }

    @SneakyThrows
    public static void cleanDB() {
        var conn = getConn();
        runner.execute(conn,"DELETE FROM credit_request_entity;");
        runner.execute(conn, "DELETE FROM payment_entity;");
        runner.execute(conn, "DELETE FROM order_entity;");
    }
}
