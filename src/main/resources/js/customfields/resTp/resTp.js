// Базовая таблица. Вставка данных в таблицу
CfldGrid.TblBase.prototype.insertHtmlTable = function() {
	this.curClsTblRow = document.getElementById(this.selectorTblTbody).insertRow();
	var arFld = this.curStrArMeta.fields;
	arFld.sort(function(a, b) {
	    return parseInt(a.order) - parseInt(b.order);
	});	
	for (var i = 0 ; i < arFld.length ; i++) {
		if (arFld[i][CfldGrid.Constant.visibleFld] == CfldGrid.Constant.show) {
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
	}			
	this.tblInsertFldAdd();		
	CfldGrid.Base.filterTblDataStatus(CfldGrid.Vars.filterStatusCurCl);		
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
				if (CfldGrid.Base.getNameFromResTpTbl(arVal[q][CfldGrid.Constant.fldId])) {
					continue;
				}				
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
				if (this.curClsTblOper == CfldGrid.Constant.operInsert || this[arFld[i][CfldGrid.Constant.fldId]] != "") {
					var ar = CfldGrid[CfldGrid.Constant.objBaseBegin+tblNameForeign].data;
					if (arFld[i].filter != "") {
						var flt = arFld[i].filter;
						var formSel = arFld[i][CfldGrid.Constant.formName];
						el.onchange = CfldGrid.Util.bind(CfldGrid.Base.onChangeFormListEl, null, formSel+";"+tblNameForeign+";"+flt, true);	
					}	
					var option = document.createElement("option");
					option.value = "";
					option.text = "";
					el.appendChild(option);	
					for (var k = 0; k < ar.length; k++) { 
						if (this.curClsTblOper == CfldGrid.Constant.operInsert && CfldGrid.Base.getNameFromResTpTbl(ar[k][CfldGrid.Constant.fldId])) {
							continue;
						}
						var option = document.createElement("option");
						option.value = ar[k][CfldGrid.Constant.fldName];
						option.text = ar[k][CfldGrid.Constant.fldName];
						el.appendChild(option);										
					}				
				}
				if (this.curClsTblOper == CfldGrid.Constant.operInsert) {
					el.value = "";	
				}
				else {
					var expr1 = this[arFld[i][CfldGrid.Constant.fldId]];
					if (expr1 != undefined && expr1 != null) {
						for(j2 = el.options.length - 1 ; j2 >= 0 ; j2--)
						{
							if (el.options[j2].value == expr1) {
								el.options[j2].selected = "true";
								break;
							}
						}						
					}
					else {
						el.value = "";
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
}
	
// Базовая таблица. Формирование таблицы HTML. Вставка управляющих элементов
CfldGrid.TblBase.prototype.tblInsertFldAdd = function () {
		var index = /(\d+)/.exec(this.tblName)[0];
		var objTbl = this;
		if (CfldGrid.Vars.urlParams["parentId"] =='${issue.parentId}') {
			var cellId = this.curClsTblRow.insertCell();
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
		}
		var cellId = this.curClsTblRow.insertCell();
		var btn = document.createElement('button');
		btn.className = "aui-button delete";
		btn.id = this.tblName+"-delete-col-"+this[CfldGrid.Constant.fldId];
		btn.title = "Удалить запись" ;
		btn.resolved="";
		btn.innerHTML = '<img src="'+AJS.params.baseURL+'/download/resources/com.itzabota.jira.plugins.servye.lsa:lsa-resources/images/delete16.png" alt="Удалить" height="16" width="16">';	
		btn.addEventListener("click", function() {
			objTbl.deleteEvent(cellId);
		}, false);									
		cellId.appendChild(btn);							
}	

//Базовая таблица. Установка Rest адреса
CfldGrid.TblBase.prototype.setRestUrl = function () {
	if (this.tblName.indexOf(CfldGrid.Constant.tblFormNum) > -1) {
		this.restUrl = CfldGrid.Vars.restBaseUrl + this.tblName + CfldGrid.Constant.tblManyEnding + "?issueKey="+CfldGrid.Vars.urlParams["key"];
	}
	else {
		this.restUrl = CfldGrid.Vars.restBaseUrl + this.tblName + CfldGrid.Constant.tblManyEnding;
	}
	
}

CfldGrid.TblBase.prototype.updateListConnFields = function () {
	if (this.tblName.indexOf(CfldGrid.Constant.tblNameResTpIssue) > - 1) {
		var index = this.tblName.match(/\d+/g);
		this.type = index[0];
	// Установка типа, наименования, структурного кода, владельца, эксперта, специалиста
		var name1 = this[CfldGrid.Constant.fldClsFunction+index];
		if (name1 != undefined && name1 != "") {
			this[CfldGrid.Constant.fldName] = name1;
			var obj = CfldGrid.Util.findElOfArByProp(CfldGrid[CfldGrid.Constant.tblEntityQueryIssue].data, CfldGrid.Constant.fldName, name1);
			if (obj != undefined && obj != "") {
				this[CfldGrid.Constant.fldCdStr] = obj[CfldGrid.Constant.fldCdStr];
				this[CfldGrid.Constant.fldExpert] = obj[CfldGrid.Constant.fldExpert];
				this[CfldGrid.Constant.fldOwner] = obj[CfldGrid.Constant.fldOwner];
				this[CfldGrid.Constant.fldSpecialist] = obj[CfldGrid.Constant.fldSpecialist];
			}			
		}
		else {
			if (this[CfldGrid.Constant.fldClsModul+index] != undefined && this[CfldGrid.Constant.fldClsModul+index] != "") {
				var name1 = this[CfldGrid.Constant.fldClsModul+index];
				if (name1 != undefined && name1 != "") {
					this[CfldGrid.Constant.fldName] = name1;
					var obj = CfldGrid.Util.findElOfArByProp(CfldGrid[CfldGrid.Constant.tblEntityQueryIssue].data, CfldGrid.Constant.fldName, name1);
					if (obj != undefined && obj != "") {
						this[CfldGrid.Constant.fldCdStr] = obj[CfldGrid.Constant.fldCdStr];
						this[CfldGrid.Constant.fldExpert] = obj[CfldGrid.Constant.fldExpert];
						this[CfldGrid.Constant.fldOwner] = obj[CfldGrid.Constant.fldOwner];
						this[CfldGrid.Constant.fldSpecialist] = obj[CfldGrid.Constant.fldSpecialist];
					}
				}
			}
			else {
				if (this[CfldGrid.Constant.fldClsResource+index] != undefined && this[CfldGrid.Constant.fldClsResource+index] != "") {
					var name1 = this[CfldGrid.Constant.fldClsResource+index];
					if (name1 != undefined && name1 != "") {
						this[CfldGrid.Constant.fldName] = name1;
						var obj = CfldGrid.Util.findElOfArByProp(CfldGrid[CfldGrid.Constant.tblEntityQueryIssue].data, CfldGrid.Constant.fldName, name1);
						if (obj != undefined && obj != "") {
							this[CfldGrid.Constant.fldCdStr] = obj[CfldGrid.Constant.fldCdStr];
							this[CfldGrid.Constant.fldExpert] = obj[CfldGrid.Constant.fldExpert];
							this[CfldGrid.Constant.fldOwner] = obj[CfldGrid.Constant.fldOwner];
							this[CfldGrid.Constant.fldSpecialist] = obj[CfldGrid.Constant.fldSpecialist];
						}
					}
				}			
			}
		}
	}
}

// Поиск имени ресурса в таблице
CfldGrid.Base.getNameFromResTpTbl = function (id) {
	for (var property in CfldGrid) {
		if (property.indexOf(CfldGrid.Constant.tblEntityResTpIssue) > -1 && property.match(/\d+/g) != null) {			
			for (var index = 0; index < CfldGrid[property].data.length; index++) {
				var dat = CfldGrid[property].data[index];
				for (var num = 1; num <= CfldGrid.Vars.maxResTp; num++) {
					if (dat[CfldGrid.Constant.fldCdStr] == id) {
						return true;
					}		
				}				
			}
		}		
	}	
	return false;
}

// Базовая таблица. Сервер. Вставка данных в таблицу БД
CfldGrid.TblBase.prototype.insert = function () {
	this.setTblDataFrForm();
	this.updateListConnFields();
	this.id = 0;
	if (this.status == undefined || this.status == null || this.status == '') {
		this.status = CfldGrid.Constant.statusDefaultName;
	}	
	var objTbl = this;
	var data = objTbl.makeJSON();
	objTbl.setObj(data);
	objTbl.data.push(objTbl.makeJSON());
	// function below reloads current page
	objTbl.insertHtmlTable();
	objTbl.formOuterFilterRefill();
};

// Базовая таблица. Сервер. Обновление данных в таблице БД
CfldGrid.TblBase.prototype.edit = function () {
	if (this.curClsTblOper == CfldGrid.Constant.operUpdate) {
		this.setTblDataFrForm();	
	}	
	if (this.status == '') {
		this.status = CfldGrid.Constant.statusDefaultName;
	}		
	this.updateListConnFields();
	var objTbl = this;
	var data = objTbl.makeJSON();
	objTbl.setObj(data);
	// function below reloads current page
	objTbl.updateHtmlTable();
	objTbl.formOuterFilterRefill();
};

// Базовая таблица. Сервер. Удаление данных из таблицы. БД убрано	
CfldGrid.TblBase.prototype.deleteTbl = function (){
	var objTbl = this;
	objTbl.data.splice(objTbl.curClsTblRow.rowIndex-1, 1);
	objTbl.updateHtmlTableDelete();
	objTbl.formOuterFilterRefill();
};	

// Базовая таблица. Сервер. Первоначальная загрузка данных		
CfldGrid.TblBase.prototype.loadDataPart = function (mainTbl, num, isBuild) {
	var objTbl = this;
	objTbl.data = [];
	for (var index = 0; index < mainTbl.data.length; index++) {
		if (mainTbl.data[index].type == num) {
			var dat = mainTbl.data[index];
			if (dat.hasOwnProperty(CfldGrid.Constant.fldClsResource)){
				dat[CfldGrid.Constant.fldClsResource+num] = dat[CfldGrid.Constant.fldClsResource];
				delete dat[CfldGrid.Constant.fldClsResource];			
			}
			if (dat.hasOwnProperty(CfldGrid.Constant.fldClsModul)){
				dat[CfldGrid.Constant.fldClsModul+num] = dat[CfldGrid.Constant.fldClsModul];
				delete dat[CfldGrid.Constant.fldClsModul];			
			}
			if (dat.hasOwnProperty(CfldGrid.Constant.fldClsFunction)){
				dat[CfldGrid.Constant.fldClsFunction+num] = dat[CfldGrid.Constant.fldClsFunction];
				delete dat[CfldGrid.Constant.fldClsFunction];			
			}			
			objTbl.data.push(dat);
		}
	}
	if (isBuild) {
		objTbl.buildHtmlTable();
		objTbl.formOuterFilterRefill();	
	}
}

// Загрузка данных ресурсов
CfldGrid.Base.loadDataResParts = function (mainTbl) {
	var i11 = -1;
	var i12 = -1;
	var i13 = -1;
	var i21 = -1;
	var i22 = -1;
	var i23 = -1;
	var i31 = -1;
	var i32 = -1;
	var i33 = -1;
	var i41 = -1;
	var i42 = -1;
	var i43 = -1;
	
	for (var i = 0; i < mainTbl.data.length; i++) {
		var index = mainTbl.data[i].type;
		if (index <= CfldGrid.Vars.maxResTp) {
			if (mainTbl.data[i].resource != undefined) {
				var ar1 = {"id" : mainTbl.data[i].cdStr, "name" : mainTbl.data[i].resource, "parentId" : "0"};
				if (CfldGrid.Util.findElOfArByProp(CfldGrid[CfldGrid.Constant.tblEntityResource+index].data, CfldGrid.Constant.fldName, ar1[CfldGrid.Constant.fldName]) == null) {
					if (index == 1) {
						i11++;
						CfldGrid[CfldGrid.Constant.tblEntityResource+index].data[i11] = ar1;	
					}		
					if (index == 2) {
						i21++;
						CfldGrid[CfldGrid.Constant.tblEntityResource+index].data[i21] = ar1;	
					}	
					if (index == 3) {
						i31++;
						CfldGrid[CfldGrid.Constant.tblEntityResource+index].data[i31] = ar1;	
					}	
					if (index == 4) {
						i41++;
						CfldGrid[CfldGrid.Constant.tblEntityResource+index].data[i41] = ar1;	
					}																		
				}			
			}
			if (mainTbl.data[i].modul != undefined) {
				var ar1 = {"id" : mainTbl.data[i].cdStr, "name" : mainTbl.data[i].modul, "parentId" : mainTbl.data[i].cdStr.substr(0, mainTbl.data[i].cdStr.lastIndexOf("."))};
				if (CfldGrid.Util.findElOfArByProp(CfldGrid[CfldGrid.Constant.tblEntityModul+index].data, CfldGrid.Constant.fldName, ar1[CfldGrid.Constant.fldName]) == null) {
					if (index == 1) {
						i12++;
						CfldGrid[CfldGrid.Constant.tblEntityModul+index].data[i12] = ar1;		
					}		
					if (index == 2) {
						i22++;
						CfldGrid[CfldGrid.Constant.tblEntityModul+index].data[i22] = ar1;		
					}	
					if (index == 3) {
						i32++;
						CfldGrid[CfldGrid.Constant.tblEntityModul+index].data[i32] = ar1;		
					}	
					if (index == 4) {
						i42++;
						CfldGrid[CfldGrid.Constant.tblEntityModul+index].data[i42] = ar1;		
					}													
				}										
			}			
			if (mainTbl.data[i]["function"] != undefined) {
				var ar1 = {"id" : mainTbl.data[i].cdStr, "name" : mainTbl.data[i]["function"], "parentId" : mainTbl.data[i].cdStr.substr(0, mainTbl.data[i].cdStr.lastIndexOf("."))};
				if (CfldGrid.Util.findElOfArByProp(CfldGrid[CfldGrid.Constant.tblEntityFunction+index].data, CfldGrid.Constant.fldName, ar1[CfldGrid.Constant.fldName]) == null) {
					if (index == 1) {
						i13++;
						CfldGrid[CfldGrid.Constant.tblEntityFunction+index].data[i13] = ar1;		
					}		
					if (index == 2) {
						i23++;
						CfldGrid[CfldGrid.Constant.tblEntityFunction+index].data[i23] = ar1;		
					}	
					if (index == 3) {
						i33++;
						CfldGrid[CfldGrid.Constant.tblEntityFunction+index].data[i33] = ar1;		
					}	
					if (index == 4) {
						i43++;
						CfldGrid[CfldGrid.Constant.tblEntityFunction+index].data[i43] = ar1;		
					}		
									
				}							
			}										
		}
	}	
}

// Базовые прототипы

CfldGrid.TblEntityRes = function(tblName) {
	CfldGrid.TblBase.apply(this, arguments);
}
CfldGrid.TblEntityRes.prototype = Object.create(CfldGrid.TblBase.prototype);
CfldGrid.TblEntityRes.prototype.constructor = CfldGrid.TblEntityRes;

CfldGrid.TblEntityResTp = function(tblName) {
	CfldGrid.TblBase.apply(this, arguments);
};
CfldGrid.TblEntityResTp.prototype = Object.create(CfldGrid.TblBase.prototype);
CfldGrid.TblEntityResTp.prototype.constructor = CfldGrid.TblEntityResTp;

CfldGrid.TblEntityProfile = function(tblName) {
	CfldGrid.TblBase.apply(this, arguments);
}
CfldGrid.TblEntityProfile.prototype = Object.create(CfldGrid.TblBase.prototype);
CfldGrid.TblEntityProfile.prototype.constructor = CfldGrid.TblEntityProfile;

CfldGrid.TblEntityResource = function(tblName) {
	CfldGrid.TblBase.apply(this, arguments);
}
CfldGrid.TblEntityResource.prototype = Object.create(CfldGrid.TblBase.prototype);
CfldGrid.TblEntityResource.prototype.constructor = CfldGrid.TblEntityResource;

CfldGrid.TblEntityModul = function(tblName) {
	CfldGrid.TblBase.apply(this, arguments);
}
CfldGrid.TblEntityModul.prototype = Object.create(CfldGrid.TblBase.prototype);
CfldGrid.TblEntityModul.prototype.constructor = CfldGrid.TblEntityModul;

CfldGrid.TblEntityFunction = function(tblName) {
	CfldGrid.TblBase.apply(this, arguments);
}
CfldGrid.TblEntityFunction.prototype = Object.create(CfldGrid.TblBase.prototype);
CfldGrid.TblEntityFunction.prototype.constructor = CfldGrid.TblEntityFunction;

CfldGrid.TblEntityResTemplate = function(tblName) {
	CfldGrid.TblBase.apply(this, arguments);
}
CfldGrid.TblEntityResTemplate.prototype = Object.create(CfldGrid.TblBase.prototype);
CfldGrid.TblEntityResTemplate.prototype.constructor = CfldGrid.TblEntityResTemplate;

for (var index = 1; index <= CfldGrid.Vars.maxResTp; index++) {
	CfldGrid["TblEntityResource"+index] = function(tblName) {
		CfldGrid.TblBase.apply(this, arguments);
	};
	CfldGrid["TblEntityResource"+index].prototype = Object.create(CfldGrid.TblBase.prototype);
	CfldGrid["TblEntityResource"+index].prototype.constructor = CfldGrid["TblEntityResource"+index];
	
	CfldGrid["TblEntityModul"+index] = function(tblName) {
		CfldGrid.TblBase.apply(this, arguments);
	};
	CfldGrid["TblEntityModul"+index].prototype = Object.create(CfldGrid.TblBase.prototype);
	CfldGrid["TblEntityModul"+index].prototype.constructor = CfldGrid["TblEntityModul"+index];

	CfldGrid["TblEntityFunction"+index] = function(tblName) {
		CfldGrid.TblBase.apply(this, arguments);
	};
	CfldGrid["TblEntityFunction"+index].prototype = Object.create(CfldGrid.TblBase.prototype);
	CfldGrid["TblEntityFunction"+index].prototype.constructor = CfldGrid["TblEntityFunction"+index];
	
	CfldGrid["TblEntityResTp"+index] = function(tblName) {
		CfldGrid.TblBase.apply(this, arguments);
	};
	CfldGrid["TblEntityResTp"+index].prototype = Object.create(CfldGrid.TblBase.prototype);
	CfldGrid["TblEntityResTp"+index].prototype.constructor = CfldGrid["TblEntityResTp"+index];
	
	CfldGrid["TblEntityResTemplate"+index] = function(tblName) {
		CfldGrid.TblBase.apply(this, arguments);
	};
	CfldGrid["TblEntityResTemplate"+index].prototype = Object.create(CfldGrid.TblBase.prototype);
	CfldGrid["TblEntityResTemplate"+index].prototype.constructor = CfldGrid["TblEntityResTemplate"+index];	
}

// Инициализация
AJS.$(document).ready(function() {
	CfldGrid.Vars.restBaseUrl = AJS.params.baseURL+CfldGrid.Constant.restPartUrl;
	CfldGrid.Vars.urlParams = CfldGrid.Util.getUrlVars();
	

	// Константы Java и базовые переменные
	CfldGrid.Base.loadBase();
	
	// Заголовок
	document.getElementById("issueName").InnerHTML = CfldGrid.Vars.urlParams["key"];
	
	if (CfldGrid.Vars.urlParams["parentId"] !='${issue.parentId}') {
		document.getElementById('profileContainerWarn').style.display = "none";
		document.getElementById('profileContainer').style.display = "none";
		document.getElementById('btnAddRecord').style.display = "none";
		document.getElementById('groupActionsBtn').style.display = "none";
				
	}
	 
	var ar = CfldGrid.Vars.arTbl;
	ar.sort(function(a, b) {
	    return parseInt(a.order) - parseInt(b.order);
	});	
	for (var i = 0; i < ar.length; i++) {
		var tbl = CfldGrid[ar[i].key] = new CfldGrid[CfldGrid.Util.toProperCase(ar[i].key)](ar[i].name);
		tbl.setRestUrl();			
		if (tbl.tblName.indexOf(CfldGrid.Constant.tblResTemplateName) > - 1) {
			tbl.tblName = tbl.tblName.replace(CfldGrid.Constant.tblResTemplateName, CfldGrid.Constant.tblResTp);
			tbl.selectorTbl = 'tbl-'+tbl.tblName; 
			tbl.selectorTblTbody='tbody-'+tbl.tblName;						
		}		
		tbl.filterOuter = ar[i].filterOuter;
		tbl.formSelOuter = ar[i].formSelOuter;
		if (ar[i].filterOuter != "") {
			var arFlt = ar[i].filterOuter;
			for (var t = 0; t < arFlt.length; t++) {
				var el = document.getElementById(arFlt[t].filterName);
				var selectorTblTbody = tbl.selectorTblTbody;
				var filterOuter = tbl.filterOuter;
				el.onchange = CfldGrid.Util.bind(CfldGrid.Base.filterTblData, null, filterOuter+";"+selectorTblTbody, true);
			}
		}

	}
	
	
	// Установка событий
	AJS.$("#btnAddRecord").click(function() {
		if (AJS.$('#idLink-resTp').attr('class')=="entity-item-active") {
			for (var index = 1; index <= CfldGrid.Vars.maxResTp; index++) {
				if (AJS.$('#tabs-li-resTp'+index).attr('class')=="menu-item active-tab") {
					CfldGrid["tblEntityResTp"+index].curClsTblOper = CfldGrid.Constant.operInsert;	
					CfldGrid["tblEntityResTp"+index].cleanDialog();
					CfldGrid["tblEntityResTp"+index].beforeDialogShow();
					AJS.dialog2("#insert-dialog-resTp"+index).show();
				}				
			}			
		}		
	});
	
	for (var index = 1; index <= CfldGrid.Vars.maxResTp; index++) {
		// Hides the dialog2
		AJS.$("#dialog-resTp"+index+"-close-button").click(function(e) {
			e.preventDefault();
			var index2 = e.currentTarget.id.match(/\d+/g); 
			if (index2 != undefined && index2 != null) {
				index2 = index2[0];
				CfldGrid["tblEntityResTp"+index2].cleanDialog();
				AJS.dialog2("#insert-dialog-resTp"+index2).hide();				
			}
		});		
		//Диалог сохранения данных в форме	
		AJS.$("#dialog-resTp"+index+"-submit-button").click(function(e) {
			e.preventDefault();
			var index2 = e.currentTarget.id.match(/\d+/g); 
			if (index2 != undefined && index2 != null) {
				index2 = index2[0];
				var rez = CfldGrid.Base.validData(index2);
				if (!rez.er) {
					if (CfldGrid["tblEntityResTp"+index2].curClsTblOper==CfldGrid.Constant.operInsert) {
						CfldGrid["tblEntityResTp"+index2].insert();
					}
					else {
						CfldGrid["tblEntityResTp"+index2].edit();
					}
					CfldGrid["tblEntityResTp"+index2].cleanDialog();
					AJS.dialog2('#insert-dialog-resTp'+index2).hide();
					if (rez.msg) {
						AJS.messages.generic("#message-form-valid-info", {
						  title: 'Информационное сообщение',
						  body: rez.msg,
						  fadeout: true,
						  delay: 5000
						});	
					}				
				}
			}				
		});		
	}

	//Активация вкладки оглавления при выборе

	AJS.$('.hrefCls').click(function(e){	
		e.preventDefault();
		var cl = AJS.$(this).closest('div').attr('class');
		if (cl == "entity-item") {
			var id = AJS.$(this).closest('div').attr('id');
			for (var key in CfldGrid.Constant.arCls) {  	
				if (id == CfldGrid.Constant.arCls[key].idLink) {
					AJS.$('#label-entity-header-items-name').text(CfldGrid.Constant.arCls[key].name);	
					AJS.$("#"+AJS.$("."+CfldGrid.Constant.arCls[key].clDiv).attr('id')).show();
					AJS.$("#"+CfldGrid.Constant.arCls[key].idLink).removeClass("entity-item").addClass("entity-item-active");			
				}
				else {
					AJS.$("#"+AJS.$("."+CfldGrid.Constant.arCls[key].clDiv).attr('id')).hide();
					if (AJS.$("#"+CfldGrid.Constant.arCls[key].idLink).attr('class')=="entity-item-active") {
						AJS.$("#"+CfldGrid.Constant.arCls[key].idLink).removeClass("entity-item-active").addClass("entity-item");
					}
				}
			}
		}
		return false;
	});
	
	AJS.$('#btnSave').click(function(e){
		CfldGrid.Base.saveIssue();		
	});	
	
	AJS.$("#dialog-form-valid-close-button").click(function(e) {
		e.preventDefault();
		AJS.dialog2("#dialog-form-valid").hide();
    });	

	AJS.$('#btnCancel').click(function(e){
		window.location.replace(CfldGrid.Util.urlIssue());
	});	
	
	
// Загрузка данных 

	CfldGrid.tblEntityProfile.loadData(false);
	CfldGrid.Base.fillProfile();

	//Фильтр записей по профилю. Событие
	AJS.$('#headSel-resTpResourceProfileName').change(function(e) {
		e.preventDefault();
		var val = document.getElementById('headSel-resTpResourceProfileName').value;
		CfldGrid.Base.filterTblDataResTp(val);
	});	
	// Общая таблица ресурсов
	CfldGrid.tblEntityRes.loadData(false);
	
	CfldGrid.Base.loadDataResParts(CfldGrid.tblEntityRes);
	// Общая таблица ресурсов заявки
	CfldGrid.tblEntityResTp.loadData(false);
	CfldGrid.tblEntityResTemplate.loadData(false);
	// Конкретика по индексу
	
	for (var index = 1; index <= CfldGrid.Vars.maxResTp; index++) {
		CfldGrid["tblEntityResTp"+index].loadDataPart(CfldGrid.tblEntityResTp, index, true);
		CfldGrid["tblEntityResTemplate"+index].loadDataPart(CfldGrid.tblEntityResTemplate, index, false);
	}

});

// Перезаполнение таблиц по фильтру профиля
CfldGrid.Base.filterTblDataResTp = function (val) {
	for (var property in CfldGrid) {
		if (property.indexOf(CfldGrid.Constant.tblEntityResTemplate) > -1 && property.match(/\d+/g) != null) {
			var index = property.match(/\d+/g)[0];
			var tbody = document.getElementById(CfldGrid[CfldGrid.Constant.tblEntityResTpIssue+index].selectorTblTbody);
			// Очистка таблицы
			AJS.$("#"+CfldGrid[CfldGrid.Constant.tblEntityResTpIssue+index].selectorTblTbody).empty();
			CfldGrid[CfldGrid.Constant.tblEntityResTpIssue+index].data = [];
			var k = -1;
			for (var i = 0; i < CfldGrid[CfldGrid.Constant.tblEntityResTemplate+index].data.length; i++) {
				if (CfldGrid[CfldGrid.Constant.tblEntityResTemplate+index].data[i][CfldGrid.Constant.colTemplateName] == val) {
					k++;					
					CfldGrid[CfldGrid.Constant.tblEntityResTpIssue+index].data[k] = CfldGrid[CfldGrid.Constant.tblEntityResTemplate+index].data[i];					
				}
			}
			if (k >= 0) {
				CfldGrid[CfldGrid.Constant.tblEntityResTpIssue+index].buildHtmlTable();
			}			
		}
	}
}

CfldGrid.TblBase.prototype.makeJSON = function () {
	var obj = new Object();
	var arFld = this.curStrArMeta.fields;
	for (var i = 0 ; i < arFld.length ; i++) { 	
		obj[arFld[i][CfldGrid.Constant.fldId]] = this[arFld[i][CfldGrid.Constant.fldId]];			
	}
	return obj;
}

CfldGrid.Base.unionDataResTp = function () {
	var unionObj = new Object();
	unionObj.resTps = [];
	unionObj.issueKey = CfldGrid.Vars.urlParams["key"];
	for (var property in CfldGrid) {
		if (property.indexOf(CfldGrid.Constant.tblEntityResTpIssue) > -1 && property.match(/\d+/g) != null) {			
			for (var index = 0; index < CfldGrid[property].data.length; index++) {
				var dat = CfldGrid[property].data[index];
				for (var num = 1; num <= CfldGrid.Vars.maxResTp; num++) {
					if (dat[CfldGrid.Constant.fldClsResource+num] != undefined) {
						dat[CfldGrid.Constant.fldClsResource] = dat[CfldGrid.Constant.fldClsResource+num];
						delete dat[CfldGrid.Constant.fldClsResource+num];					
					}		
					if (dat[CfldGrid.Constant.fldClsModul+num] != undefined) {
						dat[CfldGrid.Constant.fldClsModul] = dat[CfldGrid.Constant.fldClsModul+num];
						delete dat[CfldGrid.Constant.fldClsModul+num];					
					}	
					if (dat[CfldGrid.Constant.fldClsFunction+num] != undefined) {
						dat[CfldGrid.Constant.fldClsFunction] = dat[CfldGrid.Constant.fldClsFunction+num];
						delete dat[CfldGrid.Constant.fldClsFunction+num];					
					}						
				}				
				unionObj.resTps.push(dat);
			}
		}		
	}
	return unionObj;
}

CfldGrid.Base.validUnionDataResTp = function (resTp) {
	var unionObj = new Object();
	unionObj.resTps = [];
	unionObj.issueKey = CfldGrid.Vars.urlParams["key"];
	for (var index = 0; index < resTp.data.length; index++) {
		var dat = resTp.data[index];
		for (var num = 1; num <= CfldGrid.Vars.maxResTp; num++) {
			if (dat[CfldGrid.Constant.fldClsResource+num] != undefined) {
				dat[CfldGrid.Constant.fldClsResource] = dat[CfldGrid.Constant.fldClsResource+num];
				delete dat[CfldGrid.Constant.fldClsResource+num];
			}		
			if (dat[CfldGrid.Constant.fldClsModul+num] != undefined) {
				dat[CfldGrid.Constant.fldClsModul] = dat[CfldGrid.Constant.fldClsModul+num];
				delete dat[CfldGrid.Constant.fldClsModul+num];					
			}	
			if (dat[CfldGrid.Constant.fldClsFunction+num] != undefined) {
				dat[CfldGrid.Constant.fldClsFunction] = dat[CfldGrid.Constant.fldClsFunction+num];
				delete dat[CfldGrid.Constant.fldClsFunction+num];					
			}						
		}				
		unionObj.resTps.push(dat);
	}
	return unionObj;
}

CfldGrid.Base.validUnUnionDataResTp = function (resTp) {
	for (var index = 0; index < resTp.data.length; index++) {
		var dat = resTp.data[index];
		for (var num = 1; num <= CfldGrid.Vars.maxResTp; num++) {
			if (dat[CfldGrid.Constant.fldClsResource] != undefined) {
				dat[CfldGrid.Constant.fldClsResource+num] = dat[CfldGrid.Constant.fldClsResource];
				delete dat[CfldGrid.Constant.fldClsResource];					
			}		
			if (dat[CfldGrid.Constant.fldClsModul] != undefined) {
				dat[CfldGrid.Constant.fldClsModul+num] = dat[CfldGrid.Constant.fldClsModul];
				delete dat[CfldGrid.Constant.fldClsModul];					
			}	
			if (dat[CfldGrid.Constant.fldClsFunction] != undefined) {
				dat[CfldGrid.Constant.fldClsFunction+num] = dat[CfldGrid.Constant.fldClsFunction];
				delete dat[CfldGrid.Constant.fldClsFunction];					
			}						
		}				
	}
	return;
}

CfldGrid.Base.unUnionDataResTp = function () {
	for (var property in CfldGrid) {
		if (property.indexOf(CfldGrid.Constant.tblEntityResTpIssue) > -1 && property.match(/\d+/g) != null) {			
			for (var index = 0; index < CfldGrid[property].data.length; index++) {
				var dat = CfldGrid[property].data[index];
				for (var num = 1; num <= CfldGrid.Vars.maxResTp; num++) {
					if (dat[CfldGrid.Constant.fldClsResource] != undefined) {
						dat[CfldGrid.Constant.fldClsResource+num] = dat[CfldGrid.Constant.fldClsResource];
						delete dat[CfldGrid.Constant.fldClsResource];					
					}		
					if (dat[CfldGrid.Constant.fldClsModul] != undefined) {
						dat[CfldGrid.Constant.fldClsModul+num] = dat[CfldGrid.Constant.fldClsModul];
						delete dat[CfldGrid.Constant.fldClsModul];					
					}	
					if (dat[CfldGrid.Constant.fldClsFunction] != undefined) {
						dat[CfldGrid.Constant.fldClsFunction+num] = dat[CfldGrid.Constant.fldClsFunction];
						delete dat[CfldGrid.Constant.fldClsFunction];					
					}						
				}				
			}
		}		
	}
	return;
}

CfldGrid.Util.urlIssue = function() {
	var issueKey = "";
	issueKey = CfldGrid.Vars.urlParams["key"];
	return AJS.params.baseURL + "/browse/" + issueKey;
};

CfldGrid.Base.saveIssue = function() {
	CfldGrid.Base.mergeRest();
};

CfldGrid.Base.validData = function(index) {
	var errors = "";
	var valid = true;
	var el1 = document.getElementById("resTp"+index+"Resource"+index+"Name");	
	var el2 = document.getElementById("resTp"+index+"Modul"+index+"Name");	
	var el3 = document.getElementById("resTp"+index+"Function"+index+"Name");	
	var elComment = document.getElementById("resTp"+index+"Comment");
	for (var i = 0; i < CfldGrid["tblEntityResTp"+index].data.length; i++) {
		var resource = CfldGrid["tblEntityResTp"+index].data[i][CfldGrid.Constant.fldClsResource+index];
		var modul = CfldGrid["tblEntityResTp"+index].data[i][CfldGrid.Constant.fldClsModul+index];
		if (modul == undefined) {
			modul = "";
		}
		var func = CfldGrid["tblEntityResTp"+index].data[i][CfldGrid.Constant.fldClsFunction+index];
		if (func == undefined) {
			func = "";
		}		
		if (el1.value == resource && el2.value == modul &&el3.value == func) {
			valid = false;
			break;
		}
	}
	if (!valid) {
		var res = "";
		if (el3.value != undefined && el3.value != null && el3.value != '') {
			res = el3.value;
		}
		else {
			if (el2.value != undefined && el2.value != null && el2.value != '') {
				res = el2.value;
			}	
			else {
				if (el1.value != undefined && el1.value != null && el1.value != '') {
					res = el1.value;
				}		
			}			
		}
		errors = errors + " Ресурс " + res + " уже есть в заявке! " + "<br />";
	}
	if (el3.options.length > 0 && (el3.value == undefined || el3.value == null || el3.value == '')) {
		errors = errors + " Необходимо выбрать функцию!" + "<br />";
		valid = false;
	}
	if (el2.options.length > 0 && (el2.value == undefined || el2.value == null || el2.value == '')) {
		errors = errors + " Необходимо выбрать модуль!" + "<br />";
		valid = false;
	}	
	if (el1.options.length > 0 && (el1.value == undefined || el1.value == null || el1.value == '')) {
		errors = errors + " Необходимо выбрать ресурс!" + "<br />";
		valid = false;
	}		
	if ((el2.value == CfldGrid.Constant.resourceTypeOther ||
			el3.value == CfldGrid.Constant.resourceTypeOther) &&
			(elComment.value == undefined ||
			elComment.value == null ||
			elComment.value == '')) 
	{	
		valid = false;
		errors = errors + " Комментарий обязателен для заполнения при выборе значения модуля или функции " + CfldGrid.Constant.resourceTypeOther + "<br />";
	}
	if (!valid) {
		document.getElementById("dialog-form-valid-errors").innerHTML = errors; 
		AJS.dialog2("#dialog-form-valid").show();		
	}	
	var rez = {};
	if (valid) {
		rez = CfldGrid.Base.validRest(el1.value, el2.value, el3.value, index);
		valid = !rez.er;
	}
	if (!valid) {
		rez = {er: true};
	}
	return rez;
}


CfldGrid.Base.checkNeedFieldsFilled =  function() {
	var valid = true;
	return true;
	
	var errors = "";
	for (var property in CfldGrid) {
		if (property.indexOf(CfldGrid.Constant.tblEntityResTpIssue) > -1 && property.match(/\d+/g) != null) {	
			var num = property.match(/\d+/g)[0];						
			for (var index = 0; index < CfldGrid[property].data.length; index++) {
				var dat = CfldGrid[property].data[index];				
				if (CfldGrid[property].data.length > 0 && ((dat[CfldGrid.Constant.fldClsModul+num] == CfldGrid.Constant.resourceTypeOther ||
						dat[CfldGrid.Constant.fldClsFunction+num] == CfldGrid.Constant.resourceTypeOther) &&
						(dat[CfldGrid.Constant.fldComment] == undefined ||
						dat[CfldGrid.Constant.fldComment] == null ||
						dat[CfldGrid.Constant.fldComment] == ''))) 
				{
					valid = false;
					var tpResStr = '';
					switch (num) {
					   case '1':
						  tpResStr = 'Информационные системы';
						  break;
					   case '2':
						  tpResStr = 'Оборудование';
						  break;
					   case '3':
						  tpResStr = 'Дополнительные функции';
						  break;
					   case '4':
						  tpResStr = 'Витрина данных';
						  break;								  
					   default:
						  tpResStr = 'Тип ресура ' + num;
						  break;
					}							
					errors = errors + "\n" + tpResStr + " Строчка " + (index + 1).toString() + "<br />";						
				}				
			}
		}		
	}
	if (!valid) {
		document.getElementById("dialog-form-valid-errors").innerHTML = errors; 
		AJS.dialog2("#dialog-form-valid").show();	
	}	
	return valid;

}

CfldGrid.Base.fillProfile =  function() {
	var el = document.getElementById("headSel-resTpResourceProfileName");
	var option = document.createElement("option");
	option.value = CfldGrid.Constant.profileNo;
	option.text = CfldGrid.Constant.profileNo;
	el.appendChild(option);		
	for (var i = 0; i < CfldGrid.tblEntityProfile.data.length; i++) {
		var option = document.createElement("option");
		option.value = CfldGrid.tblEntityProfile.data[i][CfldGrid.Constant.fldName];
		option.text = CfldGrid.tblEntityProfile.data[i][CfldGrid.Constant.fldName];
		el.appendChild(option);			
	}			
	// Инициализация значением java-константы
	AJS.$("#headSel-resTpResourceProfileName").auiSelect2();	
	el.selectedIndex = CfldGrid.Constant.profileDefaultNum;
};

//@Deprecated
CfldGrid.Base.refillResSelect =  function(index) {
	var elName = "select2resTp"+index+"-res";
	var el = document.getElementById(elName);
	for(j = el.options.length - 1 ; j >= 0 ; j--)
	{
		el.remove(j);
	}	
	var src = CfldGrid.tblEntityRes;
	for (var i = 0; i < src.data.length; i++) {
		if (src.data[i].type == index) {
			var option = document.createElement("option");
			option.value = src.data[i][CfldGrid.Constant.fldName];
			option.text = src.data[i][CfldGrid.Constant.fldName];
			el.appendChild(option);					
		}
	}			
	AJS.$("#"+elName).auiSelect2();	
};

// Базовая таблица. Сервер. Константы
CfldGrid.Base.loadConstant = function () {
	AJS.$.ajax({
		type: "GET",
		dataType: "json",
		contentType: "application/json",
		url: CfldGrid.Vars.restBaseUrl + "lsaConstant" ,
		success : function(data) { 
			// here is the code that will run on client side after running php on server
			CfldGrid.Vars.javaConstant = data;
			// function below reloads current page
		},
		error : function(jqXHR, textStatus, errorThrown) { 
			var er = "Error with Get entity LsaConstant " + ". "+jqXHR+" "+textStatus + " "+errorThrown;
			alert(er);
			AJS.logError(er);
			AJS.log(er);			
		},		
	async: false
	});
}	

// Базовая таблица. Сервер. Константы
CfldGrid.Base.loadBase = function () {
	AJS.$.ajax({
		type: "GET",
		dataType: "json",
		data: {issueKey : CfldGrid.Vars.urlParams["key"]},
		contentType: "application/json",
		url: CfldGrid.Vars.restBaseUrl + "resTpsPackets" ,
		success : function(data) { 
			CfldGrid.Vars.javaConstant = data.lsaConstant;
			CfldGrid.Vars.issueSummary = data.summary;
			CfldGrid.Vars.issueAssigneeId = data.assigneeId;
			CfldGrid.Vars.employeeId = data.employeeId;
			CfldGrid.Base.updateIssue();
		},
		error : function(jqXHR, textStatus, errorThrown) { 
			var er = "Error with Get entity ResTpsPackets " + ". "+jqXHR+" "+textStatus + " "+errorThrown;
			alert(er);
			AJS.logError(er);
			AJS.log(er);			
		},		
	async: false
	});
}	

// Базовая таблица. Сервер. Слияние данных с таблицей БД
CfldGrid.Base.updateIssue = function () {
// пока комментируем
	return;
	
	var restUrl =  AJS.params.baseURL+CfldGrid.Constant.restSystemPartUrl + 'issue/' + CfldGrid.Vars.urlParams["key"];
	var objTbl = {};
	objTbl.fields = {};
	var isUpdate = false;
	if (CfldGrid.Vars.issueSummary != null) {
		objTbl.fields.summary = CfldGrid.Vars.issueSummary;
		isUpdate = true;
	} 
	if (CfldGrid.Vars.issueAssigneeId != null) {
		objTbl.fields.assignee = {"name":CfldGrid.Vars.issueAssigneeId};
		isUpdate = true;
	} 	
	if (isUpdate) {
		AJS.$.ajax({
			type: "PUT",
			url: restUrl , 
			data: JSON.stringify(objTbl),
			contentType: "application/json; charset=utf-8",
			dataType: "json",		
			success : function(data) {

			},
			error : function(jqXHR, textStatus, errorThrown) { 
				var er = "Error with Post entity " + objTbl.toString() + ". "+jqXHR+" "+textStatus + " "+errorThrown;
	//			alert(er);
				AJS.logError(er);
				AJS.log(er);			
			},		
			async: true
		});		
	}
};	

// Базовая таблица. Сервер. Валидация данных с таблицей БД
CfldGrid.Base.validRest = function (resName, modulName, funcName, index0) {
	var objTbl = {issueKey: CfldGrid.Vars.urlParams["key"], resName: resName, modulName: modulName, funcName: funcName, employeeId: CfldGrid.Vars.employeeId};
	var restUrl = CfldGrid.Vars.restBaseUrl + "resStatusValid";
	var index = index0;
	var rez = {er: true};
	AJS.$.ajax({
		type: "POST",
		url: restUrl , 
		data: JSON.stringify(objTbl),
		contentType: "application/json; charset=utf-8",
		dataType: "json",		
		success : function(data) {
			var canApproveResource = data.canApproveResource;
			rez = {er: false};
			if (canApproveResource && data.statusName != undefined && data.statusName != null && data.statusName != '' && data.statusName != CfldGrid.Constant.statusDefaultName) {
				var el = document.getElementById("resTp"+index+"Status");
				el.value = data.statusName;			
				var infos = " Доступ к ресурсу уже был выдан сотруднику до " + data.tsEndStr + " !";
				rez.msg = infos;
			}
		},
		error : function(jqXHR, textStatus, errorThrown) { 
			var er = "Error with Post entity " + objTbl.toString() + ". "+jqXHR+" "+textStatus + " "+errorThrown;
			AJS.logError(er);
			AJS.log(er);			
		},		
		async: false
	});
	return rez;	
};	

// Базовая таблица. Сервер. Слияние данных с таблицей БД
CfldGrid.Base.mergeRest = function () {
	var restUrl = CfldGrid.Vars.restBaseUrl + "resTpsPackets";
	var objTbl = CfldGrid.Base.unionDataResTp();
	AJS.$.ajax({
		type: "POST",
		url: restUrl , 
		data: JSON.stringify(objTbl),
		contentType: "application/json; charset=utf-8",
		dataType: "json",		
		success : function(data) {
			window.location.replace(CfldGrid.Util.urlIssue());						
		},
		error : function(jqXHR, textStatus, errorThrown) { 
			var er = "Error with Post entity " + objTbl.toString() + ". "+jqXHR+" "+textStatus + " "+errorThrown;
			AJS.logError(er);
			AJS.log(er);			
		},		
		async: true
	});		
};	