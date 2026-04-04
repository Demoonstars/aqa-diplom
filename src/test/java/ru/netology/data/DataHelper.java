package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {
    private static final Faker faker = new Faker(new Locale("en"));

    private DataHelper() {
    }

    @Value
    public static class CardInfo {
        String cardNumber;
        String month;
        String year;
        String owner;
        String cvc;
    }

    public static CardInfo getValidApprovedCard() {
        return new CardInfo(
                "1111 2222 3333 4444",
                generateValidMonth(),
                generateValidYear(),
                generateValidOwner(),
                generateValidCvc()
        );
    }

    public static CardInfo getValidDeclinedCard() {
        return new CardInfo(
                "5555 6666 7777 8888",
                generateValidMonth(),
                generateValidYear(),
                generateValidOwner(),
                generateValidCvc()
        );
    }

    public static String generateValidMonth() {
        int shift = (int) (Math.random() * 10);
        return LocalDate.now().plusMonths(shift).format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String generateValidYear() {
        return LocalDate.now().plusYears(2).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String generateValidOwner() {
        return faker.name().fullName().toUpperCase(Locale.ROOT);
    }

    public static String generateValidCvc() {
        return faker.number().digits(3);
    }
}