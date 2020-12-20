package VendingMachine.Window.CheckoutManagement;

public class CashTableEntry {
    private final String value;
    private final String number;


    public CashTableEntry(String value, String number) {
        this.value = value;
        this.number = number;
    }

    public String getValue() {
        return value;
    }

    public String getNumber() {
        return number;
    }

}
