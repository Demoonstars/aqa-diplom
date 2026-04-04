package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class PaymentPage {
    private SelenideElement cardNumberField = $$(".input__inner").findBy(exactText("Номер карты")).$(".input__control");
    private SelenideElement monthField = $$(".input__inner").findBy(exactText("Месяц")).$(".input__control");
    private SelenideElement yearField = $$(".input__inner").findBy(exactText("Год")).$(".input__control");
    private SelenideElement ownerField = $$(".input__inner").findBy(exactText("Владелец")).$(".input__control");
    private SelenideElement cvcField = $$(".input__inner").findBy(exactText("CVC/CVV")).$(".input__control");
    private SelenideElement continueButton = $$("button").findBy(exactText("Продолжить"));

    private SelenideElement successNotification = $(".notification_status_ok");
    private SelenideElement errorNotification = $(".notification_status_error");

    public void fillForm(DataHelper.CardInfo cardInfo) {
        cardNumberField.setValue(cardInfo.getCardNumber());
        monthField.setValue(cardInfo.getMonth());
        yearField.setValue(cardInfo.getYear());
        ownerField.setValue(cardInfo.getOwner());
        cvcField.setValue(cardInfo.getCvc());
        continueButton.click();
    }

    public void waitForSuccessNotification() {
        successNotification.shouldBe(visible, Duration.ofSeconds(15));
    }

    public void waitForErrorNotification() {
        errorNotification.shouldBe(visible, Duration.ofSeconds(15));
    }

    private SelenideElement formatError = $$(".input__sub").findBy(exactText("Неверный формат"));
    private SelenideElement emptyFieldError = $$(".input__sub").findBy(exactText("Поле обязательно для заполнения"));
    private SelenideElement expiredDateError = $$(".input__sub").findBy(exactText("Истёк срок действия карты"));
    private SelenideElement invalidDateError = $$(".input__sub").findBy(exactText("Неверно указан срок действия карты"));

    public void waitForFormatError() {
        formatError.shouldBe(visible);
    }

    public void waitForEmptyFieldError() {
        emptyFieldError.shouldBe(visible);
    }

    public void waitForExpiredDateError() {
        expiredDateError.shouldBe(visible);
    }

    public void waitForInvalidDateError() {
        invalidDateError.shouldBe(visible);
    }
}