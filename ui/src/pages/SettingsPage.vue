<script setup lang="ts">
import {ref} from "vue";
import {useUserStore} from "@/stores/UserStore.ts";
import {AutoForm, Config} from "@/components/ui/auto-form";
import {z} from "zod";
import {Button} from "@/components/ui/button";
import Divider from "@/components/Divider.vue";

const userStore = useUserStore();
userStore.getUser();

const isAccountShown = ref(true);
const isStatisticsShown = ref(false);
const img = ref("")
const ACCEPTED_IMAGE_TYPES = ["image/jpeg", "image/jpg", "image/png"];

const usernameSchema = z.object({
    username: z.string().describe("Here you can change your username!").default(userStore.user?.username),
});
const usernameConfig: Config<any> = {
    username: {
        inputProps: {
            placeholder: 'Enter your new username!'
        }
    }
};

const profileSchema = z.object({
    profile: z
        .any()
        .refine((files) => files?.length > 0, "Image is required.")
        .refine(
            (files: string) => {
                if (files) {
                    const type = files.matchAll(/image\/(?<=\/)(.*?)(?=;)/gm).next().value?.at(0) || "";
                    return ACCEPTED_IMAGE_TYPES.includes(type)
                }

                return false;
            },
            ".jpg, .jpeg, .png and .webp files are accepted."
        ),
});
const profileConfig: Config<any> = {
    profile: {
        inputProps: {
            type: "file"
        },
        label: "Here you can change your profile!",
        component: "file"
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

function onUsernameSubmit(values: z.infer<typeof usernameSchema>) {
    if (values.username === userStore.user?.username) {
        return;
    }

    userStore.editUsername(values.username);
}

function onProfileSubmit(values: z.infer<typeof profileSchema>) {
    img.value = values.profile;
    userStore.editProfile(img.value);
    userStore.userProfile = img.value;
}

</script>

<template>
    <div class="w-full h-full flex justify-center py-7 px-36">
        <div class="flex flex-col bg-secondary border border-primary w-[30rem] rounded-2xl mr-7">
            <h3 class="text-3xl p-6">Settings</h3>
            <p class="p-2 cursor-pointer">
                <router-link class="flex items-center" to="/profile">
                    <svg xmlns="http://www.w3.org/2000/svg"
                         viewBox="0 0 24 24">
                        <path
                            stroke-width="0.3"
                            d="M5.85 17.1q1.275-.975 2.85-1.537T12 15t3.3.563t2.85 1.537q.875-1.025 1.363-2.325T20 12q0-3.325-2.337-5.663T12 4T6.337 6.338T4 12q0 1.475.488 2.775T5.85 17.1M12 13q-1.475 0-2.488-1.012T8.5 9.5t1.013-2.488T12 6t2.488 1.013T15.5 9.5t-1.012 2.488T12 13m0 9q-2.075 0-3.9-.788t-3.175-2.137T2.788 15.9T2 12t.788-3.9t2.137-3.175T8.1 2.788T12 2t3.9.788t3.175 2.137T21.213 8.1T22 12t-.788 3.9t-2.137 3.175t-3.175 2.138T12 22"/>
                    </svg>
                    <span class="mr-auto">Go back to profile</span>
                </router-link>
            </p>
            <Divider class="self-center"/>
            <p class="p-2 cursor-pointer flex items-center" @click="showContent('account')">
                <svg xmlns="http://www.w3.org/2000/svg"
                     viewBox="0 0 24 24">
                    <g fill-rule="evenodd" clip-rule="evenodd">
                        <path
                            d="M12 8.25a3.75 3.75 0 1 0 0 7.5a3.75 3.75 0 0 0 0-7.5M9.75 12a2.25 2.25 0 1 1 4.5 0a2.25 2.25 0 0 1-4.5 0"/>
                        <path
                            d="M11.975 1.25c-.445 0-.816 0-1.12.02a2.8 2.8 0 0 0-.907.19a2.75 2.75 0 0 0-1.489 1.488c-.145.35-.184.72-.2 1.122a.87.87 0 0 1-.415.731a.87.87 0 0 1-.841-.005c-.356-.188-.696-.339-1.072-.389a2.75 2.75 0 0 0-2.033.545a2.8 2.8 0 0 0-.617.691c-.17.254-.356.575-.578.96l-.025.044c-.223.385-.408.706-.542.98c-.14.286-.25.568-.29.88a2.75 2.75 0 0 0 .544 2.033c.231.301.532.52.872.734a.87.87 0 0 1 .426.726a.87.87 0 0 1-.426.726c-.34.214-.64.433-.872.734a2.75 2.75 0 0 0-.545 2.033c.041.312.15.594.29.88c.135.274.32.595.543.98l.025.044c.222.385.408.706.578.96c.177.263.367.5.617.69a2.75 2.75 0 0 0 2.033.546c.376-.05.716-.2 1.072-.389a.87.87 0 0 1 .84-.005a.86.86 0 0 1 .417.731c.015.402.054.772.2 1.122a2.75 2.75 0 0 0 1.488 1.489c.29.12.59.167.907.188c.304.021.675.021 1.12.021h.05c.445 0 .816 0 1.12-.02c.318-.022.617-.069.907-.19a2.75 2.75 0 0 0 1.489-1.488c.145-.35.184-.72.2-1.122a.87.87 0 0 1 .415-.732a.87.87 0 0 1 .841.006c.356.188.696.339 1.072.388a2.75 2.75 0 0 0 2.033-.544c.25-.192.44-.428.617-.691c.17-.254.356-.575.578-.96l.025-.044c.223-.385.408-.706.542-.98c.14-.286.25-.569.29-.88a2.75 2.75 0 0 0-.544-2.033c-.231-.301-.532-.52-.872-.734a.87.87 0 0 1-.426-.726c0-.278.152-.554.426-.726c.34-.214.64-.433.872-.734a2.75 2.75 0 0 0 .545-2.033a2.8 2.8 0 0 0-.29-.88a18 18 0 0 0-.543-.98l-.025-.044a18 18 0 0 0-.578-.96a2.8 2.8 0 0 0-.617-.69a2.75 2.75 0 0 0-2.033-.546c-.376.05-.716.2-1.072.389a.87.87 0 0 1-.84.005a.87.87 0 0 1-.417-.731c-.015-.402-.054-.772-.2-1.122a2.75 2.75 0 0 0-1.488-1.489c-.29-.12-.59-.167-.907-.188c-.304-.021-.675-.021-1.12-.021zm-1.453 1.595c.077-.032.194-.061.435-.078c.247-.017.567-.017 1.043-.017s.796 0 1.043.017c.241.017.358.046.435.078c.307.127.55.37.677.677c.04.096.073.247.086.604c.03.792.439 1.555 1.165 1.974s1.591.392 2.292.022c.316-.167.463-.214.567-.227a1.25 1.25 0 0 1 .924.247c.066.051.15.138.285.338c.139.206.299.483.537.895s.397.69.506.912c.107.217.14.333.15.416a1.25 1.25 0 0 1-.247.924c-.064.083-.178.187-.48.377c-.672.422-1.128 1.158-1.128 1.996s.456 1.574 1.128 1.996c.302.19.416.294.48.377c.202.263.29.595.247.924c-.01.083-.044.2-.15.416c-.109.223-.268.5-.506.912s-.399.689-.537.895c-.135.2-.219.287-.285.338a1.25 1.25 0 0 1-.924.247c-.104-.013-.25-.06-.567-.227c-.7-.37-1.566-.398-2.292.021s-1.135 1.183-1.165 1.975c-.013.357-.046.508-.086.604a1.25 1.25 0 0 1-.677.677c-.077.032-.194.061-.435.078c-.247.017-.567.017-1.043.017s-.796 0-1.043-.017c-.241-.017-.358-.046-.435-.078a1.25 1.25 0 0 1-.677-.677c-.04-.096-.073-.247-.086-.604c-.03-.792-.439-1.555-1.165-1.974s-1.591-.392-2.292-.022c-.316.167-.463.214-.567.227a1.25 1.25 0 0 1-.924-.247c-.066-.051-.15-.138-.285-.338a17 17 0 0 1-.537-.895c-.238-.412-.397-.69-.506-.912c-.107-.217-.14-.333-.15-.416a1.25 1.25 0 0 1 .247-.924c.064-.083.178-.187.48-.377c.672-.422 1.128-1.158 1.128-1.996s-.456-1.574-1.128-1.996c-.302-.19-.416-.294-.48-.377a1.25 1.25 0 0 1-.247-.924c.01-.083.044-.2.15-.416c.109-.223.268-.5.506-.912s.399-.689.537-.895c.135-.2.219-.287.285-.338a1.25 1.25 0 0 1 .924-.247c.104.013.25.06.567.227c.7.37 1.566.398 2.292-.022c.726-.419 1.135-1.182 1.165-1.974c.013-.357.046-.508.086-.604c.127-.307.37-.55.677-.677"/>
                    </g>
                </svg>
                <span class="mr-auto">Account</span>
            </p>
            <Divider class="self-center"/>
            <p class="p-2 cursor-pointer flex items-center" @click="showContent('statistics')">
                <svg xmlns="http://www.w3.org/2000/svg"
                     viewBox="0 0 24 24">
                    <path
                        stroke-width="0.3"
                        d="M19 3H5a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2V5a2 2 0 0 0-2-2m0 16H5V5h14zM9 17H7v-5h2zm4 0h-2V7h2zm4 0h-2v-7h2z"/>
                </svg>
                <span class="mr-auto">Statistics</span>
            </p>
            <Divider class="self-center"/>
            <p class="p-2 cursor-pointer flex items-center" @click="userStore.logOut()">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
                    <g fill="none" stroke-linecap="round" stroke-linejoin="round"
                       stroke-width="2">
                        <path d="M14 8V6a2 2 0 0 0-2-2H5a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h7a2 2 0 0 0 2-2v-2"/>
                        <path d="M9 12h12l-3-3m0 6l3-3"/>
                    </g>
                </svg>
                <span class="mr-auto">Log Out</span>
            </p>
        </div>
        <div class="bg-secondary border border-primary min-h-[calc(100vh+4rem)] w-full rounded-2xl mr-7">
            <div class="flex flex-col" v-if="isAccountShown">
                <h3 class="text-3xl p-6 self-start">User settings</h3>
                <div class="m-6">
                    <AutoForm
                        class="flex justify-between"
                        :schema="usernameSchema"
                        :field-config="usernameConfig"
                        @submit="onUsernameSubmit">
                        <Button class="mt-4 self-end" variant="outline" type="submit">Save</Button>
                    </AutoForm>
                </div>
                <Divider class="self-center"/>
                <div class="m-6">
                    <AutoForm :schema="profileSchema" :field-config="profileConfig"
                              @submit="onProfileSubmit" class="flex justify-between">
                        <Button class="mt-4 self-end" variant="outline" type="submit">Save</Button>
                    </AutoForm>
                </div>
                <Divider class="self-center"/>
            </div>
            <div v-if="isStatisticsShown">

            </div>
        </div>
    </div>
</template>

<style scoped>
svg {
    width: 2rem;
    height: 2rem;
    margin-right: auto;
    fill: hsl(var(--primary));
    stroke: hsl(var(--primary));
}
</style>