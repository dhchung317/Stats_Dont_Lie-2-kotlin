package com.hyunki.statsdontlie

interface OnFragmentInteractionListener {
    fun displayMenuFragment()
    fun displayGameFragment()
    fun displayResultFragment(playerCorrectGuesses: Int, playerIncorrectGuesses: Int)
}