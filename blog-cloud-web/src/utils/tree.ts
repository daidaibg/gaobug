export function seachTreeData(data:any, id:any,options={id:"id",children:"children"}):any {
	for (let i = 0; i < data.length; i++) {
		let item = data[i];
		if (item[options.id]=== id) {
			return item;
		} else {
			// item.children 不等于 undefined && item.children.length 大于 0 时
			if (item[options.children] && item[options.children].length > 0) {
				let res = seachTreeData(item.children, id);
				// 当 res = undefined 时不会进入判断中
                // 也就是不会return
				if (res) return res;
			}
		}
	}
    return null
};
