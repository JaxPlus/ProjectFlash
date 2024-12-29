<script setup lang="ts">
</script>

<template>
    <div class="m-2 p-2 grid items-center whitespace-nowrap md:grid-cols-shop-category">
        <slot />
        <div class="w-full h-[0.20rem] mt-2 md:mt-0 bg-primary opacity-30 rounded-3xl" />
    </div>
</template>