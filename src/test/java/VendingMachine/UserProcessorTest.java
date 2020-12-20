package VendingMachine;

import VendingMachine.Data.CreditCard;
import VendingMachine.Data.Transaction;
import VendingMachine.Data.User;
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
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class UserProcessorTest {
    UserProcessor userProcessor;

    @Before
    @After
    public void restoreResources() {
        try {
            Files.copy(new File("src/main/resources/user_backup.json").toPath(),
                    new File("src/main/resources/user.json").toPath(),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Before
    public void init() throws IOException {
        userProcessor = UserProcessor.load();
        ProductProcessor.load();
        Transaction.load();
    }

    /**
     * Test UserProcessor Constructor
     */
    @Test
    public void UserProcessorConstructor() {
        assertNotNull(userProcessor);
        assertEquals(User.UserType.ANONYMOUS, userProcessor.getCurrentUser().getType());
        assertNotNull(UserProcessor.getInstance());
    }

    /**
     *
     */
    @Test
    public void testVerifyUser() {
        assertTrue(userProcessor.verifyUser("alan", "123"));
        assertFalse(userProcessor.verifyUser("blan", "1234"));

    }

    @Test
    public void testGetAmount() {
        assertEquals(0, userProcessor.getCurrentUser().getTotalPrice(), 0);
    }

    @Test
    public void testGetShoppingList() {
        assertNotNull(userProcessor.getCurrentUser().getShoppingList());
    }

    @Test
    public void testAddUser() {
        userProcessor.addUser("test", "test");
        assertTrue(userProcessor.verifyUser("test", "test"));
        assertFalse(userProcessor.addUser("test", "test"));
        userProcessor.removeUser(3);
    }

    @Test
    public void testAddUserWithType() {
        userProcessor.addUser("test", "test", "CASHIER");
        assertTrue(userProcessor.verifyUser("test", "test"));
        assertFalse(userProcessor.addUser("test", "test"));
        assertEquals(User.UserType.CASHIER, userProcessor.getCurrentUser().getType());
    }

    @Test
    public void testHasUser() {
        assertTrue(userProcessor.hasUser("alan"));
        assertFalse(userProcessor.hasUser("anyone"));
    }

    @Test
    public void testRemoveUser() {
        assertTrue(userProcessor.removeUser(2));
        assertFalse(userProcessor.removeUser(10000));
        assertFalse(userProcessor.verifyUser("blan", "123"));
    }

    @Test
    public void testChangeUsername() {
        assertTrue(userProcessor.setUsername(2, "test1"));
        assertTrue(userProcessor.verifyUser("test1", "123"));
        assertFalse(userProcessor.setUsername(10000, ""));
    }

    @Test
    public void testGetUsers() throws IOException {
        List<User> userList = DatabaseHandler.loadUserData();
        for (int i = 0; i < userList.size(); i++) {
            assertEquals(userList.get(i).getId(), userProcessor.getUsers().get(i).getId());
            assertEquals(userList.get(i).getPassword(), userProcessor.getUsers().get(i).getPassword());
            assertEquals(userList.get(i).getType(), userProcessor.getUsers().get(i).getType());
            assertEquals(userList.get(i).getUsername(), userProcessor.getUsers().get(i).getUsername());
        }
    }

    @Test
    public void testGetCurrentUser() {
        userProcessor.verifyUser("alan", "123");
        assertEquals("alan", userProcessor.getCurrentUser().getUsername());
        assertEquals("123", userProcessor.getCurrentUser().getPassword());
        assertEquals(User.UserType.CUSTOMER, userProcessor.getCurrentUser().getType());
    }

    @Test
    public void testLogoutUser() {
        userProcessor.verifyUser("alan", "123");
        userProcessor.logoutUser();
        assertEquals(User.UserType.ANONYMOUS, userProcessor.getCurrentUser().getType());
    }

    @Test
    public void testChangePassword() {
        assertTrue(userProcessor.setPassword(2, "test1"));
        assertTrue(userProcessor.verifyUser("blan", "test1"));
        assertFalse(userProcessor.setPassword(10000, "123"));
    }

    @Test
    public void testChangeType() {
        assertTrue(userProcessor.setUserType(2, "SELLER"));
        List<User> userList = UserProcessor.getInstance().getUsers();
        for (int i = 0; i < userList.size(); i++) {
            assertEquals(userList.get(i).getType(), userProcessor.getUsers().get(i).getType());
        }
        assertFalse(userProcessor.setUserType(10000, "CASHIER"));
    }

    @Test
    public void testGetInstance() {
        assertNotNull(UserProcessor.getInstance());
    }

    @Test
    public void testPay() {
        assertEquals(0, userProcessor.getCurrentUser().pay(10));
        assertEquals(1, userProcessor.getCurrentUser().pay(-1));
        assertTrue(userProcessor.getCurrentUser().setItemInCart(1,1));
    }

    @Test
    public void testCancel() {
        assertTrue(userProcessor.getCurrentUser().cancelShopping("test"));
    }

    @Test
    public void testGetChange() throws IOException{
        assertEquals(-1,userProcessor.getCurrentUser().getChange(),0);
        userProcessor.getCurrentUser().pay(10);
        assertEquals(10, userProcessor.getCurrentUser().getChange(), 0);
        CashProcessor.load();
        Map<Double, Integer> cashes = new HashMap<>();
        cashes.put(10.0,1);
        userProcessor.getCurrentUser().setItemInCart(1,1);
        userProcessor.getCurrentUser().pay(cashes);
        userProcessor.getCurrentUser().getReturnChangeMap();
    }

    @Test
    public void testPaidAmount() {
        assertEquals(0, userProcessor.getCurrentUser().getPaidAmount(), 0);
    }

    @Test
    public void testGetShoppingHistory() {
        assertNotNull(userProcessor.getCurrentUser().getShoppingHistory());
    }

    @Test
    public void testCard() {
        CreditCard creditCard = new CreditCard();
        userProcessor.getCurrentUser().setCard(creditCard);
        assertEquals(creditCard, userProcessor.getCurrentUser().getCard());
    }

    @Test
    public void testHasProduct() {
        assertFalse(userProcessor.getCurrentUser().hasSelected(1));
    }
}