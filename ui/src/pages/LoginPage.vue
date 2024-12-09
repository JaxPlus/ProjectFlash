<script setup lang="ts">
import {z} from "zod";
import SignInUpForm from "@/components/SignInUpForm.vue";
import {changePage} from "@/utility.ts";
import {useFormStore} from "@/stores/FormStore.ts";

const formStore = useFormStore()

formStore.formType = "signIn"
formStore.formSchema = z.object({
    email: z.string().describe("Email"),
    password: z.string().describe("Password"),
})
formStore.formConfig = {
    email: {
        inputProps: {
            placeholder: 'Enter your email',
            type: 'email',
        }
    },
    password: {
        inputProps: {
            placeholder: 'Enter your password',
            type: 'password',
        }
    },
}

function goToRegister() {
    formStore.clearForm()
    changePage('/register')
}

</script>

<template>
    <div class="w-full h-[calc(100vh-8.5rem)] flex items-center justify-center">
        <div class="w-1/3 bg-secondary-color p-10 rounded-2xl">
            <h2 class="text-2xl mb-5 font-bold">Sign In</h2>
            <SignInUpForm />
            <p class="mt-5">Don't have an account? <a class="text-blue-600 cursor-pointer" @click="goToRegister()">Sign up here</a>!</p>
        </div>
    </div>
</template>

<style scoped>
</style>