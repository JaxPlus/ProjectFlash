<script setup lang="ts">
import {computed, onMounted, ref} from "vue";
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

const props = defineProps<{
    item: Item
}>()

const userStore = useUserStore()

// NIE TYKAĆ, nie mam zielonego pojęcia jak ale działa więc nie tykać
const isDisabled = computed(() => userStore.isUserLoggedIn() && !(userStore.user?.money >= props.item.price) || userStore.user?.inventory.includes(props.item.id))
const defaultTheme = ref(null)

onMounted(() => {
    if (props.item.name === "default") {
        defaultTheme.value = {
            primary: "bg-[#29bcbc]",
            secondary: "bg-[#e6ebeb]",
            text: "bg-[#39393c]",
        }
    }
})

function buyItem() {
    axios.post("http://127.0.0.1:8080/shop", {
        itemId: props.item.id
    }, {
        headers: {
            Authorization: `Bearer ${$cookies.get("access-token")}`
        }
    }).then(res => {
        userStore.user.inventory.push(props.item.id)
        
        console.log(res)
    }).catch(async (err) => {
        await retryAction(buyItem)
        
        // if ($cookies.isKey("refresh-token")) {
        //     await userStore.refreshToken()
        //     await buyItem()
        // } else {
        //     return;
        // }
    })
}

/**
 * @todo NIE ZAPOMNIĆ ZMIENIĆ TEGO @CLIK BO TO TAK DZIAŁAĆ NIE MA
 * @todo Trzeba zrobić popovera jakiegoś czy coś żeby użytkownik wiedział że coś kupił
 */
</script>

<template>
    <Dialog>
        <DialogTrigger @click="userStore.switchTheme(props.item.name)"
                       class="h-[8rem] w-auto m-2 grid grid-cols-3 rounded-2xl outline-2 outline outline-primary outline-offset-2">
            <div
                :class="`h-full w-full ${props.item.name} ${defaultTheme ? defaultTheme.primary : 'bg-primary'} rounded-l-2xl`"/>
            <div :class="`h-full w-full ${props.item.name} ${defaultTheme ? defaultTheme.secondary : 'bg-secondary'}`"/>
            <div
                :class="`h-full w-full ${props.item.name} ${defaultTheme ? defaultTheme.text : 'bg-text-color'} rounded-r-2xl`"/>
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
</template>

<style scoped>
svg {
    fill: hsl(var(--primary)) !important;
    stroke: hsl(var(--primary)) !important;
}
</style>