<script setup lang="ts">

import {onMounted, ref} from "vue";
import axios from "axios";

const data = ref();

onMounted(async () => {
    await axios.get("http://127.0.0.1:8080/users")
        .then((req) => {
            data.value = req.data;
            console.log(req)
        })
})
</script>

<template>
    <h1>HELLOW WORLD11!!!11</h1>
    <p>
        {{ data }}
    </p>
</template>

<style scoped>
</style>
