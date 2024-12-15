import router from "@/router.ts";

export const changePage = (pathStr: string) => {
    router.push({path: pathStr});
};