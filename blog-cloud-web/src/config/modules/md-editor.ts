const getId = (text: string, level: string | number, raw: any, index: number) => {
    return `gaobug-heade-${index}`;
};
let mdEditorConfigFlag = false
export const mdEditorConfig = (MdEditor: any) => {
    if (mdEditorConfigFlag) return
    console.log("mdEditorConfigFlag",mdEditorConfigFlag);
    try {
        MdEditor.config({
            editorExtensions: {
                iconfont: "https://www.gaobug.com/static/js/toobar_svg.js",
            },
            markedRenderer: (renderer: any) => {
                let index = 0
                renderer.heading = (text: any, level: any, raw: any) => {
                    // console.log(text, level, raw, index);
                    const id = getId(text, level, raw, index);
                    index++
                    if (/<a.*>.*<\/a>/.test(text)) {
                        return `<h${level} id="${id}">${text.replace(
                            /(?<=\<a.*)>(?=.*<\/a>)/,
                            ' target="_blank">'
                        )}</h${level}>`;
                    } else if (text !== raw) {
                        return `<h${level} id="${id}">${text}</h${level}>`;
                    } else {
                        // <a href="#${id}">${raw}</a>
                        return `<h${level} id="${id}" class="title_md">${raw}</h${level}>`;
                    }
                };
                return renderer;
            }
        });
        
        mdEditorConfigFlag = true
    } catch (error) {
        mdEditorConfigFlag = false
    }

}