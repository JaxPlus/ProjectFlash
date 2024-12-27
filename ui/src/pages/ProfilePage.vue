<script setup lang="ts">

import {useUserStore} from "@/stores/UserStore.ts";

const userStore = useUserStore()
userStore.getUser()

</script>

<template>
    <div class="w-full h-full grid grid-cols-2 gap-2 justify-center py-7 px-36">
<!--        <div class="bg-secondary-color min-h-[calc(100vh+4rem)] min-w-[55rem] max-w-[75rem] rounded-2xl mr-7">-->
        <div class="bg-secondary-color min-h-[calc(100vh+4rem)] min-w-[30rem] max-w-[75rem] rounded-2xl mr-7">
            <div class="w-full flex">
                <img src="../assets/2d54f86eaf5cbf4df14dee5bd62c8538.jpg" class="rounded-full h-[8rem] w-[8rem] m-7"/>
                <div class="flex justify-center items-start flex-col">
                    <h2 class="text-5xl font-bold">{{ userStore.user?.username }}</h2>
                    <span>{{ userStore.user?.email }}</span>
                </div>
            </div>
            <div>

            </div>
        </div>
        <div class="h-full">
            <div class="bg-secondary-color flex items-center min-h-28 text-start rounded-2xl">
                <router-link class="px-8" to="/settings">Settings/Dashboard</router-link>
            </div>
            <!--        <div class="bg-secondary-color min-h-[calc(100vh+4rem)] min-w-[30rem] max-w-[45rem] rounded-2xl">-->
            <div class="bg-secondary-color min-h-[calc(100vh+4rem)] rounded-2xl">
                <h3 class="text-4xl text-start pt-4 mx-4 my-4">Ostatnio grane gry</h3>
                <div class="grid grid-cols-2 justify-items-center">
                    <div v-for="n in 10" class="w-3/4 h-[8rem] bg-primary-color rounded-xl shadow-lg m-2">

                    </div>
                </div>
            </div>
        </div>
    </div>
</template>