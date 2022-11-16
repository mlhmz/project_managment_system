package de.szut.lf8_project.customer;

import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    /**
     * Checks if Customer exists
     *
     * @param customerId The actual id of the customer
     * @return boolean if customer exists
     */
    public boolean checkIfCustomerExists(Long customerId) {
        return true;
    }
}
