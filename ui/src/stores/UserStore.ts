import {defineStore} from "pinia";
import {useColorMode} from "@vueuse/core";
import {ref} from "vue";
import User from "@/models/User.ts";
import axios from "axios";
import {changePage, retryAction} from "@/utility.ts";
import Item from "@/models/Item.ts";

type themes = "light" | "dark" | "cafe" | "eva" | "unicorn" | "violet" | "pink" | string;

export const useUserStore = defineStore('userStore', () => {
    const user = ref<User | null>({})
    const userProfile = ref("");
    
    async function getUser() {
        await axios.get('http://localhost:8080/user', {
            headers: {
                Authorization: `Bearer ${$cookies.get("access-token")}`
            }
        }).then((res) => {
            user.value = res.data
        }).catch(async () => {
            // console.log(err)
            
            await retryAction(getUser, () => {
                user.value = null;
            })
            
            // if ($cookies.isKey("refresh-token")) {
            //     await refreshToken()
            //     // await getUser()
            // }
            // else {
            //     user.value = null;
            //     changePage('/login');
            // }
        })
    }
    
    async function refreshToken() {
        return await axios.post('http://127.0.0.1:8080/api/auth/refresh', {
            token: $cookies.get("refresh-token"),
        }).then((res) => {
            $cookies.set("access-token", res.data.token, import.meta.env.VITE_JWT_ACCESS_TOKEN_EXP, null, null, true)
            return true;
        }).catch(() => {
            // console.log(err)
            $cookies.remove("refresh-token");
            $cookies.remove("access-token");
            changePage("/login");
            return false;
        })
    }
    
    async function editUsername(username: string) {
        await axios.patch('http://localhost:8080/user/username', {
            editUsername: username
        }, {
            headers: {
                Authorization: `Bearer ${$cookies.get("access-token")}`
            }
        }).then((res) => {
            if (res.data) {
                user.value = {
                    username: username,
                    email: user.value?.email
                }
            }
        }).catch(async (err) => {
            if ($cookies.isKey("refresh-token")) {
                await refreshToken()
                await editUsername(username)
            }
            else {
                user.value = null;
                changePage('/login');
            }
        })
    }
    
    async function getInventoryItems(): Promise<Item[]> {
        let items: Item[] = [];
        for (const itemId of user.value?.inventory) {
            await axios.get(`http://localhost:8080/shop/${itemId}`)
                .then(res => {
                    items.push(res.data)
                })
                .catch((err) => {
                    console.log(err)
                })
        }
        
        return items;
    }
    
    async function editProfile(img: string) {
        await axios.post('http://localhost:8080/user/profile', {
            img: img,
        }, {
            headers: {
                Authorization: `Bearer ${$cookies.get("access-token")}`
            }
        }).then(res => {
            console.log(res.data)
        }).catch(async (err) => {
            console.log(err)
            // await retryAction(editProfile)
        })
    }
    
    function isUserLoggedIn(): boolean {
        return user.value !== null;
    }

    function logOut() {
        $cookies.remove("access-token");
        $cookies.remove("refresh-token");
        switchTheme("light");
        user.value = {};

        changePage("/");
    }
    
    const mode = useColorMode({
        disableTransition: false,
        modes: {
            light: 'light',
            dark: 'dark',
            cafe: 'cafe',
            unicorn: 'unicorn',
            eva: 'eva',
            violet: 'violet',
            pink: 'pink',
        }
    })
    
    function switchTheme(changeTo: themes) {
        mode.value = changeTo
    }
    
    return {
        user,
        userProfile,
        getUser,
        refreshToken,
        editUsername,
        getInventoryItems,
        editProfile,
        isUserLoggedIn,
        logOut,
        mode,
        switchTheme,
    }
})