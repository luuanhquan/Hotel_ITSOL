export interface Room {
    id: number,
    number: number
}

export interface AvailableModel{
    rtID:number;
    roomType:String;
    rate:number;
    roomList: Room[]
}
