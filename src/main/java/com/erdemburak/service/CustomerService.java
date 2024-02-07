package com.erdemburak.service;

import com.erdemburak.dto.CityDto;
import com.erdemburak.dto.CreateCustomerRequest;
import com.erdemburak.dto.CustomerDto;
import com.erdemburak.dto.CustomerDtoConverter;
import com.erdemburak.model.City;
import com.erdemburak.model.Customer;
import com.erdemburak.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerDtoConverter customerDtoConverter;

    public CustomerService(CustomerRepository customerRepository, CustomerDtoConverter customerDtoConverter) {
        this.customerRepository = customerRepository;
        this.customerDtoConverter = customerDtoConverter;
    }


    public CustomerDto createCustomer(CreateCustomerRequest customerRequest){
        Customer customer = new Customer();
        customer.setId(customerRequest.getId());
        customer.setAddress(customerRequest.getAddress());
        customer.setName(customerRequest.getName());
        customer.setDateOfBirth(customerRequest.getDateOfBirth());
        customer.setCity(City.valueOf(customerRequest.getCity().name()));

        customerRepository.save(customer);

        return customerDtoConverter.convert(customer);
    }

    public List<CustomerDto> getAllCustomers() {
        List<Customer> customerList = customerRepository.findAll();

        List<CustomerDto> customerDtoList = new ArrayList<>();

        for(Customer customer: customerList){
            customerDtoList.add(customerDtoConverter.convert(customer));
        }
        return customerDtoList;
    }
}
