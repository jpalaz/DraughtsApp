/*global Backbone,_ */
var app = app || {};

(function () {
    'use strict';

    app.plyTemplate = function() {
        return _.template('<%= commentBefore %> <strong class="best">' +
            '<%= indexString %></strong> <%= commentAfter %>');
    };

    app.getPlyNotation = function(ply) {
        var from = app.hash[ply.get('from') - 1];
        var to = app.hash[ply.get('to') - 1];
        return from + "-" + to;
    };

    app.plyView = Backbone.View.extend({
        tagName: 'span',
        className: 'ply',
        template: app.plyTemplate(),

        render: function () {
            var ply = this.model;
            var plyNotation = app.getPlyNotation(ply);
            var plyLayout;
            if (!ply.get("position").get('isWhiteMove')) {
                plyLayout = ply.get('index') + ". " + plyNotation;
            } else
                plyLayout = ply.get('index') + ". ... " + plyNotation;
            ply.set('indexString', plyLayout);
            this.$el.html(this.template(ply.toJSON()));
            return this;
        }
    });

    app.pliesView = Backbone.View.extend({
        tagName: 'div',
        events: {
            'click' : 'pressPly'
        },

        initialize: function () {
            this.listenTo(this.collection, 'add', this.addOne);
        },
        render: function () {
            this.collection.each(this.addOne, this);
            return this;
        },

        addOne: function (ply) {
            var plyView = new app.plyView({model: ply});
            this.$el.append(plyView.render().el);
        },

        pressPly: function (e) {
            if (e.target !== e.currentTarget) {
                var clickedItem = e.target;
                if (clickedItem.className == 'ply') {
                    alert("Hello " + $(clickedItem).find('.best').text());
                } else {
                    alert("Found " + clickedItem.innerText);
                }
                this.onClickPly(0);
            }
            e.stopPropagation();
        },
        onClickPly: function(id) {
            //this.collection.get('byId');
        }
    });
})(jQuery);