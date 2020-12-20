package VendingMachine.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {
    private static int totalId = 0;
    private final int id;
    private String username;
    private String password;
    private Map<Permission, Boolean> permissions;
    private UserType type;
    private List<Transaction> shoppingHistory;
    private Transaction shoppingCart;
    private CreditCard card;

    public User() {
        this.username = "";
        this.password = "";
        this.setType(UserType.ANONYMOUS);
        this.id = totalId++;
        this.shoppingHistory = new ArrayList<>();
        this.shoppingCart = new Transaction(this.id);
    }

    public User(String username, String password, UserType type) {
        this.username = username;
        this.password = password;
        this.setType(type);
        this.id = totalId++;
        this.shoppingHistory = new ArrayList<>();
        this.shoppingCart = new Transaction(this.id);
    }


    public boolean setItemInCart(int id, int newQty) {
        return this.shoppingCart.set(id, newQty);
    }

    public void initPermissions() {
        this.permissions = new HashMap<>();
        permissions.put(Permission.MANAGE_ITEM, false);
        permissions.put(Permission.MANAGE_CASH, false);
        permissions.put(Permission.MANAGE_USER, false);
    }

    public void setPermission(Permission permission, boolean accessibility) {
        this.permissions.put(permission, accessibility);
    }

    public boolean getPermission(Permission permission) {
        return this.permissions.get(permission);
    }

    public int pay(double amount) {
        int status = shoppingCart.pay(amount);
        if (status == 0) {
            shoppingHistory.add(shoppingCart);
            shoppingCart = new Transaction(this.id);
        }
        return status;
    }

    public int pay(Map<Double, Integer> cashes) {
        int status = shoppingCart.pay(cashes);
        if (status == 0) {
            shoppingHistory.add(shoppingCart);
            shoppingCart = new Transaction(this.id);
        }
        return status;
    }

    public boolean cancelShopping(String reason) {
        shoppingCart.cancel(reason);
        shoppingHistory.add(shoppingCart);
        shoppingCart = new Transaction(this.id);
        return true;
    }

    public boolean hasSelected(int id) {
        return shoppingCart.hasProduct(id);
    }

    public double getChange() {
        if (shoppingHistory.size() > 0) {
            return shoppingHistory.get(shoppingHistory.size() - 1).getPaidAmount() - shoppingHistory.get(shoppingHistory.size() - 1).getTotalPrice();
        }
        return -1;
    }

    public double getPaidAmount() {
        return shoppingCart.getPaidAmount();
    }

    public double getTotalPrice() {
        return shoppingCart.getTotalPrice();
    }

    public Map<Product, Integer> getShoppingList() {
        return shoppingCart.getShoppingList();
    }

    public List<Transaction> getShoppingHistory() {
        return shoppingHistory;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return this.id;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
        this.initPermissions();
        if (type == UserType.SELLER) {
            this.setPermission(Permission.MANAGE_ITEM, true);
        } else if (type == UserType.CASHIER) {
            this.setPermission(Permission.MANAGE_CASH, true);
        } else if (type == UserType.OWNER) {
            this.setPermission(Permission.MANAGE_CASH, true);
            this.setPermission(Permission.MANAGE_ITEM, true);
            this.setPermission(Permission.MANAGE_USER, true);
        }
    }

    public CreditCard getCard() {
        return card;
    }

    public void setCard(CreditCard card) {
        this.card = card;
    }


    public Map<Double, Integer> getReturnChangeMap() {
        return shoppingHistory.get(shoppingHistory.size() - 1).getReturnedChangeMap();
    }

    public enum UserType {
        CUSTOMER,
        SELLER,
        CASHIER,
        OWNER,
        ANONYMOUS
    }

    public enum Permission {
        MANAGE_ITEM,
        MANAGE_CASH,
        MANAGE_USER
    }
}