import { createMemoryHistory, createRouter } from 'vue-router'

import HelloWorld from "./pages/MainPage.vue";
import RegisterPage from "./pages/RegisterPage.vue";

const routes = [
    { path: '/', component: HelloWorld },
    { path: '/login', component: RegisterPage },
]

const router = createRouter({
    history: createMemoryHistory(),
    routes,
})

export default router