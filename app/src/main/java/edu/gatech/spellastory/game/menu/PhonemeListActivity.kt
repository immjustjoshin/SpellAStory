package edu.gatech.spellastory.game.menu

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import edu.gatech.spellastory.R
import edu.gatech.spellastory.data.Levels
import edu.gatech.spellastory.data.LevelsDb
import edu.gatech.spellastory.data.PhonemeWordsPair
import edu.gatech.spellastory.game.GameIntent
import kotlinx.android.synthetic.main.activity_phoneme_list.*

fun Context.PhonemeListIntent(level: Int) = Intent(this, PhonemeListActivity::class.java)
    .apply { putExtra(INTENT_LEVEL, level) }

private const val INTENT_LEVEL = "level"

class PhonemeListActivity : AppCompatActivity() {

    private var level: Int = -1
    private val levelData: List<PhonemeWordsPair>
        get() = LevelsDb.getWordsForLevel(level)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phoneme_list)

        level = intent.getIntExtra(INTENT_LEVEL, -1)
        require(level != -1) { "no level provided in Intent extras" }

        rv_phonemes.adapter =
            PhonemeListAdapter(levelData) { word -> startActivity(GameIntent(word, level)) }
        rv_phonemes.layoutManager = LinearLayoutManager(this)
    }
}
