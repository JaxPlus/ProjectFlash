<script setup lang="ts">
import Item from "@/models/Item.ts";
import {
    Dialog, DialogClose,
    DialogContent,
    DialogDescription,
    DialogFooter,
    DialogHeader,
    DialogTitle, DialogTrigger
} from "@/components/ui/dialog";
import {Button} from "@/components/ui/button";
import {useUserStore} from "@/stores/UserStore.ts";
import {ref} from "vue";

const props = defineProps<{
    item: Item,
}>()

const userStore = useUserStore()

const isThemeNameShown = ref(false)
const defaultTheme = ref({
    primary: "bg-[#29bcbc]",
    secondary: "bg-[#e6ebeb]",
    text: "bg-[#39393c]",
});

</script>

<template>
    <div class="h-32 w-40 p-2">
        <Dialog>
            <DialogTrigger
                @mouseenter="isThemeNameShown = true"
                @mouseleave="isThemeNameShown = false"
                class="w-full h-full scale-95 grid grid-cols-3 rounded-2xl outline-2 outline outline-primary outline-offset-2">
                <div
                    :class="`h-full w-full ${props.item.name} ${props.item.name === 'default' ? defaultTheme.primary : 'bg-primary'} rounded-l-2xl`"/>
                <div
                    :class="`h-full w-full ${props.item.name} ${props.item.name === 'default' ? defaultTheme.secondary : 'bg-secondary'} flex justify-center items-center`" />
                <div
                    :class="`h-full w-full ${props.item.name} ${props.item.name === 'default' ? defaultTheme.text : 'bg-text-color'} rounded-r-2xl`"/>
                <Transition>
                    <div v-if="isThemeNameShown" :class="`h-full w-full absolute flex justify-center items-center ${props.item.name} ${props.item.name === 'default' ? 'bg-[#29bcbc]/75' : 'bg-primary/75'} rounded-2xl`">
                        <h2
                            :class="`text-xl w-full ${props.item.name} ${props.item.name == 'default' ? 'text-[#39393c]' : 'text-text-color'}`">
                            {{ props.item.displayName }}</h2>
                    </div>
                </Transition>
            </DialogTrigger>

            <DialogContent>
                <DialogHeader>
                    <DialogTitle>{{ props.item.displayName }}</DialogTitle>
                    <DialogDescription>
                        {{ props.item.description }}
                    </DialogDescription>
                </DialogHeader>

                <DialogFooter>
                    <DialogClose>
                        <Button @click="userStore.switchTheme(props.item.name)">
                            Use this theme
                        </Button>
                    </DialogClose>
                </DialogFooter>
            </DialogContent>
        </Dialog>
    </div>
</template>

<style scoped>
.v-enter-active,
.v-leave-active {
    transition: opacity 0.2s ease;
}

.v-enter-from,
.v-leave-to {
    opacity: 0;
}
</style>