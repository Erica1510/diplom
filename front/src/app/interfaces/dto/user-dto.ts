import {Role} from "../enums/role";

export interface UserDto {
    userId: number;
    firstName: string;
    lastName: string;
    email: string;
    username: string;
    role: Role; // Предполагается, что у вас уже есть перечисление Role
}


