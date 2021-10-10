package com.itsol.repositories;

import com.itsol.DTO.Available;
import com.itsol.DTO.BookingFilterDTO;
import com.itsol.DTO.RoomDetail;
import com.itsol.DTO.RoomTypeDTO;
import com.util.DateConvert;
import org.hibernate.query.NativeQuery;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class RoomTypeRepository extends BaseRepository {
    public List<Available> getAvailable(BookingFilterDTO filterDTO) {
        try {
            List<Available> availableList = this.getEntityManager().createNativeQuery("SELECT ID, TYPE, RATE FROM ROOM_TYPE ORDER BY RATE")
                    .unwrap(NativeQuery.class).setResultTransformer((ListResultTransformer) (tuple, alias) -> new Available(
                            ((Number) tuple[0]).intValue(),
                            tuple[1].toString(),
                            ((Number) tuple[2]).intValue()
                    )).getResultList();
            availableList.stream().forEach(rt -> rt.setRoomList(getAvailableRooms(filterDTO, rt.getRtID())));
            return availableList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public List<RoomDetail> getAvailableRooms(BookingFilterDTO filterDTO, int rooomTypeID) {
        Date from = filterDTO.getDate_from();
        Date to = filterDTO.getDate_to();
        return this.getEntityManager().createNativeQuery("SELECT R.ID, R.ROOM_NUMBER FROM ROOM R WHERE R.TYPE_ID=? AND R.ID NOT IN (SELECT RB.ROOM_ID FROM ROOM_BOOKED RB JOIN BOOKING B on RB.BOOKING_ID = B.ID " +
                        "WHERE (B.DATE_FROM BETWEEN ? AND ?) OR (B.DATE_TO BETWEEN ? AND ?) OR (B.DATE_FROM <= ? AND B.DATE_TO >=?)) ORDER BY R.ROOM_NUMBER")
                .setParameter(1, rooomTypeID)
                .setParameter(2, DateConvert.beginOfDay(from))
                .setParameter(3, new DateTime(DateConvert.beginOfDay(to)).minusDays(1).toDate())
                .setParameter(4, new DateTime(DateConvert.beginOfDay(from)).plusDays(1).toDate())
                .setParameter(5, DateConvert.beginOfDay(to))
                .setParameter(6, DateConvert.beginOfDay(from))
                .setParameter(7, DateConvert.beginOfDay(to))
                .unwrap(NativeQuery.class).setResultTransformer((ListResultTransformer) (tuple, alias) -> new RoomDetail(
                        ((Number) tuple[0]).intValue(),
                        ((Number) tuple[1]).intValue()
                )).getResultList();
    }

    public List<RoomTypeDTO> getAll() {
        return this.getEntityManager().createNativeQuery("SELECT ID, TYPE, RATE FROM ROOM_TYPE")
                .unwrap(NativeQuery.class).setResultTransformer((ListResultTransformer) (tuple, alias) -> new RoomTypeDTO(
                        ((Number) tuple[0]).intValue(),
                        tuple[1].toString(),
                        ((Number) tuple[2]).intValue()
                )).getResultList();
    }

    public void saveOrUpdate(List<RoomTypeDTO> roomTypeList) {
        List<Number> rtOld = this.getEntityManager().createNativeQuery("SELECT ID FROM ROOM_TYPE")
                .getResultList();
        List<Integer> rtNew = roomTypeList.stream().map(rt -> rt.getId()).collect(Collectors.toList());
        System.out.println(rtNew);
        rtOld.stream().filter(rt -> rtNew.indexOf(rt.intValue()) == -1).forEach(rt -> {
            System.out.println(rt);
            this.deleteRoomType(rt.intValue());
        });
        roomTypeList.stream().forEach(roomType -> {
            System.out.println(roomType);
            if (roomType.getId() == null) {
                this.saveRoomType(roomType);
            } else {
                this.updateRoomType(roomType);
            }
        });
    }

    private boolean updateRoomType(RoomTypeDTO roomType) {
        return this.getEntityManager().createNativeQuery("UPDATE ROOM_TYPE SET TYPE=?, RATE=? WHERE ID=?")
                .setParameter(1, roomType.getName())
                .setParameter(2, roomType.getRate())
                .setParameter(3, roomType.getId())
                .executeUpdate() > 0;
    }

    private boolean saveRoomType(RoomTypeDTO roomType) {
        return this.getEntityManager().createNativeQuery("INSERT INTO ROOM_TYPE (TYPE, RATE) VALUES (?,?)")
                .setParameter(1, roomType.getName())
                .setParameter(2, roomType.getRate())
                .executeUpdate() > 0;
    }

    private boolean deleteRoomType(int roomTypeID) {
        return this.getEntityManager().createNativeQuery("DELETE FROM ROOM_TYPE WHERE ID=?")
                .setParameter(1, roomTypeID)
                .executeUpdate() > 0;
    }
}

