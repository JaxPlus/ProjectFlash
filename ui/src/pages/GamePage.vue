<script setup lang="ts">
import {useRuffleStore} from "@/stores/RuffleStore.ts";
import {useGameStore} from "@/stores/GameStore.ts";
import {onMounted, ref, watch} from "vue";
import {Skeleton} from "@/components/ui/skeleton";
import GameCard from "@/components/GameCard.vue";
import { useRoute } from 'vue-router'
import Comment from "@/components/Comment.vue";
import axios from "axios";

const ruffleStore = useRuffleStore();
const player = ruffleStore.getRufflePlayer();

const gameStore = useGameStore();
const isLoading = ref(true);

// temp tablice dopóki baza danych jest przenoszona
const comments = ref([
    {
        text: "Best site to play my childhood games!",
        likes: 10000,
        dislikes: 0,
        postedBy: {
            username: "Username",
            profile: ""
        }
    },
    {
        text: "Papryczek",
        likes: 10000,
        dislikes: 0,
        postedBy: {
            username: "Papryczek",
            profile: ""
        }
    },
    {
        text: "Yes",
        likes: 10000,
        dislikes: 1,
        postedBy: {
            username: "Username",
            profile: ""
        }
    },
    {
        text: "Tyszko to dobra osoba",
        likes: 1,
        dislikes: 12,
        postedBy: {
            username: "Username",
            profile: ""
        },
    }
]);
const profilePic = ref<any[]>([]);

const route = useRoute();
const id = route.params.gameId;

gameStore.getGameById(id.toString());

watch(
    () => route.params.gameId,
    (newId) => {
        gameStore.getGameById(newId.toString());

        setTimeout(() => {
          player.load("../src/assets/games/" + gameStore?.game?.gamePath);
        }, 1000)

        window.scrollTo(0,0);
    }
)

// temp
onMounted(async () => {
    const res = await axios.get("http://localhost:8080/api/random-profile");
    profilePic.value = res.data;
})

setTimeout(() => {
    player.style.height = "100%";
    player.style.width = "100%";
    document.getElementById("container")?.appendChild(player)

    let gamePath = "../src/assets/games/" + gameStore?.game?.gamePath.substring(0, gameStore?.game?.gamePath.length - 4) + "/" + gameStore?.game?.gamePath

    player.load(gamePath);

    // temp
    for (let i = 0; i < comments.value.length; i++) {
        comments.value[i].postedBy.profile = profilePic.value[i].url;
    }
    
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

            <div class="w-full grid grid-cols-2 justify-items-center justify-evenly px-36 pb-24">

                <div
                    class="flex flex-col h-fit min-w-96 w-full">
                    <div class="bg-secondary border border-primary text-start rounded-2xl">
                        <p class="font-medium text-4xl px-12 pt-12">{{ gameStore?.game?.title }}</p>
                        <p class="font-medium text-base px-12 pt-3">Added by: Admin</p>
                        <p class=" text-lg px-12 py-8">{{ gameStore?.game?.description }}</p>
                    </div>
                    <div class="bg-secondary border border-primary flex flex-col h-fit rounded-2xl mt-4" v-for="comment in comments">
                        <Comment :game-id="parseInt(route.params.gameId[0])" :comment="comment" />
                    </div>
                </div>
                <div
                    class="bg-secondary border border-primary flex flex-col items-center p-5 min-w-min w-3/5 min-h-64 rounded-2xl gap-6">
                    <GameCard :game-path="gameStore?.game?.gamePath" title="Strike Force Heroes 2" :game-id="1"/>
                    <GameCard :game-path="gameStore?.game?.gamePath" title="Papa's Pizzeria" :game-id="2"/>
                    <GameCard :game-path="gameStore?.game?.gamePath" title="Mighty Knight" :game-id="3"/>
                    <GameCard :game-path="gameStore?.game?.gamePath" title="Kingdom Rush" :game-id="4"/>
                    <GameCard :game-path="gameStore?.game?.gamePath" title="War Of Ages" :game-id="5"/>
                </div>
            </div>
        </div>
    </div>
</template>