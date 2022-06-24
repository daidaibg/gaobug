<!--
 * @Author: daidai
 * @Date: 2021-10-13 09:10:36
 * @LastEditors: daidai
 * @LastEditTime: 2021-12-09 10:58:32
 * @FilePath: \web-pc\src\components\trees\region-tree.vue
-->
<template>
  <yh-select-tree
    :props="props"
    :options="options"
    :value="parentId"
    @select-change="select"
    placeholder="请选择"
    ref="menus-select"
    :clearable="false"
    :default-expanded-keys="[-1]"
  />
</template>

<script>
import { regionListTree } from "api/modules/sys";
export default {
  name: "region-tree-new",
  props: {
    parentId: {
      type: [Number, String],
      default: "",
    },
  },
  data() {
    return {
      props: {
        value: "id",
        label: "name",
        children: "subs",
      },
      options: [],
    };
  },
  created() {
    this.getdata();
  },
  watch: {
    parentId(newval) {
      if (newval == "") {
        this.clern();
      }
    },
  },
  mounted() {},
  methods: {
    select(val) {
      //   console.log(val)
      this.$emit("handleNodeClick", val);
    },
    clern() {
      this.$refs["menus-select"].clearHandle();
    },
    getdata() {
      this.loading = true;
      regionListTree().then((data) => {
        this.loading = false;
        if (data.success) {
          let menus = data.data;
          this.options = menus;
        //   this.$nextTick(() => {
        //     this.$refs["menus-select"].initHandle();
        //   });
        } else {
          this.$message.error(data.msg);
        }
      });
    },
  },
};
</script>
<style lang='scss' scoped>
/deep/ .yh-select-tree {
  // width: 100%;
}
</style>