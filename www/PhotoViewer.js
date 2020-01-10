var exec = require('cordova/exec');
var PhotoViewer = {
    show : function(options, successCallback, errorCallback) {
        exec(successCallback, errorCallback, 'PhotoViewer', 'show', [options]);
    }
};

module.exports = PhotoViewer;
