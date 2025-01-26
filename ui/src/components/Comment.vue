<script setup lang="ts">
const props = defineProps<{
    gameId: number;
    comment: {
        text: string;
        likes: number;
        dislikes: number;
        postedBy: {
            username: string;
            profile: string;
        }
    }
}>();

</script>

<template>
    <div class="">
        <div class="flex items-center">
            <img class="rounded-full h-14 w-14 m-4" :src="props.comment.postedBy.profile" alt="Profile pic"/>
            <h2 class="font-bold text-lg">{{ props.comment.postedBy.username }}</h2>
        </div>
        <p class="text-start m-4">{{ props.comment.text }}</p>
        <div class="flex justify-end content-baseline flex-nowrap mx-2 mb-2">
            <span class="text-end py-2">{{ props.comment.likes }}</span>
            <svg class="fill-primary stroke-primary/70 h-8 w-8" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"><g><path d="M17.36 20H8V10c.625 0 1.208-.312 1.555-.832l2.554-3.832A3 3 0 0 1 14.606 4h.213a1 1 0 0 1 .987 1.164L15 10h3.56a2 2 0 0 1 1.962 2.392l-1.2 6A2 2 0 0 1 17.36 20" opacity="0.16"/><path d="m15 10l-.986-.164A1 1 0 0 0 15 11zM4 10V9a1 1 0 0 0-1 1zm16.522 2.392l.98.196zM6 21h11.36v-2H6zM18.56 9H15v2h3.56zm-2.574 1.164l.806-4.835L14.82 5l-.805 4.836zM14.82 3h-.213v2h.213zm-3.542 1.781L8.762 8.555l1.664 1.11L12.94 5.89zM7.93 9H4v2h3.93zM3 10v8h2v-8zm17.302 8.588l1.2-6l-1.961-.392l-1.2 6zM8.762 8.555A1 1 0 0 1 7.93 9v2a3 3 0 0 0 2.496-1.336zm8.03-3.226A2 2 0 0 0 14.82 3v2zM18.56 11a1 1 0 0 1 .981 1.196l1.961.392A3 3 0 0 0 18.56 9zm-1.2 10a3 3 0 0 0 2.942-2.412l-1.961-.392a1 1 0 0 1-.98.804zM14.606 3a4 4 0 0 0-3.329 1.781l1.665 1.11A2 2 0 0 1 14.606 5zM6 19a1 1 0 0 1-1-1H3a3 3 0 0 0 3 3z"/><path stroke-width="1.2" d="M8 10v10"/></g></svg>
            <span class="text-end py-2 pl-2">{{ props.comment.dislikes }}</span>
            <svg class="fill-primary stroke-primary/70 h-8 w-8" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"><g><path d="M17.36 4H8v10c.625 0 1.208.312 1.555.832l2.554 3.832A3 3 0 0 0 14.606 20h.213a1 1 0 0 0 .987-1.164L15 14h3.56a2 2 0 0 0 1.962-2.392l-1.2-6A2 2 0 0 0 17.36 4" opacity="0.16"/><path d="m15 14l-.986.164A1 1 0 0 1 15 13zM4 14v1a1 1 0 0 1-1-1zm16.522-2.392l.98-.196zM6 3h11.36v2H6zm12.56 12H15v-2h3.56zm-2.574-1.164l.806 4.835L14.82 19l-.805-4.836zM14.82 21h-.213v-2h.213zm-3.542-1.781l-2.515-3.774l1.664-1.11l2.515 3.774zM7.93 15H4v-2h3.93zM3 14V6h2v8zm17.302-8.588l1.2 6l-1.961.392l-1.2-6zM8.762 15.445A1 1 0 0 0 7.93 15v-2a3 3 0 0 1 2.496 1.336zm8.03 3.226A2 2 0 0 1 14.82 21v-2zM18.56 13a1 1 0 0 0 .981-1.196l1.961-.392A3 3 0 0 1 18.56 15zm-1.2-10a3 3 0 0 1 2.942 2.412l-1.961.392a1 1 0 0 0-.98-.804zm-2.754 18a4 4 0 0 1-3.329-1.781l1.665-1.11a2 2 0 0 0 1.664.891zM6 5a1 1 0 0 0-1 1H3a3 3 0 0 1 3-3z"/><path stroke-width="1.2" d="M8 14V4"/></g></svg>
        </div>
    </div>
</template>