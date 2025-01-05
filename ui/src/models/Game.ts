export default interface Game {
    id: number;
    title: string;
    description: string;
    rating: number;
    gamePath: string;
    ownerFk: number;
}