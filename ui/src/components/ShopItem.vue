<script setup lang="ts">
import {onMounted, ref} from "vue";
import Item from "@/models/Item.ts";
import {useUserStore} from "@/stores/UserStore.ts";

const props = defineProps<{
    item: Item
}>()

const userStore = useUserStore()
const defaultTheme = ref(null)

onMounted(() => {
    if (props.item.name === "default") {
        defaultTheme.value = {
            primary: "bg-[#29bcbc]",
            secondary: "bg-[#e6ebeb]",
            text: "bg-[#39393c]",
        }
    }  
})
/**
 * @todo NIE ZAPOMNIĆ ZMIENIĆ TEGO @CLIK BO TO TAK DZIAŁAĆ NIE MA
 */
</script>

<template>
    <div @click="userStore.switchTheme(props.item.name)" class="h-[8rem] w-auto m-2 grid grid-cols-3 rounded-2xl outline-2 outline outline-primary outline-offset-2">
        <div :class="`h-full w-full ${props.item.name} ${defaultTheme ? defaultTheme.primary : 'bg-primary'} rounded-l-2xl`" />
        <div :class="`h-full w-full ${props.item.name} ${defaultTheme ? defaultTheme.secondary : 'bg-secondary'}`"/>
        <div :class="`h-full w-full ${props.item.name} ${defaultTheme ? defaultTheme.text : 'bg-text-color'} rounded-r-2xl`" />
    </div>
</template>