import { ref, computed, reactive } from 'vue'
import { defineStore } from 'pinia'
// import { storeToRefs } from 'pinia';
export const useHeaderStore = defineStore('header', () => {
    const headerSearch = reactive<{
        keywords:string,
        num:number
    }>({
        keywords:"",
        num:0
    })

    const setKeywords=(val:string)=>{
        headerSearch.keywords=val
    }
    return {headerSearch,setKeywords}
})
