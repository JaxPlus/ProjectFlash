import { createMemoryHistory, createRouter } from 'vue-router'

import MainPage from "./pages/MainPage.vue";
import ProfilePage from "@/pages/ProfilePage.vue";
import LoginPage from "@/pages/LoginPage.vue";
import RegisterPage from "@/pages/RegisterPage.vue";

const routes = [
    { path: '/', component: MainPage },
    { path: '/login', component: LoginPage },
    { path: '/register', component: RegisterPage },
    { path: '/profile', component: ProfilePage },
]

const router = createRouter({
    history: createMemoryHistory(),
    routes,
})

export default router