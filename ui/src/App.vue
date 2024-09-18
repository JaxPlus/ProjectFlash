<script setup lang="ts">
import Navbar from "./components/Navbar.vue";
</script>

<template>
  <Navbar />
  <nav>
    <RouterLink to="/">Go to Home</RouterLink>
  </nav>
  <main>
    <RouterView />
  </main>
</template>

<style scoped>
</style>
