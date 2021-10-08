import { NgModule } from '@angular/core';
import { CommonModule, } from '@angular/common';
import { BrowserModule  } from '@angular/platform-browser';
import { Routes, RouterModule } from '@angular/router';

import { ComponentsComponent } from './components/components.component';
import { LoginComponent } from './examples/login/login.component';
import { NucleoiconsComponent } from './components/nucleoicons/nucleoicons.component';
import {BookingComponent} from './examples/bookings/booking.component';
import {GuestComponent} from './examples/guests/guest.component';
import {RoomComponent} from './examples/rooms/room.component';

const routes: Routes =[
    { path: '', redirectTo: 'booking', pathMatch: 'full' },
    { path: 'index',                component: ComponentsComponent },
    { path: 'nucleoicons',          component: NucleoiconsComponent },
    { path: 'booking',              component: BookingComponent },
    { path: 'guest',                component: GuestComponent },
    { path: 'room',                 component: RoomComponent }
];

@NgModule({
    imports: [
        CommonModule,
        BrowserModule,
        RouterModule.forRoot(routes)
    ],
    exports: [
    ],
})
export class AppRoutingModule { }
