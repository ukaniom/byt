package b_Money;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AccountTest {
	Currency SEK, DKK;
	Bank Nordea;
	Bank DanskeBank;
	Bank SweBank;
	Account testAccount;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		SweBank.openAccount("Alice");
		testAccount = new Account("Hans", SEK);
		testAccount.deposit(new Money(10000000, SEK));

		SweBank.deposit("Alice", new Money(1000000, SEK));
	}
	
	@Test
	public void testAddRemoveTimedPayment() {
		testAccount.addTimedPayment("payment1", 2, 1, new Money(500, SEK), SweBank, "Alice");
        assertTrue(testAccount.timedPaymentExists("payment1"));

        testAccount.removeTimedPayment("payment1");
        assertFalse(testAccount.timedPaymentExists("payment1"));
	}
	
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		 testAccount.addTimedPayment("payment1", 2, 1, new Money(500, SEK), SweBank, "Alice");
		 SweBank.tick();
		 assertEquals(Integer.valueOf(1000000), SweBank.getBalance("Alice"));
	}

	@Test
	public void testAddWithdraw() {
		testAccount.addTimedPayment("payment1", 2, 1, new Money(500, SEK), SweBank, "Alice");
        testAccount.withdraw(new Money(1000, SEK));
        assertEquals(Integer.valueOf(9999000), testAccount.getBalance().getAmount());
	}
	
	@Test
	public void testGetBalance() {
		assertEquals(Integer.valueOf(10000000), testAccount.getBalance().getAmount());
	}
}
