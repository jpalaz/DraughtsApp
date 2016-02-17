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
            position: undefined,
            isBeat: false
        }
    });

    app.pliesCollection = Backbone.Collection.extend({
        model: app.plyModel
    });

    app.possiblePlyModel = Backbone.Model.extend({
        default: {
            from: 0,
            to: 0,
            position: undefined,
            isBeat: false
        }
    });

    app.possiblePliesCollection = Backbone.Collection.extend({
        model: app.possiblePlyModel,
        url: 'http://localhost:8080/position',

        getPossibles: function(positionJSON) {
            var self = this;
            this.fetch({
                data: positionJSON,
                type: 'POST',

                beforeSend: function (xhr) {
                    xhr.setRequestHeader('Content-Type', 'application/json');
                }
            });
        }
    });
})(jQuery);