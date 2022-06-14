/*
 * @Author: daidai
 * @Date: 2021-12-27 14:40:02
 * @LastEditors: daidai
 * @LastEditTime: 2021-12-30 17:10:14
 * @FilePath: \web-pc\src\views\statistics\surroundings-statistics\echarts-option.js
 */
import { colorS } from "yhht-ui/src/util";
export const colors = [
    '#F39800',
    '#00F345',
    '#0089F3',
    '#00C8F3',
    '#A000F3',
    '#F7EA58',
    '#0089F3',
    '#F39800',
    '#00F345',
    '#00C8F3',
]
/**
 * 环境历史数据配置
 */
export const HjHistory = (chart, xData, yData,name,Company) => {
    let option = {
        xAxis: {
            type: 'category',
            data: xData,
            boundaryGap: false,
            // showMaxLable:true,//显示最大值
            // showMinLable:true //显示最小值
        },
        yAxis: {
            type: 'value',
            name: Company||"", //坐标名
            nameTextStyle:{
                padding:[0,24,0,0]
             }
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'line',
                label: {
                    backgroundColor: '#6a7985'
                },
            },
            formatter:function (params) {
                var relVal = params[0].name
                for (var i = 0, l = params.length; i < l; i++) {
                  relVal += '<br/>' + params[i].marker + params[i].seriesName + '   ' + `  <span style="font-weight:900">${params[i].value}</span>`  +`<span style="font-size:12px;color:#969797"> ${ (Company||"")}</span> `
                } 
                return relVal
              }
        },
        grid: {
            x: 50,
            y: 60,
            x2: 40,
            y2: 40,
            borderWidth: 10
        },
        series: [
            {
                name: name,
                data: yData,
                type: 'line',
                smooth: true,
                symbol: yData.length==1? 'circle':'none',
                itemStyle: {
                    color: '#1890FF',
                    shadowColor: '#1890FF',
                    shadowBlur: 5,
                },
                lineStyle: {
                    shadowColor: "rgba(24,144,255,1)",
                    shadowOffsetX: 0,
                    shadowOffsetY: 0,
                    opacity: 1,
                    shadowBlur: 8,
                    type: "solid",
                    width: 2
                },
                markLine: {
                    data: [
                        { type: 'average', name: '平均值' },
                    ]
                },
                // areaStyle: {
                //     opacity: 0.6,
                //     color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                //         offset: 0,
                //         color: 'rgba(128, 255, 165)'
                //     }, {
                //         offset: 1,
                //         color: 'rgba(1, 191, 236)'
                //     }])
                // },
                markPoint: {
                    // symbolSize:[20,10],
                    data: [
                        { type: 'max', name: '最大值' },
                        { type: 'min', name: '最小值' },
                    ],
                    itemStyle: {
                        // symbolSize:[20,10],

                        color: {
                            type: 'linear',
                            x: 0,
                            y: 0,
                            x2: 0,
                            y2: 1,
                            colorStops: [{
                                offset: 0, color: 'rgba(0,132,255)' // 0% 处的颜色
                            }, {
                                offset: 1, color: 'rgba(134,194,248)' // 100% 处的颜色
                            }],
                            global: false // 缺省为 false
                        }
                    },
                    label: {
                        color: "#FFF"
                    }
                },

                label: {
                    show: true,
                    position: "top",
                    color: "#FFF",
                },
            }
        ]
    };
    chart && chart.setOption(option);
}




/**
 * 终端环境历史数据配置
 * 
 */


export const ZdHistoryChartOp = (chart, xData, yData,name, index,Company) => {
    // let series = []
    // for (let index = 0; index < yData.length; index++) {
    //     const element = yData[index];
    //     series.push({
    //         name: '终端' + (index + 1),
    //         data: element,
    //         type: 'line',
    //         smooth: true,
    //         symbol: 'none', //这句就是去掉点的
    //         itemStyle: {
    //             color: colors[index],
    //             shadowColor: colors[index],
    //             shadowBlur: 5,

    //         },
    //         lineStyle: {
    //             shadowColor: colors[index],
    //             shadowOffsetX: 0,
    //             shadowOffsetY: 0,
    //             opacity: 1,
    //             shadowBlur: 8,
    //             type: "solid",
    //             width: 2
    //         },
    //     })
    // }
    let option = {
        // title: {
        //     text: "终端环境参数曲线"
        // },
        xAxis: {
            type: 'category',
            data: xData,
            boundaryGap: false,
            axisLabel: {
                rotate: yData.length>3? 40:0
             }

        },
        yAxis: {
            type: 'value',
            name: Company ||"", //坐标名
            nameTextStyle:{
                padding:[0,20,0,0]
             },
        

        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'line',
                label: {
                    backgroundColor: '#6a7985'
                }
            }
        },
        // legend: {
        //     top: "8",
        // },
        grid: {
            x: 50,
            y: 80,
            x2: 40,
            y2: 40,
            borderWidth: 10
        },
        series: [
            {
                name: name,
                data: yData,
                type: 'line',
                smooth: true,
                symbol: yData.length==1? 'circle':'none', //这句就是去掉点的
                itemStyle: {
                    color: colors[index],
                    shadowColor: colors[index],
                    shadowBlur: 5,

                },
                lineStyle: {
                    shadowColor: colors[index],
                    shadowOffsetX: 0,
                    shadowOffsetY: 0,
                    opacity: 1,
                    shadowBlur: 8,
                    type: "solid",
                    width: 2
                },
            }
        ]
    };
    chart && chart.setOption(option);
}