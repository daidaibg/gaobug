// index.ts
import type { App } from "vue";
import setHighlight from "./highlight";

export default  function setDirectives(app: App) {
  // 加载需要的指令
  setHighlight(app);
}
