/*global Backbone */
var app = app || {};

(function () {
    'use strict';

    app.plyModel = Backbone.Model.extend({
        default: {
            index: 0,
            commentBefore: undefined,
            commentAfter: undefined,
            rate: undefined,
            from: 0,
            to: 0,
            position: undefined
        }
    });

    app.pliesCollection = Backbone.Collection.extend({
        model: app.plyModel
    });

    app.plies = new app.pliesCollection();
})(jQuery);