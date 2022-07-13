module.exports = {
    description: 'create a tablepage',
    prompts: [
        {
            type: 'input',
            name: 'name',
            message:  'Please enter tablepage name，such as "tablepageName" :',
            validate(value) {
                if (!value || value.trim === '') {
                    return 'name is required';
                }
                return true;
            },
        },
      
        {
            type: 'confirm',
            name: 'isParentFolder',
            message: '是否同名父级文件夹，such as "Y/N" :',
            default:"",
         
        },
        {
            type: 'input',
            name: 'parentFolder',
            message:'父级文件夹名称，such as "parentFolder" :',
            default:"",
            when:(data)=>{
                console.log(data);
                return !data.isParentFolder
            }
        },
        {
            type: 'confirm',
            name: 'addUpdate',
            message:  '是否有新、编辑功能，such as "Y/N" :',
            default:"N"
        }
    ], // array of inquirer prompts
    actions: (data) => {
        const dataName = data.name
        console.log(data);
        const actions = [
            {
                type: 'add',
                path: `../src/views/${data.parentFolder||dataName}/${dataName}.vue`,
                templateFile: './table-page-templates/index.hbs',
                data: {
                    
                  }
            }
        ]
        return actions
    }
}