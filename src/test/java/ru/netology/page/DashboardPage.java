package ru.netology.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private final ElementsCollection cards = $$(".list__item div");
    private final SelenideElement firstCard = $$("[data-test-id=action-deposit]").first();
    private final SelenideElement secondCard = $$("[data-test-id=action-deposit]").last();

    public DashboardPage() {
    }

    public final int getFirstCardBalance() {
        var text = cards.first().text();
        return extractBalance(text);
    }

    public final int getSecondCardBalance() {
        var text = cards.last().text();
        return extractBalance(text);
    }

    public final TransferPage firstCard() {
        firstCard.click();
        return new TransferPage();
    }

    public final TransferPage secondCard() {
        secondCard.click();
        return new TransferPage();
    }

    private static int extractBalance(String text) {
        var arr = text.split(" ");
        return Integer.parseInt(arr[5]);
    }
}
