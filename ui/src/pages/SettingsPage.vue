<script setup lang="ts">
import {ref} from "vue";
import {useUserStore} from "@/stores/UserStore.ts";
import {AutoForm, Config} from "@/components/ui/auto-form";
import {z} from "zod";
import {Button} from "@/components/ui/button";
import Divider from "@/components/Divider.vue";

const userStore = useUserStore()
userStore.getUser()

const isAccountShown = ref(true)
const isStatisticsShown = ref(false)

const schema = z.object({
    username: z.string().describe("Here you can change your username!").default(userStore.user.username),
})
const config: Config<any> = {
    username: {
        inputProps: {
            placeholder: 'Enter your new username!'
        }
    }
}

function showContent(type: "account" | "statistics") {
    switch (type) {
        case "account":
            isAccountShown.value = true
            isStatisticsShown.value = false
        break;
        case "statistics":
            isAccountShown.value = false
            isStatisticsShown.value = true
        break;
    }
}

function onSubmit(values: z.infer<typeof schema>) {
    if (values.username === userStore.user.username) {
        return;
    }
    
    userStore.editUsername(values.username);
}

</script>

<template>
    <div class="w-full h-full flex justify-center py-7 px-36">
        <div class="bg-secondary-color w-[30rem] rounded-2xl mr-7">
            <h3 class="text-3xl p-6">Settings</h3>
            <p class="p-2 cursor-pointer"><router-link to="/profile">Go back to profile</router-link></p>
            <p class="p-2 cursor-pointer" @click="showContent('account')">Account</p>
            <p class="p-2 cursor-pointer" @click="showContent('statistics')">Statistics</p>
        </div>
        <div class="bg-secondary-color min-h-[calc(100vh+4rem)] w-full rounded-2xl mr-7">
            <div class="flex flex-col" v-if="isAccountShown">
                <h3 class="text-3xl p-6 self-start">User settings</h3>
                <div class="m-6">
                    <AutoForm
                        class="flex justify-between"
                        :schema="schema"
                        :field-config="config"
                        @submit="onSubmit">
                        <Button class="mt-4 self-end" variant="outline" type="submit">Save</Button>
                    </AutoForm>
                </div>
                <Divider class="self-center" />
            </div>
            <div v-if="isStatisticsShown">
                
            </div>
        </div>
    </div>
</template>