import router from "@/router.ts";
import {useUserStore} from "@/stores/UserStore.ts";

export const changePage = (pathStr: string) => {
    router.push({path: pathStr});
};

export async function retryAction(retryFun: () => void, additional: () => void = () => {}, apiCalls = 1) {
    const userStore = useUserStore()
    
    if ($cookies.isKey("refresh-token")) {
        const res = await userStore.refreshToken()
        // console.log(apiCalls)
        
        if (apiCalls > 0) {
            apiCalls -= 1;
            await retryAction(retryFun, additional, apiCalls)
        }
        else {
            return;
        }

        if (res) {
            apiCalls -= 1;
            await retryAction(retryFun, additional, apiCalls)
            return;
        }
    }
    else {
        additional()
    }
}