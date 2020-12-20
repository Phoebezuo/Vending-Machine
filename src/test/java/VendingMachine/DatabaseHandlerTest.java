package VendingMachine;

import VendingMachine.Data.User;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class DatabaseHandlerTest {
    @Test
    public void testSaveAndLoadUserData() throws IOException {
        List<User> expected = new ArrayList<>();
        expected.add(new User("alan", "123", User.UserType.CUSTOMER));
        expected.add(new User("blan", "123", User.UserType.OWNER));
        DatabaseHandler.saveUserData(expected);

        List<User> actual = DatabaseHandler.loadUserData();
        assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).getId(), actual.get(i).getId());
            assertEquals(expected.get(i).getPassword(), actual.get(i).getPassword());
            assertEquals(expected.get(i).getUsername(), actual.get(i).getUsername());
            assertEquals(expected.get(i).getType(), actual.get(i).getType());
            assertEquals(expected.get(i).getPermission(User.Permission.MANAGE_ITEM),
                    actual.get(i).getPermission(User.Permission.MANAGE_ITEM));
            assertEquals(expected.get(i).getPermission(User.Permission.MANAGE_CASH),
                    actual.get(i).getPermission(User.Permission.MANAGE_CASH));
            assertEquals(expected.get(i).getPermission(User.Permission.MANAGE_USER),
                    actual.get(i).getPermission(User.Permission.MANAGE_USER));

        }
    }

    @Test
    public void testSaveAndLoadCashData() throws IOException {
        Map<Double, Integer> expected = new HashMap<>();
        double[] values = {100.0, 50.0, 20.0, 10.0, 5.0, 2.0, 1.0, 0.5, 0.2, 0.1, 0.05};
        for (double value : values) {
            expected.put(value, 5);
        }
        DatabaseHandler.saveCashData(expected);

        Map<Double, Integer> actual = DatabaseHandler.loadCashData();
        assertEquals(expected.size(), actual.size());
        for (double value : values) {
            assertEquals(expected.get(value), actual.get(value));
        }
    }
}
