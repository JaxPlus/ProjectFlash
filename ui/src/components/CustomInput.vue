<script setup lang="ts">

type htmlInputTypes = "text" | "password" | "button" | "checkbox" | "color" | "date" | "number" | "datetime-local" | "email" | "file" | "month" | "radio" | "range" | "reset" | "search" | "submit" | "tel" | "url" | "week"

const props = defineProps<{
    type: htmlInputTypes
}>()
</script>

<template>
    <input :type="props.type" />
</template>

<style scoped>
    input {
        
    }
</style>