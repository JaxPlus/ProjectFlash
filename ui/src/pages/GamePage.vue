<script setup lang="ts">
import {useRuffleStore} from "@/stores/RuffleStore.ts";
import {useGameStore} from "@/stores/GameStore.ts";
import {ref} from "vue";
import {Skeleton} from "@/components/ui/skeleton";
import GameCard from "@/components/GameCard.vue";

const ruffleStore = useRuffleStore();
const player = ruffleStore.getRufflePlayer();

const gameStore = useGameStore();
const isLoading = ref(true);

const id = localStorage.getItem('gameId');

if (id != null) {
    gameStore.getGameById(id);
}

setTimeout(() => {
    player.style.height = "100%";
    player.style.width = "100%";
    document.getElementById("container")?.appendChild(player)

    player.load("../src/assets/games/" + gameStore?.game?.gamePath);
    console.log(player.loadedConfig)
    isLoading.value = false;
}, 1000)

</script>


<template>
    <div class="w-full h-full">
        <div class="w-full flex flex-col items-center">
            <Skeleton :class="`w-[46rem] h-[35rem] m-8 ${isLoading ? 'block' : 'hidden'}`"/>
            <div :class="` ${isLoading ? 'hidden' : 'block'}`">
                <div class="w-[46rem] h-[35rem] m-8" id="container"></div>
            </div>
            <div class="w-full flex flex-row p-10">
                <div
                    class="bg-secondary border border-primary flex flex-col items-center min-h-64 text-start rounded-2xl">
                    <p>{{ gameStore?.game?.title }}</p>
                    <p>{{ gameStore?.game?.description }}</p>
                </div>
                <div
                    class="bg-secondary border border-primary flex flex-col items-center w-2/5 min-w-min min-h-64 ml-10 rounded-2xl">
                    <GameCard title="TEST" :game-id="1"/>
                    <GameCard title="TEST" :game-id="2"/>
                    <GameCard title="TEST" :game-id="3"/>
                    <GameCard title="TEST" :game-id="4"/>
                    <GameCard title="TEST" :game-id="5"/>
                </div>
            </div>
        </div>
    </div>
</template>