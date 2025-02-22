﻿import router from "@/router.ts";
import {useUserStore} from "@/stores/UserStore.ts";

export const changePage = (pathStr: string) => {
    router.push({path: pathStr});
};

export async function retryAction(retryFun: Function, additional: () => void = () => {}, apiCalls = 1) {
    const userStore = useUserStore()

    // @ts-ignore
    if ($cookies.isKey("refresh-token")) {
        const res = await userStore.refreshToken()
        // console.log(apiCalls)

        if (res) {
            apiCalls -= 1;
            // await retryAction(retryFun, additional, apiCalls)

            try {
                retryFun();
            }
            catch (e) {
                console.log(e);
                return;
            }
        }
        
        if (apiCalls >= 0) {
            apiCalls -= 1;
            await retryAction(retryFun, additional, apiCalls)
        }
        else {
            return;
        }
    }
    else {
        additional()
    }
}