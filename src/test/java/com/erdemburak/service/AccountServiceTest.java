package com.erdemburak.service;

import com.erdemburak.dto.AccountDto;
import com.erdemburak.dto.AccountDtoConverter;
import com.erdemburak.dto.CreateAccountRequest;
import com.erdemburak.model.Account;
import com.erdemburak.model.City;
import com.erdemburak.model.Currency;
import com.erdemburak.model.Customer;
import com.erdemburak.repository.AccountRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class AccountServiceTest {

    private AccountService accountService;

    private AccountRepository accountRepository;
    private CustomerService customerService;
    private AccountDtoConverter accountDtoConverter;

    @Before
    public void setUp() throws Exception {
        accountRepository = Mockito.mock(AccountRepository.class);
        customerService = Mockito.mock(CustomerService.class);
        accountDtoConverter = Mockito.mock(AccountDtoConverter.class);

        accountService = new AccountService(accountRepository,customerService,accountDtoConverter);
    }

    @Test
    public void whenCreateAccountCalledWithValidRequest_itShouldReturnValidAccountDto(){
        CreateAccountRequest createAccountRequest = new CreateAccountRequest("1001");
        createAccountRequest.setCustomerId("12345677");
        createAccountRequest.setBalance(1000.0);
        createAccountRequest.setCity(City.ANKARA);
        createAccountRequest.setCurrency(Currency.TL);

        Customer customer = Customer.builder()
                .id("12345677")
                .address("Adress")
                .city(City.ISTANBUL)
                .dateOfBirth(1995)
                .name("Burak")
                .build();

        Account account = Account.builder()
                .id(createAccountRequest.getId())
                .balance(createAccountRequest.getBalance())
                .currency(createAccountRequest.getCurrency())
                .customerId(createAccountRequest.getCustomerId())
                .city(createAccountRequest.getCity())
                .build();

        AccountDto accountDto = AccountDto.builder()
                .id("1001")
                .customerId("12345677")
                .currency(Currency.TL)
                .balance(1000.0)
                .build();


        Mockito.when(customerService.getCustomerById("12345677")).thenReturn(customer);
        Mockito.when(accountRepository.save(account)).thenReturn(account);
        Mockito.when(accountDtoConverter.convert(account)).thenReturn(accountDto);

        AccountDto result = accountService.createAccount(createAccountRequest);

        Assert.assertEquals(result, accountDto);
        Mockito.verify(customerService).getCustomerById("12345677");
        Mockito.verify(accountRepository).save(account);
        Mockito.verify(accountDtoConverter).convert(account);
    }

    @Test(expected = RuntimeException.class)
    public void whenCreateAccountCalledWithNonExistCustomer_itShouldReturnEmptyAccountDto(){
        CreateAccountRequest createAccountRequest = new CreateAccountRequest("1001");
        createAccountRequest.setCustomerId("12345677");
        createAccountRequest.setBalance(1000.0);
        createAccountRequest.setCity(City.ANKARA);
        createAccountRequest.setCurrency(Currency.TL);

        Mockito.when(customerService.getCustomerById("12345677")).thenReturn(Customer.builder().build());

        AccountDto expectedAccountDto = AccountDto.builder().build();
        AccountDto result = accountService.createAccount(createAccountRequest);

        Assert.assertEquals(result,expectedAccountDto);
        Mockito.verifyNoInteractions(accountRepository);
        Mockito.verifyNoInteractions(accountDtoConverter);
    }

    @Test(expected = RuntimeException.class)
    public void whenCreateAccountCalledWithCustomerWithOutId_itShouldReturnEmptyAccountDto(){
        CreateAccountRequest createAccountRequest = new CreateAccountRequest("1001");
        createAccountRequest.setCustomerId("12345677");
        createAccountRequest.setBalance(1000.0);
        createAccountRequest.setCity(City.ANKARA);
        createAccountRequest.setCurrency(Currency.TL);

        Mockito.when(customerService.getCustomerById("12345677")).thenReturn(Customer.builder()
                .id(" ")
                .build());

        AccountDto expectedAccountDto = AccountDto.builder().build();
        AccountDto result = accountService.createAccount(createAccountRequest);

        Assert.assertEquals(result,expectedAccountDto);
        Mockito.verifyNoInteractions(accountRepository);
        Mockito.verifyNoInteractions(accountDtoConverter);
    }

    @Test(expected = RuntimeException.class)
    public void whenCreateAccountCalledAndRepositoryThrewException_itShouldThrowException(){
        CreateAccountRequest createAccountRequest = new CreateAccountRequest("1001");
        createAccountRequest.setCustomerId("12345677");
        createAccountRequest.setBalance(1000.0);
        createAccountRequest.setCity(City.ANKARA);
        createAccountRequest.setCurrency(Currency.TL);

        Customer customer = Customer.builder()
                .id("12345677")
                .address("Adress")
                .city(City.ISTANBUL)
                .dateOfBirth(1995)
                .name("Burak")
                .build();

        Account account = Account.builder()
                .id(createAccountRequest.getId())
                .balance(createAccountRequest.getBalance())
                .currency(createAccountRequest.getCurrency())
                .customerId(createAccountRequest.getCustomerId())
                .city(createAccountRequest.getCity())
                .build();


        Mockito.when(customerService.getCustomerById("12345677")).thenReturn(customer);
        Mockito.when(accountRepository.save(account)).thenThrow(new RuntimeException("bla bla"));

        accountService.createAccount(createAccountRequest);

        Mockito.verify(customerService).getCustomerById("12345677");
        Mockito.verify(accountRepository).save(account);
        Mockito.verifyNoInteractions(accountDtoConverter);
    }

}