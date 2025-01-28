export default interface Game {
    id: number;
    title: string;
    description: string;
    rating: number;
    game_path: string;
    owner_fk: number;
}