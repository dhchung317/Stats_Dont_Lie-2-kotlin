package com.hyunki.statsdontlie2

interface OnFragmentInteractionListener {
    fun displayMenuFragment()
    fun displayGameFragment()
    fun displayResultFragment(playerCorrectGuesses: Int, playerIncorrectGuesses: Int)
}