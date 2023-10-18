package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$$;

public class MainPage {

    private SelenideElement heading = $$("h2").find(text("Путешествие дня"));
    private SelenideElement paymentButton = $$("button").find(text("Купить"));
    private SelenideElement creditButton = $$("button").find(text("Купить в кредит"));

    public MainPage() {
        heading.shouldBe(visible);

    }

    public PaymentPage paymentButtonClick() {
        paymentButton.click();
        return new PaymentPage();
    }

    public CreditPage creditButtonClick() {
        creditButton.click();
        return new CreditPage();
    }
}
