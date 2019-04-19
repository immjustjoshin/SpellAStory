package edu.gatech.spellastory

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import edu.gatech.spellastory.bank.BankIntent
import edu.gatech.spellastory.game.menu.LevelSelectIntent
import edu.gatech.spellastory.story.menu.StoryListIntent
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        btn_stories.setOnClickListener { startActivity(StoryListIntent()) }
        btn_words.setOnClickListener { startActivity(LevelSelectIntent()) }
        btn_bank.setOnClickListener { startActivity(BankIntent()) }
    }
}
