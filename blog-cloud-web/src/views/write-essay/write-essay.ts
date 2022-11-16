import { uploadImg } from "@/api";



// 上传图片
export const onUploadImg = async (files: any, callback: Function) => {
  const res = await Promise.all(
    files.map((file: any) => {
      return new Promise((resolve, rej) => {
        const form = new FormData();
        form.append("file", file);
        uploadImg(form).then((res) => {
          // console.log(res);
          if (res.code === 200) {
            resolve(res.data.url);
          } else {
            resolve("图片保存失败，" + res.msg);
          }
        });
      });
    })
  );
  callback(res);
};
// 上传封面
export const onUploadCover = async (option: any) => {
  const form = new FormData();
  form.append("file", option.file);
  return uploadImg(form, option)

};
//上传前
export const beforeAvatarUpload = (rawFile: any, ElMessage: any) => {
  if (!['image/jpeg', 'image/png', 'image/gif'].includes(rawFile.type)) {
    ElMessage.warning("格式必须为JPG,PNG或GIF!")
    return false
  } else if (rawFile.size / 1024 / 1024 > 10) {
    ElMessage.warning("图片大小不能超过10M!")
    return false
  }
  return true
}
// 标签列表
export const tagsList = [
  {
    name: 'javaScript',
  },
  {
    name: 'java',
  },
  {
    name: 'TS',
  },
  {
    name: 'vue3',
  },
  {
    name: 'vue2',
  },
  {
    name: 'css',
  },
  {
    name: 'html',
  },
]

// previewTheme类型：'default' | 'github' | 'vuepress' | 'mk-cute' | 'smart-blue' | 'cyanosis'
export const toolbars: any = [
  'bold',
  'underline',
  'italic',
  '-',
  'strikeThrough',
  'sub',
  'sup',
  'quote',
  'unorderedList',
  'orderedList',
  '-',
  'codeRow',
  'code',
  'link',
  'image',
  'table',
  'mermaid',
  'katex',
  0,
  1,
  2,
  3,
  '-',
  'revoke',
  'next',
  'save',

  '=',
  'prettier',
  // 'pageFullscreen',
  'fullscreen',
  'preview',
  // 'htmlPreview',
  'catalog',
  // 'github',
];