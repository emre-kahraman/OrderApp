import repository.CustomerRepository;
import model.Customer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import service.CustomerService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTests {

    @InjectMocks
    CustomerService customerService;

    @Mock
    CustomerRepository customerRepository;

    @Test
    public void itShouldSaveCustomer(){
        Customer customer = Customer.builder()
                .id(1l)
                .firstName("test")
                .lastName("test")
                .address("test")
                .signedUpDate(LocalDate.now()).build();

        when(customerRepository.save(customer)).thenReturn(true);

        boolean result = customerService.saveCustomer(customer);

        assertEquals(result, true);
    }

    @Test
    public void itShouldGetAllCustomers(){
        Customer customer = Customer.builder()
                .id(1l)
                .firstName("test")
                .lastName("test")
                .address("test")
                .signedUpDate(LocalDate.now()).build();
        Customer customer2 = Customer.builder()
                .id(2l)
                .firstName("test2")
                .lastName("test2")
                .address("test2")
                .signedUpDate(LocalDate.now()).build();

        when(customerRepository.findAll()).thenReturn(List.of(customer, customer2));

        List<Customer> customerList = customerService.getCustomers();

        assertEquals(customerList.size(), 2);
        assertEquals(customerList.get(0), customer);
        assertEquals(customerList.get(1), customer2);
    }

    @Test
    public void itShouldSearchCustomersByText(){
        Customer customer = Customer.builder()
                .id(1l)
                .firstName("test")
                .lastName("test")
                .address("test")
                .signedUpDate(LocalDate.now()).build();
        Customer customer2 = Customer.builder()
                .id(2l)
                .firstName("test2")
                .lastName("test2")
                .address("test2")
                .signedUpDate(LocalDate.now()).build();

        when(customerRepository.findAll()).thenReturn(List.of(customer, customer2));

        List<Customer> customerList = customerService.searchCustomersByText("test");

        assertEquals(customerList.size(), 2);
        assertEquals(customerList.get(0), customer);
        assertEquals(customerList.get(1), customer2);
    }

    @Test
    public void itShouldGetCustomersBySignedUpMonth(){
        Customer customer = Customer.builder()
                .id(1l)
                .firstName("test")
                .lastName("test")
                .address("test")
                .signedUpDate(LocalDate.now()).build();
        Customer customer2 = Customer.builder()
                .id(2l)
                .firstName("test2")
                .lastName("test2")
                .address("test2")
                .signedUpDate(LocalDate.now()).build();

        when(customerRepository.findAll()).thenReturn(List.of(customer, customer2));

        List<Customer> customerList = customerService.getCustomersBySignedUpMonth(2);

        assertEquals(customerList.size(), 2);
        assertEquals(customerList.get(0), customer);
        assertEquals(customerList.get(1), customer2);
    }

    @Test
    public void itShouldGetCustomerNameByCustomerId(){
        Customer customer = Customer.builder()
                .id(1l)
                .firstName("test")
                .lastName("test")
                .address("test")
                .signedUpDate(LocalDate.now()).build();

        when(customerRepository.findById(1l)).thenReturn(Optional.of(customer));

        String customerName = customerService.getCustomerNameByCustomerId(1l);

        assertEquals(customerName, customer.getFirstName());
    }
}
