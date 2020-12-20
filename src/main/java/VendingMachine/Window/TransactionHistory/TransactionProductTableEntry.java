package VendingMachine.Window.TransactionHistory;

public class TransactionProductTableEntry {

    private String name;
    private String price;
    private String category;
    private String quantity;

    public TransactionProductTableEntry(String name,String category , String price, String quantity) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public String getQuantity() {
        return quantity;
    }
}
