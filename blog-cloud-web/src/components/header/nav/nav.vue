<script setup lang='ts'>
import Props from "./props"
defineProps(Props)
</script>

<template>
    <ul class="header_list " >
        <li v-for="(item, i) in HeaderList" :key="i" :class="{ active: active == item.path }">
            <a :href="item.path" @click.prevent="jump(item.path)" >{{
                    item.translation ? $t(item.name) : item.name
            }}</a>
        </li>
        <slot></slot>
    </ul>
</template>

<style scoped lang='scss'>
  .header_list {
    height: 100%;
    display: flex;
    align-items: center;
    margin-left: 8px;

    li {
      text-align: center;
      flex-shrink: 0;
      display: flex;
      justify-content: center;
      height: 100%;
      font-size: 14px;
      padding: 0 16px;

      a {
        height: 100%;
        display: flex;
        align-items: center;
        color: var(--yh-text-color-secondary);
        position: relative;

        &::before {
          width: 100%;
          content: "";
          position: absolute;
          top: auto;
          right: 0;
          bottom: 0;
          left: 0;
          height: 2px;
          background-color: var(--yh-brand-color);
          display: none;
        }

        &:hover {
          color: var(--yh-text-color-primary);

          &::before {
            display: inline;
          }
        }
      }

      &.active {
        a {
          color: var(--yh-brand-color);
        }
      }
    }

    // .activeItem {
    //   border-color: var(--yh-brand-color);

    //   a {
    //     color: var(--yh-brand-color);
    //   }
    // }
  }
</style>