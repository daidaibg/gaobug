
import type { App } from 'vue';
import { createPinia } from 'pinia';
export * from "./modules/lang/lang-store"
export * from "./modules/theme/theme-store"
export * from "./modules/user/user-store"

const store = createPinia();

export function setupStore(app: App<Element>) {
  app.use(store);
}

export default {store} ;

