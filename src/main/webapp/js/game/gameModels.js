/*global Backbone */
var app = app || {};

(function () {
    app.gameModel = Backbone.Model.extend({
        url: 'http://localhost:8080/games'
    });
})(jQuery);
