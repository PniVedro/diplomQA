package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CreditPage {

    private SelenideElement heading = $$("h3").find(text("Кредит по данным карты"));
    private SelenideElement cardNumberField = $(byText("Номер карты")).parent().$(".input__control");
    private SelenideElement monthField = $(byText("Месяц")).parent().$(".input__control");
    private SelenideElement yearField = $(byText("Год")).parent().$(".input__control");
    private SelenideElement holderField = $(byText("Владелец")).parent().$(".input__control");
    private SelenideElement cvcField = $(byText("CVC/CVV")).parent().$(".input__control");
    private SelenideElement nextButton = $$("button").find(exactText("Продолжить"));
    private SelenideElement successNotification = $(".notification_status_ok");
    private SelenideElement errorNotification = $(".notification_status_error");
    private SelenideElement inputInvalidSub = $(".input__sub");
    public CreditPage() {
        heading.shouldBe(visible);

    }

    public void inputData(DataHelper.CardInfo card) {
        cardNumberField.setValue(card.getNumber());
        monthField.setValue(card.getMonth());
        yearField.setValue(card.getYear());
        holderField.setValue(card.getHolder());
        cvcField.setValue(card.getCvc());
        nextButton.click();
    }

    public void getSuccessNotification() {
        successNotification.should(visible, Duration.ofSeconds(15));

    }

    public void getErrorNotification() {
        errorNotification.should(visible, Duration.ofSeconds(15));

    }
    public void getInputInvalidSub(String message) {
        inputInvalidSub.shouldBe(visible).shouldHave(text(message));
    }

}
