package VendingMachine.Data;

import java.util.Objects;

public class Product {
    private static int staticCode = 1;
    private final int id;
    private Category category;
    private String name;
    private double price;
    private String code;
    private int stock;
    private int sold;

    public Product() {
        this.category = null;
        this.name = "";
        this.price = -1;
        this.stock = -1;
        this.code = "";
        this.id = staticCode++;
        this.sold = 0;
    }

    public Product(String code, Category category, String name, double price, int stock) {
        this.category = category;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.id = staticCode++;
        this.code = code;
    }

    public void sold(int sold) {
        this.sold += sold;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        return this == o;
    }

    @Override
    public String toString() {
        return Integer.toString(id);
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getSold() {
        return sold;
    }

    public enum Category {
        DRINK,
        CHOCOLATE,
        CHIP,
        CANDY
    }
}
