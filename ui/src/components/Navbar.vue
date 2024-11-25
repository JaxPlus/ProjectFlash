<script setup lang="ts">
import {ref} from "vue";
import router from "@/router";
import { useColorMode } from "@vueuse/core";
import IconButton from "./IconButton.vue";
import Menu from "./Menu.vue";
import {useUserStore} from "@/stores/UserStore.ts";

const isMenu = ref(false);
// const mode = useColorMode({ disableTransition: false })
const userStore = useUserStore()
const siteTheme = useColorMode()

const changePage = (pathStr: string) => {
    router.push({ path: pathStr });
};
</script>

<template>
    <nav class="w-full max-h-[8.5rem] z-5 flex justify-between border-b-2 border-secondary-color">
        <IconButton @click="isMenu = true">
            <svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" viewBox="0 0 24 24"><path fill="none" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 8h12M6 12h12M6 16h12"/></svg>
        </IconButton>
            
        <!-- logo -->
        <div class="flex items-center hover:cursor-pointer" @click="changePage('/')">
            <img src="../assets/icons/logo256.png" alt="Flash project Icon" class="h-16 w-16">
            <h2 class="ml-5 text-5xl tiny5-regular">Flash World</h2>
        </div>

        <div class="btn-nav-group z-1 flex">
            <div>
                <IconButton v-if="siteTheme === 'light'" @click="userStore.switchToDark('dark')">
                    <svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" viewBox="0 0 24 24"><path d="M12.058 20q-3.334 0-5.667-2.333T4.058 12q0-3.039 1.98-5.27t4.904-2.634q.081 0 .159.006t.153.017q-.506.706-.801 1.57T10.158 7.5q0 2.667 1.866 4.533t4.534 1.867q.951 0 1.813-.295t1.548-.801q.012.075.017.153t.006.159q-.384 2.923-2.615 4.903T12.057 20m0-1q2.2 0 3.95-1.213t2.55-3.162q-.5.125-1 .2t-1 .075q-3.074 0-5.237-2.162T9.158 7.5q0-.5.075-1t.2-1q-1.95.8-3.163 2.55T5.058 12q0 2.9 2.05 4.95t4.95 2.05m-.25-6.75"/></svg>
                </IconButton>
                <IconButton v-if="siteTheme === 'dark'" @click="userStore.switchToDark('cafe')">
                    <svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" viewBox="0 0 32 32"><path d="M2 28h28v2H2zm22.5-17H8a2 2 0 0 0-2 2v8a5.006 5.006 0 0 0 5 5h8a5.006 5.006 0 0 0 5-5v-1h.5a4.5 4.5 0 0 0 0-9M22 21a3.003 3.003 0 0 1-3 3h-8a3.003 3.003 0 0 1-3-3v-8h14zm2.5-3H24v-5h.5a2.5 2.5 0 0 1 0 5M19 9h-2v-.146a1.99 1.99 0 0 0-1.105-1.789L13.21 5.724A3.98 3.98 0 0 1 11 2.146V1h2v1.146a1.99 1.99 0 0 0 1.106 1.789l2.683 1.341A3.98 3.98 0 0 1 19 8.854z"/></svg>
                </IconButton>
                <IconButton v-if="siteTheme === 'cafe'" @click="userStore.switchToDark('light')">
                    <svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" viewBox="0 0 24 24"><g fill="none" stroke-width="2"><circle cx="12" cy="12" r="4" stroke-linejoin="round"/><path stroke-linecap="round" d="M20 12h1M3 12h1m8 8v1m0-18v1m5.657 13.657l.707.707M5.636 5.636l.707.707m0 11.314l-.707.707M18.364 5.636l-.707.707"/></g></svg>
                </IconButton>
            </div>
            <IconButton @click="changePage('/register')">
                <svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" viewBox="0 0 24 24"><g fill="none" stroke-width="1.5"><path d="m19.5 9.5l-.71-2.605c-.274-1.005-.411-1.507-.692-1.886A2.5 2.5 0 0 0 17 4.172C16.56 4 16.04 4 15 4M4.5 9.5l.71-2.605c.274-1.005.411-1.507.692-1.886A2.5 2.5 0 0 1 7 4.172C7.44 4 7.96 4 9 4"/><path d="M9 4a1 1 0 0 1 1-1h4a1 1 0 1 1 0 2h-4a1 1 0 0 1-1-1Z"/><path stroke-linecap="round" stroke-linejoin="round" d="M4.5 18L12 9m7.5 9l-7-8.5m-8 .5L12 21l7.5-11"/><path stroke-linecap="round" d="M3.864 16.455c.546 2.183.819 3.274 1.632 3.91C6.31 21 7.435 21 9.685 21h4.63c2.25 0 3.375 0 4.19-.635c.813-.636 1.086-1.727 1.631-3.91c.858-3.432 1.287-5.147.387-6.301C19.622 9 17.853 9 14.316 9H9.685c-3.538 0-5.306 0-6.207 1.154c-.529.677-.6 1.548-.394 2.846"/></g></svg>
            </IconButton>
            <IconButton @click="changePage('/login')">
                <svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" viewBox="0 0 24 24"><path d="M5.85 17.1q1.275-.975 2.85-1.537T12 15t3.3.563t2.85 1.537q.875-1.025 1.363-2.325T20 12q0-3.325-2.337-5.663T12 4T6.337 6.338T4 12q0 1.475.488 2.775T5.85 17.1M12 13q-1.475 0-2.488-1.012T8.5 9.5t1.013-2.488T12 6t2.488 1.013T15.5 9.5t-1.012 2.488T12 13m0 9q-2.075 0-3.9-.788t-3.175-2.137T2.788 15.9T2 12t.788-3.9t2.137-3.175T8.1 2.788T12 2t3.9.788t3.175 2.137T21.213 8.1T22 12t-.788 3.9t-2.137 3.175t-3.175 2.138T12 22"/></svg>
            </IconButton>
        </div>
    </nav>
    
    <Transition name="menuLeft">
        <Menu v-if="isMenu" @closeMenu="isMenu = false"/>
    </Transition>
    <Transition name="menuBlur">
        <div v-if="isMenu" class="fixed top-0 right-0 w-full h-full bg-[#000000B2] backdrop-blur-[2px] z-10" />
    </Transition>
</template>

<style scoped>
    @import url('https://fonts.googleapis.com/css2?family=Tiny5&display=swap');

    .btn-nav-group div {
        margin-left: 0;
    }

    .menuLeft-enter-active,
    .menuLeft-leave-active {
        transition: left 0.3s ease;
    }
    
    .menuBlur-enter-active,
    .menuBlur-leave-active {
        transition: opacity 0.3s ease;
    }

    .menuLeft-enter-from,
    .menuLeft-leave-to {
        left: -100%;
    }

    .menuBlur-enter-from,
    .menuBlur-leave-to {
        opacity: 0;
    }

    .tiny5-regular {
        font-family: "Tiny5", sans-serif;
    }

</style>