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

(function run() {
    tableGamesHeader = TABLE_GAMES.firstElementChild.outerHTML;
    tablePlayersHeader = TABLE_PLAYERS.firstElementChild.outerHTML;
    getTournament();
})();

function getTournament() {
    $.ajax({
        url : 'tournaments',
        success : function(data) {
            updateStates(data);
            createAllPlayers(data.players);
            createAllRounds(data.rounds);
        },
        dataType : "json"
    });
}

function updateStates(data) {
    TOURNAMENT_SYSTEM.innerHTML = data.system;
    PLAYERS_NUMBER.innerHTML = data.players.length;
    CURRENT_ROUND.innerHTML = data.currentRound;
    TOURNAMENT_NAME.innerHTML = data.name;
    TOURNAMENT_PLACE.innerHTML = data.place;
    TOURNAMENT_ARBITER.innerHTML = data.arbiter;
    TOURNAMENT_DATE.innerHTML = getDateString(new Date(data.begin))
        + ' - ' + getDateString(new Date(data.end));
}

function getDateString(date) {
    return date.getDay() + '.' + date.getMonth() + '.' + date.getFullYear();
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

function createAllRounds(rounds) {
    TABLE_GAMES.innerHTML = "";
    var header = TABLE_GAMES.insertRow(-1);
    header.outerHTML = tableGamesHeader;
    for (var i = 0; i < rounds.length; i++) {
        var dateString = getDateString(new Date(rounds[i].date));
        addRoundItem(dateString, i + 1);
        var games = rounds[i].games;
        for (var j = 0; j < games.length; j++) {
            addGameToHTML(games[j], j + 1);
        }
    }
}

function addRoundItem(dateString, index) {
    var row = TABLE_GAMES.insertRow(-1);
    row.innerHTML = '<td colspan="4" class="text-center warning">Round ' + index
        + ' ' + dateString + ' </td>';
}

function addGameToHTML(game, index) {
    var row = TABLE_GAMES.insertRow(-1);
    row.innerHTML = '<td>' + index + '</td><td>' + game.white.name.surname
        + '</td><td>' + getResultString(game.result) + '</td><td>'
        + game.black.name.surname + '</td>';
}

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
