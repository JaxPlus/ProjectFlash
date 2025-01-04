<script setup lang="ts">
import {computed, ref} from "vue";
import Item from "@/models/Item.ts";
import {useUserStore} from "@/stores/UserStore.ts";
import {
    Dialog,
    DialogTrigger,
    DialogContent,
    DialogHeader,
    DialogTitle,
    DialogDescription,
    DialogFooter
} from "@/components/ui/dialog";
import {Button} from "@/components/ui/button";
import axios from "axios";
import {retryAction} from "@/utility.ts";
import FlashPopover from "@/components/FlashPopover.vue";

const props = defineProps<{
    item: Item;
    class?: string;
    showDisplayName?: boolean;
}>();

const userStore = useUserStore();

// NIE TYKAĆ, nie mam zielonego pojęcia jak ale działa więc nie tykać
const isDisabled = computed(() => {
    if (userStore.user !== null) {
        return userStore.isUserLoggedIn() && !(userStore.user.money >= props.item.price) || userStore.user?.inventory.includes(props.item.id);
    }

    return true;
});
const isPopoverShown = ref(false);
const defaultTheme = ref({
    primary: "bg-[#29bcbc]",
    secondary: "bg-[#e6ebeb]",
    text: "bg-[#39393c]",
});

function buyItem() {
    axios.post("http://127.0.0.1:8080/shop", {
        itemId: props.item.id
    }, {
        headers: {
            Authorization: `Bearer ${$cookies.get("access-token")}`
        }
    }).then(() => {
        isPopoverShown.value = true;
        userStore.user?.inventory.push(props.item.id);

    }).catch(async () => {
        await retryAction(buyItem);
    });
}

/**
 * jeżeli potrzeba potestować motywy to wystarczy dodać to @click="userStore.switchTheme(props.item.name)" do <DialogTrigger>
 */
</script>

<template>
    <Dialog>
        <DialogTrigger
            class="h-[8rem] w-auto m-2 grid grid-cols-3 rounded-2xl outline-2 outline outline-primary outline-offset-2"
            :class="props.class">
            <div
                :class="`h-full w-full ${props.item.name} ${props.item.name === 'default' ? defaultTheme.primary : 'bg-primary'} rounded-l-2xl`"/>
            <div
                :class="`h-full w-full ${props.item.name} ${props.item.name === 'default' ? defaultTheme.secondary : 'bg-secondary'} flex justify-center items-center`">
                <h2 v-if="props.showDisplayName"
                    :class="`text-3xl ${props.item.type == 'default' ? 'text-[#39393c]' : 'text-text-color'}`">
                    {{ props.item.displayName }}</h2>
            </div>
            <div
                :class="`h-full w-full ${props.item.name} ${props.item.name === 'default' ? defaultTheme.text : 'bg-text-color'} rounded-r-2xl`"/>
        </DialogTrigger>

        <DialogContent>
            <DialogHeader>
                <DialogTitle>{{ props.item.displayName }}</DialogTitle>
                <DialogDescription>
                    {{ props.item.description }}
                </DialogDescription>
            </DialogHeader>

            <DialogFooter>
                <p class="self-start m-2">
                    Price: {{ props.item.price }}
                </p>
                <Button @click="buyItem()"
                        :disabled="isDisabled">
                    Buy this theme
                </Button>
            </DialogFooter>
        </DialogContent>
    </Dialog>
    <FlashPopover v-if="isPopoverShown" message="Successfully bought an item!" variant="success" :duration="6000" />
</template>