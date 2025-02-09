<script setup lang="ts">
import router from "@/router.ts";
import axios from "axios";
import {onMounted, ref} from "vue";
import {Skeleton} from "@/components/ui/skeleton";

const props = defineProps<{
    title: string
    gameId: number
}>()

const gameImageUrl = ref("");
const isLoading = ref(false);

function goToGame() {
    router.push({name: "GamePage", params: {gameId: props.gameId}});
}

onMounted(async () => {
    isLoading.value = true;
    gameImageUrl.value = "";

    await axios.get(`http://127.0.0.1:8080/game/thumbnail/${props.title.replace("'", "")}`
    ).then(res => {
        gameImageUrl.value = res.data;
    }).catch(() => {
        gameImageUrl.value = "";
    });

    isLoading.value = false;
})

</script>

<template>
    <div
        v-if="!isLoading"
        :style="`background-image: url('${gameImageUrl}')`"
         class="h-[10rem] min-w-[18rem] m-2 flex items-end bg-center bg-no-repeat bg-cover outline outline-2 outline-offset-2 outline-primary justify-center rounded-2xl bg-secondary z-5 cursor-pointer"
         @click="goToGame()">
        <p class="p-2 w-full rounded-b-2xl opacity-0 transition ease-in-out bg-gradient-to-t from-primary/70">{{
                title
            }}</p>
    </div>
    <Skeleton
        v-else
        class="h-[10rem] min-w-[18rem] m-2 flex items-end bg-center bg-no-repeat bg-cover outline outline-2 outline-offset-2 outline-primary justify-center rounded-2xl bg-secondary z-5 cursor-pointer"
    />
</template>

<style scoped>
div:hover > p {
    opacity: 1;
}
</style>