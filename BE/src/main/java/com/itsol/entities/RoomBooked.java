package com.itsol.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "ROOM_BOOKED", schema = "HOTEL")
public class RoomBooked {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    @SequenceGenerator(name = "seq", sequenceName = "ROOM_BOOK_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private int id;
    @Column(name = "NOTICE")
    private String notice;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "BOOKING_ID", referencedColumnName = "ID", nullable = false)
    private Booking booking;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "ROOM_ID", referencedColumnName = "ID", nullable = false)
    private Room room;
    @OneToMany(mappedBy = "roomBooked")
    private Collection<StayedGuest> stayedGuestList;
}
