<script setup lang="ts">
import GameCard from "@/components/GameCard.vue";
import {ScrollArea} from "@/components/ui/scroll-area";
import Game from "@/models/Game.ts";
import {Skeleton} from "@/components/ui/skeleton";

defineProps<{
    groupTitle: string;
    gameCards: Game[];
    isLoading?: boolean;
}>()
</script>

<template>
    <div class="flex flex-col m-8 z-5">
        <h3 class="w-[18rem] p-1 px-3 ml-2 mb-4 rounded-2xl text-left bg-secondary outline outline-2 outline-offset-2 outline-primary">
            {{ groupTitle }}</h3>
        <ScrollArea>
            <div v-if="!isLoading" class="flex pb-4 space-x-4 w-max">
                <div v-for="gameCard in gameCards">
                    <GameCard :title="gameCard.title" :game-id="gameCard.id"/>
                </div>
            </div>
            <div v-else class="flex pb-4 space-x-4 w-max">
                <div v-for="_ in 10">
                    <Skeleton
                        class="h-[10rem] min-w-[18rem] m-2 flex items-end bg-center bg-no-repeat bg-cover outline outline-2 outline-offset-2 outline-primary justify-center rounded-2xl bg-secondary z-5 cursor-pointer"
                    />
                </div>
            </div>
        </ScrollArea>
    </div>
</template>