import { createApp } from 'vue'
import { createPinia } from "pinia";
import './index.css'
import App from './App.vue'
import router from "./router.ts";
import VueCookies from "vue-cookies";

const pinia = createPinia()

createApp(App)
    .use(router)
    .use(pinia)
    .use(VueCookies, {
        expires: "1d"
    })
    .mount('#app')