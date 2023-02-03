import screenfull from 'screenfull';
import MarkExtension from '@/utils/marked-mark';
// const getId = (text: string, level: string | number, raw: any, index: number) => {
//     return `gaobug-heade-${index}`;
// };
let mdEditorConfigFlag = false
export const cdnBase = '/cdns';
// 代码高亮cdn链接

export const generateId = (text: string, level: number, index: number):string =>{
    const id =(text+"_"+index).replace(/ /g, '%20')
    // const id =(text+"_"+index).replace(/ /g, '-')

    return id
};


export const mdEditorConfig = (MdEditor: any) => {
    if (mdEditorConfigFlag) return
    // console.log("mdEditorConfigFlag", mdEditorConfigFlag);
    try {
        MdEditor.config({
            editorExtensions: {
                iconfont: `${cdnBase}/iconfont/toobar_svg.js`,
                highlight: {
                    js: `${cdnBase}/highlight.js/11.6.0/highlight.min.js`
                },
                katex: {
                    js: `${cdnBase}/KaTeX/0.16.4/katex.min.js`,
                    css: `${cdnBase}/KaTeX/0.16.4/katex.min.css`
                },
                screenfull: {
                    // instance:()=> import("screenfull")
                    instance:screenfull
                },
                css: {
                    // a11y: {
                    //     light: `${cdnBase}/highlight.js/11.5.1/styles/a11y-light.min.css`,
                    //     dark: `${cdnBase}/highlight.js/11.5.1/styles/a11y-dark.min.css`
                    // },
                    atom: {
                        light: `${cdnBase}/highlight.js/11.6.0/atom-one-light.min.css`,
                        dark: `${cdnBase}/highlight.js/11.6.0/atom-one-dark.min.css`
                    },
                    github: {
                        light: `${cdnBase}/highlight.js/11.6.0/github.min.css`,
                        dark: `${cdnBase}/highlight.js/11.6.0/github-dark.min.css`
                    },
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
                }
            },
            markedExtensions: [MarkExtension],
            markedRenderer: (renderer: any) => {
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
                renderer.heading = (text: string, level: number, raw: any, s: any, index: number) => {
                    // console.log(text,level,raw,s,index);
                    const id =generateId(text, level, index)
                    return `<h${level} id="${id}">${text}</h${level}>`;
                };
                renderer.link = (href: any, title: any, text: any) => {
                    // console.log(href,text,title);
                    return `<a href="${href}" title="${title}" target="_blank">${text}</a>`;
                };
                return renderer;
            }
        });

        mdEditorConfigFlag = true
    } catch (error) {
        mdEditorConfigFlag = false
    }

}