package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BankTest {
	Currency SEK, DKK;
	Bank SweBank, Nordea, DanskeBank;
	
	@Before
	public void setUp() throws Exception {
		DKK = new Currency("DKK", 0.20);
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		Nordea = new Bank("Nordea", SEK);
		DanskeBank = new Bank("DanskeBank", DKK);
		SweBank.openAccount("Ulrika");
		SweBank.openAccount("Bob");
		Nordea.openAccount("Bob");
		DanskeBank.openAccount("Gertrud");
	}

	@Test
	public void testGetName() {
		assertEquals("SweBank", SweBank.getName());
	}

	@Test
	public void testGetCurrency() {
		assertEquals(SEK, SweBank.getCurrency());
	}

	@Test
	public void testOpenAccount() throws AccountExistsException, AccountDoesNotExistException {
		SweBank.openAccount("Charlie"); // Dodajemy nowe konto
        assertNotNull(SweBank.getBalance("Charlie")); // Sprawdzamy, czy konto istnieje
	}

	@Test
	public void testDeposit() throws AccountDoesNotExistException {
		Money depositAmount = new Money(100, SEK);
        SweBank.deposit("Ulrika", depositAmount);
        assertEquals(depositAmount.getAmount(), SweBank.getBalance("Ulrika"));
	}

	@Test
	public void testWithdraw() throws AccountDoesNotExistException {
		Money withdrawAmount = new Money(50, SEK);
		SweBank.withdraw("Ulrika", withdrawAmount);
	}
	
	@Test
	public void testGetBalance() throws AccountDoesNotExistException {
		assertEquals(Integer.valueOf(0), SweBank.getBalance("Bob"));
	}
	
	@Test
	public void testTransfer() throws AccountDoesNotExistException, AccountExistsException {
		 Currency SEK = new Currency("SEK", 0.15);
		    Bank bank1 = new Bank("Bank1", SEK);
		    Bank bank2 = new Bank("Bank2", SEK);

		    // Otwórz konta w bankach
		    bank1.openAccount("Account1");
		    bank2.openAccount("Account2");

		    // Wpłać pewną kwotę na koncie w banku 1
		    Money depositAmount = new Money(100, SEK);
		    bank1.deposit("Account1", depositAmount);

		    // Przeprowadź transfer z banku 1 do banku 2
		    Money transferAmount = new Money(50, SEK);
		    bank1.transfer("Account1", bank2, "Account2", transferAmount);

		    // Sprawdź, czy kwota została poprawnie przetransferowana
		    assertEquals(50, bank1.getBalance("Account1").intValue());
		    assertEquals(50, bank2.getBalance("Account2").intValue());
	}
	
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		Money timedPaymentAmount = new Money(20, SEK);
	    SweBank.addTimedPayment("Ulrika", "payment1", 2, 1, timedPaymentAmount, Nordea, "Bob");
	    SweBank.tick();
	    assertEquals(timedPaymentAmount.getAmount(), Nordea.getBalance("Bob"));
	}
}
