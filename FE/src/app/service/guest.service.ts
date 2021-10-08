import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Room} from '../model/Available.model';
import {RoomModel} from '../model/Room.model';
import {GuestModel} from '../model/Guest.model';

@Injectable({
    providedIn: 'root'
})

export class GuestService {

    private apiServerUrl = environment.apiBaseUrl;

    constructor(private http: HttpClient) {
    }


    getPages() {
        return this.http.get<any>(`${this.apiServerUrl}/guest/total-page`);
    }

    getGuestList(page:Number):Observable<GuestModel[]> {
        return this.http.get<GuestModel[]>(`${this.apiServerUrl}/guest/${page}`);
    }
}
