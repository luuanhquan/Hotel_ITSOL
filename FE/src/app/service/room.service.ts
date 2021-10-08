import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {BookingModel} from '../model/Booking.model';
import {AvailableModel, Room} from '../model/Available.model';
import {BookingFilterModel} from '../model/BookingFilter.model';
import {BookingCreateModel} from '../model/BookingCreate.model';
import {RoomingListModel} from '../model/RoomingList.model';
import {RoomComponent} from '../examples/rooms/room.component';
import {RoomModel} from '../model/Room.model';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {RoomTypeModel} from '../model/RoomType.model';

@Injectable({
    providedIn: 'root'
})

export class RoomService{

    private apiServerUrl = environment.apiBaseUrl;

    constructor(private http: HttpClient) {
    }

    getRoomList(): Observable<RoomModel[]> {
        return this.http.get<RoomModel[]>(`${this.apiServerUrl}/room`);
    }

    deleteRoom(room: RoomModel) {
        return this.http.delete<RoomModel[]>(`${this.apiServerUrl}/room/delete/${room.id}`);
    }

    deleteType(index: number): Observable<any> {
        return this.http.delete<any>(`${this.apiServerUrl}/room/type/delete/${index}`);
    }

    getRoomType():Observable<RoomTypeModel[]> {
        return this.http.get<RoomTypeModel[]>(`${this.apiServerUrl}/room/type`);
    }

    updateRoomType(rtTemp: RoomTypeModel[]) {
        return this.http.post<RoomTypeModel[]>(`${this.apiServerUrl}/room/type/update`, rtTemp);

    }

    createRoom(roomCreate: RoomModel) :Observable<any>{
        return this.http.post<RoomTypeModel[]>(`${this.apiServerUrl}/room/create`, roomCreate);
    }

    updateRoom(roomSelected: RoomModel) {
        return this.http.put<RoomTypeModel[]>(`${this.apiServerUrl}/room/update`, roomSelected);
    }
}
