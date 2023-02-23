<script lang="ts" setup>
// https://blog.csdn.net/weixin_43909743/article/details/127633552
import JsonWorker from "monaco-editor/esm/vs/language/json/json.worker?worker";
import CssWorker from "monaco-editor/esm/vs/language/css/css.worker?worker";
import HtmlWorker from "monaco-editor/esm/vs/language/html/html.worker?worker";
import TsWorker from "monaco-editor/esm/vs/language/typescript/ts.worker?worker";
import EditorWorker from "monaco-editor/esm/vs/editor/editor.worker?worker";
import * as monaco from "monaco-editor";
import { nextTick, ref, onBeforeUnmount, onMounted, watch } from "vue";
import { editorProps } from "./monaco-editor-type";
import { defaultOption } from "./config";

import { useRoute } from "vue-router";
import { colorToHex } from "@/utils/color";


const emits = defineEmits<{
  (event: "update:modelValue", e: any): void;
  (event: "change", e: any): void;
  (event: "editor-mounted", e: any): void;
  (event: "save", e: any): void;
}>();

const props = defineProps(editorProps);

let editor: monaco.editor.IStandaloneCodeEditor;

(self as any).MonacoEnvironment = {
  getWorker(_: string, label: string) {
    if (label === "json") {
      return new JsonWorker();
    }
    if (["css", "scss", "less"].includes(label)) {
      return new CssWorker();
    }
    if (["html", "handlebars", "razor"].includes(label)) {
      return new HtmlWorker();
    }
    if (["typescript", "javascript"].includes(label)) {
      return new TsWorker();
    }
    return new EditorWorker();
  },
};

const editorInit = () => {
  nextTick(() => {
    monaco.languages.typescript.javascriptDefaults.setDiagnosticsOptions({
      noSemanticValidation: true, //禁用语义验证
      noSyntaxValidation: false,
    });
    monaco.languages.typescript.javascriptDefaults.setCompilerOptions({
      target: monaco.languages.typescript.ScriptTarget.ES2020, //启用语法验证
      allowNonTsExtensions: true,
    });

    editor = monaco.editor.create(
      document.getElementById("codeEditBox") as HTMLElement,
      {
        value: props.modelValue,
        language: props.language,
        readOnly: props.readOnly,
        theme: props.theme,
        ...{ ...defaultOption, ...props.options },
      }
    );
    addTheme();
    addCommand();
    editor.onDidChangeModelContent(() => {
      const value = editor.getValue(); // 给父组件实时返回最新文本
      emits("update:modelValue", value);
      emits("change", value);
    });
    emits("editor-mounted", editor);
  });
};

//新增快捷键
const addCommand = () => {
  //保存快捷建ctrl+ s
  const saveCommand = editor.createContextKey("saveCommand", true);
  editor.addCommand(
    monaco.KeyMod.chord(monaco.KeyMod.CtrlCmd | monaco.KeyCode.KeyS, 0),
    function () {
      emits("save", saveCommand);
    },
    "saveCommand"
  );
};

//暂时不适用
function addTheme() {
  return;
  import("./theme/dark_plus.json").then((res) => {
    console.log(res.default);
    let tokenRules: any = [];
    res.default.tokenColors.forEach((value: any): void => {
      const scopeValue = value.scope || [];
      const scopes = Array.isArray(scopeValue)
        ? scopeValue
        : scopeValue.split(",");
      scopes.forEach((scope: string): void => {
        let tokenRulesItme: any = {};
        tokenRulesItme = {
          token: scope,
          foreground: colorToHex(value.settings.foreground),
        };
        if (value.settings.background) {
          tokenRulesItme.background = colorToHex(value.settings.background);
        }
        if (value.settings.fontStyle) {
          tokenRulesItme.background = value.settings.fontStyle;
        }
        tokenRules.push(tokenRulesItme);
      });
    });
    monaco.editor.defineTheme("default-dark", {
      base: "vs-dark",
      inherit: true,
      rules: tokenRules,
      colors: {
        ...{
          "[editorBackground]": "#1E1E1E",
          "[editorForeground]": "#D4D4D4",
          "[editorInactiveSelection]": "#3A3D41",
          "[editorIndentGuides]": "#404040",
          "[editorActiveIndentGuides]": "#707070",
          "[editorSelectionHighlight]": "#ADD6FF26",
        },
        ...res.default.semanticTokenColors,
      },
    });
  });
  // monaco.editor.defineTheme('default-dark', {
  //     base: 'vs-dark',
  //     inherit: true,
  //     rules:[],
  //     colors: [] as any
  // })
}
onBeforeUnmount(() => {
  editor.dispose();
});
onMounted(() => {
  editorInit();
});

watch(
  () => props.modelValue,
  (newValue) => {
    if (editor) {
      const value = editor.getValue();
      if (newValue !== value) {
        editor.setValue(newValue);
      }
    }
  }
);

watch(
  () => props.options,
  (newValue) => {
    editor.updateOptions(newValue);
  },
  { deep: true }
);

//是否只读
watch(
  () => props.readOnly,
  () => {
    // console.log("props.readOnly", props.readOnly);
    editor.updateOptions({ readOnly: props.readOnly });
  },
  { deep: true }
);

//皮肤
watch(
  () => props.theme,
  (newval) => {
    console.log("props.theme", props.theme);
    monaco.editor.setTheme(newval);
    // editor.updateOptions({ theme: props.theme });
  }
);

//语言
watch(
  () => props.language,
  (newValue) => {
    changeLanguage(newValue);
  }
);

//切换语言
const changeLanguage = (language: string) => {
  monaco.editor.setModelLanguage(editor.getModel()!, language);
};

//设置一个确认按钮，点击时调用接口
/***
editor.setValue(newValue)
editor.getValue()
editor.onDidChangeModelContent((val)=>{ //监听值的变化  })
editor.getAction('editor.action.formatDocument').run()    //格式化代码
editor.dispose()    //销毁实例
editor.onDidChangeOptions　　//配置改变事件
editor.onDidChangeLanguage　　//语言改变事件
 */
</script>

<template>
  <div id="codeEditBox"></div>
</template>
