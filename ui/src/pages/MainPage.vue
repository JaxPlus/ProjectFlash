<script setup lang="ts">
import GameCardGroup from "@/components/GameCardGroup.vue";
import {useGameStore} from "@/stores/GameStore.ts";
import {onMounted, ref} from "vue";

const gameStore = useGameStore();

const allGameNames: string[] = [];
const allGameIds: number[] = [];

const allTagNames: string[] = [];
const allTagIds: number[] = [];

const isLoading = ref(false)

onMounted(async () => {
  isLoading.value = true

  let allGames = await gameStore.getAllGames()
  let allTags = await gameStore.getAllTags()

  allGames.forEach((game) => {
    allGameNames.push(game.title);
    allGameIds.push(game.id);
  })

  //========= od tego momentu zaczynam kombinować ==============

  // for (const tag of allTags) {
  //   let gamesByTag = await gameStore.getAllGamesByTag(tag.id);
  //
  //   document.createElement('')
  //
  //   allTagNames.push(tag.tagName);
  //   allTagIds.push(tag.id);
  // }

  //const temp = new GameCardGroup("fghrtdsfh", allTagIds, allTagIds);


  //const t2 = extendRef(temp, {groupTitle: 'rgderfghtfh', gameCards: allGameNames, gameIds: allGameIds});
  //console.log(temp.);
  //document.appendChild(temp.$el);

  // const t = extend(GameCardGroup);
  // const tt = new t();
  // tt.$mount();
  //
  // document.appendChild(tt);




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
    <GameCardGroup v-for="tag in allTagNames" v-if="!isLoading" :group-title="tag" :game-cards="allGameNames" :game-ids="allGameIds" />
</template>

<style scoped>
</style>
