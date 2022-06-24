import {FILEPOST} from "../api";
import Urls from "./home"
export const uploadImg = (param: any,config?:any) => {
    return FILEPOST(Urls['upload'], param,config||{});
  };