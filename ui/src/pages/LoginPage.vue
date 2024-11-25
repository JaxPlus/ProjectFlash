<script setup lang="ts">

import {AutoForm} from "@/components/ui/auto-form";
import {Button} from "@/components/ui/button";
import {z} from "zod";
import axios from "axios";

const formSchema = z.object({
  email: z.string().describe("Email"),
  password: z.string().describe("Password"),
})

const onSubmit = async (values: z.infer<typeof formSchema>) => {
  console.log(values.email, values.password);

  axios.post("http://127.0.0.1:8080/login", values).then((res) => {
    console.log(res);
  }).catch((err) => {
    console.log(err);
  })
}

</script>

<template>
  <div class="w-full h-[calc(100vh-8.5rem)] flex items-center justify-center">
    <div class="w-1/3 bg-secondary-color p-10 rounded-2xl">
      <h2>Sign up</h2>
      <AutoForm :schema="formSchema"
                :field-config="{
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
        }"
                @submit="onSubmit"
      >
        <Button class="mt-4" variant="outline" type="submit">Log In</Button>
      </AutoForm>
    </div>
  </div>
</template>

<style scoped>

</style>