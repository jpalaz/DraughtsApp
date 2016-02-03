$(function(){
    window.App = {
        Models: {},
        Views: {},
        Collections: {}
    };


    App.Models.PlyDTO  = Backbone.Model.extend({
        default: {
            index: 1,
            commentBefore: undefined,
            commentAfter: undefined,
            rate: undefined,
            whiteSide: true,
            from: 22,
            to: 18,
            position: {
                whites: [],
                whiteKings: [],
                blacks: [],
                blackKings: []
            },
            alternatives: [],
            parse: function(response) {

            }
        }
    });

    App.Collections.Board = Backbone.Collection.extend({
        model: App.Models.PlyDTO
    });

    //THEN IT MUST BECOME PARSE METHOD
    function parsePlies(response) {
        var parsedMoves = [];
        var moves = response["mainMoves"]["moves"];
        for(var i = 0; i < moves.length; i++) {
            var ply = moves[i];
            if(!ply["alternatives"]) ply["position"]["alternatives"] = [];
            if(!ply["position"].whites) ply["position"].whites = [];
            if(!ply["position"].whiteKings) ply["position"].whiteKings = [];
            if(!ply["position"].blacks) ply["position"].blacks = [];
            if(!ply["position"].blackKings) ply["position"].blackKings = [];
            parsedMoves.push(new App.Models.PlyDTO({
                index: ply["metadata"]["number"],
                commentBefore: ply["comment"]["commentBefore"],
                commentAfter: ply["comment"]["commentAfter"],
                rate: ply["comment"]["rate"],
                whiteSide: ply["metadata"]["whiteSide"],
                from: ply["metadata"]["from"],
                to: ply["metadata"]["to"],
                position: ply["position"],
                alternatives: ply["alternatives"]
            }));
        }
        return parsedMoves;
    }

    App.Views.Game = Backbone.View.extend({
        brdSize: 8,
        currentStep: 1,
        initialize: function () {
            this.createBoard(this.brdSize);

            // THEN IT WILL BE FETCH METHOD
            var game = {
                "id": "1",
                "metadata": {
                    "event": "Belarus Highest League 2015",
                    "site": "Minsk",
                    "date": 1454349597638,
                    "round": 1
                },
                "white": "White",
                "black": "Black",
                "result": "BLACK_WON",
                "gameType": 25,
                "begin": {
                    "whites": [21,22,23,24,25,26,27,28,29,30,31,32],
                    "whiteKings": null,
                    "blacks": [1,2,3,4,5,6,7,8,9,10,11,12],
                    "blackKings": null
                },
                "mainMoves": {
                    "moves": [
                        {
                            "metadata": {
                                "number": 1,
                                "from": 22,
                                "to": 18,
                                "whiteSide": false,
                                "beat": false
                            },
                            "position": {
                                "whites": [21,22,23,24,25,26,27,28,29,30,31,32],
                                "whiteKings": null,
                                "blacks": [1,2,3,4,5,6,7,8,9,10,11,12],
                                "blackKings": null
                            },
                            "comment": {
                                "commentBefore": "Comment before first ply",
                                "commentAfter": "Comment after first ply",
                                "rate": "GOOD"
                            },
                            "alternatives": null
                        },
                        {
                            "metadata": {
                                "number": 2,
                                "from": 22,
                                "to": 18,
                                "whiteSide": false,
                                "beat": false
                            },
                            "position": {
                                "whites": [21,18,23,24,25,26,27,28,29,30,31,32],
                                "whiteKings": null,
                                "blacks": [1,2,3,4,5,6,7,8,9,10,11,12],
                                "blackKings": null
                            },
                            "comment": {
                                "commentBefore": "Comment before first ply",
                                "commentAfter": "Comment after first ply",
                                "rate": "GOOD"
                            },
                            "alternatives": null
                        },
                        {
                            "metadata": {
                                "number": 3,
                                "from": 2,
                                "to": 13,
                                "whiteSide": false,
                                "beat": false
                            },
                            "position": {
                                "whites": [6],
                                "whiteKings": [12,22],
                                "blacks": null,
                                "blackKings": [1,2,3,4]
                            },
                            "comment": {
                                "commentBefore": "Comment before first ply23",
                                "commentAfter": "Comment after first ply12",
                                "rate": "BAAAD"
                            },
                            "alternatives": null
                        }
                    ]
                }
            };

            var plies = parsePlies(game);
            for(let i = 0; i < plies.length; i++) {
                this.collection.add(plies[i]);
            }
            this.fillGameStepsSidebar();
            this.render(1);
        },
        fillGameStepsSidebar: function () {
            var sep = ',';
            for(var i = 0; i < this.collection.length; i++) {
                if(i == this.collection.length-1) sep = '.';
                this.insertPlyIntoStepsSidebar(this.collection.at(i).attributes, sep,'#game-steps', false);
            }
        },
        insertPlyIntoStepsSidebar: function (ply,sep,appendLocation, isAlternative) {
            var listEl = this.determineListEl(ply);
            var stepNum = '';
            var classPostfix = '';
            if(isAlternative) classPostfix = '-alternative';

            if(ply.whiteSide) {
                stepNum = Math.ceil(ply.index/2) + '. ';
                sep = " ";
            }

            if(ply.commentBefore != undefined)
                $(appendLocation).append('<li id="' + ply.index + '-commentBefore" class="list-element-comment'+ classPostfix +'">[ '
                     + ply.commentBefore +' ]</li>');

            $(appendLocation).append('<li id="' + ply.index + '-step" class="list-element-ply'+ classPostfix +'">'+ stepNum + listEl + sep +'</li>');

            if(ply.commentAfter != undefined)
                $(appendLocation).append('<li id="' + ply.index + '-commentAfter" class="list-element-comment'+ classPostfix +'">[ '
                     + ply.commentAfter +' ]</li>');

            if(ply.alternatives != undefined) {
                $(appendLocation).append('<li id="' + ply.index + '-alternatives" class="list-element-alternative'+ classPostfix +'"></li>');

                var alternative = $('#' + ply.index + '-alternatives');

                alternative.append('{ ');
                var sep = ',';
                for(var i = 0; i < ply.alternatives.length; i++ ) {
                    if(i == ply.alternatives.length-1) sep = '.';
                    this.insertPlyIntoStepsSidebar(ply.alternatives[i].attributes,sep,alternative,true);
                }

                alternative.append(' }');
            }
        },
        determineListEl: function (ply) {
            var from = this.convert10x10to8x8(ply.from);
            var to = this.convert10x10to8x8(ply.to);
            return from.horizontalCoordinate + from.verticalCoordinate + "-" + to.horizontalCoordinate + to.verticalCoordinate;
        },
        convert10x10to8x8: function (coordinate10x10) {
            //hor array just a wow mind games 9k
            var hor = ['A','C','E','G','B','D','F','H'];
            var verticalCoordinate = Math.floor((coordinate10x10-1)/4) + 1;
            //and here 9k
            var horizontalCoordinate = hor[(coordinate10x10-1)%8];
            return {
                verticalCoordinate: verticalCoordinate,
                horizontalCoordinate: horizontalCoordinate
            };
// 9 3A
        },
        convert8x8to10x10: function (coordinate8x8) {
            // is not working now
        },
        createBoard: function (brdSize) {
            var firstSquareIsWhite;
            for(var i = brdSize; i >= 1; i--) {
                $('#draughts-board').append('<div id="'+ i +'-row" class="'+ this.getRowClassName() +'"></div>');
                firstSquareIsWhite = (i % 2 == 0);
                for(var j = 1; j < brdSize + 1; j++) {
                    var squareColor = this.determineSquareColor(firstSquareIsWhite,j);
                    var squareCoordinate =  this.determineSquareCoordinate(i,j);
                    var squareClassName = this.getSquareClassName();
                    if(squareColor === 'black')
                        $('#'+ i +'-row').append('<div id="'+ squareCoordinate +'-square" class="' + squareClassName + ' '+squareColor+'-square"></div>');
                    else
                        $('#'+ i +'-row').append('<div class="' + squareClassName + ' '+squareColor+'-square"></div>');
                }
            }
        },
        determineSquareColor: function (firstSquareIsWhite,j) {
        if(firstSquareIsWhite)
            if( j % 2 != 0)
                return "white";
            else
                return "black";
        else if(!firstSquareIsWhite)
            if(j % 2 != 0)
                return "black";
            else
                return "white";

    },
        determineSquareCoordinate: function (i,j) {
            return i.toString() + String.fromCharCode(64+j);
        },
        getSquareClassName: function () {
            return 'board8x8-square';
        },
        getRowClassName: function () {
            return 'row8x8';
        },
        events: {
            "click #next-step"   : "nextStep",
            "click #prev-step"   : "prevStep",

        },
        nextStep: function () {
            if(this.currentStep < this.collection.length) {
                    ++this.currentStep
                    this.render(this.currentStep);
            }
        },
        prevStep: function () {
            if(this.currentStep > 1) {
                --this.currentStep
                this.render(this.currentStep);
            }
        },
        putFigure: function (coordinate10x10, className) {
            var coordinate8x8 = this.convert10x10to8x8(coordinate10x10);
            $('#'+coordinate8x8.verticalCoordinate+coordinate8x8.horizontalCoordinate+'-square').addClass(className);
        },
        clearSquare: function (i) {
            var coordinate8x8 = this.convert10x10to8x8(i);
            $('#'+coordinate8x8.verticalCoordinate+coordinate8x8.horizontalCoordinate+'-square').removeClass('white-figure');
            $('#'+coordinate8x8.verticalCoordinate+coordinate8x8.horizontalCoordinate+'-square').removeClass('black-figure');
            $('#'+coordinate8x8.verticalCoordinate+coordinate8x8.horizontalCoordinate+'-square').removeClass('white-queen');
            $('#'+coordinate8x8.verticalCoordinate+coordinate8x8.horizontalCoordinate+'-square').removeClass('black-queen');
        },
        render: function (index) {
            var playerPosition = this.collection.at(index-1).attributes.position;
            var whites = playerPosition.whites;
            var blacks = playerPosition.blacks;
            var whiteKings = playerPosition.whiteKings;
            var blackKings = playerPosition.blackKings;
            for(var i = 1; i <= this.brdSize*this.brdSize/2; i++) {
                this.clearSquare(i);
            }

            for(var i = 0; i < whites.length; i++) {
                this.putFigure(whites[i],'white-figure');
            }
            for(var i = 0; i < blacks.length; i++) {
                this.putFigure(blacks[i],'black-figure');
            }
            for(var i = 0; i < whiteKings.length; i++) {
                this.putFigure(whiteKings[i],'white-queen');
            }
            for(var i = 0; i < blackKings.length; i++) {
                this.putFigure(blackKings[i],'black-queen');
            }
        }
    });

    App.Views.Game10x10 = App.Views.Game.extend({
        brdSize: 10,
        determineSquareCoordinate: function(i,j) {
            return 5*(i - 1) + Math.ceil(j/2);
        },
        getSquareClassName: function(squareCoordinate) {
            return 'board10x10-square';
        },
        getRowClassName: function() {
            return 'row10x10';
        },
        determineListEl: function(ply) {
            return ply.from + " - " + ply.to;
        },
        putFigure: function (coordinate10x10, className) {
            $('#'+coordinate10x10.toString()+'-square').addClass(className);
        },
        clearSquare: function (i) {
            $('#' + i + '-square').removeClass('white-figure');
            $('#' + i + '-square').removeClass('black-figure');
            $('#' + i + '-square').removeClass('white-queen');
            $('#' + i + '-square').removeClass('black-queen');
        }
    });

    App.Views.Game8x8 = App.Views.Game.extend({

    });


    var game_positions = new App.Collections.Board();

    //window.draughts_board_demonstration = new App.Views.Game10x10({collection: game_positions, el: '#draughts-board-demonstration'});
    window.draughts_board_demonstration = new App.Views.Game8x8({collection: game_positions, el: '#draughts-board-demonstration'});


});