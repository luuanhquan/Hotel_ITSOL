package com.itsol.repositories;

import com.itsol.DTO.RoomDTO;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoomRepository extends BaseRepository   {
    public List<RoomDTO> getAll() {
        return this.getEntityManager().createNativeQuery("SELECT R.ID roomID, R.FLOOR, R.ROOM_NUMBER,RT.ID, RT.TYPE, RT.RATE FROM ROOM R LEFT JOIN ROOM_TYPE RT on R.TYPE_ID = RT.ID WHERE R.STATUS=1 ORDER BY FLOOR, ROOM_NUMBER")
                .unwrap(NativeQuery.class).setResultTransformer((ListResultTransformer)(tuple, alias)-> new RoomDTO(
                        ((Number)tuple[0]).intValue(),
                        ((Number)tuple[1]).intValue(),
                        ((Number)tuple[2]).intValue(),
                        tuple[3]==null?0:((Number)tuple[3]).intValue(),
                        tuple[4]==null?"Not set":tuple[4].toString(),
                        tuple[5]==null?0:((Number)tuple[5]).intValue()
                )).getResultList();
    }
//
//    public boolean saveOrUpdate(RoomDTO room) {
//        if(room.getId()==null){
//            return this.updateRoom(room);
//        }else {
//            return this.saveRoom(room);
//        }
//    }

    public boolean updateRoom(RoomDTO room) {
        System.out.println(room.getTypeID());
        return this.getEntityManager().createNativeQuery("UPDATE ROOM SET ROOM_NUMBER=?, FLOOR=?, TYPE_ID=? WHERE ID=?")
                .setParameter(1, room.getNumber())
                .setParameter(2, room.getFloor())
                .setParameter(3, room.getTypeID())
                .setParameter(4, room.getId())
                .executeUpdate()>0;
    }

    public boolean saveRoom(RoomDTO room) {
        return this.getEntityManager().createNativeQuery("INSERT INTO ROOM (FLOOR, ROOM_NUMBER, TYPE_ID) VALUES (?,?,?)")
                .setParameter(1, room.getFloor())
                .setParameter(2, room.getNumber())
                .setParameter(3, room.getTypeID())
                .executeUpdate()>0;
    }

    public boolean deleteRoom(int roomID){
        return this.getEntityManager().createNativeQuery("UPDATE ROOM SET STATUS=0 WHERE ID=?")
                .setParameter(1, roomID)
                .executeUpdate()>0;
    }
}
