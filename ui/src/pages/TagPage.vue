<script setup lang="ts">
import {useGameStore} from "@/stores/GameStore.ts";
import {nextTick, onMounted, ref, watch} from "vue";
import Game from "@/models/Game.ts";
import GameCard from "@/components/GameCard.vue";
import {useRoute} from "vue-router";
import {Skeleton} from "@/components/ui/skeleton";

const route = useRoute();
let id = route.params.tagId;
let name = route.params.tagName;

const gameStore = useGameStore();

const allGames = ref<Game[]>([]);
const isLoading = ref(false);
const renderComponent = ref(true);

watch(
    () => route.params.tagId,
    async (newId) => {
        id = route.params.tagId;
        name = route.params.tagName;
        allGames.value = await gameStore.getAllGamesByTag(newId.toString());
        await forceRerender();
    }
)

onMounted(async () => {
    isLoading.value = true;

    allGames.value = await gameStore.getAllGamesByTag(id.toString());

    isLoading.value = false;
})

const forceRerender = async () => {
    renderComponent.value = false;
    await nextTick();
    renderComponent.value = true;
};

</script>

<template>
    <div class="flex justify-center items-center h-[20rem] bg-primary">
        <p class="text-5xl font-semibold">Category: {{ name }}</p>
    </div>
    <br>
    <div class="flex justify-center items-center w-full gap-4 flex-wrap">
        <GameCard v-for="game in allGames" v-if="renderComponent" :title="game.title" :game-id="game.id"/>
        <Skeleton
            v-for="_ in 5"
            class="h-[10rem] min-w-[18rem] m-2 flex items-end bg-center bg-no-repeat bg-cover outline outline-2 outline-offset-2 outline-primary justify-center rounded-2xl bg-secondary z-5 cursor-pointer"
            v-if="isLoading" />
    </div>
</template>