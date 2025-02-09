<script setup lang="ts">
import {useUserStore} from "@/stores/UserStore.ts";
import {onMounted, ref} from "vue";
import Item from "@/models/Item.ts";
import InventoryItem from "@/components/InventoryItem.vue";
import {Skeleton} from "@/components/ui/skeleton";
import {Button} from "@/components/ui/button";

const userStore = useUserStore()

const items = ref<Item[]>([]);
const isLoading = ref(false);

onMounted(async () => {
    isLoading.value = true;
    await userStore.getUser();
    items.value = await userStore.getInventoryItems();
    await userStore.getUserProfile();

    isLoading.value = false;
})
</script>

<template>
    <div class="w-full h-full grid grid-cols-2 gap-2 justify-center py-7 px-36">
        <div class="min-h-[calc(100vh+4rem)] min-w-[30rem] max-w-[75rem] mr-7">
            <div class="w-full border border-primary rounded-2xl bg-secondary">
                <div class="flex" v-if="isLoading">
                    <Skeleton
                        class="rounded-full outline-offset-4 outline outline-2 outline-primary h-[8rem] w-[8rem] m-7"/>
                    <div class="flex justify-center items-start flex-col">
                        <Skeleton class="w-52 h-8 mb-2"/>
                        <Skeleton class="w-36 h-6"/>
                    </div>
                </div>
                <div class="flex" v-else>
                    <img
                        v-if="userStore.userProfile != ''"
                        :src="userStore.userProfile"
                        class="rounded-full outline-offset-4 outline outline-2 outline-primary h-[8rem] w-[8rem] m-7"/>
                    <svg v-else class="rounded-full outline-offset-4 outline outline-2 outline-primary fill-primary stroke-secondary/50 h-[8rem] w-[8rem] p-2 m-7" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 22 22">
                        <path
                            d="M9 3h4v1h1v1h1v4h-1v1h-1v1H9v-1H8V9H7V5h1V4h1zm1 5v1h2V8h1V6h-1V5h-2v1H9v2zm-3 4h8v1h2v1h1v1h1v4H3v-4h1v-1h1v-1h2zm-1 4H5v1h12v-1h-1v-1h-2v-1H8v1H6z"/>
                    </svg>
                    <div class="flex justify-center items-start flex-col">
                        <h2 class="text-5xl font-bold">{{ userStore.user?.username }}</h2>
                        <span>{{ userStore.user?.email }}</span>
                    </div>
                </div>
            </div>
            <div class="w-full flex flex-col items-start mt-4 border border-primary rounded-2xl bg-secondary p-4">
                <h3 class="text-lg">Money: {{ userStore.user?.money }}</h3>
                <h4 class="text-lg">Inventory:</h4>
                <div class="grid justify-items-center grid-cols-2 lg:grid-cols-3 w-full">
                    <InventoryItem v-for="item in items" :item="item"/>
                </div>
            </div>
        </div>
        <div class="h-full">
            <div
                class="bg-secondary border border-primary flex items-center justify-between min-h-28 text-start rounded-2xl">
                <Button variant="link">
                    <router-link class="px-8 text-base" to="/settings">Settings/Dashboard</router-link>
                </Button>
            </div>
            <div class="bg-secondary border border-primary min-h-[calc(100vh+4rem)] mt-4 rounded-2xl">
                <h3 class="text-4xl text-start pt-4 mx-4 mb-4">Recently played games</h3>
                <div class="grid grid-cols-2 justify-items-center">
                    <div v-for="() in 10" class="w-3/4 h-[8rem] bg-primary rounded-xl shadow-lg m-2">
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>