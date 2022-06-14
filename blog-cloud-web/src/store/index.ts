import { createStore } from 'vuex'
import langStore from './modules/lang-store'
import themeStore from './modules/theme/theme-store'
import userStore from "./modules/user/user-store"
export default createStore({
  modules:{
    langStore,
    themeStore,
    userStore
  },

  state:{
    
  },
  mutations: {
  },
  actions: {
  },
  
})
