import { createMemoryHistory, createRouter } from 'vue-router'

import HelloWorld from "./pages/MainPage.vue";
import ProfilePage from "@/pages/ProfilePage.vue";
import LoginPage from "@/pages/LoginPage.vue";
import RegisterPage from "@/pages/RegisterPage.vue";
import GamePage from "@/pages/GamePage.vue";

const routes = [
    { path: '/', component: HelloWorld },
    { path: '/login', component: LoginPage },
    { path: '/register', component: RegisterPage },
    { path: '/profile', component: ProfilePage },
    { path: '/game', component: GamePage },
]

const router = createRouter({
    history: createMemoryHistory(),
    routes,
})

export default router