import org.junit.*;
import play.test.*;
import models.*;

public class BasicTest extends UnitTest {

    @Test
    public void insertCustomerTest() {
        Customer cust = new Customer("Adam").save();
        cust = Customer.find("byName", "Adam").first();
        assertNotNull(cust);
        assertEquals("Adam", cust.name);
        
        cust.delete();
        cust = Customer.find("byName", "Adam").first();
        assertNull(cust);
    }

    @Test
    public void dataTest1() {
    	Fixtures.deleteAllModels();
    	Fixtures.loadModels("data.yml");
    	 
        // Count things
        assertEquals(4, Customer.count());
        assertEquals(6, PurchaseOrder.count());
        assertEquals(6, LineItem.count());
        assertEquals(3, Product.count());
        
        Customer cust = Customer.find("byName", "Alpha and Sons").first();
        assertNotNull(cust);
        assertEquals(2, cust.purchaseOrders.size());
    }
}
