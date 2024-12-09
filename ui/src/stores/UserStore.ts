import {defineStore} from "pinia";
import {useColorMode} from "@vueuse/core";
import {ref} from "vue";
import User from "@/models/User.ts";
import axios from "axios";

type themes = "light" | "dark" | "cafe";

export const useUserStore = defineStore('userStore', () => {
    const user = ref<User>()
    
    async function getUser() {
        const temp = await axios.get('http://localhost:8080/user', {
            headers: {
                Authorization: `Bearer ${$cookies.get("access-token")}`
            }
        })
        console.log(temp)

        user.value = temp.data
    }
    
    const mode = useColorMode({
        disableTransition: false,
        modes: {
            light: 'light',
            dark: 'dark',
            cafe: 'cafe'
        }
    })
    
    function switchToDark(changeTo: themes) {
        mode.value = changeTo
    }
    
    return {
        user, 
        getUser,
        mode,
        switchToDark,
    }
})