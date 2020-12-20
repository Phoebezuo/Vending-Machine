package VendingMachine.Window.TransactionHistory;

import VendingMachine.Data.Product;

import java.util.Map;

public class TransactionHistoryTableEntry {
    String time;
    String moneyPaid;
    String changes;
    String paymentMethod;
    private Map<Product, Integer> shoppingList;

    public TransactionHistoryTableEntry(String time, String moneyPaid, String changes,
                                        String paymentMethod, Map<Product, Integer> shoppingList) {
        this.changes = changes;
        this.time = time;
        this.moneyPaid = moneyPaid;
        this.paymentMethod = paymentMethod;
        this.shoppingList = shoppingList;
    }

    public Map<Product, Integer> getShoppingList() {
        return shoppingList;
    }

    public String getTime() {
        return time;
    }

    public String getMoneyPaid() {
        return moneyPaid;
    }

    public String getChanges() {
        return changes;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }
}
