package repository;

import model.Invoice;

import java.math.BigDecimal;
import java.util.*;

public class InvoiceRepository implements HashMapRepository<Long, Invoice>{

    private static Map<Long, Set<Invoice>> hashMap;

    public InvoiceRepository(){
        hashMap = new HashMap<>();
    }

    @Override
    public boolean save(Long customerId, Invoice invoice){
        if(hashMap.get(customerId) == null)
            hashMap.put(customerId, new HashSet<>());
        return hashMap.get(customerId).add(invoice);
    }

    @Override
    public boolean save(Long customerId, Set<Invoice> invoiceSet){
        if(hashMap.get(customerId) == null)
            hashMap.put(customerId, new HashSet<>());
        return hashMap.get(customerId).addAll(invoiceSet);
    }

    @Override
    public Set<Invoice> findById(Long customerId){
        return hashMap.get(customerId);
    }

    @Override
    public Collection<Set<Invoice>> findAll(){
        return hashMap.values();
    }

    public List<Long> findCustomerIdsWhoHasInvoiceLowerThenPrice(BigDecimal price){
        List<Long> customerIdList = new ArrayList<>();
        hashMap.keySet().stream().forEach(key -> {
            hashMap.get(key).stream().forEach(invoice -> {
                if(invoice.getPrice().compareTo(price)<0)
                    customerIdList.add(key);
            });
        });
        return customerIdList;
    }

}
