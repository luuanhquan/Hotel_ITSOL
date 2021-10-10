import {Component, OnInit, TemplateRef} from '@angular/core';
import * as Rellax from 'rellax';
import {GuestModel} from '../../model/Guest.model';
import {GuestService} from '../../service/guest.service';
import {ModalDismissReasons, NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {HistoryModel} from '../../model/History.model';

@Component({
    selector: 'app-profile',
    templateUrl: './guest.component.html'
})
export class GuestComponent implements OnInit {
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
    focus;
    focus1;
    closeResult: string;
    guestList: GuestModel[];
    page: Number = 1;
    totalPage: Number;
    selectedGuest: GuestModel;
    title: String;
    guest_history: HistoryModel[];

    constructor(private guestService: GuestService,
                private modalService: NgbModal,) {
    }

    ngOnInit() {
        var rellaxHeader = new Rellax('.rellax-header');

        var body = document.getElementsByTagName('body')[0];
        body.classList.add('profile-page');
        var navbar = document.getElementsByTagName('nav')[0];
        navbar.classList.add('navbar-transparent');
        this.getGuestList();
    }

    ngOnDestroy() {
        var body = document.getElementsByTagName('body')[0];
        body.classList.remove('profile-page');
        var navbar = document.getElementsByTagName('nav')[0];
        navbar.classList.remove('navbar-transparent');
    }

    setGuest(guest: GuestModel) {
        this.selectedGuest = guest;
    }

    openModal(content: TemplateRef<any>, size: string) {
        this.modalService.open(content, {size: size}).result.then((result) => {
            this.closeResult = `Closed with: ${result}`;
        }, (reason) => {
            this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
        });
    }

    changeGender() {
        this.selectedGuest.gender = !this.selectedGuest.gender
    }

    saveGuest() {
        this.title = 'Edit Guest'
        this.guestService.saveGuest(this.selectedGuest).subscribe(() => {
            this.getGuestList();
            this.modalService.dismissAll()
        });
    }

    createGuest() {
        this.title = 'New Guest'
        this.selectedGuest = new class implements GuestModel {
            address = '';
            gender = false;
            id = null;
            name = '';
            personal_id = '';
            phone = '';
        }
    }

    findHistory(id: number) {
        this.guestService.findHistory(id).subscribe((data) => {
            this.guest_history = data
        })

    }

    private getGuestList() {
        this.guestService.getPages().subscribe(data => {
            this.totalPage = data
            this.guestService.getGuestList(this.page).subscribe(data => (this.guestList = data))
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
}
