<script setup lang="ts">
import {useRuffleStore} from "@/stores/RuffleStore.ts";
import {useGameStore} from "@/stores/GameStore.ts";
import {ref} from "vue";
import {Skeleton} from "@/components/ui/skeleton";

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
        <Skeleton :class="`w-[46rem] h-[35rem] ${isLoading ? 'block' : 'hidden'}`" />
        <div :class="` ${isLoading ? 'hidden' : 'block'}`">
            <div class="w-[46rem] h-[35rem]" id="container"></div>
            <div class="bg-secondary border border-primary flex flex-col items-center min-h-28 text-start rounded-2xl">
                <p>{{ gameStore?.game?.title }}</p>
                <p>{{ gameStore?.game?.description }}</p>
            </div>
        </div>
    </div>
</template>