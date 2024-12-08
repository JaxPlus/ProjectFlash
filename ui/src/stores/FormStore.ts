import {defineStore} from "pinia";
import {computed, ref} from "vue";
import axios from "axios";
import {Config} from "@/components/ui/auto-form";
import {z} from "zod";
import {changePage} from "@/utility.ts";

type FormType = "signIn" | "signUp"

export const useFormStore = defineStore('formStore', () => {
    const formType = ref<FormType>()
    const response = ref({
        error: "",
        response: ""
    })
    const formSchema = ref()
    const formConfig: Config<any> = ref()
    const url = computed(() => {
        return "http://127.0.0.1:8080".concat(formType.value === "signIn" ? "/login" : "/users")
    })

    async function userSignAction(values: z.infer<typeof props.formSchema>) {
        axios.post(url.value, values).then((res) => {
            response.value.response = res.data
            console.log(res)
            changePage('/login')
            response.value.error = ""
        }).catch((err) => {
            response.value.error = err.response.data
            console.log(err);
        })
    }
    
    function clearForm() {
        response.value.error = "";
        response.value.response = "";
    }
    
    return {
        formType,
        response,
        formSchema,
        formConfig,
        url,
        userSignAction,
        clearForm,
    }
})