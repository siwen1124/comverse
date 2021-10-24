package edu.utcs.comicwiki

import edu.utcs.comicwiki.ui.MainViewModel
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun URL2Path_isCorrect() {
        val mv = MainViewModel
        val str = mv.URL2Path("https://comicvine.gamespot.com/api/team/4060-2171/")
        println(str)
    }
}
