package ru.netology.tests.UITests;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.MainPage;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.SQLHelper.cleanDB;

public class CreditTests {
    @BeforeAll
    public static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    public void setUp() {
        open("http://localhost:8080/");
    }

    @AfterAll
    public static void tearDown() {
        cleanDB();
    }

    @AfterAll
    public static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    // Positive tests

    @DisplayName("Покупка тура в кредит с действующей карты")
    @Test
    public void shouldValidCreditCardApproved() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getValidCardApproved();
        var creditPage = mainPage.creditButtonClick();
        creditPage.inputData(cardInfo);
        var credits = SQLHelper.getCreditRequests();
        assertEquals("APPROVED", credits.get(0).getStatus());
    }

    @DisplayName("Покупка тура в кредит с недействующей карты")
    @Test
    public void shouldValidCreditCardDeclined() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getValidCardDeclined();
        var creditPage = mainPage.creditButtonClick();
        creditPage.inputData(cardInfo);
        var credits = SQLHelper.getCreditRequests();
        assertEquals("DECLINED", credits.get(0).getStatus());
    }

    //Negative tests
    
    @DisplayName("Поле Номер карты 11 цифр")
    @Test
    public void shouldNumberField11char() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getCard11char();
        var creditPage = mainPage.creditButtonClick();
        creditPage.inputData(cardInfo);
        creditPage.getInputInvalidSub("Неверный формат");
    }


    @DisplayName("Поле Номер карты 20 цифр")
    @Test
    public void shouldNumberField20char() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getCard20char();
        var creditPage = mainPage.creditButtonClick();
        creditPage.inputData(cardInfo);
        creditPage.getErrorNotification();
    }

    @DisplayName("Поле Номер карты 16 цифр")
    @Test
    public void shouldNumberField16char() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getCard16char();
        var creditPage = mainPage.creditButtonClick();
        creditPage.inputData(cardInfo);
        creditPage.getErrorNotification();
    }


    @DisplayName("Поле Номер карты 19 цифр")
    @Test
    public void shouldNumberField19char() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getCard19char();
        var creditPage = mainPage.creditButtonClick();
        creditPage.inputData(cardInfo);
        creditPage.getErrorNotification();
    }


    @DisplayName("Поле Номер карты символы")
    @Test
    public void shouldNumberFieldSymbols() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getRandomCardSymbols();
        var creditPage = mainPage.creditButtonClick();
        creditPage.inputData(cardInfo);
        creditPage.getInputInvalidSub("Неверный формат");
    }


    @DisplayName("Поле Номер карты пустое")
    @Test
    public void shouldNumberFieldEmpty() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getCardEmpty();
        var creditPage = mainPage.creditButtonClick();
        creditPage.inputData(cardInfo);
        creditPage.getInputInvalidSub("Поле обязательно для заполнения");
    }


    @DisplayName("Поле Месяц число больше 12")
    @Test
    public void shouldMonthFieldMore12() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getInvalidMonthOver12();
        var creditPage = mainPage.creditButtonClick();
        creditPage.inputData(cardInfo);
        creditPage.getInputInvalidSub("Неверно указан срок действия карты");
    }


    @DisplayName("Поле Месяц число 00")
    @Test
    public void shouldMonthFieldNull() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getInvalidMonthNull();
        var creditPage = mainPage.creditButtonClick();
        creditPage.inputData(cardInfo);
        creditPage.getInputInvalidSub("Неверно указан срок действия карты");
    }


    @DisplayName("Поле Месяц 1 число")
    @Test
    public void shouldMonthField1char() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getMonthOneChar();
        var creditPage = mainPage.creditButtonClick();
        creditPage.inputData(cardInfo);
        creditPage.getSuccessNotification();
    }


    @DisplayName("Поле Месяц математические символы")
    @Test
    public void shouldMonthFieldSymbols() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getInvalidMonthSymbols();
        var creditPage = mainPage.creditButtonClick();
        creditPage.inputData(cardInfo);
        creditPage.getInputInvalidSub("Неверный формат");
    }


    @DisplayName("Поле Месяц меньше текущего")
    @Test
    public void shouldMonthFieldLessCurrent() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getInvalidMonthLessCurrent();
        var creditPage = mainPage.creditButtonClick();
        creditPage.inputData(cardInfo);
        creditPage.getInputInvalidSub("Неверно указан срок действия карты");
    }


    @DisplayName("Поле Месяц пустое")
    @Test
    public void shouldMonthFieldEmpty() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getMonthEmpty();
        var creditPage = mainPage.creditButtonClick();
        creditPage.inputData(cardInfo);
        creditPage.getInputInvalidSub("Поле обязательно для заполнения");
    }


    @DisplayName("Поле Год меньше текущего")
    @Test
    public void shouldYearFieldLessCurrent() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getInvalidYearLessCurrent();
        var creditPage = mainPage.creditButtonClick();
        creditPage.inputData(cardInfo);
        creditPage.getInputInvalidSub("Истёк срок действия карты");
    }


    @DisplayName("Поле Год число 00")
    @Test
    public void shouldYearFieldNull() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getYearNull();
        var creditPage = mainPage.creditButtonClick();
        creditPage.inputData(cardInfo);
        creditPage.getInputInvalidSub("Истёк срок действия карты");
    }


    @DisplayName("Поле Год пустое")
    @Test
    public void shouldYearFieldEmpty() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getYearEmpty();
        var creditPage = mainPage.creditButtonClick();
        creditPage.inputData(cardInfo);
        creditPage.getInputInvalidSub("Поле обязательно для заполнения");
    }


    @DisplayName("Поле Владелец с пробелом в середине")
    @Test
    public void shouldHolderFieldWithSpaceMiddle() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getValidHolderWithSpace();
        var creditPage = mainPage.creditButtonClick();
        creditPage.inputData(cardInfo);
        creditPage.getSuccessNotification();
    }


    @DisplayName("Поле Владелец с дефисом в середине")
    @Test
    public void shouldHolderFieldWithDashMiddle() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getValidHolderWithDashMiddle();
        var creditPage = mainPage.creditButtonClick();
        creditPage.inputData(cardInfo);
        creditPage.getSuccessNotification();
    }


    @DisplayName("Поле Владелец с дефисом в начале")
    @Test
    public void shouldHolderFieldWithDashFirst() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getHolderWithDashFirst();
        var creditPage = mainPage.creditButtonClick();
        creditPage.inputData(cardInfo);
        creditPage.getInputInvalidSub("Неверный формат");
    }


    @DisplayName("Поле Владелец с дефисом в конце")
    @Test
    public void shouldHolderFieldWithDashEnd() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getHolderWithDashEnd();
        var creditPage = mainPage.creditButtonClick();
        creditPage.inputData(cardInfo);
        creditPage.getInputInvalidSub("Неверный формат");
    }


    @DisplayName("Поле Владелец с пробелом в начале")
    @Test
    public void shouldHolderFieldWithSpaceFirst() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getHolderWithSpaceFirst();
        var creditPage = mainPage.creditButtonClick();
        creditPage.inputData(cardInfo);
        creditPage.getInputInvalidSub("Неверный формат");
    }


    @DisplayName("Поле Владелец с пробелом в конце")
    @Test
    public void shouldHolderFieldWithSpaceEnd() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getHolderWithSpaceEnd();
        var creditPage = mainPage.creditButtonClick();
        creditPage.inputData(cardInfo);
        creditPage.getInputInvalidSub("Неверный формат");
    }


    @DisplayName("Поле Владелец нижний регистр")
    @Test
    public void shouldHolderFieldLowercase() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getHolderLowercase();
        var creditPage = mainPage.creditButtonClick();
        creditPage.inputData(cardInfo);
        creditPage.getSuccessNotification();
    }


    @DisplayName("Поле Владелец кириллицей")
    @Test
    public void shouldHolderFieldRu() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getHolderRu();
        var creditPage = mainPage.creditButtonClick();
        creditPage.inputData(cardInfo);
        creditPage.getInputInvalidSub("Неверный формат");
    }


    @DisplayName("Поле Владелец латиницей и числами")
    @Test
    public void shouldHolderFieldNumbers() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getHolderNumbers();
        var creditPage = mainPage.creditButtonClick();
        creditPage.inputData(cardInfo);
        creditPage.getInputInvalidSub("Неверный формат");
    }


    @DisplayName("Поле Владелец латиницей и спецсимволами")
    @Test
    public void shouldHolderFieldSymbols() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getHolderSymbols();
        var creditPage = mainPage.creditButtonClick();
        creditPage.inputData(cardInfo);
        creditPage.getInputInvalidSub("Неверный формат");
    }


    @DisplayName("Поле Владелец пустое")
    @Test
    public void shouldHolderFieldEmpty() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getHolderEmpty();
        var creditPage = mainPage.creditButtonClick();
        creditPage.inputData(cardInfo);
        creditPage.getInputInvalidSub("Поле обязательно для заполнения");
    }


    @DisplayName("Поле CVC/CVV 2 цифры")
    @Test
    public void shouldCVCField2char() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getInvalidCvc2char();
        var creditPage = mainPage.creditButtonClick();
        creditPage.inputData(cardInfo);
        creditPage.getInputInvalidSub("Неверный формат");
    }


    @DisplayName("Поле CVC/CVV 4 цифры")
    @Test
    public void shouldCVCField4char() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getInvalidCvc4char();
        var creditPage = mainPage.creditButtonClick();
        creditPage.inputData(cardInfo);
        creditPage.getSuccessNotification();
    }


    @DisplayName("Поле CVC/CVV символами")
    @Test
    public void shouldCVCFieldSymbols() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getInvalidCvcSymbols();
        var creditPage = mainPage.creditButtonClick();
        creditPage.inputData(cardInfo);
        creditPage.getInputInvalidSub("Неверный формат");
    }


    @DisplayName("Поле CVC/CVV пустое")
    @Test
    public void shouldCVCFieldEmpty() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getCvcEmpty();
        var creditPage = mainPage.creditButtonClick();
        creditPage.inputData(cardInfo);
        creditPage.getInputInvalidSub("Поле обязательно для заполнения");
    }


    @DisplayName("Все поля пустые")
    @Test
    public void shouldAllFieldsEmpty() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getAllEmpty();
        var creditPage = mainPage.creditButtonClick();
        creditPage.inputData(cardInfo);
        $(byText("Номер карты")).parent().$(".input__sub").shouldBe(visible).
                shouldHave(text("Поле обязательно для заполнения"));
        $(byText("Месяц")).parent().$(".input__sub").shouldBe(visible).
                shouldHave(text("Поле обязательно для заполнения"));
        $(byText("Год")).parent().$(".input__sub").shouldBe(visible).
                shouldHave(text("Поле обязательно для заполнения"));
        $(byText("Владелец")).parent().$(".input__sub").shouldBe(visible).
                shouldHave(text("Поле обязательно для заполнения"));
        $(byText("CVC/CVV")).parent().$(".input__sub").shouldBe(visible).
                shouldHave(text("Поле обязательно для заполнения"));
    }

}
