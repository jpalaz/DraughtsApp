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
    system: 'ROUND_ROBIN',
    url: 'tournaments/robin', // 'tournaments/swiss'
    currentRound: 0,
    currentGames: {}
};

(function run() {
    tableGamesHeader = TABLE_GAMES.firstElementChild.outerHTML;
    tablePlayersHeader = TABLE_PLAYERS.firstElementChild.outerHTML;
    getTournament();
})();

function getTournament() {
    $.ajax({
        type: "GET",
        url: appState.url,
        success: function (data) {
            updateStates(data);
            createAllPlayers(data.players);
            getNextRound();
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
        url: appState.url + '/players',
        success: function (data) {
            createAllPlayers(data);
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
    for (var i = 0, points = 0; i < appState.currentRound; ++i) {
        points += player.results[i].points;
    }
    row.innerHTML = '<td>' + index + '</td><td>' + player.person.surname
        + '</td><td>' + player.gamesPlayed + '</td>'
        + '<td>' + points + '</td>';
}

function getNextRound() {
    $.ajax({
        type: "GET",
        url: appState.url + '/next_round/',
        success: function (data) {
            CURRENT_ROUND.innerHTML = appState.currentRound = data.roundNumber;
            appState.currentGames = data;
            createRound(data);
        },
        error: function() {
            alert("Fail to get next round!");
        },
        dataType: "json"
    });
}

function createRound(round) {
    TABLE_GAMES.innerHTML = "";
    var header = TABLE_GAMES.insertRow(-1);
    header.outerHTML = tableGamesHeader;
    var dateString = getDateString(new Date(round.date));
    addRoundItem(dateString);
    var games = round.games;
    for (var j = 0; j < games.length; j++) {
        addGameToHTML(games[j], j + 1);
    }

}

function addRoundItem(dateString) {
    var row = TABLE_GAMES.insertRow(-1);
    row.innerHTML = '<td colspan="4" class="text-center warning">Round ' + appState.currentRound
        + ', ' + dateString + ' </td>';
}

function addGameToHTML(game, index) {
    var row = TABLE_GAMES.insertRow(-1);
    var drawSelectedText = 'selected';
    var whiteSelectedText = '';
    var blackSelectedText = '';
    if (game.white.person.surname == 'Free') {
        blackSelectedText = 'selected';
        drawSelectedText = '';
    } else if (game.black.person.surname == 'Free') {
        whiteSelectedText = 'selected';
        drawSelectedText = '';
    }

    row.innerHTML = '<td>' + index + '</td><td>' + game.white.person.surname
        + '</td><td><div class="col-sm-6"><select class="form-control round-result">'
        + '<option value="UNDEFINED">*</option>'
        + '<option value="WHITE_WON" ' + whiteSelectedText + '>2-0</option>'
        + '<option value="DRAW" ' + drawSelectedText + '>1-1</option>'
        + '<option value="BLACK_WON" ' + blackSelectedText + '>0-2</option></select></div></td><td>'
        + game.black.person.surname + '</td>';
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
        if (games[i].result == 'UNDEFINED') {
            if (games[i].white.person.surname == 'Free') {
                games[i].result == 'BLACK_WON';
            } else if (games[i].black.person.surname == 'Free') {
                games[i].result == 'WHITE_WON';
            } else {
                return false;
            }
        }
        delete games[i].white;
        delete games[i].black;
    }

    $.ajax({
        type: "POST",
        url: appState.url + '/current_round',
        data: JSON.stringify(games),
        success: function () {
            getNextRound();
            getPlayers();
        },
        error: function() { alert("Setup of current results failed!") },
        contentType: "application/json"
    });
});