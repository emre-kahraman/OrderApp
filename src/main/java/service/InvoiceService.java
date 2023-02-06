package service;

import repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import model.Company;
import model.Customer;
import model.Invoice;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final CustomerService customerService;

    public boolean saveInvoice(Long customerId, Invoice invoice){
        return invoiceRepository.save(customerId, invoice);
    }

    public Collection<Set<Invoice>> getInvoices(){
        return invoiceRepository.findAll();
    }

    public Set<Invoice> getInvoicesByCustomerId(Long customerId){
        return invoiceRepository.findById(customerId);
    }

    public Set<Invoice> getInvoicesByCustomerSignedUpMonth(int month){
        List<Long> customerIdList = customerService.getCustomersBySignedUpMonth(month).stream()
                .map(Customer::getId)
                .collect(Collectors.toList());
        Set<Invoice> invoiceSet = new HashSet<>();
        customerIdList.stream().forEach(customerId -> {
            if(invoiceRepository.findById(customerId) != null)
            invoiceSet.addAll(invoiceRepository.findById(customerId));
        });
        return invoiceSet;
    }

    public Set<Invoice> getInvoicesHigherThenPrice(BigDecimal price){
        Set<Invoice> invoiceSet = new HashSet<>();
        invoiceRepository.findAll().stream().forEach(invoices -> {
            invoiceSet.addAll(invoices.stream()
                    .filter(invoice -> invoice.getPrice().compareTo(price)>0)
                    .collect(Collectors.toSet()));
        });
        return invoiceSet;
    }

    public Set<Invoice> getInvoicesLowerThenPrice(BigDecimal price){
        Set<Invoice> invoiceSet = new HashSet<>();
        invoiceRepository.findAll().stream().forEach(invoices -> {
            invoiceSet.addAll(invoices.stream()
                    .filter(invoice -> invoice.getPrice().compareTo(price)<0)
                    .collect(Collectors.toSet()));
        });
        return invoiceSet;
    }

    public Set<String> getCustomerNamesWhoHasInvoiceLowerThenPrice(BigDecimal price){
        return invoiceRepository.findCustomerIdsWhoHasInvoiceLowerThenPrice(price)
                .stream()
                .map(customerService::getCustomerNameByCustomerId).collect(Collectors.toSet());
    }

    public Map<Company, Set<Invoice>> getInvoicesGroupedByCompanyAndFilteredByMonth(int month){
        Map<Company, Set<Invoice>> invoices = new HashMap<>();
        invoiceRepository.findAll().stream().forEach(invoiceSet -> invoiceSet.stream().forEach(invoice -> {
            if(invoices.get(invoice.getCompany()) == null)
                invoices.put(invoice.getCompany(), new HashSet<>());
            if(invoice.getDate().getMonthValue() == month)
                invoices.get(invoice.getCompany()).add(invoice);
        }));
        return invoices;
    }

    public List<String> getCompanyCategoriesWhichHasAverageInvoicePriceLowerThenGivenPrice(BigDecimal price, Map<Company, Set<Invoice>> invoices){
        List<String> companyCategories = new ArrayList<>();
        invoices.entrySet().stream().forEach(invoiceSet -> {
            if(invoiceSet.getValue().size()>0 && calculateAveragePriceOfInvoices(invoiceSet.getValue()).compareTo(price)<0)
                companyCategories.add(invoiceSet.getKey().getCategory());
        });
        return companyCategories;
    }

    public BigDecimal calculateAveragePriceOfInvoices(Set<Invoice> invoiceSet){
        if(invoiceSet.size() == 0)
            return null;
        return invoiceSet.stream()
                .map(Invoice::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(invoiceSet.size()));
    }

    public BigDecimal calculateTotalPriceOfInvoices(Set<Invoice> invoiceSet){
        return invoiceSet.stream()
                .map(Invoice::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
