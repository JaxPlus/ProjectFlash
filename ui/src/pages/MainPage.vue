<script setup lang="ts">
import GameCardGroup from "@/components/GameCardGroup.vue";
import {useGameStore} from "@/stores/GameStore.ts";
import {onMounted, ref} from "vue";

const gameStore = useGameStore();

const allGameNames: string[] = [];
const allGameIds: number[] = [];

const isLoading = ref(false)

onMounted(async () => {
  isLoading.value = true

  let allGames = await gameStore.getAllGames()

  allGames.forEach((game) => {
    allGameNames.push(game.title);
    allGameIds.push(game.id);
  })
  isLoading.value = false
})

</script>

<template>
    <div class="w-full h-[20rem] bg-primary flex flex-col items-center justify-center">
        <h1 class="text-8xl font-bold">Flash World</h1>
        <p>
            One Site. Million Games.
        </p>
    </div>
    <GameCardGroup v-if="!isLoading" group-title="Recommended" :game-cards="allGameNames" :game-ids="allGameIds" />
</template>

<style scoped>
</style>
