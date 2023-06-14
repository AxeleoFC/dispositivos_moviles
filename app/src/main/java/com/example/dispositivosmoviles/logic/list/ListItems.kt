package com.example.dispositivosmoviles.logic.list

import com.example.dispositivosmoviles.data.entities.marvel.MarvelChars

class ListItems {

    fun returnMarvleChars(): List<MarvelChars> {
        return listOf(
            MarvelChars(
                1,
                "Spider-Man",
                "Ultimate Spider-Man (2006)",
                "https://comicvine.gamespot.com/a/uploads/scale_small/12/124259/8126579-amazing_spider-man_vol_5_54_stormbreakers_variant_textless.jpg"
            ),
            MarvelChars(
                2,
                "Daredevil",
                "Daredevil (1964)",
                "https://comicvine.gamespot.com/a/uploads/scale_small/11118/111187046/7397359-0398898002-EQH1ysWWsAA7QLf"
            ),
            MarvelChars(
                3,
                "Moon Knight",
                "Moon Knight (1989)",
                "https://comicvine.gamespot.com/a/uploads/scale_small/12/124259/8102817-moonknight.jpg"
            ),
            MarvelChars(
                4,
                "Black Cat",
                "The Amazing Spider-Man (2006)",
                "https://comicvine.gamespot.com/a/uploads/scale_small/11144/111442876/8759849-grr.jpg"
            ),
            MarvelChars(
                5,
                "Ben Reilly",
                "The Astonishing Spider-Man (1995)",
                "https://comicvine.gamespot.com/a/uploads/scale_small/12/124259/8695705-large-9829205.jpg"
            )
        )
    }
}