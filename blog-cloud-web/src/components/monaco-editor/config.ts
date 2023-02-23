export type Theme = 'vs' | 'hc-black' | 'vs-dark' | 'hc-light' |'default-dark'
export type FoldingStrategy = 'auto' | 'indentation'
export type RenderLineHighlight = 'all' | 'line' | 'none' | 'gutter'
export interface Options {
    automaticLayout: boolean // 自适应布局
    foldingStrategy: FoldingStrategy // 折叠方式  auto | indentation
    renderLineHighlight: RenderLineHighlight // 行亮
    selectOnLineNumbers: boolean // 显示行号
    placeholder: string
    minimap: {
        // 关闭小地图
        enabled: boolean
    }
    // readOnly: Boolean // 只读
    fontSize: number // 字体大小
    scrollBeyondLastLine: boolean // 取消代码后面一大段空白
    overviewRulerBorder: boolean // 不要滚动条的边框
}
//基础配置
export const defaultOption = {
    automaticLayout: true,
    // foldingStrategy: 'indentation',
    foldingStrategy: "indentation", // 折叠方式  auto | indentation
    // renderLineHighlight: 'all',
    renderLineHighlight: "all" || "line" || "none" || "gutter", // 行亮
    selectOnLineNumbers: true, // 显示行号
    minimap: {
        // 关闭小地图
        enabled: false,
    },
    placeholder: "请输入内容",
    // readOnly: false, // 只读
    fontSize: 16, // 字体大小
    scrollBeyondLastLine: true, // 取消代码后面一大段空白
    overviewRulerBorder: false, // 不要滚动条的边框
}