<script setup lang="ts">

import router from "@/router.ts";

const props = defineProps<{
    name: string,
    tagId: string
}>()

function goToTagPage() {
  console.log(props.tagId);
  router.push({name: "TagPage", params: {tagId: props.tagId, tagName: props.name}});
}

</script>

<template>
    <h3 class="w-3/4 p-1 px-3 my-3 rounded-2xl text-left bg-secondary text-lg outline outline-2 outline-offset-2 outline-secondary/60 cursor-pointer" @click="goToTagPage()">{{ name }}</h3>
</template>