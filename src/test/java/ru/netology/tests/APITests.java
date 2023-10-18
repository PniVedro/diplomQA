package ru.netology.tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Story;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.SQLHelper;
import ru.netology.data.DataHelper;
import ru.netology.page.CreditPage;
import ru.netology.page.MainPage;

import java.util.List;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.SQLHelper.cleanDB;

public class APITests {

    private static List<SQLHelper.PaymentEntity> payments;
    private static List<SQLHelper.CreditRequestEntity> credits;
    private static List<SQLHelper.OrderEntity> orders;
    private static final String creditUrl = "/credit";
    private static final String paymentUrl = "/payment";

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

//    Positive

//    Payments
@Story(value = "Покупка с действующей карты, создание записи в таблице payment_entity")
@Test
public void shouldValidTestCardApprovedEntityAdded() {
    var cardInfo = DataHelper.getValidCardApproved();
    SQLHelper.getData(cardInfo, paymentUrl, 200);
    payments = SQLHelper.getPayments();
    credits = SQLHelper.getCreditRequests();

    assertEquals("APPROVED", SQLHelper.getPaymentStatus());
}

    @Story(value = "Покупка с действующей карты, создание записи в таблице order_entity")
    @Test
    public void shouldValidTestCardApprovedOrdersAdded() {
        var cardInfo = DataHelper.getValidCardApproved();
        SQLHelper.getData(cardInfo, paymentUrl, 200);
        orders = SQLHelper.getOrders();

        assertEquals(1, orders.size());
    }

    @Story(value = "Покупка с недействующей карты, создание записи в таблице payment_entity")
    @Test
    public void shouldValidTestCardDeclinedEntityAdded() {
        var cardInfo = DataHelper.getValidCardDeclined();
        SQLHelper.getData(cardInfo, paymentUrl, 200);
        payments = SQLHelper.getPayments();
        credits = SQLHelper.getCreditRequests();

        assertEquals("DECLINED", SQLHelper.getPaymentStatus());
    }

    @Story(value = "Покупка с недействующей карты, создание записи в таблице order_entity")
    @Test
    public void shouldValidTestCardDeclinedOrdersAdded() {
        var cardInfo = DataHelper.getValidCardDeclined();
        SQLHelper.getData(cardInfo, paymentUrl, 200);
        orders = SQLHelper.getOrders();

        assertEquals(1, orders.size());
        assertEquals(SQLHelper.getBankIDForPayment(), SQLHelper.getPaymentID());
    }

//  Credit

    @Story(value = "Покупка в кредит с действующей карты, создание записи в таблице credit_request_entity")
    @Test
    public void shouldValidTestCreditCardApprovedEntityAdded() {
        var cardInfo = DataHelper.getValidCardApproved();
        SQLHelper.getData(cardInfo, creditUrl,200);
        payments = SQLHelper.getPayments();
        credits = SQLHelper.getCreditRequests();
        assertEquals("APPROVED", SQLHelper.getCreditStatus());
    }

    @Story(value = "Покупка в кредит с действующей карты, создание записи в таблице order_entity")
    @Test
    public void shouldValidTestCreditCardApprovedOrdersAdded() {
        var cardInfo = DataHelper.getValidCardApproved();
        SQLHelper.getData(cardInfo, creditUrl, 200);
        orders = SQLHelper.getOrders();

        assertEquals(1, orders.size());
    }

    @Story(value = "Покупка в кредит с недействующей карты, создание записи в таблице credit_request_entity")
    @Test
    public void shouldValidTestCreditCardDeclinedEntityAdded() {
        var cardInfo = DataHelper.getValidCardDeclined();
        SQLHelper.getData(cardInfo, creditUrl, 200);

        assertEquals("DECLINED", SQLHelper.getCreditStatus());
    }

    @Story(value = "Покупка в кредит с недействующей карты, создание записи в таблице order_entity")
    @Test
    public void shouldValidTestCreditCardDeclinedOrdersAdded() {
        var cardInfo = DataHelper.getValidCardDeclined();
        SQLHelper.getData(cardInfo, creditUrl, 200);
        orders = SQLHelper.getOrders();

        assertEquals(1, orders.size());
    }
//     Negative

    @Story(value = "Пустой POST запрос кредита")
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

    @Story(value = "POST запрос кредита с пустым значением number")
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

    @Story(value = "POST запрос кредита с пустым значением month")
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

    @Story(value = "POST запрос кредита с пустым значением year")
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

    @Story(value = "POST запрос кредита с пустым значением holder")
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

    @Story(value = "POST запрос кредита с пустым значением cvc")
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

    @Story(value = "Покупка в кредит с действующей карты")
    @Test
    public void shouldValidCardApprovedEntityAdd() {
        open("http://localhost:8080/");
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getValidCardApproved();
        CreditPage creditPage = mainPage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.getSuccessNotification();

        assertEquals("APPROVED", SQLHelper.getCreditStatus());
    }

    @Story(value = "Покупка в кредит с недействующей карты")
    @Test
    public void shouldValidCardDeclinedEntityAdd() {
        open("http://localhost:8080/");
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getValidCardDeclined();
        CreditPage creditPage = mainPage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.getErrorNotification();

        assertEquals("DECLINED", SQLHelper.getCreditStatus());
    }
}