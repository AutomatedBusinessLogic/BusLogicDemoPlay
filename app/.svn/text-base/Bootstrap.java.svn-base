import models.Customer;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;

/**
 * Load the sample data into the database.
 */
@OnApplicationStart
public class Bootstrap extends Job<Void> {

	public void doJob() {
		
        if(Customer.count() == 0) {
        	try {
        		Fixtures.loadModels("initial-data.yml");
        	}
        	catch(Exception ex) {
        		ex.printStackTrace();
        	}
    		System.out.println("Loaded bootstrap data...");
        }
    }	
}
