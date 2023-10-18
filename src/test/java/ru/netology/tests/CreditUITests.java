package ru.netology.tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.SQLHelper;
import ru.netology.data.DataHelper;
import ru.netology.page.CreditPage;
import ru.netology.page.MainPage;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.SQLHelper.cleanDB;

public class CreditUITests {
    @BeforeAll
    public static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    public void setUp() {
        open("http://localhost:8080/");
    }

    @AfterAll
    static void tearDown() {
        cleanDB();
    }

    @AfterAll
    public static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    //  Positive

    @Test
    public void shouldValidCardApproved() {
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getValidCardApproved();
        CreditPage creditPage = mainPage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.getSuccessNotification();
        assertEquals("APPROVED", SQLHelper.getCreditStatus());
    }


    @Test
    public void shouldValidCardApprovedWithoutSpaces() {
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getValidCardApprovedWithoutSpaces();
        CreditPage creditPage = mainPage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.getSuccessNotification();
        assertEquals("APPROVED", SQLHelper.getCreditStatus());
    }


    @Test
    public void shouldValidCardDeclined() {
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getValidCardDeclined();
        CreditPage creditPage = mainPage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.getErrorNotification();
        assertEquals("DECLINED", SQLHelper.getCreditStatus());
    }

    @Test
    public void shouldNumberField11char() {
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getCard11char();
        CreditPage creditPage = mainPage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.getInputInvalidSub("Неверный формат");
    }

    //   Negative

    @Test
    public void shouldNumberField20char() {
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getCard20char();
        CreditPage creditPage = mainPage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.getErrorNotification();
    }


    @Test
    public void shouldNumberField16char() {
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getCard16char();
        CreditPage creditPage = mainPage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.getErrorNotification();
    }


    @Test
    public void shouldNumberField19char() {
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getCard19char();
        CreditPage creditPage = mainPage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.getErrorNotification();
    }


    @Test
    public void shouldNumberFieldSymbols() {
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getRandomCardSymbols();
        CreditPage creditPage = mainPage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.getInputInvalidSub("Неверный формат");
    }


    @Test
    public void shouldNumberFieldEmpty() {
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getCardEmpty();
        CreditPage creditPage = mainPage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.getInputInvalidSub("Поле обязательно для заполнения");
    }


    @Test
    public void shouldMonthFieldMore12() {
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getInvalidMonthOver12();
        CreditPage creditPage = mainPage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.getInputInvalidSub("Неверно указан срок действия карты");
    }


    @Test
    public void shouldMonthFieldNull() {
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getInvalidMonthNull();
        CreditPage creditPage = mainPage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.getInputInvalidSub("Неверно указан срок действия карты");
    }


    @Test
    public void shouldMonthField1char() {
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getMonthOneChar();
        CreditPage creditPage = mainPage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.getSuccessNotification();
    }


    @Test
    public void shouldMonthFieldSymbols() {
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getInvalidMonthSymbols();
        CreditPage creditPage = mainPage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.getInputInvalidSub("Неверный формат");
    }


    @Test
    public void shouldMonthFieldLessCurrent() {
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getInvalidMonthLessCurrent();
        CreditPage creditPage = mainPage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.getInputInvalidSub("Неверно указан срок действия карты");
    }


    @Test
    public void shouldMonthFieldEmpty() {
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getMonthEmpty();
        CreditPage creditPage = mainPage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.getInputInvalidSub("Поле обязательно для заполнения");
    }


    @Test
    public void shouldYearFieldLessCurrent() {
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getInvalidYearLessCurrent();
        CreditPage creditPage = mainPage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.getInputInvalidSub("Истёк срок действия карты");
    }


    @Test
    public void shouldYearFieldNull() {
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getYearNull();
        CreditPage creditPage = mainPage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.getInputInvalidSub("Истёк срок действия карты");
    }


    @Test
    public void shouldYearFieldEmpty() {
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getYearEmpty();
        CreditPage creditPage = mainPage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.getInputInvalidSub("Поле обязательно для заполнения");
    }


    @Test
    public void shouldHolderFieldWithSpace() {
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getValidHolderWithSpace();
        CreditPage creditPage = mainPage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.getSuccessNotification();
    }


    @Test
    public void shouldHolderFieldWithDashMiddle() {
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getValidHolderWithDashMiddle();
        CreditPage creditPage = mainPage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.getSuccessNotification();
    }


    @Test
    public void shouldHolderFieldWithDashFirst() {
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getHolderWithDashFirst();
        CreditPage creditPage = mainPage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.getInputInvalidSub("Неверный формат");
    }


    @Test
    public void shouldHolderFieldWithDashEnd() {
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getHolderWithDashEnd();
        CreditPage creditPage = mainPage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.getInputInvalidSub("Неверный формат");
    }


    @Test
    public void shouldHolderFieldWithSpaceFirst() {
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getHolderWithSpaceFirst();
        CreditPage creditPage = mainPage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.getInputInvalidSub("Неверный формат");
    }


    @Test
    public void shouldHolderFieldWithSpaceEnd() {
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getHolderWithSpaceEnd();
        CreditPage creditPage = mainPage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.getInputInvalidSub("Неверный формат");
    }


    @Test
    public void shouldHolderFieldLowercase() {
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getHolderLowercase();
        CreditPage creditPage = mainPage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.getSuccessNotification();
    }


    @Test
    public void shouldHolderFieldRu() {
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getHolderRu();
        CreditPage creditPage = mainPage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.getInputInvalidSub("Неверный формат");
    }


    @Test
    public void shouldHolderFieldNumbers() {
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getHolderNumbers();
        CreditPage creditPage = mainPage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.getInputInvalidSub("Неверный формат");
    }


    @Test
    public void shouldHolderFieldSymbols() {
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getHolderSymbols();
        CreditPage creditPage = mainPage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.getInputInvalidSub("Неверный формат");
    }


    @Test
    public void shouldHolderFieldEmpty() {
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getHolderEmpty();
        CreditPage creditPage = mainPage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.getInputInvalidSub("Поле обязательно для заполнения");
    }


    @Test
    public void shouldCVCField2char() {
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getInvalidCvc2char();
        CreditPage creditPage = mainPage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.getInputInvalidSub("Неверный формат");
    }


    @Test
    public void shouldCVCField4char() {
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getInvalidCvc4char();
        CreditPage creditPage = mainPage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.getSuccessNotification();
    }


    @Test
    public void shouldCVCFieldSymbols() {
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getInvalidCvcSymbols();
        CreditPage creditPage = mainPage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.getInputInvalidSub("Неверный формат");
    }


    @Test
    public void shouldCVCFieldEmpty() {
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getCvcEmpty();
        CreditPage creditPage = mainPage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.getInputInvalidSub("Поле обязательно для заполнения");
    }


    @Test
    public void shouldAllFieldsEmpty() {
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getAllEmpty();
        CreditPage creditPage = mainPage.creditButtonClick();
        creditPage.inputData(CardInfo);
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
