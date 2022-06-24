const getId = (text: string, level: string | number, raw: any, index: number) => {
    return `gaobug-heade-${index}`;
};
let mdEditorConfigFlag = false

export const mdEditorConfig = (MdEditor: any) => {
    if (mdEditorConfigFlag) return
    console.log("mdEditorConfigFlag", mdEditorConfigFlag);
    try {
        MdEditor.config({
            editorExtensions: {
                iconfont: "https://www.gaobug.com/static/js/toobar_svg.js",
            },
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
                renderer.heading = (text:any, level:any, raw:any, s:any, index:any) => {
                    // console.log(text,level,raw,s,index);
                    return `<h${level} id="gaobug-heade-${index}">${text}</h${level}>`;
                  };
                renderer.link = (href:any, title:any, text:any) => {
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