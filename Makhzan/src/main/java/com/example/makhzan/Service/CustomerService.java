package com.example.makhzan.Service;

import com.example.makhzan.Api.ApiException;
import com.example.makhzan.DTO.CustomerDTO;
import com.example.makhzan.Model.Customer;
import com.example.makhzan.Model.Orders;
import com.example.makhzan.Model.User;
import com.example.makhzan.Repository.CustomerRepository;
import com.example.makhzan.Repository.OrdersRepository;
import com.example.makhzan.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final OrdersRepository ordersRepository;

    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    public void register(CustomerDTO customerDTO){
        if(customerDTO.getBirthDate() == null) throw new ApiException("Birth date should not be null");
        User user=new User(null,customerDTO.getUsername(),customerDTO.getPassword(),customerDTO.getEmail(),customerDTO.getName(),customerDTO.getRole(),customerDTO.getPhonenumber(),null,null);
        user.setRole("CUSTOMER");
        String hash=new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(hash);
        userRepository.save(user);

        Customer customer=new Customer(null, customerDTO.getBirthDate(),null,user,null,null);
        customerRepository.save(customer);
    }
    //customer, admin
    public void updateCustomer(CustomerDTO customerDTO ,Integer id) {
        Customer oldCustomer = customerRepository.findCustomerById(id);
        if (oldCustomer == null) {
            throw new ApiException("Customer Not Found");
        }
        User user = userRepository.findUserById(id);
        user.setUsername(customerDTO.getUsername());
        user.setEmail(customerDTO.getEmail());
        user.setPhonenumber(customerDTO.getPhonenumber());
        oldCustomer.setBirthDate(customerDTO.getBirthDate());
        String hash=new BCryptPasswordEncoder().encode(customerDTO.getPassword());
        user.setPassword(hash);

        customerRepository.save(oldCustomer);
        userRepository.save(user);
    }
    // customer, admin
    public void deleteCustomer(Integer id){
        Customer customer = customerRepository.findCustomerById(id);
        if (customer == null) {
            throw new ApiException("Customer Not Found");
        }
        User user=userRepository.findUserById(id);
        if (user == null) {
            throw new ApiException("User Not Found");
        }
        user.setCustomer(null);
        userRepository.delete(user);
        customerRepository.delete(customer);
    }
    // customer,admin
    public CustomerDTO customerDetails(Integer userid){
        Customer customer = customerRepository.findCustomerById(userid);
        if(customer == null) throw new ApiException("User not found");
        User user = userRepository.findUserById(userid);
        CustomerDTO customerDTO = new CustomerDTO(userid,user.getUsername(),user.getPassword(),user.getEmail(),user.getName(),user.getRole(),user.getPhonenumber(),customer.getBirthDate());
        return customerDTO;
    }
    // customer
    public Set<Orders> customerOrders(Integer userid){
        Customer customer = customerRepository.findCustomerById(userid);
        if (customer == null) throw  new ApiException("User not found");
        return customer.getOrders();
    }
    //
    public List<Orders> customerAcceptedOrders(Integer userid){
        Customer customer = customerRepository.findCustomerById(userid);
        if (customer == null) throw  new ApiException("User not found");
        List<Orders> acceptedOrders = new ArrayList<>();
        for(Orders o : customer.getOrders()){
            if(o.getStatus().equals("ACCEPTED")) acceptedOrders.add(o);
        }
        return acceptedOrders;
    }

    public List<Orders> customerRejectedOrders(Integer userid){
        Customer customer = customerRepository.findCustomerById(userid);
        if (customer == null) throw  new ApiException("User not found");
        List<Orders> rejectOrders = new ArrayList<>();
        for(Orders o : customer.getOrders()){
            if(o.getStatus().equals("REJECT")) rejectOrders.add(o);
        }
        return rejectOrders;
    }

    public List<Orders> customerPendingOrders(Integer userid){
        Customer customer = customerRepository.findCustomerById(userid);
        if (customer == null) throw  new ApiException("User not found");
        List<Orders> pendingOrders = new ArrayList<>();
        for(Orders o : customer.getOrders()){
            if(o.getStatus().equals("PENDING")) pendingOrders.add(o);
        }
        return pendingOrders;
    }

    public List<Customer> mostRented(){
        return customerRepository.findCustomerByRentTimesDesc();
    }

    public List<Customer> lessRented(){
        return customerRepository.findCustomerByRentTimesASC();
    }
}
