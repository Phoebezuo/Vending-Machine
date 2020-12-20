package VendingMachine;

import VendingMachine.Processor.CardProcessor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class CardProcessorTest {

    CardProcessor cardProcessor;

    @Before
    public void init() throws IOException {
        CardProcessor.load();
        cardProcessor = CardProcessor.getInstance();
    }

    @Before
    @After
    public void restoreResources() {
        try {
            Files.copy(new File("src/main/resources/credit_cards.json").toPath(),
                    new File("src/main/resources/credit_cards.json").toPath(),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCardProcessorConstructor() {
        assertNotNull(CardProcessor.getInstance());
    }

    @Test
    public void testValidCard() {
        cardProcessor.validateCard("Charles", "40691");
        assertNull(cardProcessor.validateCard("test", "test"));
    }
}
