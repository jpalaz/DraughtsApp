/*global Backbone,_ */
var app = app || {};

(function () {
    'use strict';

    app.plyTemplate = function() {
        return _.template('<%= commentBefore %> <strong><%= indexString %></strong> <%= commentAfter %>');
    };

    app.plyView = Backbone.View.extend({
        tagName: 'span',
        className: 'ply',
        template: app.plyTemplate(),
        events: {
            'click > strong': 'selectPly'
        },

        selectPly: function () {
            alert("A ply was clicked!");
        },
        render: function () {
            var ply = this.model;
            var plyNotation = this.getPlyNotation(ply);
            var plyLayout;
            if (!ply.get("position").get('isWhiteMove')) {
                plyLayout = ply.get('index') + ". " + plyNotation;
            } else
                plyLayout = ply.get('index') + ". ... " + plyNotation;
            ply.set('indexString', plyLayout);
            this.$el.html(this.template(ply.toJSON()));
            return this;
        },
        getPlyNotation: function (ply) {
            var from = app.hash[ply.get('from') - 1];
            var to = app.hash[ply.get('to') - 1];
            return from + "-" + to;
        }
    });

    app.pliesView = Backbone.View.extend({
        tagName: 'div',

        initialize: function () {
            this.collection.on('add', this.addOne, this);
        },

        render: function () {
            this.collection.each(this.addOne, this);
            return this;
        },

        addOne: function (ply) {
            var plyView = new app.plyView({model: ply});
            this.$el.append(plyView.render().el);
        }
    });

    //app.plyView = new plyView();
})(jQuery);