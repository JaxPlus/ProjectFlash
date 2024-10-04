<script setup lang="ts">
import IconButton from "./IconButton.vue";
import Menu from "./Menu.vue";
import {ref} from "vue";

const isMenu = ref(false);

//nav {
//    position: absolute;
//    top: 0;
//    left: 0;
//    width: 100%;
//    display: flex;
//    flex-direction: row;
//    justify-content: space-between;
//    border-bottom: var(--primary-color) 1px solid;
//}
</script>

<template>
    <nav class="absolute top-0 left-0 w-full z-5 flex justify-between border-b-2 border-primary-color">
        <IconButton @click="isMenu = true">
            <svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" viewBox="0 0 24 24"><path fill="none" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 8h12M6 12h12M6 16h12"/></svg>
        </IconButton>
        <div class="btn-nav-group z-1 flex">
            <IconButton>
                <svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" viewBox="0 0 24 24"><g fill="none" stroke-width="2"><circle cx="12" cy="12" r="4" stroke-linejoin="round"/><path stroke-linecap="round" d="M20 12h1M3 12h1m8 8v1m0-18v1m5.657 13.657l.707.707M5.636 5.636l.707.707m0 11.314l-.707.707M18.364 5.636l-.707.707"/></g></svg>
            </IconButton>
            <IconButton>
                <svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" viewBox="0 0 24 24"><g fill="none" stroke-width="1.5"><path d="m19.5 9.5l-.71-2.605c-.274-1.005-.411-1.507-.692-1.886A2.5 2.5 0 0 0 17 4.172C16.56 4 16.04 4 15 4M4.5 9.5l.71-2.605c.274-1.005.411-1.507.692-1.886A2.5 2.5 0 0 1 7 4.172C7.44 4 7.96 4 9 4"/><path d="M9 4a1 1 0 0 1 1-1h4a1 1 0 1 1 0 2h-4a1 1 0 0 1-1-1Z"/><path stroke-linecap="round" stroke-linejoin="round" d="M4.5 18L12 9m7.5 9l-7-8.5m-8 .5L12 21l7.5-11"/><path stroke-linecap="round" d="M3.864 16.455c.546 2.183.819 3.274 1.632 3.91C6.31 21 7.435 21 9.685 21h4.63c2.25 0 3.375 0 4.19-.635c.813-.636 1.086-1.727 1.631-3.91c.858-3.432 1.287-5.147.387-6.301C19.622 9 17.853 9 14.316 9H9.685c-3.538 0-5.306 0-6.207 1.154c-.529.677-.6 1.548-.394 2.846"/></g></svg>
            </IconButton>
            <IconButton>
                <svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" viewBox="0 0 24 24"><path d="M5.85 17.1q1.275-.975 2.85-1.537T12 15t3.3.563t2.85 1.537q.875-1.025 1.363-2.325T20 12q0-3.325-2.337-5.663T12 4T6.337 6.338T4 12q0 1.475.488 2.775T5.85 17.1M12 13q-1.475 0-2.488-1.012T8.5 9.5t1.013-2.488T12 6t2.488 1.013T15.5 9.5t-1.012 2.488T12 13m0 9q-2.075 0-3.9-.788t-3.175-2.137T2.788 15.9T2 12t.788-3.9t2.137-3.175T8.1 2.788T12 2t3.9.788t3.175 2.137T21.213 8.1T22 12t-.788 3.9t-2.137 3.175t-3.175 2.138T12 22"/></svg>
            </IconButton>
        </div>
    </nav>
    
    <Transition name="menuLeft">
        <Menu v-if="isMenu" @closeMenu="isMenu = false"/>
    </Transition>
    <Transition name="menuBlur">
        <div v-if="isMenu" class="fixed top-0 right-0 w-full h-full bg-[#000000B2] backdrop-blur-[2px] z-5" />
    </Transition>
</template>

<style scoped>
    .btn-nav-group div {
        margin-left: 0;
        color: #161A1D;
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
</style>