package controllers;

import play.db.jpa.JPA;
import play.mvc.*;
import play.mvc.Scope.Flash;
import util.NumberFormat;

import java.math.BigDecimal;
import java.util.*;

import javax.persistence.RollbackException;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.autobizlogic.abl.engine.ConstraintException;
import com.autobizlogic.abl.logic.LogicContext;

import models.*;

/**
 * The sole controller for this demo. This is probably not a great example of how to write
 * a controller for the Play framework, but it does work.
 */
public class Application extends Controller {

	/**
	 * Handle most user interactions
	 * @param custId The id of the customer. Can be null, which is why we don't bind it
	 * @param verb What to do, can be insert, update or delete
	 * @param type The type on which to act, can be Customer, Order or LineItem
	 * @param id The id of the object on which to act
	 * @param att The attribute on which to act
	 * @param value The value to which to set it
	 */
    public static void index(String custId, String verb, String type, String id, String att, String value) {
    	
		if (verb == null || verb.trim().length() == 0) {
			showPage(custId, null);
			return;
		}
		
		if ("update".equals(verb))
			processUpdate(type, id, att, value);
		else if ("insert".equals(verb))
			processInsert(custId, type, id, att, value);
		else if ("delete".equals(verb))
			processDelete(type, id);
		
		// We want to know if there is a constraint violation on commit, that's why we commit here
		// rather than let Play do it for us. There may be a better way of doing this, but I don't know
		// Play well enough to determine that.
		String errMsg = null;
		try {
			JPA.em().getTransaction().commit();
		}
		catch(RollbackException ex) {
			if (ex.getCause() != null && ex.getCause().getCause() != null && (ex.getCause().getCause() instanceof ConstraintException)) {
				ConstraintException cex = (ConstraintException)ex.getCause().getCause();
				errMsg = cex.getMessage() + " - your last change has been rolled back.";
			}
		}
		
		showPage(custId, errMsg);
    }
    
    /**
     * Invoked whenever the user toggles the "Show transaction summary" checkbox
     * @param custId The id of the current customer
     */
    public static void toggleTxSummary(String custId) {
		session.put("showTxSummary", params.get("showTxSummary"));

		showPage(custId, null);
    }
    
	private static void processUpdate(String type, String id, String att, String value) {
		if (type == null || type.trim().length() == 0)
			return;
		if ("Order".equals(type)) {
			PurchaseOrder order = PurchaseOrder.findById(new Long(id));
			if ("paid".equals(att)) {
				Boolean oldValue = order.paid;
				if (oldValue == null)
					oldValue = Boolean.FALSE;
				order.paid = ! oldValue;
				if (oldValue)
					setCurrentUseCaseName("Order unpaid");
				else
					setCurrentUseCaseName("Order paid");
			}
			else if ("customer".equals(att)) {
				if (value == null || value.startsWith("- ")) // Do nothing if somehow the "- select a customer -" item was selected
					return;
				Customer customer = Customer.findById(new Long(value));
				order.customer = customer;
				Flash.current().success("The order has been reassigned to customer " + customer.name);
				setCurrentUseCaseName("Order reassigned");
			}
			else if ("notes".equals(att)) {
				order.notes = value;
				setCurrentUseCaseName("Order notes updated");
			}
			order.save();
		}
		else if ("Customer".equals(type)) {
			Customer customer = Customer.findById(new Long(id));
			if ("creditLimit".equals(att)) {
				BigDecimal val = NumberFormat.parseMoney(value);
				customer.creditLimit = val;
				setCurrentUseCaseName("Customer credit limit updated");
			}
			customer.save();
		}
		else if ("LineItem".equals(type)) {
			LineItem lineitem = LineItem.findById(new Long(id));
			if ("quantity".equals(att)) {
				Integer val = NumberFormat.parseNumber(value);
				lineitem.qtyOrdered = val;
				setCurrentUseCaseName("Line Item quantity updated");
			}
			else if ("unitPrice".equals(att)) {
				BigDecimal val = NumberFormat.parseMoney(value);
				lineitem.productPrice = val;
				setCurrentUseCaseName("Line Item unit price updated");
			}
			else if ("product".equals(att)) {
				Product product = Product.findById(new Long(value));
				lineitem.product = product;
				setCurrentUseCaseName("Line Item product changed");
			}
			lineitem.save();
		}
	}

	private static void processInsert(String custId, String type, String id, String att, String value) {
		if (type == null || type.trim().length() == 0)
			return;
		if ("LineItem".equals(type)) {
			PurchaseOrder order = PurchaseOrder.findById(new Long(id));
			Product product = Product.findById(new Long(1));
			LineItem newItem = new LineItem();
			newItem.purchaseOrder = order;
			order.lineItems.add(newItem);
			newItem.product = product;
			product.lineItems.add(newItem);
			newItem.qtyOrdered = 1;
			setCurrentUseCaseName("New Line Item created");
			newItem.save();
		}
		else if ("Order".equals(type)) {
			Customer customer = Customer.findById(new Long(custId));
			PurchaseOrder newOrder = new PurchaseOrder();
			newOrder.customer = customer;
			newOrder.paid = Boolean.FALSE;
			newOrder.notes = "";
			setCurrentUseCaseName("New Order created");
			newOrder.save();
		}
	}

	private static void processDelete(String type, String id) {
		if ("LineItem".equals(type)) {
			LineItem lineitem = LineItem.findById(new Long(id));
			lineitem.purchaseOrder.lineItems.remove(lineitem);
			lineitem.delete();
			setCurrentUseCaseName("Line Item deleted");
		}
		else if ("Order".equals(type)) {
			PurchaseOrder order = PurchaseOrder.findById(new Long(id));
			order.customer.purchaseOrders.remove(order);
			order.delete();
			setCurrentUseCaseName("Order deleted");
		}
	}
    
    private static void showPage(String custId, String errMsg) {
    	// Obviously this is inefficient -- the list of customer and products never changes,
    	// so we could cache it.
    	List<Customer> customers = Customer.find("order by name").fetch();
    	List<Product> products = Product.find("order by name").fetch();
    	Customer currentCustomer;
    	if (custId == null || custId.trim().length() == 0)
    		currentCustomer = customers.get(0);
    	else
    		currentCustomer = Customer.findById(new Long(custId));
        renderTemplate("Application/index.html", customers, currentCustomer, products, errMsg);
    }
    
	private static void setCurrentUseCaseName(String s) {
		Session session = (Session)JPA.em().getDelegate();
		Transaction tx = session.getTransaction();
		LogicContext.setCurrentUseCaseName(session, tx, s);
	}
}