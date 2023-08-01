package com.service.reservation.services;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.service.reservation.communication.InventoryServiceClient;
import com.service.reservation.communication.RoomServiceClient;
import com.service.reservation.entities.Bill;
import com.service.reservation.entities.OrderedItem;
import com.service.reservation.entities.Report;
import com.service.reservation.entities.Reservation;
import com.service.reservation.entities.Room;
import com.service.reservation.entities.RoomService;
import com.service.reservation.exception.ReservationNotFoundException;
import com.service.reservation.repository.BillRepository;

@Service
public class BillService {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private InventoryServiceClient inventoryServiceClient;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private RoomServiceClient roomServiceClient;

    private static final Logger logger = LoggerFactory.getLogger(BillService.class);

    public Bill generateBill(Bill bill) throws ReservationNotFoundException {

        // call the inventory-service to get room object , extract room price
        String roomNumber = bill.getRoomNumber();
        Room room = inventoryServiceClient.getRoomByRoomNumber(roomNumber).getRoom();

        if (room.getReservationId() == 0)
            throw new ReservationNotFoundException("Reservation not found for room number " + roomNumber);

        Reservation reservation = reservationService.getReservationById(room.getReservationId());
        logger.info("testing--------------------------", room.toString());

        // call room-service and get details related to rommservice --- calculate prices
        // // based on items ordered
        RoomService roomService = roomServiceClient.getRoomServiceByRoomNumberAndCompleted(roomNumber, false)
                .getRoomService();

        double roomPrice = room.getPrice() * reservation.getNumberOfNights();
        Double orderedItemPrice = 0.0;
        if (roomService != null) {
            List<OrderedItem> orderedItems = roomService.getOrderedItems();
            orderedItemPrice = orderedItems.stream()
                    .map(item -> item.getPrice() * item.getQuantity())
                    .reduce(0.0, Double::sum);

        }

        double totalAmount = roomPrice + orderedItemPrice;
        double taxes = totalAmount * 0.18;
        double netAmount = totalAmount + taxes;

        // after bill generated set completed = true in RoomService
        roomServiceClient.setRoomServiceCompleted(roomNumber, true).isSuccess();

        // setting reservation as completed or billed
        boolean billed = reservationService.setReservationBilled(room.getReservationId());

        // after bill generated and room-service completed , then set availability to
        // true
        room.setAvailable(true);
        room.setReservationId(0);
        boolean roomAvailabillity = inventoryServiceClient.setAvailabilityAndReservationId(room).isSuccess();

        boolean checkOutCompleted = false;
        if (roomAvailabillity && billed) {
            checkOutCompleted = true;
        }
        // Generating bill
        Bill result = Bill.builder()
                .roomNumber(roomNumber)
                .guestName(bill.getGuestName())
                .phoneNumber(bill.getPhoneNumber())
                .billDate(new Date())
                .totalAmount(totalAmount)
                .netAmount(netAmount)
                .taxes(taxes)
                .checkOutCompleted(checkOutCompleted)
                .build();

        return billRepository.save(result);
    }

    // get unpaid bills
    public List<Bill> getNotPaidBills() {
        return billRepository.findByPaid(false);
    }

    // set paid for bill
    public Bill setPaidForBill(int billId) {
        Bill bill = billRepository.findById(billId).orElse(null);
        if (bill != null) {
            bill.setPaid(true);
            return billRepository.save(bill);
        }
        return bill;
    }

    // Generate Report last 30 days
    public Report generateReport(int days) {
        Date thirtyDaysAgo = getThirtyDaysAgoDate(days);
        long reservations = billRepository.countBillsWithinThirtyDays(thirtyDaysAgo);

        double revenue = billRepository.calculateTotalRevenue(thirtyDaysAgo); // Custom method in BillRepository

        Report report = new Report(reservations, revenue);
        return report;

    }

    // Util methods
    private Date getThirtyDaysAgoDate(int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -days);
        return calendar.getTime();
    }

}
