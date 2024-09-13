<script setup lang="ts">
</script>

<template>
  <div>
    <button>
        <slot />
    </button>
  </div>
</template>

<style scoped>
    div {
        background-color: var(--secondary-color);
        width: 4rem;
        height: 4rem;
        border-radius: 1rem;
        margin: 2.3rem;
    }
    
    button {
        width: 100%;
        height: 100%;
        background-color: transparent;
        border: none;
        cursor: pointer;
        border-radius: 1rem;
        display: flex;
        justify-content: center;
        align-items: center;
    }
</style>