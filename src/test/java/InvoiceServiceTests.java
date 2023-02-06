import repository.InvoiceRepository;
import model.Company;
import model.Customer;
import model.Invoice;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import service.CustomerService;
import service.InvoiceService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class InvoiceServiceTests {

    @InjectMocks
    InvoiceService invoiceService;

    @Mock
    InvoiceRepository invoiceRepository;

    @Mock
    CustomerService customerService;

    @Test
    public void itShouldSaveInvoice(){
        Invoice invoice = Invoice.builder()
                .id(1l)
                .company(Company.builder().name("testCompany").category("testCategory").build())
                .price(BigDecimal.valueOf(10))
                .date(LocalDate.now()).build();

        when(invoiceRepository.save(1l, invoice)).thenReturn(true);

        boolean result = invoiceService.saveInvoice(1l, invoice);

        assertEquals(result, true);
    }

    @Test
    public void itShouldGetInvoices(){
        Invoice invoice = Invoice.builder()
                .id(1l)
                .company(Company.builder().name("testCompany").category("testCategory").build())
                .price(BigDecimal.valueOf(10))
                .date(LocalDate.now()).build();
        Invoice invoice2 = Invoice.builder()
                .id(2l)
                .company(Company.builder().name("testCompany").category("testCategory").build())
                .price(BigDecimal.valueOf(5))
                .date(LocalDate.now()).build();

        when(invoiceRepository.findAll()).thenReturn(List.of(Set.of(invoice, invoice2)));

        Collection<Set<Invoice>> invoices = invoiceService.getInvoices();

        assertEquals(invoices.size(), 1);
        assertEquals(invoices.contains(Set.of(invoice, invoice2)), true);
    }

    @Test
    public void itShouldGetInvoiceByCustomerId(){
        Invoice invoice = Invoice.builder()
                .id(1l)
                .company(Company.builder().name("testCompany").category("testCategory").build())
                .price(BigDecimal.valueOf(10))
                .date(LocalDate.now()).build();
        Invoice invoice2 = Invoice.builder()
                .id(2l)
                .company(Company.builder().name("testCompany").category("testCategory").build())
                .price(BigDecimal.valueOf(5))
                .date(LocalDate.now()).build();

        when(invoiceRepository.findById(1l)).thenReturn(Set.of(invoice, invoice2));

        Set<Invoice> invoiceSet = invoiceService.getInvoicesByCustomerId(1l);

        assertEquals(invoiceSet.size(), 2);
        assertEquals(invoiceSet.contains(invoice), true);
        assertEquals(invoiceSet.contains(invoice2), true);
    }

    @Test
    public void itShouldGetInvoicesByCustomerSignedUpMonth(){
        Invoice invoice = Invoice.builder()
                .id(1l)
                .company(Company.builder().name("testCompany").category("testCategory").build())
                .price(BigDecimal.valueOf(10))
                .date(LocalDate.now()).build();
        Invoice invoice2 = Invoice.builder()
                .id(2l)
                .company(Company.builder().name("testCompany").category("testCategory").build())
                .price(BigDecimal.valueOf(5))
                .date(LocalDate.now()).build();
        Invoice invoice3 = Invoice.builder()
                .id(3l)
                .company(Company.builder().name("testCompany").category("testCategory").build())
                .price(BigDecimal.valueOf(15))
                .date(LocalDate.now()).build();
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

        when(customerService.getCustomersBySignedUpMonth(2)).thenReturn(List.of(customer, customer2));
        when(invoiceRepository.findById(1l)).thenReturn(Set.of(invoice, invoice2));
        when(invoiceRepository.findById(2l)).thenReturn(Set.of(invoice3));

        Set<Invoice> invoiceSet = invoiceService.getInvoicesByCustomerSignedUpMonth(2);

        assertEquals(invoiceSet.size(), 3);
        assertEquals(invoiceSet.contains(invoice), true);
        assertEquals(invoiceSet.contains(invoice2), true);
        assertEquals(invoiceSet.contains(invoice3), true);
    }

    @Test
    public void itShouldGetInvoicesHigherThenPrice(){
        Invoice invoice = Invoice.builder()
                .id(1l)
                .company(Company.builder().name("testCompany").category("testCategory").build())
                .price(BigDecimal.valueOf(10))
                .date(LocalDate.now()).build();
        Invoice invoice2 = Invoice.builder()
                .id(2l)
                .company(Company.builder().name("testCompany").category("testCategory").build())
                .price(BigDecimal.valueOf(5))
                .date(LocalDate.now()).build();
        Invoice invoice3 = Invoice.builder()
                .id(3l)
                .company(Company.builder().name("testCompany").category("testCategory").build())
                .price(BigDecimal.valueOf(15))
                .date(LocalDate.now()).build();

        when(invoiceRepository.findAll()).thenReturn(List.of(Set.of(invoice, invoice2), Set.of(invoice3)));

        Set<Invoice> invoiceSet = invoiceService.getInvoicesHigherThenPrice(BigDecimal.valueOf(5));

        assertEquals(invoiceSet.size(), 2);
        assertEquals(invoiceSet.contains(invoice), true);
        assertEquals(invoiceSet.contains(invoice3), true);
    }

    @Test
    public void itShouldGetInvoicesLowerThenPrice(){
        Invoice invoice = Invoice.builder()
                .id(1l)
                .company(Company.builder().name("testCompany").category("testCategory").build())
                .price(BigDecimal.valueOf(10))
                .date(LocalDate.now()).build();
        Invoice invoice2 = Invoice.builder()
                .id(2l)
                .company(Company.builder().name("testCompany").category("testCategory").build())
                .price(BigDecimal.valueOf(5))
                .date(LocalDate.now()).build();
        Invoice invoice3 = Invoice.builder()
                .id(3l)
                .company(Company.builder().name("testCompany").category("testCategory").build())
                .price(BigDecimal.valueOf(15))
                .date(LocalDate.now()).build();

        when(invoiceRepository.findAll()).thenReturn(List.of(Set.of(invoice, invoice2), Set.of(invoice3)));

        Set<Invoice> invoiceSet = invoiceService.getInvoicesLowerThenPrice(BigDecimal.valueOf(10));

        assertEquals(invoiceSet.size(), 1);
        assertEquals(invoiceSet.contains(invoice2), true);
    }

    @Test
    public void itShouldGetCustomerNamesWhoHasInvoiceLowerThenPrice(){
        Customer customer = Customer.builder()
                .id(1l)
                .firstName("test")
                .lastName("test")
                .address("test")
                .signedUpDate(LocalDate.now()).build();

        when(invoiceRepository.findCustomerIdsWhoHasInvoiceLowerThenPrice(BigDecimal.valueOf(15))).thenReturn(List.of(1l));
        when(customerService.getCustomerNameByCustomerId(1l)).thenReturn(customer.getFirstName());

        Set<String> customerNames = invoiceService.getCustomerNamesWhoHasInvoiceLowerThenPrice(BigDecimal.valueOf(15));

        assertEquals(customerNames.size(), 1);
        assertEquals(customerNames.contains(customer.getFirstName()), true);
    }

    @Test
    public void itShouldGetInvoicesGroupedByCompanyAndFilteredByMonth(){
        Invoice invoice = Invoice.builder()
                .id(1l)
                .company(Company.builder().name("testCompany").category("testCategory").build())
                .price(BigDecimal.valueOf(10))
                .date(LocalDate.now()).build();
        Invoice invoice2 = Invoice.builder()
                .id(2l)
                .company(invoice.getCompany())
                .price(BigDecimal.valueOf(5))
                .date(LocalDate.now()).build();
        Invoice invoice3 = Invoice.builder()
                .id(3l)
                .company(Company.builder().name("testCompany2").category("testCategory2").build())
                .price(BigDecimal.valueOf(15))
                .date(LocalDate.now()).build();

        when(invoiceRepository.findAll()).thenReturn(List.of(Set.of(invoice, invoice2, invoice3)));

        Map<Company, Set<Invoice>> invoices = invoiceService.getInvoicesGroupedByCompanyAndFilteredByMonth(2);

        assertEquals(invoices.size(), 2);
    }

    @Test
    public void itShouldGeCompanyCategoriesWhichHasAverageInvoicePriceLowerThenGivenPrice(){
        Invoice invoice = Invoice.builder()
                .id(1l)
                .company(Company.builder().name("testCompany").category("testCategory").build())
                .price(BigDecimal.valueOf(10))
                .date(LocalDate.now()).build();
        Invoice invoice2 = Invoice.builder()
                .id(2l)
                .company(invoice.getCompany())
                .price(BigDecimal.valueOf(15))
                .date(LocalDate.now()).build();
        Invoice invoice3 = Invoice.builder()
                .id(3l)
                .company(Company.builder().name("testCompany2").category("testCategory2").build())
                .price(BigDecimal.valueOf(10))
                .date(LocalDate.now()).build();
        Map<Company, Set<Invoice>> invoices = new HashMap<>();
        invoices.put(invoice.getCompany(), Set.of(invoice, invoice2));
        invoices.put(invoice3.getCompany(), Set.of(invoice3));

        List<String> companyCategories = invoiceService.getCompanyCategoriesWhichHasAverageInvoicePriceLowerThenGivenPrice(BigDecimal.valueOf(15), invoices);

        assertEquals(companyCategories.size(), 2);
        assertEquals(companyCategories.contains(invoice.getCompany().getCategory()), true);
        assertEquals(companyCategories.contains(invoice3.getCompany().getCategory()), true);
    }

    @Test
    public void itShouldCalculateAveragePriceOfInvoices(){
        Invoice invoice = Invoice.builder()
                .id(1l)
                .company(Company.builder().name("testCompany").category("testCategory").build())
                .price(BigDecimal.valueOf(10))
                .date(LocalDate.now()).build();
        Invoice invoice2 = Invoice.builder()
                .id(2l)
                .company(Company.builder().name("testCompany").category("testCategory").build())
                .price(BigDecimal.valueOf(5))
                .date(LocalDate.now()).build();
        Invoice invoice3 = Invoice.builder()
                .id(3l)
                .company(Company.builder().name("testCompany").category("testCategory").build())
                .price(BigDecimal.valueOf(15))
                .date(LocalDate.now()).build();


        BigDecimal average = invoiceService.calculateAveragePriceOfInvoices(Set.of(invoice, invoice2, invoice3));

        assertEquals(average, BigDecimal.valueOf(10));
    }

    @Test
    public void itShouldCalculateTotalPriceOfInvoices(){
        Invoice invoice = Invoice.builder()
                .id(1l)
                .company(Company.builder().name("testCompany").category("testCategory").build())
                .price(BigDecimal.valueOf(10))
                .date(LocalDate.now()).build();
        Invoice invoice2 = Invoice.builder()
                .id(2l)
                .company(Company.builder().name("testCompany").category("testCategory").build())
                .price(BigDecimal.valueOf(5))
                .date(LocalDate.now()).build();
        Invoice invoice3 = Invoice.builder()
                .id(3l)
                .company(Company.builder().name("testCompany").category("testCategory").build())
                .price(BigDecimal.valueOf(15))
                .date(LocalDate.now()).build();


        BigDecimal average = invoiceService.calculateTotalPriceOfInvoices(Set.of(invoice, invoice2, invoice3));

        assertEquals(average, BigDecimal.valueOf(30));
    }
}
