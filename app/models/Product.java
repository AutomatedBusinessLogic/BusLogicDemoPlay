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

	@Column(name="price")
	public BigDecimal price;

	@OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.LAZY, mappedBy="product")
	public Set<LineItem> lineItems = new HashSet<LineItem>();

	private static final long serialVersionUID = 3886753153642012675L;
}
