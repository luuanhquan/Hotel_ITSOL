package com.itsol.entities;

import com.itsol.enums.ACTIVE_STATUS;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;


@Entity
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    @SequenceGenerator(name = "seq", sequenceName = "BOOKING_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private int id;
    @Column(name = "ROOM_COUNT")
    private int roomCount;
    @Column(name = "DATE_FROM")
    private Date dateFrom;
    @Column(name = "DATE_TO")
    private Date dateTo;
    @Column(name = "NOTICE")
    private String notice;
    @Column(name = "STATUS")
    private int status;
    @OneToMany(mappedBy = "booking")
    private Collection<RoomBooked> roomBookedList;
    private String booker;

    public ACTIVE_STATUS getStatus() {
        return ACTIVE_STATUS.valueOf(status);
    }

    public void setStatus(ACTIVE_STATUS status) {
        this.status = status.value;
    }

}
