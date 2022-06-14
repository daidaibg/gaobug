import {currentGET} from "@/api"
import {clearLocalStorage} from "@/utils"
import {StorageEnum} from "@/enums"
import {ElMessage} from "element-plus"
export default {
    namespaced: true,
    name: "userStore",
    state: {
        isLogin: false,//是否登录
        modelLoginShow: false,
        userData:{},
    },
    mutations: {
        //上线
        userOnline(state: any) {
            state.isLogin = true
        },
          //下线清除用户信息
          userOffline(state:any){
            state.isLogin=false
            state.userData={}
            clearLocalStorage(StorageEnum.GB_TOKEN_STORE)
        },
        //打开登陆弹窗
        onModelLogin(state: any) {
            state.modelLoginShow = true
        },
        //关闭登陆弹窗
        offModelLogin(state: any) {
            state.modelLoginShow = false
        },
     
    },
    actions: {
        getUserInfo({state}:any){
            currentGET('userInfo').then(res=>{
                console.log("userData",res);
                if(res.code==200){
                    state.isLogin=true
                    state.userData=res.data
                }else if(res.code ==204){  //请求成功但没内容
                    // ElMessage.error(res.msg)
                }else{
                    ElMessage.error(res.msg)
                }
            })
        }
    },

};

    // /**
    //  * 数据主键
    //  */
    //  private Long id;

    //  /**
    //   * 用户名
    //   */
    //  private String username;
 
    //  /**
    //   * 性别(1:男2:女)
    //   */
    //  private Integer gender;
 
    //  /**
    //   * 个人头像
    //   */
    //  private String avatar;
 
    //  /**
    //   * 邮箱
    //   */
    //  private String email;
 
    //  /**
    //   * 出生年月日
    //   */
    //  private Date birthday;
 
    //  /**
    //   * 手机
    //   */
    //  private String mobile;
 
    //  /**
    //   * 个人简介
    //   */
    //  private String summary;
 
    //  /**
    //   * 昵称
    //   */
    //  private String nickName;
    //  /**
    //   * 今日阅读数
    //   */
    //  private String readToday;
    //  /**
    //   * 昨日阅读数
    //   */
    //  private String readYesterday;
    //  /**
    //   * 今日点赞
    //   */
    //  private String likeToday;
    //  /**
    //   * 昨日点赞
    //   */
    //  private String likeYesterday;
    //  /**
    //   * 我的文章
    //   */
    //  private String myArticle;
    //  /**
    //   * 我的收藏
    //   */
    //  private String myCollection;
    //  /**
    //   * 我的关注
    //   */
    //  private String myAttention;
    //  /**
    //   * 最后查询时间
    //   */
    //  private Date lastTime;