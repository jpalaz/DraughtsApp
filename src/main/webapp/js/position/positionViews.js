/*global Backbone */
var app = app || {};

(function ($) {
    'use strict';

    app.positionView = Backbone.View.extend({
        brdSize: 8,
        initialize: function () {
            this.model.on('change', this.render, this);
            this.render();
        },
        render: function () {
            var position = this.model;
            var whites = position.get('whites');
            var blacks = position.get('blacks');
            var whiteKings = position.get('whiteKings');
            var blackKings = position.get('blackKings');
            for (var i = 1; i <= this.brdSize * this.brdSize / 2; i++) {
                this.clearSquare(i);
            }
            this.setDraughts(whites, 'white-figure');
            this.setDraughts(blacks, 'black-figure');
            this.setDraughts(whiteKings, 'white-queen');
            this.setDraughts(blackKings, 'black-queen');
        },
        clearSquare: function (i) {
            var coordinate8x8 = app.hash[i - 1];
            var $coordSquare = $('#' + coordinate8x8 + '-square');
            $coordSquare.removeClass('white-figure');
            $coordSquare.removeClass('black-figure');
            $coordSquare.removeClass('white-queen');
            $coordSquare.removeClass('black-queen');
        },
        setDraughts: function (draughts, className) {
            for (var i = 0; i < draughts.length; i++) {
                var coordinate8x8 = app.hash[draughts[i] - 1];
                $('#' + coordinate8x8 + '-square').addClass(className);
            }
        }
    });

    //app.positionView = new positionView();
})(jQuery);