package com.hyunki.statsdontlie2.controller

interface OnFragmentInteractionListener {
    fun displayMenuFragment()
    fun displayGameFragment()
    fun displayResultFragment()
    fun setResultsDataFromGameFragment(playerCorrectGuesses: Int, playerIncorrectGuesses: Int)
}