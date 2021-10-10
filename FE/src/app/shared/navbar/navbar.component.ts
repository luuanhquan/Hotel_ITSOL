import {Component, ElementRef, OnInit} from '@angular/core';
import {Location} from '@angular/common';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import {CheckoutModel} from '../../model/Checkout.model';

@Component({
    selector: 'app-navbar',
    templateUrl: './navbar.component.html'
})
export class NavbarComponent implements OnInit {
    title = 'ozenero';
    description = 'Angular-WebSocket Demo';
    message: string;
    disabled = true;
    name: string = 'QUAN';
    noti: boolean = false;
    listCheckOut: CheckoutModel[];
    alert = {
        id: 1,
        type: 'success',
        strong: 'Well done!',
        message: 'You successfully read this important alert message.',
        icon: 'ui-2_like'
    };
    private toggleButton: any;
    private sidebarVisible: boolean;
    private stompClient = null;

    constructor(public location: Location, private element: ElementRef,
    ) {
        this.sidebarVisible = false;

    }

    ngOnInit() {
        const navbar: HTMLElement = this.element.nativeElement;
        this.toggleButton = navbar.getElementsByClassName('navbar-toggler')[0];
        this.connect()
    }

    sidebarOpen() {
        const toggleButton = this.toggleButton;
        const html = document.getElementsByTagName('html')[0];
        setTimeout(function () {
            toggleButton.classList.add('toggled');
        }, 500);
        html.classList.add('nav-open');

        this.sidebarVisible = true;
    };

    sidebarClose() {
        const html = document.getElementsByTagName('html')[0];
        // console.log(html);
        this.toggleButton.classList.remove('toggled');
        this.sidebarVisible = false;
        html.classList.remove('nav-open');
    };

    sidebarToggle() {
        // const toggleButton = this.toggleButton;
        // const body = document.getElementsByTagName('body')[0];
        if (this.sidebarVisible === false) {
            this.sidebarOpen();
        } else {
            this.sidebarClose();
        }
    };

    isDocumentation() {
        var titlee = this.location.prepareExternalUrl(this.location.path());
        if (titlee === '/documentation') {
            return true;
        } else {
            return false;
        }
    }

    connect() {
        const socket = new SockJS('http://localhost:8888/ws');
        this.stompClient = Stomp.over(socket);

        const _this = this;
        this.stompClient.connect({}, function (frame) {
            _this.stompClient.subscribe('/topic/greetings', function (greeting) {
                _this.listCheckOut = JSON.parse(greeting.body);
                _this.noti = true
            });
        });
    }


}
