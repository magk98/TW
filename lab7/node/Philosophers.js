let waterfall = require("async/waterfall");

let forkWait = 0;
let condWait = 0;

function random(a, b) {
    return Math.random() * (b - a) + a;
}

let Fork = function () {
    this.state = 0;
    return this;
};

// zaimplementuj funkcje acquire, tak by korzystala z algorytmu BEB
// (http://pl.wikipedia.org/wiki/Binary_Exponential_Backoff), tzn:
// 1. przed pierwsza proba podniesienia widelca Filozof odczekuje 1ms
// 2. gdy proba jest nieudana, zwieksza czas oczekiwania dwukrotnie
//    i ponawia probe itd.
Fork.prototype.acquire = function(cb) {
    let wait = function(waitTime, fork, cb) {
        forkWait += waitTime;
        if (fork.state === 0) {
            fork.state = 1;
            cb();
        }
        else {
            setTimeout(function() { wait(waitTime * 2, fork, cb) }, waitTime);
        }
    };
    let initWaitTime = 1;
    forkWait += initWaitTime;
    let fork = this;
    setTimeout(function() { wait(initWaitTime*2, fork, cb) }, initWaitTime);
};

Fork.prototype.release = function() {
    this.state = 0;
};

let eat = function(id, fork1, fork2, cb) {
    setTimeout(function() {
        fork1.release();
        fork2.release();
        console.log(id + " gave back forks.");
        cb();
    }, 0);
};

let Philosopher = function (id, forks) {
    this.id = id;
    this.forks = forks;
    this.f1 = id % forks.length;
    this.f2 = (id + 1) % forks.length;
    return this;
};


// zaimplementuj rozwiazanie naiwne
// kazdy filozof powinien 'count' razy wykonywac cykl
// podnoszenia widelcow -- jedzenia -- zwalniania widelcow
Philosopher.prototype.startNaive = function(count) {
    let forks = this.forks,
        f1 = this.f1,
        f2 = this.f2,
        id = this.id;

    let tasks = [];

    for (let i = 0; i < count; i++) {
        tasks.push(function(cb) { setTimeout(cb, random(0, 5)); } );
        tasks.push(function(cb) { console.log(id + " wants left fork."); cb(); } );
        tasks.push(function(cb) { forks[f1].acquire(cb) } );
        tasks.push(function(cb) { console.log(id + " has left fork."); cb(); } );
        tasks.push(function(cb) { console.log(id + " wants right fork."); cb(); } );
        tasks.push(function(cb) { forks[f2].acquire(cb) } );
        tasks.push(function(cb) { console.log(id + " has right fork."); cb(); } );
        tasks.push(function(cb) { eat(id, forks[f1], forks[f2], cb) } );
    }

    waterfall(tasks, function(err, result) {
        console.log(id + " finished.");
    });
};

// zaimplementuj rozwiazanie asymetryczne
// kazdy filozof powinien 'count' razy wykonywac cykl
// podnoszenia widelcow -- jedzenia -- zwalniania widelcow
Philosopher.prototype.startAsym = function(count) {
    let forks = this.forks,
        f1 = this.f1,
        f2 = this.f2,
        id = this.id;

    if (id % 2 === 1) {
        f2 = this.f1;
        f1 = this.f2;
    }

    let tasks = [];

    for (let i = 0; i < count; i++) {
        tasks.push(function(cb) { setTimeout(cb, random(0, 5)); } );
        tasks.push(function(cb) { console.log(id + " wants left fork."); cb(); } );
        tasks.push(function(cb) { forks[f1].acquire(cb) } );
        tasks.push(function(cb) { console.log(id + " has left fork."); cb(); } );
        tasks.push(function(cb) { console.log(id + " wants right fork."); cb(); } );
        tasks.push(function(cb) { forks[f2].acquire(cb) } );
        tasks.push(function(cb) { console.log(id + " has right fork."); cb(); } );
        tasks.push(function(cb) { eat(id, forks[f1], forks[f2], cb) } );
    }

    waterfall(tasks, function(err, result) {
        console.log(id + " finished.");
    });
};

let Conductor = function(n) {
    this.state = n-1;
    return this;
};

Conductor.prototype.acquire = function(cb) {
    let wait = function(cond, waitTime, cb) {
        condWait += waitTime;
        if (cond.state > 0) {
            cond.state--;
            cb();
        }
        else {
            setTimeout(function() { wait(cond, waitTime * 2, cb) }, waitTime);
        }
    };
    let cond  = this;
    let initWaitTime = 1;
    condWait += initWaitTime;
    setTimeout(function() { wait(cond, initWaitTime * 2, cb) }, initWaitTime);
};

Conductor.prototype.release = function(cb) {
    this.state++;
    cb();
};

let N = 5;
let cond = new Conductor(N);

// zaimplementuj rozwiazanie z kelnerem
// kazdy filozof powinien 'count' razy wykonywac cykl
// podnoszenia widelcow -- jedzenia -- zwalniania widelcow
Philosopher.prototype.startConductor = function(count) {
    let forks = this.forks,
        f1 = this.f1,
        f2 = this.f2,
        id = this.id;

    let tasks = [];

    for (let i = 0; i < count; i++) {
        tasks.push(function(cb) { setTimeout(cb, random(0, 5)); } );
        tasks.push(function(cb) { cond.acquire(cb) } );
        tasks.push(function(cb) { console.log(id + " wants left fork."); cb(); } );
        tasks.push(function(cb) { forks[f1].acquire(cb) } );
        tasks.push(function(cb) { console.log(id + " has left fork."); cb(); } );
        tasks.push(function(cb) { console.log(id + " wants right fork."); cb(); } );
        tasks.push(function(cb) { forks[f2].acquire(cb) } );
        tasks.push(function(cb) { console.log(id + " has right fork."); cb(); } );
        tasks.push(function(cb) { eat(id, forks[f1], forks[f2], cb) } );
        tasks.push(function(cb) { cond.release(cb) } );
    }

    waterfall(tasks, function(err, result) {
        console.log(id + " finished.");
    });
};

let forks = [];
let philosophers = [];
for (let i = 0; i < N; i++) {
    forks.push(new Fork());
}

for (let i = 0; i < N; i++) {
    philosophers.push(new Philosopher(i, forks));
}

/*for (let i = 0; i < N; i++) {
    philosophers[i].startNaive(10);
}*/

/*for (let i = 0; i < N; i++) {
    philosophers[i].startAsym(10);
}*/

for (let i = 0; i < N; i++) {
    philosophers[i].startConductor(10);
}

