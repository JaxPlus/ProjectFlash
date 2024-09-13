<script setup lang="ts">
import HelloWorld from './components/HelloWorld.vue'
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
