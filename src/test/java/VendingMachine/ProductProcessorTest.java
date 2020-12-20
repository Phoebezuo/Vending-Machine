package VendingMachine;

import VendingMachine.Data.Product;
import VendingMachine.Processor.ProductProcessor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Map;

import static org.junit.Assert.*;

public class ProductProcessorTest {
    ProductProcessor productProcessor;

    @Before
    @After
    public void restoreResources() {
        try {
            Files.copy(new File("src/main/resources/product_backup.json").toPath(),
                    new File("src/main/resources/product.json").toPath(),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Before
    public void init() throws IOException {
        productProcessor = ProductProcessor.load();
    }

    @Test
    public void testGetCategory() {
        assertEquals(Product.Category.DRINK, productProcessor.getProduct(2).getCategory());
    }

    @Test
    public void testGetName() {
        assertEquals("Mineral Water", productProcessor.getProduct(1).getName());
    }

    @Test
    public void testGetPrice() {
        assertEquals(1.0, productProcessor.getProduct(1).getPrice(), 0);
    }

    @Test
    public void testGetQuantity() {
        assertEquals(7, productProcessor.getProduct(1).getStock(), 0);
    }

    @Test
    public void testProductProcessorConstructor() {
        assertNotNull(productProcessor);
    }


    @Test
    public void testAddProduct()  {
        assertFalse(productProcessor.addProduct("1", "DRINK", "Mineral Water", 1, 7));
        assertFalse(productProcessor.addProduct("20", "DRINK", "Test", 1, 20));
        assertFalse(productProcessor.addProduct("20", "DRINK", "Test", 1, -1));
        assertTrue(productProcessor.addProduct("20", "DRINK", "Test", 1, 7));
    }

    @Test
    public void testSetProductQuantity() {
        assertFalse(productProcessor.setProductStock(1, 16));
        assertTrue(productProcessor.setProductStock(1, 10));
    }

    @Test
    public void testSetProductName() {
        assertFalse(productProcessor.setProductName(1, "Sprite"));
        assertTrue(productProcessor.setProductName(1, "test"));
    }

    @Test
    public void testSetProductCategory() {
        assertTrue(productProcessor.setProductCategory(1, "CHIP"));
    }

    @Test
    public void testSetProductPrice() {
        assertTrue(productProcessor.setProductPrice(1, 10));
    }

    @Test
    public void testGetProductMap() throws IOException {
        Map<Integer, Product> test = DatabaseHandler.loadProductData();
        assertEquals(test.size(), productProcessor.getProductMap().size());
    }

    @Test
    public void testRemoveProduct() {
        assertFalse(productProcessor.removeProduct(20));
        assertTrue(productProcessor.removeProduct(1));
    }

    @Test
    public void testGetProduct() {
        assertNull(productProcessor.getProduct(20));
        assertNotNull(productProcessor.getProduct(1));
    }

    @Test
    public void testSetProductCode() {
        assertFalse(productProcessor.setProductCode(1, "5"));
        assertTrue(productProcessor.setProductCode(1, "20"));
    }

    @Test
    public void testSold() {
        productProcessor.getProduct(1).sold(1);
        assertEquals(1, productProcessor.getProduct(1).getSold(), 0);
    }

    @Test
    public void testGetHashCode() {
       int hashCode = productProcessor.getProduct(1).hashCode();
       String test = productProcessor.getProduct(1).toString();
       assertEquals(productProcessor.getProduct(1), productProcessor.getProduct(1));
    }


}















