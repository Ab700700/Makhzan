package com.example.makhzan.Service;

import com.example.makhzan.Api.ApiException;
import com.example.makhzan.DTO.LandLordDTO;
import com.example.makhzan.Model.LandLord;
import com.example.makhzan.Model.Orders;
import com.example.makhzan.Model.Storage;
import com.example.makhzan.Model.User;
import com.example.makhzan.Repository.LandLordRepository;
import com.example.makhzan.Repository.OrdersRepository;
import com.example.makhzan.Repository.StorageRepository;
import com.example.makhzan.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class LandLordService {
    private final LandLordRepository landLordRepository;
    private final UserRepository userRepository;
    private final OrdersRepository ordersRepository;
    private final StorageRepository storageRepository;
    private final StorageService storageService;

    public List<LandLord> getAllLandlords(){
        return landLordRepository.findAll();
    }
    public void register(LandLordDTO landlordDTO){
        User user = new User(null,landlordDTO.getUsername(),landlordDTO.getPassword(),landlordDTO.getEmail(),landlordDTO.getName(),landlordDTO.getRole(),landlordDTO.getPhonenumber(),null,null);
        user.setRole("LANDLORD");
        String hash=new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(hash);
        userRepository.save(user);
        LandLord landlord=new LandLord(null, "pending",landlordDTO.getIsCompany(),landlordDTO.getLicense(),user,null);
        landLordRepository.save(landlord);
    }

    public void updateLandlord(LandLordDTO landlordDTO ,Integer id) {
        LandLord landlord = landLordRepository.findLandLordById(id);
        User user = userRepository.findUserById(id);
        if (landlord == null) {
            throw new ApiException("Landlord Not Found");
        }
        user.setUsername(landlordDTO.getName());
        user.setEmail(landlordDTO.getEmail());
        user.setPhonenumber(landlordDTO.getPhonenumber());
        landlord.setIsCompany(landlordDTO.getIsCompany());
        landlord.setLicense(landlordDTO.getLicense());
        String hash=new BCryptPasswordEncoder().encode(landlordDTO.getPassword());
        user.setPassword(hash);

        landLordRepository.save(landlord);
        userRepository.save(user);
    }

    public void deleteLandlord(Integer id){
        LandLord landlord = landLordRepository.findLandLordById(id);
        if (landlord == null) {
            throw new ApiException("Landlord Not Found");
        }
        User user=userRepository.findUserById(id);
        if (user == null) {
            throw new ApiException("User Not Found");
        }
        user.setLandlord(null);
        userRepository.save(user);
        landLordRepository.delete(landlord);
        userRepository.delete(user);
    }

    public void acceptOrder(Integer orderid,Integer userid){
        Orders order = ordersRepository.findOrdersById(orderid);
        User user = userRepository.findUserById(userid);
        if(user == null|| !user.equals(order.getStorage().getLandlord())) throw new ApiException("User not found or not allowed");
        List<Orders> ordersbetween = ordersRepository.getOrdersBetween(order.getStartDate(),order.getEndDate());
        for(Orders o : ordersbetween){
            if(!o.equals(order)) {
                o.setStatus("REJECT");
                ordersRepository.save(o);
            }
        }
        order.setStatus("ACCEPTED");
        ordersRepository.save(order);
    }

    public void rejectOrder(Integer storageId, Integer orderId) {
        Orders orders=ordersRepository.findOrdersByStorageIdAndOrderId(storageId, orderId);
        if (orders==null) {
            throw new ApiException("Order Not Found");
        }
        orders.setStatus("REJECT");
        ordersRepository.save(orders);
    }

    public Set<Orders> storageOrders(Integer storageid){
        Storage storage = storageRepository.findStorageById(storageid);
        return storage.getOrders();
    }
    public Set<Storage> storages(Integer userid){
        LandLord landLord = landLordRepository.findLandLordById(userid);
        if(landLord == null) throw new ApiException("User not found");
        return landLord.getStorages();
    }

    public LandLord getLandlordDetails(Integer userid){
        LandLord landLord = landLordRepository.findLandLordById(userid);
        if(landLord == null) throw new ApiException("User not found");
        return landLord;
    }
}
