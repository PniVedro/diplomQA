package ru.netology.tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.SQLHelper;
import ru.netology.data.DataHelper;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.SQLHelper.cleanDB;

public class APITests {

    private static List<SQLHelper.PaymentEntity> payments;
    private static List<SQLHelper.CreditRequestEntity> credits;
    private static List<SQLHelper.OrderEntity> orders;
    private static final String paymentUrl = "/payment";
    private static final String creditUrl = "/credit";

    @BeforeAll
    public static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeAll
    public static void setUp() {
        cleanDB();
    }

    @AfterEach
    public void tearDown() {
        cleanDB();
    }

    @AfterAll
    public static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }


    @DisplayName(value = "Покупка тура с действующей карты, создание записи в таблице payment_entity")
    @Test
    public void shouldValidCardApprovedEntityAdded() {
        var cardInfo = DataHelper.getValidCardApproved();
        SQLHelper.getData(cardInfo, paymentUrl, 200);
        payments = SQLHelper.getPayments();

        assertEquals(1, payments.size());
        assertEquals("APPROVED", SQLHelper.getPaymentStatus());
    }


    @DisplayName(value = "Покупка тура с недействующей карты, создание записи в таблице payment_entity")
    @Test
    public void shouldValidCardDeclinedEntityAdded() {
        var cardInfo = DataHelper.getValidCardDeclined();
        SQLHelper.getData(cardInfo, paymentUrl, 200);
        payments = SQLHelper.getPayments();

        assertEquals(1, payments.size());
        assertEquals("DECLINED", SQLHelper.getPaymentStatus());
    }

    
    @DisplayName(value = "Отправка пустого POST запроса платежа")
    @Test
    public void shouldPOSTBodyEmpty() {
        var cardInfo = DataHelper.getAllEmpty();
        SQLHelper.getData(cardInfo, paymentUrl, 400);
        payments = SQLHelper.getPayments();
        credits = SQLHelper.getCreditRequests();
        orders = SQLHelper.getOrders();

        assertEquals(0, payments.size());
        assertEquals(0, credits.size());
        assertEquals(0, orders.size());
    }


    @DisplayName(value = "Отправка POST запроса платежа с пустым значением number")
    @Test
    public void shouldPOSTNumberEmpty() {
        var cardInfo = DataHelper.getCardEmpty();
        SQLHelper.getData(cardInfo, paymentUrl, 400);
        payments = SQLHelper.getPayments();
        credits = SQLHelper.getCreditRequests();
        orders = SQLHelper.getOrders();

        assertEquals(0, payments.size());
        assertEquals(0, credits.size());
        assertEquals(0, orders.size());
    }


    @DisplayName(value = "Отправка POST запроса платежа с пустым значением month")
    @Test
    public void shouldPOSTMonthEmpty() {
        var cardInfo = DataHelper.getMonthEmpty();
        SQLHelper.getData(cardInfo, paymentUrl, 400);
        payments = SQLHelper.getPayments();
        credits = SQLHelper.getCreditRequests();
        orders = SQLHelper.getOrders();

        assertEquals(0, payments.size());
        assertEquals(0, credits.size());
        assertEquals(0, orders.size());
    }


    @DisplayName(value = "Отправка POST запроса платежа с пустым значением year")
    @Test
    public void shouldPOSTYearEmpty() {
        var cardInfo = DataHelper.getYearEmpty();
        SQLHelper.getData(cardInfo, paymentUrl, 400);
        payments = SQLHelper.getPayments();
        credits = SQLHelper.getCreditRequests();
        orders = SQLHelper.getOrders();

        assertEquals(0, payments.size());
        assertEquals(0, credits.size());
        assertEquals(0, orders.size());
    }


    @DisplayName(value = "Отправка POST запроса платежа с пустым значением holder")
    @Test
    public void shouldPOSTHolderEmpty() {
        var cardInfo = DataHelper.getHolderEmpty();
        SQLHelper.getData(cardInfo, paymentUrl, 400);
        payments = SQLHelper.getPayments();
        credits = SQLHelper.getCreditRequests();
        orders = SQLHelper.getOrders();

        assertEquals(0, payments.size());
        assertEquals(0, credits.size());
        assertEquals(0, orders.size());
    }


    @DisplayName(value = "Отправка POST запроса платежа с пустым значением cvc")
    @Test
    public void shouldPOSTCvcEmpty() {
        var cardInfo = DataHelper.getCvcEmpty();
        SQLHelper.getData(cardInfo, paymentUrl, 400);
        payments = SQLHelper.getPayments();
        credits = SQLHelper.getCreditRequests();
        orders = SQLHelper.getOrders();

        assertEquals(0, payments.size());
        assertEquals(0, credits.size());
        assertEquals(0, orders.size());
    }


    @DisplayName(value = "Позитивный тест. Покупка тура в кредит с действующей карты, создание записи в таблице credit_request_entity")
    @Test
    public void shouldValidTestCreditCardApprovedEntityAdded() {
        var cardInfo = DataHelper.getValidCardApproved();
        SQLHelper.getData(cardInfo, creditUrl, 200);
        credits = SQLHelper.getCreditRequests();
        
        assertEquals(1, credits.size());
        assertEquals("APPROVED", SQLHelper.getCreditStatus());
    }


    @DisplayName(value = "Позитивный тест. Покупка тура в кредит с недействующей карты, создание записи в таблице credit_request_entity")
    @Test
    public void shouldValidTestCreditCardDeclinedEntityAdded() {
        var cardInfo = DataHelper.getValidCardDeclined();
        SQLHelper.getData(cardInfo, creditUrl, 200);
        credits = SQLHelper.getCreditRequests();

        assertEquals(1, credits.size());
        assertEquals("DECLINED", SQLHelper.getCreditStatus());
    }


    @DisplayName(value = "Отправка пустого POST запроса кредита")
    @Test
    public void shouldCreditPOSTBodyEmpty() {
        var cardInfo = DataHelper.getAllEmpty();
        SQLHelper.getData(cardInfo, creditUrl, 400);
        payments = SQLHelper.getPayments();
        credits = SQLHelper.getCreditRequests();
        orders = SQLHelper.getOrders();

        assertEquals(0, payments.size());
        assertEquals(0, credits.size());
        assertEquals(0, orders.size());
    }


    @DisplayName(value = "Отправка POST запроса кредита с пустым значением number")
    @Test
    public void shouldCreditPOSTNumberEmpty() {
        var cardInfo = DataHelper.getCardEmpty();
        SQLHelper.getData(cardInfo, creditUrl, 400);
        payments = SQLHelper.getPayments();
        credits = SQLHelper.getCreditRequests();
        orders = SQLHelper.getOrders();

        assertEquals(0, payments.size());
        assertEquals(0, credits.size());
        assertEquals(0, orders.size());
    }


    @DisplayName(value = "Отправка POST запроса кредита с пустым значением month")
    @Test
    public void shouldCreditPOSTMonthEmpty() {
        var cardInfo = DataHelper.getMonthEmpty();
        SQLHelper.getData(cardInfo, creditUrl, 400);
        payments = SQLHelper.getPayments();
        credits = SQLHelper.getCreditRequests();
        orders = SQLHelper.getOrders();

        assertEquals(0, payments.size());
        assertEquals(0, credits.size());
        assertEquals(0, orders.size());
    }


    @DisplayName(value = "Отправка POST запроса кредита с пустым значением year")
    @Test
    public void shouldCreditPOSTYearEmpty() {
        var cardInfo = DataHelper.getYearEmpty();
        SQLHelper.getData(cardInfo, creditUrl, 400);
        payments = SQLHelper.getPayments();
        credits = SQLHelper.getCreditRequests();
        orders = SQLHelper.getOrders();

        assertEquals(0, payments.size());
        assertEquals(0, credits.size());
        assertEquals(0, orders.size());
    }


    @DisplayName(value = "Отправка POST запроса кредита с пустым значением holder")
    @Test
    public void shouldCreditPOSTHolderEmpty() {
        var cardInfo = DataHelper.getHolderEmpty();
        SQLHelper.getData(cardInfo, creditUrl, 400);
        payments = SQLHelper.getPayments();
        credits = SQLHelper.getCreditRequests();
        orders = SQLHelper.getOrders();

        assertEquals(0, payments.size());
        assertEquals(0, credits.size());
        assertEquals(0, orders.size());
    }


    @DisplayName(value = "Отправка POST запроса кредита с пустым значением cvc")
    @Test
    public void shouldCreditPOSTCvcEmpty() {
        var cardInfo = DataHelper.getCvcEmpty();
        SQLHelper.getData(cardInfo, creditUrl, 400);
        payments = SQLHelper.getPayments();
        credits = SQLHelper.getCreditRequests();
        orders = SQLHelper.getOrders();

        assertEquals(0, payments.size());
        assertEquals(0, credits.size());
        assertEquals(0, orders.size());
    }

}