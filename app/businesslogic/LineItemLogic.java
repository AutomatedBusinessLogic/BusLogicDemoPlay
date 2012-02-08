package businesslogic;

import com.autobizlogic.abl.annotations.*;

public class LineItemLogic {
	
	@Formula("productPrice * qtyOrdered")
	public void deriveAmount() { }

	@ParentCopy("product.price")
	public void deriveProductPrice() { }
}
