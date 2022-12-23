import { languageList,  } from "./code-format-config";
import { languageIcons } from "@/config/languageIcons";

//获取语言数据信息 
export const getLaguageData = (data: string): any => {
    const fileNameSplit = data.split(".");
    let fileLanguage: any = fileNameSplit[fileNameSplit.length - 1];
    fileLanguage = languageList.filter((item) => {
      return item.label == fileLanguage;
    });
    fileLanguage = fileLanguage.length >= 1 ? fileLanguage[0].value : "plaintext";
    let fileIconData: any = { icon: { name: "document" } };
    for (let index = 0; index < languageIcons.length; index++) {
      const element = languageIcons[index];
      if (element.ids.includes(fileLanguage)) {
        fileIconData = element;
        break;
      }
    }
    return {
      fileIconData,
      fileLanguage,
    };
  };