import {defineStore} from "pinia";
import {useColorMode} from "@vueuse/core";
import {ref} from "vue";
import User from "@/models/User.ts";
import axios from "axios";
import {changePage} from "@/utility.ts";

type themes = "light" | "dark" | "cafe";

export const useUserStore = defineStore('userStore', () => {
    const user = ref<User>()
    
    async function getUser() {
        await axios.get('http://localhost:8080/user', {
            headers: {
                Authorization: `Bearer ${$cookies.get("access-token")}`
            }
        }).then((res) => {
            // console.log(res)
            user.value = res.data
        }).catch(async (err) => {
            // console.log(err)
            
            if ($cookies.isKey("refresh-token")) {
                await refreshToken()
                await getUser()
            }
            else {
                user.value = null;
                changePage('/login');
            }
        })
    }
    
    async function refreshToken() {
        await axios.post('http://127.0.0.1:8080/api/auth/refresh', {
            token: $cookies.get("refresh-token"),
        }).then((res) => {
            $cookies.set("access-token", res.data.token, import.meta.env.VITE_JWT_ACCESS_TOKEN_EXP, null, null, true)
        }).catch((err) => {
            // console.log(err)
        })
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
        refreshToken,
        mode,
        switchToDark,
    }
})