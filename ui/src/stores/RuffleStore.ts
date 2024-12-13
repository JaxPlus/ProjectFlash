import {defineStore} from "pinia";
import {PlayerElement, SourceAPI} from "@/models/RuffleConfig.ts";

export const useRuffleStore = defineStore('ruffleStore', () => {
    const ruffle = window.RufflePlayer.newest() as SourceAPI
    
    function getRufflePlayer(): PlayerElement {
        return ruffle.createPlayer()
    }
    
    return {
        ruffle,
        getRufflePlayer,
    }
})