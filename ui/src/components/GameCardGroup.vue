﻿<script setup lang="ts">
import GameCard from "@/components/GameCard.vue";
import {ScrollArea} from "@/components/ui/scroll-area";
import Game from "@/models/Game.ts";

const props = defineProps<{
    groupTitle: string;
    gameCards: Game[];
}>()
</script>

<template>
    <div class="flex flex-col m-8 z-5">
        <h3 class="w-[18rem] p-1 px-3 ml-2 mb-4 rounded-2xl text-left bg-secondary outline outline-2 outline-offset-2 outline-primary">{{ groupTitle }}</h3>        
        <ScrollArea>
            <div class="flex pb-4 space-x-4 w-max">
                <div v-for="gameCard in gameCards">
                    <GameCard :title="gameCard.title" :game-id="gameCard.id" />
                </div>
            </div>
        </ScrollArea>
    </div>
</template>