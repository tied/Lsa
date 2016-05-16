// Пространство имен справочников БК
var CfldGrid = CfldGrid || {};
// Константы
CfldGrid.Constant = {
		arCls : [
			    
			    ],		
		formDataType : "formDataType",	
		multiList : "MultiList",		
		statusArchive : 2,
		statusActual : 1,
		filterStatusShowRecord: "showArchiveRecords",
		filterStatusHideRecord: "hideArchiveRecords",
		curClsTblOper: "",
		tblManyEnding: "s",
		fldId: "id",
		fldName: "name",
		fldFullName: "fullName",		
		fldExpertKey: "expertKey",
		fldIdIndex: 0,
		fldStatus: "status",
		fldContainsCls: "cls",
		fldContainsStrList: "strlist",
		fldContainsMulti: "multi",
		fldContainsSeparator: "$*$",
		formName : "formName",
		objBaseBegin: "tblEntity",
		operInsert: "insert",
		operUpdate: "update",
		operArchive: "archive",
		restPartUrl: '',
		arClsHardcode: [],
		filterExprFld: "filter",
		exprYes: "YES",
		exprNo: "NO",
		visibleFld: "visible",
		hide: 0,
		show: 1		
}
// Переменные
CfldGrid.Vars = {
	arTbl : 
		[
	          
	    ],
	    restBaseUrl : "",
	    filterStatusCurCl: "showArchiveRecords"
	}

CfldGrid.Util = {};

CfldGrid.Base = {};

// Преобразование ProperCase только для первой буквы
CfldGrid.Util.toProperCase = function (s)
{
	var str = s.substr(0, 1).toUpperCase()+s.substr(1);
    return str;
}

// Get params from Url
CfldGrid.Util.getUrlVars = function (){
	var vars = {};
	var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(m,key,value) {
	vars[key] = value;
	});
	return vars;
}

// Нахождение объекта массива по имени свойства и его значению
CfldGrid.Util.findElOfArByProp = function(ar, propName, propValue) {
	var objFnd = null;
	for (var i = 0 ; i < ar.length ; i++) {
		if (ar[i][propName] == propValue) {
			objFnd = ar[i];
			break;
		}
	}
	return objFnd;
}

//Поиск значения свойства объекта в массиве по заданному свойству
CfldGrid.Util.findPropOfArByProp = function(ar, srcVal, srcPropName, trgPropName) {
	var trgVal = null;
	for (var i = 0; i < ar.length; i++) {
		if (ar[i][srcPropName] == srcVal) {
			trgVal = ar[i][trgPropName];
		}
	}
	return trgVal;
}

// Получение объекта из заданной таблицы с заданым свойством и значением
CfldGrid.Base.getObj = function (tblName, propName, propValue) {
	var obj = {};
	var arTbl = CfldGrid.Vars.arTbl;
	var data=[];
	for (var i = 0 ; i < arTbl.length ; i++) { 
		if (arTbl[i].name == tblName) {
			data = CfldGrid[CfldGrid.Constant.objBaseBegin + CfldGrid.Util.toProperCase(tblName)].data;
			obj = CfldGrid.Util.findElOfArByProp(data, propName, propValue);
			break;
		}	
	}
	return obj;
}

// Получение массива из заданной таблицы с заданым свойством и значениями
CfldGrid.Base.getArray = function (tblName, propName, propValues) {
	var arObj = [];
	var arTbl = CfldGrid.Vars.arTbl;
	var data=[];
	for (var i = 0 ; i < arTbl.length ; i++) { 
		if (arTbl[i].name == tblName) {
			data = CfldGrid[CfldGrid.Constant.objBaseBegin + CfldGrid.Util.toProperCase(tblName)].data;
			for (var j = 0; j < propValues.length; j++) {
				arObj.push(CfldGrid.Util.findElOfArByProp(data, propName, propValues[j]));
			}
			break;
		}	
	}
	return arObj;
}

CfldGrid.Util.bind = function (fn, scope, args, appendArgs) {
    var method = fn,
        applyArgs;

    return function() {
        var callArgs = args || arguments;

        if (appendArgs === true) {
            callArgs = Array.prototype.slice.call(arguments, 0);
            callArgs = callArgs.concat(args);
        }
        else if (typeof appendArgs == 'number') {
            callArgs = Array.prototype.slice.call(arguments, 0); // copy arguments first
            applyArgs = [appendArgs, 0].concat(args); // create method call params
            Array.prototype.splice.apply(callArgs, applyArgs); // splice them in
        }

        return method.apply(scope || window, callArgs);
    };
}


// Базовая таблица. Конструктор
CfldGrid.TblBase = function(tblName) {
	this.tblName = tblName;
	this.selectorTbl = 'tbl-'+tblName; 
	this.selectorTblTbody='tbody-'+tblName;		
	this.data = [];
	this.restUrl = "";
	this.curClsTblRow = "";
	
	this.curClsTblOper = "";
	var ar = CfldGrid.Vars.arTbl;
		ar.sort(function(a, b) {
		    return parseInt(a.order) - parseInt(b.order);
		});
	this.curStrArMeta = CfldGrid.Util.findElOfArByProp(ar, "name", this.tblName);	
	this.id = 0;
	this.name = "";
	this.desc = "";
	this.status = "";	
	this.filterOuter = "";
	this.arFlt = "";
}

//Получение значения элемента массива данных таблицы по имени таблицы, ИД и имени поля
CfldGrid.TblBase.prototype.getArValByIdFldNm = function (tblNameForeign, fldSrc, flsSrcVal, fldTrg) {
	var ar = CfldGrid[tblNameForeign].data;
	var rowHash;
	for (var i = 0 ; i < ar.length ; i++) {
		rowHash = ar[i];
		if (rowHash[fldSrc] == flsSrcVal) {
			break;
		}
	}
	return rowHash[fldTrg];
}

// Базовая таблица. Чистка полей формы
CfldGrid.TblBase.prototype.cleanDialog = function () {
	var arFld = this.curStrArMeta.fields;
	for (var i = 0 ; i < arFld.length ; i++) { 
		if (arFld[i][CfldGrid.Constant.visibleFld] == CfldGrid.Constant.show) {
			var el = document.getElementById(arFld[i][CfldGrid.Constant.formName]);
			if (arFld[i].formDataType == "String") {
				el.value = "";
			}
			if (arFld[i].formDataType == "int") {
				el.value = 1;				
				el.checked = false;
			}		
			if (arFld[i].formDataType == "List") {
				for(j = el.options.length - 1 ; j >= 0 ; j--)
				{
					el.remove(j);
				}	
				el.value = "";
			}			
		}		
	}
}

// Базовая таблица. Установка полей формы из объекта
CfldGrid.TblBase.prototype.setFormFields = function (data) {
	var arFld = this.curStrArMeta.fields;
	for (var i = 0 ; i < arFld.length ; i++) { 
		if (arFld[i][CfldGrid.Constant.visibleFld] == CfldGrid.Constant.show) {	
			var el = document.getElementById(arFld[i][CfldGrid.Constant.formName]);
			if (arFld[i][CfldGrid.Constant.fldId] == CfldGrid.Constant.fldStatus) {
				el.checked = false;
				el.checked = data[arFld[i][CfldGrid.Constant.fldId]]==CfldGrid.Constant.statusArchive ? true : false;
				el.value = el.checked==true ? CfldGrid.Constant.statusArchive : CfldGrid.Constant.statusActual;
				continue;
			}	
			if (arFld[i][CfldGrid.Constant.fldId].indexOf(CfldGrid.Constant.fldContainsCls) > -1) {
				for(j = el.options.length - 1 ; j >= 0 ; j--)
				{
					if (el.options[j].value == data[arFld[i][CfldGrid.Constant.fldId]][CfldGrid.Constant.fldName]) {
						el.options[j].selected = "true";
						break;
					}
				}					
				continue;
			}	
			if (arFld[i][CfldGrid.Constant.fldId].indexOf(CfldGrid.Constant.fldContainsStrList) > -1) {				
				for(j = el.options.length - 1 ; j >= 0 ; j--)
				{
					if (el.options[j].value == data[arFld[i][CfldGrid.Constant.fldId]]) {
						el.options[j].selected = "true";
						break;
					}
				}					
				continue;
			}				
			if (arFld[i][CfldGrid.Constant.formDataType] == CfldGrid.Constant.multiList) {
				continue;
			}
			if (data[arFld[i][CfldGrid.Constant.fldId]] == undefined || data[arFld[i][CfldGrid.Constant.fldId]] == "") {
				el.value = "";	
			}
			else {
				el.value = data[arFld[i][CfldGrid.Constant.fldId]];			
			}			
		}
	}
}

//Базовая таблица. Установка полей таблицы из объекта
CfldGrid.TblBase.prototype.setObj = function (data) {
	var arFld = this.curStrArMeta.fields;
	for (var i = 0 ; i < arFld.length ; i++) { 
		if (arFld[i][CfldGrid.Constant.fldId].indexOf(CfldGrid.Constant.fldContainsCls) > -1) {
		    var tblNameForeign = arFld[i][CfldGrid.Constant.fldId].substr(CfldGrid.Constant.fldContainsCls.length);
		    tblNameForeign = tblNameForeign.substr(0, 1).toLowerCase()+tblNameForeign.substr(1);
		    var val = data[arFld[i][CfldGrid.Constant.fldId]][CfldGrid.Constant.fldId];
		    this[arFld[i][CfldGrid.Constant.fldId]] = CfldGrid.Base.getObj(tblNameForeign, CfldGrid.Constant.fldId, val);
		}
		else {
			if (arFld[i][CfldGrid.Constant.formDataType] == CfldGrid.Constant.multiList) {
			    var tblNameForeign = arFld[i][CfldGrid.Constant.fldId].substr(CfldGrid.Constant.fldContainsMulti.length);
			    tblNameForeign = tblNameForeign.substr(0, 1).toLowerCase()+tblNameForeign.substr(1);
			    var vals = [];
			    var arObj = data[arFld[i][CfldGrid.Constant.fldId]];
			    for (var j = 0; j < arObj.length; j++) {
			    	if (arObj[j] != null) {
						var val = arObj[j][CfldGrid.Constant.fldId];	
						vals.push(val);						
			    	}
				}
			    if (vals.length != 0) {
				    this[arFld[i][CfldGrid.Constant.fldId]] = CfldGrid.Base.getArray(tblNameForeign, CfldGrid.Constant.fldId, vals);			    	
			    }
			}
			else {
				if (arFld[i][CfldGrid.Constant.fldId].indexOf(CfldGrid.Constant.fldContainsStrList) > -1) {
					this[arFld[i][CfldGrid.Constant.fldId]] = data[arFld[i][CfldGrid.Constant.fldId]];
				}
				else
				{
					this[arFld[i][CfldGrid.Constant.fldId]] = data[arFld[i][CfldGrid.Constant.fldId]];				
				}
			}			
		}
	}
}



//Базовая таблица. Установка полей таблицы из полей формы
CfldGrid.TblBase.prototype.setTblDataFrForm = function () {
	var arFld = this.curStrArMeta.fields;
	arFld.sort(function(a, b) {
	    return parseInt(a.order) - parseInt(b.order);
	});			
	for (var i = 0 ; i < arFld.length ; i++) { 
		if (arFld[i][CfldGrid.Constant.visibleFld] == CfldGrid.Constant.show) {	
			var el = document.getElementById(arFld[i][CfldGrid.Constant.formName]);
			if (arFld[i][CfldGrid.Constant.fldId] == CfldGrid.Constant.fldStatus) {
				this[arFld[i][CfldGrid.Constant.fldId]] = el.checked==true ? CfldGrid.Constant.statusArchive : CfldGrid.Constant.statusActual;
				continue;
			}	
			if (arFld[i][CfldGrid.Constant.fldId].indexOf(CfldGrid.Constant.fldContainsCls) > -1) {
			    var tblNameForeign = arFld[i][CfldGrid.Constant.fldId].substr(CfldGrid.Constant.fldContainsCls.length);
			    tblNameForeign = tblNameForeign.substr(0, 1).toLowerCase()+tblNameForeign.substr(1);
				var valName = el.options[el.selectedIndex].value;
			    this[arFld[i][CfldGrid.Constant.fldId]] = CfldGrid.Base.getObj(tblNameForeign, CfldGrid.Constant.fldName, valName);
				continue;
			}		
			if (arFld[i][CfldGrid.Constant.fldId].indexOf(CfldGrid.Constant.fldContainsStrList) > -1) {
			    this[arFld[i][CfldGrid.Constant.fldId]] = el.value;	
				continue;
			}				
			if (arFld[i][CfldGrid.Constant.formDataType] == CfldGrid.Constant.multiList) {
			    var tblNameForeign = arFld[i][CfldGrid.Constant.fldId].substr(CfldGrid.Constant.fldContainsMulti.length);
			    tblNameForeign = tblNameForeign.substr(0, 1).toLowerCase()+tblNameForeign.substr(1);
			    var valNames = [];
			    for (var j = 0; j < el.selectedOptions.length; j++) {
					var val = el.selectedOptions[j].value;
					valNames.push(val);
				}
		    	this[arFld[i][CfldGrid.Constant.fldId]] = CfldGrid.Base.getArray(tblNameForeign, arFld[i].multiSelectField, valNames);
				continue;
			}				
			this[arFld[i][CfldGrid.Constant.fldId]] = el.value;			
		}
	}	
}



//Базовая таблица. Установка Rest адреса
CfldGrid.TblBase.prototype.setRestUrl = function () {
	this.restUrl = CfldGrid.Vars.restBaseUrl + this.tblName + CfldGrid.Constant.tblManyEnding;
}

// Базовая таблица. Выдает объект формы
CfldGrid.TblBase.prototype.makeJSON = function () {
	var obj = new Object();
	var arFld = this.curStrArMeta.fields;
	for (var i = 0 ; i < arFld.length ; i++) { 	
		obj[arFld[i][CfldGrid.Constant.fldId]] = this[arFld[i][CfldGrid.Constant.fldId]];			
	}
	return obj;
}

// Базовая таблица. Действия перед показом диалога ввода/редактирования
CfldGrid.TblBase.prototype.beforeDialogShow = function () {
	this.formForeignTblNameRefill();
}

// Событие при выборе элемента из списка в Select'е. Переприсваивание подчиненных элементов дочернего списка
CfldGrid.Base.onChangeFormListEl = function(e, customParameter) {
	if (customParameter == undefined && typeof e == 'string') {
		var customParameter = e;
	}
	var obj1 = customParameter.split(";");
	var val = document.getElementById(obj1[0]).value;
	var tblNameForeign = obj1[1];
	var el = document.getElementById(obj1[3]);
	var fltObjSrc = obj1[2].split("=")[0].split(".");
	var fltObjTrg = obj1[2].split("=")[1].split(".");
	for(j = el.options.length - 1 ; j >= 0 ; j--)
	{
		el.remove(j);
	}
	//levelType.name=Level.levelType.name
	var arVal = CfldGrid[CfldGrid.Constant.objBaseBegin+tblNameForeign].data;
	var arValCur = null;
	for (var t = 0; t < arVal.length; t++) {
		if (arVal[t][CfldGrid.Constant.fldName] == val) {
			arValCur = arVal[t];
			for (var h = 0; h < fltObjSrc.length; h++) {
				arValCur = arValCur[fltObjSrc[h]];
			}
		}
	}	
	if (arValCur != null ) {
		arVal = CfldGrid[CfldGrid.Constant.objBaseBegin+fltObjTrg[0]].data;
		var arValCur2 = null;
		for (var q = 0; q < arVal.length; q++) {
			arValCur2 = arVal[q];
			for (var h = 1; h < fltObjTrg.length; h++) {
				arValCur2 = arValCur2[fltObjTrg[h]];
			}
			if (arValCur == arValCur2) {
				var option = document.createElement("option");
				option.value = arVal[q][CfldGrid.Constant.fldName];
				option.text = arVal[q][CfldGrid.Constant.fldName];
				el.appendChild(option);		
			}
		}
	}
	if (obj1[4] != undefined) {
		var elEnd = document.getElementById(obj1[4]);
		if (elEnd.onchange != null) {
			elEnd.onchange();
		}	
	}
	if (obj1[5] != undefined) {
		var elEnd2 = document.getElementById(obj1[5]);
		if (elEnd2.onchange != null) {
			elEnd2.onchange();
		}	
	}	
}

//Базовая таблица. Заполнение внешних dropdown'ов
CfldGrid.TblBase.prototype.formOuterFilterRefill = function () {
	// Заполнение фильтров внешних вкладок
	var arForm = this.formSelOuter;
	var ar = this.data;		
	if (arForm != "") {
		for (var i = 0; i < arForm.length; i++) {
			var el = document.getElementById(arForm[i]);	
			// Очистка dropdown'а
			for(j = el.options.length - 1 ; j >= 0 ; j--)
			{
				el.remove(j);
			}		
			// Заполнение dropdown'а
			for (var k = 0; k < ar.length; k++) {
				if (ar[k].status != CfldGrid.Constant.statusArchive) {
					var isAdd = true;
					if (isAdd) {
						var option = document.createElement("option");
						option.value = ar[k][CfldGrid.Constant.fldName];
						option.text = ar[k][CfldGrid.Constant.fldName];
						el.appendChild(option);									
					}		
				}
			}						
		}
	}	
}

//Базовая таблица. Перезаполнение внутренних dropdown'ов
CfldGrid.TblBase.prototype.formForeignTblNameRefill = function () {
	var arFld = this.curStrArMeta.fields;
	var parentId = -1;
	for (var i = 0 ; i < arFld.length ; i++) {
		if (arFld[i][CfldGrid.Constant.fldId].indexOf(CfldGrid.Constant.fldContainsCls) > -1) {
		    var tblNameForeign = arFld[i][CfldGrid.Constant.fldId].substr(CfldGrid.Constant.fldContainsCls.length);
		    var tblNameForeignLower = tblNameForeign.substr(0, 1).toLowerCase()+tblNameForeign.substr(1);
		    // Заполнение фильтров формы		    
			var el = document.getElementById(arFld[i][CfldGrid.Constant.formName]);	
			if (el != null) {
				// Очистка dropdown'а
				for(j = el.options.length - 1 ; j >= 0 ; j--)
				{
					el.remove(j);
				}		
				// Заполнение dropdown'а
				var ar = CfldGrid[CfldGrid.Constant.objBaseBegin+tblNameForeign].data;
				if (arFld[i].filter != "") {
					var flt = arFld[i].filter;
					var formSel = arFld[i][CfldGrid.Constant.fldId][CfldGrid.Constant.formName];
					el.onchange = CfldGrid.Util.bind(CfldGrid.Base.onChangeFormListEl, null, formSel+";"+tblNameForeign+";"+flt, true);	
				}				
				for (var k = 0; k < ar.length; k++) {
					if (ar[k].status != CfldGrid.Constant.statusArchive) {
						var isAdd = true;
						if (isAdd) {
							var option = document.createElement("option");
							option.value = ar[k][CfldGrid.Constant.fldName];
							option.text = ar[k][CfldGrid.Constant.fldName];
							el.appendChild(option);									
						}		
					}
				}
				if (this.curClsTblOper != CfldGrid.Constant.operInsert) {
					if (CfldGrid.Constant.arClsHardcode.indexOf(tblNameForeignLower) > - 1) {
						for(j1 = el.options.length - 1 ; j1 >= 0 ; j1--)
						{
							if (el.options[j1].value == tblNameForeignLower) {
								el.options[j1].selected = "true";
								break;
							}
						}					
					}				
				}
				if (this[arFld[i][CfldGrid.Constant.fldId]] !=undefined && CfldGrid.Constant.arClsHardcode.indexOf(tblNameForeignLower) == - 1) {
					for(j2 = el.options.length - 1 ; j2 >= 0 ; j2--)
					{
						if (el.options[j2].value == this[arFld[i][CfldGrid.Constant.fldId]][CfldGrid.Constant.fldName]) {
							el.options[j2].selected = "true";
							break;
						}
					}												
				}				
			}		
		}
		
		if (arFld[i][CfldGrid.Constant.fldId].indexOf(CfldGrid.Constant.fldContainsStrList) > -1) {
		    var tblNameForeign = arFld[i][CfldGrid.Constant.fldId].substr(CfldGrid.Constant.fldContainsStrList.length);
		    var tblNameForeignLower = tblNameForeign.substr(0, 1).toLowerCase()+tblNameForeign.substr(1);
		    // Заполнение фильтров формы		    
			var el = document.getElementById(arFld[i][CfldGrid.Constant.formName]);	
			if (el != null) {
				// Очистка dropdown'а
				for(j = el.options.length - 1 ; j >= 0 ; j--)
				{
					el.remove(j);
				}		
				// Заполнение dropdown'а
				var ar = CfldGrid[CfldGrid.Constant.objBaseBegin+tblNameForeign].data;
				if (arFld[i].filter != "") {
					var flt = arFld[i].filter;
					var formSel = arFld[i][CfldGrid.Constant.formName];
					el.onchange = CfldGrid.Util.bind(CfldGrid.Base.onChangeFormListEl, null, formSel+";"+tblNameForeign+";"+flt, true);	
				}	
				if (this.curClsTblOper == CfldGrid.Constant.operInsert) {
					var option = document.createElement("option");
					option.value = "";
					option.text = "";
					el.appendChild(option);	
				}						
				for (var k = 0; k < ar.length; k++) {
					var option = document.createElement("option");
					option.value = ar[k][CfldGrid.Constant.fldName];
					option.text = ar[k][CfldGrid.Constant.fldName];
					el.appendChild(option);										
				}
				if (this.curClsTblOper == CfldGrid.Constant.operInsert) {
					el.value = "";	
				}
				else {
					if (this[arFld[i][CfldGrid.Constant.fldId]][CfldGrid.Constant.fldName] != undefined && this[arFld[i][CfldGrid.Constant.fldId]][CfldGrid.Constant.fldName] != null) {
						for(j2 = el.options.length - 1 ; j2 >= 0 ; j2--)
						{
							if (el.options[j2].value == this[arFld[i][CfldGrid.Constant.fldId]][CfldGrid.Constant.fldName]) {
								el.options[j2].selected = "true";
								break;
							}
						}						
					}														
				}
			}		
		}		
		
		if (arFld[i][CfldGrid.Constant.formDataType] == CfldGrid.Constant.multiList) {
		    var tblNameForeign = arFld[i][CfldGrid.Constant.fldId].substr(CfldGrid.Constant.fldContainsMulti.length);
			var el = document.getElementById(arFld[i][CfldGrid.Constant.formName]);
			// Очистка dropdown'а
			for(j = el.options.length - 1 ; j >= 0 ; j--)
			{
				el.remove(j);
			}		
			// Заполнение dropdown'а
			var ar = CfldGrid[CfldGrid.Constant.objBaseBegin+tblNameForeign].data;
			for (var k = 0; k < ar.length; k++) {
				if (ar[k].status != CfldGrid.Constant.statusArchive) {
					var option = document.createElement("option");
					option.value = ar[k][arFld[i].multiSelectField];
					option.text = ar[k][arFld[i].multiSelectField];
					el.appendChild(option);				
				}
			}			
			var sel2Vals = [];
			if (this.curClsTblOper != CfldGrid.Constant.operInsert) {
				for(j2 = 0 ; j2 <= el.options.length - 1 ; j2++)
				{
					var a2 =  this[arFld[i][CfldGrid.Constant.fldId]];
					if (a2 !=undefined && a2 !=null) {
						for (var j3 = 0; j3 < a2.length; j3++) {
							if (el.options[j2].value == a2[j3][arFld[i].multiSelectField]) {
								sel2Vals.push(el.options[j2].value);
							}						
						}						
					}	
				}					
			}
			AJS.$("#"+arFld[i][CfldGrid.Constant.formName]).auiSelect2();
			AJS.$('#'+arFld[i][CfldGrid.Constant.formName]).select2('val', sel2Vals);		
		} 				
	}
	// Предварительная принудительная фильтрация
//	for (var i = 0 ; i < arFld.length ; i++) {
//		if (arFld[i][CfldGrid.Constant.fldId].indexOf(CfldGrid.Constant.fldContainsCls) > -1 && arFld[i].filter != "") {
//		    var tblNameForeign = arFld[i][CfldGrid.Constant.fldId].substr(CfldGrid.Constant.fldContainsCls.length);
//		    var tblNameForeignLower = tblNameForeign.substr(0, 1).toLowerCase()+tblNameForeign.substr(1);	    
//			var el = document.getElementById(arFld[i][CfldGrid.Constant.formName]);	
//			if (el != null) {
//				// Очистка dropdown'а
//				for(j = el.options.length - 1 ; j >= 0 ; j--)
//				{
//					el.remove(j);
//				}		
//				// Заполнение dropdown'а
//				el.onchange();			
//			}
//		}
//	}
}

//Базовая таблица. Формирование таблицы	HTML
CfldGrid.TblBase.prototype.buildHtmlTable = function () {	
	for (var i = 0 ; i < this.data.length ; i++) {
		if (this.data[i] != undefined) {
			this.setObj(this.data[i]);
			this.insertHtmlTable();		
		}
	}		
}

// Базовая таблица. Редактирование данных таблицы. Событие		
CfldGrid.TblBase.prototype.editEvent = function (cellId){
	this.curClsTblRow = cellId.parentNode;
	this.curClsTblOper = CfldGrid.Constant.operUpdate;	
	this[CfldGrid.Constant.fldId] = this.curClsTblRow.childNodes[CfldGrid.Constant.fldIdIndex].innerHTML;
	this.setObj(this.data[this.curClsTblRow.rowIndex - 1]);
	this.setFormFields(this.data[this.curClsTblRow.rowIndex - 1]);
	this.beforeDialogShow();
	this.setFormFields(this.data[this.curClsTblRow.rowIndex - 1]);
	AJS.dialog2("#insert-dialog-"+this.tblName).show();						
}		

// Базовая таблица. Удаление данных из таблицы. Событие	
CfldGrid.TblBase.prototype.deleteEvent = function (cellId){
	this.curClsTblRow = cellId.parentNode;
	this[CfldGrid.Constant.fldId] = this.curClsTblRow.childNodes[CfldGrid.Constant.fldIdIndex].innerHTML;	
	this.deleteTbl();										
			
}	
		

// Базовая таблица. Архивирование данных таблицы HTML		
CfldGrid.TblBase.prototype.archiveEvent = function (cellId){
	this.curClsTblRow = cellId.parentNode;
	this.setObj(this.data[this.curClsTblRow.rowIndex - 1]);	
	this[CfldGrid.Constant.fldStatus] = CfldGrid.Constant.statusArchive;
	this.curClsTblOper = CfldGrid.Constant.operArchive;
	this.edit();
}	

//Базовая таблица. Фильтр записей по статусу архив/неархив. Фильтрация таблиц
CfldGrid.Base.filterTblDataStatus = function (cl) {
	var tds = document.getElementsByTagName("td");
	var curChbVal = "";
	for (var i = 0; i<tds.length; i++) {
	  if (tds[i].className == "tdChkArchive") {
		curChbVal = tds[i].children[0].value; 
		if (curChbVal==CfldGrid.Constant.statusArchive) {
			if (cl == CfldGrid.Constant.filterStatusShowRecord) {
				tds[i].parentNode.hidden=false;
			}
			else {
				tds[i].parentNode.hidden=true;
			}				
		}
	  }
	}
}
//Базовая таблица. Фильтр записей по массиву внешних фильтров. Фильтрация таблиц
CfldGrid.Base.filterTblData = function (e, customParameter) {
	var arPrim = customParameter.split(";");
	var arFlt = arPrim[0].split(",");
	var selectorTblTbody = arPrim[1];
	if (arFlt == "") {
		return;
	}
	var tbody = document.getElementById(selectorTblTbody);
	for (var i = 0; i < tbody.getElementsByTagName("tr").length; i++) {
	  for (var k = 0; k < arFlt.length; k++) {
		if (tbody.rows[i].cells[[arFlt[k].fldNo]].innerHTML == document.getElementById(arFlt[k].filterName).value) {
			tbody.rows[i].hidden=false;
		}
		else {
			tbody.rows[i].hidden=true;
		}
	  }			
	}
}
		


// Базовая таблица. Вставка данных в таблицу
CfldGrid.TblBase.prototype.insertHtmlTable = function() {
	this.curClsTblRow = document.getElementById(this.selectorTblTbody).insertRow();
	var arFld = this.curStrArMeta.fields;
	arFld.sort(function(a, b) {
	    return parseInt(a.order) - parseInt(b.order);
	});	
	for (var i = 0 ; i < arFld.length ; i++) {
		var cellValue = "";
		if (arFld[i][CfldGrid.Constant.fldId].indexOf(CfldGrid.Constant.fldContainsCls) > -1) {
		    cellValue = this[arFld[i][CfldGrid.Constant.fldId]][CfldGrid.Constant.fldName];
		}	
		else {
			if (arFld[i][CfldGrid.Constant.formDataType] == CfldGrid.Constant.multiList) {
				var vals = this[arFld[i][CfldGrid.Constant.fldId]];
				if (vals !=undefined) {
					for (var j = 0; j < vals.length; j++) {
						if (cellValue != "") {
							cellValue = cellValue + CfldGrid.Constant.fldContainsSeparator;
						}
						cellValue = cellValue + vals[j][arFld[i].multiSelectField];
					}					
				}
			}
			else {
				cellValue = this[arFld[i][CfldGrid.Constant.fldId]];	
			}						
		}
		this.tblInsertFldMain(cellValue, arFld[i][CfldGrid.Constant.fldId]);			
	}			
	this.tblInsertFldAdd();		
	CfldGrid.Base.filterTblDataStatus(CfldGrid.Vars.filterStatusCurCl);		
}

// Базовая таблица. Формирование таблицы HTML. Вставка поля
CfldGrid.TblBase.prototype.tblInsertFldMain = function(cellValue, field) {
	var cellId;
	if (cellValue == null) { cellValue = ""; };
	cellId = this.curClsTblRow.insertCell();	
	if (field == CfldGrid.Constant.fldStatus) {
		var chkbox = document.createElement('input');
		chkbox.type = "checkbox";
		chkbox.className = "checkbox status";
		chkbox.id = this.tblName+"-chkarchive-"+this[CfldGrid.Constant.fldId] ;
		chkbox.name = "isArchive" ;
		chkbox.value = cellValue;
		cellId.className="tdChkArchive";
		chkbox.disabled = true;
		if (cellValue == CfldGrid.Constant.statusActual) {
			chkbox.checked = false;
		}
		else {
			chkbox.checked = true;
		}
		cellId.appendChild(chkbox);		
		return;
	}
	if (field.indexOf(CfldGrid.Constant.fldContainsMulti) > -1) {
		var arLbl = cellValue.split(CfldGrid.Constant.fldContainsSeparator);
		var lblExpr = "";
		for (var j = 0; j < arLbl.length; j++) {
			lblExpr = lblExpr + "<p><span class=\"aui-label\">"+arLbl[j]+"</span></p>";
		}
		cellId.innerHTML = lblExpr;	
	}
	else {
		cellId.innerHTML = cellValue;		
	}
}

// Базовая таблица. Формирование таблицы HTML. Вставка управляющих элементов
CfldGrid.TblBase.prototype.tblInsertFldAdd = function () {
		var cellId = this.curClsTblRow.insertCell();
		var objTbl = this;
		var btn = document.createElement('button');
			btn.className = "aui-button edit";
			btn.id = this.tblName+"-edit-col-"+this[CfldGrid.Constant.fldId];
			btn.title = "Редактировать запись" ;
			btn.resolved="";
			btn.innerHTML = '<span class="aui-icon aui-icon-small aui-iconfont-edit">Edit</span>';	
			cellId.appendChild(btn);
			btn.addEventListener("click", function() {
				objTbl.editEvent(cellId);
			}, false);
		cellId = this.curClsTblRow.insertCell();
		btn = document.createElement('button');
			btn.className = "aui-button archive";
			btn.id = this.tblName+"-archive-col-"+this[CfldGrid.Constant.fldId];
			btn.title = "Переместить запись в архив" ;
			btn.resolved="";
			btn.innerHTML = '<img src="'+AJS.params.baseURL+'/download/resources/com.itzabota.jira.plugins.servye.lsa:lsa-resources/images/archive16.png" alt="Архивировать" height="16" width="16">';					
			btn.addEventListener("click", function() {
				objTbl.archiveEvent(cellId);
			}, false);					
		cellId.appendChild(btn);
		cellId = this.curClsTblRow.insertCell();
		btn = document.createElement('button');
			btn.className = "aui-button delete";
			btn.id = this.tblName+"-delete-col-"+this[CfldGrid.Constant.fldId];
			btn.title = "Удалить запись" ;
			btn.resolved="";
			btn.innerHTML = '<img src="'+AJS.params.baseURL+'/download/resources/com.itzabota.jira.plugins.servye.lsa:lsa-resources/images/delete16.png" alt="Удалить" height="16" width="16">';	
			btn.addEventListener("click", function() {
				objTbl.deleteEvent(cellId);
			}, false);									
		cellId.appendChild(btn);		

		var chkbox = document.createElement('input');
			chkbox.type = "checkbox";
			chkbox.className = "checkbox select";
			chkbox.id = this.tblName+"-chksel-"+this[CfldGrid.Constant.fldId];
			chkbox.name = "isSelect" ;
			chkbox.value = 0;
			chkbox.enabled = true;
			chkbox.checked = false;		
			cellId = this.curClsTblRow.insertCell();
			cellId.appendChild(chkbox);						
}

// Базовая таблица. Редактирование данных таблицы и массива объекта. HTML		
CfldGrid.TblBase.prototype.updateHtmlTable = function () {
	var obj = this.makeJSON();
	var arFld = this.curStrArMeta.fields;
	var j = -1;
	for (var i = 0 ; i < arFld.length ; i++) {
	    this.data[this.curClsTblRow.rowIndex-1][arFld[i][CfldGrid.Constant.fldId]]=obj[arFld[i][CfldGrid.Constant.fldId]];
		if (arFld[i][CfldGrid.Constant.visibleFld] == CfldGrid.Constant.show) {
			j++;
			if (arFld[i][CfldGrid.Constant.fldId] == CfldGrid.Constant.fldStatus) {
				this.curClsTblRow.cells[j].childNodes[0].value=obj[arFld[i][CfldGrid.Constant.fldId]];
				this.curClsTblRow.cells[j].childNodes[0].checked=obj[arFld[i][CfldGrid.Constant.fldId]]==CfldGrid.Constant.statusArchive ? true : false;
				continue;
			}

			if (arFld[i][CfldGrid.Constant.fldId].indexOf(CfldGrid.Constant.fldContainsCls) > -1) {
				this.curClsTblRow.cells[j].innerHTML=obj[arFld[i][CfldGrid.Constant.fldId]][CfldGrid.Constant.fldName];
				continue;
			}
			if (arFld[i][CfldGrid.Constant.formDataType] == CfldGrid.Constant.multiList) {
				var vals = obj[arFld[i][CfldGrid.Constant.fldId]];
				var lblExpr = "";
				for (var j = 0; j < vals.length; j++) {
					lblExpr = lblExpr + "<p><span class=\"aui-label\">"+vals[j][arFld[i].multiSelectField]+"</span></p>";				
				}			
				this.curClsTblRow.cells[j].innerHTML = lblExpr;
				continue;
			}		
			this.curClsTblRow.cells[j].innerHTML = obj[arFld[i][CfldGrid.Constant.fldId]];
		}
	}	
	CfldGrid.Base.filterTblDataStatus(CfldGrid.Vars.filterStatusCurCl);
}	

// Базовая таблица. Удаление данных из таблицы HTML		
CfldGrid.TblBase.prototype.updateHtmlTableDelete = function () {
	document.getElementById(this.selectorTblTbody).deleteRow(this.curClsTblRow.rowIndex-1);
}	

// Базовая таблица. Сервер. Вставка данных в таблицу БД
CfldGrid.TblBase.prototype.insert = function () {
	this.setTblDataFrForm();
	var objTbl = this;
	AJS.$.ajax({
		type: "POST",
		url: objTbl.restUrl , 
	    data: JSON.stringify(objTbl.makeJSON()),
	    contentType: "application/json; charset=utf-8",
	    dataType: "json",		
		success : function(data) { 
			objTbl.setObj(data);
			objTbl.data.push(objTbl.makeJSON());
			// function below reloads current page
			objTbl.insertHtmlTable();
			objTbl.formOuterFilterRefill();
		},
		error : function(jqXHR, textStatus, errorThrown) { 
			var er = "Error with Post entity " + objTbl.toString() + ". "+jqXHR+" "+textStatus + " "+errorThrown;
			alert(er);
            AJS.logError(er);
            AJS.log(er);			
		},		
		async: true
	});	
}

// Базовая таблица. Сервер. Обновление данных в таблице БД
CfldGrid.TblBase.prototype.edit = function () {
	if (this.curClsTblOper == CfldGrid.Constant.operUpdate) {
		this.setTblDataFrForm();
	}	
	var objTbl = this;
	AJS.$.ajax({
		type: "PUT",
		url: objTbl.restUrl , 
	    data: JSON.stringify(objTbl.makeJSON()),
	    contentType: "application/json; charset=utf-8",
	    dataType: "json",		
		success : function(data) { 
			objTbl.setObj(data);
			// function below reloads current page
			objTbl.updateHtmlTable();
			objTbl.formOuterFilterRefill();
		},
		error : function(jqXHR, textStatus, errorThrown) { 
			var er = "Error with Put entity " + objTbl.toString() + ". "+jqXHR+" "+textStatus + " "+errorThrown;
			alert(er);
            AJS.logError(er);
            AJS.log(er);			
		},		
		async: true
	});	
}

// Базовая таблица. Сервер. Первоначальная загрузка данных		
CfldGrid.TblBase.prototype.loadData = function (isBuildHtml) {
	var objTbl = this;
	AJS.$.ajax({
	    type: "GET",
	    dataType: "json",
	    contentType: "application/json",
		url: objTbl.restUrl ,
		success : function(data) { 

			// here is the code that will run on client side after running php on server
			objTbl.data = data[objTbl.tblName+CfldGrid.Constant.tblManyEnding];
			// function below reloads current page
			if (isBuildHtml) {
				objTbl.buildHtmlTable();
			}
			objTbl.formOuterFilterRefill();
		},
		error : function(jqXHR, textStatus, errorThrown) { 
			var er = "Error with Get entity " + objTbl.toString() + ". "+jqXHR+" "+textStatus + " "+errorThrown;
			alert(er);
            AJS.logError(er);
            AJS.log(er);			
		},		
	async: false
	});
}

// Базовая таблица. Сервер. Удаление данных из таблицы БД	
CfldGrid.TblBase.prototype.deleteTbl = function (){
	var objTbl = this;
	AJS.$.ajax({
		type: "DELETE",
		url: objTbl.restUrl+'/'+objTbl.id, 
		success : function() { 
			// here is the code that will run on client side after running clear.php on server
			objTbl.data.splice(objTbl.curClsTblRow.rowIndex-1, 1);
			objTbl.updateHtmlTableDelete();
			objTbl.formOuterFilterRefill();
		},
		error : function(jqXHR, textStatus, errorThrown) { 
			var er = "Error with Delete entity " + objTbl.toString() + ". "+jqXHR+" "+textStatus + " "+errorThrown;
			alert(er);
            AJS.logError(er);
            AJS.log(er);			
		},			
	async: true
	});
}	

