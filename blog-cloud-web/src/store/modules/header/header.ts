import { ref, computed, reactive } from 'vue'
import { defineStore } from 'pinia'
// import { storeToRefs } from 'pinia';
export const useHeaderStore = defineStore('header', () => {
    const headerSearch = reactive<{
        keywords:string|number,
        num:number
    }>({
        keywords:"",
        num:0
    })

    const setKeywords=(val:string|number)=>{
        headerSearch.keywords=val
        headerSearch.num++

    }
    return {headerSearch,setKeywords}
})
