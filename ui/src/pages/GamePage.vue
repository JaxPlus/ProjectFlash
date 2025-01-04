<script setup lang="ts">

import {useRuffleStore} from "@/stores/RuffleStore.ts";
import {useGameStore} from "@/stores/GameStore.ts";

const ruffleStore = useRuffleStore();
const player = ruffleStore.getRufflePlayer();

const gameStore = useGameStore();


const id = localStorage.getItem('gameId');

if(id != null) {
  gameStore.getGameById(id);
}

setTimeout(() => {
  document.getElementById("container")?.appendChild(player)
  player.load("/games/"+gameStore?.game?.gamePath);
}, 1000)

</script>


<template>
  <div id="container"></div>
  <p>{{gameStore?.game?.title}}</p>
  <p>{{gameStore?.game?.description}}</p>
</template>

<style scoped>

</style>