package service;

import repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import model.Customer;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public List<Customer> getCustomers(){
        return customerRepository.findAll();
    }

    public boolean saveCustomer(Customer customer){
        return customerRepository.save(customer);
    }

    public List<Customer> searchCustomersByText(String text){
        return customerRepository.findAll().stream()
                .filter(customer -> customer.getFirstName().contains(text))
                .collect(Collectors.toList());
    }

    public List<Customer> getCustomersBySignedUpMonth(int month){
        return customerRepository.findAll().stream()
                .filter(customer -> customer.getSignedUpDate().getMonthValue()==month)
                .collect(Collectors.toList());
    }

    public String getCustomerNameByCustomerId(Long customerId){
        return customerRepository.findById(customerId).get().getFirstName();
    }
}
