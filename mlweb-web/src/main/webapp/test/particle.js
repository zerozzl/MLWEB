function draw_particle(id) {
	;+function(t) {
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
				}
		},
		e.sub = function(t, e) {
			return t.clone().sub(e.clone())
		}
		,
		e.mult = function(t, e) {
			return t.clone().mult(e)
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
		})
	}),
	
	!function(t, e) {
		var a;
		a = t.extend({
			scale: window.devicePixelRatio || 1,
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
			constructor: function(t, e) {
				return this.canvas = t,
				this.background = e,
				this.canvas.getContext ? (this.context = this.canvas.getContext("2d"),
						this.setupEvents(),
						this.setupStarfield(),
						this.setupMisc(),
						void this.startEngine()) : null
			},
			startEngine: function() {
				this.canvas.style.opacity = 1,
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
					for (this.context.fillStyle = "#66bbff",
							t = 0; t < this.particlesA.length; t++)
						e = this.particlesA[t],
						e.radius < .25 || this.context.fillRect(e.pos.x * n >> 0, e.pos.y * n >> 0, e.radius * n, e.radius * n);
					for (this.context.fillStyle = "#66bbff",
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
				this.height = innerHeight - 5,
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
		a.getRandomFloat = function(t, e) {
			return Math.random() * (e - t) + t
		}
		,
		a.getRandomInt = function(t, e) {
			return Math.floor(Math.random() * (e - t + 1) + t)
		}
		,
		window.Engine = a
	}(window.Base, window.Vector, window.Chainable),
	
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
							this.fillA = "#66bbff",
							this.fillB = "#66bbff",
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
	
	function(t) {
		var o = document.getElementById(id)
		, e = document.createElement("canvas");
		o.appendChild(e);
		new t(e);
	}(window.Engine);
}
