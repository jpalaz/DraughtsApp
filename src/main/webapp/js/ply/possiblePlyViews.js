/*global Backbone,_ */
var app = app || {};

(function () {
    'use strict';

    app.possiblePlyTemplate = function() {
        return _.template('<%= commentBefore %> <strong class="best">' +
            '<%= indexString %></strong> <%= commentAfter %>');
    };

    app.possiblePlyView = Backbone.View.extend({
        tagName: 'li',
        className: 'possiblePly',
        //template: app.plyTemplate(),

        render: function () {
            var ply = this.model;
            var plyNotation = app.getPlyNotation(ply);
            this.$el.html(plyNotation);
            return this;
        }
    });

    app.possiblePliesView = Backbone.View.extend({
        tagName: 'ul',
        events: {
            'click' : 'pressPossiblePly'
        },

        initialize: function () {
            this.listenTo(this.collection, 'add', this.addOne);
        },
        render: function () {
            this.collection.each(this.addOne, this);
            return this;
        },

        addOne: function (ply) {
            var possiblePlyView = new app.possiblePlyView({model: ply});
            this.$el.append(possiblePlyView.render().el);
        },

        pressPossiblePly: function (e) {
            console.log(this.collection.length);
            if (e.target !== e.currentTarget) {
                var clickedItem = e.target;
                alert("Selected " + clickedItem.innerText);
                this.onClickPly(0);
            }
            e.stopPropagation();
        },
        onClickPly: function(id) {
            //this.collection.get('byId');
        }
    });
})(jQuery);