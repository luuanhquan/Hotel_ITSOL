package com.itsol.repositories;

import com.itsol.DTO.*;
import com.util.DateConvert;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Transactional
public class BookingRepository extends BaseRepository {

    public List<BookingDTO> getBookingList(String text, Date date_from, Date date_to) {
        try {
            System.out.println(date_from);
            System.out.println(date_to);
            List<BookingDTO> bookingInfo = this.getEntityManager()
                    .createNativeQuery("SELECT ID, BOOKER, ROOM_COUNT, DATE_FROM, DATE_TO, NOTICE FROM BOOKING B  WHERE B.STATUS=1  AND (B.ID LIKE ? OR ( B.DATE_FROM between ? and ?) ) ORDER BY B.DATE_FROM, B.ID ")
                    .setParameter(1, text)
                    .setParameter(2, DateConvert.beginOfDay(date_from))
                    .setParameter(3, DateConvert.endOfDay(date_to))
                    .unwrap(NativeQuery.class)
                    .setResultTransformer((ListResultTransformer) (tuple, alias) -> new BookingDTO(
                            ((Number) tuple[0]).intValue(),
                            tuple[1].toString(),
                            ((Number) tuple[2]).intValue(),
                            (Date) tuple[3],
                            (Date) tuple[4],
                            tuple[5].toString()
                    ))
                    .getResultList();
            bookingInfo.stream().forEach(bk -> {
                bk.setRoomingList(this.getRoomingList(bk.getId()));
                bk.getRoomingList().stream().forEach(rm -> {
                    rm.setGuestList(this.getGuestList(rm.getId()));
                });
            });

            return bookingInfo;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }


    private List<RoomingListDTO> getRoomingList(int bookingID) {
        return this.getEntityManager().createNativeQuery("SELECT RB.ID, R.ROOM_NUMBER, RT.RATE, RT.TYPE  FROM ROOM_BOOKED RB JOIN ROOM R on RB.ROOM_ID = R.ID JOIN ROOM_TYPE RT on R.TYPE_ID = RT.ID WHERE RB.BOOKING_ID=?")
                .setParameter(1, bookingID).unwrap((NativeQuery.class))
                .setResultTransformer((ListResultTransformer) (tuple, alias) -> new RoomingListDTO(
                        ((Number) tuple[0]).intValue(),
                        ((Number) tuple[1]).intValue(),
                        ((Number) tuple[2]).intValue(),
                        tuple[3].toString()
                )).getResultList();
    }

    private List<GuestDTO> getGuestList(int roomBookedID) {
        return this.getEntityManager().createNativeQuery("SELECT G.ID, G.NAME, G.GENDER, G.ADDRESS, G.PHONE, G.PERSONAL_ID FROM GUESTS G JOIN STAYED_GUEST SG on G.ID = SG.GUEST_ID WHERE SG.ROOM_BOOKED_ID=?")
                .setParameter(1, roomBookedID)
                .unwrap(NativeQuery.class).setResultTransformer((ListResultTransformer) (tuple, alias) -> new GuestDTO(
                        ((Number) tuple[0]).intValue(),
                        (String) tuple[1],
                        ((Number) tuple[2]).intValue() == 1 ? "Male" : "Female",
                        (String) tuple[3],
                        (String) tuple[4],
                        (String) tuple[5])).getResultList();
    }

    public boolean createBooking(BookingCreateDTO bookingCreate) {
        try {
            this.getEntityManager().createNativeQuery("INSERT INTO BOOKING (BOOKER, ROOM_COUNT, DATE_FROM, DATE_TO, NOTICE) VALUES (?,?,?,?,?)")
                    .setParameter(1, bookingCreate.getBooker())
                    .setParameter(2, bookingCreate.getRoomList().size())
                    .setParameter(3, bookingCreate.getDate_from())
                    .setParameter(4, bookingCreate.getDate_to())
                    .setParameter(5, bookingCreate.getNotice()).executeUpdate();
            int booking_id = ((Number) this.getEntityManager().createNativeQuery("SELECT ID FROM BOOKING ORDER BY ID DESC")
                    .setMaxResults(1).getResultList().get(0)).intValue();
            bookingCreate.getRoomList().stream().forEach(roomID -> {
                this.getEntityManager().createNativeQuery("INSERT INTO ROOM_BOOKED ( BOOKING_ID, ROOM_ID) VALUES (?,?)")
                        .setParameter(1, booking_id)
                        .setParameter(2, roomID)
                        .executeUpdate();
            });
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletBooking(int bookingId) {
        return this.getEntityManager().createNativeQuery("UPDATE BOOKING SET STATUS = 0 WHERE ID=?")
                .setParameter(1, bookingId)
                .executeUpdate() > 0;
    }

    public void updateOrSaveGuestList(RoomingListDTO roomingListDTO) {
        List<Number> guestIdList= this.getEntityManager().createNativeQuery("SELECT GUEST_ID FROM STAYED_GUEST WHERE ROOM_BOOKED_ID=?")
                .setParameter(1, roomingListDTO.getId())
                .getResultList();
        List<Integer> guestIDNew= roomingListDTO.getGuestList().stream().map(guest-> guest.getId()).collect(Collectors.toList());
        guestIdList.stream().filter(id -> guestIDNew.indexOf(id.intValue())==-1).forEach(deletedId->this.deleteGuest(deletedId));

        roomingListDTO.getGuestList().stream().forEach(guestDTO -> {
            if (guestDTO.getId() == null) {
                this.saveGuest(guestDTO, roomingListDTO.getId());
            } else {
                this.updateGuest(guestDTO);
            }
        });
        }

    private void deleteGuest(Number deletedId) {
        this.getEntityManager().createNativeQuery("DELETE FROM STAYED_GUEST WHERE GUEST_ID = ?")
                .setParameter(1, deletedId)
                .executeUpdate();
    }

    private void updateGuest(GuestDTO guestDTO) {
        this.getEntityManager().createNativeQuery("UPDATE GUESTS SET NAME=?,ADDRESS=?,GENDER=?,PERSONAL_ID=?,PHONE=? WHERE ID=?")
                .setParameter(1, guestDTO.getName())
                .setParameter(2, guestDTO.getAddress())
                .setParameter(3, guestDTO.getGender()=="Male"?1:0)
                .setParameter(4, guestDTO.getPersonal_id())
                .setParameter(5,guestDTO.getPhone())
                .setParameter(6, guestDTO.getId())
                .executeUpdate();
    }

    private void saveGuest(GuestDTO guestDTO, Integer id) {
        if(guestDTO.getName().trim().isEmpty()){
            System.out.println("EMPTY");
            return;
        }

        this.getEntityManager().createNativeQuery("INSERT INTO GUESTS (NAME, ADDRESS, PHONE, PERSONAL_ID, GENDER) VALUES (?,?,?,?,?)")
                .setParameter(1, guestDTO.getName())
                .setParameter(2, guestDTO.getAddress())
                .setParameter(3, guestDTO.getPhone())
                .setParameter(4, guestDTO.getPersonal_id())
                .setParameter(5, guestDTO.getGender() == "Male" ? 1 : 0)
                .executeUpdate();

        int guest_id = ((Number) this.getEntityManager().createNativeQuery("SELECT ID FROM GUESTS ORDER BY ID DESC")
                .setMaxResults(1).getResultList().get(0)).intValue();

        this.getEntityManager().createNativeQuery("INSERT INTO STAYED_GUEST (GUEST_ID, ROOM_BOOKED_ID) VALUES (?,?)")
                .setParameter(1, guest_id)
                .setParameter(2, id)
                .executeUpdate();
    }

    public void createRoom(int bookingID, RoomDetail room) {
        this.getEntityManager().createNativeQuery("INSERT INTO ROOM_BOOKED (BOOKING_ID, ROOM_ID, NOTICE) VALUES (?,?,'')")
                .setParameter(1,bookingID)
                .setParameter(2, room.getID())
                .executeUpdate();
    }

    public void deleteRoom(int bookedRoomID) {
        System.out.println("OK?");
        this.getEntityManager().createNativeQuery("DELETE FROM ROOM_BOOKED WHERE ID=?")
                .setParameter(1, bookedRoomID)
                .executeUpdate();
    }
}
