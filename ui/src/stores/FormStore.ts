import {defineStore} from "pinia";
import {computed, ref} from "vue";
import axios from "axios";
import {Config} from "@/components/ui/auto-form";
import {z} from "zod";
import {changePage} from "@/utility.ts";
import Token from "@/models/Token.ts";

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
        return "http://127.0.0.1:8080".concat(formType.value === "signIn" ? "/api/auth" : "/users")
    })

    async function userSignAction(values: z.infer<typeof props.formSchema>) {
        axios.post(url.value, values).then((res) => {
            response.value.error = ""
            // console.log(res)
            
            switch (url.value) {
                case "http://127.0.0.1:8080/users":
                    response.value.response = res.data
                    changePage('/login');
                    break;
                case "http://127.0.0.1:8080/api/auth":
                    const temp = res.data as Token
                    $cookies.set("access-token", temp.accessToken, import.meta.env.VITE_JWT_ACCESS_TOKEN_EXP, null, null, true)
                    $cookies.set("refresh-token", temp.refreshToken, import.meta.env.VITE_JWT_REFRESH_TOKEN_EXP, null, null, true)
                    changePage('/profile');
                    break;
            }            
        }).catch((err) => {
            response.value.error = err.response.data
            // console.log(err);
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