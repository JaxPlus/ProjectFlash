<script setup lang="ts">

import {changePage} from "@/utility.ts";

const props = defineProps<{
    title: string
    gameId: number
}>()

function goToGame() {
  localStorage.setItem('gameId', props.gameId.toString());
  changePage('/game');
}

</script>

<template>
    <div class="h-[10rem] min-w-[18rem] m-2 flex items-end outline outline-2 outline-offset-2 outline-primary justify-center rounded-2xl bg-secondary z-5 cursor-pointer" @click="goToGame()">
        <p class="p-2 w-full rounded-b-2xl opacity-0 transition ease-in-out bg-gradient-to-t from-black">{{ title }}</p>
    </div>
</template>

<style scoped>
div:hover > p {
    opacity: 1;
}
</style>