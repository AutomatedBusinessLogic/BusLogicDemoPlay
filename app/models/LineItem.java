package models;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.db.jpa.Model;

@Entity
@Table(name="lineitem")
public class LineItem extends Model {

	@Column(name="qty_ordered")
	public Integer qtyOrdered;

	@Column(name="product_price")
	public BigDecimal productPrice;

	@Column(name="amount")
	public BigDecimal amount;

	@ManyToOne(fetch=FetchType.LAZY)
	public Product product;

	@ManyToOne(fetch=FetchType.LAZY)
	public PurchaseOrder purchaseOrder;

	private static final long serialVersionUID = 7475517830099115309L;
}
