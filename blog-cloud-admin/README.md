## 组件

### 验证码组件

l路径：src\components\login-slider 

例子：

```vue
 <LoginSlider
     v-show="codeShow"
     ref="loginSliderRef"
     :randomStr="randomStr"
     @parentHandleSubmit="handleSubmit"
     @close="closecode"
  ></LoginSlider>

<script>
	export default {
        data(){
            return {
              codeShow: false,  //控制显隐
               randomStr: getUUID(),//随机数
			}
		},
        methods:{
            //鼠标拖拽up后
             handleSubmit(code) {
                  this.captcha = code;
                  this.submit();
              },
            //关闭事件
              closecode() {
                  this.codeShow = false;
               },
            	//一些方法
            someMethods(){
                //验证成功
                 this.$refs.loginSliderRef.onSuccess();
                //验证失败
                 this.$refs.loginSliderRef.onFail();
            }
        }
    }
</script>


```

#### 参数

如果需要控制显隐，需要在组件外部控制显隐。



| 参数名              | 说明                             | 默认值 |
| ------------------- | -------------------------------- | ------ |
| randomStr           | 唯一值，例如uuid                 | --     |
| @parentHandleSubmit | 用户拖拽后鼠标松开触发           | --     |
| @close              | 用户点击关闭按钮，校验成功后触发 |        |

#### 方法

通过组件ref 去调用组件提供方法。

项目为后端验证是否成功与失败，所以需要在组件外部去出发验证成功或失败，组件内部不参与业务逻辑，只负责过去验证吗图片。

| 名称      | 说明                             |
| --------- | -------------------------------- |
| onSuccess | 校验成功，需要去调用组件内此方法 |
| onFail    | 校验失败，需要去调组件内此方法   |

