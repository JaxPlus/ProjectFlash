import {defineStore} from "pinia";
import {useColorMode} from "@vueuse/core";

type themes = "light" | "dark" | "cafe";

export const useUserStore = defineStore('userStore', () => {
    let mode = useColorMode({
        disableTransition: false,
        modes: {
            light: 'light',
            dark: 'dark',
            cafe: 'cafe'
        }
    })
    
    function switchToDark(changeTo: themes) {
        mode.value = changeTo
        console.log(mode)
    }
    
    return {
        mode,
        switchToDark,
    }
})