<script setup lang="ts">
import {useGameStore} from "@/stores/GameStore.ts";
import {onMounted, ref, watch} from "vue";
import Game from "@/models/Game.ts";
import GameCard from "@/components/GameCard.vue";
import {useRoute} from "vue-router";

const route = useRoute();
let id = route.params.tagId;
let name = route.params.tagName;

const gameStore = useGameStore();

const allGames = ref<Game[]>([]);
const isLoading = ref(false);

watch(
    () => route.params.tagId,
    async (newId) => {
      id = route.params.tagId;
      name = route.params.tagName;
      allGames.value = await gameStore.getAllGamesByTag(newId.toString());
    }
)

onMounted(async () => {
  isLoading.value = true;

  allGames.value = await gameStore.getAllGamesByTag(id.toString());

  isLoading.value = false;
})

</script>

<template>
  <div class="flex justify-center items-center h-[20rem] bg-primary">
    <p class="text-5xl font-semibold">Category: {{name}}</p>
  </div>
  <br>
  <div class="flex justify-center items-center w-full gap-4 flex-wrap">
      <GameCard v-for="game in allGames" :title="game.title" :game-id="game.id"/>
  </div>
</template>

<style scoped>

</style>