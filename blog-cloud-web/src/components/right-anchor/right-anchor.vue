<script setup lang='ts'>
import { PropType, ref, onMounted, shallowRef } from "vue";
import { useI18n } from 'vue-i18n'
import { useRoute } from "vue-router";
const currentActive = ref('')
const route = useRoute()
if (route.hash && route.hash !== '') {
    currentActive.value = route.hash
}
interface anchorListProps {
    href: any;
    title: any;
}
const props = defineProps({
    list: {
        type: Array as PropType<anchorListProps[]>,
        default: () => []
    },
    //是否进行翻译
    isNoTranslate: {
        type: Boolean,
        default: false
    }
})
const { t } = useI18n()
const handleI18n = (name: string, prefix?: string) => {
    if (props.isNoTranslate) {
        return name
    }
    let str = t(name)
    if (prefix) {
        str = prefix + t(name)
    }
    str = str.replace(/\s/g, "-")
    return str
}
</script>

<template>
    <div class='code-anchor'>
        <yh-anchor container="body" :bounds="50" :targetOffset="0" ref="anchor" :currentActive="currentActive">
            <template v-for="(item, i) in list" :key="i">
                <yh-anchor-item :href="handleI18n(item.href, '#')" :title="isNoTranslate?item.title: t(item.title)" />
            </template>
        </yh-anchor>
    </div>
</template>

<style scoped lang='scss'>
.code-anchor {
    position: fixed;
    width: 240px;
    padding-right: 10px;
    max-height: 480px;
    max-height: min(calc(100vh - 550px), 480px);
    overflow: hidden auto;
    position: absolute;
    right: 24px;
    top: 152px;
    z-index: 100;
    box-sizing: border-box;
}
</style>

