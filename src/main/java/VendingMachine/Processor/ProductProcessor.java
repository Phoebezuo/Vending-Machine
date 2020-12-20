package VendingMachine.Processor;

import VendingMachine.Data.Product;
import VendingMachine.DatabaseHandler;

import java.io.IOException;
import java.util.Map;

public class ProductProcessor {
    private static ProductProcessor productProcessor;
    private final Map<Integer, Product> productMap;

    private ProductProcessor() throws IOException {
        productMap = DatabaseHandler.loadProductData();
    }

    public static ProductProcessor getInstance() {
        return productProcessor;
    }

    public static ProductProcessor load() throws IOException {
        productProcessor = new ProductProcessor();
        return productProcessor;
    }

    public Product getProduct(int id) {
        return this.productMap.get(id);
    }

    public boolean addProduct(String code, String category, String name, double price,
                              int stock) {
        if (stock > 15 || stock < 0) {
            return false;
        }
        for (Product product : this.productMap.values()) {
            if (product.getCode().equals(code) || product.getName().equals(name)) {
                return false;
            }
        }

        Product product = new Product(code, Product.Category.valueOf(category), name, price, stock);
        productMap.put(product.getId(), product);
        return true;
    }

    public boolean removeProduct(int id) {
        if (!this.productMap.containsKey(id)) {
            return false;
        }
        this.productMap.remove(id);
        return true;
    }

    public boolean setProductStock(int id, int stock) {
        if (stock < 0 || stock > 15) {
            return false;
        }
        this.productMap.get(id).setStock(stock);
        return true;
    }

    public boolean setProductName(int id, String newName) {
        for (Product product : this.productMap.values()) {
            if (product.getId() != id && product.getName().equals(newName)) {
                return false;
            }
        }
        this.productMap.get(id).setName(newName);
        return true;
    }

    public boolean setProductCategory(int id, String newCategory) {
        this.productMap.get(id).setCategory(Product.Category.valueOf(newCategory));
        return true;
    }

    public boolean setProductPrice(int id, double price) {
        this.productMap.get(id).setPrice(price);
        return true;
    }

    public boolean setProductCode(int id, String newCode) {
        for (Product product : this.productMap.values()) {
            if (product.getId() != id && product.getCode().equals(newCode)) {
                return false;
            }
        }
        this.productMap.get(id).setCode(newCode);
        return true;
    }


    public Map<Integer, Product> getProductMap() {
        return productMap;
    }
}