import repository.CustomerRepository;
import repository.InvoiceRepository;
import model.Company;
import model.Customer;
import model.Invoice;
import service.CustomerService;
import service.InvoiceService;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        CustomerService customerService = new CustomerService(new CustomerRepository());
        InvoiceService invoiceService = new InvoiceService(new InvoiceRepository(), customerService);
        Customer customer = Customer.builder()
                .id(1l)
                .firstName("testc")
                .lastName("test")
                .address("test")
                .signedUpDate(LocalDate.now()).build();
        Customer customer2 = Customer.builder()
                .id(2l)
                .firstName("test2")
                .lastName("test2")
                .address("test2")
                .signedUpDate(LocalDate.of(2022, 6, 1)).build();
        Invoice invoice = Invoice.builder()
                .id(1l)
                .company(Company.builder().name("testCompany").category("testCategory").build())
                .price(BigDecimal.valueOf(10))
                .date(LocalDate.of(2022, 6, 1)).build();
        Invoice invoice2 = Invoice.builder()
                .id(2l)
                .company(Company.builder().name("testCompany2").category("testCategory2").build())
                .price(BigDecimal.valueOf(1500))
                .date(LocalDate.of(2022, 6, 1)).build();
        Invoice invoice3 = Invoice.builder()
                .id(3l)
                .company(invoice2.getCompany())
                .price(BigDecimal.valueOf(3000))
                .date(LocalDate.of(2022, 6, 1)).build();
        customerService.saveCustomer(customer);
        customerService.saveCustomer(customer2);
        invoiceService.saveInvoice(1l, invoice);
        invoiceService.saveInvoice(1l, invoice2);
        invoiceService.saveInvoice(2l, invoice3);
        System.out.println("All customers: " + customerService.getCustomers());
        System.out.println("Customers contains c: " + customerService.searchCustomersByText("c"));
        System.out.println("Total price of customer invoices who signed in June: " + invoiceService.calculateTotalPriceOfInvoices(invoiceService.getInvoicesByCustomerSignedUpMonth(6)));
        System.out.println("All invoices: " + invoiceService.getInvoices());
        System.out.println("Invoices higher then 1500: " + invoiceService.getInvoicesHigherThenPrice(BigDecimal.valueOf(1500)));
        System.out.println("Average price of invoices higher then 1500: " + invoiceService.calculateAveragePriceOfInvoices(invoiceService.getInvoicesHigherThenPrice(BigDecimal.valueOf(1500))));
        System.out.println("Customer names who has invoice price lower then 500: " + invoiceService.getCustomerNamesWhoHasInvoiceLowerThenPrice(BigDecimal.valueOf(500)));
        System.out.println("Company categories which has average price of invoices lower then 750 in June: " + invoiceService.getCompanyCategoriesWhichHasAverageInvoicePriceLowerThenGivenPrice(BigDecimal.valueOf(750), invoiceService.getInvoicesGroupedByCompanyAndFilteredByMonth(6)));
    }
}
