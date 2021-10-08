import {Component, OnInit, TemplateRef} from '@angular/core';
import * as Rellax from 'rellax';
import {RoomService} from '../../service/room.service';
import {RoomModel} from '../../model/Room.model';
import {ModalDismissReasons, NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {RoomTypeModel} from '../../model/RoomType.model';

@Component({
  selector: 'app-profile',
  templateUrl: './room.component.html'
})
export class RoomComponent implements OnInit {
  zoom: number = 14;
  lat: number = 44.445248;
  lng: number = 26.099672;
  styles: any[] = [{"featureType":"water","elementType":"geometry","stylers":[{"color":"#e9e9e9"},{"lightness":17}]},{"featureType":"landscape","elementType":"geometry","stylers":[{"color":"#f5f5f5"},{"lightness":20}]},{"featureType":"road.highway","elementType":"geometry.fill","stylers":[{"color":"#ffffff"},{"lightness":17}]},{"featureType":"road.highway","elementType":"geometry.stroke","stylers":[{"color":"#ffffff"},{"lightness":29},{"weight":0.2}]},{"featureType":"road.arterial","elementType":"geometry","stylers":[{"color":"#ffffff"},{"lightness":18}]},{"featureType":"road.local","elementType":"geometry","stylers":[{"color":"#ffffff"},{"lightness":16}]},{"featureType":"poi","elementType":"geometry","stylers":[{"color":"#f5f5f5"},{"lightness":21}]},{"featureType":"poi.park","elementType":"geometry","stylers":[{"color":"#dedede"},{"lightness":21}]},{"elementType":"labels.text.stroke","stylers":[{"visibility":"on"},{"color":"#ffffff"},{"lightness":16}]},{"elementType":"labels.text.fill","stylers":[{"saturation":36},{"color":"#333333"},{"lightness":40}]},{"elementType":"labels.icon","stylers":[{"visibility":"off"}]},{"featureType":"transit","elementType":"geometry","stylers":[{"color":"#f2f2f2"},{"lightness":19}]},{"featureType":"administrative","elementType":"geometry.fill","stylers":[{"color":"#fefefe"},{"lightness":20}]},{"featureType":"administrative","elementType":"geometry.stroke","stylers":[{"color":"#fefefe"},{"lightness":17},{"weight":1.2}]}];
    data : Date = new Date();
    focus;
    focus1;
    roomList: RoomModel[];
    closeResult: string;
    roomSelected: RoomModel;
    roomTypeList: RoomTypeModel[];
    roomCreate: RoomModel=new class implements RoomModel {
      floor: number;
      id=null;
      number: number;
      rate: number;
      type: String;
      typeID: number;
    };

    rtTemp:RoomTypeModel[] = [];
    rtDelete: RoomTypeModel[] = [];

    constructor(private roomService: RoomService,
                private modalService: NgbModal) { }

    ngOnInit() {
      var rellaxHeader = new Rellax('.rellax-header');

        var body = document.getElementsByTagName('body')[0];
        body.classList.add('profile-page');
        var navbar = document.getElementsByTagName('nav')[0];
        navbar.classList.add('navbar-transparent');
        this.getRoomList();
        this.getRoomType()
    }
    ngOnDestroy(){
        var body = document.getElementsByTagName('body')[0];
        body.classList.remove('profile-page');
        var navbar = document.getElementsByTagName('nav')[0];
        navbar.classList.remove('navbar-transparent');
    }

  openModal(content: TemplateRef<any>, size: String) {
    if (size == 'lg') {
      this.modalService.open(content, {size: 'lg'}).result.then((result) => {
        this.closeResult = `Closed with: ${result}`;
      }, (reason) => {
        this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
      });
    }
    if (size == 'sm') {
      this.modalService.open(content, {size: 'sm'}).result.then((result) => {
        this.closeResult = `Closed with: ${result}`;
      }, (reason) => {
        this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
      });
    }
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


    getRoomList(){
      this.roomService.getRoomList().subscribe((result)=>this.roomList=result);
    }

  getRoomType(){
    this.roomService.getRoomType().subscribe((result)=>this.roomTypeList=result);
  }

  setRoom(room: RoomModel) {
    this.roomSelected=JSON.parse(JSON.stringify(room));
  }

  deleteRoom(room: RoomModel) {
    this.roomService.deleteRoom(room).subscribe(()=>{this.getRoomList()})
  }

  setRoomTypeModel(){
      this.rtTemp=JSON.parse(JSON.stringify(this.roomTypeList))
  }

  deleteType(type: RoomTypeModel) {
      this.rtTemp.splice(this.rtTemp.indexOf(type),1);
  }
  createType(){
      this.rtTemp.push(new class implements RoomTypeModel {
        id= null;
        name="";
        rate=0;
      })
  }

  updateRoomType() {
    this.roomService.updateRoomType(this.rtTemp).subscribe(()=>{this.getRoomType(); this.getRoomList()})
    this.modalService.dismissAll()
  }

  setRoomType($event: RoomTypeModel) {
    this.roomCreate.typeID=$event.id;
    this.roomSelected.typeID=$event.id;
    this.roomCreate.type=$event.name
    this.roomSelected.type=$event.name
  }

  createRoom() {
    this.roomService.createRoom(this.roomCreate).subscribe(()=>{this.getRoomList()})
    this.modalService.dismissAll()
  }

  saveRoom() {
    this.roomService.updateRoom(this.roomSelected).subscribe(()=>{this.getRoomList()})
    this.modalService.dismissAll()
  }
}
