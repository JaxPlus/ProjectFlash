<script setup lang="ts">

import {useUserStore} from "@/stores/UserStore.ts";

const userStore = useUserStore()
userStore.getUser()

</script>

<template>
    <div class="w-full h-full grid grid-cols-2 gap-2 justify-center py-7 px-36">
        <div class="min-h-[calc(100vh+4rem)] min-w-[30rem] max-w-[75rem] mr-7">
            <div class="w-full flex border border-primary rounded-2xl bg-secondary">
                <img src="../assets/2d54f86eaf5cbf4df14dee5bd62c8538.jpg" class="rounded-full outline-offset-4 outline outline-2 outline-primary h-[8rem] w-[8rem] m-7"/>
                <div class="flex justify-center items-start flex-col">
                    <h2 class="text-5xl font-bold">{{ userStore.user?.username }}</h2>
                    <span>{{ userStore.user?.email }}</span>
                </div>
            </div>
            <div class="w-full flex flex-col items-start mt-4 border border-primary rounded-2xl bg-secondary p-4">
                <h3 class="text-lg">Money: {{ userStore.user?.money }}</h3>
                <h4 class="text-lg">Inventory:</h4>
                <div>
                    <p v-for="item in userStore.user?.inventory">{{ item }}</p>
                </div>
            </div>
        </div>
        <div class="h-full">
            <div class="bg-secondary border border-primary flex items-center min-h-28 text-start rounded-2xl">
                <router-link class="px-8" to="/settings">Settings/Dashboard</router-link>
            </div>
            <div class="bg-secondary border border-primary min-h-[calc(100vh+4rem)] mt-4 rounded-2xl">
                <h3 class="text-4xl text-start pt-4 mx-4 mb-4">Ostatnio grane gry</h3>
                <div class="grid grid-cols-2 justify-items-center">
                    <div v-for="n in 10" class="w-3/4 h-[8rem] bg-primary rounded-xl shadow-lg m-2">

                    </div>
                </div>
            </div>
        </div>
    </div>
</template>