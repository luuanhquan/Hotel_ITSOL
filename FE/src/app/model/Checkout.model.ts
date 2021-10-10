type Guest = { id: number, name: string, gender: boolean };

export interface CheckoutModel {
    room_number: number;
    guest_list: Guest[]
}
