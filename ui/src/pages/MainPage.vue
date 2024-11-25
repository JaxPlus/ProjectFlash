<script setup lang="ts">
import GameCardGroup from "@/components/GameCardGroup.vue";

const gameCards = ["Minecraft", "", "", "", "", "", ""];

</script>

<template>
    <div class="w-full h-[20rem] bg-primary-color flex flex-col items-center justify-center">
        <h1 class="text-8xl font-bold">Flash World</h1>
        <p>
            One Site. Million Games.
        </p>
    </div>
    <GameCardGroup group-title="Recommended" :game-cards="gameCards" />
    <GameCardGroup group-title="Recommended" :game-cards="gameCards" />
</template>

<style scoped>
</style>
