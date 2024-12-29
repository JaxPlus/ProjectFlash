<script setup lang="ts">
import ShopCategory from "@/components/ShopCategory.vue";
import ShopItem from "@/components/ShopItem.vue";
</script>

<template>
    <div class="w-full h-full">
        <div class="w-4/5 min-h-screen rounded-3xl mx-auto my-10 bg-secondary border border-primary">
            <ShopCategory class="pt-4">Light themes</ShopCategory>
            <div class="w-full grid px-2 grid-cols-3 md:grid-cols-5">
                <ShopItem v-for="n in 7" />
            </div>
            <ShopCategory>Dark themes</ShopCategory>
            <ShopCategory>Boxes</ShopCategory>
        </div>
    </div>
</template>