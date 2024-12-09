<script setup lang="ts">

import {useUserStore} from "@/stores/UserStore.ts";

const userStore = useUserStore()
userStore.getUser()

</script>

<template>
    <div class="w-full h-full flex justify-center items-center p-7">
        <div class="bg-secondary-color min-h-[calc(100vh+4rem)] min-w-[55rem] max-w-[75rem] rounded-2xl mr-7">
            <div class="w-full flex">
                <img src="../assets/2d54f86eaf5cbf4df14dee5bd62c8538.jpg" class="rounded-full h-[12rem] w-[12rem] m-7"/>
                <div class="flex justify-center items-start flex-col">
                    <h2 class="text-5xl font-bold">{{ userStore.user?.username }}</h2>
                    <span>{{ userStore.user?.email }}</span>
                </div>
            </div>
            <div>

            </div>
        </div>
        <div class="bg-secondary-color min-h-[calc(100vh+4rem)] min-w-[30rem] max-w-[45rem] rounded-2xl">
            <h3 class="text-4xl text-start m-7">Ostatnio grane gry</h3>
            <div class="grid grid-cols-2 justify-items-center">
                <div v-for="n in 10" class="w-4/5 h-[8rem] bg-primary-color rounded-xl shadow-lg m-5">
                    
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>

</style>