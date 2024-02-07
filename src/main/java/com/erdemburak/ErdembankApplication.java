package com.erdemburak;

import com.erdemburak.model.Account;
import com.erdemburak.model.City;
import com.erdemburak.model.Customer;
import com.erdemburak.repository.AccountRepository;
import com.erdemburak.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class ErdembankApplication implements CommandLineRunner {

	private final AccountRepository accountRepository;
	private final CustomerRepository customerRepository;

	public ErdembankApplication(AccountRepository accountRepository, CustomerRepository customerRepository) {
		this.accountRepository = accountRepository;
		this.customerRepository = customerRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(ErdembankApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Customer c1 = Customer.builder()
				.id("1234567890")
				.name("Burak Erdem")
				.city(City.ISTANBUL)
				.address("Ev address")
				.dateOfBirth(1995)
				.build();
		Customer c2 = Customer.builder()
				.id("3214567890")
				.name("Armagan Akyol")
				.city(City.ISTANBUL)
				.address("Is address")
				.dateOfBirth(1998)
				.build();
		Customer c3 = Customer.builder()
				.id("1233217890")
				.name("Armovski Erdem")
				.city(City.ANKARA)
				.address("Ev address")
				.dateOfBirth(1995)
				.build();

		customerRepository.saveAll(Arrays.asList(c1,c2,c3));

		Account a1 = Account.builder()
				.id("100")
				.customerId("1234567890")
				.city(City.ISTANBUL)
				.balance(1320.0)
				.build();
		Account a2 = Account.builder()
				.id("101")
				.customerId("3214567890")
				.city(City.ISTANBUL)
				.balance(13020.0)
				.build();
		Account a3 = Account.builder()
				.id("102")
				.customerId("1233217890")
				.city(City.ANKARA)
				.balance(10320.0)
				.build();

		accountRepository.saveAll(Arrays.asList(a1,a2,a3));
	}
}
