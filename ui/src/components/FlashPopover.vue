<script setup lang="ts">

import {onMounted, ref} from "vue";

const props = defineProps<{
    message: string,
    variant: "info" | "success" | "warning",
    duration?: number,
}>()

const isShown = ref(false);
const popoverStyle = ref("");

onMounted(() => {
    isShown.value = true;

    switch (props.variant) {
        case "info":
            popoverStyle.value = "bg-blue-500/85 outline-blue-600/95";
            break;
        case "success":
            popoverStyle.value = "bg-emerald-500/85 outline-emerald-800/95";
            break;
        case "warning":
            popoverStyle.value = "bg-red-600/85 outline-red-800/95";
            break;
    }
});

setTimeout(() => {
    isShown.value = false;
}, props.duration ? props.duration : 4000);

</script>

<template>
    <Transition name="slide-fade">
        <div @click="isShown = false" v-if="isShown"
             class="h-12 min-w-60 fixed bottom-0 right-0 flex items-center m-6 p-3 rounded-2xl outline outline-2 outline-offset-2 cursor-pointer"
             :class="popoverStyle">
            <svg v-if="variant === 'info'" class="fill-blue-900 stroke-blue-900"
                 xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
                <path
                    d="M12 17q.425 0 .713-.288T13 16v-4q0-.425-.288-.712T12 11t-.712.288T11 12v4q0 .425.288.713T12 17m0-8q.425 0 .713-.288T13 8t-.288-.712T12 7t-.712.288T11 8t.288.713T12 9m0 13q-2.075 0-3.9-.788t-3.175-2.137T2.788 15.9T2 12t.788-3.9t2.137-3.175T8.1 2.788T12 2t3.9.788t3.175 2.137T21.213 8.1T22 12t-.788 3.9t-2.137 3.175t-3.175 2.138T12 22m0-2q3.35 0 5.675-2.325T20 12t-2.325-5.675T12 4T6.325 6.325T4 12t2.325 5.675T12 20m0-8"/>
            </svg>
            <svg v-if="variant === 'success'" class="fill-emerald-300 stroke-emerald-900"
                 xmlns="http://www.w3.org/2000/svg" viewBox="0 0 48 48">
                <g stroke-linecap="round" stroke-linejoin="round" stroke-width="4">
                    <path
                        d="m24 4l5.253 3.832l6.503-.012l1.997 6.188l5.268 3.812L41 24l2.021 6.18l-5.268 3.812l-1.997 6.188l-6.503-.012L24 44l-5.253-3.832l-6.503.012l-1.997-6.188l-5.268-3.812L7 24l-2.021-6.18l5.268-3.812l1.997-6.188l6.503.012z"/>
                    <path d="m17 24l5 5l10-10"/>
                </g>
            </svg>
            <svg v-if="variant === 'warning'" class="fill-red-800 stroke-red-900" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
                <g>
                    <path
                          d="M12 2c5.523 0 10 4.477 10 10s-4.477 10-10 10S2 17.523 2 12S6.477 2 12 2m0 2a8 8 0 1 0 0 16a8 8 0 0 0 0-16m0 11a1 1 0 1 1 0 2a1 1 0 0 1 0-2m0-9a1 1 0 0 1 1 1v6a1 1 0 1 1-2 0V7a1 1 0 0 1 1-1"/>
                </g>
            </svg>
            <p class="text-lg text-text-color mx-3">{{ message }}</p>
        </div>
    </Transition>
</template>

<style scoped>
svg {
    width: 1.7rem;
    height: 1.7rem;
}

.slide-fade-enter-active {
    transition: all 0.5s ease-out;
}

.slide-fade-leave-active {
    transition: all 0.8s cubic-bezier(1, 0.5, 0.8, 1);
}

.slide-fade-enter-from,
.slide-fade-leave-to {
    transform: translateX(300px);
    opacity: 0;
}
</style>