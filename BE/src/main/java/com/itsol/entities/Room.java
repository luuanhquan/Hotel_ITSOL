package com.itsol.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.itsol.enums.ACTIVE_STATUS;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    @SequenceGenerator(name = "seq", sequenceName = "ROOM_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private int id;
    @Column(name = "FLOOR")
    private int floor;
    @Column(name = "ROOM_NUMBER")
    private int roomNumber;
    @Column(name = "STATUS")
    private int status;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "Type_id", referencedColumnName = "id", nullable = false)
    private RoomType roomType;
    @OneToMany(mappedBy = "room")
    private Collection<RoomBooked> roomBookedList;

    public ACTIVE_STATUS getStatus() {
        return ACTIVE_STATUS.valueOf(status);
    }

    public void setStatus(ACTIVE_STATUS status) {
        this.status = status.value;
    }

}
