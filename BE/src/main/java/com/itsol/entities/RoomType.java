package com.itsol.entities;

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
@Table(name = "ROOM_TYPE", schema = "HOTEL", catalog = "")
public class RoomType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    @SequenceGenerator(name = "seq", sequenceName = "ROOM_TYPE_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private int id;
    @Column(name = "TYPE")
    private String type;
    @Column(name = "RATE")
    private int rate;

    @OneToMany(mappedBy = "roomType")
    private Collection<Room> roomList;


}
