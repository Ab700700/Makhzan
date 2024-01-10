package com.example.makhzan.Service;

import com.example.makhzan.Api.ApiException;
import com.example.makhzan.DTO.OrdersDTO;
import com.example.makhzan.Model.Customer;
import com.example.makhzan.Model.Orders;
import com.example.makhzan.Model.Storage;
import com.example.makhzan.Repository.CustomerRepository;
import com.example.makhzan.Repository.OrdersRepository;
import com.example.makhzan.Repository.StorageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdersService {
    private final OrdersRepository ordersRepository;
    private final StorageRepository storageRepository;
    private final CustomerRepository customerRepository;

    public List<Orders> getOrders(){
        return ordersRepository.findAll();
    }

    public void createOrder(OrdersDTO ordersDTO, Integer userid){
        if(ordersDTO.getStartDate().isAfter(ordersDTO.getEndDate())) throw new ApiException("Write a valid date");
        Storage storage = storageRepository.findStorageById(ordersDTO.getStorageid());
        List<Orders> acceptedOrders = ordersRepository.getOrdersBetweenAndAccpted(ordersDTO.getStartDate(),ordersDTO.getEndDate());
        if(!acceptedOrders.isEmpty()) throw new ApiException("Storage already taken");
        if(storage == null) throw new ApiException("Storage not found");
        Customer customer = customerRepository.findCustomerById(userid);
        if(customer == null) throw new ApiException("User not found");
        if(!storage.getAvailable()) throw new ApiException("Storage not available");
        Orders gotit = ordersRepository.findOrdersByCustomerAndStartDateAndEndDate(customer,ordersDTO.getStartDate(),ordersDTO.getEndDate());
        if( gotit != null) throw new ApiException("You already ordered");
        long days = ChronoUnit.DAYS.between(ordersDTO.getStartDate(),ordersDTO.getEndDate());
        Double total = days * storage.getPrice();
        Orders order = new Orders(null,total,ordersDTO.getStartDate(),ordersDTO.getEndDate(),"PENDING", LocalDate.now(),customer,storage);
        ordersRepository.save(order);
    }

//    public void updateOrder(Integer id, Integer userid,OrdersDTO ordersDTO){
//        Orders oldOrder = ordersRepository.findOrdersById(id);
//        if(oldOrder == null) throw new ApiException("Order not found");
//        Customer customer = customerRepository.findCustomerById(userid);
//        if(customer == null) throw new ApiException("User not found");
//        if(!oldOrder.getCustomer().equals(customer)) throw new ApiException("User not allowed to update the order");
//        oldOrder.set
//
//    }

    public void deleteOrder(Integer id, Integer userid){
        Orders order = ordersRepository.findOrdersById(id);
        if(order== null) throw new ApiException("Order not found");
        Customer customer = customerRepository.findCustomerById(userid);
        if(customer == null) throw new ApiException("User not found");
        if(!order.getCustomer().equals(customer)) throw new ApiException("User not allowed");
        ordersRepository.delete(order);
    }



    public List<Orders> findOrdersAccepted(){
        return ordersRepository.findOrdersAccepted();
    }

    public List<Orders> findOrdersPending(){
        return ordersRepository.findOrdersPending();
    }


    public List<Orders> findOrdersRejected(){
        return ordersRepository.findOrdersRejected();
    }




    public List<Orders> findOrdersStartingBeforeDate(LocalDate date){
        return ordersRepository.findOrdersStartingBeforeDate(date);
    }

    public List<Orders> findOrdersStartingAfterDate(LocalDate date){
        return ordersRepository.findOrdersStartingAfterDate(date);
    }
}
