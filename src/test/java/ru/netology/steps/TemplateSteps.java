package ru.netology.steps;

import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Пусть;
import io.cucumber.java.ru.Тогда;
import lombok.val;
import ru.netology.data.Data;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TemplateSteps {

    @Пусть("пользователь залогинен с именем {string} и паролем {string}")
    public void loginWithNameAndPasswordAndVerify(String string, String string2) {
        open("http://localhost:9999", LoginPage.class);
        val loginPage = new LoginPage();
        val authInfo = new Data.AuthInfo(string, string2);
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = Data.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
    }

    @Когда("пользователь переводит {string} рублей с карты с номером {string} на свою \"{int}\" карту с главной страницы")
    public void transferMoneyToOwnCard(String amount, String string2, int cardId) {
        int firstCardBalance = DashboardPage.getFirstCardBalance();
        int secondCardBalance = DashboardPage.getSecondCardBalance();
        var transferPage = DashboardPage.firstCard();
        String string3 = string2.replace(" ","");
        transferPage.transferCard(new Data.CardInfo(string3), Integer.parseInt(amount));
        Data.increaseBalance(firstCardBalance, Integer.parseInt(amount));
        Data.decreaseBalance(secondCardBalance, Integer.parseInt(amount));
        DashboardPage.getFirstCardBalance();
        DashboardPage.getSecondCardBalance();
    }

    @Тогда("баланс его \"{int}\" карты из списка на главной странице должен стать {string} рублей")
    public void verify(int cardId, String string2) {
        int firstBalance = DashboardPage.getFirstCardBalance();
        assertEquals(firstBalance, Integer.parseInt(string2));
    }
}
