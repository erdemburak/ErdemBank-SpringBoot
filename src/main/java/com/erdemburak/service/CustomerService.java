package com.erdemburak.service;

import com.erdemburak.dto.CityDto;
import com.erdemburak.dto.CreateCustomerRequest;
import com.erdemburak.dto.CustomerDto;
import com.erdemburak.model.City;
import com.erdemburak.model.Customer;
import com.erdemburak.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    public CustomerDto createCustomer(CreateCustomerRequest customerRequest){
        Customer customer = new Customer();
        customer.setId(customerRequest.getId());
        customer.setAddress(customer.getAddress());
        customer.setName(customer.getName());
        customer.setDateOfBirth(customer.getDateOfBirth());
        customer.setCity(City.valueOf(customerRequest.getCity().name()));

        customerRepository.save(customer);

        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(customerRequest.getId());
        customerDto.setAddress(customer.getAddress());
        customerDto.setName(customer.getName());
        customerDto.setDateOfBirth(customer.getDateOfBirth());
        customerDto.setCity(CityDto.valueOf(customerRequest.getCity().name()));

        return customerDto;
    }

}
