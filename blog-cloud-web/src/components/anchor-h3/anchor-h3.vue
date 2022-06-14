<script setup lang='ts'>
import { PropType } from "vue";
import { useI18n } from 'vue-i18n'

const props = defineProps({
    id: {
        type: String as PropType<string>,
        default: () => ''
    }
})
const { t } = useI18n()
const handleI18n = (name: string, prefix?: string) => {
    let str = t(name)
    if (prefix) {
        str = prefix + t(name)
    }
    str = str.replace(/\s/g, "-")
    return str
}
</script>
<template>
    <h3 :id="handleI18n(id)"> <a :href="handleI18n(id, '#')">#</a> <slot>{{ $t(id) }}</slot> </h3>
</template>
<style scoped lang='scss'>
</style>