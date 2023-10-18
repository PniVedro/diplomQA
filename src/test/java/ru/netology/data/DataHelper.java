package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {
    private static Faker faker = new Faker(new Locale("en"));

    private DataHelper() {}

    @Value
    public static class CardInfo {
        String number;
        String month;
        String year;
        String holder;
        String cvc;
    }

    public static String getValidHolder() {
        return faker.name().fullName().toUpperCase();
    }

    public static String getMonth(int month) {
        return LocalDate.now().plusMonths(month).format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String getYear(int year) {
        return LocalDate.now().plusYears(year).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String getYearLessCurrent(int year) {
        return LocalDate.now().minusYears(year).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String getValidCvc() {
        return faker.numerify("###");
    }

    public static CardInfo getValidCardApproved() {
        return new CardInfo("4444 4444 4444 4441", getMonth(1), getYear(1), getValidHolder(),
                getValidCvc());
    }

    public static CardInfo getValidCardDeclined() {
        return new CardInfo("4444 4444 4444 4442", getMonth(1), getYear(1), getValidHolder(),
                getValidCvc());
    }

    public static CardInfo getValidCardApprovedWithoutSpaces() {
        return new CardInfo("4444444444444441", getMonth(1), getYear(1), getValidHolder(),
                getValidCvc());
    }

    public static CardInfo getCard16char() {
        return new CardInfo(faker.numerify("#### #### #### ####"), getMonth(1), getYear(1), getValidHolder(),
                getValidCvc());
    }

    public static CardInfo getCard19char() {
        return new CardInfo(faker.numerify("#### #### #### #### ###"), getMonth(1), getYear(1), getValidHolder(),
                getValidCvc());
    }

    public static CardInfo getCard11char() {
        return new CardInfo(faker.numerify("#### #### ###"), getMonth(1), getYear(1), getValidHolder(),
                getValidCvc());
    }

    public static CardInfo getCard20char() {
        return new CardInfo(faker.numerify("#### #### #### #### ####"), getMonth(1), getYear(1),
                getValidHolder(), getValidCvc());
    }

    public static CardInfo getRandomCardSymbols() {
        return new CardInfo(faker.letterify("???? ???? ???? ????"), getMonth(1), getYear(1), getValidHolder(),
                getValidCvc());
    }

    public static CardInfo getCardEmpty() {
        return new CardInfo("", getMonth(1), getYear(1), getValidHolder(), getValidCvc());
    }

    public static CardInfo getMonthOneChar() {
        return new CardInfo("4444 4444 4444 4441", String.valueOf(faker.number().numberBetween(1, 9)),
                getYear(1), getValidHolder(), getValidCvc());
    }

    public static CardInfo getInvalidMonthOver12() {
        return new CardInfo("4444 4444 4444 4441", String.valueOf(faker.number().numberBetween(13, 99)),
                getYear(1), getValidHolder(), getValidCvc());
    }

    public static CardInfo getInvalidMonthNull() {
        return new CardInfo("4444 4444 4444 4441", "00", getYear(1), getValidHolder(), getValidCvc());
    }

    public static CardInfo getInvalidMonthSymbols() {
        return new CardInfo("4444 4444 4444 4441", faker.letterify("??"), getYear(1),
                getValidHolder(), getValidCvc());
    }

    public static CardInfo getInvalidMonthLessCurrent() {
        return new CardInfo("4444 4444 4444 4441", getMonth(-1), getYear(0), getValidHolder(), getValidCvc());
    }

    public static CardInfo getMonthEmpty() {
        return new CardInfo("4444 4444 4444 4441", "", getYear(1), getValidHolder(), getValidCvc());
    }

    public static CardInfo getInvalidYearLessCurrent() {
        return new CardInfo("4444 4444 4444 4441", getMonth(1), getYearLessCurrent(1), getValidHolder(),
                getValidCvc());
    }

    public static CardInfo getYearNull() {
        return new CardInfo("4444 4444 4444 4441", getMonth(1), "00", getValidHolder(), getValidCvc());
    }

    public static CardInfo getYearEmpty() {
        return new CardInfo("4444 4444 4444 4441", getMonth(1), "", getValidHolder(), getValidCvc());
    }

    public static CardInfo getValidHolderWithSpace() {
        return new CardInfo("4444 4444 4444 4441", getMonth(1), getYear(1), faker.name().firstName().toUpperCase()
                + " " + faker.name().lastName().toUpperCase() + " " + faker.name().lastName().toUpperCase(), getValidCvc());
    }

    public static CardInfo getValidHolderWithDashMiddle() {
        return new CardInfo("4444 4444 4444 4441", getMonth(1), getYear(1), faker.name().firstName().toUpperCase()
                + "-" + faker.name().lastName().toUpperCase() + "-" + faker.name().lastName().toUpperCase(), getValidCvc());
    }

    public static CardInfo getHolderWithDashFirst() {
        return new CardInfo("4444 4444 4444 4441", getMonth(1), getYear(1), "-" + faker.name().fullName().toUpperCase(),
                getValidCvc());
    }

    public static CardInfo getHolderWithDashEnd() {
        return new CardInfo("4444 4444 4444 4441", getMonth(1), getYear(1), faker.name().fullName().toUpperCase() + "-",
                getValidCvc());
    }

    public static CardInfo getHolderWithSpaceFirst() {
        return new CardInfo("4444 4444 4444 4441", getMonth(1), getYear(1), " " + faker.name().fullName().toUpperCase(),
                getValidCvc());
    }

    public static CardInfo getHolderWithSpaceEnd() {
        return new CardInfo("4444 4444 4444 4441", getMonth(1), getYear(1), faker.name().fullName().toUpperCase() + " ",
                getValidCvc());
    }

    public static CardInfo getHolderLowercase() {
        return new CardInfo("4444 4444 4444 4441", getMonth(1), getYear(1), faker.name().fullName().toLowerCase(),
                getValidCvc());
    }

    public static CardInfo getHolderRu() {
         Faker fakerRu = new Faker(new Locale("ru"));
        return new CardInfo("4444 4444 4444 4441", getMonth(1), getYear(1), fakerRu.name().firstName().toUpperCase()
                + " " + fakerRu.name().lastName().toUpperCase(), getValidCvc());
    }

    public static CardInfo getHolderNumbers() {
        return new CardInfo("4444 4444 4444 4441", getMonth(1), getYear(1), faker.name().firstName().toUpperCase()
                + " " + faker.numerify("###"), getValidCvc());
    }

    public static CardInfo getHolderSymbols() {
        return new CardInfo("4444 4444 4444 4441", getMonth(1), getYear(1), faker.name().firstName().toUpperCase()
                + " @" + faker.name().lastName().toUpperCase(), getValidCvc());
    }

    public static CardInfo getHolderEmpty() {
        return new CardInfo("4444 4444 4444 4441", getMonth(1), getYear(1), "", getValidCvc());
    }

    public static CardInfo getInvalidCvc2char() {
        return new CardInfo("4444 4444 4444 4441", getMonth(1), getYear(1), getValidHolder(),
                faker.numerify("##"));
    }


    public static CardInfo getInvalidCvc4char() {
        return new CardInfo("4444 4444 4444 4441", getMonth(1), getYear(1), getValidHolder(),
                faker.numerify("####"));
    }

    public static CardInfo getInvalidCvcSymbols() {
        return new CardInfo("4444 4444 4444 4441", getMonth(1), getYear(1), getValidHolder(),
                faker.letterify("???"));
    }

    public static CardInfo getCvcEmpty() {
        return new CardInfo("4444 4444 4444 4441", getMonth(1), getYear(1), getValidHolder(), "");
    }

    public static CardInfo getAllEmpty() {
        return new CardInfo("", "", "", "", "");
    }
}
