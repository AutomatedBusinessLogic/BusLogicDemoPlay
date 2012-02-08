package models;

import java.math.BigDecimal;
import java.util.*;
import javax.persistence.*;
 
import play.db.jpa.*;

@Entity
@Table(name="customer")
public class Customer extends Model {

	@Column(name="name", unique=true, nullable=false)
	public String name;
	public String getName() { return name; }
	public void setName(String s) { name = s; }

	@Column(name="balance")
	public BigDecimal balance;
	public BigDecimal getBalance() { return balance; }
	public void setBalance(BigDecimal bd) { balance = bd; }

	@Column(name="credit_limit")
	public BigDecimal creditLimit;
	public BigDecimal getCreditLimit() { return creditLimit; }
	public void setCreditLimit(BigDecimal bd) { creditLimit = bd; }

	@OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.LAZY, mappedBy="customer")
	@OrderBy("id desc")
	public List<PurchaseOrder> purchaseOrders = new Vector<PurchaseOrder>();
	public List<PurchaseOrder> getPurchaseOrders() { return purchaseOrders; }
	public void setPurchaseOrders(List<PurchaseOrder> l) { purchaseOrders = l; }

	public Customer(String name) {
		this.name = name;
	}

	private static final long serialVersionUID = -8201111873383893170L;
}
