<script setup lang="ts">
import {onMounted, ref} from "vue";
import Item from "@/models/Item.ts";

const props = defineProps<{
    item: Item,
}>()

const defaultTheme = ref(null)

onMounted(() => {
    if (props.item.type == "default") {
        defaultTheme.value = {
            primary: "bg-[#29bcbc]",
            secondary: "bg-[#e6ebeb]",
            text: "bg-[#39393c]",
        }
    }
})

</script>

<template>
    <div class="h-72 rounded-3xl">
        <div class="w-full h-full scale-95 rounded-3xl outline-primary outline outline-2 outline-offset-2 select-none grid grid-cols-3">
            <div :class="`${props.item.name} ${defaultTheme ? defaultTheme.value.primary : 'bg-primary'} rounded-l-3xl`" />
            <div :class="`${props.item.name} ${defaultTheme ? defaultTheme.value.secondary : 'bg-secondary'} flex justify-center items-center`">
                <h2 :class="`text-3xl ${defaultTheme ? 'text-[#39393c]' : 'text-text-color'}`">{{ props.item.displayName }}</h2>
            </div>
            <div :class="`${props.item.name} ${defaultTheme ? defaultTheme.value.text : 'bg-text-color'} rounded-r-3xl`" />
        </div>
    </div>
</template>

<style scoped>
</style>