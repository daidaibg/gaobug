export const catalogueListDefault = {
    title: "default.json",
    language: "json",
    content: ``,
    id: "1",
    icon: "json",
  };
export const languageList = [
    { value: "javascript", label: "js" },
    { value: "json", label: "json" },
    { value: "typescript", label: "ts" },
    { value: "html", label: "html" },
    { value: "css", label: "css" },
    { value: "less", label: "less" },
    { value: "mysql", label: "mysql" },
    { value: "java", label: "java" },
    { value: "sql", label: "sql" },
    { value: "markdown", label: "markdown" },
    { value: "markdown", label: "md" },
    { value: "xml", label: "xml" },
    { value: "yaml", label: "yaml" },
    { value: "apex", label: "apex" },
    { value: "azcli", label: "azcli" },
    { value: "bat", label: "bat" },
    { value: "c", label: "c" },
    { value: "clojure", label: "clojure" },
    { value: "coffeescript", label: "coffeescript" },
    { value: "cpp", label: "cpp" },
    { value: "csharp", label: "csharp" },
    { value: "csp", label: "csp" },
    { value: "dockerfile", label: "dockerfile" },
    { value: "fsharp", label: "fsharp" },
    { value: "go", label: "go" },
    { value: "graphql", label: "graphql" },
    { value: "handlebars", label: "handlebars" },
    { value: "ini", label: "ini" },
    { value: "kotlin", label: "kotlin" },
    { value: "lua", label: "lua" },
    { value: "msdax", label: "msdax" },
    { value: "objective-c", label: "objective-c" },
    { value: "pascal", label: "pascal" },
    { value: "perl", label: "perl" },
    { value: "pgsql", label: "pgsql" },
    { value: "php", label: "php" },
    { value: "plaintext", label: "plaintext" },
    { value: "postiats", label: "postiats" },
    { value: "powerquery", label: "powerquery" },
    { value: "powershell", label: "powershell" },
    { value: "pug", label: "pug" },
    { value: "python", label: "python" },
    { value: "r", label: "r" },
    { value: "razor", label: "razor" },
    { value: "redis", label: "redis" },
    { value: "redshift", label: "redshift" },
    { value: "ruby", label: "ruby" },
    { value: "rust", label: "rust" },
    { value: "sb", label: "sb" },
    { value: "scheme", label: "scheme" },
    { value: "scss", label: "scss" },
    { value: "shell", label: "shell" },
    { value: "sol", label: "sol" },
    { value: "st", label: "st" },
    { value: "swift", label: "swift" },
    { value: "tcl", label: "tcl" },
    { value: "vb", label: "vb" },
];


const themeOptions = {
    
    light: [
        {
            value: "vs", label: "浅色(Visual Studio)", suffix: "浅色主题",isDivision:true,type:"light"
        },
        {
            value: "hc-light", label: "GitHub Light", suffix: "",type:"light"
        },
    ],
    dark: [
        {
            value: "vs-dark", label: "深色(Visual Studio)", suffix: "深色主题",isDivision:true,type:"dark"
        },

        {
            value: "hc-black", label: "GitHub Dark", suffix: "",type:"dark"
        },
    ]
}


// {
//   value: "vs-light-plus",
// },
// { value: "default-dark", label: "Dark+ (default dark)" },

//整体配置
export const codeEditConfig = {
    theme: themeOptions,
    language: languageList
}