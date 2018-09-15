var RL = RL || {};

RL.after = function (referenceNode, newNode) {
    referenceNode.parentNode.insertBefore(newNode, referenceNode.nextSibling);
};

RL.preventDefault = function (e) {
    if (e.preventDefault) {
        e.preventDefault();
    }
    else {
        e.returnValue = false;
    }
};

RL.mergeObjects = function (obj1, obj2) {
    var mergedObject = {}, attrname;
    for (attrname in obj1) {
        mergedObject[attrname] = obj1[attrname];
    }
    for (attrname in obj2) {
        mergedObject[attrname] = obj2[attrname];
    }
    return mergedObject;
};

RL.$ = function (s) {
    var r = document.querySelectorAll(s);
    return r.length === 1 ? r[0] : r;
};

RL.detectTouchScreen = function () {
    return ('ontouchstart' in document.documentElement);
};

RL.detectIE = function () {
    return document.all ? true : false;
};