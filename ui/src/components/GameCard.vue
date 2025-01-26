<script setup lang="ts">
import router from "@/router.ts";

const props = defineProps<{
    title: string
    gameId: number
    gamePath: string | undefined
}>()

function goToGame() {
    router.push({name: "GamePage", params: {gameId: props.gameId}});
}

</script>
<template>
    <div :style="`background-image: url('/src/assets/games/${props.gamePath}/${props.gamePath}.png')`"
        class="h-[10rem] min-w-[18rem] m-2 flex items-end bg-center bg-no-repeat bg-cover outline outline-2 outline-offset-2 outline-primary justify-center rounded-2xl bg-secondary z-5 cursor-pointer"
         @click="goToGame()">
        <p class="p-2 w-full rounded-b-2xl opacity-0 transition ease-in-out bg-gradient-to-t from-primary/70">{{ title }}</p>
    </div>
</template>

<style scoped>
div:hover > p {
    opacity: 1;
}
</style>