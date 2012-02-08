package businesslogic;

import com.autobizlogic.abl.annotations.Constraint;
import com.autobizlogic.abl.annotations.LogicContextObject;
import com.autobizlogic.abl.logic.LogicContext;

/**
 * A trivial superclass for logic objects, demonstrating how logic can be inherited.
 */
public class LogicObject {

	@LogicContextObject
	protected LogicContext context;
	
	@Constraint
	public void emptyConstraint() {
		System.out.println("emptyConstraint invoked for " + getClass());
	}

}
