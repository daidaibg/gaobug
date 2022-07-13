import { defineStore } from "pinia";
import { MenuNavStateType } from "../../interface"
export const useMenuNavStore = defineStore({
  id: "useMenuNavStore",
  state: (): MenuNavStateType => {
    return {
      isCollapse: false,
      menusViewRoutes: []
    }
  },
  getters: {

  },
  actions: {
    setIsCollapse(isCollapse: boolean) {
      this.isCollapse = isCollapse
    },
    getMenus() {
      this.menusViewRoutes = [
        {
          title: "控制台",
          path: "/home",
          icon: "gb-icon-kongzhitai",
        },
        {
          title: "文章管理",
          path: "1",
          icon: "gb-icon-guanliwenzhang",
          children: [
            {
              title: "控制台",
              path: "1-2",
              icon: "yh-icons-monitor",
            }, {
              title: "控制台",
              path: "1-3",
              icon: "yh-icons-monitor",
            }
          ],
        }, {
          title: "系统管理",
          path: "sys",
          icon: "gb-icon-xitongshezhi",
          children: [
            {
              title: "用户管理",
              path: "/sys/user",
            },
            {
              title: "角色管理",
              path: "/sys/role",
            },
            {
              title: "菜单管理",
              path: "/sys/menu",
            }
          ],
        }
      ]
    }
  },
});