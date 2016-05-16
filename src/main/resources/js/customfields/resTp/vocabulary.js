CfldGrid.Constant = {
		arCls : [
			     	{"idLink" : "idLink-resTp", "clDiv" : "resTp", "name" : "Справочник ресурсов заявки"}
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
		fldCdStr: "cdStr",		
		fldExpert: "loginExpert",
		fldOwner: "loginOwner",
		fldSpecialist: "loginSpecialist",
		fldComment: "comment",		
		fldIdIndex: 0,
		fldStatus: "statusQwo",
		fldContainsCls: "cls",
		fldContainsStrList: "strlist",
		fldContainsMulti: "multi",
		fldContainsSeparator: "$*$",
		formName : "formName",
		objBaseBegin: "tblEntity",
		operInsert: "insert",
		operUpdate: "update",
		operArchive: "archive",
		restPartUrl: '/rest/lsa/2.0/',
		restSystemPartUrl: '/rest/api/2/',
		arClsHardcode: [],
		filterExprFld: "filter",
		exprYes: "YES",
		exprNo: "NO",
		tblFormNum: "res",
		tblEntityQueryIssue: "tblEntityRes",
		tblEntityResTpIssue: "tblEntityResTp",
		tblNameResTpIssue: "resTp",
		tblEntityResTemplate : "tblEntityResTemplate",
		tblResTemplateName : "resTemplate",
		tblResTp : "resTp",
		tblEntityResource: "tblEntityResource",
		tblEntityModul: "tblEntityModul",
		tblEntityFunction: "tblEntityFunction",
		fldClsResource : "strlistResource",
		fldClsModul : "strlistModul",
		fldClsFunction : "strlistFunction",
		colTemplateNum: 8,
		colTemplateName: "template",
		visibleFld: "visible",
		hide: 0,
		show: 1,		
		profileNo: "Нет",
		profileDefaultNum: 0,
		statusDefaultName: "Новый",
		resourceTypeOther: "Другое",
		resourceTypeOtherResTp: ["Modul", "Function"]
}

// Переменные
CfldGrid.Vars = {
	javaConstant: {},
	issueSummary : {},
	issueAssigneeId	: {},
	employeeId : {},
	maxResTp: 4,
	arTbl : [
	     {"key" : "tblEntityResTp", "name" : "resTp", "order" : 40, 
	    	 "formSelOuter" : "" , 
	    	 "filterOuter" : "" ,"fields": 
			 [
				{"id" : "id", "formDataType" : "int", "name" : "ИД", "order" : 0, "filterName" : "", "formName": "resTpId", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "cdStr", "formDataType" : "String", "name" : "Структурный код", "order" : 0, "filterName" : "", "formName": "resTpСdStr", "multiSelectField" : "", "filter" : "", "visible" : 0},				
				{"id" : "name", "formDataType" : "String", "name" : "Наименование", "order" : 20, "filterName" : "", "formName": "resTpName", "multiSelectField" : "name", "filter" : "", "visible" : 0},
				{"id" : "strlistResource", "formDataType" : "List", "name" : "Ресурс", "order" : 30, "filterName" : "", "formName": "resTpResource", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "strlistModul", "formDataType" : "List", "name" : "Модуль", "order" : 40, "filterName" : "", "formName": "resTpModul", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "strlistFunction", "formDataType" : "List", "name" : "Функция", "order" : 50, "filterName" : "", "formName": "resTpFunction", "multiSelectField" : "", "filter" : "", "visible" : 0},						
				{"id" : "comment", "formDataType" : "String", "name" : "Описание", "order" : 60, "filterName" : "", "formName": "resTpComment", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "status", "formDataType" : "String", "name" : "Статус", "order" : 70,
					"formName": "resTpStatus", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "template", "formDataType" : "String", "name" : "Шаблон", "order" : 80,
					"formName": "resTpTemplate", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "loginExpert", "formDataType" : "String", "name" : "Эксперт", "order" : 90,
					"formName": "resTpLoginExpert", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "loginOwner", "formDataType" : "String", "name" : "Владелец", "order" : 100,
					"formName": "resTpLoginOwner", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "loginSpecialist", "formDataType" : "String", "name" : "Специалист", "order" : 110,
					"formName": "resTpLoginSpecialist", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "type", "formDataType" : "int", "name" : "Тип ресурса", "order" : 120,
					"formName": "resTpType", "multiSelectField" : "", "filter" : "", "visible" : 0}					
	    	]
	     },		 
	     {"key" : "tblEntityResTp1", "name" : "resTp1", "order" : 50, 
	    	 "formSelOuter" : "" , 
	    	 "filterOuter" : "" ,"fields": 
			 [
				{"id" : "id", "formDataType" : "int", "name" : "ИД", "order" : 0, "filterName" : "", "formName": "resTp1Id", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "cdStr", "formDataType" : "String", "name" : "Структурный код", "order" : 0, "filterName" : "", "formName": "resTp1СdStr", "multiSelectField" : "", "filter" : "", "visible" : 0},				
				{"id" : "name", "formDataType" : "String", "name" : "Наименование", "order" : 20, "filterName" : "", "formName": "resTp1Name", "multiSelectField" : "name", "filter" : "", "visible" : 0},
				{"id" : "strlistResource1", "formDataType" : "List", "name" : "Ресурс", "order" : 30, "filterName" : "", "formName": "resTp1Resource1Name", "multiSelectField" : "", "filter" : "id=Modul1.parentId;resTp1Modul1Name;resTp1Modul1Name", "visible" : 1},
				{"id" : "strlistModul1", "formDataType" : "List", "name" : "Модуль", "order" : 40, "filterName" : "", "formName": "resTp1Modul1Name", "multiSelectField" : "", "filter" : "id=Function1.parentId;resTp1Function1Name", "visible" : 1},
				{"id" : "strlistFunction1", "formDataType" : "List", "name" : "Функция", "order" : 50, "filterName" : "", "formName": "resTp1Function1Name", "multiSelectField" : "", "filter" : "", "visible" : 1},						
				{"id" : "comment", "formDataType" : "String", "name" : "Описание", "order" : 60, "filterName" : "", "formName": "resTp1Comment", "multiSelectField" : "", "filter" : "", "visible" : 1},
				{"id" : "status", "formDataType" : "String", "name" : "Статус", "order" : 70,
					"formName": "resTp1Status", "multiSelectField" : "", "filter" : "", "visible" : 1},
				{"id" : "template", "formDataType" : "String", "name" : "Шаблон", "order" : 80,
					"formName": "resTp1Template", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "loginExpert", "formDataType" : "String", "name" : "Эксперт", "order" : 90,
					"formName": "resTp1LoginExpert", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "loginOwner", "formDataType" : "String", "name" : "Владелец", "order" : 100,
					"formName": "resTp1LoginOwner", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "loginSpecialist", "formDataType" : "String", "name" : "Специалист", "order" : 110,
					"formName": "resTp1LoginSpecialist", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "type", "formDataType" : "int", "name" : "Тип ресурса", "order" : 120,
					"formName": "resTp1Type", "multiSelectField" : "", "filter" : "", "visible" : 0}					
	    	]
	     },
	     {"key" : "tblEntityResTp2", "name" : "resTp2", "order" : 51, 
	    	 "formSelOuter" : "" , 
	    	 "filterOuter" : "" ,"fields": 
			 [
				{"id" : "id", "formDataType" : "int", "name" : "ИД", "order" : 0, "filterName" : "", "formName": "resTp2Id", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "cdStr", "formDataType" : "String", "name" : "Структурный код", "order" : 0, "filterName" : "", "formName": "resTp2СdStr", "multiSelectField" : "", "filter" : "", "visible" : 0},				
				{"id" : "name", "formDataType" : "String", "name" : "Наименование", "order" : 20, "filterName" : "", "formName": "resTp2Name", "multiSelectField" : "name", "filter" : "", "visible" : 0},
				{"id" : "strlistResource2", "formDataType" : "List", "name" : "Ресурс", "order" : 30, "filterName" : "", "formName": "resTp2Resource2Name", "multiSelectField" : "", "filter" : "id=Modul2.parentId;resTp2Modul2Name;resTp2Modul2Name", "visible" : 1},
				{"id" : "strlistModul2", "formDataType" : "List", "name" : "Модуль", "order" : 40, "filterName" : "", "formName": "resTp2Modul2Name", "multiSelectField" : "", "filter" : "id=Function2.parentId;resTp2Function2Name", "visible" : 1},
				{"id" : "strlistFunction2", "formDataType" : "List", "name" : "Функция", "order" : 50, "filterName" : "", "formName": "resTp2Function2Name", "multiSelectField" : "", "filter" : "", "visible" : 1},						
				{"id" : "comment", "formDataType" : "String", "name" : "Описание", "order" : 60, "filterName" : "", "formName": "resTp2Comment", "multiSelectField" : "", "filter" : "", "visible" : 1},
				{"id" : "status", "formDataType" : "String", "name" : "Статус", "order" : 70,
					"formName": "resTp2Status", "multiSelectField" : "", "filter" : "", "visible" : 1},
				{"id" : "template", "formDataType" : "String", "name" : "Шаблон", "order" : 80,
					"formName": "resTp2Template", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "loginExpert", "formDataType" : "String", "name" : "Эксперт", "order" : 90,
					"formName": "resTp2LoginExpert", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "loginOwner", "formDataType" : "String", "name" : "Владелец", "order" : 100,
					"formName": "resTp2LoginOwner", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "loginSpecialist", "formDataType" : "String", "name" : "Специалист", "order" : 110,
					"formName": "resTp2LoginSpecialist", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "type", "formDataType" : "int", "name" : "Тип ресурса", "order" : 120,
					"formName": "resTp2Type", "multiSelectField" : "", "filter" : "", "visible" : 0}					
	    	]
	     },		
	     {"key" : "tblEntityResTp3", "name" : "resTp3", "order" : 52, 
	    	 "formSelOuter" : "" , 
	    	 "filterOuter" : "" ,"fields": 
			 [
				{"id" : "id", "formDataType" : "int", "name" : "ИД", "order" : 0, "filterName" : "", "formName": "resTp3Id", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "cdStr", "formDataType" : "String", "name" : "Структурный код", "order" : 0, "filterName" : "", "formName": "resTp3СdStr", "multiSelectField" : "", "filter" : "", "visible" : 0},				
				{"id" : "name", "formDataType" : "String", "name" : "Наименование", "order" : 20, "filterName" : "", "formName": "resTp3Name", "multiSelectField" : "name", "filter" : "", "visible" : 0},
				{"id" : "strlistResource3", "formDataType" : "List", "name" : "Ресурс", "order" : 30, "filterName" : "", "formName": "resTp3Resource3Name", "multiSelectField" : "", "filter" : "id=Modul3.parentId;resTp3Modul3Name;resTp3Modul3Name", "visible" : 1},
				{"id" : "strlistModul3", "formDataType" : "List", "name" : "Модуль", "order" : 40, "filterName" : "", "formName": "resTp3Modul3Name", "multiSelectField" : "", "filter" : "id=Function3.parentId;resTp3Function3Name", "visible" : 1},
				{"id" : "strlistFunction3", "formDataType" : "List", "name" : "Функция", "order" : 50, "filterName" : "", "formName": "resTp3Function3Name", "multiSelectField" : "", "filter" : "", "visible" : 1},						
				{"id" : "comment", "formDataType" : "String", "name" : "Описание", "order" : 60, "filterName" : "", "formName": "resTp3Comment", "multiSelectField" : "", "filter" : "", "visible" : 1},
				{"id" : "status", "formDataType" : "String", "name" : "Статус", "order" : 70,
					"formName": "resTp3Status", "multiSelectField" : "", "filter" : "", "visible" : 1},
				{"id" : "template", "formDataType" : "String", "name" : "Шаблон", "order" : 80,
					"formName": "resTp3Template", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "loginExpert", "formDataType" : "String", "name" : "Эксперт", "order" : 90,
					"formName": "resTp3LoginExpert", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "loginOwner", "formDataType" : "String", "name" : "Владелец", "order" : 100,
					"formName": "resTp3LoginOwner", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "loginSpecialist", "formDataType" : "String", "name" : "Специалист", "order" : 110,
					"formName": "resTp3LoginSpecialist", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "type", "formDataType" : "int", "name" : "Тип ресурса", "order" : 120,
					"formName": "resTp3Type", "multiSelectField" : "", "filter" : "", "visible" : 0}					
	    	]
	     },		
	     {"key" : "tblEntityResTp4", "name" : "resTp4", "order" : 53, 
	    	 "formSelOuter" : "" , 
	    	 "filterOuter" : "" ,"fields": 
			 [
				{"id" : "id", "formDataType" : "int", "name" : "ИД", "order" : 0, "filterName" : "", "formName": "resTp4Id", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "cdStr", "formDataType" : "String", "name" : "Структурный код", "order" : 0, "filterName" : "", "formName": "resTp4СdStr", "multiSelectField" : "", "filter" : "", "visible" : 0},				
				{"id" : "name", "formDataType" : "String", "name" : "Наименование", "order" : 20, "filterName" : "", "formName": "resTp4Name", "multiSelectField" : "name", "filter" : "", "visible" : 0},
				{"id" : "strlistResource4", "formDataType" : "List", "name" : "Ресурс", "order" : 30, "filterName" : "", "formName": "resTp4Resource4Name", "multiSelectField" : "", "filter" : "id=Modul4.parentId;resTp4Modul4Name;resTp4Modul4Name", "visible" : 1},
				{"id" : "strlistModul4", "formDataType" : "List", "name" : "Модуль", "order" : 40, "filterName" : "", "formName": "resTp4Modul4Name", "multiSelectField" : "", "filter" : "id=Function4.parentId;resTp4Function4Name", "visible" : 1},
				{"id" : "strlistFunction4", "formDataType" : "List", "name" : "Функция", "order" : 50, "filterName" : "", "formName": "resTp4Function4Name", "multiSelectField" : "", "filter" : "", "visible" : 1},						
				{"id" : "comment", "formDataType" : "String", "name" : "Описание", "order" : 60, "filterName" : "", "formName": "resTp4Comment", "multiSelectField" : "", "filter" : "", "visible" : 1},
				{"id" : "status", "formDataType" : "String", "name" : "Статус", "order" : 70,
					"formName": "resTp4Status", "multiSelectField" : "", "filter" : "", "visible" : 1},
				{"id" : "template", "formDataType" : "String", "name" : "Шаблон", "order" : 80,
					"formName": "resTp4Template", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "loginExpert", "formDataType" : "String", "name" : "Эксперт", "order" : 90,
					"formName": "resTp4LoginExpert", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "loginOwner", "formDataType" : "String", "name" : "Владелец", "order" : 100,
					"formName": "resTp4LoginOwner", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "loginSpecialist", "formDataType" : "String", "name" : "Специалист", "order" : 110,
					"formName": "resTp4LoginSpecialist", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "type", "formDataType" : "int", "name" : "Тип ресурса", "order" : 120,
					"formName": "resTp4Type", "multiSelectField" : "", "filter" : "", "visible" : 0}					
	    	]
	     },				 
	     {"key" : "tblEntityRes", "name" : "res", "order" : 0, 
	    	 "formSelOuter" : "" , 
	    	 "filterOuter" : "" ,"fields": 
			[
				{"id" : "cdStr", "formDataType" : "String", "name" : "Структурный код", "order" : 5, "filterName" : "", "formName": "resCdStr", "multiSelectField" : "", "filter" : "", "visible" : 0},				
				{"id" : "name", "formDataType" : "String", "name" : "Наименование", "order" : 10, "filterName" : "", "formName": "resName", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "resource", "formDataType" : "String", "name" : "Ресурс", "order" : 20, "filterName" : "", "formName": "resResource", "multiSelectField" : "", "filter" : "", "visible" : 0},								
				{"id" : "modul", "formDataType" : "String", "name" : "Модуль", "order" : 30, "filterName" : "", "formName": "resModul", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "function", "formDataType" : "String", "name" : "Функция", "order" : 40, "filterName" : "", "formName": "resFunction", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "comment", "formDataType" : "String", "name" : "Описание", "order" : 50, "filterName" : "", "formName": "resComment", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "type", "formDataType" : "String", "name" : "Тип ресурса", "order" : 60, "filterName" : "", "formName": "resType", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "loginExpert", "formDataType" : "String", "name" : "Эксперт", "order" : 70,
					"formName": "resLoginExpert", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "loginOwner", "formDataType" : "String", "name" : "Владелец", "order" : 80,
					"formName": "resLoginOwner", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "loginSpecialist", "formDataType" : "String", "name" : "Специалист", "order" : 90,
					"formName": "resLoginSpecialist", "multiSelectField" : "", "filter" : "", "visible" : 0}
				
	    	]
	     },
	     {"key" : "tblEntityResource1", "name" : "resource1", "order" : 10, 
	    	 "formSelOuter" : "" , 
	    	 "filterOuter" : "" ,"fields": 
			[
				{"id" : "id", "formDataType" : "String", "name" : "Структурный код", "order" : 5, "filterName" : "", "formName": "resourceCdStr", "multiSelectField" : "", "filter" : "", "visible" : 0},				
				{"id" : "name", "formDataType" : "String", "name" : "Наименование", "order" : 10, "filterName" : "", "formName": "resourceName", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "parentId", "formDataType" : "String", "name" : "Структурный код - родитель", "order" : 15, "filterName" : "", "formName": "resourceParent", "multiSelectField" : "", "filter" : "", "visible" : 0}				
	    	]
	     },
	     {"key" : "tblEntityModul1", "name" : "modul1", "order" : 11, 
	    	 "formSelOuter" : "" , 
	    	 "filterOuter" : "" ,"fields": 
			[
				{"id" : "id", "formDataType" : "String", "name" : "Структурный код", "order" : 5, "filterName" : "", "formName": "modulCdStr", "multiSelectField" : "", "filter" : "", "visible" : 0},				
				{"id" : "name", "formDataType" : "String", "name" : "Наименование", "order" : 10, "filterName" : "", "formName": "modulName", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "parentId", "formDataType" : "String", "name" : "Структурный код - родитель", "order" : 15, "filterName" : "", "formName": "modulParent", "multiSelectField" : "", "filter" : "", "visible" : 0}		
	    	]
	     },	
	     {"key" : "tblEntityFunction1", "name" : "function1", "order" : 12, 
	    	 "formSelOuter" : "" , 
	    	 "filterOuter" : "" ,"fields": 
			[
				{"id" : "id", "formDataType" : "String", "name" : "Структурный код", "order" : 5, "filterName" : "", "formName": "functionCdStr", "multiSelectField" : "", "filter" : "", "visible" : 0},				
				{"id" : "name", "formDataType" : "String", "name" : "Наименование", "order" : 10, "filterName" : "", "formName": "functionName", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "parentId", "formDataType" : "String", "name" : "Структурный код - родитель", "order" : 15, "filterName" : "", "formName": "functionParent", "multiSelectField" : "", "filter" : "", "visible" : 0}		
	    	]
	     },
	     {"key" : "tblEntityResource2", "name" : "resource2", "order" : 20, 
	    	 "formSelOuter" : "" , 
	    	 "filterOuter" : "" ,"fields": 
			[
				{"id" : "id", "formDataType" : "String", "name" : "Структурный код", "order" : 5, "filterName" : "", "formName": "resourceCdStr", "multiSelectField" : "", "filter" : "", "visible" : 0},				
				{"id" : "name", "formDataType" : "String", "name" : "Наименование", "order" : 10, "filterName" : "", "formName": "resourceName", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "parentId", "formDataType" : "String", "name" : "Структурный код - родитель", "order" : 15, "filterName" : "", "formName": "resourceParent", "multiSelectField" : "", "filter" : "", "visible" : 0}				
	    	]
	     },
	     {"key" : "tblEntityModul2", "name" : "modul2", "order" : 21, 
	    	 "formSelOuter" : "" , 
	    	 "filterOuter" : "" ,"fields": 
			[
				{"id" : "id", "formDataType" : "String", "name" : "Структурный код", "order" : 5, "filterName" : "", "formName": "modulCdStr", "multiSelectField" : "", "filter" : "", "visible" : 0},				
				{"id" : "name", "formDataType" : "String", "name" : "Наименование", "order" : 10, "filterName" : "", "formName": "modulName", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "parentId", "formDataType" : "String", "name" : "Структурный код - родитель", "order" : 15, "filterName" : "", "formName": "modulParent", "multiSelectField" : "", "filter" : "", "visible" : 0}		
	    	]
	     },	
	     {"key" : "tblEntityFunction2", "name" : "function2", "order" : 22, 
	    	 "formSelOuter" : "" , 
	    	 "filterOuter" : "" ,"fields": 
			[
				{"id" : "id", "formDataType" : "String", "name" : "Структурный код", "order" : 5, "filterName" : "", "formName": "functionCdStr", "multiSelectField" : "", "filter" : "", "visible" : 0},				
				{"id" : "name", "formDataType" : "String", "name" : "Наименование", "order" : 10, "filterName" : "", "formName": "functionName", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "parentId", "formDataType" : "String", "name" : "Структурный код - родитель", "order" : 15, "filterName" : "", "formName": "functionParent", "multiSelectField" : "", "filter" : "", "visible" : 0}		
	    	]
	     },
	     {"key" : "tblEntityResource3", "name" : "resource3", "order" : 30, 
	    	 "formSelOuter" : "" , 
	    	 "filterOuter" : "" ,"fields": 
			[
				{"id" : "id", "formDataType" : "String", "name" : "Структурный код", "order" : 5, "filterName" : "", "formName": "resourceCdStr", "multiSelectField" : "", "filter" : "", "visible" : 0},				
				{"id" : "name", "formDataType" : "String", "name" : "Наименование", "order" : 10, "filterName" : "", "formName": "resourceName", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "parentId", "formDataType" : "String", "name" : "Структурный код - родитель", "order" : 15, "filterName" : "", "formName": "resourceParent", "multiSelectField" : "", "filter" : "", "visible" : 0}				
	    	]
	     },
	     {"key" : "tblEntityModul3", "name" : "modul3", "order" : 31, 
	    	 "formSelOuter" : "" , 
	    	 "filterOuter" : "" ,"fields": 
			[
				{"id" : "id", "formDataType" : "String", "name" : "Структурный код", "order" : 5, "filterName" : "", "formName": "modulCdStr", "multiSelectField" : "", "filter" : "", "visible" : 0},				
				{"id" : "name", "formDataType" : "String", "name" : "Наименование", "order" : 10, "filterName" : "", "formName": "modulName", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "parentId", "formDataType" : "String", "name" : "Структурный код - родитель", "order" : 15, "filterName" : "", "formName": "modulParent", "multiSelectField" : "", "filter" : "", "visible" : 0}		
	    	]
	     },	
	     {"key" : "tblEntityFunction3", "name" : "function3", "order" : 32, 
	    	 "formSelOuter" : "" , 
	    	 "filterOuter" : "" ,"fields": 
			[
				{"id" : "id", "formDataType" : "String", "name" : "Структурный код", "order" : 5, "filterName" : "", "formName": "functionCdStr", "multiSelectField" : "", "filter" : "", "visible" : 0},				
				{"id" : "name", "formDataType" : "String", "name" : "Наименование", "order" : 10, "filterName" : "", "formName": "functionName", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "parentId", "formDataType" : "String", "name" : "Структурный код - родитель", "order" : 15, "filterName" : "", "formName": "functionParent", "multiSelectField" : "", "filter" : "", "visible" : 0}		
	    	]
	     },
	     {"key" : "tblEntityResource4", "name" : "resource4", "order" : 40, 
	    	 "formSelOuter" : "" , 
	    	 "filterOuter" : "" ,"fields": 
			[
				{"id" : "id", "formDataType" : "String", "name" : "Структурный код", "order" : 5, "filterName" : "", "formName": "resourceCdStr", "multiSelectField" : "", "filter" : "", "visible" : 0},				
				{"id" : "name", "formDataType" : "String", "name" : "Наименование", "order" : 10, "filterName" : "", "formName": "resourceName", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "parentId", "formDataType" : "String", "name" : "Структурный код - родитель", "order" : 15, "filterName" : "", "formName": "resourceParent", "multiSelectField" : "", "filter" : "", "visible" : 0}				
	    	]
	     },
	     {"key" : "tblEntityModul4", "name" : "modul4", "order" : 41, 
	    	 "formSelOuter" : "" , 
	    	 "filterOuter" : "" ,"fields": 
			[
				{"id" : "id", "formDataType" : "String", "name" : "Структурный код", "order" : 5, "filterName" : "", "formName": "modulCdStr", "multiSelectField" : "", "filter" : "", "visible" : 0},				
				{"id" : "name", "formDataType" : "String", "name" : "Наименование", "order" : 10, "filterName" : "", "formName": "modulName", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "parentId", "formDataType" : "String", "name" : "Структурный код - родитель", "order" : 15, "filterName" : "", "formName": "modulParent", "multiSelectField" : "", "filter" : "", "visible" : 0}		
	    	]
	     },	
	     {"key" : "tblEntityFunction4", "name" : "function4", "order" : 42, 
	    	 "formSelOuter" : "" , 
	    	 "filterOuter" : "" ,"fields": 
			[
				{"id" : "id", "formDataType" : "String", "name" : "Структурный код", "order" : 5, "filterName" : "", "formName": "functionCdStr", "multiSelectField" : "", "filter" : "", "visible" : 0},				
				{"id" : "name", "formDataType" : "String", "name" : "Наименование", "order" : 10, "filterName" : "", "formName": "functionName", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "parentId", "formDataType" : "String", "name" : "Структурный код - родитель", "order" : 15, "filterName" : "", "formName": "functionParent", "multiSelectField" : "", "filter" : "", "visible" : 0}		
	    	]
	     },		 
	     {"key" : "tblEntityProfile", "name" : "profile", "order" : 5, 
	    	 "formSelOuter" : "" , 
	    	 "filterOuter" : "" ,"fields": 
			[
				{"id" : "id", "formDataType" : "int", "name" : "ИД", "order" : 0, "filterName" : "", "formName": "profileId", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "name", "formDataType" : "String", "name" : "Наименование", "order" : 20, "filterName" : "", "formName": "profileName", "multiSelectField" : "", "filter" : "", "visible" : 1}				
	    	]
	     },	
	     {"key" : "tblEntityResTemplate", "name" : "resTemplate", "order" : 15, 
	    	 "formSelOuter" : "" , 
	    	 "filterOuter" : "" ,"fields": 
			 [
				{"id" : "id", "formDataType" : "int", "name" : "ИД", "order" : 0, "filterName" : "", "formName": "resTemplateId", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "cdStr", "formDataType" : "String", "name" : "Структурный код", "order" : 0, "filterName" : "", "formName": "resTemplateСdStr", "multiSelectField" : "", "filter" : "", "visible" : 0},				
				{"id" : "name", "formDataType" : "String", "name" : "Наименование", "order" : 20, "filterName" : "", "formName": "resTemplateName", "multiSelectField" : "name", "filter" : "", "visible" : 0},
				{"id" : "strlistResource", "formDataType" : "List", "name" : "Ресурс", "order" : 30, "filterName" : "", "formName": "resTemplateResource", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "strlistModul", "formDataType" : "List", "name" : "Модуль", "order" : 40, "filterName" : "", "formName": "resTemplateModul", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "strlistFunction", "formDataType" : "List", "name" : "Функция", "order" : 50, "filterName" : "", "formName": "resTemplateFunction", "multiSelectField" : "", "filter" : "", "visible" : 0},						
				{"id" : "comment", "formDataType" : "String", "name" : "Описание", "order" : 60, "filterName" : "", "formName": "resTemplateComment", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "status", "formDataType" : "String", "name" : "Статус", "order" : 70,
					"formName": "resTemplateStatus", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "template", "formDataType" : "String", "name" : "Шаблон", "order" : 80,
					"formName": "resTemplateTemplate", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "loginExpert", "formDataType" : "String", "name" : "Эксперт", "order" : 90,
					"formName": "resTemplateLoginExpert", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "loginOwner", "formDataType" : "String", "name" : "Владелец", "order" : 100,
					"formName": "resTemplateLoginOwner", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "loginSpecialist", "formDataType" : "String", "name" : "Специалист", "order" : 110,
					"formName": "resTemplateLoginSpecialist", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "type", "formDataType" : "int", "name" : "Тип ресурса", "order" : 120,
					"formName": "resTemplateType", "multiSelectField" : "", "filter" : "", "visible" : 0}					
	    	]
	     },	
	     {"key" : "tblEntityResTemplate1", "name" : "resTemplate1", "order" : 16, 
	    	 "formSelOuter" : "" , 
	    	 "filterOuter" : "" ,"fields": 
			 [
				{"id" : "id", "formDataType" : "int", "name" : "ИД", "order" : 0, "filterName" : "", "formName": "resTemplate1Id", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "cdStr", "formDataType" : "String", "name" : "Структурный код", "order" : 0, "filterName" : "", "formName": "resTemplate1СdStr", "multiSelectField" : "", "filter" : "", "visible" : 0},				
				{"id" : "name", "formDataType" : "String", "name" : "Наименование", "order" : 20, "filterName" : "", "formName": "resTemplate1Name", "multiSelectField" : "name", "filter" : "", "visible" : 0},
				{"id" : "strlistResource1", "formDataType" : "List", "name" : "Ресурс", "order" : 30, "filterName" : "", "formName": "resTemplate1Resource", "multiSelectField" : "", "filter" : "", "visible" : 1},
				{"id" : "strlistModul1", "formDataType" : "List", "name" : "Модуль", "order" : 40, "filterName" : "", "formName": "resTemplate1Modul", "multiSelectField" : "", "filter" : "", "visible" : 1},
				{"id" : "strlistFunction1", "formDataType" : "List", "name" : "Функция", "order" : 50, "filterName" : "", "formName": "resTemplate1Function", "multiSelectField" : "", "filter" : "", "visible" : 1},						
				{"id" : "comment", "formDataType" : "String", "name" : "Описание", "order" : 60, "filterName" : "", "formName": "resTemplate1Comment", "multiSelectField" : "", "filter" : "", "visible" : 1},
				{"id" : "status", "formDataType" : "String", "name" : "Статус", "order" : 70,
					"formName": "resTemplate1Status", "multiSelectField" : "", "filter" : "", "visible" : 1},
				{"id" : "template1", "formDataType" : "String", "name" : "Шаблон", "order" : 80,
					"formName": "resTemplate1Template", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "loginExpert", "formDataType" : "String", "name" : "Эксперт", "order" : 90,
					"formName": "resTemplate1LoginExpert", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "loginOwner", "formDataType" : "String", "name" : "Владелец", "order" : 100,
					"formName": "resTemplate1LoginOwner", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "loginSpecialist", "formDataType" : "String", "name" : "Специалист", "order" : 110,
					"formName": "resTemplate1LoginSpecialist", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "type", "formDataType" : "int", "name" : "Тип ресурса", "order" : 120,
					"formName": "resTemplate1Type", "multiSelectField" : "", "filter" : "", "visible" : 0}					
	    	]
	     },	
	     {"key" : "tblEntityResTemplate2", "name" : "resTemplate2", "order" : 17, 
	    	 "formSelOuter" : "" , 
	    	 "filterOuter" : "" ,"fields": 
			 [
				{"id" : "id", "formDataType" : "int", "name" : "ИД", "order" : 0, "filterName" : "", "formName": "resTemplate2Id", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "cdStr", "formDataType" : "String", "name" : "Структурный код", "order" : 0, "filterName" : "", "formName": "resTemplate2СdStr", "multiSelectField" : "", "filter" : "", "visible" : 0},				
				{"id" : "name", "formDataType" : "String", "name" : "Наименование", "order" : 20, "filterName" : "", "formName": "resTemplate2Name", "multiSelectField" : "name", "filter" : "", "visible" : 0},
				{"id" : "strlistResource2", "formDataType" : "List", "name" : "Ресурс", "order" : 30, "filterName" : "", "formName": "resTemplate2Resource", "multiSelectField" : "", "filter" : "", "visible" : 1},
				{"id" : "strlistModul2", "formDataType" : "List", "name" : "Модуль", "order" : 40, "filterName" : "", "formName": "resTemplate2Modul", "multiSelectField" : "", "filter" : "", "visible" : 1},
				{"id" : "strlistFunction2", "formDataType" : "List", "name" : "Функция", "order" : 50, "filterName" : "", "formName": "resTemplate2Function", "multiSelectField" : "", "filter" : "", "visible" : 1},						
				{"id" : "comment", "formDataType" : "String", "name" : "Описание", "order" : 60, "filterName" : "", "formName": "resTemplate2Comment", "multiSelectField" : "", "filter" : "", "visible" : 1},
				{"id" : "status", "formDataType" : "String", "name" : "Статус", "order" : 70,
					"formName": "resTemplate2Status", "multiSelectField" : "", "filter" : "", "visible" : 1},
				{"id" : "template2", "formDataType" : "String", "name" : "Шаблон", "order" : 80,
					"formName": "resTemplate2Template", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "loginExpert", "formDataType" : "String", "name" : "Эксперт", "order" : 90,
					"formName": "resTemplate2LoginExpert", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "loginOwner", "formDataType" : "String", "name" : "Владелец", "order" : 100,
					"formName": "resTemplate2LoginOwner", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "loginSpecialist", "formDataType" : "String", "name" : "Специалист", "order" : 110,
					"formName": "resTemplate2LoginSpecialist", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "type", "formDataType" : "int", "name" : "Тип ресурса", "order" : 120,
					"formName": "resTemplate2Type", "multiSelectField" : "", "filter" : "", "visible" : 0}					
	    	]
	     },	
	     {"key" : "tblEntityResTemplate3", "name" : "resTemplate3", "order" : 18, 
	    	 "formSelOuter" : "" , 
	    	 "filterOuter" : "" ,"fields": 
			 [
				{"id" : "id", "formDataType" : "int", "name" : "ИД", "order" : 0, "filterName" : "", "formName": "resTemplate3Id", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "cdStr", "formDataType" : "String", "name" : "Структурный код", "order" : 0, "filterName" : "", "formName": "resTemplate3СdStr", "multiSelectField" : "", "filter" : "", "visible" : 0},				
				{"id" : "name", "formDataType" : "String", "name" : "Наименование", "order" : 20, "filterName" : "", "formName": "resTemplate3Name", "multiSelectField" : "name", "filter" : "", "visible" : 0},
				{"id" : "strlistResource3", "formDataType" : "List", "name" : "Ресурс", "order" : 30, "filterName" : "", "formName": "resTemplate3Resource", "multiSelectField" : "", "filter" : "", "visible" : 1},
				{"id" : "strlistModul3", "formDataType" : "List", "name" : "Модуль", "order" : 40, "filterName" : "", "formName": "resTemplate3Modul", "multiSelectField" : "", "filter" : "", "visible" : 1},
				{"id" : "strlistFunction3", "formDataType" : "List", "name" : "Функция", "order" : 50, "filterName" : "", "formName": "resTemplate3Function", "multiSelectField" : "", "filter" : "", "visible" : 1},						
				{"id" : "comment", "formDataType" : "String", "name" : "Описание", "order" : 60, "filterName" : "", "formName": "resTemplate3Comment", "multiSelectField" : "", "filter" : "", "visible" : 1},
				{"id" : "status", "formDataType" : "String", "name" : "Статус", "order" : 70,
					"formName": "resTemplate3Status", "multiSelectField" : "", "filter" : "", "visible" : 1},
				{"id" : "template3", "formDataType" : "String", "name" : "Шаблон", "order" : 80,
					"formName": "resTemplate3Template", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "loginExpert", "formDataType" : "String", "name" : "Эксперт", "order" : 90,
					"formName": "resTemplate3LoginExpert", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "loginOwner", "formDataType" : "String", "name" : "Владелец", "order" : 100,
					"formName": "resTemplate3LoginOwner", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "loginSpecialist", "formDataType" : "String", "name" : "Специалист", "order" : 110,
					"formName": "resTemplate3LoginSpecialist", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "type", "formDataType" : "int", "name" : "Тип ресурса", "order" : 120,
					"formName": "resTemplate3Type", "multiSelectField" : "", "filter" : "", "visible" : 0}					
	    	]
	     },	
	     {"key" : "tblEntityResTemplate4", "name" : "resTemplate4", "order" : 19, 
	    	 "formSelOuter" : "" , 
	    	 "filterOuter" : "" ,"fields": 
			 [
				{"id" : "id", "formDataType" : "int", "name" : "ИД", "order" : 0, "filterName" : "", "formName": "resTemplate4Id", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "cdStr", "formDataType" : "String", "name" : "Структурный код", "order" : 0, "filterName" : "", "formName": "resTemplate4СdStr", "multiSelectField" : "", "filter" : "", "visible" : 0},				
				{"id" : "name", "formDataType" : "String", "name" : "Наименование", "order" : 20, "filterName" : "", "formName": "resTemplate4Name", "multiSelectField" : "name", "filter" : "", "visible" : 0},
				{"id" : "strlistResource4", "formDataType" : "List", "name" : "Ресурс", "order" : 30, "filterName" : "", "formName": "resTemplate4Resource", "multiSelectField" : "", "filter" : "", "visible" : 1},
				{"id" : "strlistModul4", "formDataType" : "List", "name" : "Модуль", "order" : 40, "filterName" : "", "formName": "resTemplate4Modul", "multiSelectField" : "", "filter" : "", "visible" : 1},
				{"id" : "strlistFunction4", "formDataType" : "List", "name" : "Функция", "order" : 50, "filterName" : "", "formName": "resTemplate4Function", "multiSelectField" : "", "filter" : "", "visible" : 1},						
				{"id" : "comment", "formDataType" : "String", "name" : "Описание", "order" : 60, "filterName" : "", "formName": "resTemplate4Comment", "multiSelectField" : "", "filter" : "", "visible" : 1},
				{"id" : "status", "formDataType" : "String", "name" : "Статус", "order" : 70,
					"formName": "resTemplate4Status", "multiSelectField" : "", "filter" : "", "visible" : 1},
				{"id" : "template4", "formDataType" : "String", "name" : "Шаблон", "order" : 80,
					"formName": "resTemplate4Template", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "loginExpert", "formDataType" : "String", "name" : "Эксперт", "order" : 90,
					"formName": "resTemplate4LoginExpert", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "loginOwner", "formDataType" : "String", "name" : "Владелец", "order" : 100,
					"formName": "resTemplate4LoginOwner", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "loginSpecialist", "formDataType" : "String", "name" : "Специалист", "order" : 110,
					"formName": "resTemplate4LoginSpecialist", "multiSelectField" : "", "filter" : "", "visible" : 0},
				{"id" : "type", "formDataType" : "int", "name" : "Тип ресурса", "order" : 120,
					"formName": "resTemplate4Type", "multiSelectField" : "", "filter" : "", "visible" : 0}					
	    	]
	     }	   	   	   	     
	    ],			 
	    restBaseUrl : "",
	    filterStatusCurCl: "showArchiveRecords",
		urlParams : {}
	}