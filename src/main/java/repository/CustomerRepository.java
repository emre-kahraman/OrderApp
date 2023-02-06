package repository;

import model.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerRepository implements ListRepository<Customer>{

    private static List<Customer> customers;

    public CustomerRepository(){
        customers = new ArrayList<>();
    }

    @Override
    public boolean save(Customer customer){
        return customers.add(customer);
    }

    @Override
    public boolean save(List<Customer> customerList){
        return customers.addAll(customerList);
    }

    @Override
    public List<Customer> findAll(){
        return customers;
    }

    @Override
    public Optional<Customer> findById(Long id){
        return customers.stream().filter(customer -> customer.getId()==id).findFirst();
    }
}
