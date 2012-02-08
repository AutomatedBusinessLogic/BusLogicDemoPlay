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
	public Integer getQtyOrdered() { return qtyOrdered; }
	public void setQtyOrdered(Integer qtyOrdered) { this.qtyOrdered = qtyOrdered; }

	@Column(name="product_price")
	public BigDecimal productPrice;
	public BigDecimal getProductPrice() { return productPrice; }
	public void setProductPrice(BigDecimal productPrice) { this.productPrice = productPrice; }

	@Column(name="amount")
	public BigDecimal amount;
	public BigDecimal getAmount() { return amount; }
	public void setAmount(BigDecimal amount) { this.amount = amount; }

	@ManyToOne(fetch=FetchType.LAZY)
	public Product product;
	public Product getProduct() { return product; }
	public void setProduct(Product product) { this.product = product; }

	@ManyToOne(fetch=FetchType.LAZY)
	public PurchaseOrder purchaseOrder;
	public PurchaseOrder getPurchaseOrder() { return purchaseOrder; }
	public void setPurchaseOrder(PurchaseOrder purchaseOrder) { this.purchaseOrder = purchaseOrder; }

	private static final long serialVersionUID = 7475517830099115309L;
}
