<script setup lang="ts">
import ShopCategory from "@/components/ShopCategory.vue";
import ShopItem from "@/components/ShopItem.vue";
import {onMounted, ref} from "vue";
import Item from "@/models/Item.ts";
import axios from "axios";
import {Carousel, CarouselContent, CarouselItem} from "@/components/ui/carousel";
import Autoplay from "embla-carousel-autoplay";
import {useUserStore} from "@/stores/UserStore.ts";

const userStore = useUserStore();
userStore.getUser();

const lightThemes = ref<Item[]>([]);
const darkThemes = ref<Item[]>([]);
const boxes = ref<Item[]>([]);
const featured = ref<Item[]>([]);

const plugin = Autoplay({
    delay: 10000,
    stopOnMouseEnter: true,
    stopOnInteraction: false,
});

onMounted(async () => {
    const response = await axios.get("http://127.0.0.1:8080/shop");

    for (let item of response.data) {
        switch (item.type) {
            case "dark":
                darkThemes.value.push(item);
                break;
            case "light":
                lightThemes.value.push(item);
                break;
            case "box":
                boxes.value.push(item);
                break;
        }
    }

    featured.value.push(response.data[1]);
    featured.value.push(response.data[3]);
    featured.value.push(response.data[2]);
    
    // boxes.value.push({
    //     id: 100,
    //     name: "s_flash_box",
    //     displayName: "Super Flash Box",
    //     description: "Flash Box",
    //     price: 5000,
    //     type: "box",
    // });
})
</script>

<template>
    <div class="w-full h-full">
        <div class="w-4/5 min-h-screen rounded-3xl mx-auto my-10 bg-secondary border border-primary">
            <div class="h-72 m-4">
                <Carousel
                    :plugins="[plugin]"
                    @mouseenter="plugin.stop"
                    @mouseleave="[plugin.reset(), plugin.play()];"
                >
                    <CarouselContent>
                        <CarouselItem v-for="item in featured">
                            <div class="h-72 rounded-2xl">
                                <ShopItem :show-display-name="true"
                                          class="w-full h-full scale-95 rounded-2xl outline-primary outline outline-2 outline-offset-2 select-none grid grid-cols-3"
                                          :item="item"/>
                            </div>
                        </CarouselItem>
                    </CarouselContent>
                </Carousel>
            </div>

            <ShopCategory class="pt-4">Light themes</ShopCategory>
            <div class="w-full grid px-4 grid-cols-3 md:grid-cols-5">
                <ShopItem v-for="item in lightThemes" :item="item"/>
            </div>
            <ShopCategory>Dark themes</ShopCategory>
            <div class="w-full grid px-4 grid-cols-3 md:grid-cols-5">
                <ShopItem v-for="item in darkThemes" :item="item"/>
            </div>
            <ShopCategory>Boxes</ShopCategory>
            <div class="w-full grid px-4 mb-4 grid-cols-3 md:grid-cols-5">
                <ShopItem v-for="item in boxes" :item="item"/>
            </div>
        </div>
    </div>
</template>