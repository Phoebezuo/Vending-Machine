package VendingMachine.Window.CancelHistory;

public class CancelTableEntry {

    private final String date;
    private final String user;
    private final String reason;

    public CancelTableEntry(String date, String user, String reason) {
        this.date = date;
        this.user = user;
        this.reason = reason;
    }

    public String getDate() {
        return date;
    }

    public String getUser() {
        return user;
    }

    public String getReason() {
        return reason;
    }
}
