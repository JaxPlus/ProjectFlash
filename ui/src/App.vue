<script setup lang="ts">
import Navbar from "./components/Navbar.vue";
import {useUserStore} from "@/stores/UserStore.ts";
useUserStore()
</script>

<template>
    <Navbar/>
    <main>
        <RouterView/>
    </main>
</template>

<style>
body {
    color: hsl(var(--text-color));
    background-color: hsl(var(--background));
    margin: 0;
    min-width: 320px;
    min-height: 100vh;
}

svg {
    width: 3em;
    height: 3em;
    fill: hsl(var(--background));
    stroke: hsl(var(--background));
}

@media (prefers-color-scheme: light) {
    :root {
        color: #161A1D;
        background-color: #D3D3D3;
    }

    a:hover {
        color: #747bff;
    }

    button {
        background-color: #f9f9f9;
    }
}
</style>
