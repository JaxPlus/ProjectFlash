import {defineStore} from "pinia";
import axios from "axios";
import {ref} from "vue";
import Game from "@/models/Game.ts";
import Tag from "@/models/Tag.ts";

export const useGameStore = defineStore('gameStore', () => {
    const game = ref<Game>();

    async function getAllGames(): Promise<Game[]>
    {
        const temp = await axios.get('http://localhost:8080/games');

        return temp.data
    }

    async function getGameById(id: string) {
        const temp = await axios.get('http://localhost:8080/games/'+id);

        game.value = temp.data;
    }

    async function getAllTags(): Promise<Tag[]>
    {
        const temp = await axios.get('http://localhost:8080/tags');

        return temp.data
    }

    async function getAllGamesByTag(id: string): Promise<Game[]>
    {
        const temp = await axios.get('http://localhost:8080/games/tag/'+id);

        return temp.data
    }
    return {
        game,
        getAllGames,
        getGameById,
        getAllTags,
        getAllGamesByTag,
    }
})