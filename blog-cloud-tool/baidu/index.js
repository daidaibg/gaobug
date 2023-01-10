var divTyping = document.getElementById('text')
let shubiaoDom  = document.querySelector(".shubiao")
var i = 0,
    timer = 0,
    str = '';

function typing() {
    if (i <= str.length) {
        divTyping.innerHTML = str.slice(0, i++) + '_'
        timer = setTimeout(typing, 200)
    }
    else {
        divTyping.innerHTML = str//结束打字,移除 _ 光标
        clearTimeout(timer)
        shubiaoDom.classList.add("shubiao_click")
        timer = setTimeout(() => {
            window.location.href = `https://www.baidu.com/s?wd=${str}`
            clearTimeout(timer)
        }, 1300);

    }
}
console.log(window.location.href);
//获取路径参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substring(1).match(reg);
    if (r != null) return decodeURIComponent(r[2]);
    return null;
}
str = getUrlParam('str')
if (str) {
    document.title=str
    typing();
} else {
    timer = setTimeout(() => {
        window.location.href = `https://www.baidu.com`
    }, 1000);
}




