package VendingMachine.Window.ProductManagement;

public class ProductTableEntry {

    private final String code;
    private final String name;
    private final String category;
    private final String price;
    private final String quantity;
    private final int id;
    private String inCart;


    public ProductTableEntry(String code, String name, String category, String price,
                             String quantity, int id) {
        this.code = code;
        this.name = name;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.id = id;
    }

    public ProductTableEntry(String code, String name, String category, String price,
                             String quantity, int id, String inCart) {
        this.code = code;
        this.name = name;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.id = id;
        this.inCart = inCart;
    }

    public String getInCart() {
        return inCart;
    }

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getQuantity() {
        return quantity;
    }
}
