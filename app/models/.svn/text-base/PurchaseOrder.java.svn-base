package models;

import java.math.BigDecimal;
import java.util.List;
import java.util.Vector;

import javax.persistence.*;

import play.db.jpa.Model;

@Entity
@Table(name="purchaseorder")
public class PurchaseOrder extends Model {

	@Column(name="amount_total")
	public BigDecimal amountTotal;
	public BigDecimal getAmountTotal() { return amountTotal; }
	public void setAmountTotal(BigDecimal amountTotal) { this.amountTotal = amountTotal; }

	@Column(name="paid")
	public Boolean paid;
	public Boolean getPaid() { return paid; }
	public void setPaid(Boolean paid) { this.paid = paid; }

	@Column(name="notes")
	public String notes;
	public String getNotes() { return notes; }
	public void setNotes(String notes) { this.notes = notes; }

	// Relationships
	@ManyToOne(fetch=FetchType.LAZY)
	public Customer customer;
	public Customer getCustomer() { return customer; }
	public void setCustomer(Customer customer) { this.customer = customer; }

	@OneToMany(mappedBy="purchaseOrder", cascade={CascadeType.ALL}, fetch=FetchType.EAGER)
	@OrderBy("id desc")
	public List<LineItem> lineItems = new Vector<LineItem>();
	public List<LineItem> getLineItems() { return lineItems; }
	public void setLineItems(List<LineItem> lineItems) { this.lineItems = lineItems; }

	private static final long serialVersionUID = 6324397840327025089L;
}
