package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MoneyTest {
	Currency SEK, DKK, NOK, EUR;
	Money SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
		SEK100 = new Money(10000, SEK);
		EUR10 = new Money(1000, EUR);
		SEK200 = new Money(20000, SEK);
		EUR20 = new Money(2000, EUR);
		SEK0 = new Money(0, SEK);
		EUR0 = new Money(0, EUR);
		SEKn100 = new Money(-10000, SEK);
	}

	@Test
	public void testGetAmount() {
		 assertEquals(Integer.valueOf(10000), SEK100.getAmount());
	     assertEquals(Integer.valueOf(1000), EUR10.getAmount());
	}

	@Test
	public void testGetCurrency() {
		assertEquals(SEK, SEK100.getCurrency());
        assertEquals(EUR, EUR10.getCurrency());
	}

	@Test
	public void testToString() {
		assertEquals("100.0 SEK", SEK100.toString());
        assertEquals("10.0 EUR", EUR10.toString());
	}

	@Test
	public void testGlobalValue() {
        assertEquals(Integer.valueOf(3000), EUR20.universalValue());
	}

	@Test
	public void testEqualsMoney() {
		Money anotherSEK100 = new Money(10000, SEK);
    
        assertTrue(SEK100.equals(anotherSEK100));
        assertFalse(SEK100.equals(EUR10));
	}

	@Test
	public void testAdd() {
	    Money result = SEK100.add(EUR10);
	    assertEquals(Integer.valueOf(20000), result.getAmount());
	    assertEquals(SEK, result.getCurrency());
	}

	@Test
	public void testSub() {
		Money result = SEK100.sub(EUR10);
        assertEquals(Integer.valueOf(0), result.getAmount());
        assertEquals(SEK, result.getCurrency());
	}

	@Test
	public void testIsZero() {
		assertTrue(SEK0.isZero());
        assertFalse(EUR10.isZero());
	}

	@Test
	public void testNegate() {
		Money negatedSEK100 = SEK100.negate();
        assertEquals(Integer.valueOf(-10000), negatedSEK100.getAmount());
        assertEquals(SEK, negatedSEK100.getCurrency());
	}

	@Test
	public void testCompareTo() {
		Money smallerSEK = new Money(5000, SEK);
        Money largerSEK = new Money(15000, SEK);

        assertEquals(0, SEK100.compareTo(SEK100));
        assertTrue(SEK100.compareTo(smallerSEK) > 0);
        assertTrue(SEK100.compareTo(largerSEK) < 0);

        // Testowanie z inną walutą
        Money smallerEUR = new Money(500, EUR);
        Money largerEUR = new Money(1500, EUR);

        assertTrue(EUR10.compareTo(smallerEUR) > 0);
        assertTrue(EUR10.compareTo(largerEUR) < 0);
	}
}
