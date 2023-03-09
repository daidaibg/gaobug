import { Configuration, OpenAIApi } from "openai";

let configuration, openai: any;

export const chatInit = (key: string) => {
    configuration = new Configuration({
        // organization: "org-WQNdqrOXwFXVDFDOlSjyxiCw",
        apiKey: key,
    });
    openai = new OpenAIApi(configuration);
    console.log({ configuration, openai });

    return true
}


export const createChar = async (list: any[]) => {
    let data :{
        code:number,
        msg?:string,
        data:any
    } = {
          data:{},
          code:200  
    }
    try {
        const completion = await openai.createChatCompletion({
            model: "gpt-3.5-turbo",
            messages:list,
        });
        console.log("completion",completion);
        data.data=completion.data.choices[0]
         return data
           
    } catch (error:any) {
        console.log(error); 
        const msg = JSON.stringify(error)
        
        if(msg.indexOf("code 401")!= -1){
            data.code=401
            data.msg="ncorrect API key provided: sk*********************************************. You can find your API key at https://platform.openai.com/account/api-keys."
        }else{
            data.code = 201
            data.msg="未知异常"
        }
        return data
    }
   

}



