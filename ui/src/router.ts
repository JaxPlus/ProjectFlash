import { createMemoryHistory, createRouter } from 'vue-router'

import HelloWorld from "./pages/MainPage.vue";
import RegisterPage from "./pages/RegisterPage.vue";
import ProfilePage from "@/pages/ProfilePage.vue";

const routes = [
    { path: '/', component: HelloWorld },
    { path: '/login', component: RegisterPage },
    { path: '/profile', component: ProfilePage },
]

const router = createRouter({
    history: createMemoryHistory(),
    routes,
})

export default router