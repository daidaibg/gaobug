import screenfull from "screenfull";
import { config } from "md-editor-v3";
import TargetBlankExtension from "./target-blank-extension";
//新的
//@ts-ignore
import MarkExtension from "markdown-it-mark";
//老的
// import MarkExtension from '@/utils/marked-mark';


let mdEditorConfigFlag = false;
console.log(import.meta.env);

export const cdnBase =window.location.origin+ import.meta.env.BASE_URL +"/cdns";
const highlightJsBaseCdn = `${cdnBase}/highlight.js/11.8.0/`;
// 代码高亮cdn链接

export const generateId = (text: string, level: number, index: number): string => {
  const id = (text + "_" + index).replace(/ /g, "-");
  // const id =(text+"_"+index).replace(/ /g, '-')
  return id;
};

// const getId = (text: string, level: string | number, raw: any, index: number) => {
//     return `gaobug-heade-${index}`;
// };


export const mdEditorConfig = () => {
  if (mdEditorConfigFlag) return;
  // console.log("mdEditorConfigFlag", mdEditorConfigFlag);
  try {
    config({
      iconfontType: "svg",

      markdownItConfig(md: any) {
        md.use(MarkExtension); //新的mark 扩展
        md.use(TargetBlankExtension);
      },

      // markedExtensions: [MarkExtension],//老的mark 扩展

      editorExtensions: {
        iconfont: `${cdnBase}/iconfont/md-edit.js`,
        highlight: {
          js: `${highlightJsBaseCdn}/highlight.min.js`,
          css: {
            atom: {
              light: `${highlightJsBaseCdn}/atom-one-light.min.css`,
              dark: `${highlightJsBaseCdn}/atom-one-dark.min.css`,
            },
            a11y: {
              light: `${highlightJsBaseCdn}/a11y-light.min.css`,
              dark: `${highlightJsBaseCdn}/a11y-dark.min.css`,
            },
            github: {
              light: `${highlightJsBaseCdn}/github.min.css`,
              dark: `${highlightJsBaseCdn}/github-dark.min.css`,
            },
            gradient: {
              light: `${highlightJsBaseCdn}/gradient-light.min.css`,
              dark: `${highlightJsBaseCdn}/gradient-dark.min.css`,
            },
            kimbie: {
              light: `${highlightJsBaseCdn}/kimbie-light.min.css`,
              dark: `${highlightJsBaseCdn}/kimbie-dark.min.css`,
            },
            paraiso: {
              light: `${highlightJsBaseCdn}/paraiso-light.min.css`,
              dark: `${highlightJsBaseCdn}/paraiso-dark.min.css`,
            },
            qtcreator: {
              light: `${highlightJsBaseCdn}/qtcreator-light.min.css`,
              dark: `${highlightJsBaseCdn}/qtcreator-dark.min.css`,
            },
            stackoverflow: {
              light: `${highlightJsBaseCdn}/stackoverflow-light.min.css`,
              dark: `${highlightJsBaseCdn}/stackoverflow-dark.min.css`,
            },
          },
        },
        katex: {
          js: `${cdnBase}/KaTeX/0.16.4/katex.min.js`,
          css: `${cdnBase}/KaTeX/0.16.4/katex.min.css`,
        },
        screenfull: {
          instance: screenfull,
        },
      },
    });

    mdEditorConfigFlag = true;
  } catch (error) {
    mdEditorConfigFlag = false;
    throw error;
  }
};

