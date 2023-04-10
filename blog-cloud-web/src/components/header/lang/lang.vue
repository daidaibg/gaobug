
<script setup lang='ts'>
import { useLangStore } from '@/store'
import { LangEnum } from '@/enums'
import { ElPopover } from 'element-plus'
import { langList } from '@/i18n'

interface Props {
  langClass?: string;
}
const props = withDefaults(defineProps<Props>(), {
  langClass: "",
});

const langStore = useLangStore()
const handleZh = (key: LangEnum) => {
  langStore.changeLang(key)
}


</script>
<template>
  <el-popover :width="120" trigger="hover" >
    <template #reference>
      <div class="lang" :class="langClass">
        <span class="item-inner"><svg preserveAspectRatio="xMidYMid meet" viewBox="0 0 24 24" width="1.2em"
            height="1.2em" data-v-dd9c9540="">
            <path fill="currentColor"
              d="m18.5 10l4.4 11h-2.155l-1.201-3h-4.09l-1.199 3h-2.154L16.5 10h2zM10 2v2h6v2h-1.968a18.222 18.222 0 0 1-3.62 6.301a14.864 14.864 0 0 0 2.336 1.707l-.751 1.878A17.015 17.015 0 0 1 9 13.725a16.676 16.676 0 0 1-6.201 3.548l-.536-1.929a14.7 14.7 0 0 0 5.327-3.042A18.078 18.078 0 0 1 4.767 8h2.24A16.032 16.032 0 0 0 9 10.877a16.165 16.165 0 0 0 2.91-4.876L2 6V4h6V2h2zm7.5 10.885L16.253 16h2.492L17.5 12.885z">
            </path>
          </svg></span>
      </div>
    </template>
    <ul class="langlist">
      <li v-for="item in langList" :key="item.key" :class="{ activeclass: langStore.getLang == item.key }" @click="handleZh(item.key)">
        {{ item.label }}</li>
    </ul>
  </el-popover>
</template>


<style scoped lang='scss'>
.lang {
  font-weight: 900;
  padding: 0 12px;
  cursor: pointer;
  font-size: 18px;
  color: var(--yh-text-color-primary)
}

.langlist {
  li {
    line-height: 1.8;
    cursor: pointer;

    &.activeclass {
      color: var(--yh-brand-color);
    }
  }

  li:hover {
    color: var(--yh-brand-color);
  }
}

</style>