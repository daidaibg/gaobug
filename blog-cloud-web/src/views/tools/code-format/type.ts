import type { Options, Theme } from "@/components/monaco-editor";

export interface FileItemType {
  content: string;
  id: string;
  language: string;
  title: string;
  icon: string;
  type?: string,//2:文件夹
  children?: FileItemType[]
}

export enum CommonEnums {
  theme = "theme"
}

export interface EditorOptionType {
  theme: Theme;
  commonKeyword: string;
  editValue: string;
  languageModel: string;
  hightChange: boolean;
  options: {
    minimap: {
      enabled: boolean; //显示小地图
    };
  };
}