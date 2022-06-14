<!--
 * @Author: daidai
 * @Date: 2021-12-14 11:08:58
 * @LastEditors: daidai
 * @LastEditTime: 2022-01-05 17:48:54
 * @FilePath: \web-pc\src\components\trees\region-cascader.vue
-->
<template>
  <el-cascader
    ref="region_cascader"
    :options="options"
    :props="props"
    clearable
    v-model="value"
    @change="change"
    :size="size"
  ></el-cascader>
</template>

<script>
import { regionListTree } from "api/modules/sys";

export default {
  data() {
    return {
      options: [],
      value: [],
      list: [],
      props: {
        value: "id",
        label: "name",
        children: "subs",
        checkStrictly: true,
        disabled: "disabled",
        expandTrigger: "click",
      },
      loading: true,
    };
  },
  props: {
    isDelOne: {
      type: Boolean,
      default: () => true,
    },
    disabled: {
      type: Boolean,
      default: () => false,
    },
    size: {
      type: String,
      default: () => "small",
    },
  },
  created() {
    this.getdata();
  },

  mounted() {
    //    let node = this.$refs.region_cascader.getCheckedNodes()
  },
  methods: {
    init(id) {
      //   console.log(id);
      if (id == "" || !id) {
        this.id = undefined;
        this.value = [];
        return;
      }
      // console.log(this.value)
      if (!this.loading) {
        this.id = undefined;
        this.getNode(id, this.list);
      } else {
        this.id = id;
      }
    },
    initChekeds(ids) {
      this.value = ids;
    },
    clearn(){
      this.value = []
    },
    getNode(id, list = [], returnList = []) {
      // console.log(this.list)
      // console.log(node)
      let flag = false;
      for (let index = 0; index < list.length; index++) {
        const element = list[index];
        if (element.id == id) {
          //   console.log(element);
          flag = true;
          returnList.unshift(element.id);

          if (element.id <= 0) {
            // console.log(returnList);
            if (this.isDelOne) {
              returnList.splice(0, 1);
            }
            this.value = returnList;
            this.$nextTick(()=>{
              this.change( this.value)
            })
            
            return returnList;
          } else {
            this.getNode(element.parentid, list, returnList);
          }
          break;
        }
      }
      if (!flag) {
        this.value = returnList;
        return returnList;
      }
    },
    change(val) {
      let node = this.$refs.region_cascader.getCheckedNodes();
      if (node.length > 0) {
        node = node[0];
      } else {
        node = null;
      }
      //   console.log(val);
      this.$emit("handleNodeClick", val, node);
    },
    getdata() {
      this.loading = true;
      regionListTree(this.disabled).then((data) => {
        this.loading = false;
        if (data.success) {
          // console.log(data)
          let menus = data.data;
          //   console.log(menus)
          this.list = data.list;
          if (this.isDelOne && menus.length > 0) {
            this.options = menus[0].subs;
          } else {
            this.options = menus;
          }
          if (this.id) {
            this.getNode(this.id, this.list);
          }
        } else {
          this.$message.error(data.msg);
        }
      });
    },
  },
};
</script>
<style lang='scss' scoped>
</style>