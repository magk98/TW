function printAsync(s, cb) {
        console.log(s);
        if (cb) cb();
}

function task1(cb) {
    printAsync("1", function() {
        task2(cb);
    });
}

function task2(cb) {
    printAsync("2", function() {
        task3(cb);
    });
}

function task3(cb) {
    printAsync("3", cb);
}

// wywolanie sekwencji zadan
task1(function() {
    var delay = Math.floor((Math.random()*1000)+500);
    setTimeout(function() {
    console.log('done!');
    }, delay);
});

function loop(number){
    var i;
    for(i = 0; i < number; i++){
        task1();
    }
}


/*
** Zadanie:
** Napisz funkcje loop(n), ktora powoduje wykonanie powyzszej
** sekwencji zadan n razy. Czyli: 1 2 3 1 2 3 1 2 3 ... done
**
*/

 loop(4);
