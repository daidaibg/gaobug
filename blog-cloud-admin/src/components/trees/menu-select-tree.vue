<!--
 * @Author: daidai
 * @Date: 2021-08-17 16:09:24
 * @LastEditTime: 2021-12-14 17:31:53
 * @FilePath: \web-pc\src\components\trees\menu-select-tree.vue
-->
<template>
    <el-tree :data="data" :props="defaultProps" 
        v-loading="loading"
         element-loading-spinner="el-icon-loading"
        show-checkbox
        node-key="menuId"
        :default-expanded-keys="defaultCheckedKeys"
        style="max-height:300px;overflow-y: auto;"
        @check-change="handleCheckChange"
        ref="menuSelectTree"
        element-loading-text="加载中..."></el-tree>
</template>

<script>
import { menuListUser } from 'api/modules/sys';
import {treeDataTranslate} from '@/lib/currency'
export default {
    data() {
        return {
            defaultProps:{
                children: 'subs',
                label: 'name'
            },
            data:[],
            loading:false,
            CheckedKeys:[],
            defaultExpandedKeys:[],
            defaultCheckedKeys:[]
        }
    },
    props:{
    },
    mounted() {
        this.getdata()
    },
    computed:{
       
    },
    methods: {
        init(data){
            // console.log(data,this.loading);
            if(!data){
                return
            }
            this.CheckedKeys = data;
            if(!this.loading){
                this.$nextTick(()=>{
                     this.$refs.menuSelectTree.setCheckedKeys([]);
                    //闭合所有
                    for (let i = 0; i < this.data.length; i++) {
                        this.$refs.menuSelectTree.store.nodesMap[this.data[i].menuId].expanded = false;
                    }
                    //选中
                    data.forEach((i,n) => {
                        var node = this.$refs.menuSelectTree.getNode(i);
                        // console.log(node)
                        if(node&&node.isLeaf){
                            this.$refs.menuSelectTree.setChecked(node, true);
                        }
                    });
                })
            }
           
        },
        getdata(){
            this.loading=true;
             menuListUser().then(data=>{
                // console.log(data.data)
                this.loading = false;
                if(data.success){
                    let menus =  treeDataTranslate(data.data,"menuId","parentId","orderNum")
                    // console.log(menus)
                   this.data =menus;
                    this.$nextTick(()=>{
                        this.init(this.CheckedKeys)
                    })
                }else{
                    this.$message.error(data.msg)
                }
            })
        },
        handleCheckChange(val){
            // console.log(val)
            let data = this.$refs.menuSelectTree.getCheckedKeys().concat(this.$refs.menuSelectTree.getHalfCheckedKeys())
            // console.log(data)
            this.$emit("handleNodeClick",data);
          
        }
    },

}
</script>

<style lang="scss">

</style>