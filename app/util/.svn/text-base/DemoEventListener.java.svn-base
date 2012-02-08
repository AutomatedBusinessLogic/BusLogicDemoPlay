package util;

import java.util.List;
import java.util.Vector;

import com.autobizlogic.abl.event.LogicEvent;

/**
 * A very simplistic event listener that gets notified by ABL of any business logic activity.
 * An instance of this class is registered with ABL using the ABLConfig.properties file.
 * 
 * Please do NOT use this as an example for real-world software. The main purpose for
 * this class was maximum simplicity.
 */
public class DemoEventListener implements com.autobizlogic.abl.event.LogicListener {
	
	public static DemoEventListener instance;
	public List<LogicEvent> events = new Vector<LogicEvent>();
	
	public static DemoEventListener getInstance() {
		return instance;
	}
	
	public DemoEventListener() {
		super();
		instance = this;
	}

	@Override
	public void onLogicEvent(LogicEvent event) {
		events.add(event);
	}

	public void resetEvents() {
		events.clear();
	}
}
