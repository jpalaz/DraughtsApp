$(function () {
    window.App = {
        Models: {},
        Views: {},
        Collections: {}
    };

    window.App.template = function(id) {
        return _.template( $('#' + id).html() );
    };

    window.App.hash = [
        'b8', 'd8', 'f8', 'h8',
        'a7', 'c7', 'e7', 'g7',
        'b6', 'd6', 'f6', 'h6',
        'a5', 'c5', 'e5', 'g5',
        'b4', 'd4', 'f4', 'h4',
        'a3', 'c3', 'e3', 'g3',
        'b2', 'd2', 'f2', 'h2',
        'a1', 'c1', 'e1', 'g1'
    ];

    App.Views.Position = Backbone.View.extend({
        render: function (position) {
            var whites = position.get('whites');
            var blacks = position.get('blacks');
            var whiteKings = position.get('whiteKings');
            var blackKings = position.get('blackKings');
            for (var i = 1; i <= this.brdSize * this.brdSize / 2; i++) {
                this.clearSquare(i);
            }
            for ( i = 0; i < whites.length; i++) {
                this.putFigure(whites[i], 'white-figure');
            }
            for ( i = 0; i < blacks.length; i++) {
                this.putFigure(blacks[i], 'black-figure');
            }
            for ( i = 0; i < whiteKings.length; i++) {
                this.putFigure(whiteKings[i], 'white-queen');
            }
            for ( i = 0; i < blackKings.length; i++) {
                this.putFigure(blackKings[i], 'black-queen');
            }
        },
        putFigure: function (coordinate10x10, className) {
            var coordinate8x8 = App.hash[coordinate10x10 - 1];
            $('#' + coordinate8x8 + '-square').addClass(className);
        },
        clearSquare: function (i) {
            var coordinate8x8 = App.hash[i - 1];
            var $coordSquare = $('#' + coordinate8x8 + '-square');
            $coordSquare.removeClass('white-figure');
            $coordSquare.removeClass('black-figure');
            $coordSquare.removeClass('white-queen');
            $coordSquare.removeClass('black-queen');
        }
    });

    App.Models.Position = Backbone.Model.extend({
        whites: [],
        whiteKings: [],
        blacks: [],
        blackKings: [],
        //isWhiteMove: true
        whiteMove: true,
    });

    App.Models.Ply = Backbone.Model.extend({
        default: {
            index: 1,
            commentBefore: undefined,
            commentAfter: undefined,
            rate: undefined,
            from: 22,
            to: 18,
            position: new App.Models.Position
        }
    });

    App.Collections.Plies = Backbone.Collection.extend({
        model: App.Models.Ply
    });

    App.Models.Game = Backbone.Model.extend({
        url: 'http://localhost:8080/games'
    });

    App.Views.Ply = Backbone.View.extend({
        tagName: 'span',
        className: 'ply',
        template: App.template('plyTemplate'),
        events: {
            'click > strong' : 'selectPly'
        },

        selectPly: function() {
            alert("A ply was clicked!");
        },
        render: function() {
            var ply = this.model;
            var plyNotation = this.getPlyNotation(ply);
            var plyLayout;
            if (!ply.get("position").get('whiteMove')) {
                plyLayout = ply.get('index') + ". " + plyNotation;
            } else
                plyLayout = ply.get('index')+ ". ... " + plyNotation;
            ply.set('indexString', plyLayout);
            this.$el.html( this.template(ply.toJSON()));
            return this;
        },
        getPlyNotation: function (ply) {
            var from = App.hash[ply.get('from') - 1];
            var to = App.hash[ply.get('to') - 1];
            return from + "-" + to;
        }
    });

    App.Views.Plies = Backbone.View.extend({
        tagName: 'div',

        initialize: function(){
            this.collection.on('add', this.addOne, this);
        },

        render: function(){
            this.collection.each(this.addOne, this);
            return this;
        },

        addOne: function(ply){
            var plyView = new App.Views.Ply({ model: ply });
            this.$el.append(plyView.render().el);
        }
    });

    App.Views.Game = Backbone.View.extend({
        brdSize: 8,
        currentPlyIndex: 0,
        begin: undefined,
        positionView: undefined,

        initialize: function () {
            this.createBoard(this.brdSize);
            var context = this;
            var gameView = new App.Models.Game;

            gameView.fetch({
                success: function (form) {
                    var game = form.attributes;
                    context.begin = new App.Models.Position(game.begin);
                    if (!context.begin.get('whiteKings')) context.begin.set('whiteKings',[]);
                    if (!context.begin.get('blackKings')) context.begin.set('blackKings',[]);
                    if (!context.begin.get('whites')) context.begin.set('whites',[]);
                    if (!context.begin.get('blacks')) context.begin.set('blacks',[]);

                    var plies = context.getPliesArray(game);
                    context.pliesCollection = new App.Collections.Plies(plies);
                    var pliesView = new App.Views.Plies({collection: context.pliesCollection });
                    $('#game-plies').append(pliesView.render().el);

                    context.positionView = new App.Views.Position({model: context.begin});
                    context.positionView.render(context.begin);

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
            return App.hash[
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
            "click #prev-step": "prevStep"
        },
        nextStep: function () {
            if (this.currentPlyIndex < this.pliesCollection.length) {
                ++this.currentPlyIndex;
                var position = this.pliesCollection.at(this.currentPlyIndex).get('position');
                this.positionView.render(position);
            }
        },
        prevStep: function () {
            if (this.currentPlyIndex > 0) {
                --this.currentPlyIndex;
                var position = this.pliesCollection.at(this.currentPlyIndex).get('position');
                this.positionView.render(position);
            }
        },


        getPliesArray: function (response) {
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
                    position: new App.Models.Position(ply["plyPosition"]["position"]),
                    alternatives: ply["alternatives"]
                }));
            }
            return parsedMoves;
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
        getPlyNotation: function (ply) {
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