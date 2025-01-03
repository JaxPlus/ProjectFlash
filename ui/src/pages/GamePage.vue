<script setup lang="ts">

import {useRuffleStore} from "@/stores/RuffleStore.ts";

const ruffleStore = useRuffleStore();
const player = ruffleStore.getRufflePlayer();

setTimeout(() => {
  document.getElementById("container")?.appendChild(player)
  player.load("/games/Swords and Sandals 2 (v 2.1.0).swf");
}, 1000)

</script>


<template>
  <div id="container"></div>
</template>

<style scoped>

</style>