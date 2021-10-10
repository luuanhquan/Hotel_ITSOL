package com.itsol.repositories;

import com.itsol.DTO.GuestDTO;
import com.itsol.DTO.HistoryDTO;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.Date;
import java.util.List;

@Repository
public class GuestRepository extends BaseRepository {
    private static int items_per_page = 5;

    public List<GuestDTO> findAll(int page) {
        return this.getEntityManager().createNativeQuery("SELECT ID, NAME, GENDER , ADDRESS, PHONE, PERSONAL_ID FROM GUESTS")
                .setFirstResult((page - 1) * items_per_page).setMaxResults(items_per_page)
                .unwrap(NativeQuery.class).setResultTransformer((ListResultTransformer) (tuple, alias) -> new GuestDTO(
                        ((Number) tuple[0]).intValue(),
                        tuple[1].toString(),
                        ((Number) tuple[0]).intValue() == 1,
                        tuple[3].toString(),
                        tuple[4].toString(),
                        tuple[5].toString()
                )).getResultList();
    }


    public boolean updateGuest(GuestDTO guest) {
        return this.getEntityManager().createNativeQuery("UPDATE GUESTS SET NAME=?, ADDRESS=?, PHONE=?, PERSONAL_ID=?, GENDER=? WHERE ID=?")
                .setParameter(1, guest.getName())
                .setParameter(2, guest.getAddress())
                .setParameter(3, guest.getPhone())
                .setParameter(4, guest.getPersonal_id())
                .setParameter(5, guest.getGender() ? 1 : 0)
                .setParameter(6, guest.getId())
                .executeUpdate() > 0;
    }

    public Integer createGuest(GuestDTO guest) {
        Query a = this.getEntityManager().createNativeQuery("INSERT INTO GUESTS ( NAME, ADDRESS, PHONE, PERSONAL_ID, GENDER)  VALUES (:a,:b,:c,:d,:e)");
        a.setParameter("a", guest.getName())
                .setParameter("b", guest.getAddress())
                .setParameter("c", guest.getPhone())
                .setParameter("d", guest.getPersonal_id())
                .setParameter("e", guest.getGender() ? 1 : 0)
                .executeUpdate();
        return ((Number) this.getEntityManager().createNativeQuery("SELECT ID FROM GUESTS ORDER BY ID DESC").getResultList().get(0)).intValue();

    }

    public List<HistoryDTO> getHistory(int guestID) {
        return this.getEntityManager().createNativeQuery("SELECT B.ID, B.DATE_FROM, B.DATE_TO,R.ROOM_NUMBER, RT.TYPE , RT.RATE  FROM GUESTS G JOIN STAYED_GUEST SG on G.ID = SG.GUEST_ID JOIN ROOM_BOOKED RB on RB.ID = SG.ROOM_BOOKED_ID JOIN BOOKING B on B.ID = RB.BOOKING_ID JOIN ROOM R on R.ID = RB.ROOM_ID JOIN ROOM_TYPE RT on RT.ID = R.TYPE_ID WHERE B.STATUS=1 AND G.ID=? ORDER BY B.DATE_FROM")
                .setParameter(1, guestID)
                .unwrap(NativeQuery.class).setResultTransformer((ListResultTransformer) (tuple, alias) -> new HistoryDTO(
                        ((Number) tuple[0]).intValue(),
                        (Date) tuple[1],
                        (Date) tuple[2],
                        ((Number) tuple[3]).intValue(),
                        tuple[4].toString(),
                        ((Number) tuple[5]).intValue()
                )).getResultList();
    }

    public int getTotalPage() {
        int indexes = ((Number) this.getEntityManager().createNativeQuery("SELECT COUNT(*) FROM GUESTS")
                .getSingleResult()).intValue();
        return indexes % items_per_page == 0 ? (indexes / items_per_page) : (indexes / items_per_page + 1);
    }
}
