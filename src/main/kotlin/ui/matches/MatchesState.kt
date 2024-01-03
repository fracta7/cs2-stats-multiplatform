package ui.matches

import model.MatchData

data class MatchesState(
    var matches: MutableList<MatchData> = mutableListOf()
)
