package com.example.makhzan.Repository;

import com.example.makhzan.Model.Customer;
import com.example.makhzan.Model.Orders;
import com.example.makhzan.Model.Storage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders,Integer> {

    Orders findOrdersById(Integer id);

    @Query("select o from Orders o where o.storage.id =?1")
    List<Orders> getOrders(Integer storageid);

    List<Orders> findOrdersByCustomer(Customer customer);

    @Query("select o from Orders o where o.storage.landlord.id=?1 and o.id=?2")
    Orders findOrdersByLandlordIdAndOrderId(Integer landlordId,Integer orderId);

    @Query("select o from  Orders o where  o.startDate>=?1 and o.endDate<=?2 and o.storage=?3")
    List<Orders> getOrdersBetween(LocalDate localDate1, LocalDate localDate , Storage storage);

    @Query("select o from  Orders o where  o.startDate>=?1 and o.endDate<=?2 and o.status='ACCPTED'")
    List<Orders> getOrdersBetweenAndAccpted(LocalDate localDate1, LocalDate localDate);
    @Query("select o from  Orders o where  o.startDate>=?1 and o.endDate<=?2 and o.status='ACCPTED' and o.storage=?3")
    List<Orders> getOrdersBetweenAndAccptedtwo(LocalDate localDate1, LocalDate localDate, Storage storage);
    Orders findOrdersByCustomerAndStartDateAndEndDate(Customer customer , LocalDate localDate1, LocalDate localDate2);
    @Query("select o from Orders o where o.status='ACCEPTED'")
    List<Orders> findOrdersAccepted();

    @Query("select o from Orders o where o.status='PENDING'")
    List<Orders> findOrdersPending();


    @Query("select o from Orders o where o.status='REJECT'")
    List<Orders> findOrdersRejected();



    @Query("select o from Orders o where o.startDate < ?1")
    List<Orders> findOrdersStartingBeforeDate(LocalDate targetDate);

    @Query("select o from Orders o where o.startDate > ?1")
    List<Orders> findOrdersStartingAfterDate(LocalDate targetDate);

    @Query("select o from Orders o where o.storage.id=?1 and o.id=?2")
    Orders findOrdersByStorageIdAndOrderId(Integer storageId,Integer orderId);

    @Query("select o from Orders  o where o.startDate =?1 and o.storage =?2 and o.status ='ACCEPTED'")
    Orders findOrdersByStartDate(LocalDate localDate,Storage storage);

    @Query("select o from Orders  o where o.endDate =?1 and o.storage =?2 and o.status ='ACCEPTED'")
    Orders findOrdersByEndDate(LocalDate localDate,Storage storage);
}
