import {GuestModel} from './Guest.model';

export interface RoomingListModel {
    id: number;
    room_number: number;
    rate: number;
    roomType: String;
    guestList: GuestModel[];
}
