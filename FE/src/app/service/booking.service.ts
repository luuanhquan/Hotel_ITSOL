import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {BookingModel} from '../model/Booking.model';
import {AvailableModel, Room} from '../model/Available.model';
import {BookingFilterModel} from '../model/BookingFilter.model';
import {BookingCreateModel} from '../model/BookingCreate.model';
import {RoomingListModel} from '../model/RoomingList.model';

@Injectable({
    providedIn: 'root'
})

export class BookingService{

    private apiServerUrl = environment.apiBaseUrl;

    constructor(private http: HttpClient) {
    }

    public getBookingList(date_From:Date, date_to:Date): Observable<BookingModel[]> {
        return this.http.post<BookingModel[]>(`${this.apiServerUrl}/booking`, {date_from:date_From, date_to:date_to, text:""});
    };

    public getAvailable(filter: BookingFilterModel): Observable<AvailableModel[]> {
        return this.http.post<AvailableModel[]>(`${this.apiServerUrl}/room/available`, filter);
    }

    public createBooking(bookingCreate: BookingCreateModel): Observable<any> {
        return this.http.post<any>(`${this.apiServerUrl}/booking/create`, bookingCreate);

    }

    deleteBooking(id: number):Observable<any> {
        return this.http.delete<any>(`${this.apiServerUrl}/booking/delete/${id}`);
    }

    saveGuestList(roomSelected: RoomingListModel): Observable<any>  {
        return this.http.put<any>(`${this.apiServerUrl}/booking/guest/save`, roomSelected);
    }

    createRoom(roomAddorUpdate: Room, id: number): Observable<any>  {
        return this.http.post<any>(`${this.apiServerUrl}/booking/${id}/room/create`, roomAddorUpdate);
    }

    deleteRoom(room: RoomingListModel): Observable<any>  {
        return this.http.delete<any>(`${this.apiServerUrl}/booking/room/delete/${room.id}`);
    }
}
