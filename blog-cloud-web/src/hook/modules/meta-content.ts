import { onBeforeUnmount } from "vue"

interface TargetsType {
    [key: string]: {
        target: any,
        oldContent: string|null
    }
}
export const useMetaContent = () => {
    let targets: TargetsType = {}
    const setMetaTagContent = (name: string, value: string) => {
        let target = document.querySelector(`meta[name="${name}"]`)
        if (target) {
            targets[name]={
                target : target,
                oldContent:target.getAttribute("content")
            }
            target.setAttribute("content", value);
        }
        // let newValue = value.replace(/[^a-zA-Z]/g,' ').split(" ")
        // newValue= newValue.filter(item=>{
        //     return item!=''
        // })
        // console.log(newValue);
    }
    const setOldMetaTagContent = () => {
        Object.keys(targets).forEach(item => {
            targets[item].target.setAttribute("content", targets[item].oldContent);
        })

    }
    onBeforeUnmount(() => {
        setOldMetaTagContent()
    })
    return {
        setMetaTagContent
    }
}