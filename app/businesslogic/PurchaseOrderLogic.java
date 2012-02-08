package businesslogic;

import com.autobizlogic.abl.annotations.*;

public class PurchaseOrderLogic {

	@Sum("lineItems.amount")
	public void deriveAmountTotal() { }
}
