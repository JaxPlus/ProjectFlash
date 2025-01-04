import {defineStore} from "pinia";
import axios from "axios";
import {ref} from "vue";
import Game from "@/models/Game.ts";

export const useGameStore = defineStore('gameStore', () => {

    const game = ref<Game>();

    async function getGameById(id: string) {
        const temp = await axios.get('http://localhost:8080/games/'+id);

        game.value = temp.data;
    }


    return {
        game,
        getGameById,
    }
})