<script setup lang="ts">
import {z} from "zod";
import {AutoForm} from "@/components/ui/auto-form";
import {Button} from "@/components/ui/button";

const formSchema = z.object({
    username: z.string().describe("Username"),
    email: z.string().describe("Email"),
    password: z.string().describe("Password"),
})
</script>

<template>
    <div class="w-full h-[calc(100vh-8.5rem)] flex items-center justify-center">
        <div class="w-1/3 bg-secondary-color p-10 rounded-2xl">
            <h2>Sign up</h2>
            <AutoForm :schema="formSchema"
                      :field-config="{
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
        }">
                <Button class="mt-4" variant="outline" type="submit">Create your account</Button>
            </AutoForm>
        </div>
    </div>
</template>

<style scoped>
</style>