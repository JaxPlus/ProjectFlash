import {createApp} from 'vue'
import { createPinia } from "pinia";
import './index.css'
import App from './App.vue'
import router from "./router.ts";
import VueCookies from "vue-cookies";

const pinia = createPinia()

const app = createApp(App)
    .use(router)
    .use(pinia)
    .use(VueCookies, {
        expires: "1d"
    })

app.config.globalProperties.$cookies = VueCookies.VueCookies

app.mount('#app')
    