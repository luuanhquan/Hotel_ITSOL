import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NouisliderModule } from 'ng2-nouislider';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { JwBootstrapSwitchNg2Module } from 'jw-bootstrap-switch-ng2';
import { AgmCoreModule } from '@agm/core';

import { LoginComponent } from './login/login.component';
import { ExamplesComponent } from './examples.component';
import {BookingComponent} from './bookings/booking.component';
import {GuestComponent} from './guests/guest.component';
import {RoomComponent} from './rooms/room.component';

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        NgbModule,
        NouisliderModule,
        JwBootstrapSwitchNg2Module,
        AgmCoreModule.forRoot({
            apiKey: 'YOUR_KEY_HERE'
        })
    ],
    declarations: [
        LoginComponent,
        ExamplesComponent,
        RoomComponent,
        GuestComponent,
        BookingComponent
    ]
})
export class ExamplesModule { }
