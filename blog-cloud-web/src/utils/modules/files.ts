import { saveAs } from 'file-saver';
//https://github.com/eligrey/FileSaver.js#filesaverjs
interface JSONTTYPE{
    [key:string]:string|number|null|JSONTTYPE|JSONTTYPE[]
}
export const saveJSON = async (json: JSONTTYPE,name="data.json") => {
    try {
        // 转换为 Blob
        const blob = new Blob([JSON.stringify(json)], { type: 'application/json' });
        // 使用 file-saver 库保存文件
        saveAs(blob,name);
        return true
    } catch (error) {
        return error
    }
}