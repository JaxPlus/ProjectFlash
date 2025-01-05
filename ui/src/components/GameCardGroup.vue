<script setup lang="ts">

import GameCard from "@/components/GameCard.vue";
import {ScrollArea} from "@/components/ui/scroll-area";

const props = defineProps<{
    groupTitle: string
    gameCards: string[]
}>()
</script>

<template>
    <div class="flex flex-col m-8 z-5">
        <h3 class="w-[18rem] p-1 px-3 ml-2 mb-4 rounded-2xl text-left bg-secondary">{{ groupTitle }}</h3>        
        <ScrollArea>
            <div class="flex pb-4 space-x-4 w-max">
                <div v-for="gameCard in gameCards">
                    <GameCard :title="gameCard" />
                </div>
            </div>
        </ScrollArea>
    </div>
</template>

<style scoped>

</style>