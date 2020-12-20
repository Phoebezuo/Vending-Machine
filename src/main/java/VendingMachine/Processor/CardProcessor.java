package VendingMachine.Processor;

import VendingMachine.Data.CreditCard;
import VendingMachine.DatabaseHandler;

import java.io.IOException;
import java.util.List;

public class CardProcessor {
    private static CardProcessor cardProcessor;
    private final List<CreditCard> cards;

    private CardProcessor() throws IOException {
        cards = DatabaseHandler.loadCreditCards();
    }


    public static CardProcessor getInstance() {
        return cardProcessor;
    }

    public static CardProcessor load() throws IOException {
        cardProcessor = new CardProcessor();
        return cardProcessor;
    }

    public CreditCard validateCard(String name, String number) {
        for (CreditCard card : cards) {
            if (card.getName().equals(name) && card.getNumber().equals(number)) {
                return card;
            }
        }
        return null;
    }
}
