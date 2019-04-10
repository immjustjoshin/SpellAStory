package edu.gatech.spellastory.game.menu

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import edu.gatech.spellastory.R
import kotlinx.android.synthetic.main.activity_level_select.*

fun Context.LevelSelectIntent() = Intent(this, LevelSelectActivity::class.java)

class LevelSelectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level_select)

        btn_5.setOnClickListener { startActivity(PhonemeListIntent(5)) }
        btn_6.setOnClickListener { startActivity(PhonemeListIntent(6)) }
        btn_7.setOnClickListener { startActivity(PhonemeListIntent(7)) }
        btn_8.setOnClickListener { startActivity(PhonemeListIntent(8)) }
    }
}
