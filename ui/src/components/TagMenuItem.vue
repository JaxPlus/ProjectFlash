<script setup lang="ts">

const props = defineProps<{
    name: string
}>()

</script>

<template>
    <h3 class="w-3/4 p-1 px-3 my-3 rounded-2xl text-left bg-secondary text-lg outline outline-2 outline-offset-2 outline-secondary/60">{{ name }}</h3>
</template>