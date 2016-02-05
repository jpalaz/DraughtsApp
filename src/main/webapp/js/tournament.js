'use strict';

var TABLE_GAMES = document.getElementById('games');
var TABLE_PLAYERS = document.getElementById('players');
var PLAYERS_NUMBER = document.getElementById('players-count');
var TOURNAMENT_SYSTEM = document.getElementById('system');
var CURRENT_ROUND = document.getElementById('current-round');
var TOURNAMENT_NAME = document.getElementById('tournament-name');
var TOURNAMENT_PLACE = document.getElementById('tournament-place');
var TOURNAMENT_ARBITER = document.getElementById('arbiter');
var TOURNAMENT_DATE = document.getElementById('date');

var tableGamesHeader;
var tablePlayersHeader;

var appState = {
    system: 'SWISS',
    currentRound: 0,
    currentGames: {}
};

(function run() {
    tableGamesHeader = TABLE_GAMES.firstElementChild.outerHTML;
    tablePlayersHeader = TABLE_PLAYERS.firstElementChild.outerHTML;
    getTournament();
    getNextRound()
})();

function getTournament() {
    $.ajax({
        //url : 'tournaments',
        type: "GET",
        url: 'tournaments/swiss',
        success: function (data) {
            updateStates(data);
            createAllPlayers(data.players);
        },
        dataType: "json"
    });
}

function updateStates(data) {
    TOURNAMENT_SYSTEM.innerHTML = appState.system = data.system;
    PLAYERS_NUMBER.innerHTML = data.players.length;
    TOURNAMENT_NAME.innerHTML = data.name;
    TOURNAMENT_PLACE.innerHTML = data.place;
    TOURNAMENT_ARBITER.innerHTML = data.arbiter;
    TOURNAMENT_DATE.innerHTML = getDateString(new Date(data.begin))
        + ' - ' + getDateString(new Date(data.end));
}

function getDateString(date) {
    return date.getDay() + '.' + date.getMonth() + '.' + date.getFullYear();
}

function getPlayers() {
    $.ajax({
        type: "GET",
        url: 'tournaments/players',
        success: function (data) {
            createAllPlayers(data.players);
        },
        dataType: "json"
    });
}

function createAllPlayers(players) {
    TABLE_PLAYERS.innerHTML = "";
    var header = TABLE_PLAYERS.insertRow(-1);
    header.outerHTML = tablePlayersHeader;
    for (var i = 0; i < players.length; i++) {
        addPlayerToHTML(players[i], i + 1);
    }
}

function addPlayerToHTML(player, index) {
    var row = TABLE_PLAYERS.insertRow(-1);
    row.innerHTML = '<td>' + index + '</td><td>' + player.name.surname
        + '</td><td>' + player.gamesPlayed + '</td><td>' + player.points + '</td>';
}

function getNextRound() {
    $.ajax({
        type: "GET",
        url: 'tournaments/swiss/next_round/' + appState.currentRound,
        success: function (data) {
            CURRENT_ROUND.innerHTML = ++appState.currentRound;
            appState.currentGames = data;
            createRound(data);
        },
        dataType: "json"
    });
}

function createRound(round) {
    TABLE_GAMES.innerHTML = "";
    var header = TABLE_GAMES.insertRow(-1);
    header.outerHTML = tableGamesHeader;
    var dateString = getDateString(new Date(round.date));
    addRoundItem(dateString, appState.currentRound);
    var games = round.games;
    for (var j = 0; j < games.length; j++) {
        addGameToHTML(games[j], j + 1);
    }

}

function addRoundItem(dateString, index) {
    var row = TABLE_GAMES.insertRow(-1);
    row.innerHTML = '<td colspan="4" class="text-center warning">Round ' + index
        + ', ' + dateString + ' </td>';
}

function addGameToHTML(game, index) {
    var row = TABLE_GAMES.insertRow(-1);
    row.innerHTML = '<td>' + index + '</td><td>' + game.white.name.surname
        + '</td><td><div class="col-sm-6"><select class="form-control round-result">'
        + '<option value="UNDEFINED">*</option>'
        + '<option value="WHITE_WON">2-0</option>'
        + '<option value="DRAW">1-1</option>'
        + '<option value="BLACK_WON">0-2</option></select></div></td><td>'
        + game.black.name.surname + '</td>';
} //getResultString(game.result)

function getResultString(result) {
    switch (result) {
        case 'WHITE_WON':
            return '2-0';
        case 'DRAW':
            return '1-1';
        case 'BLACK_WON':
            return '0-2';
        case 'UNDEFINED':
        default:
            return '*';
    }
}

function getResultEnumValue(result) {
    switch (result) {
        case '2-0':
            return 'WHITE_WON';
        case '1-1':
            return 'DRAW';
        case '0-2':
            return 'BLACK_WON';
        case '*':
        default:
            return 'UNDEFINED';
    }
}

$("#submit-round").click(function () {
    var games = appState.currentGames.games;
    var results = document.getElementsByClassName("round-result");
    for (var i = 0; i < games.length; ++i) {
        var e = results[i];
        games[i].result = getResultEnumValue(e.options[e.selectedIndex].text);
    }
    var url = 'tournaments/current_round';
    if (appState.system == 'SWISS') {
        url += '/swiss'
    } else {
        url += '/robin'
    }

    $.ajax({
        type: "POST",
        url: 'tournaments/current_round',
        data: JSON.stringify(games),
        success: function () {
            getNextRound();
            getPlayers();
        },
        error: function() { alert("Fail!") },
        contentType: "application/json"
    });
});