/**
 * @class Canvas DrawBoard
 * @author Jason <brotherqian@163.com>
 * @version 1.1
 * @singleton
 * @fileoverview Build Date:2014/04/12 The application is valid in a browser
 *               that supports HTML5 canvas object. A default canvas tag will
 *               render to the div you have appointed You can use this
 *               application like this: DrawBoard.renderDrawer('myHandWrite',{
 *               penColor:'#FF0000', penWidth:'1px' }); If you need a text
 *               background,this will help: DrawBoard.formatText('Sign here');
 *               See {@link DrawBoard.renderDrawer} for more details
 */
var DrawBoard/* DrawBoard对象 */= {};
DrawBoard.Id = "";
DrawBoard.penColor = "#FF0000";
DrawBoard.penWidth = 0;
DrawBoard.mouseX/* 鼠标位置X */= 0;
DrawBoard.mouseY/* 鼠标位置Y */= 0;
DrawBoard.isMouseDown/**/= false;
DrawBoard.isMouseMove/**/= false;
DrawBoard.mousePath/**/= new Array();
DrawBoard.mousePosition/**/= {
	x : 0,
	y : 0
};
DrawBoard.Events/* 事件列表 */= {
	onMouseMove/* 鼠标移动 */: function(e) {
		var me = DrawBoard;
		var ctx = me.Context;
		var canvas = me.Canvas;
		if (me.isMouseDown) {
			if (me.isMouseMove) {
				ctx.moveTo(me.mouseX, me.mouseY);
				me.isMouseMove = false;
			}
			me.mouseX = e.layerX;
			me.mouseY = e.layerY;
			me.mousePosition = {
				"x" : me.mouseX,
				"y" : me.mouseY
			};
			me.mousePath.push(me.mousePosition);
			ctx.lineTo(me.mouseX, me.mouseY);
			ctx.stroke();
		}
	},
	onMouseDown/* 鼠标按下 */: function(e) {
		var me = DrawBoard;
		var ctx = me.Context;
		var canvas = me.Canvas;
		canvas.style.borderColor = "#808080";
		var rect = canvas.getBoundingClientRect();
		me.isMouseDown = true;
		me.isMouseMove = true;
		// 计算鼠标位置 仅适用fireFox
		me.mouseX = e.layerX;
		me.mouseY = e.layerY;
		if (me.mousePath.length == 0) {
			me.redo();
		}
	},
	onMouseOut/* 鼠标移出 */: function(e) {
		var me = DrawBoard;
		me.isMouseDown = false;
	},
	onMouseUp/* 鼠标放开 */: function(e) {
		var me = DrawBoard;
		var ctx = me.Context;
		var canvas = me.Canvas;
		canvas.style.borderColor = "#C0C0C0";
		me.isMouseDown = false;
	}
};

/**
 * Render a child element to the node that has a id attribute which values
 * 
 * @param id
 * @param {String}
 *            id the name that canvas will render as it's child element
 * @param {Object}
 *            option the pen color
 * @argument {String} penColor rgb(0,0,0) or #000000 color
 * @argument {String} penWidth pen width in pixel
 * @argument {Int} width canvas board width
 * @argument {Int} height canvas board height
 */

DrawBoard.renderDrawer/* 渲染绘图对象 */= function(id, option) {
	var me = DrawBoard;
	var p = option;
	me.Id = id;
	me.penColor = p.penColor || me.penColor;
	me.penWidth = p.penWidth;
	var objContainer = document.getElementById(id);
	// 初始化Canvas元素
	var objCanvas = document.getElementById('DrawBoard')
			|| document.createElement('canvas');
	with (objCanvas) {
		width = p.width || objContainer.offsetWidth || 720;
		height = p.height || objContainer.offsetHeight || 400;
		id = 'DrawBoard';
		style.margin = 'auto';
		style.border = "1px solid #C0C0C0";
		style.borderRadius = "4px";
		style.position = "absolute";
		// style.cursor="url(../images/user/pencil.png),auto";
	}
	objContainer.appendChild(objCanvas);
	var ctx = objCanvas.getContext('2d');
	// 初始化canvas上下文对象
	with (ctx) {
		lineWidth/* 线宽 */= me.penWidth;
		strokeStyle/* 描绘颜色 */= me.penColor;
		fillStyle/* 填充颜色 */= me.penColor;
		lineCap/* 线头样式 */= "round";
		lineJoin/* 转角样式 */= "round";
		miterLimit/* 折角锐利度 */= 1;
		shadowColor = '#FC0000';
		shadowBlur = 1;
	}
	// 为Canvas元素附加事件
	objCanvas.addEventListener("mousemove", me.Events.onMouseMove, false);
	objCanvas.addEventListener("mouseout", me.Events.onMouseOut, false);
	objCanvas.addEventListener("mouseup", me.Events.onMouseUp, false);
	objCanvas.addEventListener("mousedown", me.Events.onMouseDown, false);
	objCanvas.addEventListener("contextmenu", DrawBoard.redo, false);
	// 赋值对象
	me.Canvas = me.Canvas || objCanvas;
	me.Context = me.Context || ctx;
}
DrawBoard.redo/* 清空所有窗体 */= function() {
	var me = DrawBoard;
	var canvas = me.Canvas;
	var ctx = me.Context;
	ctx.clearRect(0, 0, canvas.width, canvas.height);
	canvas.width = canvas.width;
	// 置空鼠标路径数组
	me.mousePath.splice(0, me.mousePath.length);
	me.renderDrawer(me.Id, me.penColor, me.penWidth);
};
DrawBoard.formatText/* 绘制一个水印 */= function(text) {
	var me = DrawBoard;
	var canvas = me.Canvas;
	var ctx = me.Context;
	ctx.font/* 字体 */= "100px 宋体";
	ctx.fillText(text, 100, 200);
	ctx.stroke();
};
// 取png数据类型
DrawBoard.getImage = function() {
	return DrawBoard.Context != null ? DrawBoard.Canvas.toDataURL('image/png')
			: "";
}
DrawBoard.download = function() {

}

/**
 * POST you data to the server
 * 
 * @param option
 * @argument {String} method 'POST' or 'GET' is valid
 * @argument {String} url the address to post to
 * @argument {String} isDirect if true to use a asynchronous method,or false to
 *           not use
 * @argument {Function} onSuccess the method to be called upon the success of
 *           the request
 */
DrawBoard.post = function(option) {
	var p = option;
	var request = new XMLHttpRequest()
			|| window.ActiveXObject("Microsoft.XMLHttp");
	request.onreadystatechange = function() {
		if (request.readyState == 4) {
			if (request.status == 200) {
				eval(option.onSuccess);
			} else {

			}
		} else {

		}
	};
	request.open(p.method || 'POST', p.url, p.isDirect || true);
	request.send(p.params)
}
