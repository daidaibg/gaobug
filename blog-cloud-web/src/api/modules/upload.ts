import {FILEPOST} from "../api";
import {Home} from "./home"
export const uploadImg = (param: any,config?:any) => {
    return FILEPOST(Home['upload'], param,config||{});
  };