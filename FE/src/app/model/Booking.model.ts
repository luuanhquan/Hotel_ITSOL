import {RoomingListModel} from './RoomingList.model';

export interface BookingModel{
    id: number;
    booker: String;
    room_count: number;
    date_from: Date;
    date_to: Date;
    notice?: String;
    roomingList: RoomingListModel[];
}
