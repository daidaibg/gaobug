import { languageList, } from "./code-format-config";
import { languageIcons } from "@/config/languageIcons";
import { guid } from "@/utils/current";

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

//创建文件data
export const createFileData = (data: string) => {
  const laguageData = getLaguageData(data);
  const newFileData = {
    title: data,
    language: laguageData.fileLanguage,
    content: "",
    id: guid(),
    icon: laguageData.fileIconData.icon.name,
  };
  return { newFileData, laguageData };
};

//创建文件夹data
export const createFolderData = (val: string) => {
  const newFileData = {
    title: val,
    type: "2",
    content: "",
    language: "2",
    icon: "folder",
    id: guid(),
  };
  return newFileData
};

//定时器promise类型
export const timerPromise = (time: number) => {
  return new Promise<void>((resolve, reject) => {
    const timer = setTimeout(() => {
      clearTimeout(timer);
      resolve();
    }, time);
  });
};