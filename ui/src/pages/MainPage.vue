<script setup lang="ts">
import GameCardGroup from "@/components/GameCardGroup.vue";
import {useGameStore} from "@/stores/GameStore.ts";
import {onMounted, ref} from "vue";
import Game from "@/models/Game.ts";

const gameStore = useGameStore();

const tagsGames = ref<{ tagName: string, games: Game[] }[]>([]);
const isLoading = ref(false);

onMounted(async () => {
    isLoading.value = true;

    let allTags = await gameStore.getAllTags();

    for (const tag of allTags) {
        let gamesByTag = await gameStore.getAllGamesByTag(tag.id.toString());

        tagsGames.value.push({
            tagName: tag.tag_name,
            games: gamesByTag,
        });
    }

    isLoading.value = false;
})

</script>

<template>
    <div class="w-full h-[20rem] bg-primary flex flex-col items-center justify-center">
        <h1 class="text-8xl font-bold">Flash World</h1>
        <p>
            One Site. Million Games.
        </p>
    </div>
    <GameCardGroup v-for="tag in tagsGames" v-if="!isLoading" :group-title="tag.tagName" :game-cards="tag.games" />
</template>

<style scoped>
</style>
