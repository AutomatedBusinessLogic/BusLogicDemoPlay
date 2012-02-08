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

	@Column(name="balance")
	public BigDecimal balance;

	@Column(name="credit_limit")
	public BigDecimal creditLimit;

	@OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.LAZY, mappedBy="customer")
	@OrderBy("id desc")
	public List<PurchaseOrder> purchaseOrders = new Vector<PurchaseOrder>();

	public Customer(String name) {
		this.name = name;
	}

	private static final long serialVersionUID = -8201111873383893170L;
}
