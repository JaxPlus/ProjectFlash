<script setup lang="ts">
import IconButton from "./IconButton.vue";
</script>

<template>
    <div class="menu">
        <div class="menu-content">
            <IconButton @click="$emit('closeMenu')">
                <svg xmlns="http://www.w3.org/2000/svg" width="0.75em" height="1em" viewBox="0 0 384 512"><path fill="#A4161A" d="M342.6 150.6c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0L192 210.7L86.6 105.4c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3L146.7 256L41.4 361.4c-12.5 12.5-12.5 32.8 0 45.3s32.8 12.5 45.3 0L192 301.3l105.4 105.3c12.5 12.5 32.8 12.5 45.3 0s12.5-32.8 0-45.3L237.3 256z"/></svg>
            </IconButton>
        </div>
    </div>
</template>

<style scoped>
.menu {
    position: fixed;
    width: 100vw;
    height: 100vh;
    top: 0;
    left: 0;
    display: flex;
    z-index: 10;
}

.menu-content {
    width: 20vw;
    height: 100vh;
    display: flex;
    flex-direction: column;
    background-color: var(--secondary-color);
}
</style>