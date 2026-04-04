package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.StartPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void setup() {
        open("http://localhost:8080");
    }

    @AfterEach
    void tearDown() {
        SQLHelper.cleanDatabase();
    }

    @Test
    @DisplayName("Успешная покупка тура при оплате картой со статусом APPROVED")
    void shouldSuccessPayWithApprovedCard() {
        var startPage = new StartPage();
        var paymentPage = startPage.goToPaymentPage();

        var cardInfo = DataHelper.getValidApprovedCard();

        paymentPage.fillForm(cardInfo);

        paymentPage.waitForSuccessNotification();

        var actualStatus = SQLHelper.getPaymentStatus();
        assertEquals("APPROVED", actualStatus);
    }

    @Test
    @DisplayName("Отказ в покупке тура при оплате картой со статусом DECLINED")
    void shouldDeclinePayWithDeclinedCard() {
        var startPage = new StartPage();
        var paymentPage = startPage.goToPaymentPage();

        var cardInfo = DataHelper.getValidDeclinedCard();

        paymentPage.fillForm(cardInfo);

        paymentPage.waitForErrorNotification();

        var actualStatus = SQLHelper.getPaymentStatus();
        assertEquals("DECLINED", actualStatus);
    }

    @Test
    @DisplayName("Ошибка валидации: отправка абсолютно пустой формы")
    void shouldShowErrorWhenEmptyForm() {
        var startPage = new StartPage();
        var paymentPage = startPage.goToPaymentPage();

        var emptyCard = new DataHelper.CardInfo("", "", "", "", "");
        paymentPage.fillForm(emptyCard);

        paymentPage.waitForFormatError();
        paymentPage.waitForEmptyFieldError();
    }

    @Test
    @DisplayName("Ошибка валидации: истекший срок действия карты (год в прошлом)")
    void shouldShowErrorWhenExpiredYear() {
        var startPage = new StartPage();
        var paymentPage = startPage.goToPaymentPage();
        var validCard = DataHelper.getValidApprovedCard();

        var expiredCard = new DataHelper.CardInfo(
                validCard.getCardNumber(),
                validCard.getMonth(),
                "20",
                validCard.getOwner(),
                validCard.getCvc()
        );
        paymentPage.fillForm(expiredCard);

        paymentPage.waitForExpiredDateError();
    }

    @Test
    @DisplayName("Ошибка валидации: неверный месяц (например, 15)")
    void shouldShowErrorWhenInvalidMonth() {
        var startPage = new StartPage();
        var paymentPage = startPage.goToPaymentPage();
        var validCard = DataHelper.getValidApprovedCard();

        var invalidMonthCard = new DataHelper.CardInfo(
                validCard.getCardNumber(),
                "15",
                validCard.getYear(),
                validCard.getOwner(),
                validCard.getCvc()
        );
        paymentPage.fillForm(invalidMonthCard);

        paymentPage.waitForInvalidDateError();
    }
}