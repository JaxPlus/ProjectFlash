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

            <Skeleton :class="`w-[46rem] h-[35rem] m-8 my-16 ${isLoading ? 'block' : 'hidden'}`"/>

            <div :class="`${isLoading ? 'hidden' : 'block'}`">
                <div class="w-[46rem] h-[35rem] m-8 my-16" id="container"></div>
            </div>

            <div class="w-full flex flex-row justify-evenly px-36 pb-24">

                <div
                    class="bg-secondary border border-primary flex flex-col min-h-64 min-w-96 w-3/5 text-start rounded-2xl">
                    <p class="font-medium text-4xl px-12 pt-12">{{ gameStore?.game?.title }}</p>
                    <p class="font-medium text-base px-12 pt-3">Added by: </p>
                    <p class=" text-lg px-12 py-8">{{ gameStore?.game?.description }}</p>
                </div>

                <div
                    class="bg-secondary border border-primary flex flex-col items-center p-5 min-w-min min-h-64 ml-10 rounded-2xl gap-6">
                    <GameCard title="TEST" :game-id="1"/>
                    <GameCard title="TEST" :game-id="2"/>
                    <GameCard title="sadfasdf" :game-id="3"/>
                    <GameCard title="TEST" :game-id="4"/>
                    <GameCard title="TEST" :game-id="5"/>
                </div>

            </div>
        </div>
    </div>
</template>