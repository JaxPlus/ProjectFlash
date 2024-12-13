<script setup lang="ts">
import Navbar from "./components/Navbar.vue";
// import {useRuffleStore} from "@/stores/RuffleStore.ts";

// const ruffleStore = useRuffleStore();
// const player = ruffleStore.getRufflePlayer();
//
// window.addEventListener("DOMContentLoaded", () => {
//     let container = document.getElementById("container");
//     container.appendChild(player);
//     player.load("public/gameTest/BindingOfIsaac.swf");
// })

</script>

<template>
    <Navbar/>
    <main>
<!--        <div id="container"></div>-->
        <RouterView/>
    </main>
</template>

<style>
body {
    color: hsl(var(--text-color));
    background-color: hsl(var(--background-color));
    margin: 0;
    min-width: 320px;
    min-height: 100vh;
}

svg {
    width: 3em;
    height: 3em;
    fill: hsl(var(--background-color));
    stroke: hsl(var(--background-color));
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
