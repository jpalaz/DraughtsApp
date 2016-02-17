/*global Backbone, jQuery, _ */
var app = app || {};

(function ($) {
    'use strict';

    app.hash = [
        'b8', 'd8', 'f8', 'h8',
        'a7', 'c7', 'e7', 'g7',
        'b6', 'd6', 'f6', 'h6',
        'a5', 'c5', 'e5', 'g5',
        'b4', 'd4', 'f4', 'h4',
        'a3', 'c3', 'e3', 'g3',
        'b2', 'd2', 'f2', 'h2',
        'a1', 'c1', 'e1', 'g1'
    ];

    app.gameView = Backbone.View.extend({
        brdSize: 8,
        currentPlyIndex: 0,
        begin: undefined,
        positionView: undefined,
        possiblePliesCollection: undefined,//new app.possiblePliesModel(),
        //currentPlySequence: undefined,

        initialize: function () {
            this.createBoard(this.brdSize);
            var context = this;
            var gameModel = new app.gameModel;

            gameModel.fetch({
                success: function (form) {
                    var begin = form.get('begin');
                    if (!begin['whiteKings']) begin['whiteKings'] = [];
                    if (!begin['blackKings']) begin['blackKings'] = [];
                    if (!begin['whites']) begin['whites'] = [];
                    if (!begin['blacks']) begin['blacks'] = [];

                    context.begin = new app.positionModel(begin);
                    context.positionView = new app.positionView({model: new app.positionModel(begin)});

                    var plies = context.getPliesArray(form.get('mainMoves'));
                    context.pliesCollection = new app.pliesCollection(plies);
                    var pliesView = new app.pliesView({collection: context.pliesCollection});
                    $('#game-plies').append(pliesView.render().el);

                    context.possiblePliesCollection = new app.possiblePliesCollection();
                    var possibles = new app.possiblePliesView({collection: context.possiblePliesCollection});
                    $('#possiblePlies').append(possibles.el);
                    context.possiblePliesCollection.getPossibles(JSON.stringify(begin));
                }
            });
        },
        createBoard: function (brdSize) {
            var firstSquareIsWhite;
            for (var i = brdSize; i >= 1; i--) {
                $('#draughts-board').append('<div id="' + i + '-row" class="' + this.getRowClassName() + '"></div>');
                firstSquareIsWhite = (i % 2 == 0);
                for (var j = 1; j < brdSize + 1; j++) {
                    var squareColor = this.determineSquareColor(firstSquareIsWhite, j);
                    var squareCoordinate = this.determineSquareCoordinate(i, j);
                    var squareClassName = this.getSquareClassName();
                    if (squareColor === 'black')
                        $('#' + i + '-row').append('<div id="' + squareCoordinate
                            + '-square" class="' + squareClassName + ' ' + squareColor + '-square"></div>');
                    else
                        $('#' + i + '-row').append('<div class="' + squareClassName + ' ' + squareColor + '-square"></div>');
                }
            }
        },
        determineSquareColor: function (firstSquareIsWhite, j) {
            if (firstSquareIsWhite)
                if (j % 2 != 0)
                    return "white";
                else
                    return "black";
            else if (!firstSquareIsWhite)
                if (j % 2 != 0)
                    return "black";
                else
                    return "white";
        },
        determineSquareCoordinate: function (i, j) {
            var index = (this.brdSize + 1 - i - 1) * 4 + Math.ceil(j / 2) - 1;
            return app.hash[index];
        },
        getSquareClassName: function () {
            return 'board8x8-square';
        },
        getRowClassName: function () {
            return 'row8x8';
        },
        events: {
            "click #next-step": "nextStep",
            "click #prev-step": "prevStep"
        },
        nextStep: function () {
            if (this.currentPlyIndex < this.pliesCollection.length) {
                ++this.currentPlyIndex;
                var position = this.pliesCollection.at(this.currentPlyIndex - 1).get('position');
                this.positionView.model.set(position.toJSON());
            }
        },
        prevStep: function () {
            if (this.currentPlyIndex <= 0) {
                return;
            }
            --this.currentPlyIndex;
            var position = this.begin;
            if (this.currentPlyIndex != 0) {
                position = this.pliesCollection.at(this.currentPlyIndex - 1).get('position');
            }
            this.positionView.model.set(position.toJSON());
        },
        getPliesArray: function (response) {
            var parsedMoves = [];
            var moves = response["moves"];
            for (var i = 0; i < moves.length; i++) {
                var ply = moves[i];
                if (!ply["plyPosition"]["position"].whites) ply["plyPosition"]["position"].whites = [];
                if (!ply["plyPosition"]["position"].whiteKings) ply["plyPosition"]["position"].whiteKings = [];
                if (!ply["plyPosition"]["position"].blacks) ply["plyPosition"]["position"].blacks = [];
                if (!ply["plyPosition"]["position"].blackKings) ply["plyPosition"]["position"].blackKings = [];

                parsedMoves.push(new app.plyModel({
                    index: ply["number"],
                    commentBefore: ply["comment"]["commentBefore"],
                    commentAfter: ply["comment"]["commentAfter"],
                    rate: ply["comment"]["rate"],
                    from: ply["plyPosition"]["from"],
                    to: ply["plyPosition"]["to"],
                    position: new app.positionModel(ply["plyPosition"]["position"]),
                    alternatives: ply["alternatives"]
                }));
            }
            return parsedMoves;
        }
    });

    var LEFT_ARROW = 37;
    var RIGHT_ARROW = 39;
    var game = new app.gameView({
        el: '#draughts-board-demonstration',
        collection: new app.pliesCollection()
    });

    $(document).keydown(function(e) {
        switch(e.which) {
            case LEFT_ARROW:
                game.prevStep();
                break;

            case RIGHT_ARROW:
                game.nextStep();
                break;
            default: return; // exit this handler for other keys
        }
        e.preventDefault();
    });
})(jQuery);