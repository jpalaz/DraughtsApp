/*global Backbone */
var app = app || {};

(function () {
    'use strict';

    app.positionModel = Backbone.Model.extend({
        whites: [],
        whiteKings: [],
        blacks: [],
        blackKings: [],
        isWhiteMove: true
    });
})(jQuery);