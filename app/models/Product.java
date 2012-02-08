package models;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import play.db.jpa.Model;

@Entity
@Table(name="product")
public class Product extends Model {

	@Column(name="name")
	public String name;
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	@Column(name="price")
	public BigDecimal price;
	public BigDecimal getPrice() { return price; }
	public void setPrice(BigDecimal price) {this.price = price; }

	@OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.LAZY, mappedBy="product")
	public Set<LineItem> lineItems = new HashSet<LineItem>();
	public Set<LineItem> getLineItems() { return lineItems; }
	public void setLineItems(Set<LineItem> lineItems) { this.lineItems = lineItems; }

	private static final long serialVersionUID = 3886753153642012675L;
}
