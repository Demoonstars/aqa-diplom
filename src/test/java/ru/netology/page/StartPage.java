package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$;

public class StartPage {
    private SelenideElement buyButton = $$("button").findBy(exactText("Купить"));
    private SelenideElement creditButton = $$("button").findBy(exactText("Купить в кредит"));
    private SelenideElement paymentHeading = $$("h3").findBy(exactText("Оплата по карте"));
    private SelenideElement creditHeading = $$("h3").findBy(exactText("Кредит по данным карты"));

    public PaymentPage goToPaymentPage() {
        buyButton.click();
        paymentHeading.shouldBe(visible);
        return new PaymentPage();
    }

    public PaymentPage goToCreditPage() {
        creditButton.click();
        creditHeading.shouldBe(visible);
        return new PaymentPage();
    }
}