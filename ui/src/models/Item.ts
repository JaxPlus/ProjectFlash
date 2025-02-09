import {Themes} from "@/stores/UserStore.ts";

export default interface Item {
    id: number;
    display_name: string;
    name: Themes;
    type: string;
    description: string;
    price: number;
}