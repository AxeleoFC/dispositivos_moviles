package com.flores.aplicacionmoviles.logic.validator

import com.flores.aplicacionmoviles.logic.data.MarvelChars

class ListItems {

    fun returnMarveelChars(): List<MarvelChars> {
        val items = listOf(
            MarvelChars(
                1,
                "Spiderman",
                "Civil War",
                "https://comicvine.gamespot.com/a/uploads/scale_medium/11170/111705043/8870319-spidey.jpg"
            ),
            MarvelChars(
                2,
                "Miles Morales",
                "Ultimate Marvel",
                "https://comicvine.gamespot.com/a/uploads/scale_small/12/124259/8667575-35.jpg"
            ),
            MarvelChars(
                3,
                "Daredevil",
                "The Mighty World of Marvel",
                "https://comicvine.gamespot.com/a/uploads/scale_small/11118/111187046/7397359-0398898002-EQH1ysWWsAA7QLf"
            ),
            MarvelChars(
                4,
                "Deadpool",
                "Wolverine and Deadpool",
                "https://comicvine.gamespot.com/a/uploads/scale_small/12/124259/8926324-large-2680196.jpg"
            ),
            MarvelChars(
                5,
                "Kate Bishop",
                "Hawkeye",
                "https://comicvine.gamespot.com/a/uploads/scale_small/12/124259/8251802-hawkeye_kate_bishop_vol_1_1_textless.jpg"
            )
        )
        return items
    }
}