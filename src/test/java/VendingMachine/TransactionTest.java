package VendingMachine;

import VendingMachine.Data.Transaction;
import VendingMachine.Processor.CashProcessor;
import VendingMachine.Processor.ProductProcessor;
import VendingMachine.Processor.UserProcessor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class TransactionTest {
    private Transaction transaction;

    @Before
    @After
    public void restoreResources() {
        try {
            Files.copy(new File("src/main/resources/transactions_backup.json").toPath(),
                    new File("src/main/resources/transactions.json").toPath(),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Before
    public void init() throws IOException {
        ProductProcessor.load();
        CashProcessor.load();
        Transaction.load();
        transaction = new Transaction(0);
    }

    @Test
    public void testSet() {
        assertTrue(transaction.set(1, 5));
        assertEquals(5.0, transaction.getTotalPrice(), 0);
        assertTrue(transaction.set(1, 0));
        assertEquals(0, transaction.getTotalPrice(), 0);
        assertFalse(transaction.set(1,10));
    }

    @Test
    public void testPay() {
        Map<Double, Integer> cashes = new HashMap<>();
        cashes.put(10.0,1);
        transaction.set(1,1);
        assertEquals(0,transaction.pay(cashes));
        transaction.set(1,2);
        cashes.remove(10.0);
        cashes.put(1.0,1);
        assertEquals(1,transaction.pay(cashes));
        assertNotNull(transaction.getDate());
    }

    @Test
    public void testCancel() {
        assertTrue(transaction.cancel("test"));
        assertEquals(Transaction.Status.CANCELLED, transaction.getStatus());
        assertEquals("test", transaction.getReason());
    }

    @Test
    public void testGetTransactionList() {
        assertNotNull(Transaction.getTransactionList());
    }

    @Test
    public void testGetPayment(){
        transaction.pay(10);
        assertEquals(Transaction.Payment.CARD,transaction.getPayment());
    }

    @Test
    public void testGetReturnedChangeMap() {
        assertNull(transaction.getReturnedChangeMap());
    }

    @Test
    public void testGetChange() {
        Transaction transaction = new Transaction(1);
        transaction.set(1,1);
        transaction.hasProduct(1);
        assertEquals(0,transaction.getChange(),0);
    }

    @Test
    public void getPayee() throws IOException{
        UserProcessor userProcessor = UserProcessor.load();
        userProcessor.getCurrentUser().setItemInCart(1,1);
        userProcessor.getCurrentUser().pay(10);
        userProcessor.getCurrentUser().getShoppingHistory().get(0).getPayee();

    }

}
