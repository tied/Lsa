// Пространство имен BaseUtils
BaseUtils = {};


// Преобразование ProperCase только для первой буквы
BaseUtils.toProperCase = function (s)
{
	var str = s.substr(0, 1).toUpperCase()+s.substr(1);
    return str;
};

// Нахождение объекта массива по имени свойства и его значению
BaseUtils.findElOfArByProp = function(ar, propName, propValue) {
	var objFnd = null;
	for (var i = 0 ; i < ar.length ; i++) {
		if (ar[i][propName] == propValue) {
			objFnd = ar[i];
			break;
		}
	}
	if (objFnd == null) {
		var er = "Error finding poperty "+propName + " value " + propValue +" in masssive " + ar + "!";
		alert(er);
        AJS.logError(er);
        AJS.log(er);			
		return;
	}	
	return objFnd;
};

//Поиск значения свойства объекта в массиве по заданному свойству
BaseUtils.findPropOfArByProp = function(ar, srcVal, srcPropName, trgPropName) {
	var trgVal = null;
	for (var i = 0; i < ar.length; i++) {
		if (ar[i][srcPropName] == srcVal) {
			trgVal = ar[i][trgPropName];
		}
	}
	return trgVal;
};

BaseUtils.bind = function (fn, scope, args, appendArgs) {
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
};