<script setup lang="ts">
import {Button} from "@/components/ui/button";
import {AutoForm} from "@/components/ui/auto-form";
import {z} from "zod";
import {useFormStore} from "@/stores/FormStore.ts";

const formStore = useFormStore()

const onSubmit = async (values: z.infer<typeof formStore.formSchema>) => {
    await formStore.userSignAction(values);
}

</script>

<template>
    <p v-if="formStore.response.response != ''" class="text-blue-200 p-3">You can now log to your new account!</p>
    <AutoForm
        class="space-y-5"
        :schema="formStore.formSchema"
        :field-config="formStore.formConfig"
        @submit="onSubmit"
    >
        <p v-if="formStore.response.error != ''" class="text-red-400 p-3">{{ formStore.response.error }}</p>
        <Button class="mt-4" variant="outline" type="submit">{{ formStore.formType === "signIn" ? "Sign in" : "Create account" }}</Button>
    </AutoForm>
</template>

<style scoped>

</style>