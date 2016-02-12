$(function () {
    window.App = {
        Models: {},
        Views: {},
        Collections: {}
    };

    App.Models.Ply = Backbone.Model.extend({
        default: {
            index: 1,
            commentBefore: undefined,
            commentAfter: undefined,
            rate: undefined,
            from: 22,
            to: 18,
            position: {
                whites: [],
                whiteKings: [],
                blacks: [],
                blackKings: [],
                //isWhiteMove: true
                whiteMove: true
            },
            alternatives: []
        }
    });

    App.Collections.Plies = Backbone.Collection.extend({
        model: App.Models.Ply
    });

    function parsePlies(response) {
        var parsedMoves = [];
        var moves = response["mainMoves"]["moves"];
        for (var i = 0; i < moves.length; i++) {
            var ply = moves[i];
            //if (!ply["alternatives"]) ply["alternatives"] = [];
            if (!ply["plyPosition"]["position"].whites) ply["plyPosition"]["position"].whites = [];
            if (!ply["plyPosition"]["position"].whiteKings) ply["plyPosition"]["position"].whiteKings = [];
            if (!ply["plyPosition"]["position"].blacks) ply["plyPosition"]["position"].blacks = [];
            if (!ply["plyPosition"]["position"].blackKings) ply["plyPosition"]["position"].blackKings = [];

            parsedMoves.push(new App.Models.Ply({
                index: ply["number"],
                commentBefore: ply["comment"]["commentBefore"],
                commentAfter: ply["comment"]["commentAfter"],
                rate: ply["comment"]["rate"],
                from: ply["plyPosition"]["from"],
                to: ply["plyPosition"]["to"],
                position: ply["plyPosition"]["position"],
                alternatives: ply["alternatives"]
            }));
        }
        return parsedMoves;
    }

    App.Models.Game = Backbone.Model.extend({
        url: 'http://localhost:8080/games'
    });

    App.Views.Game = Backbone.View.extend({
        brdSize: 8,
        currentStep: 0,
        hash: [
            'b8', 'd8', 'f8', 'h8',
            'a7', 'c7', 'e7', 'g7',
            'b6', 'd6', 'f6', 'h6',
            'a5', 'c5', 'e5', 'g5',
            'b4', 'd4', 'f4', 'h4',
            'a3', 'c3', 'e3', 'g3',
            'b2', 'd2', 'f2', 'h2',
            'a1', 'c1', 'e1', 'g1'
        ],
        begin: undefined,
        initialize: function () {
            this.createBoard(this.brdSize);
            var context = this;
            var game = new App.Models.Game;

            game.fetch({
                success: function (form) {
                    var g = form.attributes;

                    context.begin = g.begin;
                    if (!context.begin.whiteKings) context.begin.whiteKings = [];
                    if (!context.begin.blackKings) context.begin.blackKings = [];
                    if (!context.begin.whites) context.begin.whites = [];
                    if (!context.begin.blacks) context.begin.blacks = [];
                    console.log(context.begin);
                    var plies = parsePlies(g);
                    for (var i = 0; i < plies.length; i++) {
                        context.collection.add(plies[i]);
                    }
                    context.fillGameStepsSidebar();
                    context.render(0);
                }
            });
        },
        fillGameStepsSidebar: function () {
            var sep = ',';
            for (var i = 0; i < this.collection.length; i++) {
                if (i == this.collection.length - 1) sep = '.';
                this.insertPlyIntoStepsSidebar(this.collection.at(i).attributes, sep, '#game-steps', false);
            }
        },
        insertPlyIntoStepsSidebar: function (ply, sep, appendLocation, isAlternative) {
            var listEl = this.determineListEl(ply);
            var stepNum = '';
            var classPostfix = '';
            if (isAlternative) classPostfix = '-alternative';

            //if (!ply.position.isWhiteMove) {
            if (!ply.position.whiteMove) {
                sep = ". ";
            } else sep = ". ... "

            if (ply.commentBefore != undefined)
                $(appendLocation).append('<li id="' + ply.index + '-commentBefore" class="list-element-comment' + classPostfix + '"> '
                    + ply.commentBefore + ' </li>');

            $(appendLocation).append('<li id="' + ply.index + '-step" class="list-element-ply' + classPostfix + '">'
                + ply.index + sep + listEl + '</li>');

            if (ply.commentAfter != undefined)
                $(appendLocation).append('<li id="' + ply.index + '-commentAfter" class="list-element-comment' + classPostfix + '"> '
                    + ply.commentAfter + ' </li>');

            if (ply.alternatives != undefined) {
                $(appendLocation).append('<li id="' + ply.index + '-alternatives" class="list-element-alternative' + classPostfix + '"></li>');

                var alternative = $('#' + ply.index + '-alternatives');

                alternative.append('{ ');
                var sep = ',';
                for (var i = 0; i < ply.alternatives.length; i++) {
                    if (i == ply.alternatives.length - 1) sep = '.';
                    this.insertPlyIntoStepsSidebar(ply.alternatives[i].attributes, sep, alternative, true);
                }

                alternative.append(' }');
            }
        },
        determineListEl: function (ply) {
            var from = this.hash[ply.from - 1];
            var to = this.hash[ply.to - 1];
            return from + "-" + to;
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
            return this.hash[
            (this.brdSize + 1 - i - 1) * 4 + Math.ceil(j / 2) - 1
                ];
        },
        getSquareClassName: function () {
            return 'board8x8-square';
        },
        getRowClassName: function () {
            return 'row8x8';
        },
        events: {
            "click #next-step": "nextStep",
            "click #prev-step": "prevStep",
        },
        nextStep: function () {
            if (this.currentStep < this.collection.length) {
                ++this.currentStep
                this.render(this.currentStep);
            }
        },
        prevStep: function () {
            if (this.currentStep > 0) {
                --this.currentStep
                this.render(this.currentStep);
            }
        },
        putFigure: function (coordinate10x10, className) {
            var coordinate8x8 = this.hash[coordinate10x10 - 1];
            $('#' + coordinate8x8 + '-square').addClass(className);
        },
        clearSquare: function (i) {
            var coordinate8x8 = this.hash[i - 1];
            var $coordSquare = $('#' + coordinate8x8 + '-square');
            $coordSquare.removeClass('white-figure');
            $coordSquare.removeClass('black-figure');
            $coordSquare.removeClass('white-queen');
            $coordSquare.removeClass('black-queen');
        },
        render: function (index) {
            var playerPosition;
            if (index != 0) {
                playerPosition = this.collection.at(index - 1).attributes.position;
            } else {
                playerPosition = this.begin;
            }
            var whites = playerPosition.whites;
            var blacks = playerPosition.blacks;
            var whiteKings = playerPosition.whiteKings;
            var blackKings = playerPosition.blackKings;
            for (var i = 1; i <= this.brdSize * this.brdSize / 2; i++) {
                this.clearSquare(i);
            }

            for (var i = 0; i < whites.length; i++) {
                this.putFigure(whites[i], 'white-figure');
            }
            for (var i = 0; i < blacks.length; i++) {
                this.putFigure(blacks[i], 'black-figure');
            }
            for (var i = 0; i < whiteKings.length; i++) {
                this.putFigure(whiteKings[i], 'white-queen');
            }
            for (var i = 0; i < blackKings.length; i++) {
                this.putFigure(blackKings[i], 'black-queen');
            }
        }
    });

    App.Views.Game10x10 = App.Views.Game.extend({
        brdSize: 10,
        determineSquareCoordinate: function (i, j) {
            return 5 * (i - 1) + Math.ceil(j / 2);
        },
        getSquareClassName: function (squareCoordinate) {
            return 'board10x10-square';
        },
        getRowClassName: function () {
            return 'row10x10';
        },
        determineListEl: function (ply) {
            return ply.from + " - " + ply.to;
        },
        putFigure: function (coordinate10x10, className) {
            $('#' + coordinate10x10.toString() + '-square').addClass(className);
        },
        clearSquare: function (i) {
            var $coordSquare = $('#' + i + '-square');
            $coordSquare.removeClass('white-figure');
            $coordSquare.removeClass('black-figure');
            $coordSquare.removeClass('white-queen');
            $coordSquare.removeClass('black-queen');
        }
    });

    App.Views.Game8x8 = App.Views.Game.extend({});

    var game_positions = new App.Collections.Plies();
    //window.draughts_board_demonstration = new App.Views.Game10x10({collection: game_positions, el: '#draughts-board-demonstration'});
    window.draughts_board_demonstration = new App.Views.Game({
        collection: game_positions,
        el: '#draughts-board-demonstration'
    });
});