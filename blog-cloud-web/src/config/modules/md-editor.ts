import screenfull from 'screenfull';
import { config } from 'md-editor-v3';
//新的
//@ts-ignore
import MarkExtension from 'markdown-it-mark';
//老的
// import MarkExtension from '@/utils/marked-mark';

// const getId = (text: string, level: string | number, raw: any, index: number) => {
//     return `gaobug-heade-${index}`;
// };
let mdEditorConfigFlag = false
export const cdnBase = '/cdns';
// 代码高亮cdn链接

export const generateId = (text: string, level: number, index: number): string => {
    const id = (text + "_" + index).replace(/ /g, '%20')
    // const id =(text+"_"+index).replace(/ /g, '-')
    return id
};


export const mdEditorConfig = () => {
    if (mdEditorConfigFlag) return
    // console.log("mdEditorConfigFlag", mdEditorConfigFlag);
    try {
        config({
            markdownItConfig(md: any) {
                md.use(MarkExtension); //新的mark 扩展
            },

            // markedExtensions: [MarkExtension],//老的mark 扩展

            /**
             * 
             * @param theme 
             * @param extensions 
             * @see https://imzbf.github.io/md-editor-v3/zh-CN/docs#%F0%9F%92%B4%20%E9%85%8D%E7%BD%AE%E7%BC%96%E8%BE%91%E5%99%A8
             */
            // codeMirrorExtensions(theme: any, extensions: any) {
            //     const _exs = [...extensions, lineNumbers()];
            //     // _exs[1] = basicSetup;
            //     return _exs;
            // },
            // markedRenderer: (renderer: any) => {
            //     renderer.heading = (text: string, level: number, raw: string, s: any, index: number) => {
            //         // console.log(text,level,raw,s,index);
            //         const id = generateId(text, level, index)
            //         return `<h${level} id="${id}">${text}</h${level}>`;
            //     };
            //     renderer.link = (href: any, title: any, text: any) => {
            //         // console.log(href,text,title);
            //         return `<a href="${href}" title="${title}" target="_blank">${text}</a>`;
            //     };
            //     return renderer;
            // },
            editorExtensions: {
                iconfont: `${cdnBase}/iconfont/toobar_svg.js`,
                highlight: {
                    js: `${cdnBase}/highlight.js/11.7.0/highlight.min.js`,
                    css: {
                        atom: {
                            light: `${cdnBase}/highlight.js/11.7.0/atom-one-light.min.css`,
                            dark: `${cdnBase}/highlight.js/11.7.0/atom-one-dark.min.css`
                        },
                        github: {
                            light: `${cdnBase}/highlight.js/11.7.0/github.min.css`,
                            dark: `${cdnBase}/highlight.js/11.7.0/github-dark.min.css`
                        },
                    }
                },
                katex: {
                    js: `${cdnBase}/KaTeX/0.16.4/katex.min.js`,
                    css: `${cdnBase}/KaTeX/0.16.4/katex.min.css`
                },
                screenfull: {
                    // instance:()=> import("screenfull")
                    instance: screenfull
                },
                // css: {
                    // a11y: {
                    //     light: `${cdnBase}/highlight.js/11.5.1/styles/a11y-light.min.css`,
                    //     dark: `${cdnBase}/highlight.js/11.5.1/styles/a11y-dark.min.css`
                    // },

                    // gradient: {
                    //     light: `${cdnBase}/highlight.js/11.5.1/styles/gradient-light.min.css`,
                    //     dark: `${cdnBase}/highlight.js/11.5.1/styles/gradient-dark.min.css`
                    // },
                    // kimbie: {
                    //     light: `${cdnBase}/highlight.js/11.5.1/styles/kimbie-light.min.css`,
                    //     dark: `${cdnBase}/highlight.js/11.5.1/styles/kimbie-dark.min.css`
                    // },
                    // paraiso: {
                    //     light: `${cdnBase}/highlight.js/11.5.1/styles/paraiso-light.min.css`,
                    //     dark: `${cdnBase}/highlight.js/11.5.1/styles/paraiso-dark.min.css`
                    // },
                    // qtcreator: {
                    //     light: `${cdnBase}/highlight.js/11.5.1/styles/qtcreator-light.min.css`,
                    //     dark: `${cdnBase}/highlight.js/11.5.1/styles/qtcreator-dark.min.css`
                    // },
                    // stackoverflow: {
                    //     light: `${cdnBase}/highlight.js/11.5.1/styles/stackoverflow-light.min.css`,
                    //     dark: `${cdnBase}/highlight.js/11.5.1/styles/stackoverflow-dark.min.css`
                    // }
                // }
            },
        });

        mdEditorConfigFlag = true
    } catch (error) {
        mdEditorConfigFlag = false
        throw error
    }

}


// let index = 0
// renderer.heading = (text: any, level: any, raw: any) => {
//     // console.log(text, level, raw, index);
//     const id = getId(text, level, raw, index);
//     index++
//     if (/<a.*>.*<\/a>/.test(text)) {
//         // (?<=\<a.*)>(?=.*<\/a>)
//         return `<h${level} id="${id}">${text.replace(/(.*\<a.*)>(.*<\/a>)/, "$1" + ' target="_blank">' + "$2")}</h${level}>`;
//     } else if (text !== raw) {
//         return `<h${level} id="${id}">${text}</h${level}>`;
//     } else {
//         // <a href="#${id}">${raw}</a>
//         return `<h${level} id="${id}" class="title_md">${raw}</h${level}>`;
//     }
// };