package com.itsol.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "STAYED_GUEST", schema = "HOTEL", catalog = "")
public class StayedGuest {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    @SequenceGenerator(name = "seq", sequenceName = "STAYED_GUEST_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private boolean id;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "GUEST_ID", referencedColumnName = "ID", nullable = false)
    private Guests guest;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "ROOM_BOOKED_ID", referencedColumnName = "ID", nullable = false)
    private RoomBooked roomBooked;
}



