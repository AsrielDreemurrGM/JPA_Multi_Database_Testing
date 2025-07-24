package br.com.eaugusto.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link JPAProductQuantity}.
 * 
 * Verifies quantity increment and decrement, total price updates,
 * exception when removing more than available, and basic getters/setters.
 * 
 * @author Eduardo Augusto (github.com/AsrielDreemurrGM/)
 * @since July 22, 2025
 */
public class JPAProductQuantityTest {

	private JPAProductQuantity productQuantity;
	private JPAProduct product;

	@BeforeEach
	void setUp() {
		product = new JPAProduct();
		product.setName("Test Product");
		product.setCode("TEST-123");
		product.setPrice(BigDecimal.valueOf(10.00));

		productQuantity = new JPAProductQuantity();
		productQuantity.setProduct(product);
	}

	@Test
	void testIncrementQuantityAndUpdateTotalPrice() {
		productQuantity.add(3);
		assertEquals(3, productQuantity.getQuantity());
		assertEquals(BigDecimal.valueOf(30.00), productQuantity.getTotalPrice());
	}

	@Test
	void testDecrementQuantityAndUpdateTotalPrice() {
		productQuantity.add(5);
		productQuantity.remove(2);
		assertEquals(3, productQuantity.getQuantity());
		assertEquals(BigDecimal.valueOf(30.00), productQuantity.getTotalPrice());
	}

	@Test
	void testShouldThrowWhenRemovingTooMuch() {
		productQuantity.add(2);
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> productQuantity.remove(5));
		assertEquals("Cannot remove more than existing quantity.", exception.getMessage());
	}

	@Test
	void testGettersAndSetters() {
		productQuantity.setQuantity(7);
		productQuantity.setTotalPrice(BigDecimal.valueOf(70.00));
		productQuantity.setId(99L);

		JPASelling selling = new JPASelling();
		productQuantity.setSelling(selling);

		assertEquals(7, productQuantity.getQuantity());
		assertEquals(BigDecimal.valueOf(70.00), productQuantity.getTotalPrice());
		assertEquals(99L, productQuantity.getId());
		assertEquals(selling, productQuantity.getSelling());
		assertEquals(product.getId(), productQuantity.getProduct().getId());
	}
}
