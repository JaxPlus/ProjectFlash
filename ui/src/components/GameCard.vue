﻿<script setup lang="ts">

const props = defineProps<{
    title: string
}>()

</script>

<template>
    <div class="h-[10rem] min-w-[18rem] mx-2 flex items-end justify-center rounded-2xl bg-secondary-color z-5">
        <p class="p-2 w-full rounded-b-2xl opacity-0 transition ease-in-out bg-gradient-to-t from-black">{{ title }}</p>
    </div>
</template>

<style scoped>
div:hover > p {
    opacity: 1;
}
</style>