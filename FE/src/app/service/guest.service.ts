import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {GuestModel} from '../model/Guest.model';
import {HistoryModel} from '../model/History.model';

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

    getGuestList(page: Number): Observable<GuestModel[]> {
        return this.http.get<GuestModel[]>(`${this.apiServerUrl}/guest/${page}`);
    }

    saveGuest(selectedGuest: GuestModel) {
        return this.http.post<any>(`${this.apiServerUrl}/guest/save`, selectedGuest);
    }

    findHistory(id: number): Observable<HistoryModel[]> {
        return this.http.get<HistoryModel[]>(`${this.apiServerUrl}/guest/history/${id}`);
    }
}
