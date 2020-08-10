<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>汇率展示</title>
</head>
<body style="height: 1000px; margin: 0">
<div align="center" style="margin-top: 5%">欧元人民币K线图</div>
	<div id="container" style="height: 90%; margin-top: 5%"></div>
	<script type="text/javascript"
		src="https://cdn.jsdelivr.net/npm/echarts/dist/echarts.min.js"></script>
	<script type="text/javascript"
		src="https://cdn.jsdelivr.net/npm/echarts-gl/dist/echarts-gl.min.js"></script>
	<script type="text/javascript"
		src="https://cdn.jsdelivr.net/npm/echarts-stat/dist/ecStat.min.js"></script>
	<script type="text/javascript"
		src="https://cdn.jsdelivr.net/npm/echarts/dist/extension/dataTool.min.js"></script>
	<script type="text/javascript"
		src="https://cdn.jsdelivr.net/npm/echarts/map/js/china.js"></script>
	<script type="text/javascript"
		src="https://cdn.jsdelivr.net/npm/echarts/map/js/world.js"></script>
	<script type="text/javascript"
		src="https://cdn.jsdelivr.net/npm/echarts/dist/extension/bmap.min.js"></script>
	<script type="text/javascript">
		var dom = document.getElementById("container");
		var myChart = echarts.init(dom);
		var app = {};
		option = null;
		var upColor = '#ec0000';
		var upBorderColor = '#8A0000';
		var downColor = '#00da3c';
		var downBorderColor = '#008F28';
		//datas=从hive当中，查询出来的数据
		var datas ='<%=request.getAttribute("list")%>';
		//JS语法，跟JAVA语法无关
		var obj = JSON.parse(datas);
		//alert(obj);
		
		//创建JS数组
		var mydata=new Array();
		
		for (var i = 0; i < obj.length; i++) {
			//解析，从hive当中查询出的数据
			var tempdata = new Array();
			tempdata[0]=obj[i].date;
			tempdata[1]=obj[i].open;
			tempdata[2]=obj[i].close;
			tempdata[3]=obj[i].lowest;
			tempdata[4]=obj[i].highest;
			//放到新数据当中，为了k线图
			mydata.push(tempdata);
		}
		//alert(mydata);
		
		
		// 数据意义：开盘(open)，收盘(close)，最低(lowest)，最高(highest)
		var data0 = splitData(mydata);

		function splitData(rawData) {
			var categoryData = [];
			var values = []
			for (var i = 0; i < rawData.length; i++) {
				categoryData.push(
		rawData[i].splice(0, 1)[0]);
				values.push(rawData[i])
			}
			return {
				categoryData : categoryData,
				values : values
			};
		}

		function calculateMA(dayCount) {
			var result = [];
			for (var i = 0, len = data0.values.length; i < len; i++) {
				if (i < dayCount) {
					result.push('-');
					continue;
				}
				var sum = 0;
				for (var j = 0; j < dayCount; j++) {
					sum += data0.values[i - j][1];
				}
				result.push(sum / dayCount);
			}
			return result;
		}

		option = {
			title : {
				text : '欧元人民币',
				left : 0
			},
			tooltip : {
				trigger : 'axis',
				axisPointer : {
					type : 'cross'
				}
			},
			legend : {
				data : [ '日K', 'MA5', 'MA10', 'MA20', 'MA30' ]
			},
			grid : {
				left : '10%',
				right : '10%',
				bottom : '15%'
			},
			xAxis : {
				type : 'category',
				data : data0.categoryData,
				scale : true,
				boundaryGap : false,
				axisLine : {
					onZero : false
				},
				splitLine : {
					show : false
				},
				splitNumber : 20,
				min : 'dataMin',
				max : 'dataMax'
			},
			yAxis : {
				scale : true,
				splitArea : {
					show : true
				}
			},
			dataZoom : [ {
				type : 'inside',
				start : 50,
				end : 100
			}, {
				show : true,
				type : 'slider',
				top : '90%',
				start : 50,
				end : 100
			} ],
			series : [
					{
						name : '日K',
						type : 'candlestick',
						data : data0.values,
						itemStyle : {
							color : upColor,
							color0 : downColor,
							borderColor : upBorderColor,
							borderColor0 : downBorderColor
						},
						markPoint : {
							label : {
								normal : {
									formatter : function(param) {
										return param != null ? Math
												.round(param.value) : '';
									}
								}
							},
							data : [ {
								name : 'XX标点',
								coord : [ '2020/7/31', 2300 ],
								value : 2300,
								itemStyle : {
									color : 'rgb(41,60,85)'
								}
							}, {
								name : 'highest value',
								type : 'max',
								valueDim : 'highest'
							}, {
								name : 'lowest value',
								type : 'min',
								valueDim : 'lowest'
							}, {
								name : 'average value on close',
								type : 'average',
								valueDim : 'close'
							} ],
							tooltip : {
								formatter : function(param) {
									return param.name + '<br>'
											+ (param.data.coord || '');
								}
							}
						},
						markLine : {
							symbol : [ 'none', 'none' ],
							data : [ [ {
								name : 'from lowest to highest',
								type : 'min',
								valueDim : 'lowest',
								symbol : 'circle',
								symbolSize : 10,
								label : {
									show : false
								},
								emphasis : {
									label : {
										show : false
									}
								}
							}, {
								type : 'max',
								valueDim : 'highest',
								symbol : 'circle',
								symbolSize : 10,
								label : {
									show : false
								},
								emphasis : {
									label : {
										show : false
									}
								}
							} ], {
								name : 'min line on close',
								type : 'min',
								valueDim : 'close'
							}, {
								name : 'max line on close',
								type : 'max',
								valueDim : 'close'
							} ]
						}
					}, {
						name : 'MA5',
						type : 'line',
						data : calculateMA(5),
						smooth : true,
						lineStyle : {
							opacity : 0.5
						}
					}, {
						name : 'MA10',
						type : 'line',
						data : calculateMA(10),
						smooth : true,
						lineStyle : {
							opacity : 0.5
						}
					}, {
						name : 'MA20',
						type : 'line',
						data : calculateMA(20),
						smooth : true,
						lineStyle : {
							opacity : 0.5
						}
					}, {
						name : 'MA30',
						type : 'line',
						data : calculateMA(30),
						smooth : true,
						lineStyle : {
							opacity : 0.5
						}
					}, ]
		};
		;
		if (option && typeof option === "object") {
			myChart.setOption(option, true);
		}
	</script>
</body>
</html>