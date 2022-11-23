import { StorageEnum } from "@/enums"
import { getLocalStorage } from "@/utils/modules/storage"


export function getToken(){
   return getLocalStorage(StorageEnum.GB_TOKEN_STORE);
}