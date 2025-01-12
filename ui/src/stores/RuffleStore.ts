import {defineStore} from "pinia";
import {PlayerElement} from "@/models/RuffleConfig.ts";

export const useRuffleStore = defineStore('ruffleStore', () => {
    const ruffle = window.RufflePlayer;
    
    function getRufflePlayer(): PlayerElement {
        ruffle.config = {
            allowFullscreen: true,
            wmode: "opaque",
        }
        return ruffle.newest().createPlayer()
    }
    
    return {
        ruffle,
        getRufflePlayer,
    }
})