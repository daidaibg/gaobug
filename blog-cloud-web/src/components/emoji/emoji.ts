import { EmojiListType, EmojiObjType } from "./type";
import UtilVar from "@/config/UtilVar";

let emojiList: EmojiListType[] = [
  {
    name: "微笑",
    path: "/emoji/jj_emoji_1.7d9f699.png",
  },
  {
    name: "呲牙",
    path: "/emoji/jj_emoji_2.cd1e2bd.png",
  },
  {
    name: "色",
    path: "/emoji/jj_emoji_3.41d9a9a.png",
  },
  {
    name: "发呆",
    path: "/emoji/jj_emoji_4.28b310a.png",
  },
  {
    name: "可怜",
    path: "/emoji/jj_emoji_5.ece2a96.png",
  },
  {
    name: "流泪",
    path: "/emoji/jj_emoji_6.dde0d83.png",
  },
  {
    name: "害羞",
    path: "/emoji/jj_emoji_7.eca1535.png",
  },
  {
    name: "闭嘴",
    path: "/emoji/jj_emoji_8.c6ea514.png",
  },
  {
    name: "睡",
    path: "/emoji/jj_emoji_9.8cf4c38.png",
  },
  {
    name: "吃瓜群众",
    path: "/emoji/jj_emoji_10.42a731c.png",
  },
  {
    name: "尴尬",
    path: "/emoji/jj_emoji_11.8824107.png",
  },
  {
    name: "发怒",
    path: "/emoji/jj_emoji_12.0f24f23.png",
  },
  {
    name: "调皮",
    path: "/emoji/jj_emoji_13.aaa8265.png",
  },
  {
    name: "撇嘴",
    path: "/emoji/jj_emoji_14.8130d96.png",
  },
  {
    name: "思考",
    path: "/emoji/jj_emoji_15.f58c082.png",
  },
  {
    name: "不失礼貌的微笑",
    path: "/emoji/jj_emoji_16.9d17f6d.png",
  },
  {
    name: "奸笑",
    path: "/emoji/jj_emoji_17.bcebf79.png",
  },
  {
    name: "抓狂",
    path: "/emoji/jj_emoji_18.b93331d.png",
  },
  {
    name: "吐",
    path: "/emoji/jj_emoji_19.249e025.png",
  },
  {
    name: "偷笑",
    path: "/emoji/jj_emoji_20.7a46372.png",
  },
  {
    name: "愉快",
    path: "/emoji/jj_emoji_21.c408154.png",
  },
  {
    name: "白眼",
    path: "/emoji/jj_emoji_22.281d379.png",
  },
  {
    name: "傲慢",
    path: "/emoji/jj_emoji_23.27eb25d.png",
  },
  {
    name: "困",
    path: "/emoji/jj_emoji_24.6e6570b.png",
  },
  {
    name: "灵光一现",
    path: "/emoji/jj_emoji_25.51e6984.png",
  },
  {
    name: "流汗",
    path: "/emoji/jj_emoji_26.1117a72.png",
  },
  {
    name: "憨笑",
    path: "/emoji/jj_emoji_27.0006230.png",
  },
  {
    name: "捂脸",
    path: "/emoji/jj_emoji_28.8981538.png",
  },
  {
    name: "奋斗",
    path: "/emoji/jj_emoji_29.2801857.png",
  },
  {
    name: "咒骂",
    path: "/emoji/jj_emoji_30.91e4aa4.png",
  },
  {
    name: "疑问",
    path: "/emoji/jj_emoji_31.606e7a5.png",
  },
  {
    name: "嘘",
    path: "/emoji/jj_emoji_32.0de65f2.png",
  },
  {
    name: "晕",
    path: "/emoji/jj_emoji_33.12a8e53.png",
  },
  {
    name: "衰",
    path: "/emoji/jj_emoji_34.cf5b4d5.png",
  },
  {
    name: "骷髅",
    path: "/emoji/jj_emoji_35.8d57f28.png",
  },
  {
    name: "敲打",
    path: "/emoji/jj_emoji_36.c31c83f.png",
  },
  {
    name: "再见",
    path: "/emoji/jj_emoji_37.2f6e44f.png",
  },
  {
    name: "擦汗",
    path: "/emoji/jj_emoji_38.2d77d3e.png",
  },
  {
    name: "抠鼻",
    path: "/emoji/jj_emoji_39.5b41924.png",
  },
  {
    name: "泣不成声",
    path: "/emoji/jj_emoji_40.348108b.png",
  },
  {
    name: "坏笑",
    path: "/emoji/jj_emoji_41.2751fc1.png",
  },
  {
    name: "左哼哼",
    path: "/emoji/jj_emoji_42.886fc7b.png",
  },
  {
    name: "右哼哼",
    path: "/emoji/jj_emoji_43.ab03a70.png",
  },
  {
    name: "打哈欠",
    path: "/emoji/jj_emoji_44.74cc36f.png",
  },
  {
    name: "鄙视",
    path: "/emoji/jj_emoji_45.df24295.png",
  },
  {
    name: "委屈",
    path: "/emoji/jj_emoji_46.82c0215.png",
  },
  {
    name: "快哭了",
    path: "/emoji/jj_emoji_47.63391a1.png",
  },
  {
    name: "摸头",
    path: "/emoji/jj_emoji_48.1b40529.png",
  },
  {
    name: "阴险",
    path: "/emoji/jj_emoji_49.392c709.png",
  },
  {
    name: "亲亲",
    path: "/emoji/jj_emoji_50.419e678.png",
  },
  {
    name: "机智",
    path: "/emoji/jj_emoji_51.e6d838e.png",
  },
  {
    name: "得意",
    path: "/emoji/jj_emoji_52.cc6d6bc.png",
  },
  {
    name: "大金牙",
    path: "/emoji/jj_emoji_53.3e59e29.png",
  },
  {
    name: "拥抱",
    path: "/emoji/jj_emoji_54.08f068d.png",
  },
  {
    name: "大笑",
    path: "/emoji/jj_emoji_55.74ed629.png",
  },
  {
    name: "送心",
    path: "/emoji/jj_emoji_56.5777828.png",
  },
  {
    name: "震惊",
    path: "/emoji/jj_emoji_57.6b8baad.png",
  },
  {
    name: "酷拽",
    path: "/emoji/jj_emoji_58.327784e.png",
  },
  {
    name: "尬笑",
    path: "/emoji/jj_emoji_59.8a63cb5.png",
  },
  {
    name: "大哭",
    path: "/emoji/jj_emoji_60.9016267.png",
  },
  {
    name: "哭笑",
    path: "/emoji/jj_emoji_61.a296509.png",
  },
  {
    name: "做鬼脸",
    path: "/emoji/jj_emoji_62.d7ca811.png",
  },
  {
    name: "红脸",
    path: "/emoji/jj_emoji_63.c32f5b5.png",
  },
  {
    name: "鼓掌",
    path: "/emoji/jj_emoji_64.6c4f6bb.png",
  },
  {
    name: "恐惧",
    path: "/emoji/jj_emoji_65.19740fe.png",
  },
  {
    name: "斜眼",
    path: "/emoji/jj_emoji_66.fb6750b.png",
  },
  {
    name: "嘿哈",
    path: "/emoji/jj_emoji_67.9ceed33.png",
  },
  {
    name: "惊讶",
    path: "/emoji/jj_emoji_68.1e326db.png",
  },
  {
    name: "绝望的凝视",
    path: "/emoji/jj_emoji_69.cabd864.png",
  },
  {
    name: "囧",
    path: "/emoji/jj_emoji_70.285ebe9.png",
  },
  {
    name: "皱眉",
    path: "/emoji/jj_emoji_71.e8c2090.png",
  },
  {
    name: "耶",
    path: "/emoji/jj_emoji_72.04f3881.png",
  },
  {
    name: "石化",
    path: "/emoji/jj_emoji_73.55516c0.png",
  },
  {
    name: "我想静静",
    path: "/emoji/jj_emoji_74.d954f2d.png",
  },
  {
    name: "吐血",
    path: "/emoji/jj_emoji_75.b7ccb48.png",
  },
  {
    name: "互粉",
    path: "/emoji/jj_emoji_76.8452494.png",
  },
  {
    name: "互相关注",
    path: "/emoji/jj_emoji_77.afd454e.png",
  },
  {
    name: "加好友",
    path: "/emoji/jj_emoji_78.28310a5.png",
  },
  {
    name: "强",
    path: "/emoji/jj_emoji_79.6185c09.png",
  },
  {
    name: "钱",
    path: "/emoji/jj_emoji_80.d0a73bb.png",
  },
  {
    name: "飞吻",
    path: "/emoji/jj_emoji_81.8e1cf74.png",
  },
  {
    name: "打脸",
    path: "/emoji/jj_emoji_82.c6f2aee.png",
  },
  {
    name: "惊恐",
    path: "/emoji/jj_emoji_83.bc66012.png",
  },
  {
    name: "悠闲",
    path: "/emoji/jj_emoji_84.10dda08.png",
  },
  {
    name: "泪奔",
    path: "/emoji/jj_emoji_85.43ad2fc.png",
  },
  {
    name: "舔屏",
    path: "/emoji/jj_emoji_86.8b2a629.png",
  },
  {
    name: "紫薇别走",
    path: "/emoji/jj_emoji_87.f741137.png",
  },
  {
    name: "听歌",
    path: "/emoji/jj_emoji_88.4eb52e4.png",
  },
  {
    name: "难过",
    path: "/emoji/jj_emoji_89.5a2cba8.png",
  },
  {
    name: "生病",
    path: "/emoji/jj_emoji_90.aa0a4e8.png",
  },
  {
    name: "绿帽子",
    path: "/emoji/jj_emoji_91.57fc147.png",
  },
  {
    name: "如花",
    path: "/emoji/jj_emoji_92.13db022.png",
  },
  {
    name: "惊喜",
    path: "/emoji/jj_emoji_93.5ee641b.png",
  },
  {
    name: "吐彩虹",
    path: "/emoji/jj_emoji_94.f9c84dc.png",
  },
  {
    name: "吐舌",
    path: "/emoji/jj_emoji_95.6b0752f.png",
  },
  {
    name: "无辜呆",
    path: "/emoji/jj_emoji_96.4fb9d71.png",
  },
  {
    name: "看",
    path: "/emoji/jj_emoji_97.39cdc9f.png",
  },
  {
    name: "白眼的狗",
    path: "/emoji/jj_emoji_98.9f6d4ad.png",
  },
  {
    name: "黑脸",
    path: "/emoji/jj_emoji_99.3264111.png",
  },
  {
    name: "猪头",
    path: "/emoji/jj_emoji_100.6adf5c8.png",
  },
  {
    name: "熊吉",
    path: "/emoji/jj_emoji_101.dec591d.png",
  },
  {
    name: "不看",
    path: "/emoji/jj_emoji_102.b113c42.png",
  },
  {
    name: "玫瑰",
    path: "/emoji/jj_emoji_103.6c2eed8.png",
  },
  {
    name: "凋谢",
    path: "/emoji/jj_emoji_104.680e2d1.png",
  },
  {
    name: "嘴唇",
    path: "/emoji/jj_emoji_105.3881f71.png",
  },
  {
    name: "爱心",
    path: "/emoji/jj_emoji_106.ba42c9a.png",
  },
  {
    name: "心碎",
    path: "/emoji/jj_emoji_107.1c0b941.png",
  },
  {
    name: "赞",
    path: "/emoji/jj_emoji_108.a6defc6.png",
  },
  {
    name: "弱",
    path: "/emoji/jj_emoji_109.7854933.png",
  },
  {
    name: "握手",
    path: "/emoji/jj_emoji_110.34d5723.png",
  },
  {
    name: "ok",
    path: "/emoji/jj_emoji_111.b8dc9e6.png",
  },
  {
    name: "谢谢",
    path: "/emoji/jj_emoji_112.2dd347d.png",
  },
  {
    name: "比心",
    path: "/emoji/jj_emoji_113.582f64b.png",
  },
  {
    name: "碰拳",
    path: "/emoji/jj_emoji_114.4b8db8b.png",
  },
  {
    name: "击掌",
    path: "/emoji/jj_emoji_115.a7dbbf7.png",
  },
  {
    name: "左",
    path: "/emoji/jj_emoji_116.16df22c.png",
  },
  {
    name: "右",
    path: "/emoji/jj_emoji_117.6dd2889.png",
  },
  {
    name: "力量",
    path: "/emoji/jj_emoji_118.1612154.png",
  },
  {
    name: "胜利",
    path: "/emoji/jj_emoji_119.947368f.png",
  },
  {
    name: "抱拳",
    path: "/emoji/jj_emoji_120.6c3effe.png",
  },
  {
    name: "勾引",
    path: "/emoji/jj_emoji_121.570cd4d.png",
  },
  {
    name: "拳头",
    path: "/emoji/jj_emoji_122.b3763ee.png",
  },
  {
    name: "庆祝",
    path: "/emoji/jj_emoji_123.da47506.png",
  },
  {
    name: "礼物",
    path: "/emoji/jj_emoji_124.83c188d.png",
  },
  {
    name: "红包",
    path: "/emoji/jj_emoji_125.ac32796.png",
  },
  {
    name: "18禁",
    path: "/emoji/jj_emoji_126.21e6944.png",
  },
  {
    name: "去污粉",
    path: "/emoji/jj_emoji_127.ce1b9d7.png",
  },
  {
    name: "666",
    path: "/emoji/jj_emoji_128.e55728c.png",
  },
  {
    name: "给力",
    path: "/emoji/jj_emoji_129.0b59244.png",
  },
  {
    name: "v5",
    path: "/emoji/jj_emoji_130.2e42cc3.png",
  },
  {
    name: "菜刀",
    path: "/emoji/jj_emoji_131.e225098.png",
  },
  {
    name: "炸弹",
    path: "/emoji/jj_emoji_132.fe320d4.png",
  },
  {
    name: "便便",
    path: "/emoji/jj_emoji_133.2665597.png",
  },
  {
    name: "月亮",
    path: "/emoji/jj_emoji_134.a4856c6.png",
  },
  {
    name: "太阳",
    path: "/emoji/jj_emoji_135.5741bcc.png",
  },
  {
    name: "发",
    path: "/emoji/jj_emoji_136.5eba004.png",
  },
  {
    name: "黄瓜",
    path: "/emoji/jj_emoji_137.9dd21f9.png",
  },
  {
    name: "西瓜",
    path: "/emoji/jj_emoji_138.b0effa7.png",
  },
  {
    name: "啤酒",
    path: "/emoji/jj_emoji_139.ccb84eb.png",
  },
  {
    name: "咖啡",
    path: "/emoji/jj_emoji_140.6908619.png",
  },
  {
    name: "蛋糕",
    path: "/emoji/jj_emoji_141.7bc6b3d.png",
  },
];

export const emojiObj: EmojiObjType = {};
emojiList= emojiList.map((item) => {
  const path = UtilVar.assetsBaseUrl + "/blog-static"+item.path;
  emojiObj["[" + item.name + "]"] = path;
  return {
    ...item,
    path,
  };
});

export { emojiList };
