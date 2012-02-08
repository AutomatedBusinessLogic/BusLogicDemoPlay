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

	@Column(name="paid")
	public Boolean paid;

	@Column(name="notes")
	public String notes;

	// Relationships
	@ManyToOne(fetch=FetchType.LAZY)
	public Customer customer;

	@OneToMany(mappedBy="purchaseOrder", cascade={CascadeType.ALL}, fetch=FetchType.EAGER)
	@OrderBy("id desc")
	public List<LineItem> lineItems = new Vector<LineItem>();

	private static final long serialVersionUID = 6324397840327025089L;
}
