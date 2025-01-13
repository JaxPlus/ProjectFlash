<script setup lang="ts">
import IconButton from "./IconButton.vue";
import {useGameStore} from "@/stores/GameStore";
import {onMounted, ref} from "vue";
import Tag from "@/models/Tag.ts";
import TagMenuItem from "@/components/TagMenuItem.vue";

const gameStore = useGameStore();

const tagsGames = ref<Tag[]>([]);
const isLoading = ref(false);

onMounted(async () => {
  isLoading.value = true;

  tagsGames.value = await gameStore.getAllTags();

  isLoading.value = false;
})

</script>

<template>
    <div class="fixed w-full h-full z-20 flex top-0 left-0">
        <div class="w-1/4 h-full flex flex-col bg-primary">
            <IconButton class="bg-secondary" @click="$emit('closeMenu')">
                <svg class="fill-primary" xmlns="http://www.w3.org/2000/svg" width="0.75em" height="1em" viewBox="0 0 384 512"><path d="M342.6 150.6c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0L192 210.7L86.6 105.4c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3L146.7 256L41.4 361.4c-12.5 12.5-12.5 32.8 0 45.3s32.8 12.5 45.3 0L192 301.3l105.4 105.3c12.5 12.5 32.8 12.5 45.3 0s12.5-32.8 0-45.3L237.3 256z"/></svg>
            </IconButton>
            <div class="flex flex-col items-center">
                <TagMenuItem v-for="tag in tagsGames" :name="tag.tagName" :tagId="tag.id.toString()" @click="$emit('closeMenu')"/>
            </div>
        </div>
    </div>
</template>

<style scoped>
</style>