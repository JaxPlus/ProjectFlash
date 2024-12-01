<script setup lang="ts">
import {z} from "zod";
import SignInUpForm from "@/components/SignInUpForm.vue";
import {changePage} from "@/utility.ts";
import {useFormStore} from "@/stores/FormStore.ts";

const formStore = useFormStore()

formStore.formType = "signUp"
formStore.formSchema = z.object({
    username: z.string().describe("Username"),
    email: z.string().describe("Email"),
    password: z.string().describe("Password"),
})
formStore.formConfig = {
    username: {
        inputProps: {
            placeholder: 'Enter your username',
        }
    },
    email: {
        inputProps: {
            placeholder: 'Enter your email',
        }
    },
    password: {
        inputProps: {
            placeholder: 'Enter your password',
            type: 'password',
        }
    },
}

// axios.get("http://localhost:8080/users").then((res) => {
//   console.log(res.data)
//
//   if (res.data.length > 0) {
//     if (res.data[0].username === "asdas") {
//       console.log("jej")
//     }
//   }
//
// })

// const prop = defineProps({
//   name: String,
//   email: String,
//   password: String,
// });

function goToLogin() {
    formStore.clearForm()
    changePage('/login')
}

</script>

<template>
    <div class="w-full h-[calc(100vh-8.5rem)] flex items-center justify-center">
        <div class="w-1/3 bg-secondary-color p-10 rounded-2xl">
            <h2 class="text-2xl mb-5 font-bold">Sign up</h2>
            <SignInUpForm />
            <p class="mt-5">Already have an account? <a class="text-blue-600 cursor-pointer" @click="goToLogin()">Sign in here</a>!</p>
        </div>
    </div>
</template>

<style scoped>
</style>