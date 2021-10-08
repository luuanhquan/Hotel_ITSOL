import {Component, OnInit, TemplateRef} from '@angular/core';
import * as Rellax from 'rellax';
import {ModalDismissReasons, NgbDate, NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {BookingModel} from '../../model/Booking.model';
import {BookingService} from '../../service/booking.service';
import {AvailableModel, Room} from '../../model/Available.model';
import {BookingFilterModel} from '../../model/BookingFilter.model';
import {BookingCreateModel} from '../../model/BookingCreate.model';
import {Router} from '@angular/router';
import {RoomingListModel} from '../../model/RoomingList.model';
import {GuestModel} from '../../model/Guest.model';
import {first} from 'rxjs/operators';
import {RouterModule} from '@angular/router';


@Component({
    selector: 'app-profile',
    templateUrl: './booking.component.html'
})
export class BookingComponent implements OnInit {
    zoom: number = 14;
    lat: number = 44.445248;
    lng: number = 26.099672;
    styles: any[] = [{
        'featureType': 'water',
        'elementType': 'geometry',
        'stylers': [{'color': '#e9e9e9'}, {'lightness': 17}]
    }, {
        'featureType': 'landscape',
        'elementType': 'geometry',
        'stylers': [{'color': '#f5f5f5'}, {'lightness': 20}]
    }, {
        'featureType': 'road.highway',
        'elementType': 'geometry.fill',
        'stylers': [{'color': '#ffffff'}, {'lightness': 17}]
    }, {
        'featureType': 'road.highway',
        'elementType': 'geometry.stroke',
        'stylers': [{'color': '#ffffff'}, {'lightness': 29}, {'weight': 0.2}]
    }, {
        'featureType': 'road.arterial',
        'elementType': 'geometry',
        'stylers': [{'color': '#ffffff'}, {'lightness': 18}]
    }, {
        'featureType': 'road.local',
        'elementType': 'geometry',
        'stylers': [{'color': '#ffffff'}, {'lightness': 16}]
    }, {'featureType': 'poi', 'elementType': 'geometry', 'stylers': [{'color': '#f5f5f5'}, {'lightness': 21}]}, {
        'featureType': 'poi.park',
        'elementType': 'geometry',
        'stylers': [{'color': '#dedede'}, {'lightness': 21}]
    }, {
        'elementType': 'labels.text.stroke',
        'stylers': [{'visibility': 'on'}, {'color': '#ffffff'}, {'lightness': 16}]
    }, {
        'elementType': 'labels.text.fill',
        'stylers': [{'saturation': 36}, {'color': '#333333'}, {'lightness': 40}]
    }, {'elementType': 'labels.icon', 'stylers': [{'visibility': 'off'}]}, {
        'featureType': 'transit',
        'elementType': 'geometry',
        'stylers': [{'color': '#f2f2f2'}, {'lightness': 19}]
    }, {
        'featureType': 'administrative',
        'elementType': 'geometry.fill',
        'stylers': [{'color': '#fefefe'}, {'lightness': 20}]
    }, {
        'featureType': 'administrative',
        'elementType': 'geometry.stroke',
        'stylers': [{'color': '#fefefe'}, {'lightness': 17}, {'weight': 1.2}]
    }];
    data: Date = new Date();
    date_from: NgbDate;
    date_to: NgbDate;
    focus;
    focus1;
    bookingList: BookingModel[];
    closeResult: string;
    date_from_create: NgbDate;
    date_to_create: NgbDate;
    available: AvailableModel[];
    roomList: Room[]=[];
    bookingCreate: BookingCreateModel = new class implements BookingCreateModel {
        booker = "";
        date_from: Date;
        date_to: Date;
        notice = "";
        roomList = [];
    };

    roomEditSelected: RoomingListModel;
    bookingSelected: BookingModel;
    roomAddorUpdate: Room;
    public fields: Object = {id: 'text', number: 'icon'};
    page: 40;

    constructor(
        private bookingService: BookingService,
        private modalService: NgbModal,
        private router: Router) {
    }


    ngOnInit() {
        var rellaxHeader = new Rellax('.rellax-header');
        var body = document.getElementsByTagName('body')[0];
        body.classList.add('profile-page');
        var navbar = document.getElementsByTagName('nav')[0];
        navbar.classList.add('navbar-transparent');
        let today = new Date;
        this.date_from = new NgbDate(2021, 10, 2)
        // this.date_from= new NgbDate(today.getFullYear(),today.getMonth(), today.getDate())
        this.date_to = this.date_from;
        this.search()
    }

    ngOnDestroy() {
        var body = document.getElementsByTagName('body')[0];
        body.classList.remove('profile-page');
        var navbar = document.getElementsByTagName('nav')[0];
        navbar.classList.remove('navbar-transparent');
    }

    search() {
        let from = this.convert(this.date_from);
        let to = this.convert(this.date_to)
        if (to < from) {
            this.date_to = this.date_from
            to = from;
        }
        this.bookingService.getBookingList(from, to).subscribe(value => {
            this.bookingList = value
        });
    }

    convert(date: NgbDate) {
        return new Date(date.year, date.month - 1, date.day)
    }

    openModal(content: TemplateRef<any>, size: string) {
            this.modalService.open(content, {size: size}).result.then((result) => {
                this.closeResult = `Closed with: ${result}`;
            }, (reason) => {
                this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
            });
    }

    private getDismissReason(reason: any): string {
        if (reason === ModalDismissReasons.ESC) {
            return 'by pressing ESC';
        } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
            return 'by clicking on a backdrop';
        } else {
            return `with: ${reason}`;
        }

    }


    getAvailable(date_from_create: NgbDate, date_to_create: NgbDate) {
        this.bookingCreate.roomList = []
        if (this.date_from_create != null && this.date_to_create != null) {
            let filter: BookingFilterModel
            filter = new class implements BookingFilterModel {
                date_from: Date = null;
                date_to: Date = null;
            }
            filter.date_to = this.convert(date_to_create)
            filter.date_from = this.convert(date_from_create)
            this.bookingService.getAvailable(filter).subscribe(
                response => {
                    this.available = response;
                }
            );
        }
    }

    getAvailableByDate(date_from_create: Date, date_to_create: Date) {
        this.bookingCreate.roomList = []
        let filter: BookingFilterModel
        filter = new class implements BookingFilterModel {
            date_from: Date = date_from_create;
            date_to: Date = date_to_create;
        }
        this.bookingService.getAvailable(filter).subscribe(
            response => {
                this.available = response;
            }
        );

    }

    changeSelect(id: number) {
        let index = this.bookingCreate.roomList.indexOf(id);
        if (index == -1) {
            this.bookingCreate.roomList.push(id)
        } else {
            this.bookingCreate.roomList.splice(index, 1)
        }
    }


    setSelectedBooking(booking: BookingModel) {
        this.bookingSelected = JSON.parse(JSON.stringify(booking));
    }


    createBooking(date_from_create: NgbDate, date_to_create: NgbDate) {
        // if(this.date_to.before(this.date_from)){
        //    date_to_create=date_from_create
        // }
        this.bookingCreate.date_from = this.convert(date_from_create)
        this.bookingCreate.date_to = this.convert(date_to_create)
        console.log(this.bookingCreate)
        this.bookingService.createBooking(this.bookingCreate).subscribe(()=>{
            this.search()});
        this.modalService.dismissAll()
    }

    deleteBooking(id: number) {
        this.bookingService.deleteBooking(id).subscribe(()=>{this.search()})

    }

    changeGender(i: number) {
        this.roomEditSelected.guestList[i].gender = this.roomEditSelected.guestList[i].gender == 'Male' ? 'Female' : 'Male';

    }

    setRoom(room: RoomingListModel) {
        this.roomEditSelected = JSON.parse(JSON.stringify(room));
    }

    addGuest() {
        let selectedGuest = this.roomEditSelected.guestList[this.roomEditSelected.guestList.length - 1];
        let newGuest: GuestModel;
        newGuest = new class implements GuestModel {
            address = '';
            gender = 'Male';
            id = null;
            name = '';
            personal_id = '';
            phone = '';
        }
        this.roomEditSelected.guestList.push(newGuest)
    }

    deleteGuest(index: number) {
        this.roomEditSelected.guestList.splice(index,1)
    }

    saveGuestList() {
        this.bookingService.saveGuestList(this.roomEditSelected).pipe(first()).subscribe(()=>
        {this.search()})
        this.modalService.dismissAll()
    }

    setRoomList(roomList: Room[]) {
        this.roomList = roomList;

    }

    createRoom() {
        console.log(this.roomAddorUpdate, this.bookingSelected.id)
        this.bookingService.createRoom(this.roomAddorUpdate, this.bookingSelected.id).subscribe(()=>{this.search()})
        this.modalService.dismissAll()
    }


    setAddOrUpdateRoom(room: Room) {
        this.roomAddorUpdate = room;
    }

    deleteRoom(room: RoomingListModel) {
        this.bookingService.deleteRoom(room).subscribe(()=>{
            this.search()});
    }
}
