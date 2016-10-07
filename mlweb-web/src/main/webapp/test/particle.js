/*!
 * jQuery JavaScript Library v2.1.1
 * http://jquery.com/
 *
 * Includes Sizzle.js
 * http://sizzlejs.com/
 *
 * Copyright 2005, 2014 jQuery Foundation, Inc. and other contributors
 * Released under the MIT license
 * http://jquery.org/license
 *
 * Date: 2014-05-01T17:11Z
 */
/*!
 * Bootstrap v3.3.7 (http://getbootstrap.com)
 * Copyright 2011-2016 Twitter, Inc.
 * Licensed under the MIT license
 */
if (function(t, e) {
    "object" == typeof module && "object" == typeof module.exports ? module.exports = t.document ? e(t, !0) : function(t) {
        if (!t.document)
            throw new Error("jQuery requires a window with a document");
        return e(t)
    }
    : e(t)
}("undefined" != typeof window ? window : this, function(t, e) {
    function i(t) {
        var e = t.length
          , i = Z.type(t);
        return "function" === i || Z.isWindow(t) ? !1 : 1 === t.nodeType && e ? !0 : "array" === i || 0 === e || "number" == typeof e && e > 0 && e - 1 in t
    }
    var X = {}
      , Q = X.toString
      , K = "2.1.1"
      , Z = function(t, e) {
        return new Z.fn.init(t,e)
    };

	
    Z.fn = Z.prototype = {
        jquery: K,
    };
    


    var Et = "undefined";
    var Fe = t.jQuery
      , He = t.$;
    return Z.noConflict = function(e) {
        return t.$ === Z && (t.$ = He),
        e && t.jQuery === Z && (t.jQuery = Fe),
        Z
    }
    ,
    typeof e === Et && (t.jQuery = t.$ = Z),
    Z
}),
"undefined" == typeof jQuery)
    throw new Error("Bootstrap's JavaScript requires jQuery");
+function(t) {
    "use strict";
    var e = t.fn.jquery.split(" ")[0].split(".");
    if (e[0] < 2 && e[1] < 9 || 1 == e[0] && 9 == e[1] && e[2] < 1 || e[0] > 3)
        throw new Error("Bootstrap's JavaScript requires jQuery version 1.9.1 or higher, but lower than version 4")
}(jQuery),
/* ========================================================================
 * Bootstrap: affix.js v3.3.7
 * http://getbootstrap.com/javascript/#affix
 * ========================================================================
 * Copyright 2011-2016 Twitter, Inc.
 * Licensed under MIT (https://github.com/twbs/bootstrap/blob/master/LICENSE)
 * ======================================================================== */
+function(t) {
    "use strict";
    var e = function(t, e) {
        this.x = t || 0,
        this.y = e || 0
    }
    ;
    e.prototype = {
        clone: function() {
            return new e(this.x,this.y)
        },
        add: function(t) {
            return this.x += t.x,
            this.y += t.y,
            this
        },
        sub: function(t) {
            return this.x -= t.x,
            this.y -= t.y,
            this
        },
        subVal: function(t) {
            return this.x -= t,
            this.y -= t,
            this
        },
        mult: function(t) {
            return this.x *= t,
            this.y *= t,
            this
        },
        div: function(t) {
            return 0 === t ? this : (this.x /= t,
            this.y /= t,
            this)
        },
        mag: function() {
            return Math.sqrt(this.x * this.x + this.y * this.y)
        },
        limit: function(t) {
            return this.mag() > t && (this.normalize(),
            this.mult(t)),
            this
        },
        normalize: function() {
            var t = this.mag();
            return 0 === t ? this : (this.div(t),
            this)
        },
        heading: function() {
            return Math.atan2(this.y, this.x)
        },
        set: function(t) {
            return this.x = t.x,
            this.y = t.y,
            this
        }
    },
    e.add = function(t, e) {
        return t.clone().add(e.clone())
    }
    ,
    e.sub = function(t, e) {
        return t.clone().sub(e.clone())
    }
    ,
    e.mult = function(t, e) {
        return t.clone().mult(e)
    }
    ,
    e.div = function(t, e) {
        return t.clone().div(e)
    }
    ,
    e.random2D = function() {
        var t = Math.random(0, 1) * Math.PI * 2;
        return new e(Math.cos(t),Math.sin(t))
    }
    ,
    e.coerce = function(t) {
        return new e(t.x,t.y)
    }
    ,
    t.Vector = e
}(this),
Function.prototype.bind || (Function.prototype.bind = function(t) {
    if ("function" != typeof this)
        throw new TypeError("Function.prototype.bind - what is trying to be bound is not callable");
    var e = Array.prototype.slice.call(arguments, 1)
      , i = this
      , n = function() {}
      , o = function() {
        return i.apply(this instanceof n && t ? this : t, e.concat(Array.prototype.slice.call(arguments)))
    }
    ;
    return n.prototype = this.prototype,
    o.prototype = new n,
    o
}
),
function(t, e, i) {
    "undefined" != typeof module ? module.exports = i() : "undefined" != typeof define && "object" == typeof define.amd ? define(i) : e[t] = i()
}("Base", this, function() {
    var t = function() {}
    ;
    return t.extend = function(e, i) {
        var n = t.prototype.extend;
        t._prototyping = !0;
        var o = new this;
        n.call(o, e),
        o.base = function() {}
        ,
        delete t._prototyping;
        var s = o.constructor
          , r = o.constructor = function() {
            if (!t._prototyping)
                if (this._constructing || this.constructor === r)
                    this._constructing = !0,
                    s.apply(this, arguments),
                    delete this._constructing;
                else if (null !== arguments[0])
                    return (arguments[0].extend || n).call(arguments[0], o)
        }
        ;
        return r.ancestor = this,
        r.extend = this.extend,
        r.forEach = this.forEach,
        r.implement = this.implement,
        r.prototype = o,
        r.toString = this.toString,
        r.valueOf = function(t) {
            return "object" === t ? r : s.valueOf()
        }
        ,
        n.call(r, i),
        "function" == typeof r.init && r.init(),
        r
    }
    ,
    t.prototype = {
        extend: function(e, i) {
            if (arguments.length > 1) {
                var n = this[e];
                if (n && "function" == typeof i && (!n.valueOf || n.valueOf() !== i.valueOf()) && /\bbase\b/.test(i)) {
                    var o = i.valueOf();
                    i = function() {
                        var e = this.base || t.prototype.base;
                        this.base = n;
                        var i = o.apply(this, arguments);
                        return this.base = e,
                        i
                    }
                    ,
                    i.valueOf = function(t) {
                        return "object" === t ? i : o
                    }
                    ,
                    i.toString = t.toString
                }
                this[e] = i
            } else if (e) {
                var s = t.prototype.extend;
                t._prototyping || "function" == typeof this || (s = this.extend || s);
                for (var r = {
                    toSource: null
                }, a = ["constructor", "toString", "valueOf"], l = t._prototyping ? 0 : 1; l < a.length; l++) {
                    var p = a[l];
                    e[p] !== r[p] && s.call(this, p, e[p])
                }
                for (var c in e)
                    r[c] || s.call(this, c, e[c])
            }
            return this
        }
    },
    t = t.extend({
        constructor: function() {
            this.extend(arguments[0])
        }
    }, {
        ancestor: Object,
        version: "1.1",
        forEach: function(t, e, i) {
            for (var n in t)
                void 0 === this.prototype[n] && e.call(i, t[n], n, t)
        },
        implement: function() {
            for (var t = 0; t < arguments.length; t++)
                "function" == typeof arguments[t] ? arguments[t](this.prototype) : this.prototype.extend(arguments[t]);
            return this
        },
        toString: function() {
            return String(this.valueOf())
        }
    })
}),
function() {
    var t = function(t) {
        this.engine = t,
        this._chain = [],
        this._updateTimer = this._updateTimer.bind(this),
        this._cycle = this._cycle.bind(this)
    }
    ;
    t.prototype._running = !1,
    t.prototype._updateTimer = function(t) {
        this._timer += t,
        this._timer >= this._timerMax && (this.resetTimer(),
        this._cycle())
    }
    ,
    t.prototype.resetTimer = function() {
        return this.engine.updateChainTimer = void 0,
        this._timer = 0,
        this._timerMax = 0,
        this
    }
    ,
    t.prototype.start = function() {
        return this._running || !this._chain.length ? this : (this._running = !0,
        this._cycle())
    }
    ,
    t.prototype.reset = function() {
        return this._running ? (this.resetTimer(),
        this._timer = 0,
        this._running = !1,
        this) : this
    }
    ,
    t.prototype._cycle = function() {
        var t;
        return this._chain.length ? (t = this._chain.shift(),
        "function" === t.type ? (t.func.apply(t.scope, t.args),
        t = null ,
        this._cycle()) : ("wait" === t.type && (this.resetTimer(),
        this._timerMax = t.time / 1e3,
        this.engine.updateChainTimer = this._updateTimer,
        t = null ),
        this)) : this.reset()
    }
    ,
    t.prototype.then = t.prototype.exec = function(t, e, i) {
        return this._chain.push({
            type: "function",
            func: t,
            scope: e || window,
            args: i || []
        }),
        this.start()
    }
    ,
    t.prototype.wait = function(t) {
        return this._chain.push({
            type: "wait",
            time: t
        }),
        this.start()
    }
    ,
    window.Chainable = t
}(),
!function(t, e, i, n, o) {
    var s, r, a;
    window.requestAnimationFrame || (window.requestAnimationFrame = function() {
        return window.requestAnimationFrame || window.webkitRequestAnimationFrame || window.mozRequestAnimationFrame || function(t) {
            window.setTimeout(t, 1e3 / 60)
        }
    }()),
    s = Math.sqrt,
    r = Math.pow,
    a = t.extend({
        scale: window.devicePixelRatio || 1,
        shapes: [],
        particles: [],
        particlesA: [],
        particlesB: [],
        _deferredParticles: [],
        ticks: [],
        starGeneratorRate: 600,
        mouse: {
            x: -9999,
            y: -9999
        },
        constructor: function(t, e, i) {
            return this.canvas = t,
            this.background = e,
            this.tagLine = i,
            this.canvas.getContext ? (this.context = this.canvas.getContext("2d"),
            this.setupEvents(),
            this.setupStarfield(),
            this.setupMisc(),
            void this.startEngine()) : null
        },
        startEngine: function() {
            this.canvas.style.opacity = 1,
            new o(this).wait(1e3),
            this.render()
        },
        setupMisc: function() {
            this.last = Date.now() / 1e3,
            this.render = this.render.bind(this)
        },
        setupEvents: function() {
            this.resize = this.resize.bind(this),
            this.resize(),
            window.addEventListener("resize", this.resize, !1),
            this._handleScroll = this._handleScroll.bind(this),
            this._handleScroll(),
            window.addEventListener("scroll", this._handleScroll, !1),
            this._handleMouseCoords = this._handleMouseCoords.bind(this),
            window.addEventListener("mousemove", this._handleMouseCoords, !1)
        },
        setupStarfield: function() {
            this.particles = [],
            this.generateParticles(400)
        },
        render: function() {
            var t, e, i, n = this.scale;
            if (!this.paused) {
                if (this.scrollY > this.height)
                    return void window.requestAnimationFrame(this.render);
                for (this.context.clearRect(-(this.width / 2) * n, -(this.height / 2) * n, this.width * n, this.height * n),
                this.now = Date.now() / 1e3,
                this.tick = Math.min(this.now - this.last, .017),
                this.updateChainTimer && this.updateChainTimer(this.tick),
                t = 0; t < this.particles.length; t++)
                    this.particles[t].update(this);
                for (this.context.fillStyle = "#8750c2",
                t = 0; t < this.particlesA.length; t++)
                    e = this.particlesA[t],
                    e.radius < .25 || this.context.fillRect(e.pos.x * n >> 0, e.pos.y * n >> 0, e.radius * n, e.radius * n);
                for (this.context.fillStyle = "#b976ff",
                t = 0; t < this.particlesB.length; t++)
                    e = this.particlesB[t],
                    e.radius < .25 || this.context.fillRect(e.pos.x * n >> 0, e.pos.y * n >> 0, e.radius * n, e.radius * n);
                for (this.particlesA.length = 0,
                this.particlesB.length = 0,
                t = 0; t < this._deferredParticles.length; t++)
                    i = this.particles.indexOf(this._deferredParticles.pop()),
                    i >= 0 && this.particles.splice(i, 1);
                this.last = this.now,
                this.generateParticles(this.starGeneratorRate * this.tick >> 0),
                window.requestAnimationFrame(this.render)
            }
        },
        generateParticles: function(t, e) {
            var i;
            for (i = 0; t > i; i++)
                e ? this.particles.push(new a.Particle.Fixed(this.width,this.height)) : this.particles.push(new a.Particle(this.width,this.height))
        },
        resize: function() {
            var t, e, i = this.scale;
            window.innerWidth < 570 ? this.height = 560 : this.height = 800,
            this.width = window.innerWidth,
            this.canvas.width = this.width * i,
            this.canvas.height = this.height * i,
            this.context.translate(this.width / 2 * i >> 0, this.height / 2 * i >> 0),
            this.context.lineJoin = "bevel"
        },
        _handleMouseCoords: function(t) {
            this.mouse.x = t.pageX,
            this.mouse.y = t.pageY
        },
        _handleScroll: function() {
            this.scrollY = window.scrollY
        }
    }),
    a.map = function(t, e, i, n, o) {
        return n + (o - n) * ((t - e) / (i - e))
    }
    ,
    a.getRandomFloat = function(t, e) {
        return Math.random() * (e - t) + t
    }
    ,
    a.getRandomInt = function(t, e) {
        return Math.floor(Math.random() * (e - t + 1) + t)
    }
    ,
    a.clone = function(t) {
        var e, i = {};
        for (e in t)
            i[e] = t[e];
        return i
    }
    ,
    window.Engine = a
}(window.Base, window.Vector, window.Logo, window.Grid, window.Chainable),
function(t, e) {
    t.Particle = function(i, n) {
        var o, s, r;
        this.accel = e.coerce(this.accel),
        this.vel = e.coerce(this.vel),
        this.pos = new e(0,0),
        this.maxRadius = t.getRandomFloat(.1, 2.5),
        this.maxSpeed = t.getRandomFloat(20, 1e3),
        o = t.getRandomInt(0, 3),
        0 === o || 2 === o ? (r = 0 === o ? -(n / 2) : n / 2,
        s = t.getRandomInt(-(i / 2), i / 2)) : (r = t.getRandomInt(-(n / 2), n / 2),
        s = 3 === o ? -(i / 2) : i / 2),
        this.target = new e(s,r),
        this.getAccelVector(),
        this.maxDistance = this.distanceTo(this.target),
        this.fillA = "#8750c2",
        this.fillB = "#b976ff",
        this.frameMax = t.getRandomInt(1, 5)
    }
    ,
    t.Particle.prototype = {
        radius: 1,
        frame: 0,
        showA: !1,
        accel: {
            x: 0,
            y: 0
        },
        vel: {
            x: 0,
            y: 0
        },
        pos: {
            x: 0,
            y: 0
        },
        opacity: 1,
        maxSpeed: 1500,
        maxForce: 1500,
        getAccelVector: function() {
            this.accel = e.sub(this.target, this.pos).normalize().mult(this.maxSpeed)
        },
        update: function(t) {
            var i, n, o;
            return this.vel.add(this.accel).limit(this.maxSpeed),
            this.pos.add(e.mult(this.vel, t.tick)),
            n = t.width / 2 + this.maxRadius,
            o = t.height / 2 + this.maxRadius,
            (this.pos.x < -n || this.pos.x > n || this.pos.y < -o || this.pos.y > o) && this.kill(t),
            i = (this.maxDistance - this.distanceTo(this.target)) / this.maxDistance,
            this.radius = Math.max(.1, this.maxRadius * i),
            this.frame++,
            this.frame > this.frameMax && (this.frame = 0,
            this.showA = !this.showA),
            this.showA ? t.particlesA[t.particlesA.length] = this : t.particlesB[t.particlesB.length] = this,
            this
        },
        draw: function(t, e) {
            return this.radius < .25 ? void 0 : (this.showA ? t.fillStyle = this.fillA : t.fillStyle = this.fillB,
            t.fillRect(this.pos.x * e >> 0, this.pos.y * e >> 0, this.radius * e, this.radius * e),
            this)
        },
        kill: function(t) {
            return t._deferredParticles.push(this),
            this
        },
        distanceTo: function(t) {
            var e = this.pos.x - t.x
              , i = this.pos.y - t.y;
            return Math.sqrt(e * e + i * i)
        }
    }
}(window.Engine, window.Vector),
function(t, e, i) {
    t.Fixed = function(t, n) {
        var o, s;
        this.radius = e.getRandomFloat(.1, 1),
        this.fillA = "#3a1066",
        this.fillB = "#561799",
        this.frameMax = e.getRandomInt(4, 10),
        this.max = {
            x: t + this.maxRadius,
            y: n + this.maxRadius
        },
        this.min = {
            x: 0 - this.maxRadius,
            y: 0 - this.maxRadius
        },
        o = e.getRandomInt(0 + this.radius, t + this.radius),
        s = e.getRandomInt(0 + this.radius, n + this.radius),
        this.pos = new i(o,s)
    }
    ,
    e.Particle.Fixed.prototype = {
        radius: 1,
        pos: {
            x: 0,
            y: 0
        },
        frame: 0,
        showA: !1,
        update: function(t) {
            return this.frame++,
            this.frame > this.frameMax && (this.frame = 0,
            this.showA = !this.showA),
            this
        },
        draw: function(t, e) {
            return t.beginPath(),
            t.arc(this.pos.x * e >> 0, this.pos.y * e >> 0, this.radius * e, 0, 2 * Math.PI, !1),
            this.showA ? t.fillStyle = this.fillA : t.fillStyle = this.fillB,
            t.fill(),
            this
        }
    }
}(window.Engine.Particle, window.Engine, window.Vector),
function(t, e) {
    "use strict";
    t.Point = function(i, n, o, s) {
        this.id = i,
        this.shapeSize = s,
        this.ref = new e(n,o),
        this.pos = new e(n * s.x,o * s.y),
        this.target = this.pos.clone(),
        this.pos.x = s.x / 2,
        this.pos.y = s.y / 2,
        this.accel = e.coerce(this.accel),
        this.vel = e.coerce(this.vel),
        this.stiffness = t.getRandomFloat(150, 600),
        this.friction = t.getRandomFloat(12, 18)
    }
    ,
    t.Point.prototype = {
        radius: 1,
        stiffness: 200,
        friction: 13,
        threshold: .03,
        pos: {
            x: 0,
            y: 0
        },
        accel: {
            x: 0,
            y: 0
        },
        vel: {
            x: 0,
            y: 0
        },
        target: {
            x: 0,
            y: 0
        },
        resize: function() {
            this.target.x = this.pos.x = this.ref.x * this.shapeSize.x,
            this.target.y = this.pos.y = this.ref.y * this.shapeSize.y
        },
        updateBreathingPhysics: function() {
            this.stiffness = t.getRandomFloat(2, 4),
            this.friction = t.getRandomFloat(1, 2)
        },
        updateTarget: function(i) {
            var n;
            this.target.x = this.ref.x * i.x,
            this.target.y = this.ref.y * i.y,
            n = e.sub(i, this.shapeSize).div(2),
            this.target.sub(n),
            this.target.add({
                x: t.getRandomFloat(-3, 3),
                y: t.getRandomFloat(-3, 3)
            })
        },
        update: function(t) {
            var i;
            return i = e.sub(this.target, this.pos).mult(this.stiffness).sub(e.mult(this.vel, this.friction)),
            this.accel.set(i),
            this.vel.add(e.mult(this.accel, t.tick)),
            this.pos.add(e.mult(this.vel, t.tick)),
            i = null ,
            this
        },
        draw: function(t, e) {
            return t.beginPath(),
            t.arc(this.pos.x * e, this.pos.y * e, this.radius * e, 0, 2 * Math.PI, !1),
            t.fillStyle = "#ffffff",
            t.fill(),
            this
        }
    }
}(window.Engine, window.Vector),
function(t, e) {
    t.Point.Puller = function(t, i, n, o) {
        this.id = t,
        this.shapeSize = o,
        this.ref = new e(i,n),
        this.pos = new e(i * o.x,n * o.y),
        this.home = this.pos.clone(),
        this.accel = e.coerce(this.accel),
        this.vel = e.coerce(this.vel)
    }
    ,
    t.Point.Puller.prototype = {
        fillStyle: null ,
        defaultFillstyle: "#b976ff",
        chasingFillstyle: "#ff6b6b",
        radius: 1,
        maxSpeed: 160,
        maxForce: 50,
        pos: {
            x: 0,
            y: 0
        },
        accel: {
            x: 0,
            y: 0
        },
        vel: {
            x: 0,
            y: 0
        },
        aRad: 200,
        safety: .25,
        resize: function() {
            return this.home.x = this.pos.x = this.ref.x * this.shapeSize.x,
            this.home.y = this.pos.y = this.ref.y * this.shapeSize.y,
            this
        },
        update: function(t) {
            var i, n, o, s, r = e.coerce(t.mouse);
            return r.x += (this.shapeSize.x - t.width) / 2,
            r.y += (this.shapeSize.y - t.height) / 2,
            i = this.distanceTo(r),
            this.accel.mult(0),
            i < this.aRad ? (this._chasing = !0,
            this.toChase(r),
            this.fillStyle = this.chasingFillstyle) : (this._chasing = !1,
            this.fillStyle = this.defaultFillstyle),
            this.toChase(this.home, this.maxForce / 2),
            this.vel.add(this.accel),
            this.pos.add(e.mult(this.vel, t.tick)),
            n = e.sub(this.home, this.pos),
            o = n.mag(),
            s = this.aRad * (3 * this.safety),
            o > this.aRad - s && (n.normalize(),
            n.mult(this.aRad - s),
            this.pos = e.sub(this.home, n)),
            r = null ,
            n = null ,
            this
        },
        toChase: function(i, n) {
            var o, s, r, a, l;
            n = n || this.maxForce,
            i = e.coerce(i),
            o = e.sub(i, this.pos),
            r = o.mag(),
            o.normalize(),
            l = this.aRad * this.safety,
            a = l > r ? t.map(r, 0, l, 0, this.maxSpeed) : r > this.aRad - l ? t.map(this.aRad - r, 0, l, 0, this.maxSpeed) : this.maxSpeed,
            o.mult(a),
            s = e.sub(o, this.vel),
            s.limit(n),
            this.accel.add(s),
            i = null ,
            o = null ,
            s = null
        },
        draw: function(t, e) {
            return t.fillStyle = this.fillStyle,
            t.fillRect((this.pos.x - this.radius / 2) * e >> 0, (this.pos.y - this.radius / 2) * e >> 0, this.radius * e, this.radius * e),
            this
        },
        distanceTo: function(t) {
            var e = this.home.x - t.x
              , i = this.home.y - t.y;
            return Math.sqrt(e * e + i * i)
        }
    }
}(window.Engine, window.Vector),
function(t, e) {
    t.Polygon = function(e, i, n, o, s) {
        this.a = e,
        this.b = i,
        this.c = n,
        this.color = t.clone(o),
        this.strokeColor = s ? t.clone(s) : t.clone(o),
        s ? this.strokeColor = t.clone(s) : this.strokeColor = t.clone(o),
        this.strokeWidth = .25,
        this.maxStrokeS = this.strokeColor.s,
        this.maxStrokeL = this.strokeColor.l,
        this.maxColorL = this.color.l,
        this.strokeColor.s = 0,
        this.strokeColor.l = 100,
        this.color.l = 0,
        this.fillStyle = this.hslaTemplate.substitute(this.color),
        this.strokeStyle = this.hslaTemplate.substitute(this.strokeColor)
    }
    ,
    t.Polygon.prototype = {
        rgbaTemplate: "rgba({r},{g},{b},{a})",
        hslaTemplate: "hsla({h},{s}%,{l}%,{a})",
        hueShiftSpeed: 20,
        duration: 2,
        delay: 0,
        start: 0,
        update: function(t) {
            var e;
            this.simple || (this.start += t.tick,
            e = this.start,
            e > this.delay && e < this.delay + this.duration + 1 && this.color.l < this.maxColorL && (this.color.l = this.maxColorL * (e - this.delay) / this.duration,
            this.strokeColor.s = this.maxStrokeS * (e - this.delay) / this.duration,
            this.strokeColor.l = (this.maxStrokeL - 100) * (e - this.delay) / this.duration + 100,
            this.strokeWidth = 1.5 * (e - this.delay) / this.duration + .25,
            this.color.l > this.maxColorL && (this.color.l = this.maxColorL,
            this.strokeColor.l = this.maxStrokeL,
            this.strokeWidth = 1.5),
            this.strokeStyle = this.hslaTemplate.substitute(this.strokeColor),
            this.fillStyle = this.hslaTemplate.substitute(this.color)))
        }
    }
}(window.Engine, window.Vector),
function(t, e) {
    t.Polygon.Puller = function(t, e, i, n, o) {
        this.a = t,
        this.b = e,
        this.c = i,
        this.strokeStyle = "#ffffff"
    }
    ,
    t.Polygon.Puller.prototype = {
        checkChasing: function() {
            return this.a._chasing === !0 && this.b._chasing === !0 && this.c._chasing === !0 ? !0 : !1
        }
    }
}(window.Engine, window.Vector),
function(t, e, i, n) {
    t.Shape = function(t, o, s, r, a, l) {
        var p, c, h, u;
        for (this.pos = new n(t,o),
        this.size = new n(s,r),
        this.sizeRef = this.size.clone(),
        c = {},
        this.points = [],
        this.polygons = [],
        p = 0; p < a.length; p++)
            h = new e(a[p].id,a[p].x,a[p].y,this.size),
            c[h.id] = h,
            this.points.push(h);
        for (p = 0; p < l.length; p++)
            u = l[p],
            this.polygons.push(new i(c[u.points[0]],c[u.points[1]],c[u.points[2]],u.color,u.stroke))
    }
    ,
    t.Shape.prototype = {
        breathing: !1,
        breath: 0,
        breathLength: 1,
        breatheIn: !1,
        resize: function(t, e) {
            var i, n;
            for (this.size.x = t,
            this.size.y = t,
            this.sizeRef.x = t,
            this.sizeRef.y = t,
            this.pos.x = -(t / 2),
            this.pos.y = -(t / 2 + e),
            n = 0,
            i = this.points.length; i > n; n++)
                this.points[n].resize()
        },
        startBreathing: function() {
            var t;
            for (this.breathing = !0,
            this.breath = this.breathLength,
            t = 0; t < this.points.length; t++)
                this.points[t].updateBreathingPhysics()
        },
        breathe: function(t) {
            var e, i, o;
            if (this.breath += t,
            !(this.breath < this.breathLength)) {
                for (i = 1,
                o = n.mult(this.sizeRef, i),
                e = 0; e < this.points.length; e++)
                    this.points[e].updateTarget(o);
                this.breath = 0
            }
        },
        update: function(t) {
            var e;
            for (this.breathing === !0 && this.breathe(t.tick),
            e = 0; e < this.points.length; e++)
                this.points[e].update(t);
            for (e = 0; e < this.polygons.length; e++)
                this.polygons[e].update(t);
            return this
        },
        draw: function(t, e, i) {
            var n, o;
            for (t.translate(this.pos.x * e >> 0, this.pos.y * e >> 0),
            n = 0; n < this.polygons.length; n++)
                o = this.polygons[n],
                t.beginPath(),
                t.moveTo(o.a.pos.x * e, o.a.pos.y * e),
                t.lineTo(o.b.pos.x * e, o.b.pos.y * e),
                t.lineTo(o.c.pos.x * e, o.c.pos.y * e),
                t.closePath(),
                t.fillStyle = o.fillStyle,
                t.fill(),
                t.lineWidth = o.strokeWidth * e,
                t.strokeStyle = o.strokeStyle,
                t.stroke();
            return t.setTransform(1, 0, 0, 1, 0, 0),
            t.translate(i.width / 2 * i.scale >> 0, i.height / 2 * i.scale >> 0),
            this
        }
    }
}(window.Engine, window.Engine.Point, window.Engine.Polygon, window.Vector),
function(t, e, i, n) {
    t.Shape.Puller = function(t, o, s) {
        var r, a, l, p;
        for (this.pos = new n(0,0),
        this.size = new n(t,o),
        this.heightRatio = s.data.width / s.data.height,
        this.widthRatio = s.data.ar,
        this.resize(t, o, !0),
        a = {},
        this.points = [],
        this.polygons = [],
        r = 0; r < s.points.length; r++)
            l = new e(s.points[r].id,s.points[r].x,s.points[r].y,this.size),
            a[l.id] = l,
            this.points.push(l);
        for (r = 0; r < s.polygons.length; r++)
            p = s.polygons[r],
            this.polygons.push(new i(a[p.points[0]],a[p.points[1]],a[p.points[2]],p.color)),
            this.polygons[this.polygons.length - 1].noFill = !0;
        this.ref = void 0
    }
    ,
    t.Shape.Puller.prototype = {
        alpha: 0,
        sizeOffset: 100,
        resize: function(t, e, i) {
            var n, o, s, r;
            if (r = e + this.sizeOffset,
            s = this.size.y * this.heightRatio,
            t > s && (s = t + this.sizeOffset,
            r = s * this.widthRatio),
            this.size.y = r,
            this.size.x = s,
            this.pos.x = -(s / 2),
            this.pos.y = -(r / 2),
            i)
                return this;
            for (o = 0,
            n = this.points.length; n > o; o++)
                this.points[o].resize()
        },
        update: function(t) {
            var e;
            for (e = 0; e < this.points.length; e++)
                this.points[e].update(t);
            return this.alpha < 1 && (this.alpha = Math.min(this.alpha + 2 * t.tick, 1)),
            this
        },
        draw: function(t, e, i) {
            var n, o;
            for (t.translate(this.pos.x * e >> 0, this.pos.y * e >> 0),
            this.alpha < 1 && (t.globalAlpha = this.alpha),
            t.beginPath(),
            n = 0; n < this.polygons.length; n++)
                o = this.polygons[n],
                t.moveTo(o.a.pos.x * e >> 0, o.a.pos.y * e >> 0),
                t.lineTo(o.b.pos.x * e >> 0, o.b.pos.y * e >> 0),
                t.lineTo(o.c.pos.x * e >> 0, o.c.pos.y * e >> 0),
                t.lineTo(o.a.pos.x * e >> 0, o.a.pos.y * e >> 0);
            for (t.closePath(),
            t.lineWidth = .4 * e,
            t.strokeStyle = "rgba(108,0,243,0.15)",
            t.stroke(),
            this.alpha < 1 && (t.globalAlpha = 1),
            n = 0; n < this.points.length; n++)
                this.points[n].draw(t, e);
            for (t.beginPath(),
            n = 0; n < this.polygons.length; n++)
                this.polygons[n].checkChasing() && (o = this.polygons[n],
                t.moveTo(o.a.pos.x * e >> 0, o.a.pos.y * e >> 0),
                t.lineTo(o.b.pos.x * e >> 0, o.b.pos.y * e >> 0),
                t.lineTo(o.c.pos.x * e >> 0, o.c.pos.y * e >> 0),
                t.lineTo(o.a.pos.x * e >> 0, o.a.pos.y * e >> 0));
            return t.closePath(),
            t.fillStyle = "rgba(108,0,243,0.05)",
            t.fill(),
            t.setTransform(1, 0, 0, 1, 0, 0),
            t.translate(i.width / 2 * i.scale >> 0, i.height / 2 * i.scale >> 0),
            this
        }
    }
}(window.Engine, window.Engine.Point.Puller, window.Engine.Polygon.Puller, window.Vector),
function(t) {
    var e = function() {
        return window.navigator.userAgent.match("Trident") ? !0 : !1
    }()
      , i = {
        start: function() {
            var t = document.body.id.toLowerCase();
            this.Pages[t] && this.Pages[t]()
        },
        initializeEngine: function() {
            var e, n, o = document.getElementById("jumbotron"), r;
            o && (e = document.createElement("canvas"),
            e.className = "terraform-canvas",
            o.appendChild(e),
            new t(e,n,r))
        },
        Pages: {
            "page-home": function() {
                return e ? (document.getElementById("jumbotron").className += " static") : void i.initializeEngine()
            }
        }
    };
    i.start()
}(window.Engine);
