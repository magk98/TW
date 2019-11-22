var async = require('async');

async.waterfall(
    [
        function(callback) {
            callback(null, '1');
        },
        function(arg1,  callback) {
            var caption = '1\n' + '2 \n';
            callback(null, caption);
        },
        function(caption, callback) {
            caption += '3';
            callback(null, caption);
        }
    ],
    function (err, caption) {
        var i;
        for(i = 0; i < 4; i++){
            console.log(caption);
        }
        console.log('done');
        // Node.js and JavaScript Rock!
    }
);