<!--
 * @Author: daidai
 * @Date: 2022-01-14 16:42:21
 * @LastEditors: daidai
 * @LastEditTime: 2022-01-17 11:20:54
 * @FilePath: \web-pc\src\components\trees\company-tree.vue
-->
<template>
  <yh-select-tree
    :props="props"
    :options="options"
    v-model="id"
    @select-change="select"
    :defaultExpandedKeys="[1]"
    placeholder="请选择"
    ref="menus-select"
  />
</template>

<script>
import { currentSelectList } from "api/modules";
import { treeDataTranslate } from "@/lib/currency";
export default {
  name: "company-tree-new",
  props: {
    parentId: {
      type: [Number, String,Boolean],
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
      id:''
    };
  },
  model:{
    prop: "parentId",
    event: "change",
  },
  created() {
    this.getdata();
  },
  watch: {
    parentId(newval) {
      this.id = newval;
      if (newval == "") {
        this.clern();
      }
    },
  },
  mounted() {},
  methods: {
    select(val) {
      //   console.log(val)
      this.$emit("change", val.id);
      this.$emit("handleNodeClick", val);
    },
    clern() {
      this.$refs["menus-select"].clearHandle();
    },
    getdata() {
      this.loading = true;
      currentSelectList(10).then((data) => {
        // console.log(data.data)
        this.loading = false;
        if (data.success) {
          let menus = treeDataTranslate(data.data, "id", "parentid");
          this.options = menus;
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
  width: 100%;
}
</style>