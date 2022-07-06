<template>
    <template v-for="menu in menus">
        <template v-if="menu.children && menu.children.length > 0">
            <el-sub-menu :index="menu.path" :key="menu.path">
                <template #title>

                    <i :class="menu.icon" class="icon" v-if="menu.icon != '' && menu.icon"></i>
                    <span>{{ menu.title }}</span>
                </template>
                <menu-tree :menus="menu.children"></menu-tree>
            </el-sub-menu>
        </template>
        <template v-else>
            <el-menu-item :index="menu.path" :key="menu.path" @click="clickMenu(menu)">
                <i :class="menu.icon" class="icon" v-if="menu.icon != '' && menu.icon"></i>
                <span>{{ menu.title }}</span>
            </el-menu-item>
        </template>
    </template>
</template>
<script setup lang="ts">
import type { viewMenu } from '@/store/interface'
import { useRouter } from "vue-router"
import { ElSubMenu } from "element-plus"
import { useMenuNavStore } from "@/store"
const menuNavStore = useMenuNavStore()
const router = useRouter()
interface Props {
    menus: viewMenu[]
}
const props = withDefaults(defineProps<Props>(), {
    menus: () => [],
})

const clickMenu = (menu: viewMenu) => {

    router.push(menu.path)

}
</script>

<style scoped lang="scss">
$bg-liner-hover: linear-gradient(to right, var(--yh-brand-color-3), var(--yh-brand-color-1), var(--yh-bg-color-container));
//渐变
$bg-liner: linear-gradient(to right, var(--yh-brand-color-2), var(--yh-brand-color-1), var(--yh-bg-color-container));

.el-sub-menu .icon,
.el-menu-item .icon {
    vertical-align: middle;
    margin-right: 5px;
    width: 24px;
    text-align: center;
    font-size: 18px;
}

.is-active>.icon {
    color: var(--yh-brand-color);
}


:deep(.el-sub-menu__title) {
    border-radius: 8px;
    &:hover {
        background: $bg-liner-hover;

    }
}

.el-menu {

    .el-menu-item,
    .el-sub-menu__title {
        border-radius: 8px;

        &:hover {
            background: $bg-liner-hover;
        }
    }

}

.el-menu-item.is-active {
    position: relative;
    // background-color: var(--yh-brand-color);
    background: $bg-liner;

    >span {
        color: var(--yh-brand-color);
        // color: var(--yh-text-color-anti);
    }

    &::before {
        content: " ";
        position: absolute;
        width: 4px;
        height: 80%;
        left: 1px;
        top: 10%;
        border-radius: 2px;
        background-color: var(--yh-brand-color);
    }

}

.el-sub-menu.is-active {
    .el-sub-menu__title {

        i,
        span {
            color: var(--yh-brand-color-hover);

        }
    }

    :deep(.el-sub-menu__title) .el-icon svg {
        color: var(--yh-brand-color-hover);
    }
}
</style>
