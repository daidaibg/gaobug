import { read, utils } from 'xlsx';
export const readFileToJson = (file: any) => {
    return new Promise((resolve, reject) => {
        let reader = new FileReader()
        reader.readAsBinaryString(file)
        reader.onload = (ev: any) => {
            let workBook = read(ev.target.result, { type: 'binary', cellDates: true })
            let ws:any={};
            workBook.SheetNames.forEach(wsname=>{
                ws[wsname]= utils.sheet_to_json(workBook.Sheets[wsname]);
            })
            resolve(ws)
        }
        reader.onerror = (error: any) => {
            reject(error)
        }
    })
}

