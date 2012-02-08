package businesslogic;

import com.autobizlogic.abl.annotations.*;

public class CustomerLogic extends LogicObject {

	@Constraint(
			value = "balance <= creditLimit",
			errorMessage = "Customer {name} has a balance of ${balance} which exceeds the credit limit of ${creditLimit}"
			)
	public void constraintCreditLimit() { }
	
	@Sum("purchaseOrders.amountTotal where paid = false")
	public void deriveBalance() { }
}
