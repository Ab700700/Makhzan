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

import java.util.List;

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
    public void updateCustomer(CustomerDTO customerDTO ,Integer id) {
        Customer oldCustomer = customerRepository.findCustomerById(id);
        if (oldCustomer == null) {
            throw new ApiException("Customer Not Found");
        }
        User user = userRepository.findUserById(id);
        user.setUsername(customerDTO.getName());
        user.setEmail(customerDTO.getEmail());
        user.setPhonenumber(customerDTO.getPhonenumber());
        oldCustomer.setBirthDate(customerDTO.getBirthDate());
        String hash=new BCryptPasswordEncoder().encode(customerDTO.getPassword());
        user.setPassword(hash);

        customerRepository.save(oldCustomer);
        userRepository.save(user);
    }
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

    public CustomerDTO customerDetails(Integer userid){
        Customer customer = customerRepository.findCustomerById(userid);
        if(customer == null) throw new ApiException("User not found");
        User user = userRepository.findUserById(userid);
        CustomerDTO customerDTO = new CustomerDTO(userid,user.getUsername(),user.getPassword(),user.getEmail(),user.getName(),user.getRole(),user.getPhonenumber(),customer.getBirthDate());
        return customerDTO;
    }

//    public List<Orders> customerOrders(Integer userid){
//        Customer customer = customerRepository.findCustomerById(userid);
//
//    }
}
