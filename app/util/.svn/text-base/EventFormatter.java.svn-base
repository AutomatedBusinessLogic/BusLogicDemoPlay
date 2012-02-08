package util;

import com.autobizlogic.abl.engine.ConstraintFailure;
import com.autobizlogic.abl.event.*;
import com.autobizlogic.abl.rule.*;

public class EventFormatter {

	public static String[] formatEvent(LogicEvent event) {
		
		String eventType = "&lt;unknown&gt;";
		String desc = event.toString();
		switch(event.getEventType()) {
			case AFTER_AGGREGATE: {
				eventType = "Sum";
				LogicAfterAggregateEvent laae = (LogicAfterAggregateEvent)event;
				AbstractAggregateRule rule = laae.getAggregateRule();
				Object oldValue = laae.getOldValue();
				if (oldValue == null)
					oldValue = "<null>";
				String aggregateAttribName = rule.getBeanAttributeName();
				Object newValue = laae.getPersistentBean().get(aggregateAttribName);
				if (newValue == null)
					newValue = "<null>";
				if (rule instanceof CountRule)
					eventType = "Count";
				desc = rule.getLogicMethodName() + " for " + rule.getLogicGroup().getMetaEntity().getEntityName() +
					", old value: " + oldValue + ", new value: " + newValue;
				}
				break;
			case AFTER_FORMULA: {
				eventType = "Formula";
				LogicAfterFormulaEvent lafe = (LogicAfterFormulaEvent)event;
				FormulaRule formula = lafe.getFormulaRule();
				Object oldValue = lafe.getOldValue();
				if (oldValue == null)
					oldValue = "&lt;null>";
				String formulaAttribName = formula.getBeanAttributeName();
				Object newValue = lafe.getPersistentBean().get(formulaAttribName);
				if (newValue == null)
					newValue = "&lt;null>";
				desc = formula.getLogicMethodName() + " for " + formula.getLogicGroup().getMetaEntity().getEntityName() +
					", old value: " + oldValue + ", new value: " + newValue;
				}
				break;
			case BEFORE_ACTION:
				eventType = "Before action";
				break;
			case AFTER_ACTION:
				eventType = "After action";
				break;
			case AFTER_CONSTRAINT: {
				eventType = "Constraint";
				LogicAfterConstraintEvent lace = (LogicAfterConstraintEvent)event;
				ConstraintRule constraint = lace.getConstraintRule();
				ConstraintFailure failure = lace.getFailure();
				desc = constraint.getLogicMethodName() + " for " + constraint.getLogicGroup().getMetaEntity().getEntityName();
				if (failure == null)
					desc += ", OK";
				else
					desc += ", failed: " + failure.getConstraintMessage();
				}
				break;
			case AFTER_PARENT_COPY: {
				eventType = "Parent copy";
				LogicAfterParentCopyEvent lapce = (LogicAfterParentCopyEvent)event;
				ParentCopyRule rule = lapce.getParentCopyRule();
				Object copyValue = lapce.getPersistentBean().get(rule.getChildAttributeName());
				if (copyValue == null)
					copyValue = "&lt;null>";
				desc = "From " + rule.getRoleName() + "." + rule.getParentAttributeName() + " to " + rule.getChildAttributeName() + ", value " + copyValue;
				}
				break;
			case LOGIC_RUNNER: {
				eventType = "Logic event";
				LogicRunnerEvent lre = (LogicRunnerEvent)event;
				if (lre.getLogicRunnerEventType() == LogicRunnerEvent.LogicRunnerEventType.END)
					return null;
				desc = event.getTitle();
				}
				break;
			case BEFORE_COMMIT:
				return null;
				//eventType = "Before commit";
				//break;
			case AFTER_COMMIT:
				return null;
				//eventType = "After commit";
				//break;
			default:
				throw new RuntimeException("Unknown event type:" + event.getEventType());
		}
		
		return new String[]{eventType, desc};

	}
}
