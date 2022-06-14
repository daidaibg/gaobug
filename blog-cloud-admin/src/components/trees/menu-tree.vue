<!--
 * @Author: daidai
 * @Date: 2021-10-13 09:10:36
 * @LastEditors: daidai
 * @LastEditTime: 2021-12-08 09:59:02
 * @FilePath: \web-pc\src\components\trees\menu-tree.vue
-->
<template>
  <yh-select-tree
    :props="props"
    :options="options"
    :value="parentId"
    @select-change="select"
    placeholder="请选择"
    ref="menus-select"
  />
</template>

<script>
import { allMenuList } from "api/modules/sys";
import { treeDataTranslate } from "@/lib/currency";
export default {
  name: "menu-tree-new",
  props: {
    parentId: {
      type: [Number, String],
      default: "",
    },
  },
  data() {
    return {
      props: {
        value: "menuId",
        label: "name",
        children: "subs",
      },
      options: [],
    };
  },
  created() {
    this.getdata();
  },
    watch:{
        parentId(newval){
            if(newval==""){
                this.clern()
            }
        }
    },
  mounted() {},
  methods: {
    select(val) {
      //   console.log(val)
      this.$emit("handleNodeClick", val);
    },
    clern(){
        this.$refs['menus-select'].clearHandle()
    },
    getdata() {
      this.loading = true;
      allMenuList().then((data) => {
        // console.log(data.data)
        this.loading = false;
        if (data.success) {
          let menus = treeDataTranslate(
            data.data,
            "menuId",
            "parentId",
            "orderNum"
          );
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
/deep/ .yh-select-tree{
    // width: 100%;
}
</style>