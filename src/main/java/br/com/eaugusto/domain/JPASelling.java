package br.com.eaugusto.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import javax.persistence.*;

/**
 * Represents a selling transaction in the system.
 * 
 * Mapped to the table <code>tb_selling</code>, this entity records the products sold,
 * the client, the total price, date, and status.
 * Implements {@link IPersistable} for generic DAO compatibility.
 * 
 * Provides logic for adding, removing, and recalculating product totals within a sale.
 * 
 * @see IPersistable
 * @see JPAClient
 * @see JPAProductQuantity
 * @see javax.persistence.Entity
 * @see java.math.BigDecimal
 * @see java.time.Instant
 * 
 * @author Eduardo Augusto (github.com/AsrielDreemurrGM/)
 * @since July 21, 2025
 */
@Entity
@Table(name = "tb_selling")
public class JPASelling implements IPersistable {

	public enum Status {
		STARTED, FINISHED, CANCELLED;

		public static Status getByName(String value) {
			for (Status status : Status.values()) {
				if (status.name().equalsIgnoreCase(value)) {
					return status;
				}
			}
			return null;
		}
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "selling_seq")
	@SequenceGenerator(name = "selling_seq", sequenceName = "seq_selling", initialValue = 1, allocationSize = 1)
	private Long id;

	@Column(name = "code", nullable = false, unique = true)
	private String code;

	@ManyToOne
	@JoinColumn(
		name = "id_client_fk",
		foreignKey = @ForeignKey(name = "fk_selling_client"),
		referencedColumnName = "id",
		nullable = false
	)
	private JPAClient client;

	@OneToMany(mappedBy = "selling", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<JPAProductQuantity> products;

	@Column(name = "total_price", nullable = false)
	private BigDecimal totalPrice;

	@Column(name = "date_sold", nullable = false)
	private Instant dateSold;

	@Enumerated(EnumType.STRING)
	@Column(name = "selling_status", nullable = false)
	private Status sellingStatus;

	public JPASelling() {
		products = new HashSet<>();
	}

	public String getCode() {
		return code;
	}

	public JPAClient getClient() {
		return client;
	}

	public Set<JPAProductQuantity> getProducts() {
		return products;
	}

	public Integer getTotalProductQuantity() {
		return products.stream()
			.reduce(0, (totalQuantity, product) -> totalQuantity + product.getQuantity(), Integer::sum);
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public Instant getDateSold() {
		return dateSold;
	}

	public Status getSellingStatus() {
		return sellingStatus;
	}

	public Long getId() {
		return id;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setClient(JPAClient client) {
		this.client = client;
	}

	public void setDateSold(Instant dateSold) {
		this.dateSold = dateSold;
	}

	public void setSellingStatus(Status sellingStatus) {
		this.sellingStatus = sellingStatus;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public void setProducts(Set<JPAProductQuantity> products) {
		this.products = products;
	}

	/**
	 * Adds a product to the selling with a given quantity.
	 * 
	 * If the product is already present in the sale, it increases its quantity.
	 * Otherwise, it creates a new {@link JPAProductQuantity} entry. Automatically
	 * recalculates the total selling price afterward.
	 * 
	 * @param product  The product to be added.
	 * @param quantity The amount of the product to be added.
	 * @throws UnsupportedOperationException if the selling is already finished.
	 */
	public void addProduct(JPAProduct product, Integer quantity) {
		validateStatus();
		Optional<JPAProductQuantity> optional = findProductQuantityByCode(product.getCode());

		if (optional.isPresent()) {
			JPAProductQuantity productQuantity = optional.get();
			productQuantity.add(quantity);
		} else {
			JPAProductQuantity productQuantity = new JPAProductQuantity();
			productQuantity.setSelling(this);
			productQuantity.setProduct(product);
			productQuantity.add(quantity);
			products.add(productQuantity);
		}
		recalculateTotalSellingPrice();
	}

	/**
	 * Removes a quantity of the given product from the selling.
	 * 
	 * If the amount to remove is less than the existing quantity, it decreases the value.
	 * If equal or more, the product is entirely removed from the set.
	 * Automatically recalculates the total selling price afterward.
	 * 
	 * @param product  The product to be removed.
	 * @param quantity The quantity to be removed.
	 * @throws UnsupportedOperationException if the selling is already finished.
	 */
	public void removeProduct(JPAProduct product, Integer quantity) {
		validateStatus();
		Optional<JPAProductQuantity> optional = findProductQuantityByCode(product.getCode());

		if (optional.isPresent()) {
			JPAProductQuantity productQuantity = optional.get();
			if (productQuantity.getQuantity() > quantity) {
				productQuantity.remove(quantity);
			} else {
				products.remove(productQuantity);
			}
			recalculateTotalSellingPrice();
		}
	}

	/**
	 * Removes all products from the selling and resets the total price to zero.
	 * 
	 * @throws UnsupportedOperationException if the selling is already finished.
	 */
	public void removeAllProducts() {
		validateStatus();
		products.clear();
		totalPrice = BigDecimal.ZERO;
	}

	/**
	 * Recalculates the total selling price based on all current products in the sale.
	 * 
	 * Iterates over all {@link JPAProductQuantity} instances and sums their total prices.
	 */
	public void recalculateTotalSellingPrice() {
		BigDecimal newTotalPrice = BigDecimal.ZERO;
		for (JPAProductQuantity product : this.products) {
			newTotalPrice = newTotalPrice.add(product.getTotalPrice());
		}
		this.totalPrice = newTotalPrice;
	}


	/**
	 * Finds a product within the selling by its code.
	 * 
	 * @param code The product code to search for.
	 * @return An {@link Optional} containing the found {@link JPAProductQuantity}, if any.
	 */
	private Optional<JPAProductQuantity> findProductQuantityByCode(String code) {
		return products.stream()
			.filter(pq -> pq.getProduct().getCode().equals(code))
			.findAny();
	}

	/**
	 * Validates if the selling is in a modifiable state.
	 * 
	 * Throws {@link UnsupportedOperationException} if the selling status is {@code FINISHED}.
	 * Used before performing any operation that modifies product quantities.
	 */
	private void validateStatus() {
		if (this.sellingStatus == Status.FINISHED) {
			throw new UnsupportedOperationException("CANNOT MODIFY A FINISHED SELLING");
		}
	}
}
