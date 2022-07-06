<template>
    <div class="layout-logo">
        <template v-if="!menuNavStore.isCollapse">
            <img class="logo-img" src="@/assets/img/logo/logon-dark.png" alt="logo"
                v-if="themeStore.getTheme == ThemeEnum.DARK" />
            <img class="logo-img" src="@/assets/img/logo/logo.png" alt="logo" v-else />
            <div class="website-name">
                GaoBugAdmin
            </div>
        </template>
        <el-icon class="fold cursor-pointer" size="24px" @click="setisCollapse">
            <Fold v-if="!menuNavStore.isCollapse" />
            <Expand v-else="menuNavStore" />
        </el-icon>
    </div>
</template>

<script setup lang="ts">
import { ThemeEnum } from '@/enums'

import { ElIcon } from "element-plus"
import { Expand, Fold } from '@element-plus/icons-vue'
import { useMenuNavStore } from "@/store"
import { userThemeStore } from '@/store'

const themeStore = userThemeStore()
const menuNavStore = useMenuNavStore()
const setisCollapse = () => {
    menuNavStore.setIsCollapse(!menuNavStore.isCollapse)
}
</script>

<style scoped lang="scss">
.layout-logo {
    width: 100%;
    height: 50px;
    display: flex;
    align-items: center;
    justify-content: center;
    box-sizing: border-box;
    padding: 10px;
}

.logo-img {
    height: 24px;
}

.website-name {
    padding-left: 4px;
    font-size: 16px;
    font-weight: 600;
    color: var(--yh-brand-color);
}

.fold {
    margin-left: auto;
    color: var(--yh-brand-color);

}
</style>
